function doAjaxCall(params, callbackData, callbackFn, isNoShowAjaxIndicator) {

   if(typeof isNoShowAjaxIndicator == "undefined" || !isNoShowAjaxIndicator){
      showAjaxIndicator();
   }
   var ajaxDefObj =  {
      type : "POST",
      dataType : "json",
      async : false,
      contentType : "application/json; charset=utf-8",
      success : function(data) {
         if (data == null) {
            alert("Server Error ");
         } else if (data.errorCode && (data.errorCode != "0")) {
            alert("Server Error: " + data.errorMsg);
         } else {
            if (null != callbackFn.onSuccess) {
               if (null != callbackData) {
                  callbackFn.onSuccess(data, callbackData);
               } else {
                  callbackFn.onSuccess(data);
               }
            }
         }
      },
      error : function(xhr, desc, err) {
         if (null != callbackFn.onError) {
            callbackFn.onError(xhr, desc, err);
         } else {
            alert("Ajax call failed with the following response:\nDesc: "
               + desc + "\nErr:" + err);
         }
      },
      complete : function(jqXHR, textStatus) {
         if(typeof isNoShowAjaxIndicator == "undefined" || !isNoShowAjaxIndicator) hideAjaxIndicator();
         if (null != callbackFn.onComplete) {
            callbackFn.onComplete();
         }
      }
   };
   ajaxDefObj = initAjaxParam(ajaxDefObj, params);
   try{
      $.ajax(ajaxDefObj);
   }catch (e) {
      if(typeof isNoShowAjaxIndicator == "undefined" || !isNoShowAjaxIndicator) hideAjaxIndicator();
      alert (e);      
   }finally {
      //if(!isNoShowAjaxIndicator) hideAjaxIndicator();
   }
   return false;
}


function initAjaxParam(baseAjaxParam, customAjaxParam) {
   for(var paramName in customAjaxParam) {
      var valueByParamName = customAjaxParam[paramName];
      if(valueByParamName != undefined) {
         baseAjaxParam[paramName] = valueByParamName;
      } else {
         delete baseAjaxParam[paramName];
      }
   }
   return baseAjaxParam;
}