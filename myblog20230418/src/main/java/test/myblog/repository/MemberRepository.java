package test.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	@Query(value="SELECT m FROM Member m WHERE m.username = :username")
	List<Member> findMemberByUsername(@Param("username") String username);
	
	@Query(value = "SELECT id FROM Member WHERE username = ?1", nativeQuery=true)
	Integer findIDByName(String username);
	
}
