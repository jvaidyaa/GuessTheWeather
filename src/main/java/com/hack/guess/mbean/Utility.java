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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang.StringEscapeUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.Namespace;
//import org.jdom.output.Format;
//import org.jdom.output.XMLOutputter;


public class Utility {
	//private static Log log = LogFactory.getLog(AbstractMessageBean.class);
	public static final String userCookieName = getAppProperty("mCare.Guid.CookieName");
	public static final String merchantCookieName = getAppProperty("mCare.Current.MerchantRole.CookieName");
	public static final String customerGuidCookieName = getAppProperty("mCare.Current.CustomerGuid.CookieName");
	public static final String customerNameCookieName = getAppProperty("mCare.Current.CustomerName.CookieName");
	public static final String customerCurrencyCookieName = getAppProperty("mCare.Current.CustomerCurrency.CookieName");
	public static final String customerCountryCookieName = getAppProperty("mCare.Current.CustomerCountry.CookieName");
	public static final String transLogDefaultMonth = getAppProperty("mcare.translog.defaultMonth");	
	public static final String transLogDefaultDay = getAppProperty("mcare.translog.defaultDay");
	public static final String transLogDefaultRefreshInterval = getAppProperty("mcare.translog.defaultRefreshInterval");
	public static final String mCareEnv = getEnvironmentProperty("mCare.env");
	public static final String mCareURLOld = getEnvironmentProperty("mCare.URL.Old");


	//public static XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
	private static String obiEsbPoint = null;
	private static String mCareSg = null;
	private static String mCareTg = null;
	private static String mCareA = null;
	private static String mCareDevId = null;
	private static String mCareCipherdata = null;
	private static String mCareHexresult = null;
	private static String mCareReferer = null;
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static SimpleDateFormat sdfNeedChanged = new SimpleDateFormat(
			"yyyy-mm-dd");
	public static DecimalFormat moneydf = new DecimalFormat("#.00"); //money format
	
	public static String TOS_ACCEPTANCE_DATE = "MM/dd/yyyy";
	

	public static Map<String, String> currencySymbolMap = new HashMap<String, String>(2);
	private static final String DEFAULT_CURRENCY = "USD";
	//private static ObiGroupHierarchy[] obiGroupHierarchy;
	
	// static initializer
	static{
		currencySymbolMap.put("USD", "\u0024");
		currencySymbolMap.put("GBP",  "\u00a3");
	}

	public static String getMCareA() {
		return mCareA;
	}

	public static String getMCareCipherdata() {
		return mCareCipherdata;
	}

	public static String getMCareDevId() {
		return mCareDevId;
	}

	public static String getMCareHexresult() {
		return mCareHexresult;
	}

	public static String getMCareReferer() {
		return mCareReferer;
	}

	public static String getMCareSg() {
		return mCareSg;
	}

