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

	public String merchantCookieName = Utility.merchantCookieName;
	public String customerGuidCookieName = Utility.customerGuidCookieName;
	public String customerNameCookieName = Utility.customerNameCookieName;
	public String userCookieName = Utility.userCookieName;
	public String customerCurrencyCookieName = Utility.customerCurrencyCookieName;
	public String customerCountryCookieName = Utility.customerCountryCookieName;
	
	public String userLoginId;
	public String merchantId;
	public String customerGUID;
	public String customerCurrency;
	public String customerCountry;
	
	public boolean authorized;
	public String customerType;
	public List<String> userRights;
	
	private Boolean hasUpdateRight = false;
	private Boolean hasViewRight = false;


//	protected String fieldDisplays = "{}";
//	protected String fieldTips = "{}";
	
	protected String fieldsDisplayProperty = "{}";

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

			// error page
//			if (!verifyAuthentication()) {
//				String errorMessage = "";
//				if (Utility.isNotNullOrEmpty(getUserLoginId())) {
//					errorMessage = "User [" + getUserLoginId()
//							+ "] not authenticated.";
//				} else {
//					errorMessage = "User has no rights to view this page.";
//				}
//				redirectToErrorPage(errorMessage);
//			} else {
//				if(isShouldRedirectToHomePage()){
//					redirectToHomePage();
//				}else{
//					// STEP 3 ::: Call the logic to process the respective page.
//					String uri = request.getRequestURI();
//					Utility.printRequest(request, uri);
//
//					// load resurces
//					if (!(this instanceof ResourceMB)) {
//						loadRecource(this.getClass().getSimpleName());
//					}
//					documentBuilder();
//				}
//			}
		} catch (Exception e) {
			//e.printStackTrace();
         System.out.println("Error in setDependencies(): ");
		}
	}
	
	private boolean isShouldRedirectToHomePage(){
		boolean result = false;
		if(getMerchantId() != null){
			if(getMerchantId().isEmpty()){
				result = true;
			}
		}else{
			result = true;
		}
		
//		if(result){
//			if(getRequest().getServletPath().startsWith("/Home.jsp")){
//				return false;
//			}
//		}
		return result;
	}
	
//	private void loadRecource(String mbName) {
//		Map<String, ResourceFieldItem> config = new HashMap<String, ResourceFieldItem>();
//		String resURL = "/WEB-INF/resources/" + mbName + "Recource.xml";
//		try {
//			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
//			DocumentBuilder db;
//			db = f.newDocumentBuilder();
//
//			System.out.println("Try load page resource " + resURL);
//			InputStream is = context.getServletContext().getResourceAsStream(
//					resURL);
//
//			if (is == null)
//				return;
//			
//			JAXBContext jc = JAXBContext.newInstance(ResourceConfiguration.class);
//			Unmarshaller um = jc.createUnmarshaller();
//			ResourceConfiguration resConfig = (ResourceConfiguration)um.unmarshal(is);
//			if(resConfig != null){
//				for(ResourceFieldItem fItem : resConfig.getFieldItems()){
//					config.put(fItem.getName(), fItem);
//				}
//			}
//			
//		} catch (Exception e1) {
//			//e1.printStackTrace();
//			System.out.println("Error while parse MB resource file " + resURL, e1);
//
//		} finally {
//			Gson gson = new Gson();
//			fieldsDisplayProperty = gson.toJson(config);
//		}
//	}

//	private boolean verifyAuthentication() {
//		boolean valid = false;
//		System.out.println("Inside AbstractMessageBean::verifyAuthentication: Agent=" + getUserLoginId());
//		if (Utility.isNotNullOrEmpty(getUserLoginId())) {
//			if(Utility.isNotNullOrEmpty(getMerchantId())){
//				valid = true;
//			}else{
//				if (isUserHasValidRoles(getUserLoginId())) {
//					valid = true;
//				}
//			}
//		}
//		
//		setAuthorized(valid);
//		if (!valid) {
//			//Utility.resetCookies(request, response);
//		}
//		return valid;
//	}

