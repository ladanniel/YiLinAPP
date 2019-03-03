<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>组织机构管理</title> 
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
                <h5>组织机构</h5>
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
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addMeun" data-target="#userModal">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="editMeun" >
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="delMeun">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
                                    </button>
                                </div>
						            <div class="ibox float-e-margins" style="margin-top:15px;">
						                <div class="ibox-content">
						                    <div id="using_json" class="test"></div>
						                </div>
						            </div>
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
    <script src="${WEB_PATH}/resources/js/demo/treeview-demo.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/jsTree/jstree.min.js"></script>
<script>
	 $(document).ready(function() {
	 	var menuId = "";
	 	var leavaId = "";
        $('#using_json').jstree({
            'core': {
                    'data': ${json}
            }
	        }).bind('click.jstree', function(event) {
	                menuId = $(event.target).parents('li').attr('id');  //获取点击ID
	                leavaId = $(event.target).parents('li').attr('aria-level');  //获取点击ID
	                if(leavaId == 1) {
	                	 
	                	 $("#addMeun").removeAttr("disabled");
	               		 $("#editMeun").attr("disabled","disabled");
	               		 $("#delMeun").attr("disabled","disabled");
	                }else if(leavaId == 2) {
	                	$("#addMeun").attr("disabled","disabled");
	                	$("#editMeun").removeAttr("disabled");
	                	$("#delMeun").removeAttr("disabled");
	                }
	               
	        }).bind('dblclick.jstree', function(event) {
		});
		//加载添加页面
        $('#addMeun').click(function(){
        	$("#win_add").load("${WEB_PATH }/system/companysection/view/add.do?id="+menuId);
        });
        //加载编辑页面   
        $("#editMeun").click(function(){
        	if ("" == menuId) {
		                swal({
		                        title: "请选择要删除的组织机构",
		                        text: "请选中需要删除的组织机构，在使用删除操作。"
		                }); 
		             	return;
		    }
		    if ("1" == leavaId) {
		                swal({
		                        title: "提示",
		                        text: "顶级组织机构不能修改。"
		                }); 
		             	return;
		    }
	       	$("#win_edit").load("${WEB_PATH }/system/companysection/view/edit.do?id="+menuId);
        });
        
        //删除组织机构
	        $('#delMeun').click(function () {
	        	if ("" == menuId) {
		                swal({
		                        title: "请选择要删除的组织机构",
		                        text: "请选中需要删除的组织机构，在使用删除操作。"
		                }); 
		             	return;
		        }
			    swal({
			        title: "您确定要删除该组织机构吗",
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
								 url: '${WEB_PATH }/system/companysection/remove.do?id=' + menuId,
								cache : false,
								type : 'POST',
								success : function(result) {
									var data = $.parseJSON(result);
									if(data.success == true){
										swal("删除成功！", "您已经永久删除了该组织机构。", "success")
										location.reload();
									}else{
										swal("删除失败！", data.msg, "error")
									}
								},
								error: function(result) {
									swal("删除失败！", result.msg, "error")
								}
						});
			        } else {
			            swal("已取消", "您取消了删除操作！", "error")
			        }
			    })
			});
        
});
</script>
</body>

</html>