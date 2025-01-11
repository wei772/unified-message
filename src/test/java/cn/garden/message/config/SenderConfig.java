package cn.garden.message.config;

import cn.garden.message.sender.implementation.email.EmailConfigProperty;
import cn.garden.message.sender.implementation.sms.blacktel.BlacktelConfigProperty;
import cn.garden.message.sender.implementation.wecom.WecomConfigProperty;
import cn.garden.message.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author liwei
 */
public class SenderConfig {

    private static final Properties properties;

    static {
        properties = PropertiesUtil.getProperties(
                "application.properties"
                , "sender.properties");
    }

    public static Map<String, String> getPropertiesMap() {
        Map<String, String> map = new HashMap<>();
        for (Object key : properties.keySet()) {
            map.put((String) key, properties.getProperty((String) key));
        }
        return map;
    }

    public static String getEmailUsername() {
        return properties.getProperty("sender.email.username");
    }

    public static String getEmailHost() {
        return properties.getProperty(EmailConfigProperty.HOST.getValue());
    }

    public static String getEmailPort() {
        return properties.getProperty(EmailConfigProperty.PORT.getValue());
    }

    public static String getEmailPassword() {
        return properties.getProperty(EmailConfigProperty.PASSWORD.getValue());
    }

    public static String getEmailReceiver() {
        return properties.getProperty("sender.email.receiver");
    }

    public static String getWecomCorpId() {
        return properties.getProperty(WecomConfigProperty.CORP_ID.getValue());
    }

    public static String getWecomCorpSecret() {
        return properties.getProperty(WecomConfigProperty.CORP_SECRET.getValue());
    }

    public static String getWecomUsers() {
        return properties.getProperty("sender.wecom.users");
    }

    public static String getWecomAgentId() {
        return properties.getProperty(WecomConfigProperty.AGENT_ID.getValue());
    }

    public static String getBlacktelHost() {
        return properties.getProperty("sender.blacktel.host");
    }

    public static Integer getBlacktelEprIdKey() {
        return Integer.parseInt(properties.getProperty(BlacktelConfigProperty.EPR_ID_KEY.getValue()));
    }

    public static String getBlacktelEprIdKeySource() {
        return properties.getProperty(BlacktelConfigProperty.EPR_ID_KEY.getValue());
    }

    public static String getBlacktelUseridKey() {
        return properties.getProperty(BlacktelConfigProperty.USER_ID_KEY.getValue());
    }

    public static String getBlacktelPasswordKey() {
        return properties.getProperty(BlacktelConfigProperty.PASSWORD_KEY.getValue());
    }

    public static String getBlacktelPhone() {
        return properties.getProperty("sender.blacktel.phone");
    }

}
