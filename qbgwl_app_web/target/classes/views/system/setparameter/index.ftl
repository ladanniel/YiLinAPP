<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>菜单管理</title> 
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
    <link href="${WEB_PATH}/resources/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/jsTree/style.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">

        
        <div class="row">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>部门信息 &nbsp;&nbsp;&nbsp;<small style="color: red;">(请选择部门)</small></h5>
                    </div>
                    <div class="ibox-content">
                        <div id="dept_jstree">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-9">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5 ><font style="color: red;" id="positions_msg"> </font>职位信息</h5>
                          <div id="tool_btn">
                          	  <button type="button" class="btn btn-white btn-xs pull-right" id="delParameter"  >
	                               <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;删除参数
	                          </button>
                          	  &nbsp;&nbsp;&nbsp;
	                          <button type="button" class="btn btn-white btn-xs pull-right" id="editParameter"  style="margin-right: 10px;" >
	                               <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改参数
	                          </button>
	                          &nbsp;&nbsp;&nbsp;
	                          <button type="button" class="btn btn-white btn-xs pull-right" id="addParameter" style="margin-right: 10px;">
	                               <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增参数
	                          </button>
                          </div>
                          
                    </div>
                    
                    <div class="ibox-content">
                    	<div id="no_data" style="text-align: center;"> <h2 style="color: red;" id="res_mesg">(请选择部门) </h2></div>
                        <div id="positions_jstree"></div>  
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div id="win_setparameter"></div>
    <@import.tableManagerImportScript/>
    <script src="${WEB_PATH}/resources/js/demo/treeview-demo.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/jsTree/jstree.min.js"></script>
<script>
    var positionsId = "";
    var positionsName = "";
    var parameter_value = "";
    var dept = null;
	 $(document).ready(function() {
        $("#tool_btn").hide();
   		$('#dept_jstree').jstree({
        'core': {
                'data': ${json}
        }
        }).bind("select_node.jstree",function(event,obj) {
        		$("#positions_msg").html(obj.node.text+"--");
                $("#no_data").hide();
                dept = obj.node;
                getPositions(obj.node);
        });
		
		$('#positions_jstree').jstree({
        'core': {
                'data': []
        }
        }).bind("select_node.jstree",function(event,data){
                alert(data.node.id);
        });
        
        //加载添加页面
        $('#addParameter').click(function(){
        	 if(positionsId == ""){
        	 	swal("", "请选择需要增加的职位！", "error");
        	 	return false;
        	 }
        	 $("#win_setparameter").load("${WEB_PATH }/system/positions/view/setparameter_add.do?positionsId="+positionsId+"&positionsName="+positionsName);
        });
        
        
        //加载添加页面
        $('#editParameter').click(function(){
        	 if(positionsId == ""){
        	 	swal("", "请选择需要增加的职位！", "error");
        	 	return false;
        	 }
        	 $("#win_setparameter").load("${WEB_PATH }/system/positions/view/setparameter_edit.do?positionsId="+positionsId);
        });
        
        //删除职位信息
	    $('#delParameter').click(function () {
	        	if ("" == positionsId) {
		                swal({
		                        title: "请选择要删除的职位",
		                        text: "请选中需要删除的职位，在使用删除操作。"
		                }); 
		             	return;
		        }
			    swal({
			        title: "您确定要删除该职务的参数信息吗",
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
								url: '${WEB_PATH }/system/positions/delParameter.do?positionsIds=' + positionsId,
								cache : false,
								type : 'POST',
								success : function(result) {
									var data = $.parseJSON(result);
									if(data.success == true){
										swal("删除成功！", data.msg, "success")
										getPositions(dept);
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
	
	function getPositions(obj){
		$.ajax({
			url: '${WEB_PATH }/system/positions/getPositions.do?deptId=' +obj.id ,
			cache : false,
			type : 'POST',
			success : function(result) {
				var data = $.parseJSON(result);
				if(data.length > 0){
					$("#tool_btn").show();
					$("#positions_jstree").jstree("destroy");
					$('#positions_jstree').jstree({
				        'core': {
				                'data': data
				        }
				    }).bind("select_node.jstree",function(event,positions_data){
				    	  if(positions_data.node.original.wkaoqin == null){
				    	  	 $('#addParameter').attr("disabled",false);
				    	  	 $('#editParameter').attr("disabled",true);
				    	  	 $('#delParameter').attr("disabled",true);
				    	  }else{
				    	  	 $('#addParameter').attr("disabled",true);
				    	  	 $('#editParameter').attr("disabled",false);
				    	  	 $('#delParameter').attr("disabled",false);
				    	  	 parameter_value = positions_data.node.original.parameter_value;
				    	  }
						  positionsId = positions_data.node.id;
						  positionsName = positions_data.node.text.substring(0,positions_data.node.text.lastIndexOf("--"));	
			        });
		        }else{
		        	$("#positions_jstree").jstree("destroy");
		        	$("#res_mesg").html('("'+obj.text+'"中没有职位信息，请重新选择！)');
		        	$("#no_data").show();
		        	$("#tool_btn").hide();
		        }
	             
			},
			error: function(result) {
				swal("数据加载失败，请稍后再试！", result.msg, "error")
			}
		});
	}
</script>
</body>

</html>