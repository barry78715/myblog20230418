package test.myblog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.model.Article;
import test.myblog.model.Comment;
import test.myblog.model.Member;
import test.myblog.repository.ArticleRepository;

@Service
public class ArticleService {
	
	@Autowired
	ArticleRepository ar;
	
	public Article createArticle(Article a) {
		Article ca = ar.save(a);
		return ca;
	}
	
	public Article getArticle(Integer AID) {
		Article ga = ar.findById(AID).get();
		return ga;
	}
	
	public Article getLatestArticleId() {
		return ar.getCountOfArticleTable();
	}
	public void addOneArticleView(Integer AID) {
		Integer currentViews = ar.getCurrentViews(AID);
		ar.setNewViews(currentViews + 1, AID);
	}
	
	public void addOneArticleLike(Integer AID) {
		Integer currentLikes = ar.getCurrentLikes(AID);
		ar.setNewLikes(currentLikes + 1, AID);
	}
	
	public void substractOneArticleLike(Integer AID) {
		Integer currentLikes = ar.getCurrentLikes(AID);
		ar.setNewLikes(currentLikes - 1, AID);
	}
	
	public List<Article> getArticlesByMID(Integer id) {
		return ar.findArticleByMID(id);
		
	}
	public Integer getPostCountByMID(Integer id) {
		return ar.countPostByMID(id);
	}
	
	
	public List<String> getArticleImgListByMID(Integer id){
		List<String> ailByMID = ar.findArticleByMID(id).stream()
							  .map(a -> a.getAContent())
							  .map(ac -> ac.substring(ac.indexOf("src=\"") + 5, ac.indexOf("\"", ac.indexOf("src=\"") + 5)))
							  .collect(Collectors.toList());

		return ailByMID;
		
	}
	
	public List<Article> getArticleListByTID(Integer TID){
		return ar.findArticleListByTID(TID);
	}
}
