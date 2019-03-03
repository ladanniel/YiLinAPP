
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>货物信息增加</title>
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
    	.deliverGoods:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "发货人信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.deliverGoods {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.consignee:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "收货人信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.consignee {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.goodsbasic:after {
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
		.goodsbasic {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.goodsdetail:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "货物详细信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.goodsdetail {
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
		    content: "审核信息录入：";
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
		.company:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "发货商户信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.company {
		    margin-left: 0px;
		    padding:40px 15px 10px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.goodsdetaillog:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "货物操作日志：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.goodsdetaillog {
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
			<div style="" class="company ui-sortable"> 
				<div class="row">
					<div class="col-sm-4">
			    		<p>商户名称：${goods.account.company.name}</p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>商户区域：${goods.account.company.areaFullName}</p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>商户地址：${goods.account.company.address}</p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>操作人：${goods.account.name}</p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>操作人电话：${goods.account.phone}</p>
			    	</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div style="" class="goodsbasic ui-sortable"> 
			    <div class="row">
			    	<div class="col-sm-4">
			    		<p>发布商户：${goods.companyName}</p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>发布用户：${goods.account.name}</p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>货物单价：<span class="label label-warning"><font id="price">${goods.unitPrice}</font>元</span><button type="button" style="margin-left: 20px;" class="btn btn-outline btn-default" id="unitPrice" ><i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改单价</button></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>货物总价：<span class="label label-warning"><font id="total">${goods.totalPrice}</font>元</span></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>货物总重：<span class="label label-warning">${goods.totalWeight}吨</span></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>信息状态：<#if goods.status == "lock"><span class="label label-primary">锁定</span><#elseif goods.status == "apply"><span class="label label-default">申请发货</span></#if></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>发布时间：${goods.releaseTime?string("yyyy-MM-dd HH:mm:ss")}</p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>有效日期：<#if null == goods.finiteTime><span class="label label-primary">永久</span><#else>${goods.finiteTime?string("yyyy-MM-dd HH:mm:ss")}</#if></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>货物类型：<span class="label label-success">${goods.goodsType.name}</span></p>
			    	</div>
			    	<div class="col-sm-4">
			    		<p>运货路线：<span class="label label-primary">${goods.deliveryAreaName}</span><i class="fa fa-arrow-right"></i><span class="label label-danger">${goods.consigneeAreaName}</span></p>
			    	</div>
			    	<div class="col-sm-8">
			    		<p>备注信息：<#if null == goods.remark><font color="red">无</font><#else>${goods.remark}</#if></p>
			    	</div>
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div style="" class="deliverGoods ui-sortable"> 
			    <div class="row">
			    	<div class="col-sm-6">
			    		<p>发货人姓名：${goods.deliveryName}</p>
			    		<p>发货人联系电话：${goods.deliveryMobile}</p>
			    		<p>发货人email：<#if null == goods.deliveryEmail><font color="red">无</font><#else>${goods.deliveryEmail}</#if></p>
			    	</div>
			    	<div class="col-sm-6">
			    		<p>发货区域名称：${goods.deliveryAreaName}</p>
			    		<p>发货详细地址：${goods.deliveryAddress}</p>
			    		<p><a href="javascript:void(0)" onclick="openGis(${goods.deliveryCoordinate})"><span class="form-control-static fa green fa-map-marker">查看地图</span></a></p>
			    	</div>
			    </div>
			</div>
		</div>
		<div class="col-sm-6">
			<div style="" class="consignee ui-sortable"> 
			    <div class="row">
			    	<div class="col-sm-6">
			    		<p>收货人姓名：${goods.consigneeName}</p>
			    		<p>收货人联系电话：${goods.consigneeMobile}</p>
			    		<p>收货人email：<#if null == goods.consigneeEmail><font color="red">无</font><#else>${goods.consigneeEmail}</#if></p>
			    	</div>
			    	<div class="col-sm-6">
			    		<p>收货区域名称：${goods.consigneeAreaName}</p>
			    		<p>收货详细地址：${goods.consigneeAddress}</p>
			    		<p><a href="javascript:void(0)" onclick="openGis(${goods.consigneeCoordinate})"><span class="form-control-static fa green fa-map-marker">查看地图</span></a></p>
			    	</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div style="" class="goodsdetail ui-sortable"> 
			    <div class="row">
			    	<div class="col-sm-12">
				    	<table id="goodsDetail_grid" >
		                            <thead>
		                                <tr>
								            <th data-field="goodsType.name">货物类型</th> 
								            <th data-field="goodsName" >货物名称</th> 
								            <th data-field="weight">重量/吨</th> 
								            <th data-field="length"  data-editable="true" data-type = "text" data-save = "client" data-savemethod = "saveLog" data-validate = "number">长度/米</th> 
								            <th data-field="height" data-editable="true" data-type = "text" data-save = "client" data-savemethod = "saveLog" data-validate = "number">高度/米</th> 
								            <th data-field="diameter" data-editable="true" data-type = "text" data-save = "client" data-savemethod = "saveLog" data-validate = "number">直径/cm</th>
									        <th data-field="wingWidth" data-editable="true" data-type = "text" data-save = "client" data-savemethod = "saveLog" data-validate = "number">翼宽</th>
		                                </tr>
		                            </thead>
		                        </table>
				   	</div>
			    </div>
			</div>
		</div>
		<div class="col-sm-12" style="margin-top:10px;">
			<div style="" class="info ui-sortable"> 
			    <div class="row">
			    	<form class="form-horizontal m-t" id="form">
                        <div class="form-group col-sm-12" style="margin-left:5px;">
                        	<div class="col-sm-12">
                        		<label class="control-label">审核结果：</label>
	                        	<div class="radio radio-info radio-inline">
		                              <input type="radio" id="inlineRadio1" value="success" name="status" checked>
		                              <label for="inlineRadio1"> 通过 </label>
		                        </div>
		                        <div class="radio radio-inline">
		                              <input type="radio" id="inlineRadio2" value="back" name="status">
		                              <label for="inlineRadio2"> 退回 </label>
		                        </div> 
                        	</div>
                        	<div class="col-sm-12"  style="margin-top: 3px;" id="truck">
                        		<label class="control-label" style="margin-bottom: 8px"><font color="red">*&nbsp;</font>选择载货车辆类型：</label>
                        		<label class="control-label" style="color:red;" id="erro-tip" hidden>载货车辆类型不能为空。</label>
                        		<select data-placeholder="选择载货车辆类型" id="stock" class="chosen-select form-control" multiple >
                        			<@truckType>
                        				<#list truckTypeViews as vo>
                        					<option value="${vo.id}" hassubinfo="true">${vo.name}</option>
                        				</#list>
                        			</@truckType>
				                 </select>
                        	</div>
                        	<div class="col-sm-12" style="margin-top: 3px;">
							     <label class="control-label">审核描述：</label>
							     <textarea class="form-control" rows="2" name="auditCause"></textarea>
	                        </div>
                        </div>
					</form>
			    </div>
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
							          <#if list?size == 0>
							          	<div class="vertical-timeline-content">
							          		<h2>无记录</h2>
							          	</div>
							          <#else>
								          	<#list list as vo>
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
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=1AC8RPrl58FNLoYjoEhOiwv8SsgToWRm"></script>
	<script >
		var arrayLog = new Array()
		function openGis(xpoint,ypoint){
			$("#win_add").load("${WEB_PATH }/goods/contacts/view/gis.do?point=" + xpoint + "," + ypoint);
		}
		
		function saveLog(field,value,row){
			if(field == "weight"){
				arrayLog.push(row.id+"-weight-"+row.weight+"-"+value+"-"+row.goodsType.name+"-"+row.goodsName);
			}else if(field == "length"){
				arrayLog.push(row.id+"-length-"+row.length+"-"+value+"-"+row.goodsType.name+"-"+row.goodsName);
			}else if(field == "height"){
				arrayLog.push(row.id+"-height-"+row.height+"-"+value+"-"+row.goodsType.name+"-"+row.goodsName);
			}else if(field == "diameter"){
				arrayLog.push(row.id+"-diameter-"+row.diameter+"-"+value+"-"+row.goodsType.name+"-"+row.goodsName);
			}else if(field == "wingWidth"){
				arrayLog.push(row.id+"-wingWidth-"+row.wingWidth+"-"+value+"-"+row.goodsType.name+"-"+row.goodsName);
			}
		}
		
		function saveInfo(){
			$('#subBut').attr("disabled",true); 
				var status = $("input[name=status]:checked").val();
				var truckIds  = $("#stock").val() != null ? $("#stock").val().join(","):$("#stock").val()
				var auditCause = $("[name=auditCause]").val();
				var truckNames = "";
				var array = $(".search-choice");
				for(i = 0;i<array.length;i++){
					if(i == array.length - 1){
						truckNames += array[i].innerText;
					}else{
						truckNames += array[i].innerText + ",";
					}
				} 
				if(status == "success"){
					if("" == truckNames){
						$("#erro-tip").show();  
						$('#subBut').attr("disabled",false); 
						return;
					}
				}
				
				var goodsDetailData = $("#goodsDetail_grid").bootstrapTable('getData');
				 var goodsDetail = JSON.stringify(goodsDetailData);
				 var price = $("#price").html();
				 var total = $("#total").html();
				 layer.msg('提交中......', {icon: 16,shade: 0.3,time:8000});
				$.ajax({
					  type: 'POST',
					  url:'${WEB_PATH }/goods/goodsmanage/verify.do',  
					  data: {"status":status,"stockTypes":truckIds,"auditCause":auditCause,"stockTypeNames":truckNames,"id":"${goods.id}","goodsDetail":goodsDetail,"unitPrice":price,"totalPrice":total,"arrayLog":arrayLog.join("#")},
					  dataType: "json",
					  success:function(result){  
							layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
							  	skin: 'layui-layer-molv',
							  	icon: 1,
							  	closeBtn: 0
							},function(){
								parent.$("#exampleTableEvents").bootstrapTable('refresh');
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
		
		$(function(){
			//加载table数据
	    	var goodsDetail_grid = $("#goodsDetail_grid").bootstrapTable({
	    		url:"${WEB_PATH }/goods/goodsdetail/getListGoodsDetail.do?goodsBasicId=${goods.id}",
	            pagination:true,
	            toolbar: "#goodsDetail_grid_Toolbar",
	            pageNumber:1,  
	            pageSize:10, 
	            uniqueId:"id",
	            iconSize: "outline",
	            minimumCountColumns: 1, 
	            clickToSelect: true
	        });
			
			$("#stock").chosen({width:'100%'});
			$("input[name=status]").click(function(){
				var status = $("input[name=status]:checked").val();
			  	if(status == "success"){
			  		$("#truck").show();
			  	}else{
			  		$("#truck").hide();
			  	}
			});
			$("#close-but-goods").click(function(){
	        	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index); 
	        });
	        $("#stock").chosen().change(function(){
			    $("#erro-tip").hide();
			});
			$("#unitPrice").click(function(){
				var weight = ${goods.totalWeight};
					layer.open({
			      type: 2,
			      title: '<span style="color: #ed5565">编辑货物单价</span>',
			      shadeClose: true,
			      shade: false,
			      maxmin: false, //开启最大化最小化按钮
			      area: ['300px', '150px'],
			      content: 'edit.do?weight='+weight
			    });
			});
		});
    </script>
</body>

</html>