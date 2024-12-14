package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig {
	

	@Bean
    public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.formLogin(login -> login
	        	.loginPage("/admin/signin") // ログインページをカスタム
	        	.failureUrl("/admin/signin?error")
	        	.defaultSuccessUrl("/admin/contacts")
	        	.usernameParameter("email").passwordParameter("password")
	        	.permitAll())
	        	.authorizeHttpRequests(authz -> authz
	        			.requestMatchers("/admin/signup", "/admin/signin").permitAll()
	        			.anyRequest().authenticated()
	        		)
	        	.logout(logout -> logout
	        			.logoutUrl("/admin/logout")
	        			.logoutSuccessUrl("/admin/signin") // ログアウト後の遷移先
	        			.invalidateHttpSession(true)
	        			.deleteCookies("JSESSIONID")
	        		);

	        return http.build();
	    }

}

