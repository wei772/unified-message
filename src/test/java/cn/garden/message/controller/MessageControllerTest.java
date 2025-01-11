package cn.garden.message.controller;

import cn.garden.message.command.model.ReSendMessageRequest;
import cn.garden.message.command.model.ReSendMessageResponse;
import cn.garden.message.command.model.SendMessageRequest;
import cn.garden.message.command.model.SendMessageResponse;
import cn.garden.message.config.SenderConfig;
import cn.garden.message.domain.Message;
import cn.garden.message.repository.MessageParam;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.repository.factory.MessageRepositoryFactory;
import cn.garden.message.sender.MessageSenderChannelName;
import cn.garden.message.util.IdGenerator;
import cn.garden.message.util.RandomStringUtil;
import cn.garden.message.util.page.PageInfo;
import cn.garden.message.util.page.PageParam;
import cn.garden.message.util.page.PagedList;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageControllerTest {

    private final MessageController messageController = new MessageController();
    private final MessageRepository messageRepository = MessageRepositoryFactory.createDefault();

    @Test
    public void pageList() {
        PageParam<MessageParam> pageParam = new PageParam<>(
                new MessageParam()
                , new PageInfo(1, 30));
        PagedList<Message> messagePagedList = messageController.pageList(pageParam);

        assertTrue(CollectionUtils.isNotEmpty(messagePagedList.getDataList()));
        assertEquals(30, CollectionUtils.size(messagePagedList.getDataList()));
        assertTrue(messagePagedList.getPageInfo().getTotalCount() >= 100);
    }

    /**
     * 这种简单的转发测试好像没有太大的意义
     */
    @Test
    public void send() {
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setChannelName(MessageSenderChannelName.WECOM.getName());
        sendMessageRequest.setContent(RandomStringUtil.randomAlphanumeric(12));
        sendMessageRequest.setRecipients(List.of(SenderConfig.getWecomUsers()));

        SendMessageResponse sendMessageResponse = messageController.send(sendMessageRequest);

        assertTrue(sendMessageResponse.getSucceed());
    }


    @Test
    public void reSend() {
        Message message = new Message();
        message.setId(IdGenerator.generateRandom());
        message.setChannelName(MessageSenderChannelName.BLACKTEL.getName());
        message.setContent(RandomStringUtil.randomAlphanumeric(12));
        message.setRecipients(List.of(SenderConfig.getBlacktelPhone()));
        messageRepository.save(message);

        ReSendMessageRequest reSendMessageRequest = new ReSendMessageRequest();
        reSendMessageRequest.setMessageId(message.getId());

        ReSendMessageResponse reSendMessageResponse = messageController.reSend(reSendMessageRequest);

        assertTrue(reSendMessageResponse.getSucceed());
    }

}
