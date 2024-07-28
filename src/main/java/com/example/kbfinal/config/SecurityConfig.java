package com.example.kbfinal.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 대칭키
    @Value("${symmetric.key}")
    private String symmetrickey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        // 인가된 path를 지정해줌
                        authorizeRequests
                                .requestMatchers("/", "/actuator/**", "/check/**", "/h2-console/**", "/home", "/register", "/css/**", "/js/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/login", "/user/**").permitAll()
                                .anyRequest().authenticated()
                )
                // 로그인을 통해 인증하는 내용
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                // 세션 고정을 통해 인증 정보를 유지
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionFixation().changeSessionId()   // 로그인 후에도 세션 유지

                )
                // 해당 헤더를 통해 보안을 강화하여 전달할 수 있다.
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/h2-console/**").disable()  // CSRF 보호 비활성화 경로 설정
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

        ;

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // decoder 구현
    // AesBytesEncryptor 사용을 위한 Bean등록
    @Bean
    public AesBytesEncryptor aesBytesEncryptor() {
        return new AesBytesEncryptor(symmetrickey,"70726574657374");
    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
