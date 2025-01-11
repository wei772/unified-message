package cn.garden.message.controller;

import cn.garden.message.command.ReSendMessageCommand;
import cn.garden.message.command.SendMessageCommand;
import cn.garden.message.command.model.ReSendMessageRequest;
import cn.garden.message.command.model.ReSendMessageResponse;
import cn.garden.message.command.model.SendMessageRequest;
import cn.garden.message.command.model.SendMessageResponse;
import cn.garden.message.domain.Message;
import cn.garden.message.repository.MessageParam;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.repository.factory.MessageRepositoryFactory;
import cn.garden.message.util.page.PageParam;
import cn.garden.message.util.page.PagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final SendMessageCommand sendMessageCommand;

    private final ReSendMessageCommand reSendMessageCommand;

    private final MessageRepository messageRepository;

    public MessageController() {
        this.sendMessageCommand = new SendMessageCommand();
        this.messageRepository = MessageRepositoryFactory.createDefault();
        this.reSendMessageCommand = new ReSendMessageCommand();
    }

    @Autowired
    public MessageController(
            SendMessageCommand sendMessageCommand,
            MessageRepository messageRepository, ReSendMessageCommand reSendMessageCommand) {
        this.sendMessageCommand = sendMessageCommand;
        this.messageRepository = messageRepository;
        this.reSendMessageCommand = reSendMessageCommand;
    }

    @PostMapping("/pageList")
    public PagedList<Message> pageList(@RequestBody PageParam<MessageParam> pageParam) {
        return messageRepository.getPageList(pageParam.getParam(), pageParam.getPageInfo());

    }

    @PostMapping("/send")
    public SendMessageResponse send(@RequestBody SendMessageRequest sendMessageRequest) {
        return sendMessageCommand.execute(sendMessageRequest);
    }


    @PostMapping("/reSend")
    public ReSendMessageResponse reSend(@RequestBody ReSendMessageRequest reSendMessageRequest) {
        return reSendMessageCommand.execute(reSendMessageRequest);
    }

}