	public static String getMCareTg() {
		return mCareTg;
	}
	
//	public static PromoServiceDAO getPromoServiceDAO(
//		HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		PromoServiceDAO promoService = new PromoServiceDAO();
//
//		try {
//			promoService = new PromoServiceDAO();
//			log.info("USING ENDPOINT = " + Utility.getEnvironmentProperty("obi.endpoint"));
//			promoService.setEndpoint(Utility
//					.getEnvironmentProperty("obi.endpoint"));
//			promoService.setMerchantId(merchantId);
//			promoService.setTarget(customerGUID);
//			promoService.setAgent(userLoginId);
//		//	promoService.setReferer(Security.getRefererEncoded(request));  // DO WE NEED THIS?????
//			/*if (isOpenAuthCallRequired()) {  // DO WE NEED THIS?????
//				promoService.setSecurityToken(Security.getAuthToken(request));
//				promoService.setApiKey(Security.getAppKey());
//			} else {*/
//				promoService.setApiKey(Security.getAkesAppKey());
//				promoService.setHexresult(Security.getAkesHexResult());
//				promoService.setCipherdata(Security.getAkesCipherData());
//				String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//				//if ((cd != null) && (!cd.isEmpty())) {
//                                if(isNotNullOrEmpty(cd)){
//					promoService.setCountryCode(cd);
//				}
//			//}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set PromoServiceDAO (i.e., endpoint, token, referer, " +
//					"devid, or merchant, exception follows: "
//							+ e);
//			return null; // lookout above...
//		}
//		if (!promoService.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//		return promoService;
//	}
//	
//	public static ApPaymentDAO getApPaymentDAO(HttpServletRequest request,
//			String merchantId, String customerGUID, String userLoginId) {
//		ApPaymentDAO ap = new ApPaymentDAO();
//		try {
//			ap.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//			ap.setTarget(customerGUID);
//			ap.setAgent(userLoginId);
//			ap.setMerchantId(merchantId);
//			ap.setApiKey(Security.getAkesAppKey());
//			ap.setHexresult(Security.getAkesHexResult());
//			ap.setCipherdata(Security.getAkesCipherData());
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set ApPaymentDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!ap.connect()) { // note: this method has try-catch within
//			return null;
//		}
//		return ap;
//	}
//
//
//	public static ExternalServiceDAO getExternalServiceDAO(HttpServletRequest request, String merchantId, String customerGUID, String userLoginId){
//		ExternalServiceDAO externalServiceDAO = new ExternalServiceDAO();
//
//		try {
//			log.info("USING ENDPOINT = " + Utility.getEnvironmentProperty("obi.endpoint"));
//			externalServiceDAO.setEndpoint(Utility
//					.getEnvironmentProperty("obi.endpoint"));
//			externalServiceDAO.setMerchantId(merchantId);
//			externalServiceDAO.setTarget(customerGUID);
//			externalServiceDAO.setAgent(userLoginId);
//			externalServiceDAO.setApiKey(Security.getAkesAppKey());
//			externalServiceDAO.setHexresult(Security.getAkesHexResult());
//			externalServiceDAO.setCipherdata(Security.getAkesCipherData());
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set ExternalServiceDAO (i.e., endpoint, token, referer, " +
//					"devid, or merchant, exception follows: "
//							+ e);
//			return null; // lookout above...
//		}
//		if (!externalServiceDAO.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//		return externalServiceDAO;
//		
//	}
//	
//	public static PaymentServiceDAO getPaymentServiceDAO(
//		HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		PaymentServiceDAO ps = new PaymentServiceDAO();
//
//		try {
//			log.info("USING ENDPOINT = "
//					+ Utility.getEnvironmentProperty("obi.endpoint"));
//			ps.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//			ps.setTarget(customerGUID);
//			ps.setAgent(userLoginId);
//			ps.setMerchantId(merchantId);
//			ps.setApiKey(Security.getAkesAppKey());
//			ps.setHexresult(Security.getAkesHexResult());
//			ps.setCipherdata(Security.getAkesCipherData());
//			String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//			//if ((cd != null) && (!cd.isEmpty())) {
//                        if(isNotNullOrEmpty(cd)){
//				ps.setCountryCode(cd);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set PaymentServiceDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!ps.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//		return ps;
//	}
//	
//	
//	public static SwitchSubscriptionServiceDAO getSwitchOfferDAO(
//			HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//
//			SwitchSubscriptionServiceDAO ps = new SwitchSubscriptionServiceDAO();
//
//			try {
//				log.info("USING ENDPOINT = "
//						+ Utility.getEnvironmentProperty("obi.endpoint"));
//				ps.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//				ps.setTarget(customerGUID);
//				ps.setAgent(userLoginId);
//				ps.setMerchantId(merchantId);
//				ps.setApiKey(Security.getAkesAppKey());
//				ps.setHexresult(Security.getAkesHexResult());
//				ps.setCipherdata(Security.getAkesCipherData());
//				String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//				//if ((cd != null) && (!cd.isEmpty())) {
//	                        if(isNotNullOrEmpty(cd)){
//					ps.setCountryCode(cd);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.error("::: Failed to set SwitchSubscriptionServiceDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//						+ e);
//				return null; // lookout above...
//			}
//			if (!ps.connect()) { // note: already logged an error in this
//				// method...
//				return null; // lookout above...
//			}
//			return ps;
//		}
//	
//
//	public static ContractServiceDAO getContractServiceDAO(
//			HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		ContractServiceDAO cs = new ContractServiceDAO();
//
//		try {
//			log.info("USING ENDPOINT = "
//					+ Utility.getEnvironmentProperty("obi.endpoint"));
//			cs.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//			cs.setTarget(customerGUID);
//			cs.setAgent(userLoginId);
//			cs.setMerchantId(merchantId);
//			cs.setApiKey(Security.getAkesAppKey());
//			cs.setHexresult(Security.getAkesHexResult());
//			cs.setCipherdata(Security.getAkesCipherData());
//			String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//			//if ((cd != null) && (!cd.isEmpty())) {
//                        if(isNotNullOrEmpty(cd)){
//				cs.setCountryCode(cd);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set ContractServiceDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!cs.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//		return cs;
//	}
//
//	public static CustomerServiceDAO getCustomerServiceDAO(
//		HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		CustomerServiceDAO cs = new CustomerServiceDAO();
//		try {
//			cs.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//
//			cs.setMerchantId(merchantId);
//			cs.setTarget(customerGUID);
//			cs.setAgent(userLoginId);
//			cs.setApiKey(Security.getAkesAppKey());
//			cs.setHexresult(Security.getAkesHexResult());
//			cs.setCipherdata(Security.getAkesCipherData());
//			String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//			//if ((cd != null) && (!cd.isEmpty())) {
//                        if(isNotNullOrEmpty(cd)){
//				cs.setCountryCode(cd);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set CustomerServiceDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!cs.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//
//		return cs;
//	}
//	
//	public static CouponServiceDAO getCouponServiceDAO(HttpServletRequest request, String merchantId, String customerGUID, String userLoginId){
//		CouponServiceDAO cs = new CouponServiceDAO();
//
//		try {
//			cs.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//			cs.setMerchantId(merchantId);
//			cs.setTarget(customerGUID);
//			cs.setAgent(userLoginId);
//			cs.setApiKey(Security.getAkesAppKey());
//			cs.setHexresult(Security.getAkesHexResult());
//			cs.setCipherdata(Security.getAkesCipherData());
//			String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//			//if ((cd != null) && (!cd.isEmpty())) {
//                        if(isNotNullOrEmpty(cd)){
//				cs.setCountryCode(cd);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set CustomerServiceDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!cs.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//
//		return cs;
//	}
//	
//	public static SubscriptionServiceDAO getSubscriptionServiceDAO(
//		HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		SubscriptionServiceDAO ss = new SubscriptionServiceDAO();
//		try {
//			ss.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//
//			ss.setMerchantId(merchantId);
//			ss.setTarget(customerGUID);
//			ss.setAgent(userLoginId);
//			ss.setApiKey(Security.getAkesAppKey());
//			ss.setHexresult(Security.getAkesHexResult());
//			ss.setCipherdata(Security.getAkesCipherData());
//			String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//			//if ((cd != null) && (!cd.isEmpty())) {
//                        if(isNotNullOrEmpty(cd)){
//				ss.setCountryCode(cd);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set SubscriptionServiceDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!ss.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//
//		return ss;
//	}
//
//	public static CustomerMessagingDAO getCustomerMessagingDAO(
//		HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//
//		CustomerMessagingDAO es = new CustomerMessagingDAO();
//		try {
//			es.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//
//			es.setMerchantId(merchantId);
//			es.setTarget(customerGUID);
//			es.setAgent(userLoginId);
//			es.setApiKey(Security.getAkesAppKey());
//			es.setHexresult(Security.getAkesHexResult());
//			es.setCipherdata(Security.getAkesCipherData());
//			String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//			//if ((cd != null) && (!cd.isEmpty())) {
//                        if(isNotNullOrEmpty(cd)){
//				es.setCountryCode(cd);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set CustomerMessagingDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!es.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//
//		return es;
//
//	}
//
//	public static EventServiceDAO getEventServiceDAO(
//		HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		EventServiceDAO es = new EventServiceDAO();
//		try {
//			es.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//
//			es.setMerchantId(merchantId);
//			es.setTarget(customerGUID);
//			es.setAgent(userLoginId);
//			es.setApiKey(Security.getAkesAppKey());
//			es.setHexresult(Security.getAkesHexResult());
//			es.setCipherdata(Security.getAkesCipherData());
//			String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//			//if ((cd != null) && (!cd.isEmpty())) {
//                        if(isNotNullOrEmpty(cd)){
//				es.setCountryCode(cd);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set EventServiceDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!es.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//
//		return es;
//
//	}
//
//	public static WalletServiceDAO getWalletServiceDAO(
//			HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		WalletServiceDAO ws = new WalletServiceDAO();
//		try {
//			ws.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//			ws.setTarget(customerGUID);
//			ws.setAgent(userLoginId);
//			ws.setMerchantId(merchantId);
//			ws.setApiKey(Security.getAkesAppKey());
//			ws.setHexresult(Security.getAkesHexResult());
//			ws.setCipherdata(Security.getAkesCipherData());
//			String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//			//if ((cd != null) && (!cd.isEmpty())) {
//                        if(isNotNullOrEmpty(cd)){
//				ws.setCountryCode(cd);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set WalletServiceDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!ws.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//		return ws;
//	}
//
//	
//	
//	public static RatingServiceDAO getRatingServiceDAO(
//			HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		RatingServiceDAO rs = new RatingServiceDAO();
//		try {
//			rs.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//			rs.setTarget(customerGUID);
//			rs.setAgent(userLoginId);
//			rs.setMerchantId(merchantId);
//			rs.setApiKey(Security.getAkesAppKey());
//			rs.setHexresult(Security.getAkesHexResult());
//			rs.setCipherdata(Security.getAkesCipherData());
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set RatingServiceDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null; // lookout above...
//		}
//		if (!rs.connect()) { // note: already logged an error in this
//			// method...
//			return null; // lookout above...
//		}
//		return rs;
//	}
//	
//	
//	
//	public static PaymentReceivableDAO getPaymentReceivableDAO(
//		HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		PaymentReceivableDAO pr = new PaymentReceivableDAO();
//
//		try {
//			pr.setEndpoint(Utility.getEnvironmentProperty("obi.endpoint"));
//			pr.setTarget(customerGUID);
//			pr.setAgent(userLoginId);
//			pr.setMerchantId(merchantId);
//			pr.setApiKey(Security.getAkesAppKey());
//			pr.setHexresult(Security.getAkesHexResult());
//			pr.setCipherdata(Security.getAkesCipherData());
//			String cd = AppCookie.fetchCookieValue(request, customerCountryCookieName);
//			//if ((cd != null) && (!cd.isEmpty())) {
//                        if(isNotNullOrEmpty(cd)){
//				pr.setCountryCode(cd);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("::: Failed to set PaymentReceivableDAO (i.e., endpoint, token, referer, devid, or merchant, exception follows: "
//					+ e);
//			return null;
//		}
//		if (!pr.connect()) { // note: already logged an error in this
//			// method...
//			return null;
//		}
//		return pr;
//	}

	/**
	 * This method fetches the value of a parameter in the environment
	 * properties file.
	 */
	public static String getEnvironmentProperty(String key) {
		return getEnvironmentProperty(key, null);
	}
	
	
	
	
	
	

        /**
	 * This method fetches the value of a parameter in the environment
	 * properties file.
	 */
	public static String getEnvironmentProperty(String key, String defvalue) {
		String value = defvalue;
                try {
		   value = ResourceBundle.getBundle("mcarebeta-environment")
				.getString(key);
                }
                catch(Exception e) {
		   System.out.println("::: Failed to get the value from property file " + key + " Using default : " +  defvalue) ;  
                }
              
		return value;
	}

	/**
	 * This method fetches the value of a parameter in the environment
	 * properties file.
	 */
	public static String getAppProperty(String key) {
		String value = null;
		value = ResourceBundle.getBundle("mcare-application").getString(key);
		return value;
	}

	/**
	 * This method URL encodes a string value so that it can be passed in any
	 * HTTP calls
	 */
	public static String encodeString(String strval) {
		String encodedStrVal = "";
		if (strval == null || strval.length() == 0) {
			return null;
		} else {
			try {
				encodedStrVal = URLEncoder.encode(strval, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return encodedStrVal;
	}

	/**
	 * This method returns the URL of the web application (i.e.,
	 * "http://this.that"). Note that if the web application is running in SSL
	 * mode, then the lead is "https://..." Also, if the web application is not
	 * running on port 80, the URL includes "domain:port" in its name.
	 */
	public static String getServerUrl(HttpServletRequest request) {
		String serverURL = "";
		String protocol = "http://";
		if (Utility.getEnvironmentProperty("mcare.ssl.enabled").equals("true"))
			protocol = "https://";

		if (request.getServerPort() == 80)
			serverURL = protocol + request.getServerName()
					+ request.getContextPath();
		else
			serverURL = protocol + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath();

		return serverURL;
	}

	public static String getObiEsbEndPoint() {

		if (obiEsbPoint == null) {
			/*
			 * esbPoint = PropertiesUtil
			 * .getPropertyValue("bps.authorizer.obigeneric.logger.url");
			 */
		}
		if (obiEsbPoint == null) {
			// obiEsbPoint =
			// "https://aol.stateless.red.ps.aol.com:5002/obiproxy/authLogger";
			obiEsbPoint = "http://esbam3dv.ps.aol.com:8080/biz/wsapi";
		}
		return obiEsbPoint;
	}

	/**
	 * Returns formatted currency in USD. This is legacy API
	 * Please use <code>public static String getFormattedCurrencyAbsolute(double d, String currency)</code> for supporting
	 * different currencies.
	 * @param d
	 * @return String
	 */
	public static String getFormattedCurrencyAbsolute(double d) {
		String currencyFormatProp = getAppProperty("CurrencyFormat");
		DecimalFormat currencyFormat = new DecimalFormat(currencySymbolMap.get(DEFAULT_CURRENCY) + currencyFormatProp);
		return currencyFormat.format(Math.abs(d));
	}
	
	/**
	 * Supports USD and GBP. Defaults to USD, if input currency name is not recognized.
	 * @param d
	 * @param currency
	 * @return String
	 */
	public static String getFormattedCurrencyAbsolute(double d, String currencySymbol) {
		if(currencySymbol != null){
			String currencyFormatProp = getAppProperty("CurrencyFormat");
			DecimalFormat currencyFormat = new DecimalFormat(currencySymbol + currencyFormatProp);
			return currencyFormat.format(Math.abs(d));
		}else{
			return getFormattedCurrencyAbsolute(d);
		}
	}

	/**
	 * @param str
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replace(String str, String pattern, String replace) {
		if (null == str) {
			return str;
		}
		if (null == pattern) {
			return str;
		}
		if (null == replace) {
			return str.replaceAll(pattern, "");
		} else {
			return str.replaceAll(pattern, replace);
		}
	}

	public static String replaceJasonChar(String str) {
		if (null == str) {
			return str;
		}
		final StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator(str);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			if (character == '\"') {
				//result.append("\\\"");
			} else if (character == '\\') {
				//result.append("\\\\");
			} else if (character == '/') {
				//result.append("\\/");
			} else if (character == '\b') {
				result.append("\\b");
			} else if (character == '\f') {
				result.append("\\f");
			} else if (character == '\n') {
				result.append("\\n");
			} else if (character == '\r') {
				result.append("\\r");
			} else if (character == '\t') {
				result.append("\\t");
			} else if (character == '<') {
				
			} else if (character == '\'') {
				
			} else if (character == '>') {
				
			} else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();

	}

//	public static Long mapLongValue(Element longValueE, Namespace ns) {
//		if (null == longValueE) {
//			return null;
//		}
//		return new Long(longValueE.getValue());
//	}
//
//	public static Double mapDoubleValue(Element doubleValueE, Namespace ns) {
//		if (null == doubleValueE) {
//			return null;
//		}
//		return new Double(doubleValueE.getValue());
//	}
//
//	public static String mapStringValue(Element stringValueE, Namespace ns) {
//		if (null == stringValueE) {
//			return null;
//		}
//		return stringValueE.getValue();
//	}
//
//	public static Integer mapIntegerValue(Element integerValueE, Namespace ns) {
//		if (null == integerValueE) {
//			return null;
//		}
//		return new Integer(integerValueE.getValue());
//	}
//
//	public static Date mapDateValue(Element calendarValueE, Namespace ns,
//			String replaceChar, String dateFomatString) {
//		Calendar cal = mapCalendarValue(calendarValueE, ns, replaceChar,
//				dateFomatString);
//		if (cal == null) {
//			return null;
//		}
//		return cal.getTime();
//	}
//
//	public static Calendar mapCalendarValue(Element calendarValueE,
//			Namespace ns, String replaceChar, String dateFomatString) {
//		if ((null == calendarValueE) || (null == replaceChar)
//				|| (null == dateFomatString)) {
//			return null;
//		}
//		String str = calendarValueE.getValue().replace(replaceChar.charAt(0),
//				' ');
//		return getCalendar(dateFomatString, str);
//	}
//
//	public static Calendar getCalendar(String fmtString, String dt) {
//		System.out.println("input date : " + dt);
//		System.out.println("input fmtString : " + fmtString);
//		Calendar cal = null;
//		SimpleDateFormat format = new SimpleDateFormat(fmtString);
//
//		try {
//			//if (dt != null && dt.length() != 0) {
//                        if(isNotNullOrEmpty(dt)){
//				format.parse(dt);
//				cal = format.getCalendar();
//			}
//		} catch (Exception e) {
//			log.error("FormatString : " + fmtString + " dateString : " + dt);
//			log.error("Date parsing errored out", e);
//		}
//		if (cal != null) {
//			System.out.println("output date " + getFormattedDate(cal, fmtString));
//		}
//		return cal;
//
//	}
//
//	/**
//	 * @param cal
//	 * @return
//	 */
//
//	public static void printElement(Element e) {
//		if (e == null) {
//			System.out.println(" The element is null ");
//		} else {
//			Element e1 = (Element) e.clone();
//			System.out.println("*** The element ***\n"
//					+ xmlOut.outputString(new Document(e1)));
//		}
//	}

	public static boolean isNullOrEmpty(String s) {
		if ((s == null) || (s.trim().length() == 0)) {
			return true;
		} else {
			return false;
		}
	}

        /**
         * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
         *
         * <pre>
         * Utility.isNotNullOrEmpty(null)      = false
         * Utility.isNotNullOrEmpty("")        = false
         * Utility.isNotNullOrEmpty(" ")       = false
         * Utility.isNotNullOrEmpty("bob")     = true
         * Utility.isNotNullOrEmpty("  bob  ") = true
         * </pre>
         *
         * @param str  the String to check, may be null
         * @return <code>true</code> if the String is not empty and not null and not whitespace
         */
        public static boolean isNotNullOrEmpty(String s) {
            if ((s == null) || (s.trim().length() == 0)) {
                return false;
            } else {
                return true;
            }
        }
	/**
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static boolean validDate(String dateTime, String dateFormat)
			throws ParseException {
		Date dt = null;
		SimpleDateFormat dfm = new SimpleDateFormat(dateFormat);
		// "yyyy-MM-dd"
		dt = dfm.parse(dateTime);
		// SimpleDateFormat simply roll over the date to next valid date
		// if the format is correct
		System.out.println("Input date request:" + dateTime);
		System.out.println("Parsed date value:" + dfm.format(dt));
		if (!dfm.format(dt).equals(dateTime)) {
			return false;
		}
		return true;
	}

	public static Calendar getCalendar(java.sql.Timestamp timeStamp) {
		Calendar cal = null;
		if (timeStamp != null) {
			cal = Calendar.getInstance();
			cal.setTimeInMillis(timeStamp.getTime());
		}
		return cal;
	}

	public static String getFormattedDate(Date date) {

		String dateStr = null;

		if (date != null) {

			dateStr = sdf.format(date);
		}

		return dateStr;
	}

	public static String getFormattedDate(Calendar cal, String dateFormat) {
		Date dt = cal.getTime();
		SimpleDateFormat dfm = new SimpleDateFormat(dateFormat);
		return dfm.format(dt);
	}
	
	public static String getFormattedDate(java.sql.Timestamp timeStamp, String timezone, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		TimeZone timezoneObj = TimeZone.getTimeZone(timezone);
		format.setTimeZone(timezoneObj);
		return format.format(getCalendar(timeStamp).getTime());
	}
	
	public static XMLGregorianCalendar getNow(){
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			return null;
		}
	}

	public static String toDateString(XMLGregorianCalendar xmlDate) {
		String retDateString = null;
		if (xmlDate != null) {
			retDateString = getStringDigit(xmlDate.getMonth()) + "/"
					+ getStringDigit(xmlDate.getDay()) + "/"
					+ xmlDate.getYear() + " "
					+ getStringDigit(xmlDate.getHour()) + ":"
					+ getStringDigit(xmlDate.getMinute()) + ":"
					+ getStringDigit(xmlDate.getSecond());
		}
		return retDateString;
	}
	
	public static String toDateString(XMLGregorianCalendar xmlDate, String format) {
		if(xmlDate == null){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(xmlDate.toGregorianCalendar().getTime());
	}
	
	public static String toShortDateString(XMLGregorianCalendar xmlDate) {

		String retDateString = null;
		if (xmlDate != null) {
			retDateString = getStringDigit(xmlDate.getMonth()) + "/"
					+ getStringDigit(xmlDate.getDay()) + "/"
					+ xmlDate.getYear();
		}
		return retDateString;
	}

	public static String getStringDigit(int digit) {
		if (digit < 10) {
			return "0" + digit;
		} else {
			return "" + digit;
		}

	}

	public static String toExpiryDateString(XMLGregorianCalendar xmlDate) {

		String retDateString = null;
		if (xmlDate != null) {
			retDateString = getStringDigit(xmlDate.getMonth()) + "/"
					+ xmlDate.getYear();
		}
		return retDateString;
	}

//	public static void resetCookies(HttpServletRequest request,
//			HttpServletResponse response) {
//		if (request.getCookies() != null) {
//			for (Cookie cookie : request.getCookies()) {
//				System.out.println("Found cookie, Name=" + cookie.getName());
//				if (cookie.getName().equalsIgnoreCase(userCookieName)) {
//					System.out.println("Found cookie userCookieName=" + userCookieName);
//					AppCookie.deleteCookie(request, response, cookie.getName());
//				} else if (cookie.getName()
//						.equalsIgnoreCase(merchantCookieName)) {
//					System.out.println("Found cookie merchantCookieName="
//							+ merchantCookieName);
//					AppCookie.deleteCookie(request, response, cookie.getName());
//				} else if (cookie.getName().equalsIgnoreCase(
//						customerGuidCookieName)) {
//					System.out.println("Found cookie customerGuidCookieName="
//							+ customerGuidCookieName);
//					AppCookie.deleteCookie(request, response, cookie.getName());
//				} else if (cookie.getName().equalsIgnoreCase(
//						customerNameCookieName)) {
//					System.out.println("Found cookie customerNameCookieName="
//							+ customerNameCookieName);
//					AppCookie.deleteCookie(request, response, cookie.getName());
//				} else if (cookie.getName().equalsIgnoreCase(
//						customerCurrencyCookieName)) {
//					System.out.println("Found cookie customerCurrencyCookieName="
//							+ customerCurrencyCookieName);
//					AppCookie.deleteCookie(request, response, cookie.getName());
//				} else if (cookie.getName().equalsIgnoreCase(
//						customerCountryCookieName)) {
//					System.out.println("Found cookie customerCountryCookieName="
//							+ customerCountryCookieName);
//					AppCookie.deleteCookie(request, response, cookie.getName());
//				}
//			}
//		}
//	}

	/**
	 * @param req
	 * @param funcName
	 */
	public static void printRequest_O(HttpServletRequest req, String funcName) {

		// Log log1 = getLogger(Utility.class);

		System.out.println(":::" + funcName + "::: printing request details");

		// print all cookies
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			System.out.println(":::" + funcName + "::: cookie size:" + cookies.length);
			for (int i = 0; i < cookies.length; i++) {
				System.out.println(":::" + funcName + ":::cookie name:= value:::"
						+ cookies[i].getName() + ":=" + cookies[i].getValue()
						+ ":path:" + cookies[i].getPath() + ":domain:"
						+ cookies[i].getDomain() + ":version:"
						+ cookies[i].getVersion());
			}
		}

		System.out.println(":::" + funcName + ":::printing request headers:");
		Enumeration names = req.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			System.out.println(":::" + funcName + ":::header name:" + name);
			Enumeration values = req.getHeaders(name); // support multiple
			// values
			if (values != null) {
				while (values.hasMoreElements()) {
					String value = (String) values.nextElement();
					System.out.println(":::" + funcName + ":::header value:" + value);
				}
			}
		}

		System.out.println(":::" + funcName + ":::printing request attributes:");
		names = req.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			System.out.println(":::" + funcName + ":::attribute name:" + name);
			System.out.println(":::" + funcName + ":::attribute value:"
					+ req.getAttribute(name));
		}

		if (req.getSession() != null) {
			HttpSession session = req.getSession();
			System.out.println(":::" + funcName
					+ ":::printing request session attributes:");
			names = session.getAttributeNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				System.out.println(":::" + funcName + ":::session attribute name:"
						+ name);
				System.out.println(":::" + funcName + ":::session attribute value:"
						+ session.getAttribute(name));
			}
		}

		System.out.println(":::" + funcName + ":::printing request parameters:");
		names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			System.out.println(":::" + funcName + ":::parameter name:" + name);
			System.out.println(":::" + funcName + ":::parameter value:"
					+ req.getParameter(name));
		}

		System.out.println(":::" + funcName + ":::request pathinfo:" + req.getPathInfo());
		System.out.println(":::" + funcName + ":::request authtype:" + req.getAuthType());
		System.out.println(":::" + funcName + ":::request characterencoding:"
				+ req.getCharacterEncoding());
		System.out.println(":::" + funcName + ":::request contentlength:"
				+ req.getContentLength());
		System.out.println(":::" + funcName + ":::request contenttype:"
				+ req.getContentType());
		System.out.println(":::" + funcName + ":::request contextpath:"
				+ req.getContextPath());
		System.out.println(":::" + funcName + ":::request method:" + req.getMethod());
		System.out.println(":::" + funcName + ":::request pathtranslated:"
				+ req.getPathTranslated());
		System.out.println(":::" + funcName + ":::request protocol:" + req.getProtocol());
		System.out.println(":::" + funcName + ":::request querystring:"
				+ req.getQueryString());
		System.out.println(":::" + funcName + ":::request remoteaddr:"
				+ req.getRemoteAddr());
		System.out.println(":::" + funcName + ":::request remotehost:"
				+ req.getRemoteHost());
		System.out.println(":::" + funcName + ":::request remoteuser:"
				+ req.getRemoteUser());
		System.out.println(":::" + funcName + ":::request requestedsessionid:"
				+ req.getRequestedSessionId());
		System.out.println(":::" + funcName + ":::request requesturi:"
				+ req.getRequestURI());
		System.out.println(":::" + funcName + ":::request requestscheme:"
				+ req.getScheme());
		System.out.println(":::" + funcName + ":::request servername:"
				+ req.getServerName());
		System.out.println(":::" + funcName + ":::request serverport:"
				+ req.getServerPort());
		System.out.println(":::" + funcName + ":::request servletpath:"
				+ req.getServletPath());

		System.out.println(":::" + funcName
				+ "::: finished printing request details:::::::::::::::");
	}

        public static void printRequest(HttpServletRequest req, String funcName) {

		// Log log1 = getLogger(Utility.class);
                    
                String huge_string = new String();

		// print all cookies
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
                        huge_string = huge_string + "cookies : " + cookies.length + "\n" ;
			for (int i = 0; i < cookies.length; i++) {
                               huge_string = huge_string + "Name :" + cookies[i].getName()  
                                                         + " value:" + cookies[i].getValue()  
                                                         + " path:" + cookies[i].getPath() 
                                                         + " domain:" + cookies[i].getDomain()
                                                         + " version:" + cookies[i].getVersion() + "\n";
			}
		}
                
                huge_string = huge_string + "headers :\n" ;
		Enumeration names = req.getHeaderNames();
		while (names.hasMoreElements()) {
                        
			String name = (String) names.nextElement();
			huge_string = huge_string + "header_name=" + name;
			Enumeration values = req.getHeaders(name); // support multiple
			// values
			if (values != null) {
				while (values.hasMoreElements()) {
					String value = (String) values.nextElement();
                                        huge_string = huge_string + "[" + value + "]";

				}
			}
		}


                huge_string = huge_string + "\n" + "request attributes :\n" ;
		names = req.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
                        huge_string = huge_string + "[" + name + ":";
                        huge_string = huge_string  + req.getAttribute(name) + "]";
		}
                

		if (req.getSession() != null) {
			HttpSession session = req.getSession();
                        huge_string = huge_string + "\n" + "session attributes :\n" ;

			names = session.getAttributeNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
                        huge_string = huge_string + "[" + name + ":";
                        huge_string = huge_string  + session.getAttribute(name) + "]";

			}
		}

                huge_string = huge_string + "\n" + "request parameters :\n" ;

		names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
                        huge_string = huge_string + "[" + name + ":";
                        huge_string = huge_string  + req.getAttribute(name) + "]";

		}
                
                huge_string = huge_string + "\n getPathInfo=" + req.getPathInfo();
                huge_string = huge_string + "\n getAuthType=" + req.getAuthType();
                huge_string = huge_string + "\n getCharacterEncoding=" + req.getCharacterEncoding();
                huge_string = huge_string + "\n getContentLength=" + req.getContentLength();
                huge_string = huge_string + "\n getContentType=" + req.getContentType();
                huge_string = huge_string + "\n getContextPath=" + req.getContextPath();
                huge_string = huge_string + "\n getMethod=" + req.getMethod();
                huge_string = huge_string + "\n getProtocol=" + req.getProtocol();
                huge_string = huge_string + "\n getRemoteAddr=" + req.getRemoteAddr();
                huge_string = huge_string + "\n getRemoteHost=" + req.getRemoteHost();
                huge_string = huge_string + "\n getRemoteUser=" + req.getRemoteUser();
                huge_string = huge_string + "\n getRequestedSessionId=" + req.getRequestedSessionId();
                huge_string = huge_string + "\n getRequestURI=" + req.getRequestURI();
                huge_string = huge_string + "\n getScheme=" + req.getScheme();
                huge_string = huge_string + "\n getServerName=" + req.getServerName();
                huge_string = huge_string + "\n getServerPort=" + req.getServerPort();
                huge_string = huge_string + "\n getServletPath=" + req.getServletPath();


		System.out.println("Request details: " + funcName + "\n" + huge_string );
	}

        
        
