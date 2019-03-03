
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
				    		<p>路线 ${USER.company.companyType.name}：<span class="label label-primary">${robOrderRecord.goodsBasic.deliveryAreaName}</span>&nbsp;------->&nbsp;<span class="label label-danger">${robOrderRecord.goodsBasic.deliveryAreaName}</span></p>
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
					 <hr style=" height:2px;border:none;border-top:2px dotted #185598;" />
					 <div class="row">
						<div class="col-sm-4">
				    		<p>收货人姓名：${robOrderRecord.goodsBasic.consigneeName}</p>
				    		<p>收货区域名称：${robOrderRecord.goodsBasic.consigneeAreaName}</p>
				    	</div>
				    	<div class="col-sm-4">
				    		<p>收货人联系电话：${robOrderRecord.goodsBasic.consigneeMobile}</p>
				    		<p>收货详细地址：${robOrderRecord.goodsBasic.consigneeAddress}</p>
				    		<p></p>
				    	</div>
				    	<div class="col-sm-4">
				    		<p>收货人email：<#if null == goodsBasic.consigneeEmail><font color="red">无</font><#else>${robOrderRecord.goodsBasic.consigneeEmail}</#if></p>
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
            			     <#if USER.company.companyType.name != "个人司机">
	            			 	 <div class="btn-group" id="roborderinfo_grid_toolbar" role="group">
	                                    <button type="button" class="btn btn-outline btn-default " data-toggle="modal" id="oborderinfo-split-merge" >
	                                        <i class="iconfont" style="color:#00bb9c;font-size:16px;font-style:normal;" >&#xe61a;</i>&nbsp;&nbsp;拆分/合并
					                    </button>
	                             </div>
                             </#if>
	                         <table id="roborderinfo_grid" >
	                            <thead>
	                                <tr>
	                                	<th data-field="state" data-radio="true" data-formatter="stateFormatter"></th>
							            <th data-field="goodsTypeId" data-formatter="formatGoodsType" >货物类型</th> 
							            <th data-field="goodsName" data-formatter="formatGoodsName"> 货物名称</th> 
							            <th data-field="weight" data-formatter="formatGoodsWeight"> 重量/吨</th> 
							            <th data-field="length" data-formatter="formatGoodsLength">长度/米</th> 
							            <th data-field="height" data-formatter="formatGoodsHeight">高度/米</th> 
							            <th data-field="diameter" data-formatter="formatGoodsDiameter">直径/cm</th>
								        <th data-field="wingWidth" data-formatter="formatGoodsWingWidth">翼宽</th>
								        <th data-field="weight"><span style="color: red;">*&nbsp;&nbsp;</span>抢单重量</th>
								        <th data-field="actualWeight"  data-formatter="formatActualWeight"><span style="color: red;">*&nbsp;&nbsp;</span>抢单实际重量</th>
								        <#if USER.company.companyType.name != "个人司机">
								        <th data-field="isSpilt"  data-formatter="formatIsSpilt"><span style="color: red;">*&nbsp;&nbsp;</span>是否拆分</th>
								        <th data-field="stockId" data-editable="true" data-type = "select" data-save = "client" data-validate = "required" data-validatemethod = "validateSumBondMoney" data-source="${WEB_PATH}/global/data/getCompanyTrucks.do"><span style="color: red;">*&nbsp;&nbsp;</span>装货车辆</th>
								        <#else>
								        <th data-field="stockId"  data-visible="false" >id</th>
								        <th data-field="stockName" >装货车辆</th>
								        </#if>
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
					    	<div class="col-sm-3">
					    		<p>抢单单价(元)：<font color="#1ab394">¥&nbsp;${robOrderRecord.unitPrice}（元）</font></p>
					    		<input id="id" name="id" type="hidden" value="${robOrderRecord.id}">
		                        <input id="version" name="version" type="hidden" value="${robOrderRecord.version}">
					    	</div>
					    	<div class="col-sm-3">
					    		<p>抢单总重量(吨)：<font color="#1ab394">${robOrderRecord.weight}（吨）</font></p>
					    	</div>
					    	<div class="col-sm-3">
					    		<p>抢单总价(元)：<font color="#1ab394">¥&nbsp;${robOrderRecord.totalPrice}（元）</font></p>
					    	</div>
					    	<div class="col-sm-3">
					    		<p>备注：<#if null == robOrderRecord.remark><font color="red">无</font><#else><font color="#1ab394">${robOrderRecord.remark}</font></#if></p>
					    	</div>
						</div>
						<div class="row" id = "bond_money_div">
					    	<div class="col-sm-12">
					    		<p><label><span style="color: red;">需缴纳保证金：</span></label><font color="red" id="bond_money"></font></p>
					    	</div>
					    	<div class="col-sm-3">
					    		<div class="form-group">
	                                <label><span style="color: red;">*&nbsp;&nbsp;支付密码：</span></label>
	                                <input type="password" name="payPassword" id="payPassword" class="form-control"   maxlength="6" placeholder="请输入6位数的支付密码"/>
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
					                 				<span class="label label-primary">确认装货</span>
					                 			<#elseif vo.afterStatus == 2>
					                 				<span class="label label-primary">处理中</span>
					                 			 <#elseif vo.afterStatus == 3>
					                 				<span class="label label-warning">退回</span>
					                 			 <#elseif vo.afterStatus == 4>
					                 				<span class="label label-success">成功</span>
					                 			 <#elseif vo.afterStatus == 5>
					                 				<span class="label label-danger">作废</span>
					                 			 <#elseif vo.afterStatus == 6>
					                 				<span class="label label-danger">撤回</span>
					                 			 <#elseif vo.afterStatus == 6>
					                 				<span class="label label-danger">结束</span>
					                 			<#elseif vo.afterStatus == 7>
					                 				<span class="label label-danger">销单</span>
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
    <div id="split-merge-div"></div>
    
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
	<script src="${WEB_PATH}/resources/js/map.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=1AC8RPrl58FNLoYjoEhOiwv8SsgToWRm"></script>
	
	<script >
		var goodsTypes_id = "";
		var validatCount = 0;
		var grid_seccess = 0;
		var robOrderRecordInfo = new Map();
		var bond_money = ${parameterManage.value};
		
    	$(function(){
    		<#if USER.company.companyType.name != "个人司机">
    		$("#bond_money_div").hide();
    		</#if>
    		$(window).resize(function () {
		        $('#goodsDetail_grid').bootstrapTable('resetView');
			});  
		    
			var $roborder_from =  $('#roborder_from').validate({
				  rules: {
				     payPassword: {
				        required: true
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
	            <#if USER.company.companyType.name != "个人司机">
	            toolbar: "#roborderinfo_grid_toolbar",
	            </#if>
	            iconSize: "outline",
	            minimumCountColumns: 1, 
	            onLoadSuccess:goodsDetailLoadSuccess,
	            clickToSelect: true,
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
	        $("#oborderinfo-split-merge").click(function(){
	        	var selectRow = $("#roborderinfo_grid").bootstrapTable('getSelections');
	        	var info = selectRow[0]; 
		        if(1 != selectRow.length){
				    layer.msg('<font color="red">温馨提示：请选择一条货物信息，进行拆分/合并！</font>', {icon: 5,time: 3000}); 
				    return ;
		       	}
		       	$("#split-merge-div").load("${WEB_PATH }/order/roborderinfo/robsplitmerge.do?id="+info.id);
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
		        if(robOrderInfo.stockId == "" || null == robOrderInfo.stockId){
		        	layer.msg('<font color="red">货物详细：第'+(i+1)+'行,货物类型：['+robOrderInfo.goodsDetail.goodsType.name+'],装货车辆必须选择！</font>', {icon: 5,time: 3000});
		        	return false;
		        }
		    }
		    return true;
		}
		
		function getRobOrderRecordInfo(){
			var robOrderRecordInfos = new Array();
			var robOrderInfoData = $("#roborderinfo_grid").bootstrapTable('getData');
			for(var i = 0; i < robOrderInfoData.length; i++) {
				 var robOrderInfo = robOrderInfoData[i];
		         robOrderRecordInfos.push({id:robOrderInfo.id,actualWeight:robOrderInfo.actualWeight,stockId:robOrderInfo.stockId});
		    }
		   	return robOrderRecordInfos;
		}
		 
		
		function confirmRobOrderInfo(status){
			 var payPassword = $("#payPassword").val();
			 var roborder = $('#roborder_from').serializeJson();
			 var robOrderRecordInfos = JSON.stringify(getRobOrderRecordInfo());
			 roborder.robOrderRecordInfos = robOrderRecordInfos;
			 roborder.status = status;
			 roborder.payPassword = payPassword;
			 var robOrderRecordInfoFn = robOrderRecordInfo.keys;
			 roborder.turckIds = robOrderRecordInfoFn.toString();
			 $.yilinAjax({
		   	  	type:'POST',
		   	  	async:false,
		   	  	loadmsg:'订单确认中......',
		   	  	url:'${WEB_PATH }/order/roborder/confirmRobOrderRecord.do',
		   	  	data:roborder,
		   	  	btn:null,
        		errorcallback:null,
        		successcallback:success
		   	 });
		}
		
		function confirmRobOrder(){
			 if(grid_seccess == 0){
			 	layer.msg('<font color="red">货物详情还未加载完毕，请稍后再试！</font>', {icon: 5,time: 2000});
				return false;
			 }
			 if(validatRobOrderInfo()){
			 	 confirmRobOrderInfo("end");
			 }
		}
		function success(result){  
			if(result.success == true){
		   		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		   		parent.openorderdetail('${robOrderRecord.id}');
				parent.layer.close(index); 
		    	
			}else{
				$('#saveBut').attr("disabled",false); 
			 	$('#subBut').attr("disabled",false);       
		    	layer.alert('<font color="red">'+result.msg+'</font>', {
				  skin: 'layui-layer-molv' ,
				  closeBtn: 0,
				  icon: 5
				});
			}
		}
		 
		function formatGoodsType(index, row, element){
			if(row.split){
				return row.goodsDetail.goodsType.name; 
			}else{
				if(!row.parent){
					return '<i class="iconfont" style="font-size:16px;font-style:normal;padding-left: 30px;" >&#xe620;</i>'+row.goodsDetail.goodsType.name; 
				}else{
					return row.goodsDetail.goodsType.name; 
				}
			}
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
		function formatActualWeight(value){
    		 return '<font color="red">'+value+'</font>';
		}
		
		function validateSumBondMoney(row,value){
			 if(robOrderRecordInfo.get(value) == undefined){
			 	$("#bond_money_div").show();
    		 	robOrderRecordInfo.put(value,row.id);
    		 }else if(value != row.stockId){
    		 	robOrderRecordInfo.remove(row.stockId);
    		 }
    		 $("#bond_money").html("¥&nbsp;"+(bond_money*robOrderRecordInfo.size())+"（元）");
		}
		function stateFormatter(value, row, index) {
	        if (!row.parent) {
	            return {
	                disabled: true
	            };
	        }
	        return value;
	    }
	    
	    function formatIsSpilt(value, row, index) {
	        var value = '';
	        if (row.split) {
	            value = '<span class="label label-danger">已拆分</span>';
	        }else{
	        	value = '<span class="label">未拆分</span>';
	        }
	        return value;
	    }
	    
	    
		function goodsDetailLoadSuccess(){
			grid_seccess++;
			<#if USER.company.companyType.name == "个人司机">
			$("#bond_money").html("¥&nbsp;"+(bond_money*1)+"（元）");
			</#if>
		}
    </script>
</body>

</html>