package com.zp.springsecurity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService());
    }

    @Bean
    public UserDetailsService myUserDetailsService() {

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        String[][] usersGroupsAndRoles = {
                {"tom", "tom", "ROLE_USER"},
                {"admin", "admin", "ROLE_ADMIN"},
        };

        for (String[] user : usersGroupsAndRoles) {
            List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
            logger.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");
            inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
        }


        return inMemoryUserDetailsManager;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        /** 放开静态资源访问，不然页面上的css无法显示
         * 在security里这样配置就行了，不用像传统的配置继承WebMvcConfigurationSupport再addResourceHandler */
        web.ignoring().antMatchers("/plugins/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /** 去掉csrf过滤器
                 * 如果禁用，可能受到csrf攻击
                 * 一般不会加上这句，会在页面上加上csrf*/
//                .csrf().disable()
                /** authorizeRequests表示需要认证的request请求*/
                .authorizeRequests()
                /** 设置只有ROLE_USER的权限才能访问/index页面*/
                .antMatchers("/").hasRole("USER")
                /** 设置只有ROlE_ADMIN的权限才能访问/content页面*/
                .antMatchers("/content").hasRole("ADMIN")
                /** anyRequest()表示除上面之外的所有请求都需要认证*/
                .anyRequest().authenticated()
                .and()
                /** 默认login画面 不加下面这句会导致访问报错403*/
//                .formLogin().permitAll()
                /** 自定义login画面*/
                .formLogin().loginPage("/mylogin")
                /** 登录成功后的页面*/
                .successForwardUrl("/")
                /** 这里的loginProcessingUrl值需要与自定义login画面里form表单的action地址一致
                 * 只要与form的action地址一致就行，可以是任意值
                 * 如果不指定loginProcessingUrl，则默认为loginPage设置的值,这里也就是/mylogin，
                 * 这时就会造成在login页面点登录按钮时就无限跳转到login页面
                 * 如果loginProcessingUrl和loginPage都不设置，则默认值都为/login*/
                .loginProcessingUrl("/login33")
//                .failureForwardUrl("/error")
                .permitAll();
//                .and()
        /** httpBasic()表示支持httpBasic认证，在postman里可以选择basic auth输入用户名和密码来访问*/
//                .httpBasic();



    }

}
