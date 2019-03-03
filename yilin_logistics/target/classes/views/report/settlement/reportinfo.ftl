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
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>我的订单</h5>
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
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                     <label>货物名称：</label>
				                                 <input id="goodsName" name="goodsName" class="form-control"  type="text" value="">
			                                </div>
			                            </div>
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                     <label>车牌号：</label>
				                                 <input id="trackNo" name="trackNo" class="form-control"  type="text" value="">
			                                </div>
			                            </div>
			                            <div class="col-sm-1" style="padding-top: 22px;">
			                                 <button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
			                            </div> 
			                        </div>
			                        <div class="row">
				                        <div class="col-sm-3"> 
				                                <div class="form-group">
				                                    <label>状态：</label>
				                                    <select data-placeholder="选择运单状态" id="status" class="chosen-select form-control" multiple   >
				                                   		<option value="0" hassubinfo="true" >待装货</option>
					                                    <option value="2" hassubinfo="true" >运输中</option>
					                                    <option value="3" hassubinfo="true" >送达</option>
					                                    <option value="4" hassubinfo="true" >回执发还中</option>
					                                    <option value="5" hassubinfo="true" >送还回执中</option>
					                                    <option value="6" hassubinfo="true">订单完结</option>
					                                </select>
				                                </div>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="btn-group" id="exampleTableEventsToolbar" role="group">
                                    <a href="javascript:void(0);" class="btn btn-outline btn-default" data-toggle="modal" id="export-but" >
                                        <i class="glyphicon glyphicon-export" aria-hidden="true"></i>&nbsp;&nbsp;导出Excel
                                    </a>
                                </div>
                                <table id="exampleTableEvents"  data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
		                                    <th data-field="id"  data-visible="false">id</th>
		                                    <th data-field="endDate" data-formatter="formatTime">收货时间</th>
		                                    <th data-field="goodsName">货物名称</th>
		                                    <th data-field="specification">规格(长*高*直径)</th>
								            <th data-field="weightUnit" data-formatter="formatWeightUnit">计量单位</th>
								            <th data-field="totalWeight" data-formatter="formatWeight">发货数量</th>
								            <th data-field="actualWeight">结算数量</th>
								            <th data-field="unitPrice" data-formatter="formatUnitPrice">汽运单价</th>
								            <th data-field="totalPrice" data-formatter="formatTotalPrice">汽运总价</th>
								            <th data-field="deliveryAreaName" data-formatter="formatAreaName">起运点</th>
								            <th data-field="trackNo">车牌号</th>
								            <th data-field="userName">驾驶员</th>
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
    <script >
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        deliveryAreaName:$("#deliveryAreaName").val(),
		        goodsName:$("#goodsName").val(),
		        trackNo:$("#trackNo").val(),
		        statusIDs: $("#status").val() != null ? $("#status").val().join(","):$("#status").val(),
		        search: $("input.form-control.input-outline").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
		        deliveryNo:$("#deliveryNo").val()
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
    		layer.config({
				extend: 'extend/layer.ext.js'
			});  
    		$('#deliveryAreaName').citypicker({areaId:'deliveryAreaId'}); 
    		$("#status").chosen({width:'100%'}); 
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
			});
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/report/forms/getFormsInfo.do",
	            method: 'get',
	            search:false,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
	            showRefresh:true, 
	            showColumns:true,
	            detailView:false,
	            onClickRow:onClickContacts,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
	            sidePagination: "server", //设置为服务器端分页
	            queryParams: queryParams,//参数
	            minimumCountColumns: 1//,onExpandRow:subTableFormatter
	        }); 
	         //点击查询搜索
			$("#search").click(function(){
				$("#exampleTableEvents").bootstrapTable('refresh');
			});
	        $('input.form-control.input-outline').attr("placeholder","请输入收货地址");
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
		
		function formatAreaName(index, row, element){
		 	var tt =  '<span class="label label-primary">'+row.deliveryAreaName+'</span>';
    		return tt; 
		}
		
		function formatWeightUnit(index, row, element){
		 	var tt =  '<span class="label label-primary">吨</span>';
    		return tt; 
		}
		
		function formatTime(val){
			if(val == "" || val == null) return;
			var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
	 	function formatUnitPrice(val){
		 	return '<font class="text-danger">¥&nbsp;'+val+'</font>';
		}
		
		function formatTotalPrice(val){
	 		return '<font class="text-danger">¥&nbsp;'+val+'</font>';
	 	}
	 	
	 	function formatWeight(val){
	 		return '<font class="text-danger">'+val+'</font>';
	 	}
	 	
	 	//导出Excel
        $("#export-but").click(function(){
        	var deliveryAreaName = $("#deliveryAreaName").val();
		    var goodsName = $("#goodsName").val();
		    var trackNo = $("#trackNo").val();
		    var statusIDs = $("#status").val() != null ? $("#status").val().join(","):$("#status").val();
		    var deliveryNo = $("#deliveryNo").val();
		    if(!statusIDs){
		    	statusIDs = "0,2,3,4,5,6";
		    }
        	location.href = "${WEB_PATH}/report/forms/export.do?deliveryAreaName="+deliveryAreaName+"&goodsName="+goodsName+"&trackNo="+trackNo+"&statusIDs="+statusIDs+"&deliveryNo="+deliveryNo;
        });
    </script>
</body>

    <div id="body" class="hidden">
   		 <div id="info"></div>
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

</html>