package cn.garden.message.repository.implementation;

import cn.garden.message.domain.Message;
import cn.garden.message.repository.MessageParam;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.util.IdGenerator;
import cn.garden.message.util.page.PageInfo;
import cn.garden.message.util.page.PagedList;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Objects;

/**
 * @author liwei
 */
public class MongoMessageRepository extends MessageRepository {

    private static final String MONGO_ID = "_id";
    private final MongoTemplate mongoTemplate;

    public MongoMessageRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public PagedList<Message> getPageList(MessageParam messageParam, PageInfo pageInfo) {
        pageInfo.setTotalCount(count(messageParam));
        if (Objects.equals(pageInfo.getTotalCount(), 0L)) {
            return new PagedList<>(pageInfo);
        }

        Query query = filterMessages(messageParam).with(pageInfo.toPageable());
        List<Message> messages = mongoTemplate.find(query, Message.class);
        return new PagedList<>(pageInfo, messages);
    }

    @Override
    public List<Message> getList(MessageParam messageParam) {
        return mongoTemplate.find(filterMessages(messageParam), Message.class);
    }

    @Override
    public Long count(MessageParam messageParam) {
        return mongoTemplate.count(filterMessages(messageParam), Message.class);
    }

    @Override
    public Message getById(String id) {
        Query query = new Query(Criteria.where(MONGO_ID).is(id));
        return mongoTemplate.findOne(query, Message.class);
    }

    @Override
    public void saveCore(Message message) {
        if (StringUtils.isEmpty(message.getId())) {
            message.setId(IdGenerator.generateRandom());
        }
        mongoTemplate.save(message);
    }

    @Override
    public void remove(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where(MONGO_ID).in(ids));
        mongoTemplate.remove(query, Message.class);
    }

    private Query filterMessages(MessageParam messageParam) {
        Query query = new Query();
        if (StringUtils.isNotEmpty(messageParam.getId())) {
            query.addCriteria(Criteria.where(MONGO_ID).is(messageParam.getId()));
        }
        if (CollectionUtils.isNotEmpty(messageParam.getIds())) {
            query.addCriteria(Criteria.where(MONGO_ID).in(messageParam.getIds()));
        }

        query.with(Sort.by(Sort.Order.desc("updateTime")));
        return query;
    }
}
