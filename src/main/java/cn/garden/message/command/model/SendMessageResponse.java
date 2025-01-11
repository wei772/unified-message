package cn.garden.message.command.model;

import cn.garden.message.domain.Message;
import cn.garden.message.util.ResponseBase;

import java.util.Optional;

/**
 * @author liwei
 */
public class SendMessageResponse extends ResponseBase {

    private Message message;

    public String getMessageId() {
        return Optional.ofNullable(message).map(Message::getId).orElse(null);
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