//	private boolean isUserHasValidRoles(String userName) {
//		McareACSMapper mapper = new McareACSMapper();
//		// :TODO have to used more faster call
//		UserRoleBO role = mapper.getUserMerchantRoles(userName);
//		return !role.getUsersRoles().isEmpty();
//	}

//	public void redirectToErrorPage(String errorMessage) {
//		try {
//			if(request.getParameter("error_string") != null){
//				request.setAttribute("error_string", request.getParameter("error_string"));
//			}
//			request.setAttribute("error_string", errorMessage);
//			this.context.getServletContext().getRequestDispatcher("/Error.jsp")
//					.forward(request, response);
//		} catch (Exception e) {
//			//e.printStackTrace();
//         System.out.println("Error in redirectToErrorPage(): ", e);
//		}
//	}

	public String getCustomerGUID() {
		return customerGUID;
	}

//	public void redirectToHomePage() {
//		try {
//			response.sendRedirect(request.getContextPath() + "/Home.jsp");
//		} catch (Exception e) {
//			//e.printStackTrace();
//         System.out.println("Error in redirectToHomePage(): ", e);
//		}
//	}

//	public boolean checkRight(String resourceName) {
//		McareACSMapper mapper = new McareACSMapper();
//		return mapper.checkResource(getMerchantId(), getUserLoginId(), resourceName);
//	}
	
//	public boolean checkRightByRoleName(String roleName) {
//		McareACSMapper mapper = new McareACSMapper();
//		return mapper.listRoleRights(roleName, getMerchantId()).size() > 0?true:false;
//	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

//	public boolean isMerchantSelected() {
//		if (Utility.isNotNullOrEmpty(getMerchantId())) {
//			return true;
//		} else
//			return false;
//	}

//	public boolean isTargetCustomerGuidSelected() {
//		if (Utility.isNotNullOrEmpty(getCustomerGUID())) {
//			return true;
//		} else
//			return false;
//	}

//	public String getCustomerCurrencySymbol() {
//		String currencySymbol = Utility.currencySymbolMap
//				.get(getCustomerCurrency());
//		return (currencySymbol != null) ? currencySymbol : StringUtils.EMPTY;
//	}

	public String getCustomerType() {

		if (customerType == null) {
			if ("patch".equalsIgnoreCase(getMerchantId())
					|| "adcom".equalsIgnoreCase(getMerchantId())
					|| "aolugc".equalsIgnoreCase(getMerchantId())
					|| "aolcms".equalsIgnoreCase(getMerchantId())
					|| "hpsound".equalsIgnoreCase(getMerchantId())
					|| "gamesap".equalsIgnoreCase(getMerchantId())
					|| "pricelinemvp".equalsIgnoreCase(getMerchantId())
					|| "pricelinegthrv".equalsIgnoreCase(getMerchantId())
					|| "gps_deals_ap".equalsIgnoreCase(getMerchantId())
					|| "patchsiteap".equalsIgnoreCase(getMerchantId())
					|| "aolcmsuk".equalsIgnoreCase(getMerchantId())
					|| "aolspp".equalsIgnoreCase(getMerchantId())
					|| "patchdeals".equalsIgnoreCase(getMerchantId())) {

				customerType = "Provider";
			} else {
				customerType = "Customer";
			}

		}

		return customerType;
	}
//
//	public String getFieldDisplays() {
//		return fieldDisplays;
//	}
//
//	public void setFieldDisplays(String fieldDisplays) {
//		this.fieldDisplays = fieldDisplays;
//	}
//
//	public String getFieldTips() {
//		return fieldTips;
//	}
//
//	public void setFieldTips(String fieldTips) {
//		this.fieldTips = fieldTips;
//	}

	/**
	 * 
	 * @return return base URL for email tokens (example:
	 *         http://devccare-dl17.tweb
	 *         .aol.com:8080/selfcare/patch/MyAccount.jsp
	 *         ?merchant=patch&target=)
	 */
