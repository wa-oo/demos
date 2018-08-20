package com.example;

import com.example.controller.AccessTokenController;
import com.example.domain.AccessToken;
import com.example.domain.msg.*;
import com.example.utils.GetPropertiesUtil;
import com.example.utils.WeixinUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinApplicationTests {

    Properties properties = GetPropertiesUtil.getProperties();
    private String templateId = properties.getProperty("templateId");
    private String sendUrl = properties.getProperty("sendUrl");
    private String url = properties.getProperty("url");

    @Test
    public void send() throws IOException {
        AccessTokenController accessTokenController = new AccessTokenController();
        AccessToken accessToken = accessTokenController.getAccessToken();
        List<String> openIdList = accessTokenController.getUserOpenId(accessToken.getToken());
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        for (int i = 0; i < openIdList.size(); i++) {
            Msg msg = new Msg();
            msg.setOpenid(openIdList.get(i));
            msg.setUrl(url);
            msg.setTemplateId(templateId);

            First first = new First();
            first.setValue("模板消息测试\n");
            first.setColor("#FF83FA");

            Keyword1 keyword1 = new Keyword1();
            keyword1.setValue("政一研发部\n");
            keyword1.setColor("#FF83FA");

            Keyword2 keyword2 = new Keyword2();
            keyword2.setValue("混沌级\n");
            keyword2.setColor("#FF83FA");

            Keyword3 keyword3 = new Keyword3();
            keyword3.setValue("测试哇\n");
            keyword3.setColor("#FF83FA");

            Keyword4 keyword4 = new Keyword4();
            keyword4.setValue(df.format(date)+"\n");
            keyword4.setColor("#FF83FA");

            Remark remark = new Remark();
            remark.setValue("123456789");
            remark.setColor("#FF83FA");

            Data data = new Data();
            data.setFirst(first);
            data.setKeyword1(keyword1);
            data.setKeyword2(keyword2);
            data.setKeyword3(keyword3);
            data.setKeyword4(keyword4);
            data.setRemark(remark);
            msg.setData(data);
            WeixinUtil.sendMsg(msg, sendUrl);
        }
    }

}
