package cn.garden.message.sender.implementation.sms.blacktel;

import cn.garden.message.sender.SenderConfigProperty;

/**
 * @author liwei
 */
public enum BlacktelConfigProperty implements SenderConfigProperty {

    HOST("sender.blacktel.host", "域名"),

    EPR_ID_KEY("sender.blacktel.eprIdKey", "企业Id"),

    USER_ID_KEY("sender.blacktel.useridKey", "用户Id"),

    PASSWORD_KEY("sender.blacktel.passwordKey", "密码"),

    ;


    private final String value;

    private final String name;

    BlacktelConfigProperty(String value, String name) {
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
