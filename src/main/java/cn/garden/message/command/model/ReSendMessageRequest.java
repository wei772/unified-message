package cn.garden.message.command.model;

/**
 * @author liwei
 */
public class ReSendMessageRequest {

    private String messageId;

    public ReSendMessageRequest() {
    }

    public ReSendMessageRequest(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
