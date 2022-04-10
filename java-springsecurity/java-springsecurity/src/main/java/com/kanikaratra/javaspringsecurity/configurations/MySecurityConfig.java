package com.kanikaratra.javaspringsecurity.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    // here we define the type of security we would want basic/form based
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.httpBasic();
        //for form based authentication
        // http.formLogin();
        //http.authorizeRequests().anyRequest().authenticated();
        //only hello authenticated rest all acceptable
        http.authorizeRequests().antMatchers("/hello").authenticated();
        // same as above
        //   http.authorizeRequests().antMatchers("/hello").authenticated().anyRequest().permitAll();
        // only hello auheticated rest all are denied
        //http.authorizeRequests().antMatchers("/hello").authenticated().anyRequest().denyAll();

        //filter class
        http.addFilterBefore(new MeCustomSecurityFilter(), BasicAuthenticationFilter.class);
    }


    // user service and passwords are encorded on this layer- using default authentication provider
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//     //   BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        InMemoryUserDetailsManager inMemoryUserDetailsManager= new InMemoryUserDetailsManager();
//        UserDetails userDetails= User.withUsername("kanika").password(passwordEncoder.encode("ratra")).authorities("read").build();
//        inMemoryUserDetailsManager.createUser(userDetails);
//        // password encoding has become mandatory.
//        auth.userDetailsService(inMemoryUserDetailsManager).passwordEncoder(passwordEncoder);
//    }


    // using custom authentication provider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(myAuthenticationProvider);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
