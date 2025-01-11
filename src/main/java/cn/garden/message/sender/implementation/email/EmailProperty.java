package cn.garden.message.sender.implementation.email;


/**
 * @author liwei
 */
public enum EmailProperty {

    SUBJECT("email_subject", "邮件标题"),
    ;

    private final String value;

    private final String name;

    EmailProperty(String value, String name) {
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
