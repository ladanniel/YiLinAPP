<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>抢单审阅</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <style>
    	.info:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "详细信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.info {
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
                <h5>抢单审阅</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <div class="example-wrap"> 
	                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
				                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="verify-but" >
				                        <i class="glyphicon glyphicon-user" aria-hidden="true"></i>&nbsp;&nbsp;抢单处理
				                    </button>
                                </div>
                                <table id="exampleTableEvents" data-height="700" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true"></th>  
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="goodsBasic.deliveryNo" >发货单号</th>
								            <th data-field="account.name">抢单人姓名</th>
								            <th data-field="account.phone">抢单人电话</th>
								            <th data-field="unitPrice" data-formatter="formatMoney">竞价单价</th>
								            <th data-field="weight" data-formatter="formatTotalWeight">重量</th>
								            <th data-field="totalPrice" data-formatter="formatMoney">总价</th>
								            <th data-field="goodsTypes">货物类型</th>
								            <th data-field="status" data-formatter="formatStatus">状态</th>
								            <th data-field="create_time" data-formatter="formatTime">抢单时间</th>
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
	            url: "${WEB_PATH }/order/ordermanage/getPage.do",
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
        	var auditPerson = row.auditPerson == null ? "<font color='red'>无</font>" : row.auditPerson;
        	var auditTime   = row.auditTime == null ? "<font color='red'>无</font>" : row.auditTime;
        	console.log(auditPerson);
        	var html = '<div style="" class="info ui-sortable"><div class="row"> <div class="col-sm-4"><p>发货单价：¥ '+row.goodsBasic.unitPrice+'</p><p>发货总价：¥ '+row.goodsBasic.totalPrice+'</p></div><div class="col-sm-4"><p>发货总重：'+row.goodsBasic.totalWeight+' (吨)</p></div><div class="col-sm-4"><p>审核人：'+auditPerson+'</p><p>审核时间：'+auditTime+'</p></div></div></div>'; 
        	$.ajax({
	            url:"${WEB_PATH}/global/order/orderviews.do?id="+row.id,
	            type:'get',
	            dataType:'html',
	            cache:false, 
 				async:false, 
	            success:function(data){
	                 html += data
	            }
	        });
		    return html;
		}
		
		//审核信息
		$("#verify-but").click(function(){
			$('#verify-but').attr("disabled",true);
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
	        	$('#verify-but').attr("disabled",false);
			    layer.msg('<font color="red">温馨提示：请选择协商中或正在申请的数据进行操作。</font>', {icon: 5}); 
			    return ;
	       	}
	       	if(info.status != "apply" && info.status != "dealing"){
	       			$('#verify-but').attr("disabled",false);
	        		layer.msg('<font color="red">温馨提示：请选择协商中或正在申请的数据进行操作。</font>', {icon: 5}); 
			    	return ;
	        }
	        $('#verify-but').attr("disabled",false);
	        layer.open({
		      type: 2,
		      title: '<span style="color: #ed5565">抢单信息审查</span>',
		      shadeClose: true,
		      shade: false,
		      maxmin: false, //开启最大化最小化按钮
		      area: ['95%', '95%'],
		      content: '${WEB_PATH }/order/ordermanage/view/verify.do?id='+info.id
		    });
		});
		
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatStatus(val){
			if(val == 'apply'){
				return '<span class="label label-default">提交申请</span>';
			}else if(val == "dealing"){
				return '<span class="label label-primary">处理中</span>';
			}else if(val == "back"){
				return '<span class="label label-warning">协商中</span>';
			}else if(val == "success"){
				return '<span class="label label-success">成功</span>';
			}else if(val == "scrap"){
				return '<span class="label label-danger">作废</span>';
			}
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