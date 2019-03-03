<div style="" class="goods ui-sortable"> 
    <div class="row">
    	<div class="col-sm-4">
    		<p>货物类型：<span class="label label-success">${goods.goodsType.name}</span></p>
    	</div>
    	<div class="col-sm-4">
    		<p>审核人：<#if null == goods.auditPerson><font color="red">无</font><#else>${goods.auditPerson}</#if></p>
    	</div>
    	<div class="col-sm-4">
    		<p>审核时间：<#if null == goods.auditTime><font color="red">无</font><#else>${goods.auditTime?string("yyyy-MM-dd HH:mm:ss")}</#if></p>
    	</div>
    	<div class="col-sm-4">
    		<p>装货车辆：<#if null == goods.stockTypeNames><font color="red">无</font><#else>${goods.stockTypeNames}</#if></p>
    	</div>
    	<div class="col-sm-4">
    		<p>审核描述：<#if null == goods.auditCause><font color="red">无</font><#else>${goods.auditCause}</#if></p>
    	</div>
    	<div class="col-sm-4">
    		<p>备注信息：<#if null == goods.remark><font color="red">无</font><#else>${goods.remark}</#if></p>
    	</div>
	</div>
</div>
<div style="" class="consignor ui-sortable"> 
    <div class="row">
    	<div class="col-sm-4">
    		<p>发货人姓名：${goods.deliveryName}</p>
    		<p>发货人联系电话：${goods.deliveryMobile}</p>
    	</div>
    	<div class="col-sm-4">
    		<p>发货人email：<#if null == goods.deliveryEmail><font color="red">无</font><#else>${goods.deliveryEmail}</#if></p>
    		<p><a href="javascript:void(0)" onclick="openGis(${goods.deliveryCoordinate})"><span class="form-control-static fa green fa-map-marker">查看地图</span></a></p>
    	</div>
    	<div class="col-sm-4">
    		<p>发货区域名称：${goods.deliveryAreaName}</p>
    		<p>发货详细地址：${goods.deliveryAddress}</p>
    	</div>
	</div>
	<div class="row">
		<div class="col-sm-4">
    		<p>收货人姓名：${goods.consigneeName}</p>
    		<p>收货人联系电话：${goods.consigneeMobile}</p>
    	</div>
    	<div class="col-sm-4">
    		<p>收货人email：<#if null == goods.consigneeEmail><font color="red">无</font><#else>${goods.consigneeEmail}</#if></p>
    		<p><a href="javascript:void(0)" onclick="openGis(${goods.consigneeCoordinate})"><span class="form-control-static fa green fa-map-marker">查看地图</span></a></p>
    	</div>
    	<div class="col-sm-4">
    		<p>收货区域名称：${goods.consigneeAreaName}</p>
    		<p>收货详细地址：${goods.consigneeAddress}</p>
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
	                    <td><#if null == vo.name><font color="red">无</font><#else>${vo.name}</#if></td>
	                    <td><#if null == vo.weight><font color="red">无</font><#else>${vo.weight}</#if></td>
	                    <td><#if null == vo.surplusWeight><font color="red">无</font><#else><font color="red">${vo.surplusWeight}</font></#if></td>
	                    <td><#if null == vo.length><font color="red">无</font><#else>${vo.length}</#if></td>
	                    <td><#if null == vo.heigh><font color="red">无</font><#else>${vo.height}</#if></td>
	                    <td><#if null == vo.diameter><font color="red">无</font><#else>${vo.diameter}</#if></td>
	                    <td><#if null == vo.wingWidth><font color="red">无</font><#else>${vo.wingWidth}</#if></td>
	              </tr>
               </#list>                    
           </tbody>                     	
        </table>
   	</div>
</div>
<div class="col-sm-12" style="padding-top: 10px;">
	<div style="" class="goodsdetaillog ui-sortable" >
		<div class="row">
			 <div id="vertical-timeline" class="vertical-container light-timeline">
			     <div class="vertical-timeline-block">
			          <div class="vertical-timeline-icon blue-bg">
			                  <i class="glyphicon glyphicon-time"></i>
			          </div>
			          <#if logList?size == 0>
			          	<div class="vertical-timeline-content">
			          		<h2>无记录</h2>
			          	</div>
			          <#else>
				          	<#list logList as vo>
				           		 <div class="vertical-timeline-content">
					                 <h2>${vo.title}</h2>
					                 <p>当前状态：<#if vo.beforeStatus == 1>
					                 	<span class="label label-default">申请发货</span><#elseif vo.beforeStatus == 2>
					                 	<span class="label label-primary">锁定</span><#elseif vo.beforeStatus == 3>
					                 	<span class="label label-warning">退回</span><#elseif vo.beforeStatus == 4>
					                 	<span class="label label-success">通过</span><#elseif vo.beforeStatus == 5>
					                 	<span class="label label-danger">销毁</span></#if></p>
					                 <@companyInfo accontId = vo.audit_person_id>
					                 <p>商户名：${userinfo.account.company.name}</p>
			                 		 <p>商户类型：${userinfo.account.company.companyType.name}</p>
					                 </@companyInfo>
					                 <p>操作人：${vo.audit_person}</p>
					                 <p>备注：${vo.remark}</p>
					                 <span class="vertical-date">
							               <small>${vo.create_time?string("yyyy-MM-dd HH:mm:ss")}</small>
							         </span>
					           </div>
				           </#list>
			          </#if>
			     </div>
			</div>
        </div>
    </div>
 </div>
<script >
	function openGis(xpoint,ypoint){
		$("#win_add").load("${WEB_PATH }/goods/contacts/view/gis.do?point=" + xpoint + "," + ypoint);
	}
</script>
