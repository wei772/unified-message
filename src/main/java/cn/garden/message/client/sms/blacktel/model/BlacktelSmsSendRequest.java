package cn.garden.message.client.sms.blacktel.model;


import cn.garden.message.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author liwei
 */
public class BlacktelSmsSendRequest extends BlacktelRequestBase {

    /**
     * 功能名
     */
    private final String cmd = "send";

    /**
     * 时间戳
     */
    private Long timestamp;


    /**
     * 消息编号
     */
    private String msgId;

    /**
     * 返回结果格
     */
    private Integer format = 1;


    /**
     * 短信号码,多个号码间用半角逗号分隔
     */
    private String mobile;

    /**
     * 发送内容，需要编码utf-8格式
     */
    private String content;

    /**
     * 定时发送
     */
    private String sendDate;

    public String getCmd() {
        return cmd;
    }


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public void validate() {
        validateBase();
        if (Objects.isNull(content)) {
            throw ExceptionUtil.createDefaultException("content不能为空");
        }
        if (Objects.isNull(mobile)) {
            throw ExceptionUtil.createDefaultException("mobile不能为空");
        }
    }

    public List<String> getMobiles() {
        if (StringUtils.isEmpty(mobile)) {
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(mobile, ",")).toList();
    }

    public void setMobiles(List<String> mobiles) {
        mobile = String.join(",", mobiles);
    }
}
