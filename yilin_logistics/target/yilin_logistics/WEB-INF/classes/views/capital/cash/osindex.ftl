<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>提现记录</title> 
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
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>提现审核</h5>
                <div class="ibox-tools"> 
                    
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div class="example"> 
						                    <div style="" class="demo ui-sortable">
				                            		 <div class="row">
							                            <div class="col-sm-3" style="margin-left:5px;">
							                                <label>关键字：</label>
							                                <input class="form-control" type="text" placeholder="输入关键字搜索" name="search_val" id="search_val"/>
							                            </div>
							                            <div class="col-sm-3">
							                                <label>提现日期时段：</label>
							                                    <select class="form-control" name="datestatus" id="datestatus" placeholder="选择状态搜索">
										                              <option value="normal"><@parameterManage>${parameterManage.value}</@parameterManage>日前(正常处理)</option>
										                              <option value="particularity"><@parameterManage>${parameterManage.value}</@parameterManage>日内(特殊处理)</option>
										                              <option value="">全部</option>
															 	</select>
							                            </div>
							                            <div class="col-sm-3">
							                                <div class="form-group">
							                                    <label>状态：</label>
							                                    <select class="form-control" name="sstatus" id="sstatus" placeholder="选择状态搜索">
							                                    	  <option value="">全部</option>
										                              <option value="lock">锁定</option>
										                              <option value="waitProcess">待处理</option>
															 	</select>
							                                </div>
							                            </div>
							                             <div class="col-sm-1" style="padding-top: 22px;">
							                                 <button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							                            </div>
							                        </div>
							                    </div>
							                    </div>
						                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
						                            	<button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="lock-but" >
					                                        <i class="glyphicon glyphicon-lock" aria-hidden="true"></i>&nbsp;&nbsp;提现锁定
					                                    </button>
					                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="verify-but" >
					                                        <i class="glyphicon glyphicon-jpy" aria-hidden="true"></i>&nbsp;&nbsp;提现审核
					                                    </button>
					                                    <a href="javascript:void(0);" class="btn btn-outline btn-default" data-toggle="modal" id="export-but" >
					                                        <i class="glyphicon glyphicon-export" aria-hidden="true"></i>&nbsp;&nbsp;导出Excel
					                                    </a>
					                                </div>
						                    </div>
                                <table id="exampleTableEvents" data-height="520" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true"></th>  
		                                    <th data-field="id"  data-visible="false" >id</th>
								            <th data-field="tradeNo"  data-visible="false">提现编号</th> 
								            <th data-field="account.phone" >手机账号</th>
								            <th data-field="account.name" >姓名</th> 
								            <th data-field="bankname"  >提现银行</th>
								            <th data-field="bankcard">银行卡号</th>
								            <th data-field="openbank">开户行名称</th>
								            <th data-field="money">提现金额</th>
								            <th data-field="create_time" data-formatter="formatTime">提现申请时间</th>
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
    <div id="win_verify"></div>
    
    <@import.tableManagerImportScript/> 
    <script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/switchery/switchery.js"></script>
    <script >
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        status: $("#sstatus option:selected").val(),
		        search: $("#search_val").val(),
		        datestatus:$("#datestatus option:selected").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/capital/cashapplication/getOsPage.do",
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
	        $("#search").click(function(){
				$("#exampleTableEvents").bootstrapTable('refresh');
			});
	        $('input.form-control.input-outline').hide();
	        
	        function detailFormatter(index, row) {
	        	console.log(row);
	        	var remark = row.remark;
	        	if(null == remark || "" == remark){
	        		remark = "无";
	        	}
	        	var html = "<div style='text-align:center'>备注信息："+remark+"</div>"; 
			    return html;
			}
        });
        
        //锁定审核信息
        $("#lock-but").click(function(){
        	$('#lock-but').attr("disabled",true);
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0];  
	        if(1 != selectRow.length){
	        	$('#lock-but').attr("disabled",false);
			    layer.msg('<font color="red">谨慎操作：请选择一条提现申请进行锁定。</font>', {icon: 5}); 
			    return ;
	       	}
	       	if(info.status != "waitProcess"){
	       		$('#lock-but').attr("disabled",false);
			    layer.msg('<font color="red">谨慎操作：请选择等待处理中的数据锁定，其它数据无法锁定。</font>', {icon: 5}); 
			    return ;
	       	}
	       	swal({
					    title: "锁定提现申请",
					    text: "你是否要锁定该提现申请？",
					    type: "warning",
					    showCancelButton: true,
					    confirmButtonColor: "#DD6B55",
					    confirmButtonText: "是的，我要锁定！",
					    cancelButtonText: "我点错了…",
					    closeOnConfirm: false,
					    closeOnCancel: false
				},function(isConfirm) {
				    if(isConfirm) { 
						$.ajax({
								  type: 'POST',
								  url:'${WEB_PATH }/capital/cashapplication/lock.do',  
								  data: {"id":info.id},
								  dataType: "json",
								  success:function(result){
								  		$('#lock-but').attr("disabled",false);  
										swal("", result.msg, "success");
										$("#exampleTableEvents").bootstrapTable('refresh');
									},
									error:function(data){
									 	$('#lock-but').attr("disabled",false);  
									 	var data = $.parseJSON(data.responseText);
								        swal("", data.msg, "error");
									 }
						});
					}else {
						$('#lock-but').attr("disabled",false);
			            swal("已取消", "您取消了操作！", "error")
			        }
				});
        });
        
        //审核信息
        $('#verify-but').click(function(){
        	$('#verify-but').attr("disabled",true);
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
	        	$('#verify-but').attr("disabled",false);
			    layer.msg('<font color="red">谨慎操作：请选择一条锁定后的数据进行审核。</font>', {icon: 5}); 
			    return ;
	       	}
	       	if(info.status != "lock"){
	       			$('#verify-but').attr("disabled",false);
	        		layer.msg('<font color="red">谨慎操作：请锁定该条提现申请后再审核。</font>', {icon: 5}); 
			    	return ;
	        }
	        $('#verify-but').attr("disabled",false);
        	$("#win_verify").load("${WEB_PATH }/capital/cashapplication/view/verify.do?cashId="+info.id);
        });
        
        //导出Excel
        $("#export-but").click(function(){
		    var search = $("input.form-control.input-outline").val();
		    var status = $("#sstatus option:selected").val();
		    var datestatus = $("#datestatus option:selected").val();
        	location.href = "${WEB_PATH }/capital/cashapplication/export.do?search="+search+"&status="+status+"&datestatus="+datestatus+"&type=verify";
        });
        
        //选择刷新
		$("#sstatus").change(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
		
		//选择刷新
		$("#datestatus").change(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
        
        function formatStatus(val){
			if(val == 'failed'){
				return '<span class="label label-danger">未通过</span>';
			}else if(val == 'success'){
				return '<span class="label label-success">通过</span>';
			}else if(val == "lock"){
				return '<span class="label label-primary">锁定</span>';
			}else{
				return '<span class="label label-default">待处理</span>';
			}
			
		}
		
		function formatTime(val){
			if(null == val || "" == val){
				return "无"; 
			}
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
    </script>
</body>

</html>