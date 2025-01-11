package cn.garden.message.command;

import cn.garden.message.client.user.UserClient;
import cn.garden.message.command.model.SendMessageRequest;
import cn.garden.message.command.model.SendMessageResponse;
import cn.garden.message.domain.Message;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.sender.implementation.factory.MessageSenderFactory;
import cn.garden.message.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

/**
 * 发现消息命令
 *
 * @author liwei
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Service
public class SendMessageCommand extends SendMessageCommandBase implements Command {

    private SendMessageRequest request;

    private SendMessageResponse response;


    public SendMessageCommand() {
        super();
    }

    @Autowired
    public SendMessageCommand(MessageRepository messageRepository
            , UserClient userClient
            , MessageSenderFactory messageSenderFactory) {
        super(messageRepository, userClient, messageSenderFactory);

    }

    public void setRequest(SendMessageRequest request) {
        this.request = request;
    }

    public SendMessageResponse getResponse() {
        return response;
    }

    public SendMessageResponse execute(SendMessageRequest sendMessageRequest) {
        request = sendMessageRequest;
        execute();
        return response;
    }

    @Override
    public void execute() {
        if (Objects.isNull(request)) {
            throw ExceptionUtil.createDefaultException("request不能为空");
        }
        response = new SendMessageResponse();
        Message message = request.toMessage();
        send(message);
        response.setMessage(message);
        if (!message.succeed()) {
            response.error(message.getStatusMessage());
        } else {
            response.setSucceed(message.succeed());
        }
    }
}