//	public static BillHistoryBO mapBalanceBO(ARBalance arBalance,
//			PaymentMethodSummary pmSummary, String currencySymbol) {
//		BillHistoryBO bo = null;
//		System.out.println("map BalanceBO ");
//
//		if (arBalance != null) {
//			try {
//				double  adj=0, paid=0, billed=0;	
//				bo = new BillHistoryBO(currencySymbol);
//				XMLGregorianCalendar payCalendar = null;
//				bo.setTransactionType("Bill");
//				bo.setId(arBalance.getLineItemBillID());
//				bo.setBillId(arBalance.getLineItemBillID());
//				bo.setBalanceId(arBalance.getLineItemBalanceID());
//				
////				if (arBalance.getStatus().value().equalsIgnoreCase("PENDING")) {
////					bo.setId(arBalance.getLineItemBalanceID());
////					if (arBalance.getDateThreshold() != null) {
////						payCalendar = arBalance.getDateThreshold();
////					}
////				}
//				
//				if (arBalance.getDateThreshold() != null) {
//					payCalendar = arBalance.getDateThreshold();
//				}
//				
//				if (arBalance.getBillDate() != null) {
//					payCalendar = arBalance.getBillDate();
//				}
//				if (payCalendar != null){
//					bo.setBillDate(Utility.toShortDateString(payCalendar));
//					bo.setSortDate(payCalendar.toGregorianCalendar().getTime());
//				}
//
//				bo.setDescription(arBalance.getLineItemDescription());
//				if ( bo.getDescription()!= null && bo.getDescription().length() > 18){
//					bo.setDescriptionShort(bo.getDescription().subSequence(0, 15) + "...");
//				}
//				else{
//					bo.setDescriptionShort(bo.getDescription());
//				}
//				if (arBalance.getAmount() != null) {
//					billed=arBalance.getAmount().doubleValue();
//					bo.setBilledAmtValue(arBalance.getAmount());
//					//bo.setBilledAmt(Double.toString(arBalance.getAmount()));
//					bo.setBilledAmt(Utility.getFormattedCurrencyAbsolute(arBalance.getAmount(), currencySymbol));
//				} else {
//					bo.setBilledAmt("0");
//				}
//				if (arBalance.getAdjustmentAmount() != null) {
//					adj=arBalance.getAdjustmentAmount().doubleValue();
//					bo.setAdjustedAmtValue(arBalance.getAdjustmentAmount());
//					bo.setAdjustedAmt(Double.toString(arBalance
//							.getAdjustmentAmount()));
//				} else {
//					bo.setAdjustedAmt("0");
//				}
//				bo.setNetAmt(Utility.getFormattedCurrencyAbsolute(billed - adj, currencySymbol));
//				
//				if (arBalance.getPaidAmount() != null) {
//					bo.setPaidAmtValue(arBalance.getPaidAmount());
//					bo.setPaidAmt(Double.toString(arBalance.getPaidAmount()));
//				} else {
//					bo.setPaidAmt("0");
//				}
//				if (pmSummary != null) {
//					bo.setPaymentType(getMappingString(pmSummary
//							.getPaymentType().value()), pmSummary
//							.getLastFourDigits());
//					bo.setLastFour(pmSummary.getLastFourDigits());
//				}
//				if (arBalance.getStatus() != null)
//					bo.setStatus(getMappingString(arBalance.getStatus().value()));
//			} catch (Exception e) {
//				log.error(e.getMessage());
//			}
//		}
//		return bo;
//	}

