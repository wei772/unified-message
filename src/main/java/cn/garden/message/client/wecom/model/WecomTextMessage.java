package cn.garden.message.client.wecom.model;


/**
 * @author liwei
 */
public class WecomTextMessage {

    /**
     * 文本内容
     */
    private String content;

    public WecomTextMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
