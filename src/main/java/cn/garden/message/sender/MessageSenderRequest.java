package cn.garden.message.sender;

import cn.garden.message.util.ExceptionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author liwei
 */
public class MessageSenderRequest {

    private final Map<String, String> properties = new HashMap<>();

    private String content;

    private List<String> recipients;

    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public void addAllProperty(Map<String, String> properties) {
        if (Objects.nonNull(properties)) {
            this.properties.putAll(properties);
        }
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public void validate() {
        if (StringUtils.isEmpty(content)) {
            throw ExceptionUtil.createDefaultException("消息内容不能为空");
        }
        if (CollectionUtils.isEmpty(recipients)) {
            throw ExceptionUtil.createDefaultException("消息接收账号不能为空");
        }
    }
}
