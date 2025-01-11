package cn.garden.message.domain;

/**
 * @author liwei
 */
public class MessageDetail {

    /**
     * 消息详情状态
     */
    private String status;

    /**
     * 消息详情状态信息
     */
    private String statusMessage;

    private String recipient;

    private String sourceRecipient;

    public MessageDetail() {
    }

    public MessageDetail(String sourceRecipient, String recipient) {
        this.sourceRecipient = sourceRecipient;
        this.recipient = recipient;
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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSourceRecipient() {
        return sourceRecipient;
    }

    public void setSourceRecipient(String sourceRecipient) {
        this.sourceRecipient = sourceRecipient;
    }

    public void error(String errorMessage) {
        statusMessage = errorMessage;
        status = MessageStatus.FAIL.getName();
    }
}
