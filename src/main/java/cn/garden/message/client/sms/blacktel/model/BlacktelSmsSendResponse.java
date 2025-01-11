package cn.garden.message.client.sms.blacktel.model;

/**
 * @author liwei
 * 响应结果：{"result":"1","tips":"提交成功","msgId":null,"succeed":true}
 */
public class BlacktelSmsSendResponse extends BlacktelResponseBase {

    /**
     * 消息Id
     */
    private String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
