package com.cuzz.security.config;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: cuzz
 * @Date: 2018/9/29 12:51
 * @Description:
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        // 定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        // 开启登入功能，如果权限就来到登入页面
        http.formLogin();
        // 开启注销功能,成功注销后回到首页
        http.logout().logoutSuccessUrl("/");
    }

    // 定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("cuzz").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("cuxx").password("123456").roles("VIP3");
    }

}
