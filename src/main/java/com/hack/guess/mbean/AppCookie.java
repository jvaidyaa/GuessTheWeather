/*
 * AMERICA ONLINE CONFIDENTIAL INFORMATION
 *
 *
 * Copyright (c) 2010 AOL LLC
 *
 * All Rights Reserved.  Unauthorized reproduction, transmission, or
 * distribution of this software is a violation of applicable laws.
 *
 *
 */
package com.hack.guess.mbean;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hack.guess.mbean.Utility;


/**
 *
 * @author kevin01obrien
 */
public class AppCookie {
	//private static Log log = LogFactory.getLog(AppCookie.class);
	
	private static String COOKIE_PATH = "/mCareBeta";
	
    
    /**
     * Creates a new instance of AppCookie
     */
    public AppCookie() {
    }
    
    /*
     * addCookie method creates a cookie and adds it to the Http response
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int seconds){
    	System.out.println("Inside addCookie... name:<" + name + "> value<"+ value+ "> " );
    	if (value == null || value.isEmpty()){
    		return;
    	}
    	seconds = 2*3600;
    	String eValue = new String(value.getBytes());   	
    	
    	System.out.println("Setting cookie in response name:" + name + " value:"+ value+ " > eValue<" + eValue + ">");
    	Cookie storedCookie = getCookie(request, name);
    	if (storedCookie != null){
    		storedCookie.setValue(eValue);
    	}
    	else{
    		storedCookie = new Cookie(name, eValue);
    	}
    	storedCookie.setPath(COOKIE_PATH + "/"); //for other browsers
    	storedCookie.setSecure(request.isSecure());
    	storedCookie.setMaxAge(seconds); 
    	storedCookie.setVersion(1);
        response.addCookie(storedCookie);       
    }
    
    /*
     * deleteCookie method destroys a cookie on the browser
     */
    public static void deleteCookie (HttpServletRequest request, HttpServletResponse response, String name){
    	System.out.println("Inside deleteCookie... name:" + name );
    	Cookie storedCookie = getCookie(request, name);
    	if (storedCookie != null){
    		storedCookie.setValue("");
            storedCookie.setMaxAge(0);    // kill the cookie when now...
            storedCookie.setPath(COOKIE_PATH + "/"); //for other browsers
            response.addCookie(storedCookie);
            //fix for remove chrome's cookies
            Cookie cookieChrome = (Cookie) storedCookie.clone();
            cookieChrome.setPath(COOKIE_PATH);//akvel: for chrome!!
            response.addCookie(cookieChrome);
            System.out.println("Removing cookie in response name:" + name );
    	}
    	else{
    		System.out.println("cookie <" + name +"> doesn't exist ...");
    	}
    	
    }
    /*
     * fetchCookieValue method pulls value for cookie from Http request.  If the
     * cookie's not there, it returns null for the cookie value...
     */
    public static String fetchCookieValue(HttpServletRequest request, String name){
    	System.out.println("Inside fetchCookieValue ... name=" + name);
        String value = null;
        Cookie cookie = getCookie(request, name);
    	if(cookie != null) {
            value = cookie.getValue(); 
            if (Utility.isNotNullOrEmpty(value)){
            	try{
            		value =  value;
                }catch (Exception e) {
                	System.out.println("Decrypt cookie value failed ... \n");
                	e.printStackTrace();
                }
            }
        }
        return value;
    }
    
    
    public static Cookie getCookie(HttpServletRequest request, String name){
    	System.out.println("Inside getCookie ... ");
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if(cookies [i].getName().equals(name)) {
                    cookie = cookies[i];
                    break;
                }
            }
        }
        return cookie;
    }
}
