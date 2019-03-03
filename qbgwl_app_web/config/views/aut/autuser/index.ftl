<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>普通用户认证审核管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
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
		.search{
		    width:300px;
		}
    </style>
    <link href="${WEB_PATH}/resources/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>普通认证审核管理</h5>
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
			                             <div class="col-sm-3"> 
			                                <div class="form-group">
			                                    <label>认证状态：</label>
			                                    <select data-placeholder="选择认证状态" id="auth" class="chosen-select form-control" multiple  >
			                                    
			                                    	<option value="notapply" hassubinfo="true">未申请认证</option>
				                                    <option value="notAuth" hassubinfo="true">未认证通过</option>
				                                    <option value="auth" hassubinfo="true" >认证通过</option>
				                                    <option value="waitProcess" hassubinfo="true" selected = "selected">等待审核</option>
				                                    <option value="supplement" hassubinfo="true">认证补录</option>
				                                    <option value="waitProcessSupplement" hassubinfo="true" selected = "selected">补录等待审核</option>
				                                </select>
			                                </div>
			                            </div>
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>商户名称</label>
			                                    <input id="companyName" name="companyName" class="form-control" type="text" placeholder="请输入商户名">
			                                </div>
			                            </div> 
			                            <div class="col-sm-3" style="padding-top: 22px;">
			                                 <button type="button" class="btn btn-primary" id="search">查询</button>
			                            </div> 
			                        </div>
			                    </div>
                                <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="autuser" >
                                        <i class="fa fa-truck" aria-hidden="true"></i>&nbsp;&nbsp;用户审核
                                    </button>
                                     <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="queryuser" >
                                        <i class="glyphicon glyphicon-user" aria-hidden="true"></i>&nbsp;&nbsp;查看用户信息
                                    </button>
                                </div>
                                <table id="exampleTableEvents"  data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-radio="true"></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="account" >账户号</th>  
								            <th data-field="name" >姓名</th>  
								            <th data-field="authentication" data-formatter="formatterUserAudit">认证状态</th> 
								            <th data-field="status" data-formatter="formatterStatus">状态</th>  
								            <th data-field="phone" >手机号</th>
								            <th data-field="capitalStatus" data-formatter="formatterCapitalStatus">资金账户状态</th>
								            <th data-field="companyName" data-formatter="formatterCompanyName">所属商户</th>
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
    
    <div id="aut_win"></div>
    <@import.tableManagerImportScript/>
    <script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
    <script src="${WEB_PATH }/resources/js/plugins/fancybox/jquery.fancybox.js"></script>
    <script >
    	$(function(){
    		$("#auth").chosen({width:'100%'}); 
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
		    });
		    function queryParams(params) {
		        console.log($("#companytype").val());
		        var temp = {  
			        limit: params.limit,  
			        offset: params.offset, 
			        accountAuths: $("#auth").val() != null ? $("#auth").val().join(","):$("#auth").val(),
			        search:params.search,
			        companyName:$("#companyName").val(),
			        maxrows: params.limit,
			        pageindex:params.pageNumber,
		        };
		        return temp;
			}
    		//加载table数据
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/aut/autuser/getPage.do",
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
	            minimumCountColumns: 1, 
	            search: true,    //隐藏搜索框
	            clickToSelect: true, 
	            icons: {
	                refresh: "glyphicon-repeat", 
	                columns: "glyphicon-list"
	            }
	        });
	        $('input.form-control.input-outline').attr("placeholder","请输入账号/姓名/联系电话"); //设置搜索提示信息
	        
        });
         //点击查询搜索
		$("#search").click(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
        //审核商户信息页面
        $("#autuser").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    layer.msg('请选择一行您要审核的用户信息，一次有且只有一个用户信息。', {icon: 5}); 
			    return ;
	       	}
	       	var account = selectRow[0];  
	        if(account.authentication == "auth"){
	        	layer.msg('【'+account.name+'】用户已经通过审核，无需重复审核！', {icon: 5}); 
	        	return ;
	        }else if(account.authentication == "notAuth"){
	        	layer.msg('【'+account.name+'】用户已经审核，需要等待商户重新提交信息后，才能进行审核！', {icon: 5}); 
	        	return ;
	        }else if(account.authentication == "notapply"){
	        	layer.msg('【'+account.name+'】用户未申请认证，不能进行审核！', {icon: 5}); 
	        	return ;
	        }else if(account.authentication == "supplement"){
	        	layer.msg('【'+account.name+'】用户处于认证信息补录状态，不能进行审核！', {icon: 5}); 
	        	return ;
	        }
	       	$("#aut_win").load("${WEB_PATH}/aut/autuser/audituser.do?accountId="+account.id);
        });
        //查看商户信息
        $("#queryuser").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var account = selectRow[0];  
	        if(1 != selectRow.length){
			    layer.msg('请选择一行您要审核的用户信息，一次有且只有一个用户信息。', {icon: 5}); 
			    return ;
	       	}
	       	if(account.authentication == "notapply"){
	        	layer.msg('【'+account.name+'】用户未申请认证，不能查看信息！', {icon: 5}); 
	        	return ;
	        }
	       	$("#aut_win").load("${WEB_PATH}/aut/autuser/queryuser.do?accountId="+account.id);
        });
        
        function success(result){  
			if(result.success == true){ 
				swal({
			        title: "操作成功",
			        text: result.msg
		    	})
		    	$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				$('#saveBut').attr("disabled",false);    
				swal({
			        title: "操作失败",
			        text: result.msg
		    	})
			}
		}
        function formatterCompanyType(){
			var typeName = '<span class="label label-primary">'+row.companyType.name+'</span>';
        	return typeName;
		}
		
		function formatterUserAudit(val){
			var auditName = "";
			if(val == "notapply"){
				auditName = '<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;未申请认证 </span>';
			}else if(val == "waitProcess") {
				auditName = '<span class="label label-primary"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;等待审核</span>';
			}else if(val == "waitProcessSupplement") {
				auditName = '<span class="label label-primary"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;补录等待审核</span>';
			}else if(val == "notAuth"){
				auditName = '<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;审核未通过</span>';
			}else if(val == "auth"){
				auditName = '<span class="label label-success"><i class="fa fa-truck"></i>&nbsp;&nbsp;认证通过</span>';
			}else if(val == "supplement"){
				auditName = '<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;认证补录</span>';
			} 
        	return auditName;
		}
		function formatterCompanyName(index, row, element){
        	return row.company.name;
		}
		
		function  formatterCapitalStatus(val){
			var capitalStatus = "";
			if(val == "notenable"){
				capitalStatus = '<span class="label label-danger">未启用</span>';
			}else if(val == "open"){
				capitalStatus = '<span class="label label-danger">已开启 </span>';
			}else if(val == "close"){
				capitalStatus = '<span class="label label-success">冻结关闭</span>';
			} 
			return capitalStatus;
		}
		
		function formatterStatus(val){
			var auditName = "";
			if(val == "start"){
				auditName = '<span class="label label-primary">启用</span>';
			}else if(val == "stop"){
				auditName = '<span class="label label-danger">停用 </span>';
			}else if(val == "cancel"){
				auditName = '<span class="label label-danger">停用 </span>';
			}else if(val == "delete"){
				auditName = '<span class="label label-danger">删除</span>';
			} 
        	return auditName;
		} 
        
    </script>
</body>

</html>