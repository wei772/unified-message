package cn.garden.message.repository;

import cn.garden.message.domain.Message;
import cn.garden.message.util.page.PageInfo;
import cn.garden.message.util.page.PagedList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author liwei
 */
public abstract class MessageRepository {

    public abstract Long count(MessageParam messageParam);

    public abstract PagedList<Message> getPageList(MessageParam messageParam, PageInfo pageInfo);

    public abstract List<Message> getList(MessageParam messageParam);

    public abstract Message getById(String id);

    /**
     * 保存消息
     */
    public void save(Message message) {
        if (Objects.isNull(message.getCreateTime())) {
            message.setCreateTime(LocalDateTime.now());
        }
        message.setUpdateTime(LocalDateTime.now());
        saveCore(message);
    }

    protected abstract void saveCore(Message message);

    public abstract void remove(List<String> ids);
}
