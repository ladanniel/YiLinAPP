<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>系统设置</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>系统设置</h5>
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
								            <th data-formatter="formatKey">参数名称</th> 
								            <th data-field="key">参数key</th>
								            <th data-field="value">参数值</th> 
								            <th data-field="description">参数描述</th>
								            <th data-field="create_time" data-formatter="formatTime">创建时间</th>
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
	            url: "${WEB_PATH }/system/parameter/getPage.do",
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
	        $('#del').click(function () {
	        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        	var info = selectRow[0]; 
	        	if(0 == selectRow.length){
				    layer.msg('<font color="red">温馨提示：请选择一条记录删除。</font>', {icon: 5}); 
				    return ;
	        	}
			    swal({
			        title: "您确定要删除该参数",
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
					   	  	 url:'${WEB_PATH }/system/parameter/delete.do?id=' + info.id,
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
        	$("#win_add").load("${WEB_PATH }/system/parameter/view/add.do");
        });
        //加载修改页面
        $("#edit").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条数据编辑。</font>', {icon: 5}); 
			    return ;
	       	}
	       	var info = selectRow[0];  
	       	$("#win_edit").load("${WEB_PATH }/system/parameter/view/edit.do?id="+info.id);
        });
		
		function success(result) {
			if(result.success == true){
				swal("删除成功！", "您已经永久删除来该参数。", "success")
				$("button[name='refresh']").trigger("click");
			}else{
				swal("删除失败！", "您已经永久删除了该参数。", "success")
			}
		}
		
        function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatKey(index, row, element){
			if(row.key == "driver"){
				return '<span class="label label-primary">司机</span>';
			}else if(row.key == "consignor"){
				return '<span class="label label-success">货主</span>';
			}else if(row.key == "withdrawal"){
				return '<span class="label label-default">提现有效期</span>';
			}
		}
    </script>
</body>

</html>