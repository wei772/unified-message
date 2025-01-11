package cn.garden.message.command;

import cn.garden.message.client.user.UserClient;
import cn.garden.message.command.model.ReSendMessageRequest;
import cn.garden.message.command.model.ReSendMessageResponse;
import cn.garden.message.domain.Message;
import cn.garden.message.domain.MessageStatus;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.sender.implementation.factory.MessageSenderFactory;
import cn.garden.message.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

/**
 * 消息重发命令
 * 有字段就不是无状态了，不能单实例
 *
 * @author liwei
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Service
public class ReSendMessageCommand extends SendMessageCommandBase implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReSendMessageCommand.class);
    private final ReSendMessageResponse response = new ReSendMessageResponse();
    private String messageId;

    public ReSendMessageCommand() {
        super();
    }

    @Autowired
    public ReSendMessageCommand(MessageRepository messageRepository
            , UserClient userClient
            , MessageSenderFactory messageSenderFactory) {
        super(messageRepository, userClient, messageSenderFactory);
    }


    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public ReSendMessageResponse getResponse() {
        return response;
    }

    public ReSendMessageResponse execute(ReSendMessageRequest request) {
        messageId = request.getMessageId();
        execute();
        return getResponse();
    }

    @Override
    public void execute() {
        if (StringUtils.isEmpty(messageId)) {
            throw ExceptionUtil.createDefaultException("消息Id不能为空");
        }
        Message message = messageRepository.getById(messageId);
        if (Objects.isNull(message)) {
            throw ExceptionUtil.createDefaultException("找不到消息" + messageId);
        }
        if (MessageStatus.DONE.getName().equals(message.getStatus())) {
            LOGGER.info("成功发送的消息不支持重发{}", messageId);
            response.error("成功发送的消息不支持重发");
            return;
        }
        send(message);
        if (!message.succeed()) {
            response.error(message.getStatusMessage());
        } else {
            response.setSucceed(message.succeed());
        }

    }
}
