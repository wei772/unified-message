package cn.garden.message.sender.implementation.factory;

import cn.garden.message.sender.MessageSender;
import cn.garden.message.sender.MessageSenderChannelName;
import cn.garden.message.sender.implementation.email.EmailSender;
import cn.garden.message.sender.implementation.sms.BlacktelSmsSender;
import cn.garden.message.sender.implementation.sms.SmsSender;
import cn.garden.message.sender.implementation.wecom.WecomMessageSender;
import cn.garden.message.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liwei
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Service
public class MessageSenderFactory {

    private final List<MessageSender> senders;

    public MessageSenderFactory() {
        senders = new ArrayList<>();
        senders.add(new WecomMessageSender());
        senders.add(new SmsSender());
        senders.add(new BlacktelSmsSender());
        senders.add(new EmailSender());
    }

    /**
     * 如果@Scope使用prototype，MessageSender会有多个，用request才是最近
     */
    @Autowired
    public MessageSenderFactory(List<MessageSender> senders) {
        this.senders = senders;
    }

    public MessageSender create(String channelName) {
        MessageSenderChannelName messageSenderChannelName = MessageSenderChannelName.of(channelName);

        MessageSender messageSender = senders.stream()
                .filter(m -> Objects.equals(m.getChannelName(), messageSenderChannelName))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(messageSender)) {
            return messageSender;
        }

        throw ExceptionUtil.createDefaultException("找不到消息发送者");
    }

}
