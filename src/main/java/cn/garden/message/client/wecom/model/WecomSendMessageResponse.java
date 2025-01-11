package cn.garden.message.client.wecom.model;

import cn.garden.message.sender.MessageSenderResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liwei
 */
public class WecomSendMessageResponse extends WecomResponseBase {

    /**
     * 不合法的userid，不区分大小写，统一转为小写
     */
    @JsonProperty("invaliduser")
    private String invalidUser;

    /**
     * 不合法的partyid
     */
    @JsonProperty("invalidparty")
    private String invalidParty;


    /**
     * 不合法的标签id
     */
    @JsonProperty("invalidtag")
    private String invalidTag;


    /**
     * 没有基础接口许可(包含已过期)的userid
     */
    @JsonProperty("unlicenseduser")
    private String unlicensedUser;

    /**
     * 消息id，用于撤回应用消息
     */
    @JsonProperty("msgid")
    private String msgId;


    /**
     * 仅消息类型为“按钮交互型”，“投票选择型”和“多项选择型”的模板卡片消息返回，应用可使用response_code调用更新模版卡片消息接口，72小时内有效，且只能使用一次
     */
    @JsonProperty("response_code")
    private String responseCode;

    public String getInvalidUser() {
        return invalidUser;
    }

    public void setInvalidUser(String invalidUser) {
        this.invalidUser = invalidUser;
    }

    public String getInvalidParty() {
        return invalidParty;
    }

    public void setInvalidParty(String invalidParty) {
        this.invalidParty = invalidParty;
    }

    public String getInvalidTag() {
        return invalidTag;
    }

    public void setInvalidTag(String invalidTag) {
        this.invalidTag = invalidTag;
    }

    public String getUnlicensedUser() {
        return unlicensedUser;
    }

    public void setUnlicensedUser(String unlicensedUser) {
        this.unlicensedUser = unlicensedUser;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public MessageSenderResponse toMessageSenderResponse() {
        MessageSenderResponse messageSenderResponse = new MessageSenderResponse();
        messageSenderResponse.setSucceed(succeed());
        if (BooleanUtils.isNotTrue(succeed())) {
            messageSenderResponse.setErrorMessage(getErrMsg());
        }
        return messageSenderResponse;
    }

    @JsonIgnore
    public List<String> getInvalidUsers() {
        if (StringUtils.isEmpty(invalidUser)) {
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(invalidUser, "|")).toList();

    }

    @JsonIgnore
    public void setInvalidUsers(List<String> invalidUsers) {
        if (CollectionUtils.isEmpty(invalidUsers)) {
            invalidUser = StringUtils.EMPTY;
        }
        invalidUser = StringUtils.join(invalidUsers, "|");
    }
}
