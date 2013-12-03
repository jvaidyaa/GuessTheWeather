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

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


public class SavePredictionMB extends AbstractMessageBean {
	private static final String INSERT_WEATHER = "INSERT INTO weather_data (weather_id, email_address, zip_code, weather_value, weather_date)" +
			" VALUES (weather_sq.nextval, :email_address, :zip_code, :weather_value, :weather_date ";

	//private static Log LOG = LogFactory.getLog(SavePredictionMB.class);
	
	private static BasicDataSource dataSource = null;
	
	private List<String> contractNames;
	public String contractName;
	
	public String getContractText() {
		return contractText;
	}

	public void setContractText(String contractText) {
		this.contractText = contractText;
	}

	private String contractText;
	
	private String jsonData;
	
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	

	public List<String> getContractNames() {
		return contractNames;
	}

	public void setContractNames(List<String> contractNames) {
		this.contractNames = contractNames;
	}

	public static BasicDataSource getDataSource() throws Exception {
		if (dataSource == null) {

			Context initCtx = new InitialContext();
			System.out.println("Got initial context");
			dataSource = (BasicDataSource) initCtx
					.lookup("java:comp/env/jdbc/ObiConfigDB");
			if (null == dataSource.getUsername()) {
				System.out.println("username is null");
				//setDataSourceProperties();
			}
		}
		return dataSource;
	}

