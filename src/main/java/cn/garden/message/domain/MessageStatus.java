package cn.garden.message.domain;

import cn.garden.message.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 消息状态
 *
 * @author liwei
 */
public enum MessageStatus {

    DONE("done", "发送成功"),

    FAIL("fail", "失败"),
    ;


    private final String name;

    private final String description;

    MessageStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static MessageStatus of(String name) {
        for (MessageStatus value : values()) {
            if (StringUtils.equals(name, value.getName())) {
                return value;
            }
        }
        throw ExceptionUtil.createDefaultException("找不到MessageStatus枚举" + name);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
