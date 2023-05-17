package test.myblog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import test.myblog.model.Member;
import test.myblog.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository mr;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember(Member m) {
		Member ms = new Member();
		ms.setId(m.getId());
		ms.setAuthority(m.getAuthority());
		ms.setName(m.getName());
		ms.setUsername(m.getUsername());
		ms.setPassword(passwordEncoder.encode(m.getPassword()));
		ms.setGender(m.getGender());
		ms.setBirthday(m.getBirthday());
		ms.setcTime(m.getcTime());
		ms.setlTime(m.getlTime());
		ms.setPic(m.getPic());
		return mr.save(ms);	
	}
	public Member getMemberByUsername(String username) {
		return mr.findMemberByUsername(username).get(0);
		
	}
	
	public Integer getIDByUsername(String username) {
		return mr.findIDByName(username);
	}
	
	public Optional<Member> getMemberByID(Integer id) {
		return mr.findById(id);
	}
}
