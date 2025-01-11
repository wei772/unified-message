package cn.garden.message.client.email;

import cn.garden.message.client.email.model.EmailSendRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

import static cn.garden.message.config.SenderConfig.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author liwei
 */
public class EmailClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailClientTest.class);

    /**
     * 涉及到外部资源，默认禁用，可以手工点击运行该单元测试
     * 异常1：
     * java.lang.IllegalArgumentException: Subject must not be null
     * 分析 title不能为空
     * <p>
     * 异常2：
     * javamail java.lang.IllegalStateException: No provider of jakarta.mail.util.StreamProvider
     * 解决引入org.eclipse.angus
     * <p>
     * 异常3：
     * jakarta.mail.MessagingException: Got bad greeting from SMTP host: smtp.163.com, port: 587, response: [EOF]
     * 解决一般都是 25端口
     */
    @Test
    @Disabled
    public void send() {
        EmailClient emailClient = new EmailClient();
        EmailSendRequest emailSendRequest = new EmailSendRequest();
        emailSendRequest.setHost(getEmailHost());
        emailSendRequest.setPort(25);
        emailSendRequest.setPassword(getEmailPassword());
        emailSendRequest.setUsername(getEmailUsername());
        emailSendRequest.setReceivers(Collections.singletonList(getEmailReceiver()));
        emailSendRequest.setContent("测试");
        emailSendRequest.setSubject("测试邮件");

        emailClient.send(emailSendRequest);
    }

    /**
     * 全是validSent
     */
    @Test
    @Disabled
    public void sendTwoEmail() {
        EmailClient emailClient = new EmailClient();
        EmailSendRequest emailSendRequest = new EmailSendRequest();
        emailSendRequest.setHost(getEmailHost());
        emailSendRequest.setPort(25);
        emailSendRequest.setPassword(getEmailPassword());
        emailSendRequest.setUsername(getEmailUsername());
        emailSendRequest.setReceivers(List.of(getEmailReceiver(), "77200000@qq.com"));
        emailSendRequest.setContent("测试");
        emailSendRequest.setSubject("测试邮件");

        emailClient.send(emailSendRequest);
    }

    /**
     * 全是validSent
     */
    @Test
    @Disabled
    public void sendErrorEmail() {
        EmailClient emailClient = new EmailClient();
        EmailSendRequest emailSendRequest = new EmailSendRequest();
        emailSendRequest.setHost(getEmailHost());
        emailSendRequest.setPort(25);
        emailSendRequest.setPassword(getEmailPassword());
        emailSendRequest.setUsername(getEmailUsername());
        emailSendRequest.setReceivers(List.of("ewewe@qq1tt.com", "77200000@qq1.com"));
        emailSendRequest.setContent("测试");
        emailSendRequest.setSubject("测试邮件");

        emailClient.send(emailSendRequest);
    }

    @Test
    public void sendNullPassword() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            EmailClient emailClient = new EmailClient();
            EmailSendRequest emailSendRequest = new EmailSendRequest();
            emailSendRequest.setHost(getEmailHost());
            emailSendRequest.setPort(25);
            emailSendRequest.setPassword(null);
            emailSendRequest.setUsername(getEmailUsername());
            emailSendRequest.setReceivers(Collections.singletonList(getEmailReceiver()));
            emailSendRequest.setContent("测试");
            emailSendRequest.setSubject("测试邮件");

            emailClient.send(emailSendRequest);
        });
        LOGGER.warn("sendNullPassword", runtimeException);
    }
}
