package cn.garden.message.client.sms.blacktel.api;

import cn.garden.message.client.sms.blacktel.model.BlacktelSmsStatusRequest;
import feign.QueryMap;
import feign.RequestLine;

/**
 * @author liwei
 */
public interface BlacktelStatusService {

    /**
     * 这个接口返回不规范，
     */
    @RequestLine("GET /api/sms")
    String getSmsStatus(@QueryMap BlacktelSmsStatusRequest statusRequest);
}
