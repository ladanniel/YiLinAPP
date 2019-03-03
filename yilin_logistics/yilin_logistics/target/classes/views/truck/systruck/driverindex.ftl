
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
            	<div style="" class="goodsbasic ui-sortable" >
                    <div class="row">
				    	<div class="col-sm-4">
				    		<p>路线：<span class="label label-primary">${goodsBasic.deliveryAreaName}</span>&nbsp;------->&nbsp;<span class="label label-danger">${goodsBasic.deliveryAreaName}</span></p>
				    	</div>
				    	<div class="col-sm-2">
				    		<p>货物类型：&nbsp;${goodsBasic.goodsType.name}</p>
				    	</div>
				    	<div class="col-sm-2">
				    		<p>单价：<font class="text-danger">¥&nbsp;${goodsBasic.unitPrice}</font></p>
				    	</div>
				    	<div class="col-sm-2">
				    		<p>总重量：&nbsp;${goodsBasic.totalWeight}(吨)</p>
				    	</div>
				    	<div class="col-sm-2">
				    		<p>总价：<font class="text-danger">¥&nbsp;${goodsBasic.totalPrice}</font></p>
				    	</div>
				    	<div class="col-sm-8">
				    		<p>备注信息：<#if null == goods.remark><font color="red">无</font><#else>${goodsBasic.remark}</#if></p>
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
				    		<p>发货人姓名：${goodsBasic.deliveryName}</p>
				    		<p>发货区域名称：${goodsBasic.deliveryAreaName}</p>
				    		
				    	</div>
				    	<div class="col-sm-4">
				    		<p>发货人联系电话：${goodsBasic.deliveryMobile}</p>
				    		<p>发货详细地址：${goodsBasic.deliveryAddress}</p>
				    	</div>
				    	<div class="col-sm-4">
				    		<p>发货人email：<#if null == goodsBasic.deliveryEmail><font color="red">无</font><#else>${goodsBasic.deliveryEmail}</#if></p>
				    	</div>
					 </div>
					 <hr style=" height:2px;border:none;border-top:2px dotted #185598;" />
					 <div class="row">
						<div class="col-sm-4">
				    		<p>收货人姓名：${goodsBasic.consigneeName}</p>
				    		<p>收货区域名称：${goodsBasic.consigneeAreaName}</p>
				    	</div>
				    	<div class="col-sm-4">
				    		<p>收货人联系电话：${goodsBasic.consigneeMobile}</p>
				    		<p>收货详细地址：${goodsBasic.consigneeAddress}</p>
				    		<p></p>
				    	</div>
				    	<div class="col-sm-4">
				    		<p>收货人email：<#if null == goodsBasic.consigneeEmail><font color="red">无</font><#else>${goodsBasic.consigneeEmail}</#if></p>
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
	                         <table id="goodsDetail_grid" >
	                            <thead>
	                                <tr>
	                                	<th data-field="state" data-checkbox="true" data-formatter="stateFormatter"></th>  
							            <th data-field="goodsTypeId" data-formatter="formatGoodsType" >货物类型</th> 
							            <th data-field="goodsName"> 货物名称</th> 
							            <th data-field="weight"> 重量/吨</th> 
							            <th data-field="surplusWeight" data-formatter="formatSurplusWeight">剩余重量/吨</th> 
							            <th data-field="length" data-formatter="formatGoodsLength">长度/米</th> 
							            <th data-field="height" data-formatter="formatGoodsHeight">高度/米</th> 
							            <th data-field="diameter" data-formatter="formatGoodsDiameter">直径/cm</th>
								        <th data-field="wingWidth" data-formatter="formatGoodsWingWidth">翼宽</th>
								        <th data-field="weight" data-editable="true" data-type = "text" data-save = "client" data-validate = "required,number,isFloatGtZero" data-validatemethod = "validateWeight" data-formatter="formatWeight"><span style="color: red;">*&nbsp;&nbsp;</span>实际重量</th>
	                                </tr>
	                            </thead>
	                        </table>
                        </div>
                    </div>
                </div>
            </div> 
            <div class="col-sm-12"  >
            	<form id="roborder_from">
	            	<div style="" class="roborder ui-sortable">
	            		 <div class="row">
	                       <div class="col-sm-2">
	                            <div class="form-group">
	                                <label><span style="color: red;">*&nbsp;&nbsp;</span>单价(元)：</label>
	                                <input id="unitPrice" name="unitPrice" type="text" class="form-control" placeholder="请输入货运单价">
	                                <input id="goodsBasic.id" name="goodsBasic.id" type="hidden" value="${goodsBasic.id}">
	                            </div>
	                        </div> 
	                        <div class="col-sm-8">
		                          <div class="form-group">
		                            <label>备注：</label>
		                            <input id="remark" name="remark" type="text" class="form-control" placeholder="请输入备注信息">
		                        </div>
		                    </div>
	                    </div>
	                </div>
                </from>
            </div>
        </div>
    </div>
    <div id="selectcontacts"></div>
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
	    	var goodsDetail_grid = $("#goodsDetail_grid").bootstrapTable({
	    		url:"${WEB_PATH }/goods/goodsdetail/getListGoodsDetail.do?goodsBasicId=${goodsBasic.id}",
	            uniqueId:"id",
	            iconSize: "outline",
	            minimumCountColumns: 1, 
	            onLoadSuccess:goodsDetailLoadSuccess
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
        }); 
        function validatRobOrderInfo(){
        	if(validatGoodsDetail()){
        		validatCount++;
        		var $valid = $("#roborder_from").valid();
        		return $valid;
        	}
		}
		function validatGoodsDetail(){
			var selectRow = $("#goodsDetail_grid").bootstrapTable('getSelections');
			if(selectRow.length > 0){
				for(var i = 0; i < selectRow.length; i++) {
			        var goodsDetail = selectRow[i];
			        if(goodsDetail.weight == ""){
			        	layer.msg('<font color="red">货物详细：第'+(i+1)+'行,货物类型：['+goodsDetail.goodsType.name+'],实际重量必须填写！</font>', {icon: 5,time: 3000});
			        	return false;
			        }
			    }
		    }else{
		    	 layer.msg('<font color="red">温馨提示：请勾您需要抢单的货物详情！。</font>', {icon: 5}); 
		    	 return false;
		    }
		    return true;
		}
		
		function getRobOrderRecordInfo(){
			var robOrderRecordInfos = new Array();
			var goodsDetailData = $("#goodsDetail_grid").bootstrapTable('getSelections');
			for(var i = 0; i < goodsDetailData.length; i++) {
				 var goodsDetail = goodsDetailData[i];
		         robOrderRecordInfos.push({goodsDetailId:goodsDetail.id,weight:goodsDetail.weight});
		    }
		   	return robOrderRecordInfos;
		}
		
		function saveRobOrderInfo(status){
			 layer.msg('提交中......', {icon: 16,shade: 0.3,time:8000});
			 var roborder = $('#roborder_from').serializeJson();
			 var robOrderRecordInfos = JSON.stringify(getRobOrderRecordInfo());
			 roborder.robOrderRecordInfos = robOrderRecordInfos;
			 roborder.status = status;
			 $.yilinAjax({
		   	  	type:'POST',
		   	  	url:'${WEB_PATH }/order/roborder/saveRobOrderRecord.do',
		   	  	data:roborder,
		   	  	btn:null,
        		errorcallback:null,
        		successcallback:success
		   	 });
		}
		
		
		function test(){
			 if(grid_seccess == 0){
			 	layer.msg('<font color="red">货物详情还未加载完毕，请稍后再试！</font>', {icon: 5,time: 2000});
				return false;
			 }
			 if(validatRobOrderInfo()){
			 	
			 	 saveRobOrderInfo("apply");
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
				   	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				   	parent.$('#rob-order').attr("disabled",false);
					parent.layer.close(index); 
					
				});
			}else{
		    	layer.alert('<font color="red">'+result.msg+'</font>', {
				  skin: 'layui-layer-molv' ,
				  closeBtn: 0,
				  icon: 5
				},function(){
				   	parent.$('#rob-order').attr("disabled",false);
				});
			}
		}
		
		function formatGoodsType(index, row, element){
    		return row.goodsType.name; 
		}
		
		function formatSurplusWeight(val){
		    var value = "";
		    if(val == 0){
		    	value = "已抢完";
		    }else{
		    	value = val+'&nbsp;(吨)';
		    }
    		return '<font class="text-danger">'+value+'</font>'; 
		}
		function validateWeight(row,value){
    		if(value > row.surplusWeight){
    			return  "实际重量不能大于货物剩余重量，货物类型：["+row.goodsType.name+"]，货物剩余重量为:["+row.surplusWeight+"]！";
    		}
		}
		function formatGoodsLength(index, row, element){
    		return row.length == 0 ? "无" : row.length; 
		}
		function formatGoodsHeight(index, row, element){
    		return row.height == 0 ? "无" : row.height; 
		}
		function formatGoodsDiameter(index, row, element){
    		return row.diameter == 0 ? "无" : row.diameter; 
		}
		function formatGoodsWingWidth(index, row, element){
    		return row.wingWidth == 0 ? "无" : row.wingWidth; 
		}
		function formatWeight(value, row, index){
			if(row.surplusWeight == 0){
				var editable = new Object();
	            editable = {
	                "value": '<font class="text-danger">已抢完</font>',
	                "editable": "false" 
	            };
				return editable;
			}
			return "";
		}
		
		function stateFormatter(value, row, index) {
	        if (row.surplusWeight == 0) {
	            return {
	                disabled: true
	            };
	        }
	        return value;
	    }
		function goodsDetailLoadSuccess(){
			grid_seccess++;
		}
    </script>
</body>

</html>