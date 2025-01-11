package cn.garden.message.sender;

import cn.garden.message.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liwei
 */
public enum MessageSenderChannelName {

    WECOM("wecom", "企业微信"),

    SMS("sms", "短信"),

    BLACKTEL("blacktel", "blacktel短信"),

    EMAIL("email", "邮件"),

    ;


    private final String name;

    private final String description;

    MessageSenderChannelName(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static MessageSenderChannelName of(String name) {
        for (MessageSenderChannelName value : values()) {
            if (StringUtils.equals(name, value.getName())) {
                return value;
            }
        }
        throw ExceptionUtil.createDefaultException("找不到MessageSenderChannelName枚举" + name);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
