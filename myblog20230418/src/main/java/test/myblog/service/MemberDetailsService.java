package test.myblog.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import test.myblog.model.Member;
import test.myblog.repository.MemberRepository;

@Service
public class MemberDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberRepository mr;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		List<Member> lm = mr.findMemberByUsername(username);
		System.out.println(username);
		System.out.println(lm.get(0));
		Optional<Member> om = lm.stream()
								  .filter(m -> m.getUsername().equals(username))
								  .findFirst();
		if(!om.isPresent()) {
			throw new UsernameNotFoundException("Not found!");
		}
		
		String authority = lm.get(0).getAuthority();
		String password = lm.get(0).getPassword();
		System.out.println(password);
		return new User(username, password, createAuthorities(authority));
	}
	
	public static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
	private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

	public static Collection<? extends GrantedAuthority> createAuthorities(String authority) {
		if (authority.contains("admin")) {
			return ADMIN_ROLES;
		}
		return USER_ROLES; 
	}

}
