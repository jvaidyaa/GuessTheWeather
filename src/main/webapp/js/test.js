var getInputFieldValue = function(selector){
	var value = $(selector).val();
	if(value != null){
		value = $.trim(value);
	}
	return value;
};

function save () {
	var dop = getInputFieldValue("#dop");
	var zip = getInputFieldValue("#zip");
	var email = getInputFieldValue("#email");
	var prediction = getInputFieldValue("#prediction");
	
                         var tmData = {
                         "url" : "SavePredicion.jsp",
                         "data" : {
                                    			"dop" : escape(dop),
                                    			"zip" : escape(zip),
                                    			"email" : escape(email),
                                    			"prediction" : escape(prediction)
                                  }
                           };
                         
                          performAction = true;
                         
                         
                         if (performAction) {
                             // send the request
                             var ajaxParams = {"url": "include/SavePredicion.jsp",
                                                 data: rmData,
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
                             doAjaxCall(ajaxParams, null, callbackFn);
                         }
           
}