//	public static List<BillHistoryBO> getBalance(
//			ArrayOfARPaymentMethodBalance aob, String currencySymbol) {
//		List<BillHistoryBO> list = new ArrayList<BillHistoryBO>();
//
//		if (aob != null) {
//			List<ARPaymentMethodBalance> arpmBal = aob
//					.getARPaymentMethodBalance();
//			System.out.println("balance " + arpmBal.size());
//			for (int i = 0; i < arpmBal.size(); i++) {
//				List<ARBalance> balanceList = arpmBal.get(i).getBalances()
//						.getARBalance();
//				PaymentMethodSummary pmSummary = arpmBal.get(i).getPmSummary();
//				System.out.println("balance::: number of arBalance "
//						+ balanceList.size());
//				for (int k = 0; k < balanceList.size(); k++) {
//					BillHistoryBO historyBO = mapBalanceBO(balanceList.get(k),
//							pmSummary, currencySymbol);
//					if (historyBO != null) {
//						list.add(historyBO);
//					}
//				}
//			}
//		}
//		return list;
//	}

//	private static void addBalanceRec(ARBalance arBalance,
//			PaymentMethodSummary pmSummary, List<BillHistoryBO> mainList,
//			List<BillHistoryBO> asscList) {
//		System.out.println("addBalanceRec ");
//		BillHistoryBO bo = null;
//
//		if (arBalance != null) {
//			try {
//				bo = new BillHistoryBO();
//				XMLGregorianCalendar payCalendar = null;
//				bo.setTransactionType("Bill");
//				bo.setId(arBalance.getLineItemBillID());
//				bo.setBillId(arBalance.getLineItemBillID());
//				if (arBalance.getStatus().value().equalsIgnoreCase("PENDING")) {
//					bo.setId(arBalance.getLineItemBalanceID());
//					if (arBalance.getDateThreshold() != null) {
//						payCalendar = arBalance.getDateThreshold();
//					}
//				}
//				if (arBalance.getBillDate() != null) {
//					payCalendar = arBalance.getBillDate();
//				}
//				bo.setBillDate(Utility.toShortDateString(payCalendar));
//				bo.setSortDate(payCalendar.toGregorianCalendar().getTime());
//
//				bo.setDescription(arBalance.getLineItemDescription());
//				if (arBalance.getAmount() != null) {
//					bo.setBilledAmtValue(arBalance.getAmount());
//					bo.setBilledAmt(Double.toString(arBalance.getAmount()));
//				} else {
//					bo.setBilledAmt("0");
//				}
//				if (arBalance.getAdjustmentAmount() != null) {
//					bo.setAdjustedAmtValue(arBalance.getAdjustmentAmount());
//					bo.setAdjustedAmt(Double.toString(arBalance
//							.getAdjustmentAmount()));
//				} else {
//					bo.setAdjustedAmt("0");
//				}
//				if (arBalance.getPaidAmount() != null) {
//					bo.setPaidAmtValue(arBalance.getPaidAmount());
//					bo.setPaidAmt(Double.toString(arBalance.getPaidAmount()));
//				} else {
//					bo.setPaidAmt("0");
//				}
//				if (pmSummary != null) {
//					bo.setPaymentType(pmSummary.getPaymentType().value(),
//							pmSummary.getLastFourDigits());
//					bo.setLastFour(pmSummary.getLastFourDigits());
//				}
//				if (arBalance.getStatus() != null)
//					bo.setStatus(arBalance.getStatus().value());
//				mainList.add(bo);
//
//				// add chargeback
//				if (arBalance.getStatus().value()
//						.equalsIgnoreCase("CHARGEBACK")) {
//					BillHistoryBO credit = new BillHistoryBO();
//					credit.setTransactionType("CHARGEBACK");
//					credit.setId(arBalance.getLineItemBalanceID());
//					credit.setBillId(arBalance.getLineItemBillID());
//					credit.setBillDate(Utility.toShortDateString(arBalance
//							.getChargebackPostDate()));
//					credit.setDescription(arBalance.getLineItemDescription()
//							+ "CHARGEBACK");
//					credit.setBilledAmt("0");
//					credit.setAdjustedAmt("0");
//					if (arBalance.getUnpaidAmount() != null) {
//						credit.setPaidAmtValue(arBalance.getUnpaidAmount());
//						credit.setPaidAmt(Double.toString(arBalance
//								.getUnpaidAmount()));
//					} else {
//						credit.setPaidAmt("0");
//					}
//					if (pmSummary != null) {
//						credit.setPaymentType(pmSummary.getPaymentType()
//								.value(), pmSummary.getLastFourDigits());
//						credit.setLastFour(pmSummary.getLastFourDigits());
//					}
//					credit.setStatus(arBalance.getStatus().value());
//					asscList.add(credit);
//				}
//			} catch (Exception e) {
//				log.error(e.getMessage());
//			}
//		}
//		return;
//	}

//	private static void addCreditRec(ARCreditView arCredit,
//			List<BillHistoryBO> mainList, List<BillHistoryBO> asscList) {
//		BillHistoryBO bo = null;
//		System.out.println("addCreditRec ");
//
//		if (arCredit != null) {
//			try {
//				bo = new BillHistoryBO();
//
//				bo.setTransactionType("CREDIT");
//				bo.setId(arCredit.getCredit().getCreditId());
//				bo.setBillDate(Utility.toShortDateString(arCredit.getCredit()
//                        .getIssueDate()));
//				bo.setSortDate(arCredit.getCredit().getIssueDate()
//                        .toGregorianCalendar().getTime());
//				bo.setDescription(arCredit.getCredit().getLineItemDescription());
//				if (arCredit.getCredit().getAmount() != null) {
//					bo.setBilledAmtValue(arCredit.getCredit().getAmount());
//					bo.setBilledAmt(Double.toString(arCredit.getCredit()
//							.getAmount()));
//				} else {
//					bo.setBilledAmt("0");
//				}
//				bo.setAdjustedAmt("0");
//				bo.setPaidAmt("0");
//				if (arCredit.getPaymentMethod() != null) {
//					bo.setPaymentType(arCredit.getPaymentMethod().get(0)
//							.getPaymentType().value(), arCredit
//							.getPaymentMethod().get(0).getLastFourDigits());
//					bo.setLastFour(arCredit.getPaymentMethod().get(0)
//                            .getLastFourDigits());
//				}
//				if (arCredit.getCredit().getStatus() != null)
//					bo.setStatus(arCredit.getCredit().getStatus().value());
//				if (arCredit.getCredit().getBillId() != null) {
//					bo.setBillId(arCredit.getCredit().getBillId());
//					asscList.add(bo);
//				} else {
//					mainList.add(bo);
//				}
//			} catch (Exception e) {
//				log.error(e.getMessage());
//			}
//		}
//		return;
//	}

