package EventManagement.Event.config;

import EventManagement.Event.filter.CustomFilterSecurity;
import EventManagement.Event.security.CustomAuthenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Khai báo cho spring boot biết custom security
public class SecurityConfig {



    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, CustomAuthenProvider customAuthenProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomFilterSecurity customFilterSecurity) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( author -> {
                    author.requestMatchers("/Auth/**").permitAll(); //Tự do không cần đăng nhập
                    author.requestMatchers("/api-sponsor/**").permitAll(); //Tự do không cần đăng nhập
                    author.requestMatchers("/api-visitor/**").permitAll();
                    author.requestMatchers(HttpMethod.POST,"/api-feedbacks/**").hasRole("EO");
                    author.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                    author.requestMatchers(HttpMethod.GET,"/api-events/**").permitAll();
                    author.requestMatchers(HttpMethod.POST,"/api-events/**").hasRole("EO");
                    author.anyRequest().authenticated();
                })
                .addFilterBefore(customFilterSecurity, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
