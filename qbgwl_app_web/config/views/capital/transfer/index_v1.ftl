<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>转账记录</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>转账记录</h5>
                <div class="ibox-tools"> 
                    
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div class="example"> 
                                <form class="form-horizontal m-t" id="addResourceInfo">
						                    <div class="modal-body row"> 
						                    		<div class="form-group">
						                                <label class="col-md-1 control-label">编号：</label>
						                                <div class="col-md-2">
						                                    <input id="stradeNo" name＝"stradeNo" type="text"  class="form-control" placeholder="输入流水号搜索" maxlength="36">
						                                </div>
						                                <label class="col-md-1 control-label">类型：</label>
														<div class="col-md-2">
															<select class="form-control" id="transferTypeId">
																<option value="">全部</option>
																<@transferType >
											                    	 <#list transferTypeViews as vo >
										                              	 <option value="${vo.id}">${vo.name}</option> 
										                             </#list>
															    </@transferType>
														    </select>
														</div>
						                                <label class="col-md-1 control-label">对方帐户：</label>
						                                <div class="col-md-2">
						                                    <input id="stoAccount" name="stoAccount" type="text"  class="form-control"  placeholder="输入收款人账号或手机号搜索" maxlength="18">
						                                </div>
						                                <div class="col-md-2">
						                                	<button type="button" class="btn btn-primary" id="search">查询</button>
						                                </div>
						                            </div>
						                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
					                                    <button type="button" class="btn btn-primary" id="add">转账</button>
					                                </div>
						                    </div>
						                 	</form>
                                <table id="exampleTableEvents" data-height="520" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
								            <th data-field="tradeNo" >转账编号</th>  
								            <th data-field="sourceAccount">交易方式</th>
								            <th data-field="sourcType">交易方式类型</th>
								            <th data-field="toAccount.phone"  data-formatter="formatPhone">对方帐户</th>
								            <th data-field="money"  data-formatter="formatMoney">转账金额</th>
								            <th data-field="transferType.name">类型</th>
								            <th data-field="create_time" data-formatter="formatTime">时间</th>
								            <th data-field="remark">备注</th>
								            <th data-field="status" data-formatter="formatStatus" >状态</th>   
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
	<div id="win_edit"></div> 
    
    <@import.tableManagerImportScript/> 
    <script >
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        tradeNo: $("#stradeNo").val(),
		        reAccount: $("#stoAccount").val(),
		        transferTypeId: $("#transferTypeId option:selected").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/capital/transfer/getPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
	            showRefresh:true, 
	            showColumns:true,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
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
	        $('input.form-control.input-outline').hide();
        });
        
        //加载添加页面
        $('#add').click(function(){
        	$("#win_add").load("${WEB_PATH }/capital/transfer/view/add.do");
        });
        
        //点击查询搜索
		$("#search").click(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
        
        function formatStatus(val){
			if(val == 'failed'){
				return '<span class="label label-danger">失败</span>';
			}else if(val == 'success'){
				return '<span class="label label-success">成功</span>';
			}else{
				return '<span class="label label-default">待处理</span>';
			}
			
		}
		
		function formatPhone(val){
		 	var tt = val.substring(0,3) + "****" + val.substring(val.length - 4,val.length);
    		return tt; 
		}
		
		function formatMoney(val){
			if(val < 0){
				return '<span class="label label-danger">'+val+'</span>'; 
			}else{
				return '<span class="label label-primary">+'+val+'</span>'; 
			}
		}
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
    </script>
</body>

</html>