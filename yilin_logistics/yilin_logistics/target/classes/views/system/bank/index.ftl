<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>银行管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>银行参数</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <div class="example-wrap"> 
	                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
	                            	<button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addInfo" >
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="editInfo" >
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" id="delInfo">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
                                    </button> 
                                </div>
                                <table id="exampleTableEvents" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true"></th> 
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="cnName" >银行中文全称</th>
		                                    <th data-field="shortName">银行英文缩写</th>
		                                    <th data-field="image" data-formatter="formatImage">银行logo图片</th>
		                                    <th data-field="markImage" data-formatter="formatMarkImage">银行标志图片</th>
								            <th data-field="create_time" data-formatter="formatTime" >创建时间</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Panel Other -->
    </div>
    <div id="win_add"></div>
    <@import.tableManagerImportScript/> 
    <script src="${WEB_PATH}/resources/js/ajaxfileupload.js"></script>
    <script >
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        search: $("input.form-control.input-outline").val(),
		        mylock:$("#lock").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
    		$("#close-lock-but").attr("disabled",true);
    		$("#query-all-but").attr("disabled",true);
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/bank/getPage.do",
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
	            queryParams: queryParams,//参数
	            minimumCountColumns: 1, 
	            search: true,    //隐藏搜索框
	            clickToSelect: true,  
	        });
	        $('input.form-control.input-outline').attr("placeholder","输入关键字搜索");
        });
		
		function formatTime(val){
			if(null == val || "" == val){
				return '<span class="label label-success">永久</span>';
			}
    		return new Date(val).toLocaleString(); 
		}
		
		function formatImage(val){
			return "<img src='"+val+"' style='width:50%px;height:50%'/>";
		}
		
		function formatMarkImage(val){
			return "<img src='"+val+"' style='width:25%px;height:25%'/>";
		}
		
		
		$("#addInfo").click(function(){
        	$("#win_add").load("${WEB_PATH }/system/bank/view/add.do");
		});
		
		$('#delInfo').click(function () {
	        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
		        if(1 != selectRow.length){
				    layer.msg('<font color="red">温馨提示：请选择一条数据进行操作。</font>', {icon: 5}); 
				    return ;
		       	}
			    swal({
			        title: "您确定要删除信息吗",
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
								url: '${WEB_PATH }/system/bank/remove.do?id=' + selectRow[0].id,
								cache : false,
								type : 'POST',
								success : function(result) {
									var data = $.parseJSON(result);
									$("#exampleTableEvents").bootstrapTable('refresh');
									swal("删除成功！", data.msg, "success")
								},
								error: function(result) {
									var data = $.parseJSON(result.responseText);
							        swal("删除失败！", data.msg, "erro")
								}
						});
			        } else {
			            swal("已取消", "您取消了删除操作！", "error")
			        }
			    })
		});
        
		
		$("#editInfo").click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条数据进行操作。</font>', {icon: 5}); 
			    return ;
	       	}
	       	var data = selectRow[0];  
	       	$("#win_add").load("${WEB_PATH }/system/bank/view/edit.do?id="+data.id);
        });
    </script>
</body>

</html>