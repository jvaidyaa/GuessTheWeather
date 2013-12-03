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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;





public class CookieBean {
	//private static Log log = LogFactory.getLog(CookieBean.class);

	private String agentGuid;
	private String merchantId;
	private String customerGuid;
	private String customerCurrency;
	private String customerCountry;
	private String mCareEnv;
	
	
	public void setCookies(PageContext context){
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		setCookies(request, response);
	}
	
	public void setCookies(HttpServletRequest request, HttpServletResponse response  ) {
		System.out.println("Inside::setCookies");
		try {
			
			merchantId = AppCookie.fetchCookieValue(request, Utility.merchantCookieName);
			customerGuid = AppCookie.fetchCookieValue(request,	Utility.customerGuidCookieName);
			customerCurrency = AppCookie.fetchCookieValue(request,	Utility.customerCurrencyCookieName);
			customerCountry = AppCookie.fetchCookieValue(request, Utility.customerCountryCookieName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Agent: " + getAgentGuid() + " Merchant:" + getMerchantId()
				+ " customer:" + getCustomerGuid());
	}

	/**
	 * @return the agentGuid
	 */
	public String getAgentGuid() {
		return agentGuid;
	}

	/**
	 * @param agentGuid
	 *            the agentGuid to set
	 */
	public void setAgentGuid(String agentGuid) {
		this.agentGuid = agentGuid;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId
	 *            the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return the customerGuid
	 */
	public String getCustomerGuid() {
		return customerGuid;
	}

	/**
	 * @param customerGuid
	 *            the customerGuid to set
	 */
	public void setCustomerGuid(String customerGuid) {
		this.customerGuid = customerGuid;
	}

	/**
	 * @return the customerCurrency
	 */
	public String getCustomerCurrency() {
		return customerCurrency;
	}

	/**
	 * @param customerCurrency
	 *            the customerCurrency to set
	 */
	public void setCustomerCurrency(String customerCurrency) {
		this.customerCurrency = customerCurrency;
	}

	/**
	 * @return the customerCountry
	 */
	public String getCustomerCountry() {
		return customerCountry;
	}

	/**
	 * @param customerCountry
	 *            the customerCountry to set
	 */
	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

}