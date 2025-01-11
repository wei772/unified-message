package cn.garden.message.client.sms.blacktel.api;

import cn.garden.message.client.sms.blacktel.model.BlacktelSmsSendRequest;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsSendResponse;
import feign.QueryMap;
import feign.RequestLine;

/**
 * @author liwei
 */
public interface BlacktelSendService {

    @RequestLine("GET /api/webservice")
    BlacktelSmsSendResponse sendSms(@QueryMap BlacktelSmsSendRequest sendRequest);

}
