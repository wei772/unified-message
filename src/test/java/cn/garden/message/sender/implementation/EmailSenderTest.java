package cn.garden.message.sender.implementation;

import cn.garden.message.sender.MessageSenderChannel;
import cn.garden.message.sender.MessageSenderRequest;
import cn.garden.message.sender.MessageSenderResponse;
import cn.garden.message.sender.implementation.email.EmailSender;
import cn.garden.message.util.RandomStringUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static cn.garden.message.config.SenderConfig.*;
import static cn.garden.message.sender.implementation.email.EmailConfigProperty.*;
import static cn.garden.message.sender.implementation.email.EmailProperty.SUBJECT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author liwei
 */
public class EmailSenderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderTest.class);

    @Test
    @Disabled
    public void send() {
        EmailSender emailMessageSender = new EmailSender();
        MessageSenderChannel senderChannel = new MessageSenderChannel();
        senderChannel.add(HOST.getValue(), getEmailHost());
        senderChannel.add(USERNAME.getValue(), getEmailUsername());
        senderChannel.add(PASSWORD.getValue(), getEmailPassword());
        senderChannel.add(PORT.getValue(), getEmailPort());

        MessageSenderRequest senderRequest = new MessageSenderRequest();
        senderRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        senderRequest.setRecipients(Collections.singletonList(getEmailReceiver()));
        senderRequest.addProperty(SUBJECT.getValue(), "测试邮件");

        MessageSenderResponse senderResponse = emailMessageSender.send(senderChannel, senderRequest);
        assertTrue(senderResponse.succeed());
    }

    @Test
    public void sendNullContent() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            EmailSender emailMessageSender = new EmailSender();
            MessageSenderChannel senderChannel = new MessageSenderChannel();
            senderChannel.add(HOST.getValue(), getEmailHost());
            senderChannel.add(USERNAME.getValue(), getEmailUsername());
            senderChannel.add(PASSWORD.getValue(), getEmailPassword());
            senderChannel.add(PORT.getValue(), getEmailPort());

            MessageSenderRequest senderRequest = new MessageSenderRequest();
            senderRequest.setContent(null);
            senderRequest.setRecipients(Collections.singletonList(getEmailReceiver()));
            senderRequest.addProperty(SUBJECT.getValue(), "测试邮件");
            emailMessageSender.send(senderChannel, senderRequest);

        });
        LOGGER.warn("sendNullPassword", runtimeException);
    }

    @Test
    public void sendNullSubject() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            EmailSender emailMessageSender = new EmailSender();
            MessageSenderChannel senderChannel = new MessageSenderChannel();
            senderChannel.add(HOST.getValue(), getEmailHost());
            senderChannel.add(USERNAME.getValue(), getEmailUsername());
            senderChannel.add(PASSWORD.getValue(), getEmailPassword());
            senderChannel.add(PORT.getValue(), getEmailPort());

            MessageSenderRequest senderRequest = new MessageSenderRequest();
            senderRequest.setContent("测试");
            senderRequest.setRecipients(Collections.singletonList(getEmailReceiver()));
            senderRequest.addProperty(SUBJECT.getValue(), null);
            emailMessageSender.send(senderChannel, senderRequest);

        });
        LOGGER.warn("sendNullSubject", runtimeException);
    }
}
