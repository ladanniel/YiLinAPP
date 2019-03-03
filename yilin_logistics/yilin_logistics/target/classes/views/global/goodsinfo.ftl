
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
    		<p>发货人email：<#if null == goods.deliveryEmail><font color="red">无</font><#else>${goods.deliveryEmail}</#if></p>
    	</div>
	</div>
</div>
<div class="row" style="margin-top: 13px">
    <div class="col-sm-12">
    	<table class="table table-bordered">
        	<thead>
            	<tr>
                     <th data-toggle="true">货物名称</th>
                     <th>货物类型</th>
                     <th>重量/吨</th>
                     <th>剩余重量/吨</th>               
                     <th>长度/米</th>
                     <th>高度/米</th>
                     <th>直径/cm</th>
                     <th>翼宽</th>               
               	</tr>
           </thead>
           <tbody>
               <#list list as vo>
                   <tr>
	                    <td><#if null == vo.goodsName><font color="red">无</font><#else>${vo.goodsName}</#if></td>
	                    <td><#if null == vo.goodsType.name><font color="red">无</font><#else>${vo.goodsType.name}</#if></td>
	                    <td><#if null == vo.weight><font color="red">无</font><#else>${vo.weight}</#if></td>
	                    <td><font color="red"><#if vo.surplusWeight == 0>${vo.surplusWeight}（已抢完）<#else>${vo.surplusWeight}</#if></font></td>
	                    <td><#if null == vo.length || vo.length==0>无<#else><font color="red">${vo.length}</font></#if></td>
	                    <td><#if null == vo.heigh || vo.heigh==0>无<#else><font color="red">${vo.height}</font></#if></td>
	                    <td><#if null == vo.diameter || vo.diameter==0>无</font><#else><font color="red">${vo.diameter}</font></#if></td>
	                    <td><#if null == vo.wingWidth  || vo.wingWidth==0>无<#else><font color="red">${vo.wingWidth}</font></#if></td>
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
