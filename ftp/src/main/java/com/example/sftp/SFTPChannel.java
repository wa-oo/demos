package com.example.sftp;

import com.jcraft.jsch.*;

import java.util.Map;
import java.util.Properties;

/**
 * @author wangtaog@mail.taiji.com.cn
 * @date 2018/9/7
 */
public class SFTPChannel {
    Session session = null;
    Channel channel = null;

    public ChannelSftp getChannel(Map<String, String> sftpDetails) throws JSchException {

        String ftpHost = sftpDetails.get(SFTPConstants.SFTP_REQ_HOST);
        String port = sftpDetails.get(SFTPConstants.SFTP_REQ_PORT);
        String ftpUserName = sftpDetails.get(SFTPConstants.SFTP_REQ_USERNAME);
        String ftpPassword = sftpDetails.get(SFTPConstants.SFTP_REQ_PASSWORD);

        int ftpPort = SFTPConstants.SFTP_DEFAULT_PORT;
        if (port != null && !port.equals("")) {
            ftpPort = Integer.valueOf(port);
        }

        // 创建JSch对象
        JSch jsch = new JSch();
        // 根据用户名，主机ip，端口获取一个Session对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
        if (ftpPassword != null) {
            // 设置密码
            session.setPassword(ftpPassword);
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        // 为Session对象设置properties
        session.setConfig(config);
        // 设置timeout时间
//        session.setTimeout(timeout);
        // 通过Session建立链接
        session.connect();

        //打开SFTP通道
        channel = session.openChannel("sftp");
        // 建立SFTP通道的连接
        channel.connect();
        return (ChannelSftp) channel;
    }

    public void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}