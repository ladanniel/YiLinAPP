<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>货车品牌</title>
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>货车品牌</h5>
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
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addTruckBrand">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="editTruckBrand" >
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="delTruckBrand">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
                                    </button> 
                                </div>
                                <table id="exampleTableEvents" data-height="630" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-checkbox="true"></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="name" >品牌名称</th>
								            <th data-field="first_letter" >品牌首字母</th>
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
	            url: "${WEB_PATH }/truck/truckBrand/getPage.do",
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
	        $('input.form-control.input-outline').attr("placeholder","请输入车辆品牌名称/首字母"); //设置搜索提示信息
	        //删除车辆类型
	        $('#delTruckBrand').click(function () {
	        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        	if(0 == selectRow.length){
				    layer.msg('请选择您要删除的货车品牌。', {icon: 5}); 
				    return ;
	        	}
	        	var truckBrandIds = "";
	        	for(var i=0;i<selectRow.length;i++){
	        		truckBrandIds +=  selectRow[i].id +",";
				}
			    swal({
			        title: "您确定要删除该货车品牌吗",
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
					  	type: 'GET',
					 	url:'${WEB_PATH }/truck/truckBrand/remove.do?ids=' + truckBrandIds,
					  	dataType: "json",
					 	 success:function(result){  
							swal("", result.msg, "success");							
							$("#exampleTableEvents").bootstrapTable('refresh');
						},
						error:function(data){
						 	var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						 }
					});
			        } else {
			            swal("已取消", "您取消了删除操作！", "error")
			        }
			    })
			});
        });
        
        
        
        
        
        
        
        
        //加载添加页面
        $('#addTruckBrand').click(function(){
        	$("#win_add").load("${WEB_PATH }/truck/truckBrand/view/add.do");
        });
        //加载修改页面
        $("#editTruckBrand").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    layer.msg('请选择您要修改的货车品牌，一次有且只有一个货车品牌。', {icon: 5}); 
				    return ;
	       	}
	       	var truckBrandInfo = selectRow[0];  
	       	$("#win_edit").load("${WEB_PATH }/truck/truckBrand/view/edit.do?id="+truckBrandInfo.id);
        });
        
        function formatStatus(val){
			if(val == '0'){
				return '<span class="label label-primary">启用</span>';
			}
			return '<span class="label label-danger">停用</span>';
		}
		
		function success(result) {
			var data = $.parseJSON(result);
			if(data.success == true){
				swal("删除成功！", "您已经永久删除了该资源。", "success")
				$("#resourceTableEvents").bootstrapTable('refresh');
			}else{
				swal("删除失败！", data.msg, "error")
			}
		}
    </script>
</body>

</html>