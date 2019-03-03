<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>角色管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>商户类型</h5>
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
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addCompanyType" >
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="editCompanyType" >
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="delCompanyType">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
                                    </button> 
                                </div>
                                <table id="exampleTableEvents"  data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-radio="true"></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="name" >类型名称</th> 
								            <th data-field="is_register" data-formatter="formatterIsAdmin">是否在注册页面显示</th> 
								            <th data-field="is_aut" data-formatter="formatterIsAdmin">是否启用认证</th> 
								            <th data-field="business_license" data-formatter="formatterIsAdmin">是否启用营业执照认证</th> 
								            <th data-field="idcard" data-formatter="formatterIsAdmin">是否启用身份证认证</th>
								            <th data-field="driving_license" data-formatter="formatterIsAdmin">是否启用驾驶证认证</th>
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
    <script >
    	$(function(){
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
			});
    		//加载table数据
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/companytype/getPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:20,   
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
	        
	        $('input.form-control.input-outline').attr("placeholder","请输入商户类型名称"); //设置搜索提示信息
	        //删除商户类型
	        $('#delCompanyType').click(function () {
	        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        	if(0 == selectRow.length){
				    swal({
				        title: "提示",
				        text: "请选中需要删除的商户类型，再使用删除操作。"
				    })
				    return ;
	        	}
	        	var companyTypeIds = "";
	        	for(var i=0;i<selectRow.length;i++){
	        		companyTypeIds = companyTypeIds + "=" +selectRow[i].id;
				}
			    swal({
			        title: "您确定要删除该商户类型吗",
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
					   	  	 url:'${WEB_PATH }/system/companytype/remove.do?ids=' + companyTypeIds,
					   	  	 data:null,
		            		 errorcallback:null,
		            		 btn:null,
		            		 successcallback:success
				   	  	});
			        } else {
			            swal("已取消", "您取消了删除操作！", "error")
			        }
			    })
			});
        });
        //加载添加页面
        $('#addCompanyType').click(function(){
        	$("#win_add").load("${WEB_PATH }/system/companytype/view/add.do");
        });
        //加载修改页面
        $("#editCompanyType").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    swal({
			        title: "选择修改商户类型操作错误",
			        text: "请选择一行您要修改的商户类型信息，一次有且只有一个商户类型信息。"
			    })
			    return ;
	       	}
	       	var companyType = selectRow[0];  
	       	$("#win_edit").load("${WEB_PATH }/system/companytype/view/edit.do?id="+companyType.id);
        });
        function formatterIsAdmin(val){
			if(val == true){
				return '<span class="label label-primary">是</span>';
			}
			return '<span class="label label-danger">否</span>';
		}
		
		function success(result) {
			if(result.success == true){
				swal("删除成功！", "您已经永久删除了该商户类型。", "success");
				$("button[name='refresh']").trigger("click");
			}else{
				swal({
			        title: "无法删除该商户类型",
			        text: result.msg
			    })
			}
		}
        
    </script>
</body>

</html>