package cn.garden.message.client.email;

import cn.garden.message.client.email.model.EmailSendRequest;
import cn.garden.message.util.ExceptionUtil;
import cn.garden.message.util.JsonUtil;
import jakarta.mail.*;
import jakarta.mail.event.TransportEvent;
import jakarta.mail.event.TransportListener;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * 发视邮件
 *
 * @author liwei
 */
@Service
public class EmailClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailClient.class);

    /**
     * 发送邮件
     */
    public void send(EmailSendRequest request) {
        if (Objects.isNull(request)) {
            throw ExceptionUtil.createDefaultException("发送邮件请求不能为空");
        }
        request.validate();

        // smtp配置，可保存到properties文件，读取
        Properties props = new Properties();
        props.put("mail.smtp.host", request.getHost());
        props.put("mail.smtp.port", request.getPort());
        props.put("mail.smtp.ssl", false);

        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.user", request.getUsername());
        props.put("mail.smtp.pass", request.getPassword());

        // 创建会话
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 需要认证
                PasswordAuthentication auth = new PasswordAuthentication(
                        request.getUsername(),
                        request.getPassword());
                return auth;
            }
        });


        try {
            // 构建邮件消息
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(request.getUsername()));
            List<InternetAddress> addresses = request
                    .getReceivers()
                    .stream()
                    .map(m -> {
                        try {
                            return new InternetAddress(m);
                        } catch (AddressException e) {
                            throw ExceptionUtil.createDefaultException("接收者", e);
                        }
                    })
                    .toList();
            msg.setRecipients(Message.RecipientType.TO, addresses.toArray(new Address[0]));
            msg.setSubject(request.getSubject());
            msg.setText(request.getContent());
            msg.setSentDate(new Date());

            // 发送邮件
            Transport transport = session.getTransport();
            transport.addTransportListener(new TransportListener() {
                @Override
                public void messagePartiallyDelivered(TransportEvent e) {
                    LOGGER.info("消息部分投递");
                }

                @Override
                public void messageNotDelivered(TransportEvent e) {
                    LOGGER.info("消息未投递");
                }

                @Override
                public void messageDelivered(TransportEvent e) {
                    //163邮箱全是回调这个，但是登录后可以看到发送不成功
                    LOGGER.info("消息已投递{}", JsonUtil.toJson(e.getValidSentAddresses()));
                }
            });

            transport.connect();
            transport.sendMessage(msg, addresses.toArray(new Address[0]));

        } catch (
                MessagingException e) {
            throw ExceptionUtil.createDefaultException("发送邮件异常", e);
        }
    }


}
