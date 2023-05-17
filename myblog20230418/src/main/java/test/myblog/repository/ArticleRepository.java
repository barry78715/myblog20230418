package test.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import java.util.List;

import test.myblog.model.Article;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>{
	@Query(value="SELECT * FROM Article ORDER BY AID DESC LIMIT 1" ,nativeQuery=true)
	Article getCountOfArticleTable();
	
	@Query(value="SELECT AView FROM Article WHERE AID = :AID", nativeQuery=true)
	Integer getCurrentViews(Integer AID);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Article SET AView = :newViews WHERE AID = :AID", nativeQuery=true)
	void setNewViews(Integer newViews, Integer AID);
	
	@Query(value="SELECT ALike FROM Article WHERE AID = :AID", nativeQuery=true)
	Integer getCurrentLikes(Integer AID);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Article SET ALike = :newLikes WHERE AID = :AID", nativeQuery=true)
	void setNewLikes(Integer newLikes, Integer AID);
	
	//計算id發文數
	@Query(value = "SELECT * FROM Article WHERE MID = :id", nativeQuery = true)
    List<Article> findArticleByMID(@Param("id") Integer id);
	
	//計算id發文數
	@Query(value = "SELECT count(*) FROM Article WHERE MID = :id", nativeQuery = true)
    Integer countPostByMID(@Param("id") Integer id);
	
	@Query(value = "SELECT * FROM Article WHERE TID = :TID", nativeQuery = true)
    List<Article> findArticleListByTID(@Param("TID") Integer TID);
}
