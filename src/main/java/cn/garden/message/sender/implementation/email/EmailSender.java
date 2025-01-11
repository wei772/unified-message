package cn.garden.message.sender.implementation.email;

import cn.garden.message.client.email.EmailClient;
import cn.garden.message.client.email.model.EmailSendRequest;
import cn.garden.message.sender.MessageSender;
import cn.garden.message.sender.MessageSenderChannelName;
import cn.garden.message.sender.MessageSenderResponse;
import cn.garden.message.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import static cn.garden.message.sender.implementation.email.EmailConfigProperty.*;
import static cn.garden.message.sender.implementation.email.EmailProperty.SUBJECT;


/**
 * @author liwei
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Service
public class EmailSender extends MessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private final EmailClient emailClient;

    public EmailSender() {
        this.emailClient = new EmailClient();
    }

    @Autowired
    public EmailSender(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    @Override
    protected MessageSenderResponse send() {
        MessageSenderResponse messageSenderResponse = new MessageSenderResponse();

        EmailSendRequest emailSendRequest = new EmailSendRequest();
        emailSendRequest.setHost(channel.get(HOST.getValue()));
        emailSendRequest.setPort(channel.getIntValue(PORT.getValue()));
        emailSendRequest.setPassword(channel.get(PASSWORD.getValue()));
        emailSendRequest.setUsername(channel.get(USERNAME.getValue()));
        emailSendRequest.setReceivers(senderRequest.getRecipients());
        emailSendRequest.setContent(senderRequest.getContent());
        emailSendRequest.setSubject(senderRequest.getProperty(SUBJECT.getValue()));
        if (StringUtils.isEmpty(emailSendRequest.getSubject())) {
            throw ExceptionUtil.createDefaultException("邮件标题必填,请在properties添加email_subject进行设置");
        }

        try {
            emailClient.send(emailSendRequest);
        } catch (Exception ex) {
            LOGGER.warn("发送邮件失败", ex);
            messageSenderResponse.error(ex.getMessage());
        }

        return messageSenderResponse;
    }

    @Override
    public MessageSenderChannelName getChannelName() {
        return MessageSenderChannelName.EMAIL;
    }
}
