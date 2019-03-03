
<div style="" class="consignor ui-sortable"> 
	 <div class="row">
    	<div class="col-sm-4">
    		<p>发货人姓名：${goods.deliveryName}</p>
    		<p>发货区域名称：${goods.deliveryAreaName}</p>
    		
    	</div>
    	<div class="col-sm-4">
    		<p>发货人联系电话：${goods.deliveryMobile}</p>
    		<p>发货详细地址：${goods.deliveryAddress}&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="openGis(${goods.deliveryCoordinate})"><span class="form-control-static fa green fa-map-marker">查看地图</span></a></p>
    	</div>
    	<div class="col-sm-4">
    		<p>发货人email：<#if goods.deliveryEmail??><font color="red">无</font><#else>${goods.deliveryEmail}</#if></p>
    	</div>
	</div>
	<div class="row">
		<div class="col-sm-4">
    		<p>收货人姓名：${goods.consigneeName}</p>
    		<p>收货区域名称：${goods.consigneeAreaName}</p>
    	</div>
    	<div class="col-sm-4">
    		<p>收货人联系电话：${goods.consigneeMobile}</p>
    		<p>收货详细地址：${goods.consigneeAddress} &nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="openGis(${goods.consigneeCoordinate})"><span class="form-control-static fa green fa-map-marker">查看地图</span></a></p>
    		<p></p>
    	</div>
    	<div class="col-sm-4">
    		<p>收货人email：<#if goods.consigneeEmail??><font color="red">无</font><#else>${goods.consigneeEmail}</#if></p>
    	</div>
	</div>
	<div class="row">
		<div class="col-sm-4">
    		<p><#if status == 2 >查看车辆当前位置：<a href="javascript:void(0)" onclick="openTruckLocation()"><span class="form-control-static fa green fa-map-marker">查看车辆位置</span></#if></a></p>
    	</div>
	</div>
</div>
<div class="row" style="margin-top: 13px">
	<table></table>  
</div>
<script >
	function openGis(xpoint,ypoint){
		$("#win_add").load("${WEB_PATH }/goods/contacts/view/gis.do?point=" + xpoint + "," + ypoint);
	}
	function openTruckLocation(){
		$("#win_add").load("${WEB_PATH}/goods/contacts/view/gps.do?robOrderId=${robOrderId}");
	}
</script>
