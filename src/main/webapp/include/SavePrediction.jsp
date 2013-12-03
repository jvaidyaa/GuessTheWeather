<%--
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
--%>

<%@ page import="com.hack.guess.mbean.SavePredictionMB" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="cookieBean" class="com.hack.guess.mbean.CookieBean" scope="request">
	<jsp:setProperty name="cookieBean" property="cookies" value="${pageContext}"/>
</jsp:useBean>
<jsp:useBean id="messageBean" class="com.hack.guess.mbean.SavePredictionMB" scope="request">
	<jsp:setProperty name="messageBean" property="userLoginId" value="${cookieBean.agentGuid}"/>
    <jsp:setProperty name="messageBean" property="merchantId" value="${cookieBean.merchantId}"/>
    <jsp:setProperty name="messageBean" property="customerGUID" value="${cookieBean.customerGuid}"/>
    <jsp:setProperty name="messageBean" property="customerCurrency" value="${cookieBean.customerCurrency}"/>
    <jsp:setProperty name="messageBean" property="customerCountry" value="${cookieBean.customerCountry}"/>
    <jsp:setProperty name="messageBean" property="dependencies" value="${pageContext}"/>
</jsp:useBean>
	<script type="text/javascript">
	
	var isEmpty = function(value){
		if(value != undefined || value != null){
			return $.trim(value).length == 0;	
		}
		return true;
	};
	
	
	var getInputFieldValue = function(selector){
		var value = $(selector).val();
		if(value != null){
			value = $.trim(value);
		}
		return value;
	};
	
	var isNotEmpty = function(value){
		return !isEmpty(value);
	};
	
	var isLoadComplete = false;
	
	function GUIDTooltipShow() {
		var text = 	"<b>GUID</b> of any past transaction."+
					"If you enter a GUID which already <b>exists</b>"+
					" inside the mCare`s database, then the status page"+
					" of that GUID <b>'ll be displayed</b>."
		tooltip.show(text);
	}

	function paymentIdTooltipShow() {
		var text = 	"<b>Payment Id</b> of any past transaction.";
		tooltip.show(text);
	}
	
        var npValidator;

        var validateAmount = function(amount) {
          var format = /^[0-9]*(\.[0-9]{2})?$/
          if (!format.test(amount.value)) {
            alert("Invalid amount Format. Please correct and submit again." + "<" + amount.value + ">");
          }
          return true;
        } // end of validateAmount

        var generateGuid = function() {           
              var acctIdStr = $.trim($("#np_accountId").val());
              while (acctIdStr.length < 29) {
                acctIdStr = '0' + acctIdStr;
              }
              $("#lbl_np_generatedGuid").html("patchdeals-" + acctIdStr);
              $("#np_generatedGuid").val("patchdeals-" + acctIdStr);
              showTokenOnProviderPage($("#np_generatedGuid").val());
        }

	function showTokenOnProviderPage(guid){
		var timePeriod = "<%=Utility.getEnvironmentProperty("mcare.patchdeals.email.hourToExpire")%>";
		generateEmailTokenLink(guid,timePeriod,'showTokenOnProviderPagehandler');
	}
	
	function showTokenOnProviderPagehandler(authToken){
		var timePeriod = "<%=Utility.getEnvironmentProperty("mcare.patchdeals.email.hourToExpire")%>";
		var baseURL = "<%= messageBean.getSelfcareBaseURL() %>";
                var merchantId = '${messageBean.merchantId}';
		var emailURL = baseURL +authToken;
		$("#np_tokenvaliditySpan").html(timePeriod);
		$("#np_validToken").html(emailURL);						
	}
	
    var crateAccount = function() {
      npValidator.resetForm();
      npValidator.hideStars();        
      npValidator.validateAllField();
      if (npValidator.hasErrors()) return false;
	  var nameIndicator = $('input:radio[name=np_nameId]:checked').val();	
	  var acctId = $.trim($("#np_accountId").val());
          var firstName = $.trim($("#np_firstName").val());
          var lastName = $.trim($("#np_lastName").val());
	  var businessName = $.trim($("#np_businessName").val());
          var email = $.trim($("#np_emailAddress").val());
          var toEmail = $.trim($("#np_toAddress").text());
          var guid = $.trim($("#np_generatedGuid").val());
	  var sendCustomerMail = '';
	  if ($('#np_emailCustomer').attr('checked')) {
		 sendCustomerMail = 'Y';
	  }else{
		sendCustomerMail = 'N';
	  }
	  
	  var loadAccountIndicator = '';	
      if ($('#np_loadAccountId').attr('checked')) {
		 loadAccountIndicator = 'Y';
	  }else{
		loadAccountIndicator = 'N';
	  }
	  
      if (acctId == null || acctId == "") {
        //alert("Please enter account id for this vendor.");
        return false;
      }
	  if(nameIndicator == 'P'){
		  if (firstName == null || firstName == "") {
			//alert("Please enter vendor's first name.");
			return false;
		  }
		  if (lastName == null || lastName == "") {
			//alert("Please enter vendor's last name.");
			return false;
		  }
		}else{
			if (businessName == null || businessName == "") {
			//alert("Please enter vendor's last name.");
			return false;
		  }
		}
      if (email == null || email == "") {
        //alert("Please enter vendor's email");
        return false;
      }

      if (confirm("Are you sure you want to add this vendor to OBI ?")) {
		$(".popup_error").hide();
        // send the request
        var ajaxParams = {"url": "include/PatchdealsVendorMethodAjax.jsp",
                            dataType: "html",
                            data: {"action": "createGuid",
                                    "acctId": acctId,
                                    "nameIndicator": nameIndicator,
                                    "firstName": firstName,
                                    "lastName": lastName,
                                    "businessName": businessName,
                                    "email": email,
                                    "toEmail": toEmail,
                                    "customerGuid": guid,
                                    "sendCustomerMail": sendCustomerMail
                            },
                            contentType: null
        };
        var callbackFn = {
              onSuccess: function(data) {
                    var alreadyExists;
                    var canStatus;
                    var status;
                    try {
                      var pos = data.indexOf("{");
                      var len = data.length;
                      var fixedData = data.substring(pos, len);
                      canStatus = eval('(' + fixedData + ')');
                      status = canStatus.status;
                      alreadyExists = canStatus.alreadyExists;
                    } catch (e) {
                    }
                    if (status == "true") {
                        if(sendCustomerMail == 'Y'){
                            alert("The vendor has been successfully added in OBI and a mail has been sent to the given mail address.");
                        }else{
                            alert("The vendor has been successfully added in OBI. Email has not been sent to the vendor.");
                        }
                        if(loadAccountIndicator == 'Y'){
                            var fullName = '';
                            if(nameIndicator == 'P'){
                                fullName = firstName +" " +lastName;
                            }else{
                                fullName = businessName;
                            }
                            loadCustomerStatusScreen(guid,fullName);
                        }else{
                            $('#addNPPopup').dialog('closeOk');
                        }
                        reserFieldValues();
                        updateFirstName();
                        updateLastName();
                        updateBusinessName();

                    } else {
                      if (alreadyExists) {
                        if (confirm("This vendor already exists in mCare. Do you want go to the vendor status page before resending an email?")) {
                          setCustomer("mCarebeta.Current.CustomerGuid", guid);
                          setCustomer("mCarebeta.Current.CustomerName", firstName + " " + lastName);
                        }
                      } else {
                        $("#np_errMsg").html("There was an error adding this vendor to OBI.  Please try again later.");
                        $(".popup_error").show();
                      }
                    }
              },
              onError: function(xhr, desc, err) {
                    $("#np_errMsg").html("Ajax call failed with the following response:\nDesc: " + desc + "\nErr:" + err);
                    $(".popup_error").show();
              }
        };
        doAjaxCall(ajaxParams, null, callbackFn);
      }

    }

    var reserFieldValues = function() {
          $("#np_accountId").val("");
          $("#np_firstName").val("");
          $("#np_lastName").val("");
	  $("#np_businessName").val("");
          $("#np_emailAddress").val("");
          $("#lbl_np_generatedGuid").html("");
          $("#np_generatedGuid").val("");
	  $("#np_tokenvaliditySpan").html("");
	  $("#np_validToken").html("");

          //To reset radio buttons
          $('input:radio[name=np_nameId]:nth(0)').attr('checked',true);
          showPersonalOrBusinessDetails()

          //To reset checkboxes
          $("#np_loadAccountId").attr('checked',true);
          $("#np_emailCustomer").attr('checked',true);
          $("#np_lbl_email_content").css('display','block');

    }

        var updatefilds = function() {
              generateGuid();
              updateFirstName();
              updateLastName();
              updateBusinessName();
        }

        var updateFirstName = function() {
          $("#np_firstNameSpan").html($("#np_firstName").val());
        }
	
        var updateLastName = function() {
          $("#np_lastNameSpan").html($("#np_lastName").val());
        }

        var updateBusinessName = function() {
          $("#np_businessNameSpan").html($("#np_businessName").val());
        }
    
	var showPersonalOrBusinessDetails = function() {	
		if($('input:radio[name=np_nameId]:checked').val() == 'P'){
			$("#np_lbl_firstNameId").css('display','block');
			$("#np_lbl_lastNameId").css('display','block');
			$("#np_lbl_businessId").css('display','none');
                        $("#np_businessName").val("");
		}else{
			$("#np_lbl_firstNameId").css('display','none');
			$("#np_lbl_lastNameId").css('display','none');
			$("#np_lbl_businessId").css('display','block');
			$("#np_firstName").val("");
			$("#np_lastName").val("");
		}                
		updateFirstName();
                updateLastName();
                updateBusinessName();                
                npValidator.resetForm();
                npValidator.hideStars();   
    }
	
    var trimStr = function(aString) {
      var inputStr = "" + aString;
      return inputStr.replace(/^\s+|\s+$/g, '');
    }

	function clearSearch(){
		$('.tab-one input').val("");
		$('.tab-two input').val("");
	}
	
	function checkAndOpenGUID( guidValue ){

		var ajaxParams = {"url": "include/findCustomerData.jsp",
                            dataType: "html",
                            data: {"action": "checkGUID",
                                    "targetGuid": escape(guidValue)
                            },
                            contentType: null
        };
        var callbackFn = {
              onSuccess: function(data) {
					if($.trim(data)== "Ok"){
						setCustomer("mCarebeta.Current.CustomerGuid", guidValue);
					}else{
						alert("Guid has not been found. Please enter another one.");					
					}
              },
              onError: function(xhr, desc, err) {
                    $("#np_errMsg").html("Ajax call failed with the following response:\nDesc: " + desc + "\nErr:" + err);
                    $(".popup_error").show();
              }
        };
        doAjaxCall(ajaxParams, null, callbackFn);
		
	}
	
	function setCustomer(cookieName, value){
		 var merchantId = $("#merchantId").val();	 	
		 switch (merchantId) {
	        case "patch": 
	        case "adcom":
	        case "aolcms":
	        case "hpsound":
	        case "gamesap":
	        case "aolcmsuk":
	        case "pricelinemvp":
	        case "pricelinegthrv":	        	
	        case "aolugc":
	        case "gps_deals_ap":
	        case "patchdeals":
	        case "patchsiteap":
	        case "aolspp":
	        case "studionow":
	        case "studionowuk":
	          setCookie(cookieName, value,900,null,true,'CustomerStatus.jsp'); break;
	        default: 
	          setCookie(cookieName, value,900,null,true,'ARCustomerStatus.jsp');
    	}
	}
	function setCustomerBill(cookieName, value, billId){
		setCookie(cookieName, value, 900, null, true, 'BillingHistoryDetail.jsp?billId='+billId)     
	}
	function paymentCriteria(value){
	  if (value != "CHECKING" ) {
		  $("#routing_label").hide();
	  } else {
		  $("#routing_label").show();
	  }
  }
	
  var tabId = "tab-basic";
	var oTable;
		
	$(document).ready(function() {
		//menu 
		$("#nav>li>a").siblings().prev().addClass("haschild");
		$("#nav ul li a").wrap("<div></div>");
		$("#nav li:nth-child(2)").addClass("active");
		
		$("#search-results").css({"display":"none"});
		$("div.table").addClass("none");
		$("#load").hide();
		//Initialize the tabs.	
		$("#searchTabs").tabs();

		// Now make the search tabs visible 
		document.getElementById("searchTabsWrapper").style.visibility = "visible";
		
		$('#searchTabs').bind('tabsselect', function(event, ui) {
			tabId = ui.panel.id; 
			if(tabId == "tab-guid") {
				$("#load").show();
				$("#search").hide();
			} else{
				$("#search").show();
				$("#load").hide();
			}
		});
			
		// Initialzie the tables...
    oTable = $('#search-results table').dataTable( {
    		"aoColumns": [
			{ "sWidth": "11%" },
			{ "sWidth": "12%" },
			{ "sWidth": "13%" },
			{ "sWidth": "18%" },
			{ "sWidth": "7%" },
			{ "sWidth": "8%" },
			{ "sWidth": "10%" },							
			{ "sWidth": "21%" }],
			"bPaginate": true,
			"sPaginationType": "full_numbers",
			"bLengthChange": true,
		//	"bFilter": false,
			"bSort": true,
			"bInfo": false,
			"sDom": '<"top"lf>rt<"bottom"p>',
			"iDisplayLength": 10, 
			"bProcessing": true,
			"bRetrieve": true,
			"bDestroy": true,
			"aLengthMenu": [[10, 20, -1] , ["10/page", "20/page", "All" ]],
			"bAutoWidth": false,
			
			"oLanguage": {
                            "sSearch": "Search within results:"
                        },
                        
            "fnServerData" : function ( url, data, callback) {
				$.ajax( {
					"url": url,
					"type": "POST",
					"data": data,
					"success": callback,
					"dataType": "json",
					"cache": false,
					"error": function (xhr, error, thrown) {
						if ( error == "parsererror" ) {
								alert( "Server Error: Server couldn't process your request at this time. Please try it later." );
						}
					}
				} );
			},
			            
			"fnDrawCallback": function ( oSettings ) { 
				if(isLoadComplete){
					var tmData = new Array();
					for(var dIndex =0; dIndex < oSettings.aoData.length; dIndex++){
						tmData[dIndex] = oSettings.aoData[dIndex]._aData;
					}
					$("#dataTableCache").val(JSON.stringify(tmData));
				}
				if (oSettings.fnRecordsTotal()<20){
					$("#bottomMessage").css({"display":"none"});
					/*if (oSettings.fnRecordsTotal()<=10) {
						$("div.dataTables_paginate").hide();
						$("div.dataTables_length").hide();
					} else {
						$("div.dataTables_paginate").show();
					}*/
				}
				else{
					$("#bottomMessage").css({"display":"block"});
					/*$("div.dataTables_paginate").show();
					$("div.dataTables_length").show();*/
				}

                                if (this.parent().find("div.paging_full_numbers").find('span:not([class])').text() == '' ){
                                         this.parent().find("div.paging_full_numbers").find('span:not([class])').html("<span class=\"paginate_active\">1</span>");
                                }
			}	 
		});
		isLoadComplete = true;
		// check cache
		var cacheValue = $("#dataTableCache").val();
		if(cacheValue != undefined && $.trim(cacheValue).length > 0){
			 var dataValue = JSON.parse(cacheValue);
			 oTable.fnAddData(dataValue);
			 $("div.table").removeClass("none");
         	 $("#search-results").css({"display":"block"});
		}
		
		$("#search").bind('click', function() {
			var merchantId = getInputFieldValue("#merchantId");
			var targetGuid = getInputFieldValue("#guid");
			var firstName = getInputFieldValue("#firstname");
			var lastName = getInputFieldValue("#lastname");
			var email = getInputFieldValue("#email");
			
			prePostDataProcessing("USED_TAB", tabId );
			// this field uses for setup what options user use for search 
			var tabOptions = "";
			
			if (tabId == "tab-basic"){
                              var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                              if (email != "" && !filter.test(email) ) {
                                    alert('Please provide a valid email address');
                                    $("#email").focus();
                                    return false;
                              }
                          
                              tabOptions = buildOption(tabOptions, "GUID", targetGuid);
                              tabOptions = buildOption(tabOptions, "First Name", firstName);
                              tabOptions = buildOption(tabOptions, "Last Name", lastName);
                              tabOptions = buildOption(tabOptions, "Email", email);


                              if (!isNotEmpty(targetGuid) && !isNotEmpty(firstName ) && !isNotEmpty(lastName) && !isNotEmpty(email)){
                            	  alert('All fields can not be blank.');
                                  return false;
                              }
		               	                           
                              switch (merchantId) {
                                    case "patch":
                                    case "hpsound":
                                    case "gamesap":
                                    case "aolcms":
                                          if( isNotEmpty(targetGuid)|| isNotEmpty(firstName ) || isNotEmpty(lastName) || isNotEmpty(email) ){
                                          	var tmData = {
                                            	"url" : "include/findCustomerData.jsp",
                                            	"data" : {
                                            			"targetGuid" : escape(targetGuid),
                                            			"firstName" : escape(firstName),
                                            			"lastName" : escape(lastName),
                                            			"email" : escape(email)
                                            		}
                                            };
                                          	 
                                             oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?targetGuid="+escape(targetGuid) + "&firstName=" +escape(firstName) + "&lastName="+escape(lastName)+"&email="+escape(email));
                                             $("div.table").removeClass("none");
                                             $("#search-results").css({"display":"block"});
                                          }
                                          break;
                                    default:
                                          if( isNotEmpty(targetGuid)|| isNotEmpty(firstName ) || isNotEmpty(lastName) || isNotEmpty(email) ){
                                             var tmData = {
                                            	"url" : "include/findCustomerData.jsp",
                                            	"data" : {
                                            			"targetGuid" : escape(targetGuid),
                                            			"firstName" : escape(firstName),
                                            			"lastName" : escape(lastName),
                                            			"email" : escape(email)
                                            		}
                                            };
                                             oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?targetGuid="+escape(targetGuid) + "&firstName=" +escape(firstName) + "&lastName="+escape(lastName)+"&email="+escape(email));
                                             $("div.table").removeClass("none");
                                             $("#search-results").css({"display":"block"});
                                          }
                                          break;
                              }
			} else if (tabId == "tab-business") {
                              var businessname =  getInputFieldValue("#businessname");
                              tabOptions = buildOption(tabOptions, "Business Name", businessname);
                              if( businessname != ""){
                               				var tmData = {
                                            	"url" : "include/findCustomerData.jsp",
                                            	"data" : {
                                            			"firstName" : ".",
                                            			"lastName" : escape(businessname)
                                            		}
                                            };
                                    oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?firstName=.&lastName="+escape(businessname));
                                    $("div.table").removeClass("none");
                                    $("#search-results").css({"display":"block"});
                              }
			} else if (tabId == "tab-mon") {
                               var mon = getInputFieldValue("#mon");
                               
                               tabOptions = buildOption(tabOptions, "MON", mon);
                               if( mon != ""){
                               		 var tmData = {
                                            	"url" : "include/findCustomerData.jsp",
                                            	"data" : {
                                            			"mon" : escape(mon)
                                            		}
                                    };
                                    oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?mon="+escape(mon));
                                    $("div.table").removeClass("none");
                                    $("#search-results").css({"display":"block"});
                               }else {
                            	   alert('MON can not be blank.');
                            	   $("#mon").focus();
                               }
			} else if (tabId == "tab-orderid") {
                              var orderid = getInputFieldValue("#orderid");



                              
                              tabOptions = buildOption(tabOptions, "Order Id", orderid);
                              if( orderid != ""){
                              		 var tmData = {
                                            	"url" : "include/findCustomerData.jsp",
                                            	"data" : {
                                            			"orderId" : escape(orderid)	
                                            		}
                                     };
                                            
                                    oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?orderId="+escape(orderid));
                                    $("div.table").removeClass("none");
                                    $("#search-results").css({"display":"block"});
                              }else {
                           	   	alert('Order Id can not be blank.');
                           	 	$("#orderid").focus();
                              }
			}else if (tabId == "tab-ssn") {
                              var ssn =  getInputFieldValue("#ssn");
                              tabOptions = buildOption(tabOptions, "SSN", ssn);
                              if(ssn!="") {
                              	 var tmData = {
                                            	"url" : "include/findCustomerData.jsp",
                                            	"data" : {
                                            			"ssn" : escape(ssn)	
                                            		}
                                  };	
                                  oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?ssn="+escape(ssn));
                                  $("div.table").removeClass("none");
                                  $("#search-results").css({"display":"block"});
                              }
			} else if (tabId == "tab-payment") {
                              var paymenttype = getInputFieldValue("#paymenttype");
                              var bankroutno = getInputFieldValue("#bankroutno");
                              var accountno = getInputFieldValue("#accountno");

                              if (accountno == ''){
                            	  alert('Account Number can not be blank.');
                                  $("#accountno").focus();
                                  return false;
                              }

                              var filter = /^[a-zA-Z0-9]+$/;
                              if (!filter.test(accountno) ) {
                                    alert('Please provide a valid account number.');
                                    $("#accountno").focus();
                                    return false;
                              }
                              
			  	
                              tabOptions = buildOption(tabOptions, "Payment Type", paymenttype);
                              tabOptions = buildOption(tabOptions, "Bankrout Number", bankroutno);
                              tabOptions = buildOption(tabOptions, "Account Number", accountno);
			  	
                              if ( paymenttype == "CHECKING" ) {
                                    if( accountno != "" && bankroutno != ""){
                                         if (isNaN(accountno)){
                                                 alert("Please enter a valid account number.");
                                                 return false;
                                         }
                                         else if (isNaN(bankroutno)){
                                                 alert("Please enter a valid bank routing number.");
                                                 return false;
                                         }
                                          var tmData = {
                                            	"url" : "include/findCustomerData.jsp",
                                            	"data" : {
                                            			"paymentType" : escape(paymenttype),
                                            			"routingNumber" : escape(bankroutno),
                                            			"paymentAccountNumber" : escape(accountno)
                                            		}
                                  		 };	
                                         
                                         oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?paymentType="+escape(paymenttype) + "&routingNumber=" +escape(bankroutno) + "&paymentAccountNumber="+escape(accountno));
                                         $("div.table").removeClass("none");
                                         $("#search-results").css({"display":"block"});
                                    }
                               } else {
//                                      if( accountno != "" ){
//                                                if (isNaN(accountno)){
//                                                        alert("Please enter a valid account number.");
//                                                        return false;
//                                                }
                                            var tmData = {
                                            	"url" : "include/findCustomerData.jsp",
                                            	"data" : {
                                            			"paymentType" : escape(paymenttype),
                                            			"paymentAccountNumber" : escape(accountno)
                                            		}
                                            };
                                               
                                           oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?paymentType="+escape(paymenttype) + "&paymentAccountNumber="+escape(accountno));
                                           $("div.table").removeClass("none");
                                           $("#search-results").css({"display":"block"});
//                                       } else {
//                                            accountno=" ";
//                                            var tmData = {
//                                             	"url" : "include/findCustomerData.jsp",
//                                             	"data" : {
//                                             			"paymentType" : escape(paymenttype),
//                                             			"paymentAccountNumber" : escape(accountno)
//                                             		}
//                                   		    };	
//                                            oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?paymentType="+escape(paymenttype) + "&paymentAccountNumber="+escape(accountno));
//                                            $("div.table").removeClass("none");
//                                            $("#search-results").css({"display":"block"});
//                                       }
                               }
                        } else if (tabId == "tab-sapid") {
	    		    var sapid = getInputFieldValue("#sapid");
                            tabOptions = buildOption(tabOptions, "SAP Id", sapid);
		            if( sapid != ""){
		            	var tmData = {
                         	"url" : "include/findCustomerData.jsp",
                         	"data" : {
                         			"sapAcct" : escape(sapid)
                         		}
               		    };	
                                  		    
			            oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?sapAcct="+escape(sapid));
			            $("div.table").removeClass("none");
			            $("#search-results").css({"display":"block"});
                            }
		      	}else if (tabId == "tab-paymentid") {

                    var paymentId =  $.trim(getInputFieldValue("#paymentId"));
                    if (paymentId == ''){
                  	  alert('Payment Id can not be blank.');
                        $("#paymentId").focus();
                        return false;
                    }else if(paymentId.length <= 5 || paymentId.length >= 15 || !isWholeNumber(paymentId)){
                        alert('Payment Id should be a whole number with 6 to 14 digits');
                        return;
                    }

             		 var tmData = {
                         	"url" : "include/findCustomerData.jsp",
                         	"data" : {
                         			"paymentId" : escape(paymentId)	
                         		}
                  };

                  oTable.fnReloadAjax(tmData);//"include/findCustomerData.jsp?orderId="+escape(orderid));
                  $("div.table").removeClass("none");
                  $("#search-results").css({"display":"block"});                    
				}
                        prePostDataProcessing("USED_TAB_["+tabId+"]", tabOptions);
		});

		$("#load").bind('click', function() {
			var guid = $.trim($("#guid1").val());
			 prePostDataProcessing("USED_TAB_["+tabId+"]", "GUID");
	         if(guid != "" && guid != "*"){
				checkAndOpenGUID(guid);
	         }else{
		        if (guid == ""){
		        	alert("GUID can not be blank.");
			    }else {
		        	alert("Please enter a valid GUID");
			    }
	         }
         });
		
		//$("#search").trigger("click");
		$("#paymenttype").trigger("onchange");

		$("#content").keyup(function(event){
                        if(event.keyCode == 13 || event.which == 13){
                            if($("#load").css("display")=="none")
                                  $("#search").trigger("click");
                            else
                                  $("#load").trigger("click");
                            return false; // need this for safari, otherwise wouldn't work
                        }  
		});

    $("#addNPPopup").dialog({
      bgiframe: true,
      autoOpen: false,
      width: 405,
      modal: falseForSafari()
    });
	
	
    npValidator = createValidatorWrapper($("#np_form"), {
      rules: {		
			np_accountId:{required: true, maxlength: 29},
			np_firstName:{required: true, nonnum: true, maxlength: 48},
			np_lastName:{required: true, nonnum: true, maxlength: 48},
			np_businessName:{required: true, alphaNumericWithSpaces: true, maxlength: 48},
			np_emailAddress:{required: true, email: true, maxlength: 60},
			np_generatedGuid:{required: true},
			np_email:{required: true, email: true, maxlength: 60, emailExists: function() {
				return $("#np_toAddress").text();
			  } }			
		},
      errorLabelContainer:"#np_warningdiv label.warning",
      onkeyup: false
    });

    npValidator.settings.messages.np_accountId = {
      required: "The SFDC Account ID field is required",
      maxlength: "Please enter no more than {0} <br> characters for the SFDC Account ID."
    }
    npValidator.settings.messages.np_firstName = {
      required: "The Contact First Name field is required",
      nonnum:   "The Contact First Name field shouldn't be a numeric.",
      maxlength: "Please enter no more than {0} <br> characters for the Contact First Name."
    }
    npValidator.settings.messages.np_lastName = {
      required: "The Contact Last Name field is required",
      nonnum:   "The Contact Last Name field shouldn't be a numeric.",
      maxlength: "Please enter no more than {0} <br> characters for the Contact Last Name."
    }
	npValidator.settings.messages.np_businessName = {
      required: "The Business Name field is required",
      alphaNumericWithSpaces:   "The Business Name field should contain numbers and alphabetic characters.",
      maxlength: "Please enter no more than {0} <br> characters for the Business Name."
    }
    npValidator.settings.messages.np_emailAddress = {
      required: "The Contact Email Address field is required",
      email:   "Please enter a valid Contact Email Address.",
      maxlength: "Please enter no more than {0} <br> characters for the Contact Email Address."
    }
    npValidator.settings.messages.np_generatedGuid = {
      required: "The Generated GUID field is required"
    }

	npValidator.settings.messages.np_email = {
			required: "The To Address field is required",
			email: "Please enter a valid email address",
			emailExists: "This email address already exists",
			maxlength: "Please enter no more than {0} <br> characters"
	}    

    $("#np_accountId").keypress(function(e) {
      return skipCharacters(e, fieldCharactersMap.textField);
    });

    $("#np_firstName").keypress(function(e) {
      return skipCharacters(e, fieldCharactersMap.textField);
    });

    $("#np_lastName").keypress(function(e) {
      return skipCharacters(e, fieldCharactersMap.textField);
    });
	
	$("#np_businessName").keypress(function(e) {
      return skipCharacters(e, fieldCharactersMap.textField);
    });
	
	$("#np_emailAddress").keypress(function(e) {
      return skipCharacters(e, fieldCharactersMap.emailField);
    });
	
	$("#addNP").unbind("click").bind("click", function(e){
                reserFieldValues();
                updateFirstName();
                updateLastName();
                updateBusinessName();
		$("#ap_errMsg").html("");
		$(".popup_error").hide();

                npValidator.resetForm();
                npValidator.hideStars();
                showPersonalOrBusinessDetails();

                $('#addNPPopup').dialog('open');
        });
	
	$("#np_save").unbind("click").bind("click",function(e){
          crateAccount();
          //$('#addNPPopup').dialog('closeCancel');
        });

    $("#np_cancel").unbind("click").bind("click",function(e){
      $('#addNPPopup').dialog('closeCancel');
    });

    $("#np_emailCustomer").click(function() {
        if ($(this).is(":checked")){
        	$("#np_lbl_email_content").css('display','block');            
        }else{
        	$("#np_lbl_email_content").css('display','none');
    }});

    $("#addEmailAddress").css("display", "none");
    $("#toAddress").css("display", "none");
    
    
	$("#addAddress").click(function() {
		$("#np_email").val("");
		$("#addAddress").css("display", "none");
		$("#addEmailAddress").css("display", "block");
	});

	$("#np_addEmailAddressCancel").click(function() {
		resetAddAddressState();
	});

	$("#np_addEmailAddress").unbind("click").bind("click", function(e) {
		var fieldId = "np_email";
		npValidator.validateField(fieldId);
		if (npValidator.hasFieldErrors(fieldId)) return false;
		if($.trim($("#np_toAddress").text()) != "") {
			setToAddress($("#np_toAddress").text() + "|" + $("#np_email").val(), undefined);
		} else {
			setToAddress($("#np_email").val(), undefined);
		}
		resetAddAddressState();
		$("#toAddress").css("display", "block");
	});

	var resetAddAddressState = function() {
		$("#np_email").val("");
		$("#addEmailAddress").css("display", "none");
		$("#addAddress").css("display", "block");
		npValidator.resetForm();
		npValidator.hideStars();
	}
	
	});
	
	
	function loadCustomerStatusScreen(guid,fullName) {
		setCustomer("mCarebeta.Current.CustomerGuid", guid);
		setCustomer("mCarebeta.Current.CustomerName", fullName);
	}

    var setToAddress = function(value, excludeEmail) {
        $("#np_toAddress").html("");
        $("#np_toAddress_deleter").html("");        
        var toAddressList = value.split('|');
        for (var n = 0; n < toAddressList.length; n++) {
          var address = toAddressList[n];
          if (address == excludeEmail) {
              if (toAddressList.length == 1){
                  $("#toAddress").css("display", "none");
              }
              continue;
          }
          var toAddressHtml = $("#np_toAddress").html();
          $("#np_toAddress").html(toAddressHtml + (toAddressHtml == '' ? "" : "|<br>") + address);
          if ((excludeEmail == undefined && toAddressList.length >= 1) ||
              (excludeEmail != undefined && toAddressList.length > 1)) {
            var toAddressDeleterHtml = $("#np_toAddress_deleter").html();
            $("#np_toAddress_deleter").html(toAddressDeleterHtml + (toAddressDeleterHtml == '' ? "" : "<br>") + "<a href=\"#\" onclick=\"deleteAddress('" + address + "');\"><img src=\"https://s.aolcdn.com/os/OBI/mCare/css/images/delete-email.png\" class=\"remove-email-address\"></a>");
          }
        }
    }

    var deleteAddress = function(emailAddress) {
        setToAddress($("#np_toAddress").text(), emailAddress);
    }   
	
	</script>
