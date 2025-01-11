package cn.garden.message.client.sms.blacktel;

import cn.garden.message.client.sms.blacktel.model.BlacktelSmsStatusResponse;
import cn.garden.message.client.sms.blacktel.model.BlacktelStatusbox;
import cn.garden.message.client.sms.blacktel.model.BlacktelStatusboxList;
import cn.garden.message.util.XmlUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * xml 序列与反序列化这么复杂，只能说垃圾格式
 *
 * @author liwei
 */
public class BlacktelSmsStatusResponseTest {

    @Test
    public void xmlToResponse() {
        String xml = "<returnsms>\n" +
                "    <count>1</count>\n" +
                "    <list>\n" +
                "        <statusbox>\n" +
                "            <mobile>13533231121</mobile>\n" +
                "            <msgId>928512599411178608</msgId>\n" +
                "            <status>1</status>\n" +
                "            <deliverdTime>2025-01-06 15:14:00</deliverdTime>\n" +
                "        </statusbox>\n" +
                "    </list>\n" +
                "</returnsms>";
        BlacktelSmsStatusResponse response = XmlUtil.toObject(xml, BlacktelSmsStatusResponse.class);

        assertEquals(1, response.getCount());
        assertEquals(1, CollectionUtils.size(response.getList().getStatusboxes()));
        assertEquals("928512599411178608", response.getList().getStatusboxes().get(0).getMsgId());

    }

    @Test
    public void responseToXml() {
        BlacktelSmsStatusResponse blacktelSmsStatusResponse = new BlacktelSmsStatusResponse();
        blacktelSmsStatusResponse.setCount(1);
        BlacktelStatusboxList blacktelStatusboxList = new BlacktelStatusboxList();

        List<BlacktelStatusbox> statusboxes = new ArrayList<>();
        BlacktelStatusbox blacktelStatusbox = new BlacktelStatusbox();
        blacktelStatusbox.setMobile("13533231121");
        blacktelStatusbox.setMsgId("928512599411178608");
        blacktelStatusbox.setStatus("1");
        blacktelStatusbox.setDeliverdTime("2025-01-06 15:14:00");

        statusboxes.add(blacktelStatusbox);
        blacktelStatusboxList.setStatusboxes(statusboxes);
        blacktelSmsStatusResponse.setList(blacktelStatusboxList);

        String xml = XmlUtil.toXml(blacktelSmsStatusResponse);

        BlacktelSmsStatusResponse xmlResponse = XmlUtil.toObject(xml, BlacktelSmsStatusResponse.class);

        assertEquals(1, xmlResponse.getCount());
        assertEquals(1, CollectionUtils.size(xmlResponse.getList().getStatusboxes()));
        assertEquals("928512599411178608", xmlResponse.getList().getStatusboxes().get(0).getMsgId());

    }
}
