package com.example.controller;

import com.example.domain.AccessToken;
import com.example.domain.UserInfo;
import com.example.utils.DecriptUtil;
import com.example.utils.GetPostUtil;
import com.example.utils.GetPropertiesUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/wechat")
public class AccessTokenController {

    Properties properties = GetPropertiesUtil.getProperties();

//    @Value("wx.accessToken")
    private String token = properties.getProperty("token");
//
//    @Value("${wx.appid}")
    private String APPID = properties.getProperty("APPID");
//
//    @Value("wx.appsecret")
    private String APPSECRET = properties.getProperty("APPSECRET");
//
//    @Value("wx.accessTokenUrl")
    private String ACCESS_TOKEN_URL = properties.getProperty("ACCESS_TOKEN_URL");
//
//    @Value("wx.openIdUrl")
    private String OPENID_ID_URL = properties.getProperty("OPENID_ID_URL");
//
//    @Value("wx.userInfo")
    private String USER_INFO = properties.getProperty("USER_INFO");



    /**
     * 信息校验
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/h")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("开始签名校验");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        ArrayList<String> array = new ArrayList<String>();
        array.add(signature);
        array.add(timestamp);
        array.add(nonce);

        //排序
        String sortString = DecriptUtil.sort(token, timestamp, nonce);
        //加密
        String mytoken = DecriptUtil.SHA1(sortString);
        //校验签名
        if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
            System.out.println("签名校验通过。");
            response.getWriter().println(echostr); //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
        } else {
            System.out.println("签名校验失败。");
        }
    }

    /**
     * 获取accessToken
     * @return
     * @throws IOException
     */
    public AccessToken getAccessToken() throws IOException {
        AccessToken accessToken = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        JSONObject jsonObject = GetPostUtil.doGetStr(url);
        if (jsonObject != null) {
            accessToken.setToken(jsonObject.getString("access_token"));
            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return accessToken;
    }

    /**
     * 获取公众号关注的用户openid
     * @return
     */
    public List<String> getUserOpenId(String access_token)
    {
        String path = OPENID_ID_URL.replace("ACCESS_TOKEN", access_token);
        List<String> result = null;
        try
        {
            JSON strResp = GetPostUtil.doGetStr(path);
            JSONObject jasonObject = JSONObject.fromObject(strResp);
            Map map = (Map)jasonObject;
            Map tmapMap = (Map) map.get("data");
            result = (List<String>) tmapMap.get("openid");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过用户openid 获取用户信息
     * @param userOpenids
     * @return
     */
    private List<UserInfo> getUserInfo(List<String> userOpenids) throws IOException {
        // 1、获取access_token
        // 使用测试 wx9015ccbcccf8d2f5 02e3a6877fa5fdeadd78d0f6f3048245
        AccessToken token = getAccessToken();
        String tAccess_Token = token.getToken();
        // 2、封装请求数据
        List user_list = new ArrayList<Map>();
        for (int i = 0; i < userOpenids.size(); i++)
        {
            String openid = userOpenids.get(i);
            Map tUserMap = new HashMap<String, String>();
            tUserMap.put("openid", openid);
            tUserMap.put("lang", "zh_CN");
            user_list.add(tUserMap);
        }
        System.out.println(user_list.toString());
        Map requestMap = new HashMap<String, List>();
        requestMap.put("user_list", user_list);
        String tUserJSON = JSONObject.fromObject(requestMap).toString();

        // 3、请求调用
        JSON result = getUserInfobyHttps(tAccess_Token, tUserJSON);

        JSONObject jasonObject = JSONObject.fromObject(result);
        String tMapData = jasonObject.toString();

        System.out.println(result);

        // 4、解析返回将结果
        return parseUserInfo(tMapData);
    }

    /**
     * 解析返回用户信息数据
     * @param userInfoJSON
     * @return
     */
    private List<UserInfo> parseUserInfo(String userInfoJSON)
    {
        List user_info_list = new ArrayList<UserInfo>();
        JSONObject jasonObject = JSONObject.fromObject(userInfoJSON);
        Map tMapData = (Map)jasonObject;
        List<Map> tUserMaps = (List<Map>) tMapData.get("user_info_list");

        for (int i = 0; i < tUserMaps.size(); i++)
        {
            UserInfo tUserInfo = new UserInfo();
            tUserInfo.setSubscribe((Integer) tUserMaps.get(i).get("subscribe"));
            tUserInfo.setSex((Integer) tUserMaps.get(i).get("sex"));
            tUserInfo.setOpenId((String) tUserMaps.get(i).get("openid"));
            tUserInfo.setNickname((String) tUserMaps.get(i).get("nickname"));
            tUserInfo.setLanguage((String) tUserMaps.get(i).get("language"));
            tUserInfo.setCity((String) tUserMaps.get(i).get("city"));
            tUserInfo.setProvince((String) tUserMaps.get(i).get("province"));
            tUserInfo.setCountry((String) tUserMaps.get(i).get("country"));
            tUserInfo.setHeadimgurl((String) tUserMaps.get(i).get("headimgurl"));
            tUserInfo.setSubscribetime((Integer) tUserMaps.get(i).get("subscribe_time"));
            tUserInfo.setRemark((String) tUserMaps.get(i).get("remark"));
            tUserInfo.setGroupid((Integer) tUserMaps.get(i).get("groupid"));
            user_info_list.add(tUserInfo);
        }
        return user_info_list;
    }

    /**
     * 调用HTTPS接口，获取用户详细信息
     * @param access_token
     * @param requestData
     * @return
     */
    private JSON getUserInfobyHttps(String access_token, String requestData) throws IOException {
        // 返回报文
        String path = USER_INFO.replace("ACCESS_TOKEN", access_token);
        JSON strResp = GetPostUtil.doPostStr(path, JSONObject.fromObject(requestData));
        return strResp;
    }
}
