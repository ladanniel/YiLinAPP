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
    <style>
    	.demo:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "条件查询：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.demo {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		}
    	.goods:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "货物详细信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.goods {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    margin-top: 10px;
		}
    
    	.consignor:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "联系人详细";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		
		.orderinfo:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "订单详细";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		
	  .truckinfo:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "司机详细";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.consignor {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    margin-top: 10px;
		    padding-bottom: 10px;
		}
    </style>
</head>

<body style="padding: 10px;">

<div style="" class="consignor orderinfo ui-sortable" > 
	 <div class="row">
    	<div class="col-sm-3">
    		<p>订单号：${robOrderRecord.robOrderNo}</p>
    		<p>发货商户：${company.name}</p>
    	
    	</div>
    	<div class="col-sm-6">
    		<p>路线(出发地/目的地)：<span class="label label-primary">${robOrderRecord.goodsBasic.deliveryAreaName}</span>&nbsp;&nbsp;->&nbsp;&nbsp;<span class="label label-danger">${robOrderRecord.goodsBasic.consigneeAreaName}</span></p>
    		<p>接单商户 ：${robOrderRecord.companyName}</p>
    	</div>
    	<div class="col-sm-3">
    		<p>货物类型：<span class="label label-success">${robOrderRecord.goodsBasic.goodsType.name}</span></p>
    	</div>
	</div>
	<hr style=" height:0.1px;border:none;border-top:0.1px dotted #185598;" />
	<div class="row">
		<div class="col-sm-4">
    		<p>原单价：¥&nbsp;${robOrderRecord.goodsBasic.unitPrice}</p>
    		<p>抢单单价：<font class="text-danger">¥&nbsp;${robOrderRecord.unitPrice}</font></p>
    	</div>
    	<div class="col-sm-4">
    		<p>原重量/吨：¥&nbsp;${robOrderRecord.goodsBasic.totalWeight}</p>
    		<p>抢单重量/吨：<font class="text-danger">¥&nbsp;${robOrderRecord.weight}</font></p>
    		<p></p>
    	</div>
    	<div class="col-sm-4">
    		<p>原总价（总重量*单价）：¥&nbsp;${robOrderRecord.goodsBasic.totalPrice}</p>
    		<p>抢单总价（总重量*单价）：<font class="text-danger">¥&nbsp;${robOrderRecord.totalPrice}</font></p>
    	</div>
	</div>
</div>

<div style="" class="consignor  ui-sortable"> 
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
</div>

