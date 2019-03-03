<!DOCTYPE html>
<html>

<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
    <title>首页</title> 
  	<#import "/common/import.ftl" as import>
  	<#import "/common/orderstatistics.ftl" as statistics>
  	<#import "/common/goodsstatistics.ftl" as goods>
  	
	<@import.mianImportCss/>   
	<@import.minImportScript/> 
   <script src="${WEB_PATH}/resources/js/plugins/echarts/echarts.min.js"></script>
   <script src="${WEB_PATH}/resources/js/plugins/jquery-ybp/raphael-2.1.4.min.js"></script>
   <script src="${WEB_PATH}/resources/js/plugins/jquery-ybp/justgage.js"></script>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
           <@import.capitalAccountInfo/>
           <@import.robordercount/>
            <!-- 订单统计-->
	        <@statistics.order/> 
	        <!-- 订单统计 -->
	        <@goods.goodscount/> 
	        
        </div>
    </div>
</div>
</body>

</html>