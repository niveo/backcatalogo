package br.com.ams.backcatalogo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthWebSecurityConfiguration {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests().anyRequest().authenticated().and().cors().and().csrf().disable().formLogin()
				.disable().httpBasic().and().build();
	}

	@Bean
	InMemoryUserDetailsManager userDetailsService() {
		var user = User.withUsername("admin").password("{noop}admin").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}

}
