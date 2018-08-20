package com.example.controller;

import com.example.domain.AccessToken;
import com.example.utils.GetPostUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class AccessTokenController {

    @Value("${wx.appid}")
    private String APPID;

    @Value("wx.appsecret")
    private String APPSECRET;

    @Value("wx.accessTokenUrl")
    private String ACCESS_TOKEN_URL;

    @Value("wx.openIdUrl")
    private String OPENID_ID_URL;

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
}
