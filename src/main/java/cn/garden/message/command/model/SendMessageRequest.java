package cn.garden.message.command.model;

import cn.garden.message.domain.Message;
import cn.garden.message.domain.RecipientType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liwei
 */
public class SendMessageRequest {

    private final Map<String, String> properties = new HashMap<>();

    private String channelName;

    private String content;

    /**
     * 接收者类型
     *
     * @see RecipientType
     */
    private String recipientType;

    /**
     * 接收者
     */
    private List<String> recipients;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public Message toMessage() {
        SendMessageRequest sendMessageRequest = this;
        Message message = new Message();
        message.setChannelName(sendMessageRequest.getChannelName());
        message.setContent(sendMessageRequest.getContent());
        message.setRecipients(sendMessageRequest.getRecipients());
        message.setRecipientType(sendMessageRequest.getRecipientType());
        message.setProperties(sendMessageRequest.getProperties());
        return message;
    }

}
