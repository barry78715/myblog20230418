package test.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/post", "/memberHP/**").permitAll()
			.antMatchers("/login", "/register").anonymous();
			
		http.formLogin()
			.loginProcessingUrl("/perform_login")
			.loginPage("/login")
			.defaultSuccessUrl("/");
			
		http.logout()
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/")
            .permitAll();
		return http.build();
	}
}
