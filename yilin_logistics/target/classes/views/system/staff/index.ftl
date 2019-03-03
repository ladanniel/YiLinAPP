<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>用户详情管理</title> 
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
                <h5>用户详情</h5>
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
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;添加用户详情
                                    </button>
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="updateStaff" >
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改用户详情
                                    </button>
                                </div>
                                <table id="exampleTableEvents"  >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-radio="true"   ></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="name" >姓名</th> 
								            <th data-field="age"   >年龄</th> 
								            <th data-field="birthday"  data-formatter="formatTime" >出生日期</th> 
								            <th data-field="sex" data-formatter="formatterStaffSex" >性别</th> 
								            <th data-field="nation" >民族</th> 
								            <th data-field="origin" >籍贯</th> 
								            <th data-field="education.name" >学历</th> 
								            <th data-field="major" >专业</th> 
								            <th data-field="graduate_school" >毕业学校</th> 
								            <th data-field="email" >邮箱</th> 
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
	            url: "${WEB_PATH }/system/staff/getPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
	            showRefresh:true, 
	            showColumns:true,
	            detailView:true,
	            detailFormatter:detailFormatter,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
	            sidePagination: "server", //设置为服务器端分页 
	            queryParams: queryParams,//参数
	            minimumCountColumns: 1, 
	            clickToSelect: true
	        });
	        
	        $('input.form-control.input-outline').attr("placeholder","请输入名称"); //设置搜索提示信息
	        
        });
        
        //添加用户详情页面
        $("#addCompanyAccount").click(function(){
	       	$("#company_win").load("${WEB_PATH}/system/staff/view/add.do");
        });
        $("#updateStaff").click(function(){
        var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var user = selectRow[0];  
	        if(1 != selectRow.length){
			    layer.msg('请选择一行用户信息，一次有且只有一个商户信息。', {icon: 5}); 
			    return ;
	       	}
	       	$("#company_win").load("${WEB_PATH}/system/staff/view/update.do?id="+user.id);
        });
        
       	function detailFormatter(index, row) {
        	var html = ""; 
        	$.ajax({
	            url:"${WEB_PATH}/system/staff/view/infodetail.do?id="+row.id,
	            type:'get',
	            dataType:'html',
	            cache:false, 
 				async:false, 
	            success:function(data){
	                 html =    data
	            }
	        });
		    return html;
		}
        
		
		 
		function success(result) {
			if(result.success == true){
				swal("保存成功！", result.msg, "success");
				$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				swal("保存失败！", result.msg, "error");
			}
		}
		
		function formatterStaffSex(val){
			var name = "";
			if(val == "male"){
				name = '<span class="label label-info">&nbsp;&nbsp;男&nbsp;&nbsp;</span>';
			}else{
				name = '<span class="label label-primary">&nbsp;&nbsp;女&nbsp;&nbsp;</span>';
			}
        	return name;
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