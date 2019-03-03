<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>应急处理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/js/plugins/ystep/css/ystep.css" rel="stylesheet">
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
		    content: "联系人信息";
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
		}
		.input-group-btn:last-child>.btn, .input-group-btn:last-child>.btn-group {
    z-index: 2;
    margin-left: -1px;
    margin-top: 23px;
}
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>应急处理</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <div class="example-wrap"> 
                        		<div style="" class="demo ui-sortable">
                            		 <div class="row" style="margin-bottom: 10px;"> 
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>发货地区：</label>
			                                    <input id="deliveryAreaName" name="deliveryAreaName" class="form-control" readonly type="text" value="">
												<input id="deliveryAreaId" name="deliveryAreaId" type="hidden" >
			                                </div>
			                            </div> 
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>收货货地区：</label>
			                                    <input id="consigneeAreaName" name="consigneeAreaName" class="form-control" readonly type="text" value="">
												<input id="consigneeAreaId" name="consigneeAreaId" type="hidden" >
			                                </div>
			                            </div> 
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                     <label>货物类型：</label>
				                                 <select data-placeholder="选择货物类型" id="goodsType_id" name="goodsType_id" class="chosen-select form-control" multiple>
				                                	<#list goodsTypeList as goodsType >
				                                		<option value="${goodsType.id}" hassubinfo="true">${goodsType.name}</option>
				                                 	</#list>
				                                 </select>
			                                </div>
			                            </div> 
			                            <div class="col-sm-3" style="padding-top: 22px;">
			                                 <button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
			                            </div> 
			                        </div>
			                        <div class="row">
			                           <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>订单号：</label>
			                                    <input id="robOrderNo" name="robOrderNo" class="form-control"  type="text" value="">
			                                </div>
			                            </div> 
			                             <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>运输单号：</label>
			                                    <input id="transportNo" name="transportNo" class="form-control"  type="text" value="">
			                                </div>
			                            </div> 
			                             
			                             <div class="col-sm-3"> 
			                                <div class="form-group">
			                                    <label>申请类型：</label>
			                                    <select data-placeholder="选择申请类型" id="specialType" class="chosen-select form-control">
			                                       <option value="" hassubinfo="true" ></option>
			                                   		<option value="0" hassubinfo="true">急救</option>
				                                    <option value="1" hassubinfo="true">仲裁</option>
				                                </select>
			                                </div>
			                            </div>
			                            <div class="col-sm-3"> 
			                                <div class="form-group">
			                                    <label>状态：</label>
			                                    <select data-placeholder="选择处理状态" id="specialStatus" class="chosen-select form-control" multiple   >
			                                   		<option value="0" hassubinfo="true" selected = "selected" >等待介入</option>
				                                    <option value="1" hassubinfo="true" selected = "selected" >正在处理</option>
				                                    <option value="2" hassubinfo="true" >处理完成</option>
				                                </select>
			                                </div>
			                            </div>
			                        </div>
			                            
			                        </div>
			                    </div>
                                <table id="exampleTableEvents"  data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="robbedCompanyName">发货商户</th>
		                                    <th data-field="companyName">接单商户</th>
		                                    <th data-field="robOrderNo">订单号</th>
								            <th data-field="deliveryAreaName" data-formatter="formatAreaName">路线(出发地/目的地)</th>
								            <th data-field="goodsType" data-formatter="formatGoodsType">货物类型</th>
								            <th data-field="unitPrice" data-formatter="formatUnitPrice">单价</th>
								            <th data-field="weight" data-formatter="formatWeight">总重量/吨</th>
								            <th data-field="totalPrice" data-formatter="formatTotalPrice">总价（总重量*单价）</th>
								             <th data-field="depositUnitPrice" data-formatter="formatDeposit">运输押金总金额</th>
								            <!--<th data-field="status" data-formatter="formatStatus">状态</th>-->
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Panel Other -->
    </div>
    <div id="win_add"></div>
    <@import.tableManagerImportScript/> 
    <script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/switchery/switchery.js"></script>
    <script src="${WEB_PATH}/resources/js/city-picker.data.js"></script>
	<script src="${WEB_PATH}/resources/js/city-picker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/ystep/js/ystep.js"></script> 
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=1AC8RPrl58FNLoYjoEhOiwv8SsgToWRm"></script>
       <script src="${WEB_PATH}/resources/js/plugins/fancybox/jquery.fancybox.js"></script>
   
   <script>
		$(function(){
			
		});  
	</script>
    <script >
    
    	$(function(){
    	
	    	$("#company").bsSuggest('init', {
	    	   getDataMethod: "url",   //获取数据的方式，总是从 URL 获取
	    	   autoDropup: true,       //自动判断菜单向上展开
	            url: "${WEB_PATH }/order/myorder/getCompanyList.do?keyword=",
	            effectiveFields: ["name"],
	            searchFields: [ "name"],
	            effectiveFieldsAlias:{name: "姓名"},
	            idField: "id",
	            keyField: "name",
	            fnProcessData: function (json){
			      return json;
	            }
	        }).on('onDataRequestSuccess', function (e, result) {
	            console.log('onDataRequestSuccess: ', result);
	        }).on('onSetSelectValue', function (e, keyword, data) {
	            console.log('onSetSelectValue: ', keyword, data);
	        }).on('onUnsetSelectValue', function () {
	            console.log('onUnsetSelectValue');
	        });
	        
	        $("#robbedCompany").bsSuggest('init', {
	    	   getDataMethod: "url",   //获取数据的方式，总是从 URL 获取
	    	   autoDropup: true,       //自动判断菜单向上展开
	            url: "${WEB_PATH }/order/myorder/getCompanyList.do?keyword=",
	            effectiveFields: ["name"],
	            searchFields: [ "name"],
	            effectiveFieldsAlias:{name: "姓名"},
	            idField: "id",
	            keyField: "name",
	            fnProcessData: function (json){
			      return json;
	            }
	        }).on('onDataRequestSuccess', function (e, result) {
	            console.log('onDataRequestSuccess: ', result);
	        }).on('onSetSelectValue', function (e, keyword, data) {
	            console.log('onSetSelectValue: ', keyword, data);
	        }).on('onUnsetSelectValue', function () {
	            console.log('onUnsetSelectValue');
	        });
		    
    	
    	});
    	
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        deliveryAreaName:$("#deliveryAreaName").val(),
		        consigneeAreaName:$("#consigneeAreaName").val(),
		        goodsTypeId:$("#goodsType_id").val() != null ? $("#goodsType_id").val().join(","):$("#goodsType_id").val(),
		        specialStatusIDs: $("#specialStatus").val() != null ? $("#specialStatus").val().join(","):$("#specialStatus").val(),
		        search: $("input.form-control.input-outline").val(),
		        robOrderNo: $("#robOrderNo").val(),
		        transportNo:$("#transportNo").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
		        companyName:$("#company").val(),
		        specialType:$("#specialType").val(),
		        robbedCompanyName:$("#robbedCompany").val()
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
    		layer.config({
				extend: 'extend/layer.ext.js'
			});  
    		$('#deliveryAreaName').citypicker({areaId:'deliveryAreaId'}); 
    		$('#consigneeAreaName').citypicker({areaId:'consigneeAreaId'}); 
    		$("#goodsType_id").chosen({width:'100%'}); 
    		$("#specialStatus").chosen({width:'100%'}); 
    		$("#specialType").chosen({width:'100%'}); 
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
			});
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/order/myorder/specialorder.do",
	            method: 'get',
	            search:false,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
	            showRefresh:true, 
	            showColumns:true,
	            detailView:true,
	            //detailFormatter:detailFormatter, 
	            onClickRow:onClickContacts,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
	            sidePagination: "server", //设置为服务器端分页
	            queryParams: queryParams,//参数
	            minimumCountColumns: 1, 
	           // clickToSelect: true, 
	            onExpandRow:subTableFormatter
	        }); 
	         //点击查询搜索
			$("#search").click(function(){
				$("#exampleTableEvents").bootstrapTable('refresh');
			});
	        $('input.form-control.input-outline').attr("placeholder","请输入收获地址");
        });
        
        
        $('#rob-cancel').click(function(){
        	  var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	  var info = selectRow[0]; 
	          if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条抢单信息，进行取消！</font>', {icon: 5}); 
			    return ;
	       	  }
	          layer.prompt({title: '请填写取消原因（必须填写）', formType: 2}, function(value, index, elem){
			  		saveRobOrderCancel(info.id,value,index);
			  }); 
        });
        
        $("#translate-but").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条抢单信息，进行撤回！</font>', {icon: 5}); 
			    return ;
	       	}
        	layer.confirm('尊敬的用户您好：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">您确定把当前抢单信息撤回吗？</span>',{icon: 3, title:'抢单撤回信息提示',btn: ['确定','取消']},
    		function(){
    			  $.yilinAjax({
			   	  	 type:'POST',
			   	  	 url:'${WEB_PATH }/order/roborder/withdrawRobOrder.do?id='+ info.id, 
			   	  	 data:null,
            		 errorcallback:null,
            		 successcallback:successTranslate
			   	  });
			}, function(){
				  layer.closeAll();
			});
        });
        
        $('#confirm-but').click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条抢单信息，进行确认！</font>', {icon: 5}); 
			    return ;
	       	} 
		    layer.open({
		      type: 2,
		      title: '<span style="color: #ed5565"><i class="fa fa-pencil"></i>&nbsp;&nbsp;抢单信息确认</span>',
		      shadeClose: true,
		      shade: false,
		      maxmin: false, //开启最大化最小化按钮
		      area: ['88%', '88%'],
		      content: '${WEB_PATH }/order/roborder/confirmroborder.do?id='+info.id,
		      btn: ['<i class="fa fa-check"></i>&nbsp;确认','取消'],
		      yes: function(index, layero){
			      var body = layer.getChildFrame('body', index);
			      var iframeWin = window[layero.find('iframe')[0]['name']];
			      iframeWin.confirmRobOrder();
			  }, 
			  cancel: function(){  
					layer.open({
					  content: '<font color="#ed5565;">您可能有填写的信息未保存，确定关闭当前窗口吗？</font>',
					  icon: 2,
					  btn: ['确定', '取消'],
					  yes: function(index, layero){
					    	layer.closeAll();
					  } 
					});
					return false;
			  } 
		    });
        });
        
        $('#edit-but').click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条抢单信息，进行编辑！</font>', {icon: 5}); 
			    return ;
	       	}
	       	 
		    layer.open({
		      type: 2,
		      title: '<span style="color: #ed5565"><i class="fa fa-pencil"></i>&nbsp;&nbsp;抢单信息修改</span>',
		      shadeClose: true,
		      shade: false,
		      maxmin: false, //开启最大化最小化按钮
		      area: ['88%', '88%'],
		      content: '${WEB_PATH }/order/roborder/editroborder.do?id='+info.id,
		      btn: ['<i class="fa fa-hand-rock-o"></i>&nbsp;提交','取消'],
		      yes: function(index, layero){
			      var body = layer.getChildFrame('body', index);
			      var iframeWin = window[layero.find('iframe')[0]['name']];
			      iframeWin.updateRobOrder();
			  }, 
			  cancel: function(){  
					layer.open({
					  content: '<font color="#ed5565;">您可能有填写的信息未保存，确定关闭当前窗口吗？</font>',
					  icon: 2,
					  btn: ['确定', '取消'],
					  yes: function(index, layero){
					    	layer.closeAll();
					  } 
					});
					return false;
			  } 
		    });
        });
        
        function onClickContacts(index, row, element){
			if(index.status == 'apply'){
				$("#edit-but").attr("disabled",true); 
		 	 	$("#translate-but").attr("disabled",false); 
		 	 	$("#rob-cancel").attr("disabled",false); 
		 	 	$("#confirm-but").attr("disabled",true); 
			}else if(index.status == "withdraw"){
				$("#edit-but").attr("disabled",false); 
		 	 	$("#translate-but").attr("disabled",true); 
		 	 	$("#rob-cancel").attr("disabled",false); 
		 	 	$("#confirm-but").attr("disabled",true); 
			}else if(index.status == "back"){
				$("#edit-but").attr("disabled",false); 
		 	 	$("#translate-but").attr("disabled",true); 
		 	 	$("#rob-cancel").attr("disabled",false); 
		 	 	$("#confirm-but").attr("disabled",true); 
			}else if(index.status == "success"){
				$("#edit-but").attr("disabled",true); 
		 	 	$("#translate-but").attr("disabled",true); 
		 	 	$("#rob-cancel").attr("disabled",false); 
		 	 	$("#confirm-but").attr("disabled",false); 
			}else if(index.status == "dealing"){
				$("#edit-but").attr("disabled",true); 
		 	 	$("#translate-but").attr("disabled",true); 
		 	 	$("#rob-cancel").attr("disabled",true); 
		 	 	$("#confirm-but").attr("disabled",true); 
			} 
		}
        
        function successTranslate(result) {
			layer.closeAll();
			if(result.success == true){
				swal("撤回成功！", result.msg, "success");
				$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				swal("撤回失败！", result.msg, "error");
				$("#exampleTableEvents").bootstrapTable('refresh');
			}
		}
        
        var prompt = null;
        function  saveRobOrderCancel(id,text,index){
        	prompt = index;
        	layer.msg('', {icon: 16,shade: 0.3,time:8000});
			$.yilinAjax({
			  	 type:'POST',
			  	 loadmsg:'取消中......',
			  	 url:'${WEB_PATH }/order/roborder/saveRobOrderCancel.do',
			  	 data:{id:id,cancelRemark:text},
				 errorcallback:null,
				 btn:null,
				 successcallback:success
			});
        }
        
        function success(result){  
			layer.closeAll(); 
			if(result.success == true){
				layer.close(prompt); 
		    	layer.msg(result.msg, {icon: 1}); 
		    	$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				layer.close(prompt); 
				layer.msg('<font color="red">'+result.msg+'</font>', {icon: 5}); 
			}
		} 
		
		function subTableFormatter(index, row, $detail){
			 var cur_table = $detail.html('<table></table>').find('table');
			 $(cur_table).bootstrapTable({
		            url: "${WEB_PATH }/order/myorder/getTruckPage.do",
		            method: 'get',
		            queryParams:function(params){
		              var temp = {  
					        limit: params.limit,  
					        offset: params.offset,
					        maxrows: params.limit,
					        pageindex:params.pageNumber,
					        robOrderId: row.id,
					        isSpecial:true
				        };
				        return temp;
		            },
		           // queryParams: { robOrderId: row.id},
		            clickToSelect: true,
		            detailView: true,//父子表
		            uniqueId: "robOrderId",
		            pagination:false,
	                pageNumber:1,  
	                pageSize:10,   
		            sidePagination: "server", //设置为服务器端分页
		            pageSize: 10,
		            pageList: [10, 25],
		            onExpandRow:subTableFormatter_1,
		            columns: [{
		                field: 'transportNo',
		                title: '运输单号'
		            },{
		                field: 'turckUserName',
		                title: '司机'
		            }, {
		                field: 'phone',
		                title: '司机号码'
		            }, {
		                field: 'trackNo',
		                title: '车牌号'
		            }, {
		                field: 'cardType',
		                title: '车辆类型'
		            },{
		                field: 'unitPrice',
		                title: '单价',
		                formatter:function(val){
		                	return '<font class="text-danger">¥&nbsp;'+val+'</font>';
		                	
		                }
		            },{
		                field: 'totalWeight',
		                title: '总重量/吨',
		                formatter:function(val){
		                	return '<font class="text-danger">'+val+' (吨)</font>';
		                	
		                }
		            },{
		                field: 'transportationCost',
		                title: '总价',
		                formatter:function(val){
		                	return '<font class="text-danger">¥&nbsp;'+val+'</font>';
		                	
		                }
		            },{
		                field: 'transportationDeposit',
		                title: '运输押金总金额',
		                formatter:function(val){
		                	return '<font class="text-danger">¥&nbsp;'+val+'</font>';
		                	
		                }
		            },{
		                field: 'turckCost',
		                title: '车辆押金总金额',
		                formatter:function(val){
		                	return '<font class="text-danger">¥&nbsp;'+val+'</font>';
		                	
		                }
		            },{
		                field: 'status',
		                title: '运单状态',
		                formatter:function(val){
		                	if(val == 0){
								return '<span class="label label-default">待装货</span>';
							}else if(val == "1"){
								return '<span class="label label-primary">确认装货</span>';
							}else if(val == "2"){
								return '<span class="label label-primary">运输中</span>';
							}else if(val == "3"){
								return '<span class="label label-warning">送达</span>';
							}else if(val == "4"){
								return '<span class="label label-success">回执发还中</span>';
							}else if(val == "5"){
								return '<span class="label label-danger">送还回执中</span>';
							}else if(val == "6"){
								return '<span class="label label-primary">订单完结</span>';
							}else if(val == "7"){
								return '<span class="label la label-danger">已销单</span>';
							}
		                }
		            },{
		                field: 'specialtype',
		               	title: '请求类型',
		               	formatter:function(val,row,index){
		               		if(val==0){
		               			return '<span class="label label-danger">请求急救</span>';
		               		}else if(val==1){
		               		 	return '<span class="label label-danger">申请仲裁</span>';
		               		}
		                }
		             },{
		                field: 'specialStatus',
		               	title: '处理状态',
		               	formatter:function(val,row,index){
		               		if(val==0){
		               			return '<span class="label label-default">等待介入</span>';
		               		}else if(val==1){
		               		 	return '<span class="label label-warning">正在处理</span>';
		               		}else if(val==2){
		               		 	return '<span class="label label-success">处理完成</span>';
		               		}
		                }
		             }]
	          });
		}
		
		
		function subTableFormatter_1(index, row, $detail){
			 var info = $detail.html('<div><div id="info"></div>').find('#info');
			 if(row.specialtype==0){
			 	infoload(info,row);
			 }else{
			 	zcinfoload(info,row);
			 }
			 
		}
		
		
		
		function getValidate(){
		    var a_submit  = {
		    		rules:{
			 			remark: {
					      required: true
					    },
					    processingType: {
					      required: true
					    },
					    savetype:{
					    	required: true
					    }
			 		},
			 		 messages:{
			 		 	remark: {
		                	required:e+"请输入处理意见！"
		            	},
		            	processingType:{
		            		required:e+"请选择处理类型"
		            	},
		            	savetype: {
		                	required:e+"请选择处理步骤！"
		            	}
			 		 },
			 		 errorPlacement: function(error, element) {
			 		    console.log(element.parent().parent().parent().find('.form-group'));
			 		 	if (element.is(':radio')){
			 		 	 error.appendTo(element.parent().parent().parent().find('.form-group')); //将错误信息添加当前元素的父结点后面
			 		 	}else{
			 		 		 error.insertAfter(element);
			 		 	}
		            	
		             }
		    };
		    return a_submit;
		}
		
		function getValidate_1(){
		    var a_submit  ={
		    	rules:{
			 			remark: {
					      required: true
					    },
					    savetype:{
					    	required: true
					    }
			 		},
			 		 messages:{
			 		 	remark: {
		                	required:e+"请输入处理意见！"
		            	},
		            	savetype: {
		                	required:e+"请选择处理步骤！"
		            	}
		            	
			 		 }
		    
		    };
		    return a_submit;
		}
		
		
		//仲裁处理
		function zcinfoload(info,row){
			
			 var div = info.load("${WEB_PATH}/order/myorder/view/arbitrationinfo.do?robConfirmId="+row.robConfirmId,function(){
			 	$(".fancybox").fancybox({openEffect:"none",closeEffect:"none"}); 
			 	 var validate_a_submit = jQuery.extend({}, $.validator.defaults, getValidate_1());
			 	 $("#log_info_form").validate(validate_a_submit);
			 	
			 	 
			 	 
			 	  $("#saveBtn").on('click',function(){
			 	  
			 	       var savetype = $("#savetype").val();
				 	   if(savetype==1){//赔偿
				 	   		var processingType = $('input:radio[name=rocessingResult]:checked').val();
				 	   		if(processingType==undefined){
				 	   		    $("#type-form").find("#savetype-error");
				 	   			var label = $('<label id="savetype-error" class="error" for="remark"><i class="fa fa-times-circle"></i> 请输入处理意见！</label>');
				 	   			label.appendTo($("#type-form")); //将错误信息添加当前元素的父结点后面
				 	   			return false;
				 	   		}
				 	   }
			 	  
			 	  
			 	      var $valid = $("#log_info_form").valid();
			 	      if(!$valid){
			 	    	return false;
			 	      }
			 	      
			 	    var processingType = $('input:radio[name=rocessingResult]:checked').val()=='indemnify' ? 1 : 0;
			 	    if(processingType==1){ //赔偿
			 	    layer.confirm('<span style="color: red">'+$("#message").html()+'</span>',{icon: 3, title:'赔偿信息提示',btn: ['确定','取消']},
			    		function(){
			 	    		var loginfo = $('#log_info_form').serializeJson();
			    			
			    		   $.yilinAjax({
					   	  	type:'POST',
					   	  	loadmsg:'保存中......',
					   	  	url:'${WEB_PATH }/order/myorder/savearbitration.do',
					   	  	data:loginfo,
					   	  	btn:null,
			        		errorcallback:null,
			        		successcallback:function(result){
			        			var savetype = $("#savetype").val();
			        			if(savetype==0){
			        				$("tr[data-uniqueid='"+row.robOrderId+"'] td").last().html('<td style=""><span class="label label-warning">正在处理</span></td>');
			        			}else{
			        				$("tr[data-uniqueid='"+row.robOrderId+"'] td").last().html('<td style=""><span class="label label-success">处理完成</span></td>');
			        			}
			        		
			        			if(result.success == true){
			        					layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
										  	skin: 'layui-layer-molv',
										  	icon: 1,
										  	closeBtn: 0
										},function(){
											zcinfoload(info,row);
											layer.closeAll();
										});
								}else{
									layer.alert('<font color="red">'+result.msg+'</font>', {
									  skin: 'layui-layer-molv' ,
									  closeBtn: 0,
									  icon: 5
									});
								}
			        		}
					   	 });
			    			
			    			
						}, function(){
							  //layer.closeAll();
						});
			 	    }else{
			 	    	
			 	    		var loginfo = $('#log_info_form').serializeJson();
			    			
			    		   $.yilinAjax({
					   	  	type:'POST',
					   	  	loadmsg:'保存中......',
					   	  	url:'${WEB_PATH }/order/myorder/savearbitration.do',
					   	  	data:loginfo,
					   	  	btn:null,
			        		errorcallback:null,
			        		successcallback:function(result){
			        			var savetype = $("#savetype").val();
			        			if(savetype==0){
			        				$("tr[data-uniqueid='"+row.robOrderId+"'] td").last().html('<td style=""><span class="label label-warning">正在处理</span></td>');
			        			}else{
			        				$("tr[data-uniqueid='"+row.robOrderId+"'] td").last().html('<td style=""><span class="label label-success">处理完成</span></td>');
			        			}
			        			
			        			if(result.success == true){
			        					layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
										  	skin: 'layui-layer-molv',
										  	icon: 1,
										  	closeBtn: 0
										},function(){
											zcinfoload(info,row);
											layer.closeAll();
										});
								}else{
									layer.alert('<font color="red">'+result.msg+'</font>', {
									  skin: 'layui-layer-molv' ,
									  closeBtn: 0,
									  icon: 5
									});
								}
			        		}
					   	 });
			 	    	
			 	    	
			 	    	
			 	    }
			
		   		});
			 	 
			 	
			 });
			 
		}
		
		//急救处理
		function infoload(info,row){
			   $(".fancybox").fancybox({openEffect:"none",closeEffect:"none"}); 
			   var div = info.load("${WEB_PATH}/order/myorder/view/specialorderinfo.do?robConfirmId="+row.robConfirmId,function(){
			 	
			 	$('#log_info_form').validate({
			 		rules:{
			 			remark: {
					      required: true
					    }
			 		},
			 		 messages:{
			 		 	remark: {
		                	required:e+"请输入处理意见！"
		            	}
			 		 }
			 	});
			 	
			 	$("#confirmSaveBtn").on('click',function(){
			 		var $valid = $("#log_info_form").valid();
			 	    if(!$valid){
			 	    	return false;
			 	    }
			 	    
			 	    var loginfo = $('#log_info_form').serializeJson();
			 	    $('#confirmSaveBtn').attr("disabled",true);
			 	     $.yilinAjax({
				   	  	type:'POST',
				   	  	loadmsg:'保存中......',
				   	  	url:'${WEB_PATH }/order/myorder/savespecialorderinfo.do?type=1',
				   	  	data:loginfo,
				   	  	btn:null,
		        		errorcallback:null,
		        		successcallback:function(result){
		        			$('#confirmSaveBtn').attr("disabled",false);
		        			if(result.success == true){
		        				$("tr[data-uniqueid='"+row.robOrderId+"'] td").last().html('<td style=""><span class="label label-success">处理完成</span></td>');
								infoload(info,row);
								layer.closeAll();
							}else{
								layer.alert('<font color="red">'+result.msg+'</font>', {
								  skin: 'layui-layer-molv' ,
								  closeBtn: 0,
								  icon: 5
								});
							}
		        		}
				   	 });
			 	    
			 	    
			 	});
			 	
			 	$("#saveBtn").on('click',function(){
			 	    var $valid = $("#log_info_form").valid();
			 	    if(!$valid){
			 	    	return false;
			 	    }
			 	    var loginfo = $('#log_info_form').serializeJson();
			 	    
			 	    $('#saveBtn').attr("disabled",true);
			 	     
			 	     $.yilinAjax({
				   	  	type:'POST',
				   	  	loadmsg:'保存中......',
				   	  	url:'${WEB_PATH }/order/myorder/savespecialorderinfo.do',
				   	  	data:loginfo,
				   	  	btn:null,
		        		errorcallback:null,
		        		successcallback:function(result){
		        			$('#saveBtn').attr("disabled",false);
		        		$("tr[data-uniqueid='"+row.robOrderId+"'] td").last().html('<td style=""><span class="label label-warning">正在处理</span></td>');
		        			if(result.success == true){
		        					layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
									  	skin: 'layui-layer-molv',
									  	icon: 1,
									  	closeBtn: 0
									},function(){
										infoload(info,row);
										layer.closeAll();
									});
							}else{
								layer.alert('<font color="red">'+result.msg+'</font>', {
								  skin: 'layui-layer-molv' ,
								  closeBtn: 0,
								  icon: 5
								});
							}
		        		}
				   	 });
			 	    
		   		});
			 });
		}
		
		
		function subTableQueryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset,
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
		
        function detailFormatter(index, row) {
        	//加载层-风格4
        	var html = ""; 
        	$.ajax({
	            url:"${WEB_PATH}/order/roborder/getRobOrderInfo.do?id="+row.id,
	            type:'get',
	            dataType:'html',
	            cache:false, 
 				async:false, 
	            success:function(data){
	                 html = data
	            }
	        });
		    return html;
		}
		
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatAreaName(index, row, element){
		 	var tt =  '<span class="label label-primary">'+row.deliveryAreaName+'</span>&nbsp;&nbsp;------->&nbsp;&nbsp;<span class="label label-danger">'+row.consigneeAreaName+'</span>';
    		return tt; 
		}
		
		function formatFiniteTime(index, row, element){
			var tt = "";
			if(row.longTime){
				tt = '<span class="label label-danger"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;长期有效</span>'
			}else{
			    tt=new Date(row.finiteTime).toLocaleString(); 
			}
    		return tt; 
		}
		
		
	 	function formatGoosUnitPrice(index, row, element){
	 		return '¥&nbsp;'+row.goodsBasic.unitPrice;
	 	}
	 	function formatUnitPrice(val){
		 	return '<font class="text-danger">¥&nbsp;'+val+'</font>';
		}
		function formatGoodsTotalPrice(index, row, element){
	 		return '¥&nbsp;'+row.goodsBasic.totalPrice;
	 	}
		function formatTotalPrice(val){
	 		return '<font class="text-danger">¥&nbsp;'+val+'</font>';
	 	}
	 	function formatDeposit(value,row,index){
		   var deposit = row.weight*value;
	 	   return '<font class="text-danger">¥&nbsp;'+deposit.toFixed(2)+'</font>';
	 	}
	 	function formatTotalWeight(index, row, element){
	 		return row.goodsBasic.totalWeight+"&nbsp;(吨)";
	 	}
	 	function formatWeight(val){
	 		return '<font class="text-danger">'+val+'&nbsp;(吨)</font>';
	 	}
	 	function formatGoodsType(index, row, element){
    		return row.goodType; 
		}
		function formatStatus(val){
		    if(val == '0'){
				return '<span class="label label-default">待装货</span>';
			}else if(val == "1"){
				return '<span class="label label-primary">确认装货</span>';
			}else if(val == "2"){
				return '<span class="label label-primary">运输中</span>';
			}else if(val == "3"){
				return '<span class="label label-warning">送达</span>';
			}else if(val == "4"){
				return '<span class="label label-success">回执发还中</span>';
			}else if(val == "5"){
				return '<span class="label label-danger">送还回执中</span>';
			}else if(val == "6"){
				return '<span class="label label-primary">订单完结</span>';
			}else if(val == "7"){
				return '<span class="label la label-danger">已销单</span>';
			}
		}
    </script>
    
        <div id="body" class="hidden">
	      <div class="col-sm-12">
	         <div class="panel panel-primary">
	             <div class="panel-body">
	             	<div class="ystep1" style="text-align:center;"></div>                         
	             </div>
	            </div>
	       </div>

        <div class="col-sm-12">
     		<table></table>
        </div>
    </div>
</body>

</html>