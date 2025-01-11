package cn.garden.message.domain;

import cn.garden.message.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 接收者类型
 *
 * @author liwei
 */
public enum RecipientType {

    SOURCE("source"),

    USER("user"),
    ;


    private final String name;


    RecipientType(String name) {
        this.name = name;
    }

    public static RecipientType of(String name) {
        if (StringUtils.isEmpty(name)) {
            return SOURCE;
        }

        for (RecipientType value : values()) {
            if (StringUtils.equals(name, value.getName())) {
                return value;
            }
        }
        throw ExceptionUtil.createDefaultException("找不到RecipientType枚举" + name);
    }

    public String getName() {
        return name;
    }

}
