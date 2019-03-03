
<div style="" class="consignor ui-sortable"> 
	 <div class="row">
    	<div class="col-sm-4">
    		<p>发货人姓名：${robOrderRecord.goodsBasic.deliveryName}</p>
    		<p>发货区域名称：${robOrderRecord.goodsBasic.deliveryAreaName}</p>
    		
    	</div>
    	<div class="col-sm-4">
    		<p>发货人联系电话：${robOrderRecord.goodsBasic.deliveryMobile}</p>
    		<p>发货详细地址：${robOrderRecord.goodsBasic.deliveryAddress}&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="openGis(${robOrderRecord.goodsBasic.deliveryCoordinate})"><span class="form-control-static fa green fa-map-marker">查看地图</span></a></p>
    	</div>
    	<div class="col-sm-4">
    		<p>发货人email：<#if null == robOrderRecord.goodsBasic.deliveryEmail><font color="red">无</font><#else>${robOrderRecord.goodsBasic.deliveryEmail}</#if></p>
    	</div>
	</div>
	<#if robOrderRecord.status == "success">
	<div class="row">
		<div class="col-sm-4">
    		<p>收货人姓名：${robOrderRecord.goodsBasic.consigneeName}</p>
    		<p>收货区域名称：${robOrderRecord.goodsBasic.consigneeAreaName}</p>
    	</div>
    	<div class="col-sm-4">
    		<p>收货人联系电话：${robOrderRecord.goodsBasic.consigneeMobile}</p>
    		<p>收货详细地址：${robOrderRecord.goodsBasic.consigneeAddress} &nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="openGis(${robOrderRecord.goodsBasic.consigneeCoordinate})"><span class="form-control-static fa green fa-map-marker">查看地图</span></a></p>
    		<p></p>
    	</div>
    	<div class="col-sm-4">
    		<p>收货人email：<#if null == robOrderRecord.goodsBasic.consigneeEmail><font color="red">无</font><#else>${robOrderRecord.goodsBasic.consigneeEmail}</#if></p>
    	</div>
	</div>
	</#if>
</div>
<div class="row" style="margin-top: 13px">
    <div class="col-sm-12">
    	<table class="table table-bordered">
        	<thead>
            	<tr>
                     <th data-toggle="true">货物名称</th>
                     <th>货物类型</th>
                     <th>原重量/吨</th> 
                     <th>抢单重量/吨</th>               
                     <th>长度/米</th>
                     <th>高度/米</th>
                     <th>直径/cm</th>
                     <th>翼宽</th> 
               	</tr>
           </thead>
           <tbody>
               <#list robOrderRecordInfo as vo>
                   <tr>
	                    <td><#if null == vo.goodsDetail.goodsName><font color="red">无</font><#else>${vo.goodsDetail.goodsName}</#if></td>
	                    <td><#if null == vo.goodsDetail.goodsType.name><font color="red">无</font><#else>${vo.goodsDetail.goodsType.name}</#if></td>
	                    <td><#if null == vo.goodsDetail.weight><font color="red">无</font><#else>${vo.goodsDetail.weight}</#if></td>
	                    <td><#if null == vo.weight><font color="red">无</font><#else><font color="red">${vo.weight}</font></#if></td>
	                    <td><#if null == vo.goodsDetail.length || vo.goodsDetail.length == 0><font color="red">无</font><#else>${vo.goodsDetail.length}</#if></td>
	                    <td><#if null == vo.goodsDetail.heigh || vo.goodsDetail.heigh == 0><font color="red">无</font><#else>${vo.goodsDetail.height}</#if></td>
	                    <td><#if null == vo.goodsDetail.diameter || vo.goodsDetail.diameter == 0><font color="red">无</font><#else>${vo.goodsDetail.diameter}</#if></td>
	                    <td><#if null == vo.goodsDetail.wingWidth || vo.goodsDetail.wingWidth == 0><font color="red">无</font><#else>${vo.goodsDetail.wingWidth}</#if></td>
	                    
	              </tr>
               </#list>                    
           </tbody>                     	
        </table>
   	</div>
</div>
<script >
	function openGis(xpoint,ypoint){
		$("#win_add").load("${WEB_PATH }/goods/contacts/view/gis.do?point=" + xpoint + "," + ypoint);
	}
</script>
