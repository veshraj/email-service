package com.test.emailservice.modules.auth.configs;


import com.test.emailservice.modules.auth.filters.JwtFilter;
import com.test.emailservice.modules.auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthService authService;

    @Autowired
    JwtFilter jwtFilter;

    /**
     * @Description this function overrides the default athentication service provider and you can change the
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authenticate","/api/users/register").permitAll()
                .antMatchers("/api/authenticate/otp").permitAll()
                .antMatchers("/swagger-ui.html", "/v3/api-docs", "/swagger-ui", "/swagger-ui/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /**
         * @Description this will set fiter before each request
         */
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        /**
         * @Description this will set headers automatically
         */
        http.headers().cacheControl();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
