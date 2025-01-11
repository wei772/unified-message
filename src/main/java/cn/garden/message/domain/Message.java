package cn.garden.message.domain;

import cn.garden.message.sender.SenderDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 消息对象
 *
 * @author liwei
 */
public class Message {

    /**
     * 消息Id
     */
    private String id;

    /**
     * 消息状态
     */
    private String status;

    /**
     * 消息状态信息
     */
    private String statusMessage;

    private Map<String, String> properties;

    private String channelName;

    private String content;

    /**
     * 接收者类型
     *
     * @see RecipientType
     */
    private String recipientType;

    private List<String> recipients;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<MessageDetail> details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

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

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    /**
     * 同类型更新，有点怪
     */
    public void update(Message value) {
        if (value == null) {
            return;
        }
        setStatus(value.getStatus());
        setStatusMessage(value.getStatusMessage());
        setProperties(value.getProperties());
        setChannelName(value.getChannelName());
        setContent(value.getContent());
        setRecipients(value.getRecipients());
        setRecipientType(value.getRecipientType());
        setDetails(value.getDetails());
    }

    public boolean succeed() {
        return MessageStatus.DONE.getName().equals(status);
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public List<MessageDetail> getDetails() {
        return details;
    }

    public void setDetails(List<MessageDetail> details) {
        this.details = details;
    }

    public MessageDetail getDetail(String recipient) {
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        return details
                .stream()
                .filter(m -> StringUtils.equals(m.getRecipient(), recipient))
                .findFirst()
                .orElse(null);
    }

    public void updateMessageDetail(SenderDetail senderDetail) {
        MessageDetail messageDetail = details
                .stream()
                .filter(m -> StringUtils.equals(m.getSourceRecipient(), senderDetail.getRecipient()))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(messageDetail)) {
            if (senderDetail.succeed()) {
                messageDetail.setStatus(MessageStatus.DONE.getName());
            } else {
                messageDetail.setStatus(MessageStatus.FAIL.getName());
            }
            messageDetail.setStatusMessage(senderDetail.getErrorMessage());
        }
    }


}
