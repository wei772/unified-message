package cn.garden.message.sender.implementation.wecom;

import cn.garden.message.client.wecom.WecomMessageClient;
import cn.garden.message.client.wecom.model.WecomSendMessageRequest;
import cn.garden.message.client.wecom.model.WecomSendMessageResponse;
import cn.garden.message.sender.MessageSender;
import cn.garden.message.sender.MessageSenderChannelName;
import cn.garden.message.sender.MessageSenderResponse;
import cn.garden.message.sender.SenderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static cn.garden.message.sender.implementation.wecom.WecomConfigProperty.*;

/**
 * @author liwei
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Service
public class WecomMessageSender extends MessageSender {

    private final WecomMessageClient wecomMessageClient;

    public WecomMessageSender() {
        wecomMessageClient = new WecomMessageClient();
    }

    @Autowired
    public WecomMessageSender(WecomMessageClient wecomMessageClient) {
        this.wecomMessageClient = wecomMessageClient;
    }

    @Override
    protected MessageSenderResponse send() {
        WecomSendMessageRequest wecomSendMessageRequest = new WecomSendMessageRequest();
        wecomSendMessageRequest.setCorpId(channel.get(CORP_ID.getValue()));
        wecomSendMessageRequest.setCorpSecret(channel.get(CORP_SECRET.getValue()));
        wecomSendMessageRequest.setAgentId(channel.get(AGENT_ID.getValue()));
        wecomSendMessageRequest.setText(senderRequest.getContent());
        wecomSendMessageRequest.setToUsers(senderRequest.getRecipients());

        WecomSendMessageResponse wecomSendMessageResponse = wecomMessageClient.sendMessage(wecomSendMessageRequest);

        MessageSenderResponse messageSenderResponse = wecomSendMessageResponse.toMessageSenderResponse();
        List<String> invalidUsers = wecomSendMessageResponse.getInvalidUsers();
        List<SenderDetail> senderDetails = new ArrayList<>();
        for (String recipient : senderRequest.getRecipients()) {
            SenderDetail senderDetail = new SenderDetail();
            senderDetail.setRecipient(recipient);
            if (invalidUsers.contains(recipient)) {
                senderDetail.error("非法用户");
            } else {
                senderDetail.setSucceed(true);
            }
            senderDetails.add(senderDetail);
        }
        messageSenderResponse.setDetails(senderDetails);
        return messageSenderResponse;
    }

    @Override
    public MessageSenderChannelName getChannelName() {
        return MessageSenderChannelName.WECOM;
    }
}
