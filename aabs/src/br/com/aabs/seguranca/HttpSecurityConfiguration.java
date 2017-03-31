package br.com.aabs.seguranca;

import javax.enterprise.event.Observes;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

public class HttpSecurityConfiguration {
	
	public void onInit(@Observes SecurityConfigurationEvent event) {
        SecurityConfigurationBuilder builder = event.getBuilder();
        
        builder
        .http()
            .allPaths()
                .authenticateWith()
                    .form()
                        .authenticationUri("/login.xhtml")
                        .loginPage("/login.xhtml")
                        .errorPage("/error.xhtml")
                        .restoreOriginalRequest()
            
            .forPath("/login.xhtml").unprotected()
            .forPath("/publico/*").unprotected()
            .forPath("/javax.faces.resource/*").unprotected()
            .forPath("/css/*").unprotected()
            .forPath("/js/*").unprotected()
            
            .forPath("/logout")
                .logout()
                	.redirectTo("/login.xhtml");
    }
}