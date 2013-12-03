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
 * This is the interface implemented by the AbstractMessageBean to make a framework
 * However if need be a different controller can extend this to have no affect on 
 * Business Message beans.
 */
package com.hack.guess.mbean;

import javax.servlet.jsp.PageContext;

public interface MessageBean {
	public void setDependencies(PageContext request);
	// Generate the Data for Frontend by calling the respective APIs in steps
	// (using Builder Pattern)
	public void documentBuilder();
}
