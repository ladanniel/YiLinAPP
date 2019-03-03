<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>商户审核</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
    <link href="${WEB_PATH}/resources/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
    
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
		.account:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "账户信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.account {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    margin-top: 10px;
		}
		.company:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "商户信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.company {
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
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>平台商户</h5>
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
			                                    <label>商户类型：</label>
			                                    <select data-placeholder="选择商户类型" id="companytype" class="chosen-select form-control" multiple  >
				                                    <@companyTypeInfo isreg = "true">
					                    	 			<#list companyTypeViews as companyType >
				                                    		<option value="${companyType.id}" hassubinfo="true">${companyType.name}</option>
				                                     	</#list>
										 			</@companyTypeInfo>
				                                </select>
			                                </div> 
			                            </div>
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>地区：</label>
			                                    <input id="areaFullName" name="areaFullName" class="form-control" readonly type="text" value="">
												<input id="area_id" name="area_id" type="hidden" >
			                                </div>
			                            </div> 
			                           <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>商户状态：</label>
			                                    <select data-placeholder="选择商户状态" id="status" class="chosen-select form-control"  multiple  >
				                                    <option value="open" hassubinfo="true" selected = "selected">启用</option>
				                                    <option value="colse" hassubinfo="true" >关闭</option>
				                                </select>
			                                </div>
			                            </div> 
			                            <div class="col-sm-3" style="padding-top: 22px;">
			                                 <button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
			                            </div> 
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>商户来源：</label>
			                                    <select data-placeholder="请选择商户来源" id="source" class="chosen-select form-control"  multiple >
				                                      <option value="1" hassubinfo="true" >前端注册</option>
				                                      <option value="0" hassubinfo="true" >后台增加</option>
				                                </select>
			                                </div> 
			                            </div>
			                             <div class="col-sm-5"> 
			                                <div class="form-group">
			                                    <label>认证状态：</label>
			                                    <select data-placeholder="选择认证状态" id="auth" class="chosen-select form-control" multiple   >
			                                   		<option value="notapply" hassubinfo="true" selected = "selected">未申请认证</option>
				                                    <option value="notAuth" hassubinfo="true">未认证通过</option>
				                                    <option value="auth" hassubinfo="true" selected = "selected">认证通过</option>
				                                    <option value="waitProcess" hassubinfo="true" >等待审核</option>
				                                    <option value="supplement" hassubinfo="true" selected = "selected">认证补录</option>
				                                    <option value="waitProcessSupplement" hassubinfo="true" selected = "selected" >补录等待审核</option>
				                                </select>
			                                </div>
			                            </div>
			                        </div>
			                    </div>
                                <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addCompanyAccount" >
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;添加商户账户
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addaudtcompany" >
                                        <i class="fa fa-credit-card" aria-hidden="true"></i>&nbsp;&nbsp;商户证件管理
                                    </button>
                                </div>
                                <table id="exampleTableEvents"  >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-radio="true" data-formatter="readoFormatter" ></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="name" data-formatter="formatterCompanyName">商户名称</th> 
								            <th data-field="companyType" data-formatter="formatterCompanyType">商户类型</th> 
								            <th data-field="audit" data-formatter="formatterCompanyAudit">认证状态</th> 
								            <th data-field="status"  data-editable="true" data-type = "select" data-save = "service" data-validate = "required" data-source="${WEB_PATH}/global/data/getCompanyStatusList.do">商户状态</th> 
								            <th data-field="source" data-formatter="formatterSource">用户来源</th> 
								            <th data-field="areaFullName" data-formatter="formatterCompanyAreaFullName">所在地区</th>
								            <th data-field="contactName" data-editable="true" data-type = "text" data-save = "service" data-validate = "required,isChinese">联系人</th>
								            <th data-field="contactTel" data-editable="true" data-type = "text" data-save = "service" data-validate = "required,isMobile">联系人电话</th>
								            <th data-field="create_time" data-formatter="formatTime">创建时间</th>
								            <th data-field="update_time" data-formatter="formatTime">提交时间</th>
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
    
    <div id="company_win"></div>
    <@import.tableManagerImportScript/>
    <script src="${WEB_PATH }/resources/js/plugins/fancybox/jquery.fancybox.js"></script>
    <script src="${WEB_PATH}/resources/js/city-picker.data.js"></script>
	<script src="${WEB_PATH}/resources/js/city-picker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/wizard/jquery.bootstrap.wizard.js"></script>
	<script src="${WEB_PATH}/resources/js/ajaxfileupload.js"></script>
	
    <script >
        var account = null;
    	$(function(){
    		$("#companytype").chosen({width:'100%'}); 
    		$("#auth").chosen({width:'100%'}); 
    		$("#status").chosen({width:'100%'}); 
    		$("#source").chosen({width:'100%'});
    		$('#areaFullName').citypicker({areaId:'area_id'}); 
    		$("#roleSelect").chosen(); 
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
		    });
		    function queryParams(params) {
		        var temp = {  
			        limit: params.limit,  
			        offset: params.offset, 
			        companyTypes: $("#companytype").val() != null ? $("#companytype").val().join(","):$("#companytype").val(),
			        companyAuths: $("#auth").val() != null ? $("#auth").val().join(","):$("#auth").val(),
			        status_serch: $("#status").val() != null ? $("#status").val().join(","):$("#status").val(),
			        source_serch: $("#source").val() != null ?  $("#source").val().join(","):$("#source").val(),
			        search:params.search,
			        areaFullName:$("#areaFullName").val(),
			        maxrows: params.limit,
			        pageindex:params.pageNumber,
		        };
		        return temp;
			}
    		//加载table数据
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/company/getPage.do",
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
	        
	        $('input.form-control.input-outline').attr("placeholder","请输入商户名称/联系人/联系人电话"); //设置搜索提示信息
	        
        });
        
         //点击查询搜索
		$("#search").click(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
        //添加商户账户信息页面
        $("#addCompanyAccount").click(function(){
	       	$("#company_win").load("${WEB_PATH}/system/company/view/addcompanyaccount.do");
        });
         //商户认证信息页面
        $("#addaudtcompany").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var company = selectRow[0];  
	        if(1 != selectRow.length){
			    layer.msg('请选择一行商户信息，一次有且只有一个商户信息。', {icon: 5}); 
			    return ;
	       	}
	       	$("#company_win").load("${WEB_PATH}/system/company/addcompanyaudtinfo.do?companyId="+company.id);
        });
        
        function detailFormatter(index, row) {
        	var html = ""; 
        	$.ajax({
	            url:"${WEB_PATH}/global/data/getCompantId.do?id="+row.id,
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
        
        function formatterCompanyType(index, row, element){
			var typeName = '<span class="label label-primary">'+row.companyType.name+'</span>';
        	return typeName;
		}
		
		function updateColumn(updateColume){
			 $.yilinAjax({
		   	  	type:'POST',
		   	  	url:'${WEB_PATH }/system/company/updateCompanyFiled.do', 
		   	  	data:updateColume,
        		errorcallback:null,
        		successcallback:success
		   	});
		}
		 
		function success(result) {
			if(result.success == true){
				swal("保存成功！", result.msg, "success");
				$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				swal("保存失败！", result.msg, "error");
			}
		}
		
		function formatterCompanyAudit(value, row, index){
			var auditName = "";
			if(row.companyType.is_aut){
				if(row.audit == "notapply"){
					auditName = '<span class="label label-danger"><i class="iconfont" style="font-size:14px;font-style:normal;" >&#xe643;</i>&nbsp;&nbsp;未提交认证审核</span>';
				}else if(row.audit == "waitProcess") {
					auditName = '<span class="label label-primary"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;等待审核</span>';
				}else if(row.audit == "waitProcessSupplement") {
					auditName = '<span class="label label-primary"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;补录等待审核</span>';
				}else if(row.audit == "notAuth"){
					auditName = '<span class="label label-danger"><i class="iconfont" style="font-size:14px;font-style:normal;" >&#xe64a;</i>&nbsp;&nbsp;审核未通过</span>';
				}else if(row.audit == "auth"){
					auditName = '<span class="label label-success"><i class="iconfont" style="font-size:14px;font-style:normal;" >&#xe645;</i>&nbsp;&nbsp;认证通过</span>';
				}else if(row.audit == "supplement"){
					auditName = '<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;认证信息补录</span>';
				} 
			}else{
				auditName = '<span class="label label-danger"><i class="iconfont" style="font-size:14px;font-style:normal;" >&#xe648;</i>&nbsp;&nbsp;免认证</span>';
			}
        	return auditName;
		}
		
		
		function readoFormatter(value, row, index) {
	        if(row.source == 1 && row.audit == "notapply"){
	        	return {
	                disabled: true
	            };
	        }
	        return value;
	    }
	    
	    function formatterCompanyName(val){
			if(val == null || val == ""){
				val = '<span class="label label-danger">无</span>';
			} 
			return val;
		}
		
		function formatterCompanyAreaFullName(val){
			if(val == null || val == ""){
				val = '<span class="label label-danger">无</span>';
			} 
			return val;
		}
		 
		function formatterSource(val){
			var sourceName = "";
			if(val == 1){
				sourceName = '<span class="label label-primary">前端注册</span>';
			}else if(val == 0){
				sourceName = '<span class="label label-danger">后台增加</span>';
			}  
        	return sourceName;
		}
		function formatterIsAdmin(val){
			if(val == true){
				return '<span class="label label-primary">是</span>';
			}
			return '<span class="label label-danger">否</span>';
		}
		function formatTime(val){
			if(val != null){
				var tt=new Date(val).toLocaleString(); 
    			return tt; 
			}else{
				sourceName = '<span class="label label-danger">无</span>';
				return sourceName;
			}
		 	
		}
    </script>
</body>

</html>