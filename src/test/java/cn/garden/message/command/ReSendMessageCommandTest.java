package cn.garden.message.command;

import cn.garden.message.command.model.ReSendMessageRequest;
import cn.garden.message.command.model.ReSendMessageResponse;
import cn.garden.message.config.SenderConfig;
import cn.garden.message.domain.Message;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.repository.factory.MessageRepositoryFactory;
import cn.garden.message.sender.MessageSenderChannelName;
import cn.garden.message.util.IdGenerator;
import cn.garden.message.util.RandomStringUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author liwei
 */
public class ReSendMessageCommandTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReSendMessageCommandTest.class);

    private final MessageRepository messageRepository = MessageRepositoryFactory.createDefault();

    @Test
    public void reSend() {
        Message message = new Message();
        message.setId(IdGenerator.generateRandom());
        message.setChannelName(MessageSenderChannelName.BLACKTEL.getName());
        message.setContent(RandomStringUtil.randomAlphanumeric(12));
        message.setRecipients(List.of(SenderConfig.getBlacktelPhone()));
        messageRepository.save(message);

        ReSendMessageCommand reSendMessageCommand = new ReSendMessageCommand();
        ReSendMessageResponse reSendMessageResponse = reSendMessageCommand.execute(
                new ReSendMessageRequest(message.getId()));

        assertTrue(reSendMessageResponse.getSucceed());
    }

}
