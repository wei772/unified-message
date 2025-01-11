package cn.garden.message.client.wecom.model;


import cn.garden.message.util.ExceptionUtil;
import cn.garden.message.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author liwei
 */
public class WecomSendMessageRequest extends WecomRequestBase {

    /**
     * 发送用户列表,使用 | 分割
     */
    @JsonProperty("touser")
    private String toUser;

    /**
     * 发送部门列表
     */
    @JsonProperty("toparty")
    private String toParty;

    /**
     * 消息类型
     */
    @JsonProperty("msgtype")
    private String msgType = "text";

    /**
     * 企业应用的id
     */
    @JsonProperty("agentid")
    private String agentId;

    /**
     * 文本对象
     */
    private WecomTextMessage text;


    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getToParty() {
        return toParty;
    }

    public void setToParty(String toParty) {
        this.toParty = toParty;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public WecomTextMessage getText() {
        return text;
    }

    public void setText(WecomTextMessage text) {
        this.text = text;
    }

    public void setText(String content) {
        this.text = new WecomTextMessage(content);
    }

    public void validate() {
        validateBase();
        if (StringUtils.isEmpty(agentId)) {
            throw ExceptionUtil.createDefaultException("agentId不能为空");
        }

    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

    public List<String> getToUsers() {
        return Arrays.stream(StringUtils.split(getToUser(), "|")).toList();
    }

    public void setToUsers(List<String> users) {
        this.toUser = String.join("|", users);
    }
}
