package com.b2b.configuration;

import com.b2b.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String loginPage = "/login";
        String logoutPage = "/logout";

        http.
                authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/vendor/**").permitAll()
                .antMatchers("/vendor/animate/**").permitAll()
                .antMatchers("/vendor/aos/**").permitAll()
                .antMatchers("/vendor/bootstrap/css/**").permitAll()
                .antMatchers("/vendor/bootstrap/js/**").permitAll()
                .antMatchers("/vendor/counterup/**").permitAll()
                .antMatchers("/vendor/font-awesome/css/**").permitAll()
                .antMatchers("/vendor/font-awesome/fonts/**").permitAll()
                .antMatchers("/vendor/icofont/fonts/**").permitAll()
                .antMatchers("/vendor/icofont/**").permitAll()
                .antMatchers("/vendor/ionicons/css/**").permitAll()
                .antMatchers("/vendor/ionicons/fonts/**").permitAll()
                .antMatchers("/vendor/isotope-layout/**").permitAll()
                .antMatchers("/vendor/owl.carousel/**").permitAll()
                .antMatchers("/vendor/venobox/**").permitAll()
                .antMatchers("/vendor/owl.carousel/assets/**").permitAll()
                .antMatchers("/vendor/jquery/**").permitAll()
                .antMatchers("/vendor/jquery.easing/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/img/clients/**").permitAll()
                .antMatchers("/img/intro-carousel/**").permitAll()
                .antMatchers("/img/portfolio/**").permitAll()
                .antMatchers("/vendor/waypoints/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers(loginPage).permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/loginAgain").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/addDemand").permitAll()
                .antMatchers("/productAdded").permitAll()
                .antMatchers("/demandListDisplay").permitAll()
                .antMatchers("/supplyListDisplay").permitAll()
                .antMatchers("/seeMore").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage(loginPage)
                .loginPage("/")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
                .logoutSuccessUrl("/index").and().exceptionHandling();

    }


}
