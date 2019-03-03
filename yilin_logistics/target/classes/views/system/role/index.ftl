<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>角色管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/jsTree/style.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>角色管理</h5>
                <div class="ibox-tools">
                  
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div class="example"> 
                                <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addRole" data-target="#userModal">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="editRole" >
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="delRole">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="setMenu">
                                        <i class="glyphicon glyphicon-cog" aria-hidden="true"></i>&nbsp;&nbsp;WEB菜单设置
                                    </button> 
                                    <button type="button" class="btn btn-outline btn-default" id="setMenuApp">
                                        <i class="fa fa-android" aria-hidden="true"></i>&nbsp;&nbsp;APP菜单设置
                                    </button>
                                </div>
                                <table id="exampleTableEvents"  data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-radio="true"></th>  
                                            <th data-field="id" data-visible = "false"  >id</th>  
								            <th data-field="name"  >名称</th>  
								            <th data-field="companyTypeName" data-formatter="formatterTypeName" >所属商户类型</th>
								            <th data-field="is_admin" data-formatter="formatterIsAdmin" >是否管理员</th>
								            <th data-field="is_aut" data-formatter="formatterIsAdmin" >是否启用认证</th>
								            <th data-field="idcard" data-formatter="formatterIsAdmin" >是否启用身份证认证</th>
								            <th data-field="driving_license" data-formatter="formatterIsAdmin" >是否启用驾驶证认证</th>
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
	<div id="win_set"></div>
    <@import.tableManagerImportScript/> 
    <script src="${WEB_PATH}/resources/js/plugins/jsTree/jstree.min.js"></script>
    <script >
    	
    	$(function(){
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
			});
    		//加载table数据
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/role/getPage.do",
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
	            search: true,    //隐藏搜索框
	            clickToSelect: true, 
	            icons: {
	                refresh: "glyphicon-repeat", 
	                columns: "glyphicon-list"
	            }
	        });
	        
	         $('input.form-control.input-outline').attr("placeholder","请输入角色名称"); //设置搜索提示信息
	        //删除角色
	        $('#delRole').click(function () {
	        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        	if(0 == selectRow.length){
				    swal({
				        title: "请选择要删除的角色",
				        text: "请选中需要删除的角色，在使用删除操作。"
				    })
				    return ;
	        	}
			    swal({
			        title: "您确定要删除该角色吗",
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
						$.yilinAjax({
					   	  	type:'POST',
					   	  	url:'${WEB_PATH }/system/role/remove.do?roleIds=' + selectRow[0].id, 
					   	  	data:null,
		            		errorcallback:null,
		            		successcallback:success
					   	});
			        } else {
			            swal("已取消", "您取消了删除操作！", "error")
			        }
			    })
			});
        });
        //加载添加页面
        $('#addRole').click(function(){
        	$("#win_add").load("${WEB_PATH }/system/role/view/add.do");
        });
        //加载修改页面
        $("#editRole").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    swal({
			        title: "选择修改角色操作错误",
			        text: "请选择一行您要修改的角色信息，一次有且只有一个角色信息。"
			    })
			    return ;
	       	}
	       	var roleInfo = selectRow[0];  
	       	$("#win_edit").load("${WEB_PATH }/system/role/view/edit.do?roleId="+roleInfo.id);
        });
        
        
        $("#setMenu").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    swal({
			        title: "选择修改角色操作错误",
			        text: "请选择一行您要修改的角色信息，一次有且只有一个角色信息。"
			    })
			    return ;
	       	}
	       	var roleInfo = selectRow[0];  
	       	$("#win_set").load("${WEB_PATH }/system/role/view/set.do?roleId="+roleInfo.id);
        });
        
        
         $("#setMenuApp").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    swal({
			        title: "选择修改角色操作错误",
			        text: "请选择一行您要修改的角色信息，一次有且只有一个角色信息。"
			    })
			    return ;
	       	}
	       	var roleInfo = selectRow[0];  
	       	layer.open({
		      type: 2,
		      title: '<span style="color: #ed5565"><i class="fa fa-pencil"></i>&nbsp;&nbsp;角色【'+roleInfo.name+'】APP菜单分配</span>',
		      shadeClose: true,
		      shade: 0.1,
		      maxmin: false, //开启最大化最小化按钮
		      area: ['65%', '65%'],
		      content: '${WEB_PATH}/system/role/view/setApp.do?roleId='+roleInfo.id,
		      btn: ['<i class="fa fa-check"></i>&nbsp;确认','取消'],
		      yes: function(index, layero){
			      var body = layer.getChildFrame('body', index);
			      var iframeWin = window[layero.find('iframe')[0]['name']];
			      iframeWin.roleSetMenuApp();
			  } 
		    });
        });
        
        
        
        function success(result) {
			if(result.success == true){
				swal("删除成功！", "您已经永久删除了该角色。", "success")
				$("button[name='refresh']").trigger("click");
			}else{
				swal("删除失败！",result.msg, "error")
			}
		}
        
        function formatStatus(val){
			if(val == '1'){
				return '<span class="label label-primary">启用</span>';
			}
			return '<span class="label label-danger">停用</span>';
		}
		
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatterTypeName(index, row, element){ 
        	var typeName = '<span class="label label-primary">'+row.companyType.name+'</span>';
        	return typeName;
		} 
		function formatterIsAdmin(val){
			if(val == true){
				return '<span class="label label-primary">是</span>';
			}
			return '<span class="label label-danger">否</span>';
		}
    </script>
</body>

</html>