package io.hotdogger.login.config;

import io.hotdogger.login.security.JwtRequestFilter;
import io.hotdogger.login.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * A class used to override Spring Security's authentication methods.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Connects to custom UserDetailsService for getting user or "customer" details
     */
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * Connects to JwtRequestFilter for getting supporting the JWT being passed through
     */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Used to prevent an error with the AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Define the BCryptPasswordEncoder as a bean in the configuration for encrypting passwords
     *
     * @return the BCryptPasswordEncoder()
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CorsConfigurer to handle the CORS and allow localhost:3000 (front-end app) to access this
     * back-end API and allow it to send various requests to it too.
     *
     * @return the CorsMappings
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT")
                        .allowedOrigins("*");
            }
        };
    }

    /**
     * For overriding the DAO authenticationProvider that connects to the custom database with email
     * and password
     *
     * @return the daoAuthProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(myUserDetailsService);
        daoAuthProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthProvider;
    }

    /**
     * The authentication manager that takes in the DaoAuthentication provider and configures it
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Used to override Spring security's http configure method, which it has custom authentication
     * based on user roles and the new endpoint "/login"
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/players/register").permitAll() //POST only to register, accessible to all
                .antMatchers(HttpMethod.GET, "/saves/**").permitAll()
                .antMatchers(HttpMethod.POST, "/saves").permitAll()
                .antMatchers(HttpMethod.GET, "/*").permitAll()
                .anyRequest().authenticated() //all other endpoints protected, like customers
                .and().sessionManagement() // related to sessions...
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}