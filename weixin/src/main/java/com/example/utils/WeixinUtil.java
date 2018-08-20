package com.example.utils;

import com.example.controller.AccessTokenController;
import com.example.domain.AccessToken;
import com.example.domain.msg.Msg;
import com.example.domain.menu.Button;
import com.example.domain.menu.ClickButton;
import com.example.domain.menu.Menu;
import com.example.domain.menu.ViewButton;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class WeixinUtil {

    // 创建菜单
    @Value("wx.createMenuUrl")
    public static String CREATE_MENU_URL;

    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();

        ClickButton button11 = new ClickButton();
        button11.setName("Click1");
        button11.setType("click");
        button11.setKey("11");

        ViewButton button21 = new ViewButton();
        button21.setName("View1");
        button21.setType("view");
        button21.setUrl("https://github.com/wangtao-Allen");

        ClickButton button31 = new ClickButton();
        button31.setName("扫码");
        button31.setType("scancode_push");
        button31.setKey("31");

        ClickButton button32 = new ClickButton();
        button32.setName("地理位置");
        button32.setType("location_select");
        button32.setKey("32");

        Button button3 = new Button();
        button3.setName("功能1");
        button3.setSub_button(new Button[]{button31,button32});

        menu.setButton(new Button[]{button11,button21,button3});

        return menu;
    }

    /**
     * 创建菜单
     * @param accessToken
     * @param menu
     * @return
     * @throws IOException
     */
    public static int createMenu(String accessToken,String menu) throws IOException {
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN",accessToken);
        JSONObject jsonObject = GetPostUtil.doPostStr(url, JSONObject.fromObject(menu));
        if (jsonObject != null) {
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    /**
     * 上传永久图片素材
     * @param fileurl
     * @param type
     * @param token
     * @return
     */
    public static JSONObject addMaterialEver(String fileurl, String type, String token) {
        try {
            File file = new File(fileurl);
            //上传素材
            String path = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=" + token + "&type=" + type;
            String result = connectHttpsByPost(path, null, file);
            result = result.replaceAll("[\\\\]", "");
            System.out.println("result:" + result);
            JSONObject resultJSON = JSONObject.fromObject(result);
            if (resultJSON != null) {
                if (resultJSON.get("media_id") != null) {
                    System.out.println("上传" + type + "永久素材成功");
                    return resultJSON;
                } else {
                    System.out.println("上传" + type + "永久素材失败");
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  String connectHttpsByPost(String path, String KK, File file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        URL urlObj = new URL(path);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        String result = null;
        con.setDoInput(true);

        con.setDoOutput(true);

        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary="
                        + BOUNDARY);

        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\""

                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        System.out.println("result---" + result);
        return result;

    }


    // 发送模板消息
    public static JSONObject sendMsg(Msg msg, String sendUrl) throws IOException {

        AccessTokenController accessTokenController = new AccessTokenController();
        AccessToken accessToken = accessTokenController.getAccessToken();
        sendUrl = sendUrl.replace("ACCESS_TOKEN", String.valueOf(accessToken.getToken()));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", msg.getOpenid()); // 接收方的openid
        jsonObject.put("template_id", msg.getTemplateId()); // 模板id
        jsonObject.put("url", msg.getUrl()); // 用户点击消息跳转的路径

        JSONObject firstObj = new JSONObject();
        firstObj.put("value",msg.getData().getFirst().getValue());
        firstObj.put("color",msg.getData().getFirst().getColor());

        JSONObject keyObj1 = new JSONObject();
        keyObj1.put("value",msg.getData().getKeyword1().getValue());
        keyObj1.put("color",msg.getData().getKeyword1().getColor());

        JSONObject keyObj2 = new JSONObject();
        keyObj2.put("value",msg.getData().getKeyword2().getValue());
        keyObj2.put("color",msg.getData().getKeyword2().getColor());

        JSONObject keyObj3 = new JSONObject();
        keyObj3.put("value",msg.getData().getKeyword3().getValue());
        keyObj3.put("color",msg.getData().getKeyword3().getColor());

        JSONObject keyObj4 = new JSONObject();
        keyObj4.put("value",msg.getData().getKeyword4().getValue());
        keyObj4.put("color",msg.getData().getKeyword4().getColor());

        JSONObject remarkObj = new JSONObject();
        remarkObj.put("value",msg.getData().getRemark().getValue());
        remarkObj.put("color",msg.getData().getRemark().getColor());

        JSONObject dataObj = new JSONObject();
        dataObj.put("first", firstObj);
        dataObj.put("keyword1", keyObj1);
        dataObj.put("keyword2", keyObj2);
        dataObj.put("keyword3", keyObj3);
        dataObj.put("keyword4", keyObj4);
        dataObj.put("remark", remarkObj);
        jsonObject.put("data", dataObj);
        JSONObject json = GetPostUtil.doPostStr(sendUrl, jsonObject);
        System.out.println(jsonObject.toString());
        return json;
    }

}
