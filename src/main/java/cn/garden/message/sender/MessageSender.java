package cn.garden.message.sender;

import cn.garden.message.util.ExceptionUtil;

import java.util.Objects;

/**
 * 消息发送者
 *
 * @author liwei
 */
public abstract class MessageSender {

    protected MessageSenderChannel channel;

    protected MessageSenderRequest senderRequest;

    protected MessageSenderResponse messageSenderResponse = new MessageSenderResponse();

    public MessageSenderResponse send(MessageSenderChannel channel, MessageSenderRequest senderRequest) {
        if (Objects.isNull(channel)) {
            throw ExceptionUtil.createDefaultException("senderChannel不能为空");
        }
        if (Objects.isNull(senderRequest)) {
            throw ExceptionUtil.createDefaultException("senderRequest不能为空");
        }
        senderRequest.validate();

        this.channel = channel;
        this.senderRequest = senderRequest;
        this.messageSenderResponse = send();
        return messageSenderResponse;
    }

    protected abstract MessageSenderResponse send();

    public abstract MessageSenderChannelName getChannelName();

    public MessageSenderChannelName getParentChannelName() {
        return null;
    }
}
