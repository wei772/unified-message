package cn.garden.message.sender.implementation;

import cn.garden.message.config.SenderConfig;
import cn.garden.message.sender.MessageSender;
import cn.garden.message.sender.MessageSenderChannel;
import cn.garden.message.sender.MessageSenderRequest;
import cn.garden.message.sender.MessageSenderResponse;
import cn.garden.message.sender.implementation.factory.MessageSenderFactory;
import cn.garden.message.util.RandomStringUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static cn.garden.message.config.SenderConfig.*;
import static cn.garden.message.sender.implementation.wecom.WecomConfigProperty.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author liwei
 */
public class MessageSenderFactoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSenderFactoryTest.class);

    @Test
    public void sendWecomMessage() {
        MessageSender wecomMessageSender = new MessageSenderFactory().create("wecom");
        MessageSenderChannel senderChannel = new MessageSenderChannel();
        senderChannel.add(CORP_ID.getValue(), getWecomCorpId());
        senderChannel.add(CORP_SECRET.getValue(), getWecomCorpSecret());
        senderChannel.add(AGENT_ID.getValue(), getWecomAgentId());

        MessageSenderRequest senderRequest = new MessageSenderRequest();
        senderRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        senderRequest.setRecipients(Collections.singletonList(getWecomUsers()));

        MessageSenderResponse senderResponse = wecomMessageSender.send(senderChannel, senderRequest);

        assertTrue(senderResponse.succeed());
    }

    @Test
    public void sendWecomMessageByChannel() {
        MessageSenderChannel messageSenderChannel = new MessageSenderChannel("wecom");
        messageSenderChannel.addAll(SenderConfig.getPropertiesMap());

        MessageSender wecomMessageSender = new MessageSenderFactory().create(messageSenderChannel.getName());
        MessageSenderChannel senderChannel = new MessageSenderChannel();
        senderChannel.addAll(messageSenderChannel.getAll());

        MessageSenderRequest senderRequest = new MessageSenderRequest();
        senderRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        senderRequest.setRecipients(Collections.singletonList(getWecomUsers()));

        MessageSenderResponse senderResponse = wecomMessageSender.send(senderChannel, senderRequest);

        assertTrue(senderResponse.succeed());
    }

    @Test
    public void sendWecomMessageNullSecret() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            MessageSender wecomMessageSender = new MessageSenderFactory().create("wecom");
            MessageSenderChannel senderChannel = new MessageSenderChannel();
            senderChannel.add(CORP_ID.getValue(), getWecomCorpId());
            senderChannel.add(CORP_SECRET.getValue(), null);
            senderChannel.add(AGENT_ID.getValue(), getWecomAgentId());

            MessageSenderRequest senderRequest = new MessageSenderRequest();
            senderRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
            senderRequest.setRecipients(Collections.singletonList(getWecomUsers()));
            wecomMessageSender.send(senderChannel, senderRequest);
        });
        LOGGER.warn("sendNullSecret", runtimeException);
    }
}
