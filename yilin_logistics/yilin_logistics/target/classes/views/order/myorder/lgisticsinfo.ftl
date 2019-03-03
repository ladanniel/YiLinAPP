<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>货物信息</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/js/plugins/ystep/css/op_express.css" rel="stylesheet">
</head>

<body >
 <div class="row">
 <#if (listInfo.status) !=0>
  <div class="col-sm-3">
   </div>
   <div class="col-sm-6" style="margin-top: 100px;">
 	&nbsp;&nbsp;&nbsp;：( 该单号暂无物流进展，请稍后再试，或检查公司和单号是否有误。
   </div>
 <#else>

 <div class="op_express_delivery_main" style="display: block;"> 
   <div class="op_express_delivery_main_container"> 
    <div class="op_express_delivery_main_content opui-scroll-ctrl-content opui-scroll-onwheel"> 
     <div>
      <ul class="op_express_delivery_timeline_box">
      
      
      <#list listInfo.data.info.context as info>
      
      	<#if info_index == 0>
      	  <li class="op_express_delivery_timeline_new c-clearfix">
	        <div class="op_express_delivery_timeline_title">
	         <div class="op_express_delivery_timeline_circle op_express_delivery_timeline_circle">
	          <i class="c-icon op_express_delivery_timeline_circle_red"></i>
	          <i class="c-text c-text-danger c-text-mult c-gap-left-small">最新</i>
	         </div>
	         <div class="op_express_delivery_timeline_info">
	           ${info.timeStr}
	          <br />${info.desc}
	         </div>
	        </div>
	      </li>
        </#if>
      	<li class="op_express_delivery_timeline_label c-clearfix">
        <div class="op_express_delivery_timeline_title">
         <div class="op_express_delivery_timeline_circle">
          <i class="c-icon op_express_delivery_timeline_circle_blue"></i>
         </div>
         <div class="op_express_delivery_timeline_info">
          ${info.timeStr}
          <br />${info.desc}
         </div>
        </div>
        </li>
      </#list>
      
      </ul>
     </div> 
    </div>
   </div> 
  </div>
 	
 </#if>
  </div>
  </body>

</html>