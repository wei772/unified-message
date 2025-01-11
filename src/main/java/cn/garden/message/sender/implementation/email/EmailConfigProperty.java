package cn.garden.message.sender.implementation.email;

import cn.garden.message.sender.SenderConfigProperty;

/**
 * @author liwei
 */
public enum EmailConfigProperty implements SenderConfigProperty {

    USERNAME("sender.email.username", "用户名称"),

    PASSWORD("sender.email.password", "用户密码"),

    HOST("sender.email.host", "服务器地址"),

    PORT("sender.email.port", "服务器端口"),

    ;

    private final String value;

    private final String name;

    EmailConfigProperty(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