	@Override
	public void documentBuilder() {
		
		System.out.println("inside contract management ");

		if (request.getParameter("save") != null) {
			System.out.println("Inside save " );
			String emailAddress = null;
			String zipCode = null;
			String weatherValue = null;
			String weatherDate = null;
			Enumeration params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String pName = "" + params.nextElement();
				String pValue;
				try {
						pValue = URLDecoder.decode(request.getParameter(pName),
								"UTF-8").trim();
	
						//if (pValue == null || pValue.isEmpty() || pValue.length() > 256) {
                                                if (Utility.isNullOrEmpty(pValue) || pValue.length() > 256) {
							throw new Exception("Bad propery value " + pName + "="+ request.getParameter(pName));
					        }
				} catch (Exception e1) {
					System.out.println("bad param " + e1);
					jsonData = "{msg:\"Bad data " + pName + "\"}";
					return;
				}
	
				System.out.println("Get field " + pName + " " + pValue);
				
				if ("save".equals(pName)) {
					continue;
				}
				
				if ("email".equals(pName)) {
					System.out.println("email address:  " + pValue);
					emailAddress = pValue;
					continue;
				}
				if ("zip".equals(pName)) {
					System.out.println("zip code:  " + pValue);
					zipCode = pValue;
					continue;
				}
				if ("prediction".equals(pName)) {
					System.out.println("weather value:  " + pValue);
					weatherValue = pValue;
					continue;
				}
				if ("dop".equals(pName)) {
					System.out.println("dop value:  " + pValue);
					weatherDate = pValue;
					continue;
				}
			}
			
			//createContract here
//			String luid = customerGUID;
//			Contract contract = new Contract();
			//Calendar today = Calendar.getInstance();
//			XMLGregorianCalendar today = null;
//			try {
//			  today = DatatypeFactory.newInstance()
//			    .newXMLGregorianCalendar(
//			        new GregorianCalendar());
//			  
//			} catch (DatatypeConfigurationException e) {
//			  e.printStackTrace();
//			}
			
			Connection conn = null;
			PreparedStatement stmt = null;

			try {
				conn = getDataSource().getConnection();
				
				stmt = conn.prepareStatement(INSERT_WEATHER);
				
				stmt.setString(1, emailAddress);
				stmt.setString(2, zipCode);
				stmt.setString(3, weatherValue);	
				stmt.setString(4,  weatherDate);
				
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
			 

			
//			contract.setAcceptedDate(today);
//			contract.setStatus(ContractStatusType.NOT_ACCEPTED);
//			
//			contract.setTosName(tosName);
//
//			if(contract.getContractAttributes() != null ){
//				ArrayOfAttribute attributes = new ArrayOfAttribute();
//				Attribute wbsAtribute = new Attribute();
//				wbsAtribute.setAttributeName("wbsid");
//				wbsAtribute.setAttributeValue(wbsValue);
//				attributes.getAttribute().add(wbsAtribute);
//				contract.getContractAttributes().add(attributes);
//			}
			
			System.out.println(" before create contract " );
			try {
//				ContractServiceDAO contractService = Utility.getContractServiceDAO(request, merchantId, customerGUID, userLoginId);
//				Contract retContract = contractService.createContract(contract);
//				System.out.println(" after create contract " );
//				if ( retContract != null){
//					ContractID conId = retContract.getContractID();
//					if (conId != null){
//						sendContractEmail(request,merchantId,customerGUID);
//					}
//				}
				
			
//			} catch (Exception e) {
//				e.printStackTrace();
//				LOG.warn("Can't create contract data on server "
//						+ e.getMessage());
//				jsonData = "{msg:\"Server error\"}";
//				return;
//			}
			
//			jsonData = "{msg:\"ok\"}";
//		}
		
//		if (request.getParameter("expire") != null ) {
//			System.out.println("inside expire contract on the backend");
//			try {
//				String expire = request.getParameter("expire");
//				if ("1".equalsIgnoreCase(expire)){
//					ContractServiceDAO contractService = Utility.getContractServiceDAO(request, merchantId, customerGUID, userLoginId);
//					
//					Contract con = new Contract();
//					String conId = request.getParameter("conId");
//					String tosName = request.getParameter("conName");
//					ContractID contractId = new ContractID();
//					contractId.setValue(conId);
//					con.setContractID(contractId);
//					con.setTosName(tosName);
//					con.setStatus(ContractStatusType.INACTIVE);
//					
//					Contract retContract = contractService.updateContract(con);
//					if (retContract != null){
//						ContractStatusType conStatus = retContract.getStatus();
//						if (conStatus.equals(ContractStatusType.INACTIVE)){
							//sendContractEmail()
//						}
//					}
//					jsonData = "{msg:\"ok\"}";
//				}
				
			} catch (Exception e) {
				e.printStackTrace();
//				LOG.warn("Can't retrive tos from server " + e.getMessage());
				return;
			}
		}
//		String  tosName = request.getParameter("tosName");
//		if (tosName != null ){
//			System.out.println(" in view TOS");
//			ContractServiceDAO contractService = Utility.getContractServiceDAO(request, merchantId, customerGUID, userLoginId);
//			TOS tos = contractService.getTerm(tosName, null);
//			contractText = tos.getContractLongText();
//			System.out.println(" contract text is " + contractText);
//		}
//		
//		if (request.getParameter("save") == null && request.getParameter("expire") == null
//				&& tosName == null) {
//			System.out.println(" in list TOS");
//
//			try {
//				ContractServiceDAO contractService = Utility.getContractServiceDAO(request, merchantId, customerGUID, userLoginId);
//				
//				TOSDefinition[] tosdefArray = contractService.listTOS();
//				ContractView[] contractViewArray = contractService.listContracts();
//				boolean addContractToList = true;
//				if (tosdefArray != null){
//					contractNames = new ArrayList();
//					
//					for (int i=0;i< tosdefArray.length; i++){
//						contractName = tosdefArray[i].getTosName();
//						
//						if ("Patch Do Not assign".equals(contractName)){
//							continue;
//						}
//						
//						if (contractViewArray !=null){
//							
//							System.out.println("contractName " + contractName);
//							addContractToList = true;
//							for (int k=0;k<contractViewArray.length;k++){
//								if (contractViewArray[k].getTosName().equalsIgnoreCase(contractName)){
//									addContractToList = false;
//								}
//							}
//							if (addContractToList == true){
//								contractNames.add(contractName);
//							}
//						}else{
//							System.out.println("inside else contractName " + contractName);
//							if (addContractToList == true){
//								contractNames.add(contractName);
//							}
//						}
//					}
//
//				}
//				
//				if ( contractName != null){
//					System.out.println("contractName.size " + contractName.length()); 
//					Collections.sort(contractNames);
//				}
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				LOG.warn("Can't retrive tos from server " + e.getMessage());
//				return;
//			}
//		}
	}
	
