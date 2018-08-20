package com.example.controller;

import com.example.domain.AccessToken;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static com.example.utils.WeixinUtil.doGetStr;

@Controller
public class AccessTokenController {

    @Value("wx.appid")
    private final String APPID;

    @Value("wx.appsecret")
    private final String APPSECRET;

    /**
     * 获取accessToken
     * @return
     * @throws IOException
     */
    public static AccessToken getAccessToken() throws IOException {
        AccessToken accessToken = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        JSONObject jsonObject = doGetStr(url);
        if (jsonObject != null) {
            accessToken.setToken(jsonObject.getString("access_token"));
            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return accessToken;
    }
}
