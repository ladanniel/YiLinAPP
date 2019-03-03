<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>数据库表列管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
    
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>数据库表列管理</h5>
                <div class="ibox-tools"> 
                    <div class="input-group" style="margin-top:-8px;">
                        <select data-placeholder="请选择数据库表..." class="table_list" style="width:350px;"  >
                            <option value="">请选择数据库表</option>
                            <#list listTable as tables>
                              	<option value="${tables.id}" hassubinfo="true">${tables.s_table_comment}(${tables.s_table_name})</option>
                            </#list>  
                        </select>
                    </div> 
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div class="example"> 
                                <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addObject" data-target="#userModal">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
                                    </button> 
                                    <button type="button" class="btn btn-outline btn-default" id="synchronization">
                                        <i class="glyphicon glyphicon-refresh" aria-hidden="true"></i>&nbsp;&nbsp;数据同步
                                    </button> 
                                </div>
                                <table id="exampleTableEvents" data-height="520" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-checkbox="true"></th>  
                                            <th data-field="id"  data-visible="false" >id</th>  
								            <th data-field="t_column_name" >列英文名</th>  
								            <th data-field="t_column_comment"  >列中文名</th> 
								            <th data-field="t_column_data_type"  >列数据类型</th> 
								            <th data-field="t_column_data_length"  >列数据类型长度</th>
								            <th data-field="t_is_nullable"  >列是否为空</th> 
								            <th data-field="t_column_default"  >列默认值</th> 
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
    	//设置传入参数
		function queryParams(params) {
		  return params
		}
    	//加载用户table数据
    	$(function(){ 
    	    $(".table_list").chosen();
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/column/getPage.do",
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
	        
	        $('input.form-control.input-outline').attr("placeholder","请输入：表英文名/表中文名"); //设置搜索提示信息
	        
	        //删除用户
	        $('#delObject').click(function () {
	        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        	if(0 == selectRow.length){
				    swal({
				        title: "请选择要删除的用户",
				        text: "请选中需要删除的用户，在使用删除操作。"
				    })
				    return ;
	        	}
	        	var ids = "";
	        	for(var i=0;i<selectRow.length;i++){
	        		ids = ids + "=" +selectRow[i].id;
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
								url : '${WEB_PATH }/system/column/remove.do?ids=' + ids,
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
        
	    $('#synchronization').click(function () {
        	$.ajax({
					url : '${WEB_PATH }/system/column/synchronization.do',
					cache : false,
					type : 'POST',
					success : function(result) {
						var data = $.parseJSON(result);
						if(data.success == true){
							swal("系统提示",data.msg, "success")
							$("#exampleTableEvents").bootstrapTable('refresh');
						}
					},
					error: function(result) {
						swal("系统提示", data.msg, "error")
					}
			});
        });
        //修改用户    data-toggle="modal" data-target="#userModal"  aria-hidden="true"
        $("#updateObect").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    swal({
			        title: "选择修改用户操作错误",
			        text: "请选择一行您要修改的用户信息，一次有且只有一个用户信息。"
			    })
			    return ;
	       	}
	       	var userInfo = selectRow[0];  
	       	$("#win_edit").load("${WEB_PATH }/system/column/view/edit.do?id="+userInfo.id);
        });
        
        
        
        //加载添加页面
        $('#addObject').click(function(){
        	$("#win_add").load("${WEB_PATH }/system/column/view/add.do");
        });
        
		
    </script>
</body>

</html>