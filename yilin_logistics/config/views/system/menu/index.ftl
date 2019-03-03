<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>菜单管理</title> 
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
                <h5>菜单管理</h5>
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
        $('#using_json').jstree({
        	
            'core': {
            	'themes':{
			            "icons" : false
			        },
                'data': ${json}
                    
            }
	        }).bind('click.jstree', function(event) {
	                menuId = $(event.target).parents('li').attr('id');  //获取点击ID
	        }).bind('dblclick.jstree', function(event) {
	        }).bind("loaded.jstree", function () {
	           //$('#using_json').jstree("open_all");
	        });
		//加载添加页面
        $('#addMeun').click(function(){
        	if ("" == menuId) {
		                swal({
		                        title: "请选择要增加的父菜单",
		                        text: "请选中需要增加的父菜单，在使用新增操作。"
		                }); 
		             	return;
		    }
        	$("#win_add").load("${WEB_PATH }/system/menu/view/add.do?menuId="+menuId);
        });
        //加载编辑页面   
        $("#editMeun").click(function(){
        	if ("" == menuId) {
		                swal({
		                        title: "请选择要修改的菜单",
		                        text: "请选中需要修改的菜单，在使用修改操作。"
		                }); 
		             	return;
		    }
	       	$("#win_edit").load("${WEB_PATH }/system/menu/view/edit.do?menuId="+menuId);
        });
        
        //删除菜单
        $('#delMeun').click(function () {
        	if ("" == menuId) {
                swal({
                        title: "请选择要删除的菜单",
                        text: "请选中需要删除的菜单，在使用删除操作。"
                }); 
             	return;
	        }
		    swal({
		        title: "您确定要删除该菜单吗",
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
				   	  	url:'${WEB_PATH }/system/menu/remove.do?menuId=' + menuId,
				   	  	data:null,
	            		errorcallback:null,
	            		successcallback:success
			   	    });
		        } else {
		            swal("已取消", "您取消了删除操作！", "error")
		        }
		    })
		});
		
		function success(result) {
			console.log(result);
			if(result.success == true){
				swal("删除成功！", "您已经永久删除了该菜单。", "success")
				location.reload();
			}else{
				swal("删除失败！", result.msg, "error")
			}
		}
        
});
</script>
</body>

</html>