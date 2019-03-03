<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>数据字典管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>转账类型</h5>
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
					                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="add" data-target="#userModal">
					                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
					                                    </button>
					                                    <button type="button" class="btn btn-outline btn-default" id="edit" >
					                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改
					                                    </button>
					                                     
					                                    <button type="button" class="btn btn-outline btn-default" id="del">
					                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
					                                    </button> 
					                                </div>
                                <table id="exampleTableEvents" data-height="700" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-radio="true"></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="name" >转账类型名称</th> 
								            <th data-field="companyType.name" >商户类型名称</th> 
								            <th data-field="create_time" data-formatter="formatTime">时间</th>
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
    		//加载table数据
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/transfertype/getPage.do",
	             method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:15,   
	            showRefresh:true, 
	            showColumns:true,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
	            sidePagination: "server", //设置为服务器端分页
	            searchText:"",   //设置搜索框文本初始值
	            search: true,    //隐藏搜索框
	            minimumCountColumns: 1,  
	            showToggle:false, 
	            clickToSelect: true,  
	            icons: {
	                refresh: "glyphicon-repeat", 
	                columns: "glyphicon-list"
	            }
	        });
	       $('input.form-control.input-outline').attr("placeholder","请输入转账类型名称"); //设置搜索提示信息
	        $('#del').click(function () {
	        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        	var info = selectRow[0]; 
	        	if(0 == selectRow.length){
				    layer.msg('<font color="red">温馨提示：请选择一条记录删除。</font>', {icon: 5}); 
				    return ;
	        	}
			    swal({
			        title: "您确定要删除该转账类型吗",
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
					   	  	 url:'${WEB_PATH }/system/transfertype/remove.do?id=' + info.id,
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
        $('#add').click(function(){
        	$("#win_add").load("${WEB_PATH }/system/transfertype/view/add.do");
        });
        //加载修改页面
        $("#edit").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    swal({
			        title: "选择修改转账类型操作错误",
			        text: "请选择一行您要修改的转账类型信息，一次有且只有一个转账类型信息。"
			    })
			    return ;
	       	}
	       	var transferType = selectRow[0];  
	       	$("#win_edit").load("${WEB_PATH }/system/transfertype/view/edit.do?id="+transferType.id);
        });
		
		function success(result) {
			if(result.success == true){
				swal("删除成功！", "您已经永久删除了该转账类型。", "success")
				$("button[name='refresh']").trigger("click");
			}else{
				swal("删除成功！", "您已经永久删除了该转账类型。", "success")
			}
		}
		
		//点击查询搜索
		$("#search").click(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
        function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
    </script>
</body>

</html>