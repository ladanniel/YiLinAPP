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
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>我的抢单列表</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <div class="example-wrap"> 
                        		<div style="" class="demo ui-sortable">
                            		 <div class="row" style="margin-bottom: 10px;"> 
			                            <div class="col-sm-4">
			                                <div class="form-group">
			                                    <label>发货地区：</label>
			                                    <input id="deliveryAreaName" name="deliveryAreaName" class="form-control" readonly type="text" value="">
												<input id="deliveryAreaId" name="deliveryAreaId" type="hidden" >
			                                </div>
			                            </div> 
			                            <div class="col-sm-4">
			                                <div class="form-group">
			                                    <label>收货货地区：</label>
			                                    <input id="consigneeAreaName" name="consigneeAreaName" class="form-control" readonly type="text" value="">
												<input id="consigneeAreaId" name="consigneeAreaId" type="hidden" >
			                                </div>
			                            </div> 
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                     <label><span style="color: red;">*&nbsp;&nbsp;</span>货物类型：</label>
				                                 <select data-placeholder="选择货物类型" id="goodsType_id" name="goodsType_id" class="chosen-select form-control" multiple>
				                                	<#list goodsTypeList as goodsType >
				                                		<option value="${goodsType.id}" hassubinfo="true">${goodsType.name}</option>
				                                 	</#list>
				                                 </select>
			                                </div>
			                            </div> 
			                            <div class="col-sm-1" style="padding-top: 22px;">
			                                 <button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
			                            </div> 
			                        </div>
			                        <div class="row">
			                             <div class="col-sm-5"> 
			                                <div class="form-group">
			                                    <label>状态：</label>
			                                    <select data-placeholder="选择认证状态" id="status" class="chosen-select form-control" multiple   >
			                                   		<option value="apply" hassubinfo="true" selected = "selected">等待确认</option>
				                                    <option value="dealing" hassubinfo="true" selected = "selected">处理中</option>
				                                    <option value="withdraw" hassubinfo="true" selected = "selected">撤回</option>
				                                    <option value="back" hassubinfo="true" selected = "selected">退回</option>
				                                    <option value="success" hassubinfo="true" selected = "selected">通过</option>
				                                    <option value="scrap" hassubinfo="true" >销单</option>
				                                </select>
			                                </div>
			                            </div>
			                        </div>
			                    </div>
	                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
	                            	<button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="confirm-but" >
				                        <i class="fa fa-check" aria-hidden="true"></i>&nbsp;&nbsp;操作
				                    </button> 
	                            	<button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="edit-but" >
				                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;编辑
				                    </button> 
	                            	<button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="translate-but" >
				                        <i class="fa fa-mail-reply" aria-hidden="true"></i>&nbsp;&nbsp;撤回
				                    </button>
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="rob-cancel" >
                                        <i class="fa fa-remove" aria-hidden="true"></i>&nbsp;&nbsp;取消抢单
				                    </button>
                                </div>
                                <table id="exampleTableEvents"  data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true" data-click-to-select="true"></th>
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="robOrderNo">单号</th>
								            <th data-field="deliveryAreaName" data-formatter="formatAreaName">路线(出发地/目的地)</th>
								            <th data-field="goodsType" data-formatter="formatGoodsType">货物类型</th>
								            <th data-field="goosUnitPrice" data-formatter="formatGoosUnitPrice">原单价</th>
								            <th data-field="unitPrice" data-formatter="formatUnitPrice">抢单单价</th>
								            <th data-field="totalWeight" data-formatter="formatTotalWeight">原重量/吨</th>
								            <th data-field="weight" data-formatter="formatWeight">抢单重量/吨</th>
								            <th data-field="goodstotalPrice" data-formatter="formatGoodsTotalPrice">原总价（总重量*单价）</th>
								            <th data-field="totalPrice" data-formatter="formatTotalPrice">抢单总价（总重量*单价）</th>
								            <th data-field="status" data-formatter="formatStatus">状态</th>
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
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=1AC8RPrl58FNLoYjoEhOiwv8SsgToWRm"></script>
    <script >
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        deliveryAreaName:$("#deliveryAreaName").val(),
		        consigneeAreaName:$("#consigneeAreaName").val(),
		        goodsTypeId:$("#goodsType_id").val() != null ? $("#goodsType_id").val().join(","):$("#goodsType_id").val(),
		        status_serch: $("#status").val() != null ? $("#status").val().join(","):$("#status").val(),
		        search: $("input.form-control.input-outline").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
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
    		$("#status").chosen({width:'100%'}); 
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
			});
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/order/roborder/getMyRobOrderPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
	            showRefresh:true, 
	            showColumns:true,
	            detailView:true,
	            detailFormatter:detailFormatter, 
	            onCheck:onClickContacts,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
	            sidePagination: "server", //设置为服务器端分页
	            queryParams: queryParams,//参数
	            minimumCountColumns: 1, 
	            clickToSelect: true, 
	        }); 
	         //点击查询搜索
			$("#search").click(function(){
				$("#exampleTableEvents").bootstrapTable('refresh');
			});
	        $('input.form-control.input-outline').attr("placeholder","请输入单号/收获地址");
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
	       	}else if(info.status != "success"){
	       		layer.msg('<font color="red">该订单【'+info.robOrderNo+'】,商户还没有确认，不能确认该订单！</font>', {icon: 5}); 
			    return ;
	       	}
		    layer.open({
		      type: 2,
		      title: '<span style="color: #ed5565"><i class="fa fa-pencil"></i>&nbsp;&nbsp;抢单信息确认</span>',
		      shadeClose: true,
		      shade: 0.3,
		      maxmin: false, //开启最大化最小化按钮
		      area: ['95%', '95%'],
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
	       	layer.msg('加载中......', {icon: 16,shade: 0.3,time:5000});
	       	$.ajax({
				  type: 'POST',
				  url:'${WEB_PATH }/order/roborder/checkeditroborder.do',  
				  data: {"id":info.id},
				  dataType: "json",
				  success:function(result){
				  	  layer.closeAll();
					  if(result.success){
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
									  		$('#edit-but').attr("disabled",false);
									    	layer.closeAll();
									  } 
									});
									return false;
							  } 
						    });
						    $('#edit-but').attr("disabled",true); 
					  }else{
					  		layer.alert('<font color="red">'+result.msg+'</font>', {icon: 5});
						    var ids = [info.id];
				            $("#exampleTableEvents").bootstrapTable('remove', {
				                field: 'id',
				                values: ids
				            });
					  }
				  },
			});
		    
        });
        
        function onClickContacts( row, element){
			if(row.status == 'apply'){
				$("#edit-but").attr("disabled",true); 
		 	 	$("#translate-but").attr("disabled",false); 
		 	 	$("#rob-cancel").attr("disabled",false); 
		 	 	$("#confirm-but").attr("disabled",true); 
			}else if(row.status == "withdraw"){
				$("#edit-but").attr("disabled",false); 
		 	 	$("#translate-but").attr("disabled",true); 
		 	 	$("#rob-cancel").attr("disabled",false); 
		 	 	$("#confirm-but").attr("disabled",true); 
			}else if(row.status == "back"){
				var row_id = "#"+row.id;
				layer.tips("退货原因："+row.remark, row_id,{tips: [1, '#f8ac59']});
				$("#edit-but").attr("disabled",false); 
		 	 	$("#translate-but").attr("disabled",true); 
		 	 	$("#rob-cancel").attr("disabled",false); 
		 	 	$("#confirm-but").attr("disabled",true); 
			}else if(row.status == "success"){
				$("#edit-but").attr("disabled",true); 
		 	 	$("#translate-but").attr("disabled",true); 
		 	 	$("#rob-cancel").attr("disabled",false); 
		 	 	$("#confirm-but").attr("disabled",false); 
			}else if(row.status == "dealing"){
				$("#edit-but").attr("disabled",true); 
		 	 	$("#translate-but").attr("disabled",true); 
		 	 	$("#rob-cancel").attr("disabled",true); 
		 	 	$("#confirm-but").attr("disabled",true); 
			}else if(row.status == "scrap"){
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
		
		
		function openorderdetail(id) {
        	layer.open({
		      type: 2,
		      title: '<span style="color: #ed5565"><i class="fa fa-pencil"></i>&nbsp;&nbsp;订单信息</span>',
		      shadeClose: true,
		      shade: 0.1,
		      maxmin: false, //开启最大化最小化按钮
		      area: ['80%', '80%'],
		      content: '${WEB_PATH}/order/myorder/orderdetailsinfo.do?id='+id,
		      btn: ['<i class="fa fa-check"></i>&nbsp;确认'],
		      yes: function(index, layero){
		      	   layer.close(index); 
			       $("#exampleTableEvents").bootstrapTable('refresh');
			  },
			  cancel: function(index){  
				   layer.close(index); 
			       $("#exampleTableEvents").bootstrapTable('refresh');
			  } 
		    });
		}
		
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatAreaName(index, row, element){
		 	var tt =  '<span class="label label-primary">'+row.goodsBasic.deliveryAreaName+'</span>&nbsp;&nbsp;------->&nbsp;&nbsp;<span class="label label-danger">'+row.goodsBasic.consigneeAreaName+'</span>';
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
	 	function formatTotalWeight(index, row, element){
	 		return row.goodsBasic.totalWeight+"&nbsp;(吨)";
	 	}
	 	function formatWeight(val){
	 		return '<font class="text-danger">'+val+'&nbsp;(吨)</font>';
	 	}
	 	function formatGoodsType(index, row, element){
    		return row.goodsBasic.goodsType.name; 
		}
		function formatStatus(index, row, element){
			if(row.status == 'apply'){
				return '<span class="label label-default">等待确认</span>';
			}else if(row.status == "dealing"){
				return '<span class="label label-primary">正在处理</span>';
			}else if(row.status == "back"){
				return '<span class="label label-warning" id="'+row.id+'">退回</span>';
			}else if(row.status == "success"){
				return '<span class="label label-success">通过</span>';
			}else if(row.status == "scrap"){
				return '<span class="label label-danger">销单</span>';
			}else if(row.status == "withdraw"){
				return '<span class="label label-info">撤回</span>';
			}
		}
    </script>
</body>

</html>