package cn.garden.message.sender;

import cn.garden.message.config.SenderConfig;
import cn.garden.message.sender.implementation.email.EmailConfigProperty;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static cn.garden.message.config.SenderConfig.getWecomCorpId;
import static cn.garden.message.config.SenderConfig.getWecomCorpSecret;
import static cn.garden.message.sender.MessageSenderChannelName.SMS;
import static cn.garden.message.sender.implementation.wecom.WecomConfigProperty.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * @author liwei
 */
public class MessageSenderChannelTest {

    @Test
    public void getAll() {
        List<MessageSenderChannel> all = MessageSenderChannel.getAllChannel();

        assertEquals(4, all.size());
    }


    @Test
    public void updateConfig() {
        HashMap<String, String> map = new HashMap<>();
        map.put(EmailConfigProperty.USERNAME.getValue(), "liwei");
        MessageSenderChannel.putChannelProperties(map);

        MessageSenderChannel email = MessageSenderChannel.getChannel("email");

        assertEquals("liwei", email.get(EmailConfigProperty.USERNAME.getValue()));
    }

    @Test
    public void wecomConfig() {
        MessageSenderChannel messageSenderChannel = new MessageSenderChannel("wecom");

        messageSenderChannel.add(CORP_ID.getValue(), getWecomCorpId());
        messageSenderChannel.add(CORP_SECRET.getValue(), getWecomCorpSecret());

        assertEquals(3, messageSenderChannel.getAll().size());
        assertEquals(getWecomCorpSecret(), messageSenderChannel.get(CORP_SECRET.getValue()));
        assertNull(messageSenderChannel.get(AGENT_ID.getValue()));
    }

    @Test
    public void emailConfig() {
        MessageSenderChannel messageSenderChannel = new MessageSenderChannel("email");

        assertEquals(4, messageSenderChannel.getAll().size());
    }

    @Test
    public void smsConfig() {
        MessageSenderChannel messageSenderChannel = new MessageSenderChannel(SMS.getName(), List.of("blacktel"));

        assertEquals(4, messageSenderChannel.getAll().size());
    }


    @Test
    public void blacktelConfig() {
        MessageSenderChannel messageSenderChannel = new MessageSenderChannel("blacktel", "sms");

        assertEquals("sms", messageSenderChannel.getParent());
        assertEquals(4, messageSenderChannel.getAll().size());
    }


    @Test
    public void addAll() {
        MessageSenderChannel messageSenderChannel = new MessageSenderChannel("wecom");

        messageSenderChannel.addAll(SenderConfig.getPropertiesMap());

        assertEquals(3, messageSenderChannel.getAll().size());
        assertEquals(getWecomCorpSecret(), messageSenderChannel.get(CORP_SECRET.getValue()));
    }
}
