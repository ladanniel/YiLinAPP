<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>用户资金列表</title> 
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
                <h5>用户资金</h5>
                <div class="ibox-tools"> 
                    
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            
						                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
					                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="look-but" >
					                                        <i class="fa fa-file-text-o" aria-hidden="true"></i>&nbsp;&nbsp;查看记录
					                                    </button>
					                                    <a href="javascript:void(0);" class="btn btn-outline btn-default" data-toggle="modal" id="export-but" >
					                                        <i class="glyphicon glyphicon-export" aria-hidden="true"></i>&nbsp;&nbsp;导出Excel
					                                    </a>
					                                </div>
                                <table id="exampleTableEvents" data-height="700" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true"></th>  
		                                    <th data-field="id"  data-visible="false" >id</th>
								            <th data-field="account.account">用户账号</th>
								            <th data-field="account.phone">用户手机</th>
								            <th data-field="account.name">姓名</th>
								            <th data-field="total">总金额</th>
								            <th data-field="avaiable">可用余额</th>
								            <th data-field="frozen">冻结资金</th>
								            <th data-field="totalrecharge">总充值</th>
								            <th data-field="totalcash">总提现</th>
								            <th data-field="create_time"  data-formatter="formatTime" >创建日期</th>   
								            <th data-field="account.capitalStatus" data-formatter="formatStatus" >状态</th>   
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
	            url: "${WEB_PATH }/capital/account/getOsPage.do",
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
        
        //查看用户资金详情
        $('#look-but').click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条记录查看。</font>', {icon: 5}); 
			    return ;
	       	}
	       	$("#win_add").load("${WEB_PATH }/capital/account/view/look.do?id="+info.id);
        });
        
        //导出Excel
        $("#export-but").click(function(){
		    var search = $("input.form-control.input-outline").val();
        	location.href = "${WEB_PATH }/capital/account/export.do?search="+search;
        });
        
        function formatStatus(val){
			if(val == 'close'){
				return '<span class="label label-danger">冻结</span>';
			}else if(val == 'open'){
				return '<span class="label label-success">正常</span>';
			}
		}
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
    </script>
</body>

</html>