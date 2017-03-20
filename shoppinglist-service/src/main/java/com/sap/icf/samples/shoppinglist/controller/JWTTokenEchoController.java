package com.sap.icf.samples.shoppinglist.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.xs2.security.container.UserInfo;
import com.sap.xs2.security.container.UserInfoException;

/**
 * The class <code>JWTTokenEchoController</code> is used for echoing the JWT Token to the UI. The token is usually hidden away through the approuter but
 * is sometimes needed for testing the ODATA Service through Postman once it has activated security. 
 * As this is a potential security issue, the coding is commented out and should only be activated when analyzing concrete security issues. 
 * 
 * Do not activate this coding when in productive use!!!
 * 
 * @version   20.09.2016
 * @author    D020038
 */
@RestController
public class JWTTokenEchoController {

//	@Autowired
//	private UserInfo userInfo;
//	
//	@RequestMapping("/jwtecho")
//    public String getJWTEcho( @RequestHeader("Authorization") String authorization ) {
//	    
//        try {
//			return "<!DOCTYPE HTML>" +
//			"<html>" +
//			"<head><title>JWT Echo</title></head>" +
//			"<body>" +
//			"Please decode the JWT token <a href = \"https://jwt.io\" target=\"_new\">here</a><br><br>" +
//			"<table border=1><tr><th>Field</th><th>Value</th></tr>" +
//			"<tr><td>Logon Name</td><td>" +userInfo.getLogonName() + "</td><tr>" +
//			"<tr><td>Identity Zone</td><td>" +userInfo.getIdentityZone() + "</td><tr>" +
//			"<tr><td>Expiration Date</td><td>" +userInfo.getExpirationDate() + "</td><tr>" +
//			"<tr><td>Authorization Header</td><td><textarea rows=\"10\" cols=\"100\">" + authorization + "</textarea></td><tr>" +
//			"</table>" +
//			"<br/>has local scope EditProduct = \"" + userInfo.checkLocalScope("EditProduct") + "\"" +
//			"</body>" +
//			"</html>";
//		} catch (UserInfoException e) {
//			return "";
//		}
//    }

}