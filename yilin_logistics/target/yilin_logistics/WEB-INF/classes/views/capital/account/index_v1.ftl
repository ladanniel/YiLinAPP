<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>资金账户</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>资金账户 - 资金流水详情</h5>
                <div class="ibox-tools"> 
                    
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                                <form class="form-horizontal m-t">
						                    <div class="modal-body row"> 
						                    		<div class="form-group">
						                    				<div class="col-sm-4">&nbsp;
						                    				</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                            			总资产：<span style="color:red">¥&nbsp;<font id="total">${account.total}</font></span> =
                                    							可用余额：<span style="color:red">¥&nbsp;<font id="avaiable">${account.avaiable}</font></span> +
                                    							冻结资金：<span style="color:red">¥&nbsp;<font id="frozen">${account.frozen}</font></span>
                                    				</div>
                                    				<div class="form-group">
                                    					<div class="col-sm-5">&nbsp;</div>
                                    					<button type="button" class="btn btn-primary" id="recharge">充值</button>  
						                                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                					<button type="button" class="btn btn-primary" id="cash">提现</button>
	                                							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                					<button type="button" class="btn btn-primary" id="transfer">转账</button>
	                                							
                                    				</div>
						                    		<div class="form-group">
						                    			<label class="col-lg-1 col-md-1  col-xs-6 control-label">付款帐户：</label>
						                    				<div class="col-md-2">
							                                    <input id="tradeAccount" name＝"tradeAccount" type="text"  class="form-control" placeholder="银行卡号/交易帐户号" maxlength="36">
							                                </div>
						                                <label class="col-md-2 col-xs-6 control-label">时间范围：</label>
						                                <div class="col-lg-3 col-md-5  col-xs-6" id="data">
						                                    <div class="input-daterange input-group" id="datepicker">
								                                <input type="text" class="input-sm form-control" name="start" id="start" value="" placeholder="选择开始时间"/>
								                                <span class="input-group-addon">到</span>
								                                <input type="text" class="input-sm form-control" name="end" id="end" value="" placeholder="选择结束时间"/>
								                            </div>
						                                </div>
						                                <label class="col-lg-1 col-md-1  col-xs-6 control-label">类型：</label>
						                                <div class="col-lg-2 col-md-2  col-xs-6">
						                                    <select class="form-control" name="sstatus" id="sstatus" placeholder="全部">
						                                    	  <option value="">全部</option>
									                              <option value="recharge">充值</option> 
									                              <option value="frozen">冻结</option>
									                              <option value="cash">提现</option>
									                              <option value="transfer">转账</option>
									                              <option value="fee">手续费</option>
									                              <option value="consume">消费</option>
									                              <option value="income">收款</option>
									                              <option value="other">其它</option>
														 	</select>
						                                </div>
						                                <div class="col-lg-1 col-md-1">
						                                	<button type="button" class="btn btn-primary" id="search">查询</button>
						                                </div>
						                            </div>
						                    </div>
						                 	</form>
                                <table id="exampleTableEvents" data-height="520" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="sourceAccount" >交易方式</th>
								            <th data-field="sourceType" >交易方式类型</th>  
								            <th data-field="type"  data-formatter="formatType">类型</th>
								            <th data-field="operator">操作人</th>
								           <th data-field="create_time" data-formatter="formatTime">时间</th>
								            <th data-field="remark">备注</th>
                                        </tr>
                                    </thead>
                                </table>
                        </div>
                        <!-- End Example Events -->
                    </div>
                </div>
            </div>
        </div>
        <!-- End Panel Other -->
    </div>
    <div id="cash-div"></div>
	<div id="recharge-div"></div> 
	<div id="transfer-div"></div> 
    
    <@import.tableManagerImportScript/> 
    <script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/switchery/switchery.js"></script>
    	<script type="text/javascript"> 
    	$("#data .input-daterange").datepicker({
	        keyboardNavigation: !1,
	        forceParse: !1,
	        autoclose: !0
	    }); {
	        var i = document.querySelector(".js-switch"),
	        t = (new Switchery(i, {
	            color: "#1AB394"
	        }), document.querySelector(".js-switch_2")),
	        a = (new Switchery(t, {
	            color: "#ED5565"
	        }), document.querySelector(".js-switch_3"));
	        new Switchery(a, {
	            color: "#1AB394"
	        })
	    } 
	    $("#start").change(function(){
	    	var end = $("#end").val();
	    	var start = $("#start").val();
	    	if(end == null || "" == end){
	    		$("#end").val(start);
	    	}else{
	    		var end1 = end.replace("-","");
	    		var start1 = start.replace("-","");
	    		if(end1 < start1){
	    			$("#end").val(start);
	    		}
	    	}
	    });
	    $("#end").change(function(){
	    	var end = $("#end").val();
	    	var start = $("#start").val();
	    	if(start == null || "" == start){
	    		$("#start").val(end);
	    	}else{
	    		var end1 = end.replace("-","");
	    		var start1 = start.replace("-","");
	    		if(end1 < start1){
	    			$("#start").val(end);
	    		}
	    	}
	    });
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        tradeAccount: $("#tradeAccount").val(),
		        start: $("#start").val(),
		        end: $("#end").val(),
		        type: $("#sstatus option:selected").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/capital/account/getPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
	            showRefresh:true, 
	            showColumns:true,
	            iconSize: "outline",
	            sidePagination: "server", //设置为服务器端分页
	            queryParams: queryParams,//参数
	            searchText:"",   //设置搜索框文本初始值
	            minimumCountColumns: 1,  
	            showToggle:false, 
	            clickToSelect: true,  
	            icons: {
	                refresh: "glyphicon-repeat", 
	                columns: "glyphicon-list"
	            }
	        });
	        
	        $('.fixed-table-toolbar').remove();
        });
        
         //点击查询搜索
		$("#search").click(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
        
        function formatType(val){
			if(val == 'recharge'){
				return '<span class="label label-primary">充值</span>';
			}else if(val == 'frozen'){
				return '<span class="label">冻结</span>';
			}else if(val == 'cash'){
				return '<span class="label label-danger">提现</span>';
			}else if(val == 'transfer'){
				return '<span class="label label-danger">转账</span>';
			}else if(val == 'fee'){
				return '<span class="label label-danger">手续费</span>';
			}else if(val == 'consume'){
				return '<span class="label label-danger">消费</span>';
			}else if(val == 'income'){
				return '<span class="label label-primary">收款</span>';
			}else{
				return '<span class="label">其它</span>';
			}
			
		}
		
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		$('#recharge').click(function(){
			$("#recharge-div").load("${WEB_PATH }/capital/rechargerecord/view/add.do");
		});  
		$('#cash').click(function(){
			$("#cash-div").load("${WEB_PATH }/capital/cashapplication/view/add.do");
		});  
		$('#transfer').click(function(){
			$("#transfer-div").load("${WEB_PATH }/capital/transfer/view/add.do");
		});
    </script>
</body>

</html>