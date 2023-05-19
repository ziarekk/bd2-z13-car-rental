package z13.rentivo.security;


import z13.rentivo.views.LoginView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(userDetailsService.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers("/images/**").permitAll();

        super.configure(http);

        setLoginView(http, LoginView.class);
    }
}

