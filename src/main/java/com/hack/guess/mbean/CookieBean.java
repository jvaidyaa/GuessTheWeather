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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;





public class CookieBean {
	//private static Log log = LogFactory.getLog(CookieBean.class);

	private String dop;
	private String email;
	private String zip;
	private String prediction;
	
	private static final String INSERT_WEATHER = "INSERT INTO weather_data (weather_id, email_address, zip_code, weather_value, weather_date, created_by, created_dt)" +
			" VALUES (weather_sq.nextval, :email_address, :zip_code, :weather_value, :weather_date, :created_by, sysdate) ";

	//private static Log LOG = LogFactory.getLog(SavePredictionMB.class);
	
	private static BasicDataSource dataSource = null;
	
	public static BasicDataSource getDataSource() throws Exception {
		System.out.println("inside getDataSource");
		if (dataSource == null) {

			Context initCtx = new InitialContext();
			System.out.println("Got initial context");
			dataSource = (BasicDataSource) initCtx
					.lookup("java:comp/env/jdbc/ObiConfigDB");
			if (null == dataSource.getUsername()) {
				System.out.println("username is null");
				dataSource.setUsername("obi_redenv");
				dataSource.setPassword("obir3d3nv");
				//setDataSourceProperties();
			}
		}
		return dataSource;
	}


	
	
	public void setCookies(PageContext context){
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		setCookies(request, response);
	}
	
	public void setCookies(HttpServletRequest request, HttpServletResponse response  ) {
		System.out.println("Inside::setCookies");
		try {
			
			dop = AppCookie.fetchCookieValue(request, "dop");
			email = AppCookie.fetchCookieValue(request,	"email");
			zip = AppCookie.fetchCookieValue(request,	"zip");
			prediction = AppCookie.fetchCookieValue(request,"prediction");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("dop: " + getDop() + " email:" + getEmail()
				+ " zip:" + getZip());
		
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getDataSource().getConnection();
			
			stmt = conn.prepareStatement(INSERT_WEATHER);
			
			stmt.setString(1, email);
			stmt.setString(2, zip);
			stmt.setString(3, prediction);	
			stmt.setString(4,  dop);
			stmt.setString(5, "insert");
			System.out.println("after insert");
			
			stmt.executeUpdate();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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