//	public String getSelfcareBaseURL() {
//		return new StringBuilder(
//				Utility.getEnvironmentProperty("mcare.patchdeals.selfcare.url"))
//				.append(getMerchantId()).append("/MyAccount.jsp?merchant=")
//				.append(getMerchantId()).append("&target=").toString();
//	}

	public String getFieldsDisplayProperty() {
		return fieldsDisplayProperty;
	}

	public void setFieldsDisplayProperty(String fieldsDisplayProperty) {
		this.fieldsDisplayProperty = fieldsDisplayProperty;
	}

	/**
	 * @return the userLoginId
	 */
	public String getUserLoginId() {
		return userLoginId;
	}

	/**
	 * @param userLoginId the userLoginId to set
	 */
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return the customerCurrency
	 */
	public String getCustomerCurrency() {
		return customerCurrency;
	}

	/**
	 * @param customerCurrency the customerCurrency to set
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
	 * @param customerCountry the customerCountry to set
	 */
	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	/**
	 * @param customerGUID the customerGUID to set
	 */
	public void setCustomerGUID(String customerGUID) {
		this.customerGUID = customerGUID;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the merchantCookieName
	 */
	public String getMerchantCookieName() {
		return merchantCookieName;
	}

	/**
	 * @param merchantCookieName the merchantCookieName to set
	 */
	public void setMerchantCookieName(String merchantCookieName) {
		this.merchantCookieName = merchantCookieName;
	}

	/**
	 * @return the customerGuidCookieName
	 */
	public String getCustomerGuidCookieName() {
		return customerGuidCookieName;
	}

	/**
	 * @param customerGuidCookieName the customerGuidCookieName to set
	 */
	public void setCustomerGuidCookieName(String customerGuidCookieName) {
		this.customerGuidCookieName = customerGuidCookieName;
	}

	/**
	 * @return the customerNameCookieName
	 */
	public String getCustomerNameCookieName() {
		return customerNameCookieName;
	}

	/**
	 * @param customerNameCookieName the customerNameCookieName to set
	 */
	public void setCustomerNameCookieName(String customerNameCookieName) {
		this.customerNameCookieName = customerNameCookieName;
	}

	/**
	 * @return the customerCurrencyCookieName
	 */
	public String getCustomerCurrencyCookieName() {
		return customerCurrencyCookieName;
	}

	/**
	 * @param customerCurrencyCookieName the customerCurrencyCookieName to set
	 */
	public void setCustomerCurrencyCookieName(String customerCurrencyCookieName) {
		this.customerCurrencyCookieName = customerCurrencyCookieName;
	}

	/**
	 * @return the customerCountryCookieName
	 */
	public String getCustomerCountryCookieName() {
		return customerCountryCookieName;
	}

	/**
	 * @param customerCountryCookieName the customerCountryCookieName to set
	 */
	public void setCustomerCountryCookieName(String customerCountryCookieName) {
		this.customerCountryCookieName = customerCountryCookieName;
	}

	/**
	 * @return the userCookieName
	 */
	public String getUserCookieName() {
		return userCookieName;
	}

	/**
	 * @param userCookieName the userCookieName to set
	 */
	public void setUserCookieName(String userCookieName) {
		this.userCookieName = userCookieName;
	}

	public Boolean getHasUpdateRight() {
		return hasUpdateRight;
	}

	public void setHasUpdateRight(Boolean hasUpdateRight) {
		this.hasUpdateRight = hasUpdateRight;
	}

	public Boolean getHasViewRight() {
		return hasViewRight;
	}

	public void setHasViewRight(Boolean hasViewRight) {
		this.hasViewRight = hasViewRight;
	}

}