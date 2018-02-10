package mongodb.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import mongodb.common.MongoFactory;
import mongodb.dao.BaseDao;
public class BaseDaoImpl<T> implements BaseDao<T>{
    private Class<T> clazz;

    @SuppressWarnings("unchecked")
	public BaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) getClass()
                .getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoFactory mongoFactory;

    public List<T> find(Query query){
        return mongoTemplate.find(query, clazz);
    }

    public List<T> findList(int skip, int limit) {
        Query query = new Query();
        query.with(new Sort(new Order(Direction.ASC, "_id")));
        query.skip(skip).limit(limit);
        return find(query);
    }


    public T findOne(String id) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        return mongoTemplate.findOne(query, clazz);
    }

    public void insert(T entity) { 
        mongoTemplate.insert(entity);
    }


    public void update(T entity) throws Exception {
        Map<String, Object> map = mongoFactory.converObjectToParams(entity);

        Query query = new Query();
        query.addCriteria(new Criteria("link").is(map.get("link")));
        Update update = (Update) map.get("update");

        this.update(query, update);
    }


    public void update(Query query,Update update){
        mongoTemplate.updateFirst(query, update, clazz);
    }

    public Long findCount(Query query){
        return mongoTemplate.count(query, clazz);
    }

    public void remove(T entity){
        mongoTemplate.remove(entity);
    }
    
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
