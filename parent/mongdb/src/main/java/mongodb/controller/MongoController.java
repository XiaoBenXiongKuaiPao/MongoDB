package mongodb.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import mongodb.common.Pagination;
import mongodb.dao.impl.ArticleDaoImpl;
import mongodb.pojo.Article;
import mongodb.pojo.Student;
@RestController
public class MongoController {
	@Autowired
	private ArticleDaoImpl articleDao;

	@RequestMapping(value="/user")
	public void insert() {
		for(int i=0;i<100;i++) {
			Article article = new Article();
			article.setLink(String.valueOf(i));
			articleDao.createArticle(article);
		}
	}
	
	@RequestMapping(value="/list")
	public List<Article> list(@RequestParam("page") String page) {
		long count = articleDao.findCount(23);
		Pagination pagination;
		if(page==null) {
			 pagination = new Pagination(1, count);
		}else {
			
			pagination = new Pagination(Integer.valueOf(page), count);
		}
		List<Article> manlist = articleDao.findListByPage(pagination, 23);
		return manlist;
	}
	 @Autowired
	 private MongoTemplate mongoTemplate;
	@RequestMapping(value="/data")
	public Object findAll(){
		 @SuppressWarnings("rawtypes")
		 Query query = new Query();
		 query.addCriteria(Criteria.where("age").gt(12));
		 List<Student> findAll = mongoTemplate.find(query,Student.class);
		 
		 Query query1 = new Query();
		 query1.addCriteria(Criteria.where("age").is(12).and("sex").is("woman"));
		 List<Student> findAll1 = mongoTemplate.find(query1,Student.class);
		 
		 Query query3 = new Query();
		 query3.addCriteria(Criteria.where("age").is(12)).skip(1);
		 List<Student> findAll3 = mongoTemplate.find(query3,Student.class);
		 
		 Student student = new Student();
		 student.setAge(15);
		 student.setId("faf");
		 student.setName("sdfgsg");
		 student.setSex("WOMAN");
		 mongoTemplate.insert(student, "student");
		 
		 
		 Query query6 = new Query();
		 query6.addCriteria(Criteria.where("sex").is("WOMAN"));
		 mongoTemplate.updateFirst(query6, Update.update("name", "花花"), "student");
		 
		 
		 Query query7 = new Query();
		 query7.addCriteria(Criteria.where("sex").is("WOMAN"));
		 mongoTemplate.remove(query7, "student");
		 
		 System.out.println(findAll3);
		 return findAll;
	}
	
	
	
	
	
	
	@RequestMapping(value="/test")
	public Object test(){
		return "ok";
		
	}
	
	
	@RequestMapping(value="/link")
	public Article findOneByLink(@RequestParam("link") String link) {
		
		return articleDao.findOneByLink(link);
		
	}
	
	@RequestMapping(value="/update")
	public void updateByLink(@RequestBody Article article) {
		article.setUpdateTime(new Date());
		try {
			articleDao.update(article);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/remove")
	public void deleteByLink(String link) {
		articleDao.removeByLink(link);
	}
	
}
