package cn.garden.message.client.sms.blacktel.api;

import cn.garden.message.client.sms.blacktel.model.BlacktelSmsStatusRequest;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsStatusResponse;
import cn.garden.message.client.sms.blacktel.model.BlacktelStatusbox;
import cn.garden.message.client.sms.blacktel.model.BlacktelStatusboxList;
import cn.garden.message.util.JsonUtil;
import cn.garden.message.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MockBlacktelStatusService implements BlacktelStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockBlacktelStatusService.class);

    @Override
    public String getSmsStatus(BlacktelSmsStatusRequest statusRequest) {
        if (StringUtils.isEmpty(statusRequest.getUserId())) {
            BlacktelSmsStatusResponse blacktelSmsStatusResponse = new BlacktelSmsStatusResponse();
            blacktelSmsStatusResponse.setResult("-3");
            blacktelSmsStatusResponse.setTips("key参数错误");

            LOGGER.info("getSmsStatus" + JsonUtil.toJson(blacktelSmsStatusResponse));
            return JsonUtil.toJson(blacktelSmsStatusResponse);
        }

        List<BlacktelStatusbox> statusboxList = MockBlacktelSendService.getStatusboxList();

        BlacktelSmsStatusResponse blacktelSmsStatusResponse = new BlacktelSmsStatusResponse();
        blacktelSmsStatusResponse.setCount(statusboxList.size());
        BlacktelStatusboxList blacktelStatusboxList = new BlacktelStatusboxList();
        blacktelStatusboxList.setStatusboxes(statusboxList);
        blacktelSmsStatusResponse.setList(blacktelStatusboxList);

        String xml = XmlUtil.toXml(blacktelSmsStatusResponse);
        LOGGER.info("getSmsStatus" + JsonUtil.toJson(xml));
        return xml;
    }
}