//	public static List<BillHistoryBO> getBillHistoryBOList(
//			List<ARPaymentMethodBalance> arpmBal, List<ARCreditView> arCredit) {
//		List<BillHistoryBO> bhMainBOList = new ArrayList<BillHistoryBO>();
//		List<BillHistoryBO> bhAsscBOList = new ArrayList<BillHistoryBO>();
//		List<BillHistoryBO> bhBOList = new ArrayList<BillHistoryBO>();
//
//		// get balance
//		System.out.println("get balances");
//		if (arpmBal != null) {
//			System.out.println("balance::: number of activities " + arpmBal.size());
//			for (int i = 0; i < arpmBal.size(); i++) {
//				List<ARBalance> balanceList = arpmBal.get(i).getBalances()
//						.getARBalance();
//				PaymentMethodSummary pmSummary = arpmBal.get(i).getPmSummary();
//				System.out.println("balance::: number of arBalance "
//						+ balanceList.size());
//				for (int k = 0; k < balanceList.size(); k++) {
//					addBalanceRec(balanceList.get(k), pmSummary, bhMainBOList,
//							bhAsscBOList);
//				}
//			}
//		}
//
//		// get credit
//		System.out.println("get credits");
//		if (arCredit != null) {
//			System.out.println("credit::: number of activities " + arCredit.size());
//			for (int i = 0; i < arCredit.size(); i++) {
//				if (arCredit.get(i) != null) {
//					addCreditRec(arCredit.get(i), bhMainBOList, bhAsscBOList);
//				}
//			}
//		}
//
//		System.out.println("sort");
//		Collections.sort(bhMainBOList);
//
//		// add credits into a HashMap with billID as key
//		System.out.println("build hasp map");
//		HashMap hm = new HashMap();
//		for (int i = 0; i < bhAsscBOList.size(); i++) {
//			String billId = bhAsscBOList.get(i).getBillId();
//			BillHistoryBO credit = new BillHistoryBO();
//			credit = bhAsscBOList.get(i);
//			List<BillHistoryBO> creditList = null;
//
//			if (hm.containsKey(billId)) {
//				creditList = (List<BillHistoryBO>) hm.get(billId);
//				creditList.add(credit);
//				hm.put(billId, creditList);
//			} else {
//				creditList = new ArrayList<BillHistoryBO>();
//				creditList.add(credit);
//				hm.put(billId, creditList);
//			}
//		}

		// associate credits with bills
//		for (int j = 0; j < bhMainBOList.size(); j++) {
//			BillHistoryBO balance = bhMainBOList.get(j);
//			bhBOList.add(balance);
//			String bId = balance.getBillId();
//			if (bId != null) {
//				if (!hm.isEmpty() && hm.containsKey(bId)) {
//					List<BillHistoryBO> cl = new ArrayList<BillHistoryBO>();
//					cl = (List<BillHistoryBO>) hm.get(bId);
//					for (int k = 0; k < cl.size(); k++) {
//						bhBOList.add(cl.get(k));
//					}
//				}
//			}
//		}
//
//		return bhBOList;
//	}

//	public static PaymentInstrumentBO mapPaymentInstumentBO(
//			CustomerPaymentInstrumentView instrument) {
//		PaymentInstrumentBO bo = null;
//		System.out.println("map PaymentMethodInstrumentBO ");
//
//		if (instrument != null) {
//			try {
//				bo = new PaymentInstrumentBO();
//				bo.setInstrumentId(instrument.getId());
//				String paymentType = getMappingString(instrument
//						.getPaymentType());
//				bo.setPaymentType(paymentType);
//	            if(!"CHECKING".equalsIgnoreCase(paymentType) 
//	                    && !"INVOICE".equalsIgnoreCase(paymentType)
//	                    && !"MONEY_BOOKERS".equalsIgnoreCase(paymentType)
//	                    && !"PAYPAL_SUBSCRIPTION".equalsIgnoreCase(paymentType)) { 
//					bo.setExpiryDate(Utility.toShortDateString(instrument.getExpiryDate()));
//					bo.setExpirationMonth(instrument.getExpiryDate().getYear());
//					bo.setExpirationMonth(instrument.getExpiryDate().getMinute());
//	            }            
//				String lastFourDigits = instrument.getLastFourDigits()
//						.toString();
//				bo.setLastFourDigits(lastFourDigits);
//				bo.setInstrumentType(paymentType + " " + lastFourDigits);
//				bo.setAccountNumber(instrument.getAccountNumber());
//                if("CHECKING".equalsIgnoreCase(paymentType)) {
//					bo.setRoutingNumber(instrument.getRoutingNumber());
//					bo.setExpiryDate("");
//                }
//				bo.setCreditVerificationValue(instrument
//						.getCreditVerificationValue());
//				bo.setFirstName(instrument.getFirstName());
//				bo.setLastName(instrument.getLastName());
//				bo.setStreetAddress(instrument.getStreetAddress());
//				bo.setCity(instrument.getCity());
//				bo.setState(instrument.getState());
//				bo.setPostalCode(instrument.getZip());
//				bo.setCountry(instrument.getCountry());
//	                
//				if (instrument.isDefaultFlag() != null) {
//					bo.setDefaultValue(instrument.isDefaultFlag());
//					if (instrument.isDefaultFlag()) {
//						bo.setStatus("Default");
//					} else {
//						bo.setStatus("Active");
//					}
//				}
//			} catch (Exception e) {
//				log.error(e.getMessage());
//			}
//		}
//		return bo;
//	}

//	public static List<PaymentInstrumentBO> getInstrument(
//			ArrayOfCustomerPaymentInstrumentView aoi) {
//		List<PaymentInstrumentBO> list = new ArrayList<PaymentInstrumentBO>();
//		;
//		if (aoi != null) {
//			List<CustomerPaymentInstrumentView> instrumentList = aoi
//					.getCustomerPaymentInstrumentView();
//			System.out.println("instrument " + instrumentList.size());
//			for (int i = 0; i < instrumentList.size(); i++) {
//				PaymentInstrumentBO piBO = mapPaymentInstumentBO(instrumentList
//						.get(i));
//				if (piBO != null) {
//					list.add(piBO);
//				}
//			}
//		}
//		return list;
//	}
	
	
//	public static Map<String, SubscriptionView> getSwitchedFromToMap(SubscriptionView[] subList){
//	   Map<String, SubscriptionView> rez = new HashMap<String, SubscriptionView>();
//       if (subList != null){
//          for (SubscriptionView subscriptionView : subList) {
//            for (SubscriptionID id : subscriptionView.getId()) {
//               rez.put(id.getValue(), subscriptionView);
//            }
//          }
//       }
//	   return rez;
//	}
	

//	public static Map<String, Instrument> getInstrumentMap(InstrumentsView iView){
//		Map<String, Instrument> result = new HashMap<String, Instrument>();
//		
//		if (iView != null){
//		    for (ArrayOfInstrument instArr : iView.getInstruments()) {
//				for (Instrument inst : instArr.getInstrument()) {
//					result.put(base64Encode(inst.getId()), inst);
//				}
//			}	
//		}
//		return result;
//	}
	
	
//	public static String base64Encode(String someId){
//		return someId == null ? null : Base64.encode(someId.getBytes());
//	}
//	
//	public static String base64Decode(String hashStr){
//		return hashStr == null ? null : new String(Base64.decode(hashStr));
//	}


