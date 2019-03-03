<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>App上传列表</title> 
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
                <h5>APP上传</h5>
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
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addCompanyAccount" >
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;上传app
                                    </button>
                                </div>
                                <table id="exampleTableEvents"  >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-radio="true"   ></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="create_time" data-formatter="formatTime" >上传时间</th> 
								            <th data-field="versionCode"   >版本号</th> 
								            <th data-field="versionName"   >版本名称</th> 
								            <th data-field="content"   >版本更新内容</th> 
								            <th data-field="path"   >apk路径</th> 
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
    
    <div id="company_win"></div>
    <@import.tableManagerImportScript/>
	<script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${WEB_PATH}/resources/js/city-picker.data.js"></script>
	<script src="${WEB_PATH}/resources/js/city-picker.js"></script>
	<script src="${WEB_PATH}/resources/js/ajaxfileupload.js"></script>
	<script src="${WEB_PATH}/resources/js/jquery-form.js"></script>
    <script >
    	Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
    	$(function(){
    		$("#roleSelect").chosen(); 
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
		    });
		    function queryParams(params) {
		        var temp = {  
			        limit: params.limit,  
			        offset: params.offset, 
			        maxrows: params.limit,
			        pageindex:params.pageNumber,
			        search:params.search
		        };
		        return temp;
			}
    		//加载table数据
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/system/appupload/getPage.do",
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
	            queryParams: queryParams,//参数
	            minimumCountColumns: 1, 
	            clickToSelect: true, 
	        });
	        
	        $('input.form-control.input-outline').attr("placeholder","请输入版本号"); //设置搜索提示信息
	        
        });
        
        //添加用户详情页面
        $("#addCompanyAccount").click(function(){
	       	$("#company_win").load("${WEB_PATH}/system/appupload/view/add.do");
        });
        
        
		
		 
		function success(result) {
			if(result.success == true){
				swal("保存成功！", result.msg, "success");
				$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				swal("保存失败！", result.msg, "error");
			}
		}
		
		
		function formatTime(val){
			if(val != null){
				var tt=new Date(val).Format("yyyy年MM月dd日");
    			return tt; 
			}else{
				sourceName = '<span class="label label-danger">无</span>';
				return sourceName;
			}
		 	
		}
		
		function readoFormatter(value, row, index) {
	        if(row.source == 1 && row.audit == "notapply"){
	        	return {
	                disabled: true
	            };
	        }
	        return value;
	    }
	    
		
    </script>
</body>

</html>