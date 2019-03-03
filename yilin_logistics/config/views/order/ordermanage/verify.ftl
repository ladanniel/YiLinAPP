
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>抢单信息审查</title>
    <meta name="keywords" content="易林物流平台-个人信息认证">
    <meta name="description" content="易林物流平台-个人信息认证">
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-editable.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
	<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
	
	<style>
		.order:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "货物基本信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.order {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.orderdetail:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "抢单详情：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.orderdetail {
		    margin-left: 0px;
		    padding:40px 15px 10px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.info:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "审查信息录入：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.info {
		    margin-left: 0px;
		    padding:40px 15px 10px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.roborder:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: red;
		    content: "抢单信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.roborder {
		    margin-left: 0px;
		    padding:40px 15px 10px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		 
    </style>
</head>

<body class="gray-bg">
	<div class="row">
		<div class="col-sm-12">
			<div style="" class="order ui-sortable"> 
			    <div class="row">
			    	<div class="col-sm-4">
			    		<p>发货订单号：${info.goodsBasic.deliveryNo}</p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>货物类型：<span class="label label-success">${info.goodsBasic.goodsType.name}</span></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>发货发布时间：${info.goodsBasic.releaseTime?string("yyyy-MM-dd HH:mm:ss")}</p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>发货单价：<span class="label label-warning">${info.goodsBasic.unitPrice}元</span></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>发货总价：<span class="label label-warning">${info.goodsBasic.totalPrice}元</span></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>发货总重：<span class="label label-warning">${info.goodsBasic.totalWeight}吨</span></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>运货路线：<span class="label label-primary">${info.goodsBasic.deliveryAreaName}</span><i class="fa fa-arrow-right"></i><span class="label label-danger">${info.goodsBasic.consigneeAreaName}</span></p>
			    	</div>
			    	<div class="col-sm-8">
			    		<p>备注信息：<#if goods.remark??><font color="red">无</font><#else>${info.goodsBasic.remark}</#if></p>
			    	</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div style="" class="orderdetail ui-sortable"> 
			    <div class="row">
			    	<div class="col-sm-4">
						<p>抢单人姓名：${info.account.name}</p>
					</div>
					<div class="col-sm-4">
						<p>抢单人电话：${info.account.phone}</p>
					</div>
					<div class="col-sm-4">
						<p>抢单时间：${info.create_time?string("yyyy-MM-dd HH:mm:ss")}</p>
					</div>
					<div class="col-sm-4">
						<p>竞价单价：<span class="label label-warning">${info.unitPrice}元</span></p>
					</div>
					<div class="col-sm-4">
						<p>竞价总价：<span class="label label-warning">${info.totalPrice}元</span></p>
					</div>
					<div class="col-sm-4">
						<p>预载物重：<span class="label label-warning">${info.weight}吨</span></p>
					</div>
					<div class="col-sm-4">
						<p>预载货物类型：${info.goodsTypes}</p>
					</div>
					<div class="col-sm-4">
						<p>抢单信息状态：<#if info.status == "dealing"><span class="label label-primary">处理中</span><#elseif info.status == "apply"><span class="label label-default">申请处理</span></#if></p>
					</div>
			    	<div class="col-sm-12">
				    	 <table id="goodsDetailGrid" >
	                            <thead>
	                                <tr>
							            <th data-field="goodsDetail.goodsType.name">货物类型</th> 
							            <th data-field="goodsDetail.goodsName"> 货物名称</th> 
							            <th data-field="goodsDetail.weight"> 总重量/吨</th>
							            <th data-field="goodsDetail.embarkWeight" data-formatter="ableWeight"> 剩余可运重量/吨</th> 
							            <th data-field="goodsDetail.length">长度/米</th> 
							            <th data-field="goodsDetail.height">高度/米</th> 
							            <th data-field="goodsDetail.diameter">直径/cm</th>
								        <th data-field="goodsDetail.wingWidth">翼宽</th>
								        <th data-field="weight" data-formatter="fomatWeight">抢运重量/吨</th>
	                                </tr>
	                            </thead>
	                        </table>
				   	</div>
			    </div>
			</div>
		</div>
		<div class="col-sm-12"  >
            	<div style="" class="roborder ui-sortable">
            		 <div class="row">
                       <div id="vertical-timeline" class="vertical-container light-timeline">
					     <div class="vertical-timeline-block">
					          <div class="vertical-timeline-icon blue-bg">
					                  <i class="glyphicon glyphicon-time"></i>
					          </div>
					          <#if list?size == 0>
					          	<div class="vertical-timeline-content">
					          		<h2>无记录</h2>
					          	</div>
					          <#else>
						          	<#list list as vo>
						           		 <div class="vertical-timeline-content">
							                 <h2>${vo.title}</h2>
							                 
							                 <p>当前状态：
					                 			 <#if vo.afterStatus == 0>
					                 				<span class="label label-default">提交申请</span>
					                 			 <#elseif vo.afterStatus == 1>
					                 				<span class="label label-primary">处理中</span>
					                 			 <#elseif vo.afterStatus == 2>
					                 				<span class="label label-warning">退回</span>
					                 			 <#elseif vo.afterStatus == 3>
					                 				<span class="label label-success">成功</span>
					                 			 <#elseif vo.afterStatus == 4>
					                 				<span class="label label-danger">作废</span>
					                 			 <#elseif vo.afterStatus == 5>
					                 				<span class="label label-danger">撤回</span>
					                 			 <#elseif vo.afterStatus == 6>
					                 				<span class="label label-danger">结束</span>
					                 			 </#if>
							                 </p>
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
		<div class="col-sm-12" style="margin-top:10px;">
			<div style="" class="info ui-sortable"> 
			    <div class="row">
			    	<form class="form-horizontal m-t" id="form">
                        <div class="form-group col-sm-12" style="margin-left:5px;">
                        	<div class="row">
                        		<div class="col-sm-12">
                        			<div class="row">
                        				<div class="col-sm-12">
                        					<label class="control-label">审核结果：</label>
				                        	<div class="radio radio-info radio-inline" id="close-success">
					                              <input type="radio" id="inlineRadio1" value="success" name="status" checked>
					                              <label for="inlineRadio1"> 通过 </label>
					                        </div>
					                        <div class="radio radio-info radio-inline"  id="close-back">
					                              <input type="radio" id="inlineRadio2" value="back" name="status">
					                              <label for="inlineRadio2"> 协商 </label>
					                        </div> 
					                        <div class="radio radio-info radio-inline">
					                              <input type="radio" id="inlineRadio3" value="scrap" name="status">
					                              <label for="inlineRadio3"> 作废 </label>
					                        </div> 
                        				</div>
                        				<div id="paysuccess">
	                        				<div class="col-sm-12" style="margin-top: 3px;">
											     <label class="control-label" style="color:red;">审查通过需要支付预付款金额为：<font id="money-tip">${money}<font>元</label>
					                        </div>
					                        <div class="col-sm-12" style="margin-top: 3px;">
											     <label class="control-label">输入支付密码：</label>
											     <label class="control-label" style="color:red;" id="erro-tip" hidden>支付密码不能为空。</label>
											     <input type="password" name="payPassword" id="payPassword" class="form-control" style="width:250px;" maxlength="6"/>
					                        </div>
					                    </div>
				                        <div class="col-sm-12" style="margin-top: 3px;">
										     <label class="control-label">审核描述：</label>
										     <textarea class="form-control" rows="2" name="auditCause" name="remark" id="remark"></textarea>
				                        </div>
                        			</div>
	                        	</div>
                        	</div>
                        </div>
					</form>
			    </div>
			</div>
		</div>
	</div>
    <div id="win_add"></div>
    <script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/ajax.extend.js"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${WEB_PATH}/resources/js/plugins/wizard/jquery.bootstrap.wizard.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/bootstrap-table.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/bootstrap-editable.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.method.js"></script>
    <script src="${WEB_PATH}/resources/js/city-picker.data.js"></script>
	<script src="${WEB_PATH}/resources/js/city-picker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
	<script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/chosen/chosen.jquery.js"></script>
	<script >
		var weightNum = 0;
		$(function(){
			$("#stock").chosen({width:'100%'});
			
			//加载table数据
	    	var goodsDetail_grid = $("#goodsDetailGrid").bootstrapTable({
	    		url:"${WEB_PATH }/order/ordermanage/getListOrderDetail.do?id=${info.id}",
	            uniqueId:"id",
	            iconSize: "outline",
	            minimumCountColumns: 1, 
	            onLoadSuccess:goodsDetailLoadSuccess,
	            clickToSelect: true
	        });
			
			$("input[name=status]").click(function(){
				var status = $("input[name=status]:checked").val();
			  	if(status == "success"){
			  		$("#paysuccess").show();
			  	}else{
			  		$("#paysuccess").hide();
			  	}
			});
			$("#close-but-goods").click(function(){
	        	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index); 
	        });
	        
		});
		
		function saveInfo(){
			$('#subBut').attr("disabled",true); 
				var status = $("input[name=status]:checked").val();
				var payPassword = $("#payPassword").val();
				var remark = $("#remark").val();
				if(status == "success"){
					if("" == payPassword){
						$("#erro-tip").show();  
						$('#subBut').attr("disabled",false); 
						return;
					}
				}
				layer.msg('提交中......', {icon: 16,shade: 0.3,time:8000});
				$.ajax({
					  type: 'POST',
					  url:'${WEB_PATH }/order/ordermanage/verify.do',  
					  data: {"status":status,"payPassword":payPassword,"remark":remark,"id":"${info.id}"},
					  dataType: "json",
					  success:function(result){  
							layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
							  	skin: 'layui-layer-molv',
							  	icon: 1,
							  	closeBtn: 0
							},function(){
								parent.$("#${info.goodsBasic.id}").bootstrapTable('refresh');
							   	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							   	parent.$('#addUser').attr("disabled",false);
								parent.layer.close(index); 
								
							});
						 },
						 error:function(data){
						 	var data = $.parseJSON(data.responseText);
					        layer.alert('<font color="red">'+ data.msg+'</font>', {
							  skin: 'layui-layer-molv' ,
							  closeBtn: 0,
							  icon: 5
							},function(index){
							   	layer.close(index);
							});
						 }
				});
		}
		
		function ableWeight(index, row, element){
			var weight = Number(row.goodsDetail.weight);
			var embarkWeight = Number(row.goodsDetail.embarkWeight);
			var data = weight - embarkWeight;
			if(data >= row.weight){
				return '<font style="color:#1ab394;">' + data +'</font>'; 
			}
	    	return '<font style="color:#f8ac59;">' + data +'</font>';
		}
		
		function fomatWeight(val){
			return '<font style="color:#1ab394;">' + val +'</font>'; 
		}
		
		function goodsDetailLoadSuccess(){
			var parameter = ${parameter};
			var data = $("#goodsDetailGrid").bootstrapTable('getData');
			var remark = "";
			var flag = true;
			$.each(data,function(n,value){
				var weight = Number(value.goodsDetail.weight);
				var embarkWeight = Number(value.goodsDetail.embarkWeight);
				var dataVal = weight - embarkWeight;
				if(dataVal < value.weight){
					flag = false;
					weightNum += dataVal;
					remark += "货物类型：" + value.goodsDetail.goodsType.name + ",货物名称：" + value.goodsDetail.goodsName + "，由于剩余重量不足，可托运该货物"+dataVal+"吨；";
				}else{
					weightNum += value.weight;
				}
			});
			if(!flag){
				if(weightNum == 0){
					$("#close-success").hide();
					$("#close-back").hide();
					$("#paysuccess").hide();
					$("#inlineRadio3").attr("checked","true");
					$("#inlineRadio1").removeAttr("checked");
					$("#remark").val("您抢运的货物也被抢完，该抢单无效。");
				}else{
					$("#remark").val(remark);
					$("#paysuccess").remove();
					$("#close-success").remove();
					$("#inlineRadio2").attr("checked","true");
				}
			}else{
				var totalMoney = weightNum * parameter;
				$("#money-tip").html(totalMoney.toFixed(2));
				$("#remark").val(remark);
			}
		}
    </script>
</body>

</html>