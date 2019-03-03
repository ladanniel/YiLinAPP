<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>抢单日志</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <style>
    	.columns.columns-right.btn-group.pull-right{
    		display: none;
    	}
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
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>抢单日志</h5>
                <div class="ibox-tools"> 
                    
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div style="" class="demo ui-sortable">
                            		 <div class="row" style="margin-bottom: 10px;"> 
                            		 			 <div class="col-sm-3">
					                                <div class="form-group">
					                                    <label>抢单单号：</label>
					                                    <input id="orderNo" name="orderNo" class="form-control"  type="text" value="">
					                                </div>
					                            </div> 
					                            <div class="col-sm-4">
					                                <div class="form-group">
					                                    <label>发货单号：</label>
					                                    <input id="goodsNo" name="goodsNo" class="form-control"  type="text" value="">
					                                </div>
					                            </div> 
					                            <#if query == "1">
			                            		<div class="col-sm-4">
					                               	<div class="input-group">
						                                <label>抢单商户：</label>
								                        <input type="text" class="form-control" id="company" name="company">
								                        <div class="input-group-btn">
								                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
								                                <span class="caret"></span>
								                            </button>
								                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
								                            </ul>
								                        </div>
								                        <!-- /btn-group -->
							                    	</div>
					                            </div>
					                            </#if>
					                            <div class="col-sm-1" style="padding-top: 22px;">
					                                 <button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
					                            </div> 
			                        </div>
			                    </div>
                                <table id="exampleTableEvents" data-height="720" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="robOrderNo" >抢单单号</th>
		                                    <th data-field="goodsBasic.deliveryNo" >发货单号</th>
		                                    <th data-field="companyName" >抢单商户名称</th>
		                                    <th data-field="account.name" >抢单人姓名</th>
		                                    <th data-field="account.phone" >抢单人电话</th>
		                                    <th data-field="goodsBasic.goodsType.name" >货物类型</th>
		                                    <th data-field="unitPrice" data-formatter="formatMoney">竞价单价</th>
											<th data-field="weight" data-formatter="formatTotalWeight">竞载重量</th>
		                                    <th data-field="create_time" data-formatter="formatTime">抢单时间</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                        <!-- End Example Events -->
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
    <script >
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        search: $("input.form-control.input-outline").val(),
		        goodsNo:$("#goodsNo").val(),
		        orderNo:$("#orderNo").val(),
		        companyName:$("#company").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
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
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/order/orderlog/getLogPage.do",
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
	        $("#search").click(function(){
				$("#exampleTableEvents").bootstrapTable('refresh');
			});
	        $('input.form-control.input-outline').hide();
        });
        
        function detailFormatter(index, row) {
        	var html = ""; 
        	$.ajax({
	            url:"${WEB_PATH}/global/log/orderlog.do?orderId="+row.id,
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
		
		function formatAreaName(index, row, element){
		 	var tt =  '<span class="label label-primary">'+row.deliveryAreaName+'</span><i class="fa fa-arrow-right"></i><span class="label label-danger">'+row.consigneeAreaName+'</span>';
    		return tt; 
		}
        
        function formatStatus(val){
			if(val == 'save'){
				return '<span class="label label-info">保存</span>';
			}else if(val == 'apply'){
				return '<span class="label label-default">申请发货</span>';
			}else if(val == "lock"){
				return '<span class="label label-primary">锁定</span>';
			}else if(val == "back"){
				return '<span class="label label-warning">退回</span>';
			}else if(val == "success"){
				return '<span class="label label-success">通过</span>';
			}else if(val == "scrap"){
				return '<span class="label label-danger">销毁</span>';
			}
		}
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatGoodsType(index, row, element){
    		return row.goodsType.name; 
		}
		
		function formatMoney(val){
	 		return '<font class="text-danger">¥&nbsp;'+val+'</font>';
	 	}
	 	function formatTotalWeight(val){
	 		return val+"&nbsp;(吨)";
	 	}
    </script>
</body>

</html>