//	public static CustomerSubPaymentInfoBO mapSubPaymentInfoBo(SubscriptionView subView, String currencySymbol, Map<String, SubscriptionView> switchMap, Instrument instrument) {
//		if (subView == null)
//			return null;
//
//		String timestampFormat = "yyyy-MM-dd HH:mm:ss";
//		CustomerSubPaymentInfoBO bo = null;
//		PaymentInstrumentBO piBO = null;
//		List<PairDataBO> attributes = null;
//		PairDataBO pdBO = null;
//
//		try {
//			bo = new CustomerSubPaymentInfoBO();
//			//String subId = custSubPayInfo.getSubscriptionId().getValue();
//			String subId = subView.getId().get(0).getValue();
//			bo.setSubscriptionId(subId);
//			bo.setClearSubId((IDUtil.decode(subId)[0]));
//			String offerSubId = subView.getOfferSubscriptionId().get(0).getValue();
//			bo.setOfferSubscriptionId(offerSubId);
//			bo.setClearOfferSubscriptionId(IDUtil.decode(offerSubId)[0]);
//
//			bo.setSubscriptionName(subView.getProductName());
//			bo.setProductDisplayType(subView.getProductDisplayType());
//
//			bo.setBillableFlag(subView.isBillableFlag());
//			bo.setShareableFlag(subView.isShareableFlag());
//			bo.setActivateNowFlag(subView.isActivateNowFlag());
//			bo.setGenericFlag(subView.isGenericFlag());
//			bo.setRedirectFlag(subView.isRedirectFlag());
//
//			if (switchMap != null){
//				if (switchMap.get(subId).getOldSubscriptionId() != null) {
//					bo.setSwitchedFromObj(switchMap.get(base64Encode(switchMap.get(subId).getOldSubscriptionId().toString())));
//				}
//				
//				for (SubscriptionView kv : switchMap.values()) {
//					if (kv.getOldSubscriptionId() != null && subId.equals(base64Encode(kv.getOldSubscriptionId().toString()))) {
//						bo.setSwitchedToObj(kv);
//					}
//				}
//			}
//
//			bo.setV2EnabledBundle(subView.isV2EnabledBundle());
//			bo.setV2Enabled(subView.isV2Enabled());
//			//sale price returns in cents
//			bo.setSalePrice(currencySymbol + (subView.getSalePrice() == null ? 0 : subView.getSalePrice() / 100f));
//
//			if (subView.getProductName() != null && subView.getProductName().length() > 16) {
//				bo.setSubNameShort(subView.getProductName().subSequence(0, 15) + "...");
//			} else {
//				bo.setSubNameShort(subView.getProductName());
//			}
//
//			if (subView.isInFreeMonth() != null) {
//				if (subView.isInFreeMonth().booleanValue())
//					bo.setDiscountStatus("In Free Month");
//				bo.setInFreeMonth(subView.isInFreeMonth().booleanValue() ? "YES" : "NO");
//			}
//
//			if (subView.getFreeMonthStartDate() != null)
//				bo.setFreeMonthStartDate(Utility.toDateString(subView.getFreeMonthStartDate(), timestampFormat));
//
//			if (subView.getFreeMonthEndDate() != null)
//				bo.setFreeMonthEndDate(Utility.toDateString(subView.getFreeMonthEndDate(), timestampFormat));
//
//			bo.setSubscriptionDate(Utility.toDateString(subView.getSubscriptionStartDate(), timestampFormat));
//			
//			//FIXME customerSubPaymentInfo.rate != SubscriptionView.rateId?
//			if (subView.getRateId() != null && subView.getRateId().length() > 0) {
//				bo.setRate(Utility.getFormattedCurrencyAbsolute(Double.parseDouble(subView.getRateId()) / 100, currencySymbol));
//			}
//			bo.setNextBillDate(Utility.toDateString(subView.getNextBillCancelDate(), timestampFormat));
//			bo.setSubscriptionStatus(subView.getStatus().value());
//
//			//:TODO Hot fix for #0002700
//			if (subView.getOriginalCancelRequestedDate() != null && subView.getSubscriptionEndDate() != null) {
//				if (subView.getSubscriptionEndDate().toGregorianCalendar().getTime().after(new Date())) {
//					bo.setSubscriptionStatus("Pending Cancel");
//				}
//			}
//
//			String paymentMethodId = subView.getPaymentMethodId();
//			if (instrument != null) {
//				piBO = new PaymentInstrumentBO();
//				piBO.setInstrumentId(IDUtil.decode(paymentMethodId)[0]);
//				piBO.setAccountNumber(instrument.getPaymentInstrument().getAccountNumber());
//				piBO.setRoutingNumber(instrument.getPaymentInstrument().getRoutingNumber());
//				piBO.setExpiryDate(Utility.toDateString(instrument.getPaymentInstrument().getExpiryDate(), timestampFormat));
//				piBO.setCreditVerificationValue(instrument.getPaymentInstrument().getCreditVerificationValue());
//				piBO.setLastFourDigits((instrument.getPaymentInstrument().getLastFourDigits()).toString());
//				piBO.setPinNumber(instrument.getPaymentInstrument().getPinNumber());
//				piBO.setAuthorizeEmailAddress(instrument.getPaymentInstrument().getAuthorizeEmailAddress());
//				piBO.setIbanCode(instrument.getPaymentInstrument().getIbanCode());
//				piBO.setSwiftCode(instrument.getPaymentInstrument().getSwiftCode());
//				piBO.setAccountHolderName(instrument.getPaymentInstrument().getAccountHolderName());
//				piBO.setPaymentType(getMappingString(instrument.getPaymentInstrument().getPaymentType().value()));
//				bo.setPaymentInstrument(piBO);
//			}
//			OfferID o = subView.getOfferId();
//			if (o != null)
//				bo.setOfferId(o.getValue());
//			bo.setOfferName(subView.getOfferName());
//			bo.setOfferPrice(Utility.getFormattedCurrencyAbsolute(Double.parseDouble(String.valueOf(subView.getOfferPrice())) / 100, currencySymbol));
//			bo.setSvuProductId(subView.getSvuProductId());
//			bo.setProductId(subView.getProductId().getValue());
//
//			if (subView.isInTrial() != null) {
//				if (subView.isInTrial().booleanValue())
//					bo.setDiscountStatus("In Trial");
//				bo.setInTrial(subView.isInTrial().booleanValue() ? "YES" : "NO");
//			}
//			bo.setDiscountAmount(subView.getDiscountAmount());
//			if (subView.getTrialStartDate() != null) {
//				bo.setTrialStartDate(Utility.toDateString(subView.getTrialStartDate(), timestampFormat));
//			}
//
//			if (subView.getTrialEndDate() != null) {
//				bo.setTrialEndDate(Utility.toDateString(subView.getTrialEndDate(), timestampFormat));
//			}
//
//			if (subView.getOriginalCancelRequestedDate() != null) {
//				bo.setOriginalCancelRequestedDate(Utility.toDateString(subView.getOriginalCancelRequestedDate(), timestampFormat));
//			}
//
//			if (subView.getSubscriptionEndDate() != null) {
//				bo.setSubscriptionEndDate(Utility.toDateString(subView.getSubscriptionEndDate(), timestampFormat));
//			}
//
//			bo.setPaymentMethodId(paymentMethodId);
//			
////			if (subView.getSetupFee() != null && subView.getSetupFee().length() > 0) {
////				bo.setSetupFee(Utility.getFormattedCurrencyAbsolute(Double.parseDouble(subView.getSetupFee()) / 100, currencySymbol));
////			}
//			
//			bo.setSourceOfOrder(subView.getSourceOfOrder());
//			bo.setCancelDate(Utility.toDateString(subView.getCancelDate(), timestampFormat));
//			if (subView.getBillingFrequency() != null) {
//				bo.setBillingFrequency(getMappingString(subView.getBillingFrequency().value()));
//			} else {
//				bo.setBillingFrequency("");
//			}
//			bo.setOrderId(subView.getOrderId());
//			bo.setMerchantId(subView.getMerchantId());
//			bo.setContactEmail(subView.getContactEmail());
//			bo.setExternalGuid(subView.getExternalGuid());
//			bo.setOriginalCancelRequestedDate(Utility.toDateString(subView.getOriginalCancelRequestedDate(), timestampFormat));
//
//			
//			bo.setSubSharingView(subView.getSubSharingView() != null && !subView.getSubSharingView().isEmpty() ? subView.getSubSharingView().get(0) : null);
//			bo.setProductType(subView.getProductType());
//			bo.setSecondaryLuid(subView.getSecondaryLuid());
//			bo.setExternalSubscriptionId(subView.getExternalSubscriptionId());
//			
//			bo.setCycleDom(subView.getCycleDom());
//			
//			if (subView.getCycleDate() != null) {
//				bo.setCycleDate(Utility.toDateString(subView.getCycleDate(), timestampFormat));
//			}
//
//			if (subView.getProvisioningAttributes() != null && !subView.getProvisioningAttributes().isEmpty()) {
//				attributes = new ArrayList<PairDataBO>();
//				// Create the McAfee Download URL
//				int numOfField = 0;
//				String downLoadURL = null;
//				String passwd = null;
//				String ccid = null;
//				String partnerID = null;
//				String email = null;
//
//				for (ArrayOfAttribute aa : subView.getProvisioningAttributes()) {
//
//					List<Attribute> attList = aa.getAttribute();
//					if (attList != null && !attList.isEmpty()) {
//						for (Attribute attribute : attList) {
//							pdBO = new PairDataBO();
//							pdBO.setName(attribute.getAttributeName());
//							pdBO.setValue(attribute.getAttributeValue());
//							System.out.println("++++++++++++++ " + attribute.getAttributeName() + " ++++ " + attribute.getAttributeValue());
//							attributes.add(pdBO);
//
//							if ("EMAIL_ADDRESS".equalsIgnoreCase(attribute.getAttributeName())) {
//								email = attribute.getAttributeValue();
//								numOfField++;
//							} else if ("PASSWORD".equalsIgnoreCase(attribute.getAttributeName())) {
//								passwd = attribute.getAttributeValue();
//								numOfField++;
//							} else if ("ID".equalsIgnoreCase(attribute.getAttributeName())) {
//								ccid = attribute.getAttributeValue();
//								numOfField++;
//							} else if ("PARTNER_ID".equalsIgnoreCase(attribute.getAttributeName())) {
//								partnerID = attribute.getAttributeValue();
//								numOfField++;
//							}
//						}
//					}
//				}
//				if (numOfField == 4) { //we got all four paramenters to build the download URL
//					downLoadURL = "<a id='downLoadLink' href=\"http://us.mcafee.com/root/partnerlogin.asp?EMAIL_ADDRESS=" + email + "&PASSWORD=" + passwd + "&CCID=" + ccid
//							+ "&AFF_ID=" + partnerID + "&REMEMBER_ME=1&RETURN_URL=www.aol.com\">User's DownLoad McAfee Link</a>";
//					pdBO = new PairDataBO();
//					pdBO.setName("McAfee Download URL");
//					pdBO.setValue(downLoadURL);
//					attributes.add(pdBO);
//				}
//				bo.setAttributes(attributes);
//
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//		return bo;
//	}

//	public static Map<String ,CustomerSubPaymentInfoBO> getSubscription(
//			SubscriptionView[] subViewList, InstrumentsView instView,  String currencySymbol) {
//		
//		Map<String ,CustomerSubPaymentInfoBO> result = new HashMap<String ,CustomerSubPaymentInfoBO>();
//		if (subViewList != null) {
//			System.out.println("subscription " +subViewList.length);
//			
//			Map<String, SubscriptionView> switchMap = getSwitchedFromToMap(subViewList);
//			Map<String, Instrument> instMap = getInstrumentMap(instView);
//			
//			for (SubscriptionView sView : subViewList) {
//				CustomerSubPaymentInfoBO subBO = mapSubPaymentInfoBo(sView, currencySymbol, switchMap, instMap.get(sView.getPaymentMethodId()));
//				if (subBO != null) {
//					result.put(subBO.getSubscriptionId(), subBO);
//				}
//			}
//		}
//		return result;
//	}
	
	/**
	 * @param custSubPayInfo
	 * @param currencySymbol
	 * @param switchMap
	 * @return
	 */
