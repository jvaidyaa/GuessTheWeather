var getInputFieldValue = function(selector){
	var value = $(selector).val();
	if(value != null){
		value = $.trim(value);
	}
	return value;
};

function save () {
	var dop = document.getElementById("dop");
	if (dop != null){
		dop = dop.value;
	}
	
	var zip = document.getElementById("zip");
	if (zip != null){
		zip = zip.value;
	}
	
	var email = document.getElementById("email");
	if (email != null){
		email = email.value;
	}
	var prediction = document.getElementById("prediction");
	if (prediction != null){
		prediction = prediction.value;
	}
	
                         var tmData = {
                         "url" : "include/SavePrediction.jsp",
                         "data" : {
                                    			"dop" : escape(dop),
                                    			"zip" : escape(zip),
                                    			"email" : escape(email),
                                    			"prediction" : escape(prediction)
                                  }
                           };
                         alert (" dop " + dop);
                         alert (" zip " + zip);
                         
                         alert (" email " + email);
                         
                         alert (" prediction " + prediction);
                         
                          performAction = true;
                         
                         
                         if (performAction) {
                             // send the request
                        	 alert(" just before call to SavePrediction ")
                             var ajaxParams = {"url": "SavePrediction.jsp",
                                                 data: tmData,
                                                 dataType: "html",
                                                 contentType: null
                                              };
                             var callbackFn = {
                                   onSuccess: function(data) {
                                        
                                         var err;
                                         var status;
                                         try {
                                             var pos = data.indexOf("{");
                                             var len = data.length;
                                             var fixedData = data.substring(pos, len);
                                             var canStatus = eval('(' + fixedData + ')');
                                             status = canStatus.status;
                                             err =  canStatus.errorCode +":" + canStatus.errorMsg;
                                         } catch (e) {}
                                         if (status == "true") {
                                             alert("success");
                                             
                                         } else {
                                             $("#ab_errMsg").html("There was an error \nPlease try again later.");
                                             
                                         }
                                   },
                                   onError: function(xhr, ajaxOptions, err) {
                                          $("#ab_errMsg").html("There was an error trying to issue charge back.\n" +err+"\nPlease try again later.");
                                 	     $(".popup_error").show();
                                   }
                             };
                             doAjaxCall(ajaxParams, null, callbackFn,true);
                         }
           
}