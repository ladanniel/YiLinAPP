<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>发货记录</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <style>
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
                <h5>发货记录</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <div class="example-wrap"> 
	                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
				                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="my-verify-but" >
				                        <i class="glyphicon glyphicon-user" aria-hidden="true"></i>&nbsp;&nbsp;我的审核
				                        <input type="hidden" id="mylock" />
				                    </button>
				                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="query-all-but" >
				                        <i class="fa fa-table" aria-hidden="true"></i>&nbsp;&nbsp;查看全部
				                        <input type="hidden" id="lock" />
				                    </button>
                                </div>
                                <table id="exampleTableEvents" data-height="720" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="deliveryNo" >发货单号</th>
		                                    <th data-field="deliveryAreaName" data-formatter="formatAreaName">路线(出发地/目的地)</th>
		                                    <th data-field="companyName">发布信息商户</th>
		                                    <th data-field="account.name">发布信息用户</th>
								            <th data-field="unitPrice" data-formatter="formatMoney">单价</th>
								            <th data-field="totalPrice" data-formatter="formatMoney">总价</th>
								            <th data-field="totalWeight" data-formatter="formatTotalWeight">总重量/吨</th>
								            <th data-field="status" data-formatter="formatStatus">状态</th>
								            <th data-field="auditPerson">审核人</th>
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
		        mylock:$("#mylock").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
    		$("#query-all-but").attr("disabled",true);
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/goods/goodsmanage/getRecordPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:15,   
	            showRefresh:true, 
	            showColumns:true,
	            detailView:true,
	            detailFormatter:detailFormatter,
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
		
		$("#query-all-but").click(function(){
			$("#mylock").val("");
        	$("#my-verify-but").attr("disabled",false);
        	$("#query-all-but").attr("disabled",true);
        	$("#exampleTableEvents").bootstrapTable('refresh');
        });  
        
        $("#my-verify-but").click(function(){
        	$("#mylock").val("mylock");
        	$("#my-verify-but").attr("disabled",true);
        	$("#query-all-but").attr("disabled",false);
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