//	public static CustomerSubPaymentInfoBO mapSubPaymentInfoBo(CustomerSubPaymentInfo custSubPayInfo, String currencySymbol) {
//		
//		String timestampFormat = "yyyy-MM-dd HH:mm:ss";
//	    CustomerSubPaymentInfoBO bo = null;
//		PaymentInstrumentBO piBO = null;
//		List<PairDataBO> attributes = null;
//		PairDataBO pdBO = null;
//
//		if (custSubPayInfo != null) {
//			try {
//				bo = new CustomerSubPaymentInfoBO();
//				String subId = custSubPayInfo.getSubscriptionId().getValue();
//				bo.setSubscriptionId(subId);
//				bo.setClearSubId((IDUtil.decode(subId)[0]));
//				String offerSubId = custSubPayInfo.getOfferSubscriptionId().getValue();
//				bo.setOfferSubscriptionId(offerSubId);
//				bo.setClearOfferSubscriptionId(IDUtil.decode(offerSubId)[0]);
//				
//				bo.setSubscriptionName(custSubPayInfo.getSubscriptionName());
//				bo.setProductDisplayType(custSubPayInfo.getProductDisplayType());
//				
//				bo.setBillableFlag(custSubPayInfo.isBillableFlag());
//				bo.setShareableFlag(custSubPayInfo.isShareableFlag());
//				bo.setActivateNowFlag(custSubPayInfo.isActivateNowFlag());
//				bo.setGenericFlag(custSubPayInfo.isGenericFlag());
//				bo.setRedirectFlag(custSubPayInfo.isRedirectFlag());
//				
//				bo.setV2EnabledBundle(custSubPayInfo.isV2EnabledBundle());
//				bo.setV2Enabled(custSubPayInfo.isV2Enabled());
//				//sale price returns in cents
//				bo.setSalePrice(currencySymbol + ( custSubPayInfo.getSalePrice() == null ? 0 : custSubPayInfo.getSalePrice() / 100f));
//														
//				if (custSubPayInfo.getSubscriptionName()!= null && custSubPayInfo.getSubscriptionName().length() >16){
//					bo.setSubNameShort(custSubPayInfo.getSubscriptionName().subSequence(0, 15)+ "...");
//				}
//				else{
//					bo.setSubNameShort(custSubPayInfo.getSubscriptionName());
//				}
//				
//				if (custSubPayInfo.isInFreeMonth()!=null) {
//					if (custSubPayInfo.isInFreeMonth().booleanValue()) bo.setDiscountStatus("In Free Month");					
//					bo.setInFreeMonth(custSubPayInfo.isInFreeMonth().booleanValue() ? "YES" : "NO");
//				}
//				
//				if (custSubPayInfo.getFreeMonthStartDate()!=null)
//					bo.setFreeMonthStartDate(Utility.toDateString(custSubPayInfo.getFreeMonthStartDate(), timestampFormat));
//				
//				if (custSubPayInfo.getFreeMonthEndDate()!=null)
//					bo.setFreeMonthEndDate(Utility.toDateString(custSubPayInfo.getFreeMonthEndDate(), timestampFormat));
//				
//				bo.setSubscriptionDate(Utility.toDateString(custSubPayInfo.getSubscriptionDate(), timestampFormat));
//                                    if(custSubPayInfo.getRate() != null && custSubPayInfo.getRate().length() > 0){
//                                            bo.setRate(Utility.getFormattedCurrencyAbsolute(Double.parseDouble(custSubPayInfo.getRate()) / 100, currencySymbol));
//                                    }
//				bo.setNextBillDate(Utility.toDateString(custSubPayInfo.getNextBillDate(), timestampFormat));
//				bo.setSubscriptionStatus(custSubPayInfo.getSubscriptionStatus().value());
//				
//				//:TODO Hot fix for #0002700
//				if(custSubPayInfo.getOriginalCancelRequestedDate() != null && custSubPayInfo.getSubscriptionEndDt() != null){
//					if(custSubPayInfo.getSubscriptionEndDt().toGregorianCalendar().getTime().after(new Date())){
//						bo.setSubscriptionStatus("Pending Cancel");
//					}
//				}
//				
//				
//				String paymentMethodId = custSubPayInfo.getPaymentMethodId();
//				if (custSubPayInfo.getPaymentInsrument() != null) {
//					piBO = new PaymentInstrumentBO();
//					piBO.setInstrumentId(IDUtil.decode(paymentMethodId)[0]);
//					piBO.setAccountNumber(custSubPayInfo.getPaymentInsrument()
//							.getAccountNumber());
//					piBO.setRoutingNumber(custSubPayInfo.getPaymentInsrument()
//							.getRoutingNumber());
//					piBO.setExpiryDate(Utility.toDateString(custSubPayInfo
//							.getPaymentInsrument().getExpiryDate(), timestampFormat));
//					piBO.setCreditVerificationValue(custSubPayInfo
//							.getPaymentInsrument().getCreditVerificationValue());
//					piBO.setLastFourDigits((custSubPayInfo
//							.getPaymentInsrument().getLastFourDigits())
//							.toString());
//					piBO.setPinNumber(custSubPayInfo.getPaymentInsrument()
//							.getPinNumber());
//					piBO.setAuthorizeEmailAddress(custSubPayInfo
//							.getPaymentInsrument().getAuthorizeEmailAddress());
//					piBO.setIbanCode(custSubPayInfo.getPaymentInsrument()
//							.getIbanCode());
//					piBO.setSwiftCode(custSubPayInfo.getPaymentInsrument()
//							.getSwiftCode());
//					piBO.setAccountHolderName(custSubPayInfo
//							.getPaymentInsrument().getAccountHolderName());
//					piBO.setPaymentType(getMappingString(custSubPayInfo
//							.getPaymentInsrument().getPaymentType().value()));
//					bo.setPaymentInstrument(piBO);
//				}
//				OfferID o = custSubPayInfo.getOfferId();
//				if (o != null)  bo.setOfferId(o.getValue());
//				bo.setOfferName(custSubPayInfo.getOfferName());
//				bo.setOfferPrice(Utility.getFormattedCurrencyAbsolute(Double.parseDouble(String.valueOf(custSubPayInfo.getOfferPrice())) / 100, currencySymbol));
//				bo.setSvuProductId(custSubPayInfo.getSvuProductId());
//				bo.setProductId(custSubPayInfo.getProductId());
//				
//				if (custSubPayInfo.isInTrial() != null) {
//					if (custSubPayInfo.isInTrial().booleanValue()) bo.setDiscountStatus("In Trial");	
//					bo.setInTrial(custSubPayInfo.isInTrial().booleanValue() ? "YES" : "NO");						
//				}
//				bo.setDiscountAmount(custSubPayInfo.getDiscountAmount());
//				if (custSubPayInfo.getTrialStartDate()!=null){
//					bo.setTrialStartDate(Utility.toDateString(custSubPayInfo.getTrialStartDate(), timestampFormat));
//				}
//				
//				if (custSubPayInfo.getTrialEndDate()!=null){ 
//					bo.setTrialEndDate(Utility.toDateString(custSubPayInfo.getTrialEndDate(), timestampFormat));
//				}
//				
//				if(custSubPayInfo.getOriginalCancelRequestedDate() != null){
//					bo.setOriginalCancelRequestedDate(Utility.toDateString(custSubPayInfo.getOriginalCancelRequestedDate(), timestampFormat));
//				}
//				
//				if(custSubPayInfo.getSubscriptionEndDt() != null){
//					bo.setSubscriptionEndDate(Utility.toDateString(custSubPayInfo.getSubscriptionEndDt(), timestampFormat));
//				}
//				
//				bo.setPaymentMethodId(paymentMethodId);
//                                    if(custSubPayInfo.getSetupFee() != null && custSubPayInfo.getSetupFee().length() > 0){
//                                            bo.setSetupFee(Utility.getFormattedCurrencyAbsolute(Double.parseDouble(custSubPayInfo.getSetupFee()) / 100, currencySymbol));
//                                    }
//				bo.setSourceOfOrder(custSubPayInfo.getSourceOfOrder());
//				bo.setCancelDate(Utility.toDateString(custSubPayInfo.getCancelDate(), timestampFormat));
//				if (custSubPayInfo.getBillingFrequency() != null){
//					bo.setBillingFrequency(getMappingString(custSubPayInfo.getBillingFrequency().value()));
//				}else {
//					bo.setBillingFrequency("");
//				}
//				bo.setOrderId(custSubPayInfo.getOrderId());
//				bo.setMerchantId(custSubPayInfo.getMerchantId());
//				bo.setContactEmail(custSubPayInfo.getContactEmail());
//				bo.setExternalGuid(custSubPayInfo.getExternalGuid());
//				bo.setOriginalCancelRequestedDate(Utility.toDateString(custSubPayInfo.getOriginalCancelRequestedDate(), timestampFormat));
//				
//				bo.setSubSharingView(custSubPayInfo.getSubSharingView());
//				bo.setProductType(custSubPayInfo.getProductType());
//				bo.setSecondaryLuid(custSubPayInfo.getSecondaryLuid());
//				bo.setExternalSubscriptionId(custSubPayInfo.getExternalSubscriptionId());
//				
//				
//				if (custSubPayInfo.getAttributes() != null
//						&& !custSubPayInfo.getAttributes().isEmpty()) {
//					attributes = new ArrayList<PairDataBO>();
//					// Create the McAfee Download URL
//					int numOfField = 0;
//					String downLoadURL =  null; 
//					String passwd = null;
//					String ccid = null;
//					String partnerID = null;
//					String email = null;
//					
//			
//					for (ArrayOfAttribute aa : custSubPayInfo.getAttributes()) {
//
//						List<Attribute> attList = aa.getAttribute();
//						if (attList != null && !attList.isEmpty()) {
//							for (Attribute attribute : attList) {
//								pdBO = new PairDataBO();
//								pdBO.setName(attribute.getAttributeName());
//								pdBO.setValue(attribute.getAttributeValue());
//								System.out.println("++++++++++++++ " + attribute.getAttributeName() + " ++++ " + attribute.getAttributeValue());
//								attributes.add(pdBO);
//								
//								if ("EMAIL_ADDRESS".equalsIgnoreCase(attribute.getAttributeName())){
//									email = attribute.getAttributeValue();
//									numOfField++;
//								}
//								else if ("PASSWORD".equalsIgnoreCase(attribute.getAttributeName())){
//									passwd = attribute.getAttributeValue();
//									numOfField++;
//								}
//								else if ("ID".equalsIgnoreCase(attribute.getAttributeName())){
//									ccid = attribute.getAttributeValue();
//									numOfField++;
//								}
//								else if ("PARTNER_ID".equalsIgnoreCase(attribute.getAttributeName())){
//									partnerID = attribute.getAttributeValue();
//									numOfField++;
//								}							
//							}
//						}
//					}
//					if (numOfField == 4){  //we got all four paramenters to build the download URL
//						downLoadURL = "<a id='downLoadLink' href=\"http://us.mcafee.com/root/partnerlogin.asp?EMAIL_ADDRESS=" + email +
//									"&PASSWORD=" + passwd + "&CCID=" + ccid + "&AFF_ID=" + partnerID + 
//									"&REMEMBER_ME=1&RETURN_URL=www.aol.com\">User's DownLoad McAfee Link</a>";		
//						pdBO = new PairDataBO();
//						pdBO.setName("McAfee Download URL");
//						pdBO.setValue(downLoadURL);
//						attributes.add(pdBO);
//					}
//					bo.setAttributes(attributes);
//					
//				}
//			} catch (Exception e) {
//				log.error(e.getMessage());
//				e.printStackTrace();
//			}
//		}
//
//		return bo;
//	}

//	public static List<CustomerSubPaymentInfoBO> getSubscription(ArrayOfCustomerSubPaymentInfo aos, String currencySymbol) {
//		List<CustomerSubPaymentInfoBO> list = new ArrayList<CustomerSubPaymentInfoBO>();
//		if (aos != null) {
//			List<CustomerSubPaymentInfo> subList = aos.getCustomerSubPaymentInfo();
//			System.out.println("subscription " + subList.size());
//
//			for (int i = 0; i < subList.size(); i++) {
//				System.out.println("sub " + i);
//				CustomerSubPaymentInfoBO subBO = mapSubPaymentInfoBo(subList.get(i), currencySymbol);
//				if (subBO != null) {
//					list.add(subBO);
//				}
//			}
//		}
//		return list;
//	}
	

