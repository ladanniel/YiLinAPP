<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>抢单审查</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <style>
    	.order:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "抢单信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
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
		    margin-top: 10px;
		}
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>发货管理</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <div class="example-wrap"> 
                                <table id="exampleTableEvents" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="deliveryNo" >发货单号</th>
								            <th data-field="deliveryAreaName" data-formatter="formatAreaName">路线(出发地/目的地)</th>
								            <th data-field="status" data-formatter="formatStatus" >状态</th>
								            <th data-field="goodsType" data-formatter="formatGoodsType">货物类型</th>
								            <th data-field="unitPrice" data-formatter="formatMoney">单价</th>
								            <th data-field="totalPrice" data-formatter="formatMoney">总价(单价*总重量)</th>
								            <th data-field="totalWeight" data-formatter="formatTotalWeight">总重量/吨</th>
								            <th data-field="releaseTime" data-formatter="formatTime" >发布时间</th>
								            <th data-field="finiteTime"  data-formatter="formatFiniteTime" >截至时间</th>   
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
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
			});
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/order/ordermanage/getGoodsPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
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
	            url:"${WEB_PATH}/order/ordermanage/view/orderList.do?id="+row.id,
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
		
		function formatStatus(val){
			if(val == 'save'){
				return '<span class="label label-info">保存</span>';
			}else if(val == 'apply'){
				return '<span class="label label-default">申请发货</span>';
			}else if(val == "lock"){
				return '<span class="label label-primary">正在处理</span>';
			}else if(val == "back"){
				return '<span class="label label-warning">退回</span>';
			}else if(val == "success"){
				return '<span class="label label-success">通过</span>';
			}else if(val == "scrap"){
				return '<span class="label label-danger">销毁</span>';
			}
		}
		
	 	function formatMoney(val){
	 		return '<font class="text-danger">¥&nbsp;'+val+'</font>';
	 	}
	 	function formatTotalWeight(val){
	 		return val+"&nbsp;(吨)";
	 	}
	 	function formatGoodsType(index, row, element){
    		return row.goodsType.name; 
		}
		
		function formatAreaName(index, row, element){
		 	var tt =  '<span class="label label-primary">'+row.deliveryAreaName+'</span><i class="fa fa-arrow-right"></i><span class="label label-danger">'+row.consigneeAreaName+'</span>';
    		return tt; 
		}
    </script>
</body>

</html>