package cn.garden.message.client.sms.blacktel.api;

import cn.garden.message.client.sms.blacktel.model.BlacktelSmsSendRequest;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsSendResponse;
import cn.garden.message.client.sms.blacktel.model.BlacktelStatusbox;
import cn.garden.message.util.JsonUtil;
import cn.garden.message.util.RegexUtil;
import cn.garden.message.util.TestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockBlacktelSendService implements BlacktelSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockBlacktelSendService.class);

    private static final List<BlacktelStatusbox> statusboxList = new ArrayList<>();

    public static List<BlacktelStatusbox> getStatusboxList() {
        return statusboxList;
    }

    @Override
    public BlacktelSmsSendResponse sendSms(BlacktelSmsSendRequest sendRequest) {
        BlacktelSmsSendResponse blacktelSmsSendResponse = new BlacktelSmsSendResponse();
        if (StringUtils.isEmpty(sendRequest.getUserId())) {
            blacktelSmsSendResponse.setResult("-3");
            blacktelSmsSendResponse.setTips("key参数错误");
        }

        List<String> mobiles = sendRequest.getMobiles();
        //完全仿造与实际不符，实际不会报错
        for (String mobile : mobiles) {
            if (TestUtil.isTestData(mobile)) {
                blacktelSmsSendResponse.setResult("-4");
                blacktelSmsSendResponse.setTips("手机号错误");
            }

            BlacktelStatusbox blacktelStatusbox = new BlacktelStatusbox();
            blacktelStatusbox.setMobile(mobile);
            blacktelStatusbox.setMsgId(sendRequest.getMsgId());
            blacktelStatusbox.setStatus("0");
            if (!RegexUtil.isNumber(mobile)) {
                blacktelStatusbox.setStatus("1");
            }

            blacktelStatusbox.setDeliverdTime(LocalDateTime.now().toString());
            statusboxList.add(blacktelStatusbox);
        }


        LOGGER.info("sendSms" + JsonUtil.toJson(blacktelSmsSendResponse));
        return blacktelSmsSendResponse;
    }


}