</head>
<body>
<!--[if lte IE 6]>
        <div id="ie6">
<![endif]-->
<input type="hidden" id="dataTableCache">
<div id="wrapper">
	<div id="header">
		<jsp:include page="include/Header.jsp" />
		<!-- SUB MENU -->
		<ul class="breadcrumb">
			<li><a href="Home.jsp" id="findCust_lbl_mCare">mCare</a></li>
			<li><a href="#" id="findCust_custTypeDet">${messageBean.customerType} Details</a></li>
            <li id="findCust_custType">Choose ${messageBean.customerType}</li>
		</ul> <!-- CRUMB -->
	<div id="findCust_div1"></div> <!-- HEADER ENDS HERE -->
	<div class="clear" id="findCust_div2"></div>
	
	<!-- CONTENT STARTS HERE -->
	<div id="content">
	<div>
       <h2 id="findCust_lbl_findCust" style="margin: 0px; padding-bottom: 15px; padding-top: 15px;">Find ${messageBean.customerType}</h2>
	</div>
    <c:if test="${messageBean.hasRightsToAddProvider}">
      <div style="padding-left: 208px; top: -35px; position: relative; width: 200px;"><a href="javascript:void(0);"><img id="addNP" src="https://s.aolcdn.com/os/OBI/mCare/css/images/add-provider.png" alt="Add Provider"/></a></div>
    </c:if>
		<input type='hidden' id='merchantId' name='merchantId' value='${messageBean.merchantId}'>
		<form class="field-one">
    		<div id="searchTabsWrapper" class="navigation" style="visibility:hidden">
	      		<div id="searchTabs"  style="width: 1000px">
			        <ul>
			          <li><a href="#tab-basic" id="findCust_lbl_BasSearch">Basic Search </a></li>
			          <c:if test="${messageBean.businessSearchVisible}">
			          	<li><a href="#tab-business" id="findCust_lbl_BusinessSearch">Business Search </a></li>
			          </c:if>
					  <c:if test="${messageBean.monSearchVisible}">
						 <li><a href="#tab-mon" id="findCust_lbl_MonSearch">MON Search </a></li>
					  </c:if>
					  <c:if test="${messageBean.orderIdSearchVisible}">
						<li><a href="#tab-orderid" id="findCust_lbl_OidSearch">Order Id Search </a></li>
					  </c:if>
					  <c:if test="${messageBean.paymentMethodSearchVisible}">
						<li><a href="#tab-payment" id="findCust_lbl_PayMethSearch">Payment Method Search 
						</a></li>
					  </c:if>
				     <c:if test="${messageBean.sapIdSearchVisible}">
						<li><a href="#tab-sapid" id="findCust_lbl_sapsearch">SAP Id Search </a></li>
					  </c:if>
					  <c:if test="${messageBean.ssnSearchVisible}">
						 <li><a href="#tab-ssn" id="findCust_lbl_ssnSearch">SSN/EID Search </a></li>
					  </c:if>
			          <li><a href="#tab-guid" id="findCust_lbl_guidSearch">Go to GUID</a></li>
			          <c:if test="${messageBean.paymentIdSearchVisible}">			          
			          	<li><a href="#tab-paymentid" id="findCust_lbl_paymentidsearch">Payment Id Search</a></li>
			          </c:if>

			        </ul>
		        	<div id="tab-basic">
		          		<div class="box1" style="overflow:auto; overflow-x:hidden; overflow-y:hidden;">
							<div class="tab-one">			
	   	              			<ul>
	   	              				<fieldset>	   	              				
									<li>
										<label>GUID
											<img src="https://s.aolcdn.com/os/OBI/mCare/css/images/help.png" onmouseover="tooltip.show('Customer&rsquo;s General User ID. <br/> If you don&rsquo;t know the Customers GUID, you can enter a partial GUID using wild card characters such as &ldquo;*&rdquo;.');" onmouseout="tooltip.hide();" />
										</label>
											<input type="text" name="guid" id="guid" maxlength="40" onKeyPress="return avoidInterestChar(this, event)"/>
									</li>
									<li><label id="findCust_lbl_fname">First Name</label> <input type="text" name="firstname" id="firstname"  maxlength="30" onKeyPress="return avoidInterestChar(this, event)"/></li>
									<li><label id="findCust_lbl_email">Email</label> <input type="text" name="email" id="email" maxlength="255" onKeyPress="return avoidInterestChar(this, event)"/></li>
									<li><label id="findCust_lbl_lname">Last Name</label> <input type="text" name="lastname" id="lastname"  maxlength="30" onKeyPress="return avoidInterestChar(this, event)"/></li>
									</fieldset>
								</ul>
					    	</div>
		          		</div>
		        	</div>
		        	<c:if test="${messageBean.businessSearchVisible}">
	        			<div id="tab-business"> 
		          			<div class="box1">
	            				<div class="tab-one">
				                	<ul>
				                    	<li class="size2 moveleft"><label id="findCust_lbl_business">Business Name <img src="https://s.aolcdn.com/os/OBI/mCare/css/images/question-mark.png" alt="?" onmouseover="tooltip.show('Search by Business Name.');" onmouseout="tooltip.hide();" /></label><input type="text" id="businessname" name="businessname" maxlength="30" onKeyPress="return avoidInterestChar(this, event)"/></li>				                    
				                	</ul>
					    	</div>
		          		</div>
		        	</div>
		        	</c:if>
		        	<c:if test="${messageBean.monSearchVisible}">
			        	<div id="tab-mon">
		            		<div class="box1">
		            			<div class="tab-one">
		    						<ul>
		                				<li class="size1"><label id="findCust_lbl_mon">MON <img src="https://s.aolcdn.com/os/OBI/mCare/css/images/question-mark.png" alt="?" onmouseover="tooltip.show('MON is the Merchant Order Number.  An identifying number AOL and Paymentech use.  Paymentech is our payment processor.');" onmouseout="tooltip.hide();" /></label><input type="text" id="mon" name="mon" maxlength="30" onKeyPress="return avoidInterestChar(this, event)"/></li>
		              				</ul>
						    	</div>
			          		</div>
			        	</div>
			        </c:if>
			        <c:if test="${messageBean.orderIdSearchVisible}">
			        	<div id="tab-orderid">
			          		<div class="box1">
		            			<div class="tab-one">
		              				<ul>
		                				<li class="moveleft"><label id="findCust_lbl_orderid">Order Id <img src="https://s.aolcdn.com/os/OBI/mCare/css/images/question-mark.png" alt="?" onmouseover="tooltip.show('Order ID of any past transaction.');" onmouseout="tooltip.hide();" /></label><input type="text" id="orderid" name="orderid" maxlength="55" onKeyPress="return avoidInterestChar(this, event)"/></li>
		           		    	</ul>
						    	</div>
			          		</div>
			        	</div>				
			        </c:if>
			        <c:if test="${messageBean.paymentMethodSearchVisible}">
			        	<div id="tab-payment">
			          		<div class="box1">
		            			<div class="tab-one">
		              				<ul>
		              					<fieldset>
										<li class="size2 moveleft">
											<label>Payment Type
												<img src="https://s.aolcdn.com/os/OBI/mCare/css/images/question-mark.png" onmouseover="tooltip.show('Credit Card or Checking Account');" onmouseout="tooltip.hide();" />
											</label>
												 <select id="paymenttype" name="paymenttype" onchange="javascript:paymentCriteria(this.options[this.selectedIndex].value)">
                                <option  value="AMERICAN_EXPRESS" id="findCust_lbl_amerExpr">American Express</option>
                                <option  value="CARTE_BLANCHE" id="findCust_lbl_carBla">Carte Blanche</option>
                                <option  value="DINERSCARD" id="findCust_lbl_dinClu">Diner's Club</option>
                                <option  value="DISCOVER" id="findCust_lbl_disc">Discover</option>
                                <option  value="MASTER_CARD" id="findCust_lbl_mastCrd">MasterCard</option>
                                <option  value="MASTER_CARD_DEBIT" id="findCust_lbl_mastCrdDeb">MasterCard Debit</option>
                                <option  value="VISA" id="findCust_lbl_visa">Visa</option>
                                <option  value="VISA_DEBIT" id="findCust_lbl_vidaDeb">Visa Debit</option>
                                <option  value="CHECKING" id="findCust_lbl_che">Checking</option>
                                <option  value="INVOICE" id="findCust_lbl_che">Invoice</option>
						  						</select>
										</li>
										<li class="blank"> <label id="bankroutingno-label">  </label> &nbsp; </li>
			      						<li class="size3 moveleft" id="routing_label"> <label>Bank Routing Number</label><input type="text" id="bankroutno" name="bankroutno" maxlength="30"/></li>
			      						<li class="size2" id="accountno-label"> <label>Account Number <img src="https://s.aolcdn.com/os/OBI/mCare/css/images/question-mark.png" alt="?" onmouseover="tooltip.show('You must enter the entire account number, including leading zeros.');" onmouseout="tooltip.hide();" /></label><input type="text" id="accountno" name="accountno" maxlength="60"/></li>
			      						</fieldset>	
									</ul>
						    	</div>
			          		</div>
			        	</div>		
						</c:if>
						<c:if test="${messageBean.sapIdSearchVisible}">
			        	<div id="tab-sapid">
			          		<div class="box1">
		            			<div class="tab-one">
		              				<ul>
		                				<li class="moveleft"><label id="findCust_lbl_sapid">SAP Id <img src="https://s.aolcdn.com/os/OBI/mCare/css/images/question-mark.png" alt="?" onmouseover="tooltip.show('SAP ID of any past transaction.');" onmouseout="tooltip.hide();" /></label><input type="text" id="sapid" name="sap" maxlength="30" onKeyPress="return avoidInterestChar(this, event)"/></li>
		           		    	</ul>
						    	</div>
			          		</div>
			        	</div>
			        	</c:if>
			        	<c:if test="${messageBean.ssnSearchVisible}">
			        	<div id="tab-ssn">
		            		<div class="box1">
		            			<div class="tab-one">
		    						<ul>
		                				<li class="size2 moveleft"><label id="findCust_lbl_ssn">SSN&nbsp;/&nbsp;EID&nbsp;<img src="https://s.aolcdn.com/os/OBI/mCare/css/images/question-mark.png" alt="?" onmouseover="tooltip.show('Social Security Number or Employer Identification Number');" onmouseout="tooltip.hide();" /></label><input type="text" id="ssn" name="ssn" maxlength="30" onKeyPress="return avoidInterestChar(this, event)"/></li>
		              				</ul>
						    	</div>
			          		</div>
			        	</div>				
			        </c:if>        	
						<div id="tab-guid">
			          		<div class="box1">
		            			<div class="tab-one">
					                <ul>
					                    <li class="moveleft"><label id="findCust_lbl_guid">GUID <img src="https://s.aolcdn.com/os/OBI/mCare/css/images/question-mark.png" alt="?" onmouseover="GUIDTooltipShow();" onmouseout="tooltip.hide();" /></label><input type="text" id="guid1" name="sap" maxlength="40" onKeyPress="return avoidInterestChar(this, event)"/></li>
					                </ul>
						    	</div>
			          		</div>
			        	</div>			        	
			        	<c:if test="${messageBean.paymentIdSearchVisible}">			        	
			        	<div id="tab-paymentid">
		            		<div class="box1">
		            			<div class="tab-one">
		    						<ul>
		                				<li class="size2 moveleft"><label id="findCust_lbl_paymentid">Payment Id <img src="https://s.aolcdn.com/os/OBI/mCare/css/images/question-mark.png" alt="?" onmouseover="paymentIdTooltipShow();" onmouseout="tooltip.hide();" /></label><input type="text" id="paymentId" name="paymentId" maxlength="14" onKeyPress="return avoidInterestChar(this, event)"/></li>
		              				</ul>
						    	</div>
			          		</div>
			        	</div>			        	
			        	</c:if>
			        	
				</div>	<!-- SEARCH TABS END HERE -->	
				
			</div><!-- NAVIGATION ENDS HERE -->
			<div class="box2" style="width: 1000px">				
			  	<div class="tab-one">
				  	<ul>
					  	<li>  &nbsp; &nbsp;  &nbsp;
    						<a href="#"><img src="https://s.aolcdn.com/os/OBI/mCare/css/images/search.png" id="search" alt="Search" class="search" /></a>
							<a href="#"><img src="https://s.aolcdn.com/os/OBI/mCare/css/images/load.png" id="load" class="search" alt="Load" /></a>
     						<a href="javascript:clearSearch()"><img id="findCust_reset" src="https://s.aolcdn.com/os/OBI/mCare/css/images/reset.png" alt="Search" class="search"/></a>
			  			</li>
				  	</ul>
			  	</div>				
		  	</div>
		</form>		
		
		<div id="search-results" class="search-results-container">
			<div class="table">
				<table class="tab-two">
					<thead>
				        <tr>
				          <th id="findCust_lbl_hGuid">GUID</th>
				          <th id="findCust_lbl_hFname">First Name</th>
				          <th id="findCust_lbl_hLname">Last Name</th>
				          <th id="findCust_lbl_hStrAddr">Street Address</th>
				          <th id="findCust_lbl_hCity">City</th>
				          <th id="findCust_lbl_hState">State</th>
				          <th id="findCust_lbl_hPostCd">Postal Code</th>
				          <th id="findCust_lbl_hEmailAddr">Email Address</th>
				        </tr>
				    </thead>
				</table> <!-- TAB TWO ENDS HERE -->
			</div> <!-- TABLE ENDS HERE -->			
			<div id="bottomMessage">
				<p class="searchMessage directions" id="findCust_lbl_note">* More than 20 providers met your search criteria, if you do not see your provider, please refine your search.&nbsp;&nbsp;</p>
			</div><!-- end div#bottomMessage -->
		</div>  <!-- SEARCH-RESULTS ENDS HERE -->
	</div> <!-- CONTENT ENDS HERE -->
  <jsp:include page="include/AddProviderMethod.jsp" />
	<jsp:include page="include/SendFeedback.jsp" />
	<jsp:include page="include/footer.jsp" />
</body>
</html>
