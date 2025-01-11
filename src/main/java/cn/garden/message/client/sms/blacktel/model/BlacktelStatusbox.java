package cn.garden.message.client.sms.blacktel.model;


/**
 * @author liwei
 */
//@XmlRootElement(name = "statusbox")
public class BlacktelStatusbox {

    private String mobile;

    private String msgId;

    /**
     * 0 成功
     * 1 失败
     */
    private String status;

    private String deliverdTime;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliverdTime() {
        return deliverdTime;
    }

    public void setDeliverdTime(String deliverdTime) {
        this.deliverdTime = deliverdTime;
    }
}
