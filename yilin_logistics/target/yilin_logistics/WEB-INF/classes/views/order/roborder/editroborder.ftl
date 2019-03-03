
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>抢单信息修改</title>
    <meta name="keywords" content="易林物流平台-个人信息认证">
    <meta name="description" content="易林物流平台-个人信息认证">
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/js/plugins/stickup/stickup.css" rel="stylesheet">    
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-editable.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
	<link href="${WEB_PATH}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<style>
    	.consignor:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "联系人信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
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
		    background-color: white;
		}
		.goodsbasic:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: red;
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
		    color: red;
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
    <div class="wrapper wrapper-content animated fadeInRight">
    	 <div class="row">
	        <div class="col-sm-12" style=" padding-bottom: 10px;">
	        	<div class="zero-clipboard">
            	    <div class="btn-clipboard">
                       	<button type="button" class="btn btn-danger btn-xs" id="route_btn" data-parameter = "consignee"><i class="fa fa-map-marker"></i>&nbsp;查看路线地图</button>
                    </div> 
				</div>
            	<div style="" class="goodsbasic ui-sortable" >
                    <div class="row">
				    	<div class="col-sm-4">
				    		<p>路线：<span class="label label-primary">${robOrderRecord.goodsBasic.deliveryAreaName}</span>&nbsp;------->&nbsp;<span class="label label-danger">${robOrderRecord.goodsBasic.deliveryAreaName}</span></p>
				    	</div>
				    	<div class="col-sm-2">
				    		<p>货物类型：&nbsp;${robOrderRecord.goodsBasic.goodsType.name}</p>
				    	</div>
				    	<div class="col-sm-2">
				    		<p>单价：<font class="text-danger">￥&nbsp;${robOrderRecord.goodsBasic.unitPrice}</font></p>
				    	</div>
				    	<div class="col-sm-2">
				    		<p>总重量：&nbsp;${robOrderRecord.goodsBasic.totalWeight}(吨)</p>
				    	</div>
				    	<div class="col-sm-2">
				    		<p>总价：<font class="text-danger">￥&nbsp;${robOrderRecord.goodsBasic.totalPrice}</font></p>
				    	</div>
				    	<div class="col-sm-8">
				    		<p>备注信息：<#if null == goods.remark><font color="red">无</font><#else>${robOrderRecord.goodsBasic.remark}</#if></p>
				    	</div>
					</div>
				</div> 
			 </div> 
		  </div>  
          <div class="row">
	        <div class="col-sm-12" style=" padding-bottom: 10px;">
            	<div style="" class="consignor ui-sortable" >
                    <div class="row">
				    	<div class="col-sm-4">
				    		<p>发货人姓名：${robOrderRecord.goodsBasic.deliveryName}</p>
				    		<p>发货区域名称：${robOrderRecord.goodsBasic.deliveryAreaName}</p>
				    		
				    	</div>
				    	<div class="col-sm-4">
				    		<p>发货人联系电话：${robOrderRecord.goodsBasic.deliveryMobile}</p>
				    		<p>发货详细地址：${robOrderRecord.goodsBasic.deliveryAddress}</p>
				    	</div>
				    	<div class="col-sm-4">
				    		<p>发货人email：<#if null == goodsBasic.deliveryEmail><font color="red">无</font><#else>${robOrderRecord.goodsBasic.deliveryEmail}</#if></p>
				    	</div>
					 </div>
				</div> 
			 </div> 
		</div> 
        <div class="row">
	        <div class="col-sm-12" style=" padding-bottom: 10px;">
            	<div style="" class="goodsdetail ui-sortable" >
            		<div class="row">
            			 <div class="col-sm-12">
	                         <table id="roborderinfo_grid" >
	                            <thead>
	                                <tr>
							            <th data-field="goodsTypeId" data-formatter="formatGoodsType" >货物类型</th> 
							            <th data-field="goodsName" data-formatter="formatGoodsName"> 货物名称</th> 
							            <th data-field="weight" data-formatter="formatGoodsWeight"> 重量/吨</th> 
							            <th data-field="surplusWeight" data-formatter="formatSurplusWeight">剩余重量/吨</th> 
							            <th data-field="length" data-formatter="formatGoodsLength">长度/米</th> 
							            <th data-field="height" data-formatter="formatGoodsHeight">高度/米</th> 
							            <th data-field="diameter" data-formatter="formatGoodsDiameter">直径/cm</th>
								        <th data-field="wingWidth" data-formatter="formatGoodsWingWidth">翼宽</th>
								        <th data-field="weight" data-editable="true" data-type = "text" data-save = "client" data-validate = "required,number,isFloatGtZero" data-validatemethod = "validateWeight"  data-formatter="formatWeight"><span style="color: red;">*&nbsp;&nbsp;</span>抢单重量</th>
	                                </tr>
	                            </thead>
	                        </table>
                        </div>
                    </div>
                </div>
            </div> 
            <div class="col-sm-12" style=" padding-bottom: 10px;" >
            	<form id="roborder_from">
	            	<div style="" class="roborder ui-sortable">
	            		 <div class="row">
	                       <div class="col-sm-2">
	                            <div class="form-group">
	                                <label><span style="color: red;">*&nbsp;&nbsp;</span>抢单单价(元)：</label>
	                                <input id="unitPrice" name="unitPrice" type="text" class="form-control" placeholder="请输入货运单价" value="${robOrderRecord.unitPrice}">
	                                <input id="goodsBasic.id" name="goodsBasic.id" type="hidden" value="${robOrderRecord.goodsBasic.id}">
	                            </div>
	                        </div> 
	                        <div class="col-sm-8">
		                          <div class="form-group">
		                            <label>备注：</label>
		                            <input id="remark" name="remark" type="text" class="form-control" placeholder="请输入备注信息"  >
		                            <input id="id" name="id" type="hidden" value="${robOrderRecord.id}">
		                            <input id="version" name="version" type="hidden" value="${robOrderRecord.version}">
		                        </div>
		                    </div>
	                    </div>
	                </div>
                </from>
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
        </div> 
    </div>
    <div id="route_map"></div>
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
	<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
	<script src="${WEB_PATH}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=1AC8RPrl58FNLoYjoEhOiwv8SsgToWRm"></script>
	
	<script >
		var goodsTypes_id = "";
		var validatCount = 0;
		var grid_seccess = 0;
    	$(function(){
    		$(window).resize(function () {
		        $('#goodsDetail_grid').bootstrapTable('resetView');
			});  
		    
			var $roborder_from =  $('#roborder_from').validate({
				  rules: {
				     unitPrice: {
				        required: true,
				        number: true
				     }
				  },
				  errorPlacement: function(error, element) {
		            	element.after(error)
		          }
			});
    		
			//加载table数据
	    	var roborderinfo_grid = $("#roborderinfo_grid").bootstrapTable({
	    		url:"${WEB_PATH }/order/roborderinfo/getListGoodsDetail.do?robOrderRecordId=${robOrderRecord.id}",
	            uniqueId:"id",
	            iconSize: "outline",
	            minimumCountColumns: 1, 
	            onLoadSuccess:goodsDetailLoadSuccess,
	            clickToSelect: true
	        });
	        $("#table_not_result").css("padding","5%");
	        $("#close-but-goods").click(function(){
	        	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index); 
	        });
	        $("#saveBut").click(function(){
	        	 if(validatGoodsBasicDetailInfo()){
	        	 	saveGoodsBasicDetail("save");
	        	 } 
	        });
	        $("#subBut").click(function(){
	        	 if(validatGoodsBasicDetailInfo()){
	        	 	saveGoodsBasicDetail("apply");
	        	 } 
	        }); 
	        $("#route_btn").click(function(){
	        	  $("#route_map").load("${WEB_PATH }/goods/goodsbasic/view/gisRoute.do?id=${robOrderRecord.goodsBasic.id}");
	        });
	        layer.tips('点击我可以查看路程公路数哦！', '#route_btn', {
			  tips: [4, '#78BA32']
			});
        }); 
        function validatRobOrderInfo(){
        	if(validatGoodsDetail()){
        		validatCount++;
        		var $valid = $("#roborder_from").valid();
        		return $valid;
        	}
		}
		function validatGoodsDetail(){
			var robOrderInfoData = $("#roborderinfo_grid").bootstrapTable('getData');
			for(var i = 0; i < robOrderInfoData.length; i++) {
		        var robOrderInfo = robOrderInfoData[i];
		        if(robOrderInfo.weight == ""){
		        	layer.msg('<font color="red">货物详细：第'+(i+1)+'行,货物类型：['+robOrderInfo.goodsDetail.goodsType.name+'],抢单重量必须填写！</font>', {icon: 5,time: 3000});
		        	return false;
		        }else{
		        	if(robOrderInfo.goodsDetail.surplusWeight != 0 && (robOrderInfo.weight > robOrderInfo.goodsDetail.surplusWeight)){
		        		var msg = robOrderInfo.goodsDetail.surplusWeight == 0 ? robOrderInfo.goodsDetail.surplusWeight+"&nbsp;(吨)&nbsp;已抢完": robOrderInfo.goodsDetail.surplusWeight;
		        		layer.msg('<font color="red">货物详细：第'+(i+1)+'行,货物类型：['+robOrderInfo.goodsDetail.goodsType.name+'],剩余重量为：'+msg+'，抢单重量不能大于剩余重量！</font>', {icon: 5,time: 3000});
		        		return false;
		        	}
		        }
		    }
		    return true;
		}
		
		function formatWeight(value, row, index){
			if(row.goodsDetail.surplusWeight == 0){
				var editable = new Object();
	            editable = {
	                "value": '<font class="text-danger">已抢完</font>',
	                "editable": "false" 
	            };
				return editable;
			}else{
				return row.weight;
			}
			
		}
		
		function getRobOrderRecordInfo(){
			var robOrderRecordInfos = new Array();
			var robOrderInfoData = $("#roborderinfo_grid").bootstrapTable('getData');
			for(var i = 0; i < robOrderInfoData.length; i++) {
				 var robOrderInfo = robOrderInfoData[i];
				 var weight = robOrderInfo.goodsDetail.surplusWeight == 0?'0':robOrderInfo.weight;
		         robOrderRecordInfos.push({id:robOrderInfo.id,goodsDetailId:robOrderInfo.goodsDetail.id,weight:weight});
		    }
		   	return robOrderRecordInfos;
		}
		
		function updateRobOrderInfo(status){
			 var roborder = $('#roborder_from').serializeJson();
			 var robOrderRecordInfos = JSON.stringify(getRobOrderRecordInfo());
			 roborder.robOrderRecordInfos = robOrderRecordInfos;
			 roborder.status = status;
			 $.yilinAjax({
		   	  	type:'POST',
		   	  	loadmsg:'提交中......',
		   	  	url:'${WEB_PATH }/order/roborder/updateRobOrderRecord.do',
		   	  	data:roborder,
		   	  	btn:null,
        		errorcallback:null,
        		successcallback:success
		   	 });
		}
		
		
		function updateRobOrder(){
			 if(grid_seccess == 0){
			 	layer.msg('<font color="red">货物详情还未加载完毕，请稍后再试！</font>', {icon: 5,time: 2000});
				return false;
			 }
			 if(validatRobOrderInfo()){
			 	 updateRobOrderInfo("apply");
			 }
		}
		function success(result){  
			if(result.success == true){
		    	layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
				  	skin: 'layui-layer-molv',
				  	icon: 1,
				  	closeBtn: 0
				},function(){
					parent.$("#exampleTableEvents").bootstrapTable('refresh');
					parent.$('#edit-but').attr("disabled",false);
				   	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); 
				});
			}else{
				$('#saveBut').attr("disabled",false); 
			 	$('#subBut').attr("disabled",false);       
		    	layer.alert('<font color="red">'+result.msg+'</font>', {
				  skin: 'layui-layer-molv' ,
				  closeBtn: 0,
				  icon: 5
				},function(){
					parent.$('#edit-but').attr("disabled",false);
				});
			}
		}
		function formatSurplusWeight(index, row, element){
		    var value = "";
		    if(row.goodsDetail.surplusWeight == 0){
		    	value = row.goodsDetail.surplusWeight+"&nbsp;(吨)&nbsp;已抢完";
		    }else{
		    	value = row.goodsDetail.surplusWeight+'&nbsp;(吨)';
		    }
    		return '<font class="text-danger">'+value+'</font>'; 
		}
		
		function formatGoodsType(index, row, element){
    		return row.goodsDetail.goodsType.name; 
		}
		function formatGoodsName(index, row, element){
    		return row.goodsDetail.goodsName; 
		}
		function formatGoodsWeight(index, row, element){
    		return row.goodsDetail.weight == 0 ? "无" : row.goodsDetail.weight; 
		}
		function formatGoodsLength(index, row, element){
    		return row.goodsDetail.length == 0 ? "无" : row.goodsDetail.length; 
		}
		function formatGoodsHeight(index, row, element){
    		return row.goodsDetail.height == 0 ? "无" : row.goodsDetail.height; 
		}
		function formatGoodsDiameter(index, row, element){
    		return row.goodsDetail.diameter == 0 ? "无" : row.goodsDetail.diameter; 
		}
		function formatGoodsWingWidth(index, row, element){
    		return row.goodsDetail.wingWidth == 0 ? "无" : row.goodsDetail.wingWidth; 
		}
		function validateWeight(row,value){
    		if(value > row.goodsDetail.surplusWeight){
    			return  "实际重量不能大于货物重量，货物类型：["+row.goodsDetail.goodsType.name+"]，剩余货物重量为:["+row.goodsDetail.surplusWeight+"]！";
    		}
		}
		 
		function goodsDetailLoadSuccess(){
			grid_seccess++;
		}
    </script>
</body>

</html>