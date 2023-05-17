package test.myblog.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.myblog.model.Follower;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer>{
	//粉絲數
	@Query(value = "SELECT count(*) FROM Follower WHERE MID = :id", nativeQuery = true)
    Integer countFollowed(@Param("id") Integer id);
	
	//追蹤中
	@Query(value = "SELECT count(*) FROM Follower WHERE FollowerID = :id", nativeQuery = true)
    Integer countFollowing(@Param("id") Integer id);
	
	//粉絲名單
    @Query(value="SELECT FollowerID FROM Follower WHERE MID = ?1", nativeQuery = true)
    List<Integer> findFollowedListByID(Integer id);
    
    //追蹤名單
  	@Query(value = "SELECT MID FROM Follower WHERE FollowerID = ?1", nativeQuery = true)
  	List<Integer> findFollowingListByID(Integer id);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Follower WHERE MID = :MID AND FollowerID = :FollowerID", nativeQuery = true)
    void deleteByMIDAndFollowerID(@Param("MID") Integer MID, @Param("FollowerID") Integer FollowerID);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Follower (MID, FollowerID) VALUES (:MID, :FollowerID)", nativeQuery = true)
    void createByMIDAndFollowerID(@Param("MID") Integer MID, @Param("FollowerID") Integer FollowerID);
    
    @Query(value = "SELECT count(*) FROM Follower WHERE FollowerID = :id AND MID = :MID", nativeQuery = true)
    Integer checkIfMemberIsFollower(@Param("id") Integer id, @Param("MID") Integer MID);
}

