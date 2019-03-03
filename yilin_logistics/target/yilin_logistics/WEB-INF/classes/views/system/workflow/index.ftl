<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>流程部署管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>流程部署管理</h5>
                <div class="ibox-tools"> 
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-wrench"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div class="example"> 
                                <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addleave" data-target="#userModal">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;部署流程
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="updateUser" >
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;删除流程
                                    </button>
                                </div>
                                <table id="exampleTableEvents" data-height="520" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-checkbox="true"></th>  
                                            <th data-field="id"  data-visible="false" >id</th>  
                                            <th data-field="taskid" >taskid</th>  
								            <th data-field="name" >请假人</th>  
								            <th data-field="leaveCount"  >请假天数</th> 
								            <th data-field="leaveStartDate"  >请假开始时间</th>  
								            <th data-field="leaveEndDate"  >请假结束时间</th> 
								            <th data-field="leaveReason"  >请假内容</th> 
								            <th data-field="areaName"  >流程定义标识</th>
								            <th data-field="roleNames"  >流程实例标识</th> 
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
	<div id="win_setarea"></div>
    <@import.tableManagerImportScript/>
    <script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script >
    	//设置传入参数
		function queryParams(params) {
		  return params
		}
    	//加载用户table数据
    	$(function(){ 
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/leave/getPage.do",
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
	        
	        $('input.form-control.input-outline').attr("placeholder","帐号/姓名/手机号搜索"); //设置搜索提示信息
	        
	        //删除用户
	        $('#delUser').click(function () {
	        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        	if(0 == selectRow.length){
				    swal({
				        title: "请选择要删除的用户",
				        text: "请选中需要删除的用户，在使用删除操作。"
				    })
				    return ;
	        	}
	        	var userIds = "";
	        	for(var i=0;i<selectRow.length;i++){
	        		userIds = userIds + "=" +selectRow[i].id;
				}
			    swal({
			        title: "您确定要删除该用户吗",
			        text: "删除后将无法恢复，请谨慎操作！",
			        type: "warning",
			        showCancelButton: true,
			        confirmButtonColor: "#DD6B55",
			        confirmButtonText: "是的，我要删除！",
			        cancelButtonText: "让我再考虑一下…",
			        closeOnConfirm: false,
			        closeOnCancel: false
			    },
			    function(isConfirm) {
			        if (isConfirm) {
			        	$.ajax({
								url : '${WEB_PATH }/system/user/remove.do?userIds=' + userIds,
								cache : false,
								type : 'POST',
								success : function(result) {
									var data = $.parseJSON(result);
									if(data.success == true){
										swal("删除成功！", "您已经永久删除了该用户。", "success")
										$("#exampleTableEvents").bootstrapTable('refresh');
									}
								},
								error: function(result) {
									swal("删除失败！", "系统异常。", "error")
								}
						});
			        } else {
			            swal("已取消", "您取消了删除操作！", "error")
			        }
			    })
			});
        });
        //修改用户    data-toggle="modal" data-target="#userModal"  aria-hidden="true"
        $("#updateUser").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    swal({
			        title: "选择修改用户操作错误",
			        text: "请选择一行您要修改的用户信息，一次有且只有一个用户信息。"
			    })
			    return ;
	       	}
	       	var userInfo = selectRow[0];  
	       	$("#win_edit").load("${WEB_PATH }/system/user/view/edit.do?userId="+userInfo.id);
        });
        
        
        //区域设置
        $("#setArea").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    swal({
			        title: "选择修改用户操作错误",
			        text: "请选择一行您要修改的用户信息，一次有且只有一个用户信息。"
			    })
			    return ;
	       	}
	       	var userInfo = selectRow[0];  
	       	$("#win_setarea").load("${WEB_PATH }/system/user/view/setarea.do?userId="+userInfo.id);
        });
        
        //加载添加页面
        $('#addleave').click(function(){
        	$("#win_add").load("${WEB_PATH }/system/leave/view/add.do");
        });
        
        function formatStatus(val){
			if(val == '1'){
				return '<span class="label label-danger">停用</span>';
			}
			return '<span class="label label-primary">正常</span>';
		}
		
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
    </script>
</body>

</html>