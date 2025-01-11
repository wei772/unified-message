package cn.garden.message.sender.implementation.sms;

import cn.garden.message.client.sms.blacktel.BlacktelSmsClient;
import cn.garden.message.client.sms.blacktel.model.*;
import cn.garden.message.sender.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Objects;

import static cn.garden.message.sender.implementation.sms.blacktel.BlacktelConfigProperty.*;

/**
 * 通过Blacktel平台发送短信
 *
 * @author liwei
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Service
public class BlacktelSmsSender extends MessageSender implements QueryMessageStatus {

    private final BlacktelSmsClient blacktelSmsClient;

    public BlacktelSmsSender() {
        blacktelSmsClient = new BlacktelSmsClient();
    }

    @Autowired
    public BlacktelSmsSender(BlacktelSmsClient blacktelSmsClient) {
        this.blacktelSmsClient = blacktelSmsClient;
    }


    @Override
    protected MessageSenderResponse send() {

        MessageSenderResponse messageSenderResponse = new MessageSenderResponse();

        BlacktelSmsSendRequest request = new BlacktelSmsSendRequest();
        request.setEprId(channel.getIntValue(EPR_ID_KEY.getValue()));
        request.setUserId(channel.get(USER_ID_KEY.getValue()));
        request.setPassword(channel.get(PASSWORD_KEY.getValue()));
        request.setMobiles(senderRequest.getRecipients());
        request.setContent(senderRequest.getContent());
        request.setMsgId(senderRequest.getMessageId());

        BlacktelSmsSendResponse sendResponse = blacktelSmsClient.send(request);
        messageSenderResponse.setSucceed(sendResponse.isSucceed());
        messageSenderResponse.setErrorMessage(sendResponse.getTips());

        return messageSenderResponse;
    }

    @Override
    public MessageSenderChannelName getChannelName() {
        return MessageSenderChannelName.BLACKTEL;
    }

    @Override
    public MessageSenderChannelName getParentChannelName() {
        return MessageSenderChannelName.SMS;
    }

    @Override
    public List<SenderDetail> queryStatus() {
        BlacktelSmsStatusRequest request = new BlacktelSmsStatusRequest();
        request.setEprId(channel.getIntValue(EPR_ID_KEY.getValue()));
        request.setUserId(channel.get(USER_ID_KEY.getValue()));
        request.setPassword(channel.get(PASSWORD_KEY.getValue()));

        BlacktelSmsStatusResponse status = blacktelSmsClient.getStatus(request);
        return status
                .getList()
                .getStatusboxes()
                .stream()
                .map(this::toSenderDetail)
                .toList();

    }

    private SenderDetail toSenderDetail(BlacktelStatusbox blacktelStatusbox) {
        if (blacktelStatusbox == null) {
            return null;
        }
        SenderDetail senderDetail = new SenderDetail();
        senderDetail.setRecipient(blacktelStatusbox.getMobile());
        senderDetail.setMessageId(blacktelStatusbox.getMsgId());
        if (Objects.equals(blacktelStatusbox.getStatus(), "1")) {
            senderDetail.error("发送短信失败");
        }
        return senderDetail;
    }
}
