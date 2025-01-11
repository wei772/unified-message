package cn.garden.message.command;

import cn.garden.message.command.model.SendMessageRequest;
import cn.garden.message.command.model.SendMessageResponse;
import cn.garden.message.domain.Message;
import cn.garden.message.domain.MessageDetail;
import cn.garden.message.domain.MessageStatus;
import cn.garden.message.domain.RecipientType;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.repository.factory.MessageRepositoryFactory;
import cn.garden.message.sender.MessageSenderChannelName;
import cn.garden.message.util.RandomStringUtil;
import cn.garden.message.util.TestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static cn.garden.message.config.SenderConfig.getBlacktelPhone;
import static cn.garden.message.config.SenderConfig.getWecomUsers;
import static cn.garden.message.sender.implementation.email.EmailProperty.SUBJECT;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class SendMessageCommandTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageCommandTest.class);

    MessageRepository messageRepository = MessageRepositoryFactory.createDefault();

    @Test
    public void sendWecom() {
        SendMessageCommand sendMessageCommand = new SendMessageCommand();

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setChannelName(MessageSenderChannelName.WECOM.getName());
        sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        String testUser = TestUtil.generateTestString();
        sendMessageRequest.setRecipients(List.of(getWecomUsers(), testUser));

        SendMessageResponse sendMessageResponse = sendMessageCommand.execute(sendMessageRequest);
        Message message = messageRepository.getById(sendMessageResponse.getMessageId());
        MessageDetail weComDetail = message.getDetail(getWecomUsers());
        MessageDetail testUserDetail = message.getDetail(testUser);

        assertTrue(sendMessageResponse.getSucceed());
        assertNotNull(message);
        assertNotNull(weComDetail);
        assertEquals(MessageStatus.DONE.getName(), weComDetail.getStatus());
        assertNotNull(testUserDetail);
        assertEquals(MessageStatus.FAIL.getName(), testUserDetail.getStatus());
    }


    @Test
    public void sendBlacktelSms() {
        SendMessageCommand sendMessageCommand = new SendMessageCommand();

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setChannelName(MessageSenderChannelName.BLACKTEL.getName());
        sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        String testPhone = "test12345678912";
        sendMessageRequest.setRecipients(List.of(getBlacktelPhone(), testPhone));

        SendMessageResponse sendMessageResponse = sendMessageCommand.execute(sendMessageRequest);

        Message message = messageRepository.getById(sendMessageResponse.getMessageId());
        MessageDetail succeedDetail = message.getDetail(getBlacktelPhone());
        MessageDetail failDetail = message.getDetail(testPhone);

        assertTrue(sendMessageResponse.getSucceed());
        assertTrue(CollectionUtils.isNotEmpty(sendMessageResponse.getMessage().getDetails()));
        assertNotNull(message);
        assertNotNull(succeedDetail);
        assertEquals(MessageStatus.DONE.getName(), succeedDetail.getStatus());
        assertNotNull(failDetail);
        assertEquals(MessageStatus.FAIL.getName(), failDetail.getStatus());
    }

    @Test
    public void sendSms() {
        SendMessageCommand sendMessageCommand = new SendMessageCommand();

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setChannelName(MessageSenderChannelName.SMS.getName());
        sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        String testPhone = "test12345678912";
        sendMessageRequest.setRecipients(List.of(getBlacktelPhone(), testPhone));

        SendMessageResponse sendMessageResponse = sendMessageCommand.execute(sendMessageRequest);

        Message message = messageRepository.getById(sendMessageResponse.getMessageId());
        MessageDetail succeedDetail = message.getDetail(getBlacktelPhone());
        MessageDetail failDetail = message.getDetail(testPhone);

        assertTrue(sendMessageResponse.getSucceed());
        assertTrue(CollectionUtils.isNotEmpty(sendMessageResponse.getMessage().getDetails()));
        assertNotNull(message);
        assertNotNull(succeedDetail);
        assertEquals(MessageStatus.DONE.getName(), succeedDetail.getStatus());
        assertNotNull(failDetail);
        assertEquals(MessageStatus.FAIL.getName(), failDetail.getStatus());
    }


    @Test
    public void sendWecomByUser() {
        SendMessageCommand sendMessageCommand = new SendMessageCommand();

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setChannelName(MessageSenderChannelName.WECOM.getName());
        sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        sendMessageRequest.setRecipientType(RecipientType.USER.getName());
        sendMessageRequest.setRecipients(List.of(getWecomUsers()));

        SendMessageResponse sendMessageResponse = sendMessageCommand.execute(sendMessageRequest);

        Message message = messageRepository.getById(sendMessageResponse.getMessageId());

        assertTrue(sendMessageResponse.getSucceed());
        assertNotNull(message);
    }

    @Test
    public void sendSmsByUser() {
        SendMessageCommand sendMessageCommand = new SendMessageCommand();

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setChannelName(MessageSenderChannelName.SMS.getName());
        sendMessageRequest.setRecipientType(RecipientType.USER.getName());
        sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        sendMessageRequest.setRecipients(List.of(getWecomUsers()));

        SendMessageResponse sendMessageResponse = sendMessageCommand.execute(sendMessageRequest);
        Message message = messageRepository.getById(sendMessageResponse.getMessageId());

        assertTrue(sendMessageResponse.getSucceed());
        assertNotNull(message);
    }

    @Test
    public void sendNullRequest() {
        assertThrows(RuntimeException.class, () -> {
            SendMessageCommand sendMessageCommand = new SendMessageCommand();
            sendMessageCommand.execute(null);
        });
    }

    @Test
    public void sendNullAccounts() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            SendMessageCommand sendMessageCommand = new SendMessageCommand();
            SendMessageRequest sendMessageRequest = new SendMessageRequest();
            sendMessageRequest.setChannelName(MessageSenderChannelName.WECOM.getName());
            sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
            sendMessageRequest.setRecipients(null);

            sendMessageCommand.execute(sendMessageRequest);
        });

        LOGGER.warn("sendNullAccounts", runtimeException);
    }

    @Test
    public void sendWecomFail() {
        SendMessageCommand sendMessageCommand = new SendMessageCommand();
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setChannelName(MessageSenderChannelName.WECOM.getName());
        sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        sendMessageRequest.setRecipients(List.of(TestUtil.generateTestString()));

        SendMessageResponse messageResponse = sendMessageCommand.execute(sendMessageRequest);

        assertEquals(MessageStatus.FAIL.getName(), messageResponse.getMessage().getStatus());
        assertNotNull(messageResponse.getMessage().getStatusMessage());
    }


    @Test
    public void sendSmsFail() {
        SendMessageCommand sendMessageCommand = new SendMessageCommand();
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setChannelName(MessageSenderChannelName.SMS.getName());
        sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        sendMessageRequest.setRecipients(List.of(TestUtil.generateTestString()));

        SendMessageResponse messageResponse = sendMessageCommand.execute(sendMessageRequest);

        assertEquals(MessageStatus.FAIL.getName(), messageResponse.getMessage().getStatus());
        assertNotNull(messageResponse.getMessage().getStatusMessage());
    }

    @Test
    public void sendEmailFail() {
        SendMessageCommand sendMessageCommand = new SendMessageCommand();
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setChannelName(MessageSenderChannelName.EMAIL.getName());
        sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        sendMessageRequest.setRecipients(List.of(TestUtil.generateTestString()));
        sendMessageRequest.addProperty(SUBJECT.getValue(), "测试邮件");

        SendMessageResponse messageResponse = sendMessageCommand.execute(sendMessageRequest);

        assertEquals(MessageStatus.FAIL.getName(), messageResponse.getMessage().getStatus());
        assertNotNull(messageResponse.getMessage().getStatusMessage());
    }
}
