package cn.garden.message.sender.implementation.wecom;

import cn.garden.message.sender.SenderConfigProperty;

/**
 * @author liwei
 */
public enum WecomConfigProperty implements SenderConfigProperty {

    CORP_ID("sender.wecom.corpId", "企业Id"),

    CORP_SECRET("sender.wecom.corpSecret", "企业密钥"),

    AGENT_ID("sender.wecom.agentId", "客户ID"),

    ;

    private final String value;

    private final String name;

    WecomConfigProperty(String value, String name) {
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
