package mongodb.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public interface BaseDao<T> {
	/**
     * 获取查询数据的数量
     * @Title: findCount 
     * @Description: TODO
     * @param @param query
     * @param @return   
     * @return Long   
     * @throws
     */
    public Long findCount(Query query);
    /**
     * 查询数据
     * @Title: find 
     * @Description: TODO
     * @param @param query
     * @param @return   
     * @return List<T>   
     * @throws
     */
    public List<T> find(Query query);
    /**
     * 分页查询数据
     * @Title: findList 
     * @Description: TODO
     * @param @param start
     * @param @param limit
     * @param @return   
     * @return List<T>   
     * @throws
     */
    public List<T> findList(int start, int limit);
    /**
     * 根据id获取单个数据
     * @Title: findOne 
     * @Description: TODO
     * @param @param id
     * @param @return   
     * @return T   
     * @throws
     */
    public T findOne(String id);
    /**
     * 插入一条数据
     * @Title: insert 
     * @Description: TODO
     * @param @param entity   
     * @return void   
     * @throws
     */
    public void insert(T entity);
    /**
     * 更新数据
     * @Title: update 
     * @Description: TODO
     * @param @param entity
     * @param @throws Exception   
     * @return void   
     * @throws
     */
    public void update(T entity) throws Exception;
    /**
     * 更新数据
     * @Title: update 
     * @Description: TODO
     * @param @param query
     * @param @param update   
     * @return void   
     * @throws
     */
    public void update(Query query,Update update);
    /**
     * 删除数据
     * @Title: remove 
     * @Description: TODO
     * @param @param entity   
     * @return void   
     * @throws
     */
    public void remove(T entity);
    
}
