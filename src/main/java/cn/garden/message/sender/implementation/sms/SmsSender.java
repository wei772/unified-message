package cn.garden.message.sender.implementation.sms;

import cn.garden.message.sender.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 发送短信
 *
 * @author liwei
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Service
public class SmsSender extends MessageSender implements QueryMessageStatus {

    private final List<MessageSender> smsSenders;

    private MessageSender currentSmsSender;

    public SmsSender() {
        smsSenders = List.of(new BlacktelSmsSender());
    }

    @Autowired
    public SmsSender(List<MessageSender> smsSenders) {
        if (CollectionUtils.isNotEmpty(smsSenders)) {
            this.smsSenders = smsSenders
                    .stream()
                    .filter(m -> Objects.equals(m.getParentChannelName(), getChannelName()))
                    .collect(Collectors.toList());
        } else {
            this.smsSenders = List.of(new BlacktelSmsSender());
        }
    }

    @Override
    protected MessageSenderResponse send() {
        MessageSenderResponse messageSenderResponse = new MessageSenderResponse();
        for (MessageSender smsSender : smsSenders) {
            messageSenderResponse = smsSender.send(channel, senderRequest);
            if (messageSenderResponse.succeed()) {
                currentSmsSender = smsSender;
                return messageSenderResponse;
            }
        }
        return messageSenderResponse;

    }

    @Override
    public MessageSenderChannelName getChannelName() {
        return MessageSenderChannelName.SMS;
    }

    @Override
    public List<SenderDetail> queryStatus() {
        if (Objects.nonNull(currentSmsSender)) {
            if (currentSmsSender instanceof QueryMessageStatus queryMessageStatus) {
                return queryMessageStatus.queryStatus();
            }
        }
        return new ArrayList<>();
    }
}
