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

                    author.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                    author.requestMatchers(HttpMethod.GET,"/api-events/**").permitAll();
                    author.requestMatchers(HttpMethod.GET,"/api-events/account").hasRole("EO");

                    author.requestMatchers(HttpMethod.DELETE,"/api-events/**").hasRole("EO");


                    author.requestMatchers(HttpMethod.POST,"/api-events/**").hasRole("EO");
                    author.requestMatchers(HttpMethod.GET,"/api/v1/vnpay/**").permitAll();
                    author.requestMatchers(HttpMethod.POST,"/api-sponsor/**").hasRole("SPONSOR");
                    author.requestMatchers(HttpMethod.POST,"/api-sponsor/{id}").hasRole("SPONSOR");
                    author.requestMatchers(HttpMethod.GET,"/api-sponsor/**").permitAll();
                    author.requestMatchers("/api-feedbacks/**").hasRole("EO");
                    author.requestMatchers("/api-feedbackanswer/**").hasRole("EO");
                    author.requestMatchers("/feedbackQuestions/**").hasRole("EO");
                    author.requestMatchers("/api-visitor-answer/**").hasRole("VISITOR");
                    author.requestMatchers(HttpMethod.POST,"/api-ticket/create_ticket_order").permitAll();
                    author.requestMatchers(HttpMethod.PUT, "/api-ticket/update/**").permitAll();
                    author.requestMatchers("/api-ticket/**").permitAll();
                    author.requestMatchers("/api-cart/**").permitAll();
                    author.requestMatchers("/api-accounts/**").hasRole("ADMIN");
                    author.requestMatchers("/api-event-operators/**").permitAll();
                    author.requestMatchers(HttpMethod.POST, "/api-attendance/createListByEvent/**").permitAll();
                    author.requestMatchers(HttpMethod.POST,"/api-tickets/**").permitAll();
                    author.requestMatchers("/api-feedback-questions-event/**").permitAll();
                    author.requestMatchers("/api-auth-resetpassword/**").permitAll();
                    author.anyRequest().authenticated();
                })
                .addFilterBefore(customFilterSecurity, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
