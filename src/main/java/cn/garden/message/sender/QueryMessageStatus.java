package cn.garden.message.sender;

import java.util.List;

/**
 * 用于异步查询消息状态接口
 *
 * @author liwei
 */
public interface QueryMessageStatus {

    List<SenderDetail> queryStatus();
}