<div style="" class="consignor truckinfo ui-sortable"> 
	<div class="row" style="margin-top: 13px">
    <div class="col-sm-12">
			<div class="fixed-table-container" style="padding-bottom: 0px;">
			  <div class="fixed-table-header" style="display: none;">
			    <table></table>
			  </div>
			  <div class="fixed-table-body">
			    <div class="fixed-table-loading" style="top: 41px; display: none;">
			      <div class="sk-spinner sk-spinner-wave" style="padding-top:1%;">
			        <div class="sk-rect1"></div>
			        <div class="sk-rect2"></div>
			        <div class="sk-rect3"></div>
			        <div class="sk-rect4"></div>
			        <div class="sk-rect5"></div>
			      </div>
			    </div>
			    <table class="table table-hover">
			      <thead>
			        <tr>
			          <th class="detail" rowspan="1">
			            <div class="fht-cell"></div>
			          </th>
			          <th style="" data-field="turckUserName" tabindex="0">
			            <div class="th-inner ">司机</div>
			            <div class="fht-cell"></div>
			          </th>
			          <th style="" data-field="phone" tabindex="0">
			            <div class="th-inner ">司机号码</div>
			            <div class="fht-cell"></div>
			          </th>
			          <th style="" data-field="trackNo" tabindex="0">
			            <div class="th-inner ">车牌号</div>
			            <div class="fht-cell"></div>
			          </th>
			          <th style="" data-field="cardType" tabindex="0">
			            <div class="th-inner ">车辆类型</div>
			            <div class="fht-cell"></div>
			          </th>
			          <th style="" data-field="unitPrice" tabindex="0">
			            <div class="th-inner ">单价</div>
			            <div class="fht-cell"></div>
			          </th>
			          <th style="" data-field="totalWeight" tabindex="0">
			            <div class="th-inner ">总重量/吨</div>
			            <div class="fht-cell"></div>
			          </th>
			          <th style="" data-field="transportationCost" tabindex="0">
			            <div class="th-inner ">总价</div>
			            <div class="fht-cell"></div>
			          </th>
			          <th style="" data-field="status" tabindex="0">
			            <div class="th-inner ">状态</div>
			            <div class="fht-cell"></div>
			          </th>
			        </tr>
			      </thead>
			      <tbody>
			      	<#list truckList as vo>
			      		 <tr>
			      		 	 <td></td>
			      		 	  <td style="">${vo.turckUserName}</td>
					          <td style="">${vo.phone}</td>
					          <td style="">${vo.trackNo}</td>
					          <td style="">${vo.cardType}</td>
					          <td style="">
					            <font class="text-danger">¥&nbsp;${vo.unitPrice}</font></td>
					          <td style="">
					            <font class="text-danger">${vo.totalWeight} (吨)</font></td>
					          <td style="">
					            <font class="text-danger">¥&nbsp;${vo.transportationCost}</font></td>
					          <td style=""><span class="label label-default">
					          	<#switch vo.status>
								  <#case "0">
								             待装货
								     <#break>
								  <#case "1">
								             运输中
								     <#break>
								  <#case "2">
								            回执发还中
								     <#break>
								  <#case "3">
								             送达
								   <#break>
								  <#case "4">
								             送还回执中
								   <#break>
								   <#case "5">
								          订单完结
								   <#break>
								  <#default>
								     
								</#switch>
					          </span></td>
			      		 </tr>
			        
			       	<#if (vo.oderInfo?size > 0)>
				      <tr class="detail-view">
				          <td colspan="9">
				            <div class="bootstrap-table">
				              <div class="fixed-table-toolbar"></div>
				              <div class="fixed-table-container" style="padding-bottom: 0px;">
				                <div class="fixed-table-header" style="display: none;">
				                  <table></table>
				                </div>
				                <div class="fixed-table-body">
				                  <div class="fixed-table-loading" style="top: 41px; display: none;">
				                    <div class="sk-spinner sk-spinner-wave" style="padding-top:1%;">
				                      <div class="sk-rect1"></div>
				                      <div class="sk-rect2"></div>
				                      <div class="sk-rect3"></div>
				                      <div class="sk-rect4"></div>
				                      <div class="sk-rect5"></div>
				                    </div>
				                  </div>
				                  <table class="table table-hover">
				                    <thead>
				                      <tr>
				                        <th style="" data-field="goodName" tabindex="0">
				                          <div class="th-inner ">货物名称</div>
				                          <div class="fht-cell"></div>
				                        </th>
				                        <th style="" data-field="goodTypeName" tabindex="0">
				                          <div class="th-inner ">货物类型</div>
				                          <div class="fht-cell"></div>
				                        </th>
				                        <th style="" data-field="unitPrice" tabindex="0">
				                          <div class="th-inner ">单价</div>
				                          <div class="fht-cell"></div>
				                        </th>
				                        <th style="" data-field="weight" tabindex="0">
				                          <div class="th-inner ">重量</div>
				                          <div class="fht-cell"></div>
				                        </th>
				                        <th style="" data-field="totalPrice" tabindex="0">
				                          <div class="th-inner ">总价</div>
				                          <div class="fht-cell"></div>
				                        </th>
				                      </tr>
				                    </thead>
				                    <tbody>
				                     <#list vo.oderInfo as info>
					                     <tr>
					                        <td style="">${info.goodName}</td>
					                        <td style="">${info.goodTypeName}</td>
					                        <td style="">
					                          <font class="text-danger">¥&nbsp;${info.unitPrice}</font></td>
					                        <td style="">
					                          <font class="text-danger">${info.weight}&nbsp;(吨)</font></td>
					                        <td style="">
					                          <font class="text-danger">¥&nbsp;${info.totalPrice}</font></td>
					                      </tr>
				                      </#list>
				                    </tbody>
				                  </table>
				                </div> 
				              </div>
				            </div> 
				          </td>
				       	 </tr>
			         </#if>
			        </#list>
			      </tbody>
			    </table>
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
</body>

</html>