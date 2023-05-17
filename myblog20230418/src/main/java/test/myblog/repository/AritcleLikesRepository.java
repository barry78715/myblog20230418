package test.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.model.ArticleLikes;

public interface AritcleLikesRepository extends JpaRepository<ArticleLikes, Integer>{
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM ArticleLikes WHERE MID = :MID AND AID = :AID", nativeQuery = true)
    void deleteByMIDAndAID(@Param("MID") Integer MID, @Param("AID") Integer AID);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO ArticleLikes (MID, AID) VALUES (:MID, :AID)", nativeQuery = true)
    void createByMIDAndAID(@Param("MID") Integer MID, @Param("AID") Integer AID);
    
	@Query(value = "SELECT count(*) FROM ArticleLikes WHERE AID = :AID", nativeQuery = true)
    Integer getLikeCountByAID(@Param("AID") Integer AID);
	
	@Query(value = "SELECT count(*) FROM ArticleLikes WHERE MID = :MID AND AID = :AID", nativeQuery = true)
	Integer checkIfMemberLikedArticle(@Param("MID") Integer MID, @Param("AID") Integer AID);
}

