<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>短信接口</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>短信接口</h5>
                <div class="ibox-tools"> 
                    
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div class="example">
                            	<div class="btn-group" id="exampleTableEventsToolbar" role="group">
                            			<button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="add-but" >
					                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;添加接口
					                    </button>
					                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="edit-but" >
					                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;编辑接口
					                    </button>
					                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="look-but" >
					                                        <i class="fa fa-file-text-o" aria-hidden="true"></i>&nbsp;&nbsp;查看详情
					                                    </button>
					                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="send-but" >
					                                        <i class="fa fa-file-text-o" aria-hidden="true"></i>&nbsp;&nbsp;测试接口
					                                    </button>
					                                </div>
                                <table id="exampleTableEvents" data-height="700" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true"></th>  
		                                    <th data-field="id"  data-visible="false" >id</th>
								            <th data-field="name" >短信接口名称</th> 
								            <th data-field="sendUrl">发送短信URL</th>
								            <th data-field="sendSuccess">成功表达式</th>
								            <th data-field="ownedBusiness" >短信提供商</th>
								            <th data-field="create_time"  data-formatter="formatTime" >创建日期</th>   
								            <th data-field="status" data-formatter="formatStatus">启用状态</th>   
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
    <@import.tableManagerImportScript/> 
    <script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/switchery/switchery.js"></script>
    <script >
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        search: $("input.form-control.input-outline").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/sendmessage/getPage.do",
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
	            searchText:"",   //设置搜索框文本初始值
	            minimumCountColumns: 1,  
	            showToggle:false, 
	            clickToSelect: true,  
	            icons: {
	                refresh: "glyphicon-repeat", 
	                columns: "glyphicon-list"
	            }
	        });
	        $('input.form-control.input-outline').attr("placeholder","输入关键字搜索");
        });
        
        //选择刷新
		$("#sstatus").change(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
        
        //查看提现记录
        $('#look-but').click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条记录查看。</font>', {icon: 5}); 
			    return ;
	       	}
	       	$("#win_add").load("${WEB_PATH }/system/sendmessage/view/look.do?id="+info.id);
        });
        
        //加载添加页面
        $('#add-but').click(function(){
        	$("#win_add").load("${WEB_PATH }/system/sendmessage/view/add.do");
        });
        
        //加载编辑页面
        $('#edit-but').click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条记录编辑。</font>', {icon: 5}); 
			    return ;
	       	}
	       	$("#win_add").load("${WEB_PATH }/system/sendmessage/view/edit.do?id="+info.id);
        });
        
        $("#send-but").click(function(){
        	$.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/sendmessage/send.do',  
					  data: {},
					  dataType: "json",
					  success:function(result){  
							swal("", result.msg, "success");
						},
						error:function(data){
						 	var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						 }
					});
        });
        
        function formatStatus(val){
			if(val == 'notenabled'){
				return '<span class="label label-danger">禁用</span>';
			}else if(val == 'enabled'){
				return '<span class="label label-success">启用</span>';
			}
			
		}
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
    </script>
</body>

</html>