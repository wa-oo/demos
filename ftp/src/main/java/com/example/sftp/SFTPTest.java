package com.example.sftp;

import com.jcraft.jsch.ChannelSftp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangtaog@mail.taiji.com.cn
 * @date 2018/9/7
 */
public class SFTPTest {

    public SFTPChannel getSFTPChannel() {
        return new SFTPChannel();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SFTPTest test = new SFTPTest();

        Map<String, String> sftpDetails = new HashMap<String, String>();
        // 设置主机ip，端口，用户名，密码
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "10.0.18.111");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "test");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "12345678");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");

        // 本地文件名
        String src = "D:\\权威指南.pdf";
        // 目标文件名
        String dst = "/home/test/user/attachment/权威指南2.pdf";

        SFTPChannel channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails);

        // 代码段2
        chSftp.put(src, dst, ChannelSftp.OVERWRITE);

        chSftp.quit();
        channel.closeChannel();
    }
}
