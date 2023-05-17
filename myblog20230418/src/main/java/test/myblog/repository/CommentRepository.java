package test.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	
	@Query(value = "SELECT * FROM comment WHERE AID = :AID", nativeQuery = true)
    List<Comment> getCommentListByAID(@Param("AID") Integer AID);
	
	@Query(value="SELECT * FROM Comment ORDER BY CID DESC LIMIT 1" ,nativeQuery=true)
	Comment getCountOfCommentTable();
	
	@Query(value="SELECT CLike FROM Comment WHERE CID = :CID", nativeQuery=true)
	Integer getCurrentCommentLikes(Integer CID);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Comment SET CLike = :newCLikes WHERE CID = :CID", nativeQuery=true)
	void setNewCommentLikes(Integer newCLikes, Integer CID);
}
