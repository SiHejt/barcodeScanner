package kirin.barcodescanner.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import kirin.barcodescanner.Entity.MyRole;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests()
                .antMatchers("/", "/home/**", "/images/**", "/css/**", "/custom-login", "/cart.png", "/review.png", "/search.png", "/pyeonlilogo_fin2_r_update2_fin.png", "/google.png", "/searchresult.png").permitAll()
                .antMatchers("/myPageMenu/**").hasRole(MyRole.USER.name())
                .anyRequest().authenticated()
            .and()
            .logout().logoutSuccessUrl("/")
            .and()
            .oauth2Login()
                .loginPage("/custom-login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
            .and()
            .defaultSuccessUrl("/", true);

        return http.build();
    }
}