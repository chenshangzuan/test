/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
public class AdminServerConfig extends WebSecurityConfigurerAdapter {

    private final AdminServerProperties adminServer;

    /**
     * Instantiates a new Security secure config.
     *
     * @param adminServer the admin server
     */
    public AdminServerConfig(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        final String adminServerContextPath = this.adminServer.getContextPath();
        successHandler.setDefaultTargetUrl(adminServerContextPath+"/");

        http.authorizeRequests()
                .antMatchers(adminServerContextPath + "/assets/**").permitAll() // <1>
                .antMatchers(adminServerContextPath + "/login").permitAll()
//                .antMatchers(adminServerContextPath + "/instances").permitAll()
//                .antMatchers(adminServerContextPath + "/instances/*").permitAll()
//                .antMatchers(adminServerContextPath + "/actuator/**").permitAll()
                .anyRequest().authenticated() // <2>
                .and()
                .formLogin().loginPage(adminServerContextPath + "/login").successHandler(successHandler).and() // <3>
                .logout().logoutUrl(adminServerContextPath + "/logout").and()
                .httpBasic().and() // <4>
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // <5>
                .ignoringAntMatchers(
                        adminServerContextPath + "/instances",  // <6>
                        adminServerContextPath + "/instances/*",  // <6>
                        adminServerContextPath + "/actuator/**"  // <7>
                )
                .and()
                .rememberMe().key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600);

    }
}
