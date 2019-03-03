<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>联系人列表</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>联系人列表</h5>
                <div class="ibox-tools"> 
                    
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            
						                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
					                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="add-but" >
					                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;添加
									                    </button>
									                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="edit-but" >
									                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;编辑
									                    </button>
									                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="remove-but" >
									                        <i class="glyphicon glyphicon-remove" aria-hidden="true"></i>&nbsp;&nbsp;删除
									                    </button>
					                                </div>
                                <table id="exampleTableEvents" data-height="700" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true"></th>  
		                                    <th data-field="id"  data-visible="false" >id</th>
								            <th data-field="name">姓名</th>
								            <th data-field="mobile">手机号</th>
								            <th data-field="email">邮箱</th>
								            <th data-field="areaFullName">区域</th>
								            <th data-field="address">详细地址</th>
								            <th data-field="contactsType" data-formatter="formatType">联系人类型</th>
								            <th data-field="point" data-formatter="formatGis">查看联系人位置</th>
								            <th data-field="create_time"  data-formatter="formatTime" >创建日期</th>   
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
    <script src="${WEB_PATH }/resources/js/plugins/fancybox/jquery.fancybox.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/wizard/jquery.bootstrap.wizard.js"></script>
	<script src="${WEB_PATH}/resources/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=1AC8RPrl58FNLoYjoEhOiwv8SsgToWRm"></script>
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
	            url: "${WEB_PATH }/goods/contacts/getPage.do",
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
        
        $('#edit-but').click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条记录编辑。</font>', {icon: 5}); 
			    return ;
	       	}
	       	$("#win_add").load("${WEB_PATH }/goods/contacts/view/edit.do?id="+info.id);
        });
        
        $("#add-but").click(function(){
        		$("#win_add").load("${WEB_PATH }/goods/contacts/view/add.do");
        });
        
        $("#remove-but").click(function(){
        		var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        		var info = selectRow[0]; 
		        if(0 == selectRow.length){
					    layer.msg('<font color="red">温馨提示：请选择一条记录删除。</font>', {icon: 5}); 
					    return ;
		        }
				swal({
				        title: "您确定要删除该联系人吗",
				        text: "删除后将无法恢复，请谨慎操作！",
				        type: "warning",
				        showCancelButton: true,
				        confirmButtonColor: "#DD6B55",
				        confirmButtonText: "是的，我要删除！",
				        cancelButtonText: "我点错了...",
				        closeOnConfirm: false,
				        closeOnCancel: false
			    },
				function(isConfirm) {
				        if (isConfirm) {
							$.ajax({
								  type: 'POST',
								  url: '${WEB_PATH }/goods/contacts/remove.do',  
								  data: {"id":info.id},
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
					}else {
			            swal("已取消", "您取消了删除操作！", "error")
			        }
				});
		});
        
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatGis(val){
			var value = '<a href="javascript:void(0)" onclick="openGis('+val+')"><p class="fa green fa-map-marker">&nbsp;查看地图</p></a>';
        	return value;
		}
		
		function formatType(val){
			if(val == 'consignor'){
				return '<span class="label label-success">发货人</span>';
			}else{
				return '<span class="label label-primary">收货人</span>';
			}
        	return value;
		}
		
		function openGis(xpoint,ypoint){
			$("#win_add").load("${WEB_PATH }/goods/contacts/view/gis.do?point=" + xpoint + "," + ypoint);
		}
    </script>
</body>

</html>