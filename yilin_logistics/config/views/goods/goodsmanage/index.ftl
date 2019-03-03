<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>发货审核</title> 
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
		.input-group-btn:last-child>.btn, .input-group-btn:last-child>.btn-group {
		    z-index: 2;
		    margin-left: -1px;
		    margin-top: 23px;
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
    <div class="wrapper wrapper-content animated fadeInRight">

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>发货审核</h5>
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
			                                     <label>发货信息筛选：</label>
				                                 <select data-placeholder="查询全部" id="lock" name="lock" class="form-control">
				                                	<option value="" hassubinfo="true">查询全部</option>
				                                	<option value="mylock" hassubinfo="true">我的锁定</option>
				                                	<#if roleName == "管理员">
								                    	<option value="lock" hassubinfo="true">全部锁定</option>
								                    </#if>
				                                 </select>
			                                </div>
			                            </div>
			                         </div>
			                    </div>
	                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
				                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="verify-but" >
				                        <i class="glyphicon glyphicon-user" aria-hidden="true"></i>&nbsp;&nbsp;发货审核
				                    </button>
				                   
				                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="close-lock-but" >
				                        <i class="fa fa-mail-reply" aria-hidden="true"></i>&nbsp;&nbsp;撤销锁定
				                    </button>
                                </div>
                                <table id="exampleTableEvents" data-height="720" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true"></th>  
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="deliveryNo" >发货单号</th>
		                                    <th data-field="deliveryAreaName" data-formatter="formatAreaName">路线(出发地/目的地)</th>
		                                    <th data-field="companyName">发布信息商户</th>
		                                    <th data-field="account.name">发布信息用户</th>
								            <th data-field="unitPrice" data-formatter="formatMoney">单价</th>
								            <th data-field="totalPrice" data-formatter="formatMoney">总价</th>
								            <th data-field="totalWeight" data-formatter="formatTotalWeight">总重量/吨</th>
								            <th data-field="status" data-formatter="formatStatus">状态</th>
								            <th data-field="hasLock" data-formatter="formatLock">是否锁定</th>
								            <th data-field="releaseTime" data-formatter="formatTime" >发布时间</th>
								            <th data-field="finiteTime"  data-formatter="formatTime" >有效期</th>   
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
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=1AC8RPrl58FNLoYjoEhOiwv8SsgToWRm"></script>
    <script >
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        search: $("input.form-control.input-outline").val(),
		        mylock:$("#lock option:selected").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
    		$("#close-lock-but").attr("disabled",true);
    		$("#query-all-but").attr("disabled",true);
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/goods/goodsmanage/getPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:15,   
	            showRefresh:true, 
	            showColumns:true,
	            detailView:true,
	            detailFormatter:detailFormatter,
	            onClickRow:onClickContacts,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
	            sidePagination: "server", //设置为服务器端分页
	            queryParams: queryParams,//参数
	            minimumCountColumns: 1, 
	            search: true,    //隐藏搜索框
	            clickToSelect: true,  
	        });
	        $('input.form-control.input-outline').attr("placeholder","输入关键字搜索");
        });
        
        function detailFormatter(index, row) {
        	var html = ""; 
        	$.ajax({
	            url:"${WEB_PATH}/global/goodsdata/goodsviews.do?id="+row.id,
	            type:'get',
	            dataType:'html',
	            cache:false, 
 				async:false, 
	            success:function(data){
	                 html =    data
	            }
	        });
		    return html;
		}
		
		function onClickContacts(index, row, element){
			if(index.status == 'apply'){
				$("#close-lock-but").attr("disabled",true); 
				$("#verify-but").attr("disabled",false); 
			}else if(index.status == "lock"){
				$("#close-lock-but").attr("disabled",false); 
				$("#verify-but").attr("disabled",false); 
			}else if(index.status == "back"){
				$("#close-lock-but").attr("disabled",true); 
				$("#verify-but").attr("disabled",true); 
			}else if(index.status == "success"){
				$("#close-lock-but").attr("disabled",true); 
				$("#verify-but").attr("disabled",true); 
			} 
		}
        
        //审核信息
        $('#verify-but').click(function(){
        	$('#verify-but').attr("disabled",true);
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
	        	$('#verify-but').attr("disabled",false);
			    layer.msg('<font color="red">温馨提示：请选择锁定后或正在申请的数据进行操作。</font>', {icon: 5}); 
			    return ;
	       	}
	       	if(info.status != "apply" && info.status != "lock"){
	       			$('#verify-but').attr("disabled",false);
	        		layer.msg('<font color="red">温馨提示：请选择锁定后或正在申请的数据进行操作。</font>', {icon: 5}); 
			    	return ;
	        }
	        $('#verify-but').attr("disabled",false);
	        layer.open({
		      type: 2,
		      title: '<span style="color: #ed5565">审核发货信息</span>',
		      shadeClose: true,
		      shade: false,
		      maxmin: false, //开启最大化最小化按钮
		      area: ['95%', '95%'],
		      content: '${WEB_PATH }/goods/goodsmanage/view/verify.do?id='+info.id,
		      btn: ['确定','取消'],
			  yes: function(index, layero){
			      var body = layer.getChildFrame('body', index);
			      var iframeWin = window[layero.find('iframe')[0]['name']];
			      iframeWin.saveInfo();
			  },
			  cancel: function(){
			  	  $("#exampleTableEvents").bootstrapTable('refresh');
		  	  	  $('#verify-but').attr("disabled",false); 
				  layer.closeAll();
			  }
		    });
        });
        
        $("#close-lock-but").click(function(){
        	$('#close-lock-but').attr("disabled",true);
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length || !info.hasLock){
	        	$('#close-lock-but').attr("disabled",false);
			    layer.msg('<font color="red">温馨提示：请选择一条锁定后的信息撤销。</font>', {icon: 5}); 
			    return ;
	       	}
	       	$.ajax({
					  type: 'POST',
					  url:'${WEB_PATH }/goods/goodsmanage/closeLock.do',  
					  data: {"id":info.id},
					  dataType: "json",
					  success:function(result){  
					  		swal("", result.msg, "success");
					  		$("#exampleTableEvents").bootstrapTable('refresh');
						},
						error:function(data){
						 	$('#sclose-lock-but').attr("disabled",false); 
						 	var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						 }
			});
        });
        
        $("#my-lock-but").click(function(){
        	$("#lock").val("mylock");
        	$("#my-lock-but").attr("disabled",true);
        	$("#my-locked-but").attr("disabled",false)
        	$("#query-all-but").attr("disabled",false);
        	$("#exampleTableEvents").bootstrapTable('refresh');
        });
        
        $("#query-all-but").click(function(){
        	$("#lock").val("");
        	$("#my-lock-but").attr("disabled",false);
        	$("#query-all-but").attr("disabled",true);
        	$("#my-locked-but").attr("disabled",false);
        	$("#exampleTableEvents").bootstrapTable('refresh');
        });  
        
        $("#my-locked-but").click(function(){
        	$("#lock").val("lock");
        	$("#my-lock-but").attr("disabled",false);
        	$("#query-all-but").attr("disabled",false);
        	$("#my-locked-but").attr("disabled",true);
        	$("#exampleTableEvents").bootstrapTable('refresh');
        });
        
		function formatTime(val){
			if(null == val || "" == val){
				return '<span class="label label-success">永久</span>';
			}
    		return new Date(val).toLocaleString(); 
		}
		
		function formatAreaName(index, row, element){
		 	var tt =  '<span class="label label-primary">'+row.deliveryAreaName+'</span><i class="fa fa-arrow-right"></i><span class="label label-danger">'+row.consigneeAreaName+'</span>';
    		return tt; 
		}
		
		function formatMoney(val){
	 		return '<font class="text-danger">¥&nbsp;'+val+'</font>';
	 	}
	 	function formatTotalWeight(val){
	 		return val+"&nbsp;(吨)";
	 	}
		
		function formatLock(index, row, element){
			if(row.auditPersonId == "${userId}" && row.hasLock){
				return '<span class="label label-success">是(我)</span>';
			}else if(row.auditPersonId != "${userId}" && row.hasLock){
				return '<span class="label label-primary">是('+row.auditPerson+')</span>';
			}else{
				return '<span class="label label-warning">未锁定</span>';
			}
		}
		
		 //选择刷新
		$("#lock").change(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
		
		function formatStatus(val){
			if(val == 'save'){
				return '<span class="label label-info">保存</span>';
			}else if(val == 'apply'){
				return '<span class="label label-default">申请发货</span>';
			}else if(val == "lock"){
				return '<span class="label label-primary">处理中</span>';
			}else if(val == "back"){
				return '<span class="label label-warning">退回</span>';
			}else if(val == "success"){
				return '<span class="label label-success">通过</span>';
			}else if(val == "scrap"){
				return '<span class="label label-danger">销毁</span>';
			}
		}
    </script>
</body>

</html>