    private void sendContractEmail(HttpServletRequest request,
            String merchant, String luid) throws Exception {
        System.out.println("inside sendContractEmail " + merchant + "  " + luid);
//        CatalogServiceDAO cts = Utility.getCatalogServiceDAO(request, merchant, luid, userLoginId);
//        if (cts == null) {
//            return;
//        }
//        PaymentServiceDAO ps = Utility.getPaymentServiceDAO(request, merchant, luid, userLoginId);
//        if (ps == null) {
//            return;
//        }
//        CustomerMessagingDAO cms = Utility.getCustomerMessagingDAO(request, merchant, luid, userLoginId);
//        
//
//        try {
//            registerMyHostnameVerifier();
//
//            System.out.println("calling listPaymentMethods");
//            //System.out.println("endpoint is " + Utility.getEnvironmentProperty("obi.endpoint"));
//            PaymentMethodView[] pmView = ps.listPaymentMethods();
//            PaymentMethodID pmId = null;
//            PaymentMethod pm = null;
//            String emailAddress = null;
//            String lineItemId = null;
//            UserName uname = null;
//            String userName = null;
//            if (pmView != null) {
//                for (int i = 0; i < pmView.length; i++) {
//                    pmId = pmView[i].getId();
//                    System.out.println(" obtained pmid " + pmId.getValue());
//                    lineItemId = pmView[i].getLineItemID();
//                    System.out.println(" obtained lineItemid " + lineItemId);
//                    break;
//                }
//            }
//            if (pmId != null) {
//                System.out.println(" getting pm " + pmId.getValue());
//                pm = ps.getPaymentMethod(pmId.getValue());
//            }
//
//            if (pm != null) {
//                BillingInfo billInfo = pm.getBillingInformation();
//                if (billInfo != null) {
//                    emailAddress = billInfo.getEmailAddress();
//                }
//                uname = billInfo.getName();
//                if (uname != null) {
//                    userName = uname.getFirstName() + " " + uname.getLastName();
//                    if ("FIRST_NAME".equalsIgnoreCase(uname.getFirstName())) {
//                        userName = billInfo.getEmailAddress();
//                    }
//                }
//            }
//            System.out.println(" email address is " + emailAddress);



//            EmailRequest emailRequest = new EmailRequest();
//            ArrayOfEmailAddress emailAddressArray = new ArrayOfEmailAddress();
//            EmailAddress address = new EmailAddress();
//            address.setValue(emailAddress);
//            emailAddressArray.getEmailAddress().add(address);
//            //emailAddressArray[0] = address;



//            emailRequest.setToAddresses(emailAddressArray);
//
//
//            System.out.println("Retrieving EmailTemplate from Catalog service using Line Item ID '" + lineItemId + "', Offer ID " + null + "', and Email Template Function " + "ContractorAgreement");
//
//            // VERIFY_TEMPLATE_NAME
//            EmailTemplate emailTemplate = cts.getEmailTemplate(
//                    lineItemId, null,
//                    "ContractorAgreement");
//
//            if (emailTemplate != null) {
//                System.out.println("Email template '" + emailTemplate.getName() + "' retrieved from Catalog service.");
//                emailRequest.setMessageName(emailTemplate.getName());
//
//                Attribute[] attributes = new Attribute[1];
//                // Attribute attr = new Attribute();
//
//                Attribute attr = new Attribute();
//                attr.setAttributeName("Username");
//                attr.setAttributeValue(userName);
//
//
//                ArrayOfAttribute attribArray = new ArrayOfAttribute();
//                attribArray.getAttribute().add(attr);
//
//                emailRequest.setMessageVariables(attribArray);
//
//                System.out.println("Sending email using Customer Messaging Service");
//                // Create an email request
//                cms.sendEmail(emailRequest);
//                System.out.println("Sent " + emailTemplate.getName() + " E-mail to <" + emailAddress + ">");
//            } else {
//                System.out.println("No email template returned from Catalog service.");
//            }
//        } catch (UnsupportedOperationException e) {
//            e.printStackTrace();
//            System.out.println("Exception occured sending email, UnsopportedOperatoinException");
//            throw e;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Generic Exception occured sending e-mail \n" + e.getMessage());
//            throw e;
//        }
    }

    /**
     * Registers a hostname verifier.
     */
    private void registerMyHostnameVerifier() {
        javax.net.ssl.HostnameVerifier myHv = new javax.net.ssl.HostnameVerifier() {

            public boolean verify(String hostName,
                    javax.net.ssl.SSLSession session) {
                return true;
            }
        };
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(myHv);
    }


}