//	public static Map<String, CustomerSubPaymentInfoBO> getSubscriptions(CustomerServiceDAO cs, String customerCurrencySymbol) {
//		Map<String, CustomerSubPaymentInfoBO> result = new HashMap<String, CustomerSubPaymentInfoBO>();
//		ObiResult or = cs.getCustomerSubPaymentInfo();
//
//		CustomerSubPaymentInfo[] custSubPayInfoList = (CustomerSubPaymentInfo[]) or.getObiResultObj();
//		System.out.println(custSubPayInfoList == null ? "Subscription list is null." : "Size of Subscription list is " + custSubPayInfoList.length);
//		for (CustomerSubPaymentInfo custSubPayInfo : custSubPayInfoList) {
//			if (custSubPayInfo != null) {
//				CustomerSubPaymentInfoBO custSubBO = Utility.mapSubPaymentInfoBo(custSubPayInfo, customerCurrencySymbol);
//				if (custSubBO != null) {
//					log.info("##custSubPayInfo=" + custSubPayInfo);
//					result.put(custSubBO.getSubscriptionId(), custSubBO);
//				}
//			}
//
//		}
//
//		return result;
//	}

	public static String getMappingString(String key) {
		String value = null;

		//if (key != null && !key.isEmpty()) {
         if(isNotNullOrEmpty(key)){
        	 try {
        		 value = ResourceBundle.getBundle("mcare-mapping").getString(key);
        	 }catch (MissingResourceException e){
        		 System.out.println("Can't find '" + key + "'");
        		 value = key;
        	 }
			//value = value != null ? value : key;
		}

		return value;
	}
	
	public static String getShortString(String origStr, int len) {
		
		String value = null;

		if (origStr != null && origStr.length()> len) {
			value = origStr.subSequence(0, len-3) + "...";
		}

		return value;
	}

	public static String convertDate(String oriDate) {
		String retDate = null;
		try {
			Date date = sdfNeedChanged.parse(oriDate);
			retDate = sdf.format(date);

		} catch (Exception e) {
//			//log.error("FormatString error the orginal date is not in the format of yyyy-mm-dd: <"
//					+ oriDate + ">");
//			log.error("Date parsing errored out", e);
		}
		if (retDate == null) {
			retDate = oriDate;
		}

		return retDate;
	}

	

	
        
//	public static Long getBillingMonth(){
//		
//		String bmStr = getEnvironmentProperty("mcare.billing.defaultMonth");
//		Long bmLong = null;
//		//if (bmStr != null && !bmStr.isEmpty()){
//                if(isNotNullOrEmpty(bmStr)){
//			try{
//				bmLong = Long.valueOf(bmStr);
//			}
//			catch(Exception e){
//				log.error("mcare.billing.defaultMonth in the property file was specified wrong!");
//				bmLong = new Long(2);
//			}
//		}
//		return bmLong;
//	}
	
//	public static String escapeHtml(String input) {
//	     return StringEscapeUtils.escapeHtml(input).replaceAll("'", "\\\\'");
//
//	}
	
    
    

 
 

    
    
	public static XMLGregorianCalendar dateStringToXMLGregorianCalendar(
			String inDate) throws DatatypeConfigurationException {
		XMLGregorianCalendar outDate = null;
		int year = -1;
		int month = -1;
		int day = -1;
		int hour = -1;
		int minute = -1;
		int second = -1;
		int millisecond = -1;
		int timezone = -1;
		String amPmMarker = null;
		String token = "/";
		StringTokenizer strTokDate = new StringTokenizer(inDate);
		String tok = null;
		try {
			while (strTokDate.hasMoreElements()) {
				tok = strTokDate.nextToken(token);
				if (tok.startsWith("/")) {
					tok = tok.substring(1, 5);
				}
				if (tok.startsWith(":") && tok.length() > 1) {
					tok = tok.substring(1, tok.length());
				}
				if (tok.startsWith(" ") && tok.length() > 1) {
					tok = tok.substring(1, tok.length());
				}
				System.out.println("date token = '" + tok + "'");
				if (month == -1) {
					month = Integer.parseInt(tok);
				} else if (day == -1) {
					day = Integer.parseInt(tok);
					token = " ";
				} else if (year == -1) {
					year = Integer.parseInt(tok);
					token = ":";
				} else if (hour == -1) {
					hour = Integer.parseInt(tok);
				} else if (minute == -1) {
					minute = Integer.parseInt(tok);
					token = " ";
				} else if (second == -1) {
					second = Integer.parseInt(tok);
				} else if (amPmMarker == null) {
					amPmMarker = new String(tok);
					break;
				}

			}
		} catch (NumberFormatException e) {
			System.out.println(
					"date token = '" + tok + "' caused exception:"
							+ e.getMessage());
		}
		if (year == -1)
			year = 0;
		if (month == -1)
			month = 0;
		if (day == -1)
			day = 0;
		if (hour == -1)
			hour = 0;
		if (minute == -1)
			minute = 0;
		if (second == -1)
			second = 0;
		if (millisecond == -1)
			millisecond = 0;
		if (timezone == -1)
			timezone = 0;

		if (amPmMarker != null) {
			if (amPmMarker.equalsIgnoreCase("AM") && hour == 12) {
				hour = 0;
			} else if (amPmMarker.equalsIgnoreCase("PM") && hour != 12
					&& hour > 0) {
				hour += 12;
			}
		}

		outDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(year,
				month, day, hour, minute, second, millisecond, timezone);

		return outDate;
	}	
	
	/**
	 * This method URL dncodes a UTF-8 encoded string
	 */
	public static String decodeString(String strval) {
		String decodedStrVal = "";
		//if (strval == null || strval.length() == 0) {
                if(isNullOrEmpty(strval)){
			return null;
		} else {
			try {
				decodedStrVal = URLDecoder.decode(strval, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return decodedStrVal;
	}		
	
	public static int getListPreEventMaxRecordValue() {
		int maxNumRecord = 500;
		try {
			maxNumRecord = Integer.valueOf(Utility
					.getEnvironmentProperty("mcare.list.preevent.max.num.record"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxNumRecord;
	}

//	public static Map<String, Object> parseObiResult(ObiResult result) {
//		Map<String, Object> resultObject = new HashMap<String, Object>();
//		resultObject.put("errorCode", "0");
//		resultObject.put("errorMsg", "Success");
//
//		if (result != null) {
//			ObiErrorObj obiErrObj = result.getObiErrorObj();
//			if (obiErrObj == null) {
//				if (result.getObiResultObj() == null) {
//					resultObject.put("errorCode", result.getObiErrorObj()
//							.getErrCode());
//					resultObject.put("errorMsg", result.getObiErrorObj()
//							.getErrMsg());
//				}
//			} else {
//				resultObject.put("errorCode", result.getObiErrorObj()
//						.getErrCode());
//				resultObject.put("errorMsg", result.getObiErrorObj()
//						.getErrMsg());
//			}
//		} else {
//			resultObject.put("errorCode", "mCare-1000");
//			resultObject.put("errorMsg",
//					"Error: Server couldn't access obi server ...");
//		}
//		return resultObject;
//	}

//	public static String mapErrorJsonString(Map<String, Object> map) {
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("errorCode", (String) map.get("errorCode"));
//		jsonObject.put("errorMsg", (String) map.get("errorMsg"));
//		return jsonObject.toString();
//	}
	/**
	 * Gets the authorize email address attached to a given payment method
	 * @param paymentMethodId the payment method identifier
	 * @param merchantId the merchant identifier
	 * @param guid the customer identifier
	 * @param request the HTTP request
	 * @return the requested email address, or <code>null</code> if not available 
	 */
//	public static String fetchPaymentMethodEmail(String paymentMethodId, String merchantId, String guid, String userLoginId, HttpServletRequest request) {
//		System.out.println("======  fetchPaymentMethodEmail("+ paymentMethodId+")");
//		String email = null;
//		// fetch the list of available payment methods for this merchant and customer	
//		System.out.println("::: fetching the instruments" );
//	    WalletServiceDAO ws = getWalletServiceDAO(request, merchantId, guid, userLoginId);
//        if (ws!=null) {
//	        PaymentDetailLevel pdl = PaymentDetailLevel.fromValue("DETAIL");
//	        InstrumentsView iv = null;
//			ObiResult or = ws.listInstruments(pdl, true);
//			if (or.getObiResultObj() != null) {
//				iv = (InstrumentsView ) or.getObiResultObj();
//			}				 
//	        if (iv!=null && iv.getNumberInstruments() != null) {
//	            if (iv.getNumberInstruments()!=0) {
//	                ArrayOfInstrument aoi = iv.getInstruments().get(0);
//	                List<Instrument> insl = aoi.getInstrument();
//	                PaymentInstrumentBO pibo;
//	                for(Instrument ins: insl) {
//	                	pibo = convertInstrumentToBo(ins);
//	                	if (pibo!=null && pibo.getInstrumentId().equalsIgnoreCase(paymentMethodId)) {
//	                		// found
//	                		email = pibo.getAuthorizeEmailAddress();
//	                		break;
//						}
//	                }
//	            }
//	        }
//		}
//		return email;
//	}

//	public static ObiGroupHierarchy[] getObiGroupHierarchy(
//			HttpServletRequest request, String merchantId, String customerGUID, String userLoginId) {
//		if (obiGroupHierarchy == null || obiGroupHierarchy.length == 0) {
//			EventServiceDAO preEventService = getEventServiceDAO(
//					request, merchantId, customerGUID, userLoginId);
//			if (preEventService == null) {
//				return null;
//			}
//			obiGroupHierarchy = preEventService.listGroupHierarchy();
//		}
//		return obiGroupHierarchy;
//	}	
	
	public static boolean contains(List<String> contents, String name){
		boolean retCode = false;		
		//if ( contents != null && contents.size() > 0 && name != null && !name.isEmpty()){
                if ( contents != null && contents.size() > 0 && isNotNullOrEmpty(name)){
                        for (String item: contents){
				if (item.equalsIgnoreCase(name)){
					retCode = true;
					break;
				}
			}			
		}else{
			System.out.println("Utility:contains -- At least one of the input is null or empty");
		}		
		return retCode;
	}
	
//	public static String getShortString(String oriStr){		
//		String retStr = null;		
//		//if (oriStr != null & !oriStr.isEmpty()){
//                if(isNotNullOrEmpty(oriStr)){
//			if (oriStr.length() > 10){
//				retStr = oriStr.substring(0, 3) + "..." + oriStr.substring(oriStr.length()-4, oriStr.length());
//			}else{
//				retStr = oriStr;
//			}
//		}			
//		return retStr;
//	}
	
	
//	public static String unescape(String str){
//		String retString = null;
//		try{
//			retString = URLDecoder.decode(str,"UTF-8"); 
//		}
//		catch (Exception e){
//			log.error("Decoding escaped string failed. The orginal string =<" + str + ">");
//			log.error("Decoding Error Message: =<" + e.getMessage() + ">");
//		}
//		
//		return retString;
//	}
	
//	public static DateRange getDateRange(int numOfMonths){
//		DateRange dateRange = null;
//		XMLGregorianCalendar startDate = null;
//		XMLGregorianCalendar endDate = null;
//		GregorianCalendar gc = null;
//		GregorianCalendar endGc = null;
//
//		dateRange = new DateRange();
//		gc = new GregorianCalendar();
//		endGc = new GregorianCalendar();
//		endGc.add(Calendar.MONTH, -numOfMonths);
//		try {
//			endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
//			startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(
//					endGc);
//		} catch (Exception e) {
//			log.warn("Problem setting XMLGregorianCalendar to current data/time",e);
//		}
//		dateRange.setEnd(endDate);
//		dateRange.setStart(startDate);
//		
//		return dateRange;
//	}
//	
//	/**
//	 * @see StringEscapeUtils#escapeJavaScript(String)
//	 * 
//	 * @param stringToEscape
//	 * @return
//	 */
//	public static String escapeJavaScript(final String stringToEscape) {
//		return StringEscapeUtils.escapeJavaScript(stringToEscape);
//	}

 

 
}