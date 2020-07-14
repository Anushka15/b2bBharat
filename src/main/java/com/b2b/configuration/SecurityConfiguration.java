package com.b2b.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    private final String USERS_QUERY = "select email, password, active from user where email=?";
    private final String ROLES_QUERY = "select u.email, r.role from user u inner join user_role ur on (u.id = ur.user_id) inner join role r on (ur.role_id=r.role_id) where u.email=?";

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/img/**",
            "/vendor/**",
            "/",
            "/index**",
            "/login",
            "/refistration"

    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(ROLES_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
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
                .antMatchers("/index/**").permitAll()
                .antMatchers("/#about").permitAll()
                .antMatchers("/index/**").permitAll().anyRequest()
                .authenticated().and().csrf().disable()
                .formLogin().loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/index")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60*60)
                .and().exceptionHandling().accessDeniedPage("/access_denied");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);

        return db;
    }
}