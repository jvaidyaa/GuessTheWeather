/*
 * AMERICA ONLINE CONFIDENTIAL INFORMATION
 *
 * $Author$
 *
 * Copyright (c) 2010 AOL LLC
 *
 * All Rights Reserved.  Unauthorized reproduction, transmission, or
 * distribution of this software is a violation of applicable laws.
 *
 * This class is a the base controller for the Merchant Care application
 * It acts as the Front/Application Controller design pattern and does various
 * common functions which are needed by individual page message beans like 
 * Checking merchant/customer Authentication , Error redirecting etc
 * 
 * Ideally this class should be extended by the individual page message beans.
 * The purpose is to move all the common code [not utility] related to page context requests 
 * to make the page development faster.
 *  
 * If extended this class will make a callback to your respective messagebean [Command Pattern]
 */
package com.hack.guess.mbean;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;





public abstract class AbstractMessageBean implements MessageBean {

	//private static Log log = LogFactory.getLog(AbstractMessageBean.class);

	public HttpServletRequest request;
	public HttpServletResponse response;
	public PageContext context;


	public String dop;
	public String email;
	public String zip;
	public String prediction;

	/**
	 * Gets the current HTTP request
	 * 
	 * @return the current HTTP request
	 */
	protected HttpServletRequest getRequest() {
		return request;
	}

	public void setDependencies(PageContext context) {
		System.out.println("Inside AbstractMessageBean::setDependencies");

		try {
			this.context = context;
			this.request = (HttpServletRequest) context.getRequest();
			this.response = (HttpServletResponse) context.getResponse();
			
			dop = request.getParameter("dop");
			email = request.getParameter("email");
			zip = request.getParameter("zip");
			prediction = request.getParameter("prediction");

		} catch (Exception e) {
			//e.printStackTrace();
         System.out.println("Error in setDependencies(): ");
		}
	}

	public String getDop() {
		return dop;
	}

	public void setDop(String dop) {
		this.dop = dop;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}
	

}