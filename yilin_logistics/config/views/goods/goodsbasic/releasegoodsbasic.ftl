
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
		    color: red;
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
		.route:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: red;
		    content: "路线信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.route {
		    margin-left: 0px;
		    padding:40px 15px 10px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		#routeMap {width:100%; height:340px;overflow: hidden;margin:0;}
		#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}
		#r-result{height:100%;width:20%;float:left;}
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
        		<div class="modal-footer" style="z-index:99995;text-align: center;">
		        	<button type="button" class="btn btn-primary btn-w-m" id="saveBut"><i class="fa fa-save"></i>&nbsp;保存</button>
		        	<button type="button" class="btn btn-danger btn-w-m" id="subBut"><i class="glyphicon glyphicon-open"></i>&nbsp;提交</button>
		            <button type="button" class="btn btn-white btn-w-m" id="close-but-goods"><i class="fa fa-close"></i>&nbsp;关闭</button>
		        </div>
	        	<form id="goodsbasic_detail_info_form">
	        		<input id="stockTypeNames" name="stockTypeNames" type="hidden" >
	        		<input id="stockTypes" name="stockTypeNames" type="hidden" >
		            <div class="col-sm-6" style="padding-right: 0px;">
			            <div class="zero-clipboard">
		            	    <div class="btn-clipboard">
		            	    	<div class="checkbox checkbox-success checkbox-inline">
		                            <input type="checkbox" id="isSaveConsignorCheckbox" name="isSaveConsignorCheckbox" value="true" >
		                            <label for="isSaveConsignorCheckbox">保存到常用发货人</label>
		                            <input type="hidden" id="saveConsignor" name = "saveConsignor" value="false" >
		                        </div>
		                       	<button type="button" class="btn btn-danger btn-xs" id="consignor_btn" data-parameter = "consignor"><i class="fa fa-table"></i>&nbsp;发货人选择</button>
		                    </div> 
						</div>
		            	<div style="" class="deliverGoods ui-sortable">
		            		 <div class="row"> 
		                        <div class="col-sm-6">
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>出发地：</label>
		                                <input id="deliveryAreaName" name="deliveryAreaName" class="form-control" readonly type="text" value="">
										<input id="deliveryAreaId" name="deliveryAreaId" type="hidden" >
		                            </div>
		                        </div> 
		                        <div class="col-sm-6">
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>出发地址：</label>
		                                <input id="deliveryAddress" name="deliveryAddress" type="text" style="width:92%;" class="form-control" placeholder="请输入出发地址">
		                                <a style="cursor:hand" id="deliveryAddress_point"><i class="glyphicon glyphicon-map-marker" style="color: #1AB394;font-size: 26px;float: right;margin-top: -30px;cursor:hand"></i></a>
		                                <input id="deliveryCoordinate" name="deliveryCoordinate" type="hidden" >
		                            </div>
		                        </div> 
		                    </div>
		                     <div class="row">
		                        <div class="col-sm-4">
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>发货人：</label>
		                                <input id="deliveryName" name="deliveryName" type="text" class="form-control" placeholder="请输入发货人姓名" value="${idcard.name}" readonly>
		                            </div> 
		                        </div>
		                        <div class="col-sm-4"> 
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>发货人联系电话：</label>
		                                <input id="deliveryMobile" name="deliveryMobile" type="text" class="form-control" placeholder="请输入发货人联系电话${account.phone}" value="${account.phone}" readonly>
		                            </div>
		                        </div>
		                        <div class="col-sm-4"> 
		                            <div class="form-group">
		                                <label>发货人邮箱：</label>
		                                <input id="deliveryEmail" name="deliveryEmail" type="text" class="form-control" placeholder="请输入发货人邮箱">
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-sm-6">
		            	<div class="zero-clipboard">
		            	    <div class="btn-clipboard">
		            	    	<div class="checkbox checkbox-success checkbox-inline">
		                            <input type="checkbox" id="isSaveConsigneeCheckbox" name="isSaveConsigneeCheckbox" value="true" >
		                            <label for="isSaveConsigneeCheckbox">保存到常用收货人</label>
		                            <input type="hidden" id="saveConsignee" name = "saveConsignee" value="false" >
		                        </div>
		                       	<button type="button" class="btn btn-danger btn-xs" id="consignee_btn" data-parameter = "consignee"><i class="fa fa-table"></i>&nbsp;收货人选择</button>
		                    </div> 
						</div>
		            	<div style="" class="consignee ui-sortable">
		            		 <div class="row"> 
		                        <div class="col-sm-6">
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>收货地：</label>
		                                <input id="consigneeAreaName" name="consigneeAreaName" class="form-control" readonly type="text" value="">
										<input id="consigneeAreaId" name="consigneeAreaId" type="hidden" >
		                            </div>
		                        </div> 
		                        <div class="col-sm-6">
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>收货地址：</label>
		                                <input id="consigneeAddress" name="consigneeAddress" type="text" style="width:92%;" class="form-control" placeholder="请输入收货地址">
		                                <a style="cursor:hand" id="consigneeAddress_point"><i class="glyphicon glyphicon-map-marker" style="color: red;font-size: 26px;float: right;margin-top: -30px;cursor:hand"></i></a>
		                                <input id="consigneeCoordinate" name="consigneeCoordinate" type="hidden" >
		                            </div>
		                        </div> 
		                    </div>
		                     <div class="row">
		                        <div class="col-sm-4">
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>收货人：</label>
		                                <input id="consigneeName" name="consigneeName" type="text" class="form-control" placeholder="请输入收货人姓名">
		                            </div> 
		                        </div>
		                        <div class="col-sm-4"> 
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>联系电话：</label>
		                                <input id="consigneeMobile" name="consigneeMobile" type="text" class="form-control" placeholder="请输入收货人联系电话">
		                            </div>
		                        </div>
		                        <div class="col-sm-4"> 
		                            <div class="form-group">
		                                <label>邮箱：</label>
		                                <input id="consigneeEmail" name="consigneeEmail" type="text" class="form-control" placeholder="请输入收货人邮箱">
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-sm-12" style="padding-top: 10px;display: none;" id="route_div" >
		            	<div style="" class="route ui-sortable">
		            		 <div class="panel panel-default">
				                <div class="panel-heading">
				                    <h4 class="panel-title">
				                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"><font id="routeMapArea"></font><font class="text-danger" id="routeMapInfo">正在计算中......</font></a>
				                        <input id="mapKilometer" name="mapKilometer" type="hidden">
				                    </h4>
				                </div>
				                <div id="collapseTwo" class="panel-collapse collapse in">
				                    <div class="panel-body">
				                         <div id="routeMap"></div>
				                    </div>
				                </div>
				             </div>
		                </div>
		            </div>
		            <div class="col-sm-12" style="padding-top: 10px;"  >
		            	<div style="" class="goodsbasic ui-sortable">
		            		 <div class="row">
		                        <div class="col-sm-4">
		                            <div class="form-group">
		                                <label class="control-label" style="margin-bottom: 8px"><font color="red">*&nbsp;</font>选择载货车辆类型：</label>
	                        			<label class="control-label" style="color:red;" id="erro-tip" hidden>载货车辆类型不能为空。</label>
		                                <select data-placeholder="选择载货车辆类型" id="stock" class="chosen-select form-control" multiple>
		                                	<@truckType>
		                        				<#list truckTypeViews as vo>
		                        					<option value="${vo.id}" hassubinfo="true">${vo.name}</option>
		                        				</#list>
		                        			</@truckType>
		                                </select>
		                            </div> 
		                        </div>
		                        <div class="col-sm-4">
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>截至时间：</label>
		                                <input id="finiteTimeStr" name="finiteTimeStr" class="form-control" style="width:75%;" readonly type="text"  placeholder="请选择时间">
		                                <div style="float: right;margin-top: -25px;">
		                                	<div class="checkbox checkbox-success checkbox-inline" >
					                            <input type="checkbox" id="longTimeCheckbox" name="longTimeCheckbox" value="true" >
					                            <label for="longTimeCheckbox">是否长期</label>
					                            <input type="hidden" id="longTime" name = "longTime" value="false" >
					                        </div>
		                                </div>
		                            </div>
		                        </div> 
		                       <div class="col-sm-4">
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>单价(元)：</label>
		                                <input id="unitPrice" name="unitPrice" type="text" class="form-control" placeholder="请输入货物单价">
		                            </div>
		                        </div> 
		                    </div>
		                    <div class="row">
		                        <div class="col-sm-12">
		                              <div class="form-group">
		                                <label>货物说明：</label>
		                                <input id="remark" name="remark" type="text" class="form-control" placeholder="请输入备注信息">
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
	            </form>
	            
	            <div class="col-sm-12" style="padding-top: 10px;">
	            	<div style="" class="goodsdetail ui-sortable" >
	            		<div class="row">
	            			 <div class="col-sm-12">
	            			 	 <div class="btn-group" id="goodsDetail_grid_Toolbar" role="group">
	                                <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="add_goodsdetail" >
	                                    <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;添加货物
				                    </button>
				                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="remove_goodsdetail" >
				                        <i class="glyphicon glyphicon-remove" aria-hidden="true"></i>&nbsp;&nbsp;删除
				                    </button>
	                             </div>
		                         <table id="goodsDetail_grid" >
		                            <thead>
		                                <tr>
		                                	<th data-field="state" data-radio="true"></th>
								            <!--<th data-field="goodsTypeId" data-editable="true" data-type = "select" data-save = "client" data-validate = "required" data-source="${WEB_PATH}/global/data/getGoodsTypeById.do"><span style="color:red;">*&nbsp;&nbsp;</span>货物类型</th>-->
								            <th data-field="goodsType" data-editable="true" data-type = "select" data-save = "client" data-validate = "required" data-source="${WEB_PATH}/global/data/getGoodsType.do"><span style="color:red;">*&nbsp;&nbsp;</span>货物类型</th> 
								            <!--<th data-field="goodsName" data-editable="true" data-type = "text" data-save = "client" data-validate = "required"><span style="color: red;">*&nbsp;&nbsp;</span>货物名称</th>-->
								            <th data-field="goodsTypeId" data-editable="true" data-type = "select" data-save = "client" data-validate = "required"  source-cache="false" data-source='getName' ><span style="color: red;">*&nbsp;&nbsp;</span>货物名称</th>
								            <th data-field="specifica" data-editable="true" data-type = "text" data-save = "client" data-validate = "required"><span style="color: red;">*&nbsp;&nbsp;</span>规格</th>
								            <th data-field="weight" data-editable="true" data-type = "text" data-save = "client" data-validate = "required,number" ><span style="color: red;">*&nbsp;&nbsp;</span>重量/吨</th> 
								            <th data-field="length"  data-editable="true" data-type = "text" data-save = "client" data-validate = "number">长度/米</th> 
								            <th data-field="height" data-editable="true" data-type = "text" data-save = "client" data-validate = "number">高度/米</th> 
								            <th data-field="diameter" data-editable="true" data-type = "text" data-save = "client" data-validate = "number">直径/cm</th>
									        <th data-field="wingWidth" data-editable="true" data-type = "text" data-save = "client" data-validate = "number">翼宽</th>
		                                </tr>
		                            </thead>
		                       </table>
	                      </div>
	                 </div>
	             </div>
            </div>
        </div>
    </div>
    <div id="selectcontacts"></div>
    <script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/ajax.extend.js"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${WEB_PATH}/resources/js/plugins/wizard/jquery.bootstrap.wizard.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/stickup/stickUp.min.js"></script>
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
		var goodsTypes_id = "";
		var validatCount = 0;
		var deliveryAreaAddress = "";
		var consigneeAreaAddress = "";
		var finiteTimeStrVal = "";
    	$(function(){
    		$(window).resize(function () {
		        $('#goodsDetail_grid').bootstrapTable('resetView');
			});
    		$('#deliveryAreaName').citypicker({areaId:'deliveryAreaId'}); 
    		$('#consigneeAreaName').citypicker({areaId:'consigneeAreaId'}); 
    		$("#stock").chosen({width:'100%'}); 
    		$("#stock").chosen().change(function(){
			    $("#erro-tip").hide();
			});
    		/*$('#goodsType_id').on('change', function(e, params) {
    			if(goodsTypes_id != params.selected){
    				$("#goodsDetail_grid").bootstrapTable('removeAll');
    			}
    			goodsTypes_id = params.selected;
    			
			});*/
			$("#finiteTimeStr").datepicker({
		        keyboardNavigation: !1,
		        forceParse: !1,
		        autoclose: !1
		    });
		    
			var $goodsbasic_detail_info_form =  $('#goodsbasic_detail_info_form').validate({
				  rules: {
				    deliveryAreaName: {
				      required: true
				    },
				    deliveryAddress: {
				      required: true
				    },
				    deliveryName: {
				      required: true
				    },
				    deliveryMobile:{
				      required: true,
				      isMobile: true
				    },
				    consigneeAreaName: {
				      required: true
				    },
				    consigneeAddress: {
				      required: true
				    },
				    consigneeName: {
				      required: true
				    },
				    consigneeMobile:{
				      required: true,
				      isMobile: true
				    },
				    goodsType_id: {
				      required: true
				    },
				    finiteTimeStr: {
				      required: true
				    },
				    unitPrice: {
				      required: true,
				      number: true
				    },
				    stockTypeNames:{
				      required: true
				    },
				    stockTypes:{
				      required: true
				    }
				  },
				  messages: {
		            name: {
		                remote:e+"商户名已存在，请重新输入！"
		            }
		          },
				  errorPlacement: function(error, element) {
				  		if(element.get(0).id == "deliveryAreaName"){
		            		$('#deliveryAreaName').citypicker("setError");
		            	}
		            	if(element.get(0).id == "consigneeAreaName"){
		            		$('#consigneeAreaName').citypicker("setError");
		            	}
		            	element.before(error)
		          }
			});
			//加载table数据
	    	var goodsDetail_grid = $("#goodsDetail_grid").bootstrapTable({
	            pagination:true,
	            toolbar: "#goodsDetail_grid_Toolbar",
	            pageNumber:1,  
	            pageSize:10, 
	            uniqueId:"id",
	            iconSize: "outline",
	            minimumCountColumns: 1, 
	            clickToSelect: true,
	            onEditableSave: function (field, row, oldValue, $el) {
	            	var	tempRows =  $("#goodsDetail_grid").bootstrapTable("getSelections");
		       		var rempRow = (tempRows!=null && tempRows.length>0) ? tempRows[0] : null;
		       		var idx = -1;
		       		for(var i = 0;i<this.data.length;i++){
		       			if(this.data[i]==row){
		       				idx = i;
		       				break;
		       			}
		       		}
		       		if(rempRow==null) return;
		       		if(field=="goodsType" ){
		       			if(row.goodsType !=oldValue && idx !=-1   ){
			       			 $("#goodsDetail_grid").bootstrapTable("updateCell",{
			       			 	field:'goodsTypeId',
			       			 	index:idx,
			       			 	value:''
			       			 });
		       			 }
		       		}
	            }
	        });
	        
	       window.mySourceReady=function(options,source){
        	  if(source == "getName"||source.indexOf("getGoodsTypeById") != -1){
        	 	  var index = $(this.options.scope).parents('tr').eq(0).attr("data-index");
        	 	  if(index!=null && index != ""){
        	 	  	index = parseInt(index);
        	 	  }
        	 	  if(index!=null){
        	 	  	 var id = $("#goodsDetail_grid").bootstrapTable('getData')[index].goodsType;
	          	 	 options.source="${WEB_PATH}/global/data/getGoodsTypeById.do?id=" + id;
	          	  	 options.sourceCache=false;
	          	  }
	          }
	       }
	        $("#table_not_result").css("padding","5%");
	        $("#close-but-goods").click(function(){
				layer.open({
					  content: '<font color="#ed5565;">您可能有填写的信息未保存，确定关闭当前窗口吗？</font>',
					  icon: 2,
					  btn: ['确定', '取消'],
					  yes: function(index, layero){
					     	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(index); 
					  } 
				});
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
	        $("#delivery_btn").click(function(){
	        	  $("#selectcontacts").load("${WEB_PATH }/goods/goodsbasic/selectcontacts.do?contactsType="+$("#delivery_btn").attr("data-parameter"));
	        });
	        $("#consignee_btn").click(function(){
	        	  $("#selectcontacts").load("${WEB_PATH }/goods/goodsbasic/selectcontacts.do?contactsType="+$("#consignee_btn").attr("data-parameter"));
	        });
	        $("#consignor_btn").click(function(){
	        	  $("#selectcontacts").load("${WEB_PATH }/goods/goodsbasic/selectcontacts.do?contactsType="+$("#consignor_btn").attr("data-parameter"));
	        });
	        $("#longTimeCheckbox").click(function(){
	             
	        	 if($("#longTimeCheckbox").is(':checked')){
	        	 	finiteTimeStrVal = $("#finiteTimeStr").val();
	        	 	$("#finiteTimeStr").val("长期有效");
	        	 	$("#longTime").val(true);
	        	 }else{
	        	 	$("#finiteTimeStr").val(finiteTimeStrVal);
	        	 	$("#longTime").val(false);
	        	 }
	        });
	        $("#isSaveDeliveryCheckbox").click(function(){
	        	 if($("#isSaveDeliveryCheckbox").is(':checked')){
	        	 	$("#saveDelivery").val("true");
	        	 }else{
	        	 	$("#saveDelivery").val("false");
	        	 }
	        });
	        $("#isSaveConsigneeCheckbox").click(function(){
	        	 if($("#isSaveConsigneeCheckbox").is(':checked')){
	        	 	$("#saveConsignee").val("true");
	        	 }else{
	        	 	$("#saveConsignee").val("false");
	        	 }
	        });
	        $("#isSaveConsignorCheckbox").click(function(){
	        	 if($("#isSaveConsignorCheckbox").is(':checked')){
	        	 	$("#saveConsignor").val("true");
	        	 }else{
	        	 	$("#saveConsignor").val("false");
	        	 }
	        });
	        $("#deliveryAddress").blur(function(){
	        	
	        	var deliveryAreaName =  $("#deliveryAreaName").val();
				var deliveryAddress =  $("#deliveryAddress").val();
				if(deliveryAreaName == "" || deliveryAddress == "" ){
					layer.msg('<font color="red">温馨提示：请选择发货地并填写详细地址后查看地图。</font>', {icon: 5}); 
					return;
				}else{
					if(deliveryAreaAddress != "" &&  deliveryAreaAddress != deliveryAreaName+""+deliveryAddress){
						$("#deliveryCoordinate").val("");
			    	}
			    	$("#selectcontacts").load("${WEB_PATH }/goods/goodsbasic/view/gis.do?id=deliveryAreaName,deliveryAddress,deliveryCoordinate");
			    }
			    deliveryAreaAddress = deliveryAreaName+""+deliveryAddress;
			});
			
			$("#consigneeAddress").blur(function(){
				var consigneeAreaName =  $("#consigneeAreaName").val();
				var consigneeAddress =  $("#consigneeAddress").val();
				if(consigneeAreaName == "" || consigneeAddress == "" ){
					layer.msg('<font color="red">温馨提示：请选择收货地并填写详细地址后查看地图。</font>', {icon: 5}); 
					return;
				}else{
					if(consigneeAreaAddress != "" && consigneeAreaAddress != consigneeAreaName+""+consigneeAddress){
						$("#consigneeCoordinate").val("");
			    	}
			    	$("#selectcontacts").load("${WEB_PATH }/goods/goodsbasic/view/gis.do?id=consigneeAreaName,consigneeAddress,consigneeCoordinate");
			    }
			    consigneeAreaAddress = consigneeAreaName+""+consigneeAddress;
			});
			$("#deliveryAddress_point").click(function(){
				var deliveryAreaName =  $("#deliveryAreaName").val();
				var deliveryAddress =  $("#deliveryAddress").val();
				if(deliveryAreaName == "" || deliveryAddress == "" ){
					layer.msg('<font color="red">温馨提示：请选择发货地并填写详细地址后查看地图。</font>', {icon: 5}); 
					return;
				}
			    $("#selectcontacts").load("${WEB_PATH }/goods/goodsbasic/view/gis.do?id=deliveryAreaName,deliveryAddress,deliveryCoordinate");
			});
			$("#consigneeAddress_point").click(function(){
				var consigneeAreaName =  $("#consigneeAreaName").val();
				var consigneeAddress =  $("#consigneeAddress").val();
				if(consigneeAreaName == "" || consigneeAddress == "" ){
					layer.msg('<font color="red">温馨提示：请选择收货地并填写详细地址后查看地图。</font>', {icon: 5}); 
					return;
				}
			    $("#selectcontacts").load("${WEB_PATH }/goods/goodsbasic/view/gis.do?id=consigneeAreaName,consigneeAddress,consigneeCoordinate");
			});
        }); 
        jQuery(function($) {
	        $(document).ready( function() {
	          	$('.modal-footer').stickUp();
	        });
        });
        
        $("#add_goodsdetail").click(function(){
        	var randomId = 100 + ~~(Math.random() * 100);
        	$("#goodsDetail_grid").bootstrapTable('insertRow', {
                index: 0,
                row: {
                    id: randomId,
                    goodsTypeId:'',
                    weight: '',
                    length: '',
                    height: '',
                    diameter: '',
                    wingWidth: '',
                    specifica: ''
                }
            });
        });
        $("#remove_goodsdetail").click(function(){
        	var ids = $.map($("#goodsDetail_grid").bootstrapTable('getSelections'), function (row) {
                return row.id;
            });
            $("#goodsDetail_grid").bootstrapTable('remove', {
                field: 'id',
                values: ids
            });
        });
        
        function validatGoodsBasicDetailInfo(){
			var $valid = $("#goodsbasic_detail_info_form").valid();
  			if(!$valid) {
  				validatCount++;
  				return false;
  			}else{
  				var goodsDetailData = $("#goodsDetail_grid").bootstrapTable('getData');
  				if(goodsDetailData.length == 0){
  					layer.msg('<font color="red">请添加货物详细信息！。</font>', {icon: 5,time: 2000});
  					layer.tips('点击这里增加货物信息!', '#add_goodsdetail',{tips: [1, 'red'],time: 2000});
  					return false;
  				}else{
	        	 	var truckIds  = $("#stock").val() != null ? $("#stock").val().join(","):$("#stock").val();
					if(null == truckIds){
						$("#erro-tip").show();
						return false;
					}
  					for(var i = 0; i < goodsDetailData.length; i++) {
				        var goodsDetail = goodsDetailData[i];
				        if(goodsDetail.goodsType == ""){
				        	layer.msg('<font color="red">货物详细：第'+(i+1)+'行,货物类型必须选择！</font>', {icon: 5,time: 2000});
				        	return false;
				        }
				        if(goodsDetail.goodsTypeId == ""){
				        	layer.msg('<font color="red">货物详细：第'+(i+1)+'行,货物名称必须填写！</font>', {icon: 5,time: 2000});
				        	return false;
				        }
				        if(goodsDetail.weight == ""){
				        	layer.msg('<font color="red">货物详细：第'+(i+1)+'行,货物重量必须填写！</font>', {icon: 5,time: 2000});
				        	return false;
				        }
				        else{
				        	if(goodsDetail.weight <=0){
				        		layer.msg('<font color="red">货物详细：第'+(i+1)+'行,货物重量必须大于0！</font>', {icon: 5,time: 2000});
				        		return false;
				        	}
				        }
				        if(goodsDetail.specifica == ""){
				        	layer.msg('<font color="red">货物详细：第'+(i+1)+'行,货物规格必须填写！</font>', {icon: 5,time: 2000});
				        	return false;
				        }
				    }
				    return true;
  				}
  				return true;
  			} 
		}
		
		function saveGoodsBasicDetail(status){
			 var goodsBasic = $('#goodsbasic_detail_info_form').serializeJson();
			 var goodsDetailData = $("#goodsDetail_grid").bootstrapTable('getData');
			 var goodsDetail = JSON.stringify(goodsDetailData);
			 var truckIds  = $("#stock").val() != null ? $("#stock").val().join(","):$("#stock").val();
			 var truckNames = "";
			 var array = $(".search-choice");
			 for(i = 0;i<array.length;i++){
				 if(i == array.length - 1){
					 truckNames += array[i].innerText;
				 }else{
					 truckNames += array[i].innerText + ",";
				 }
			 }
			 goodsBasic.goodsDetail = goodsDetail;
			 goodsBasic.status = status;
			 goodsBasic.mapKilometer = $('#mapKilometer').val();
			 goodsBasic.stockTypeNames = truckNames;
			 goodsBasic.stockTypes = truckIds;
			 $('#saveBut').attr("disabled",true); 
			 $('#subBut').attr("disabled",true);    
			 $.yilinAjax({
		   	  	type:'POST',
		   	  	url:'${WEB_PATH }/goods/goodsbasic/saveSubGoodsBasicDetail.do',
		   	  	data:goodsBasic,
		   	  	btn:null,
        		errorcallback:null,
        		successcallback:success
		   	 });
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
					parent.layer.close(index); 
					
				});
			}else{
				$('#saveBut').attr("disabled",false); 
			 	$('#subBut').attr("disabled",false);       
		    	layer.alert('<font color="red">'+result.msg+'</font>', {
				  skin: 'layui-layer-molv' ,
				  closeBtn: 0,
				  icon: 5,
				});
			}
		}
		
		function mapDrivingRoute(pointStarte,pointEnd){  
			$('#route_div').show();
			var routemap = new BMap.Map("routeMap");
			routemap.centerAndZoom(new BMap.Point(116.404, 39.915), 12);
			routemap.enableScrollWheelZoom();//启动鼠标滚轮缩放地图
			routemap.enableKeyboard();//启动键盘操作地图
			var deliveryaddress = $('#deliveryAreaName').val()+"&nbsp;&nbsp;"+$('#deliveryAddress').val();
			var consigneeaddress = $('#consigneeAreaName').val()+"&nbsp;&nbsp;"+$('#consigneeAddress').val();
			var output = "";
			var searchComplete = function (results){
				if (transit.getStatus() != BMAP_STATUS_SUCCESS){
					return ;
				}
				var plan = results.getPlan(0);
				output += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总路程为：" ;
				output += plan.getDistance(true) + "，";             //获取距离
				output += "预计时间：";
				output += plan.getDuration(true) + "，";                //获取时间
				$('#routeMapInfo').html(output);
				$('#routeMapArea').html(deliveryaddress +"&nbsp;&nbsp;&nbsp;&nbsp;----->&nbsp;&nbsp;&nbsp;&nbsp;"+consigneeaddress);
				var mapKilometer = plan.getDistance(true);
				$('#mapKilometer').val(mapKilometer);
				
			}
			var transit = new BMap.DrivingRoute(routemap, {renderOptions: {map: routemap},
				onSearchComplete: searchComplete,
				onPolylinesSet: function(){        
			}});
			var point_delivery = pointStarte.split(",");
	     	var point_consignee = pointEnd.split(",");
	     	var point_starte = new BMap.Point(point_delivery[0],point_delivery[1]);
	     	var point_end = new BMap.Point(point_consignee[0],point_consignee[1]);
			transit.search(point_starte,point_end);
		}
    </script>
</body>

</html>