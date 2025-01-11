package cn.garden.message.sender;

import cn.garden.message.util.ExceptionUtil;
import cn.garden.message.util.PropertiesUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static cn.garden.message.sender.MessageSenderChannelName.*;

/**
 * 配置发送者相关属性
 *
 * @author liwei
 */
public class MessageSenderChannel {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSenderChannel.class);
    private final static List<MessageSenderChannel> messageSenderChannels = new ArrayList<>();

    static {
        initAll();
    }

    private final Map<String, String> properties = new HashMap<>();

    private final String name;

    private final String parent;

    private final List<String> children;

    private final Set<String> configNames = new HashSet<>();

    public MessageSenderChannel() {
        this(StringUtils.EMPTY, StringUtils.EMPTY);
    }

    public MessageSenderChannel(String name) {
        this(name, StringUtils.EMPTY);
    }

    public MessageSenderChannel(String name, String parent) {
        this.name = name;
        this.parent = parent;
        this.children = null;
        loadConfigNames();
    }

    public MessageSenderChannel(String name, List<String> children) {
        this.name = name;
        this.children = children;
        this.parent = null;
        loadConfigNames();
    }

    public static void initAll() {
        messageSenderChannels.add(new MessageSenderChannel(WECOM.getName()));
        messageSenderChannels.add(new MessageSenderChannel(EMAIL.getName()));

        messageSenderChannels.add(new MessageSenderChannel(
                SMS.getName()
                , Collections.singletonList(BLACKTEL.getName())));
        messageSenderChannels.add(new MessageSenderChannel(
                BLACKTEL.getName()
                , SMS.getName()));

        Map<String, String> propertiesMap = PropertiesUtil.getPropertiesMap("application.properties");
        putChannelProperties(propertiesMap);
    }

    public static List<MessageSenderChannel> getAllChannel() {
        return messageSenderChannels;
    }

    public static MessageSenderChannel getChannel(String name) {
        return messageSenderChannels
                .stream()
                .filter(m -> StringUtils.equals(m.getName(), name))
                .findFirst()
                .orElse(null);
    }

    public static void putChannelProperties(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            for (MessageSenderChannel messageSenderChannel : messageSenderChannels) {
                messageSenderChannel.add(entry.getKey(), entry.getValue());
            }
        }
    }

    public Boolean add(String key, String value) {
        if (CollectionUtils.isEmpty(configNames)) {
            properties.put(key, value);
            return true;
        }
        //configNames 不为空才需要判断是否存在
        if (configNames.contains(key)) {
            properties.put(key, value);
            return true;
        }
        return false;
    }


    public void addAll(Map<String, String> properties) {
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }


    public String get(String key) {
        return properties.get(key);
    }

    public Integer getIntValue(String key) {
        String stringValue = get(key);
        if (StringUtils.isEmpty(stringValue)) {
            return null;
        }
        return Integer.parseInt(stringValue);
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public Map<String, String> getAll() {
        return properties;
    }

    /**
     * 加载所有的对应枚举配置项
     */
    private void loadConfigNames() {
        if (CollectionUtils.isEmpty(children)) {
            loadOneConfigNames(name, parent);
        } else {
            for (String child : children) {
                loadOneConfigNames(child, name);
            }
        }
    }

    private void loadOneConfigNames(String name, String parent) {
        //名称为空时，不需要加载默认配置属性
        if (StringUtils.isEmpty(name)) {
            return;
        }

        String enumFullName = "cn.garden.message.sender.implementation."
                + getConfigPackage(name, parent)
                + "."
                + getConfigPropertyName(name);
        try {
            Class<?> enumClass = Class.forName(enumFullName);
            Object[] enumConstants = enumClass.getEnumConstants();

            for (Object enumConstant : enumConstants) {
                SenderConfigProperty configProperty = (SenderConfigProperty) enumConstant;
                configNames.add(configProperty.getValue());
                add(configProperty.getValue(), null);
            }
        } catch (ClassNotFoundException e) {
            throw ExceptionUtil.createDefaultException("加载配置模板类失败:" + enumFullName, e);
        }
    }

    private String getConfigPropertyName(String name) {
        return StringUtils.capitalize(name) + "ConfigProperty";
    }

    private String getConfigPackage(String name, String parent) {
        String pack = "";
        if (StringUtils.isNotEmpty(parent)) {
            pack = parent + ".";
        }
        return pack + name;
    }


}
