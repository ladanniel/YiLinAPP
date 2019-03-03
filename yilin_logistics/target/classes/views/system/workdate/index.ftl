<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>角色管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
	 <link href="${WEB_PATH}/resources/css/plugins/clockpicker/clockpicker.css" rel="stylesheet">
    <@import.tableManagerImportCss/>
   
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>上下班时间管理</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-wrench"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#">选项1</a>
                        </li>
                        <li><a href="#">选项2</a>
                        </li>
                    </ul>
                    <a class="close-link">
                        <i class="fa fa-times"></i>
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
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addWordDate" >
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="editWordDate" >
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="delWordDate">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
                                    </button> 
                                </div>
                                <table id="workDateTableEvents" data-height="630" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-checkbox="true"></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="go_to_work_date" >上班时间</th>  
								            <th data-field="go_off_work_date" >下班时间</th>     
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
	<div id="win_set"></div>
    <@import.tableManagerImportScript/>
    <script src="${WEB_PATH}/resources/js/plugins/clockpicker/clockpicker.js"></script>
    <script >
    	$(function(){
    		
    		//加载table数据
	    	$("#workDateTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/positions/workdate/getPage.do",
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
	            minimumCountColumns: 1, 
	            search: false,    //隐藏搜索框
	            clickToSelect: true, 
	            icons: {
	                refresh: "glyphicon-repeat", 
	                columns: "glyphicon-list"
	            }
	        });
	        //删除角色
	        $('#delWordDate').click(function () {
	        	var selectRow = $("#workDateTableEvents").bootstrapTable('getSelections');
	        	if(0 == selectRow.length){
				    swal({
				        title: "请选择要删除的工作时间",
				        text: "请选中需要删除的工作时间，在使用删除操作。"
				    })
				    return ;
	        	}
	        	var roleIds = "";
	        	for(var i=0;i<selectRow.length;i++){
	        		roleIds = roleIds + "=" +selectRow[i].id;
				}
			    swal({
			        title: "您确定要删除该工作时间吗",
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
								url : '${WEB_PATH }/system/positions/removeWorkDate.do?wIds=' + roleIds,
								cache : false,
								type : 'POST',
								success : function(result) {
									var data = $.parseJSON(result);
									if(data.success == true){
										swal("删除成功！", "您已经永久删除了该角色。", "success")
										$("#workDateTableEvents").bootstrapTable('refresh');
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
        //加载添加页面
        $('#addWordDate').click(function(){
        	$("#win_add").load("${WEB_PATH }/system/positions/view/addworkdate.do");
        });
        //加载修改页面
        $("#editWordDate").click(function(){
        	var selectRow = $("#workDateTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    swal({
			        title: "选择修改角色操作错误",
			        text: "请选择一行您要修改的角色信息，一次有且只有一个角色信息。"
			    })
			    return ;
	       	}
	       	var wInfo = selectRow[0];  
	       	$("#win_edit").load("${WEB_PATH }/system/positions/view/editworkdate.do?wId="+wInfo.id);
        });
        
        
       
        
        
	 
    </script>
</body>

</html>