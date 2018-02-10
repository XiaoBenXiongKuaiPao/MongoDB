package mongodb.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import mongodb.common.Pagination;
import mongodb.dao.ArticleDao;
import mongodb.pojo.Article;
import mongodb.pojo.Student;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao{
	
	public Article findOneByLink(String link) {
        Query query = new Query(Criteria.where("link").is(link));

        return getMongoTemplate().findOne(query, Article.class);
    }
	
	public List<Student> findAll() {

        return getMongoTemplate().findAll(Student.class);
    }

    public Article findNewestByState(Integer state){
        Query query = createQuery(state);
        query.limit(1).with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));

         List<Article> list = this.find(query);

         if(list.size()>0){
             return list.get(0);
         }

        return null;
    }

    public List<Article> findListByPage(Pagination page,Integer state) {  
        Query query = createQuery(state);
        query.with(page.getSort());
        query.skip(page.getSkip()).limit(page.getPageSize());// 从skip开始,取多少条记录  

        return this.find(query);  
    }

    public Long findCount(Integer state){
        Query query = createQuery(state);
        return this.findCount(query);
    }

    private Query createQuery(Integer state){
        Query query = new Query();
        query.addCriteria(Criteria.where("state").is(state));  
        return query;
    }

    public Article findOneByLinkOrTitle(String link, String title) {
        Query query = new Query(
                new Criteria().orOperator(Criteria.where("title").is(title),    Criteria.where("link").is(link))
                );
        return getMongoTemplate().findOne(query, Article.class);
    }

	public void createArticle(Article article) {
		article.setTitle("ok");
		article.setCreateTime(new Date());
		article.setState(23);
		insert(article);
		
	}

	public void removeByLink(String link) {
		 Query query = new Query();
	     query.addCriteria(Criteria.where("link").is(link));  
	     getMongoTemplate().remove(query, Article.class);
		
	}

	/*@Override
	public void createArticle(Article article) {
		article.setTitle("ok");
		article.setCreateTime(new Date());
		article.setState(23);
		insert(article);
	}

	@Override
	public void removeByLink(String link) {
        Query query = new Query();
        query.addCriteria(Criteria.where("link").is(link));  
		getMongoTemplate().remove(query, Article.class);
	}*/
}
