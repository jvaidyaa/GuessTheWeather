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


	//public static XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
	private  String dop = null;
	private  String email = null;
	private  String zip = null;
	private  String prediction = null;
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