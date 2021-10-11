package com.example.schoolmanagement.security;

import com.example.schoolmanagement.security.jwt.JwtTokenVerifier;
import com.example.schoolmanagement.security.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.example.schoolmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override

    protected void configure(HttpSecurity http) throws Exception {
//        http
////                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
////                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
////                .loginPage("/login")
////                .permitAll()
//                .defaultSuccessUrl("/courses", true)
//                .and()
//                .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/api-docs*", "/swagger*", "/swagger*/**").permitAll()
                .anyRequest()
                .authenticated();

    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        CustomUserDetails hawtsauce = CustomUserDetails.builder()
//                .username("hawtsauce")
//                .password(passwordEncoder.encode("Testing321"))
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//
//        CustomUserDetails admin = CustomUserDetails.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("Testing321"))
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        CustomUserDetails staff = CustomUserDetails.builder()
//                .username("staff")
//                .password(passwordEncoder.encode("Testing321"))
//                .authorities(STAFF.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                admin,
//                staff,
//                hawtsauce
//        );
//
//    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
}
