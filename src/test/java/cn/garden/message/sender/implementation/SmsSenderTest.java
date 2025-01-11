package cn.garden.message.sender.implementation;

import cn.garden.message.sender.MessageSenderChannel;
import cn.garden.message.sender.MessageSenderRequest;
import cn.garden.message.sender.MessageSenderResponse;
import cn.garden.message.sender.implementation.sms.SmsSender;
import cn.garden.message.util.RandomStringUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static cn.garden.message.config.SenderConfig.*;
import static cn.garden.message.sender.implementation.sms.blacktel.BlacktelConfigProperty.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author liwei
 */
public class SmsSenderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSenderTest.class);

    @Test
    public void send() {
        SmsSender smsSender = new SmsSender();
        MessageSenderChannel senderChannel = new MessageSenderChannel();

        senderChannel.add(HOST.getValue(), getBlacktelHost());
        senderChannel.add(USER_ID_KEY.getValue(), getBlacktelUseridKey());
        senderChannel.add(PASSWORD_KEY.getValue(), getBlacktelPasswordKey());
        senderChannel.add(EPR_ID_KEY.getValue(), getBlacktelEprIdKeySource());
        senderChannel.add(HOST.getValue(), getBlacktelHost());

        MessageSenderRequest senderRequest = new MessageSenderRequest();
        senderRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        senderRequest.setRecipients(Collections.singletonList(getBlacktelPhone()));

        MessageSenderResponse senderResponse = smsSender.send(senderChannel, senderRequest);
        assertTrue(senderResponse.succeed());
    }

    @Test
    public void sendNullPassword() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                    SmsSender smsSender = new SmsSender();
                    MessageSenderChannel senderChannel = new MessageSenderChannel();

                    senderChannel.add(HOST.getValue(), getBlacktelHost());
                    senderChannel.add(USER_ID_KEY.getValue(), getBlacktelUseridKey());
                    senderChannel.add(PASSWORD_KEY.getValue(), null);
                    senderChannel.add(EPR_ID_KEY.getValue(), getBlacktelEprIdKeySource());
                    senderChannel.add(HOST.getValue(), getBlacktelHost());

                    MessageSenderRequest senderRequest = new MessageSenderRequest();
                    senderRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
                    senderRequest.setRecipients(Collections.singletonList(getBlacktelPhone()));

                    smsSender.send(senderChannel, senderRequest);
                }
        );
        LOGGER.warn("sendNullPassword", runtimeException);

    }
}
