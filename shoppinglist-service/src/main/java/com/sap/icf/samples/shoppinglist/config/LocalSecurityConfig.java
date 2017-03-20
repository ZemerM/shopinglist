package com.sap.icf.samples.shoppinglist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import com.sap.icf.samples.shoppinglist.utils.JwtGenerator;
import com.sap.icf.samples.shoppinglist.utils.UserInfoWrapper;
import com.sap.xs2.security.commons.SAPOfflineTokenServices;
import com.sap.xs2.security.container.UserInfo;
import com.sap.xs2.security.container.UserInfoException;


/**
 * This config provides a reconfiguration of the security configation capable of running with a xsuaa instance, for unit tests and local execution.
 * It uses a key pair for the JWT token generation/validation stored in local files.
 * To test using a http client, you can pass one of the predefined Authorization Bearer ... headers found in src/main/resources/jwtTokensForTesting.txt.
 * 
 * @author D020038
 *
 */
@Profile("default")
@Configuration
public class LocalSecurityConfig {

	public static final String XSAPPNAME = "test-xsappname";
	
	@Bean
    public SAPOfflineTokenServices sapOfflineTokenServices() {
        JwtGenerator jwtGenerator = new JwtGenerator();
        SAPOfflineTokenServices sapOfflineTokenServices = new SAPOfflineTokenServices();
        sapOfflineTokenServices.setTrustedClientId(jwtGenerator.getClientId());
        sapOfflineTokenServices.setTrustedIdentityZone(jwtGenerator.getIdentityZone());
        sapOfflineTokenServices.setVerificationKey(jwtGenerator.getPublicKey());
        sapOfflineTokenServices.afterPropertiesSet();
        return sapOfflineTokenServices;
    }

	@Bean
	@Scope(value=WebApplicationContext.SCOPE_REQUEST,proxyMode=ScopedProxyMode.TARGET_CLASS)
	public UserInfo userInfoBean() {
		try {
			return new UserInfoWrapper(XSAPPNAME);
		} catch (UserInfoException e) {
			return null;
		}
	}
}