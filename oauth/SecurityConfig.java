package kirin.barcodescanner.oauth;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import kirin.barcodescanner.Entity.MyRole;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Securiy 설정 활성화
public class SecurityConfig {
 
    private final CustomOAuth2UserService customOAuth2UserService;
 
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // h2-console 화면을 사용하기 위해 해당 옵션을 disable
        http.csrf().disable().headers().frameOptions().disable()
                .and()// URL 별 권환 관리 설정 (authorizeRequests()가 선언되어야만 anyMatchers옵션 사용가능)
                .authorizeRequests()
                // antMatchers를 통해 권환 관리 대상을 지정하고, URL,HTTP 메소드별 관리 가능
                .antMatchers("/","/home/**").permitAll() // "/"등 지정된 URL들은 permitAll() 옵션으로 전체 열람 권한 부여
                .antMatchers("/myPageMenu/**").hasRole(MyRole.USER.name()) // 해당 주소는 USER 권한을 가진 사람만 가능
                .anyRequest().authenticated() // 설정된 값들 이외 나머지 URL들은 모두 인증된 사용자(로그인한)들에게만 허용
                .and()
                .logout().logoutSuccessUrl("/") // 로그아웃 성공시 해당 주소로 이동
                .and()
                .oauth2Login() 
                .userInfoEndpoint() 
                .userService(customOAuth2UserService) 
                
                .and().defaultSuccessUrl("/", true);
        return http.build();}
}