package test.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.model.Article;


public interface CommentLikesRepository extends JpaRepository<Article, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM CommentLikes WHERE MID = :MID AND CID = :CID", nativeQuery = true)
    void deleteByMIDandCID(@Param("MID") Integer MID, @Param("CID") Integer CID);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO CommentLikes (MID, CID) VALUES (:MID, :CID)", nativeQuery = true)
    void createByMIDAndCID(@Param("MID") Integer MID, @Param("CID") Integer CID);
    
    @Query(value = "SELECT count(*) FROM CommentLikes WHERE CID = :CID", nativeQuery = true)
    Integer getLikeCountByCID(@Param("CID") Integer CID);
    
    @Query(value = "SELECT count(*) FROM CommentLikes WHERE MID = :MID AND CID = :CID", nativeQuery = true)
    Integer checkIfMemberLikedComment(@Param("MID") Integer MID, @Param("CID") Integer CID);
    
    
}
