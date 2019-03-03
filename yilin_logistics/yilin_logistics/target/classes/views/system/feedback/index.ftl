<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>意见反馈</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>意见反馈</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <div class="example-wrap"> 
	                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
                                </div>
                                <table id="exampleTableEvents" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="title" >反馈标题</th>
		                                    <th data-field="account.account" >反馈用户账号</th>
		                                    <th data-field="account.phone">反馈用户手机号码</th>
		                                    <th data-field="account.name">反馈用户姓名</th>
		                                    <th data-field="type">反馈来源</th>
								            <th data-field="create_time" data-formatter="formatTime" >反馈时间</th>
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
	            url: "${WEB_PATH }/system/feedback/getPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:15,   
	            showRefresh:true, 
	            showColumns:true,
	            detailView:true,
	            detailFormatter:detailFormatter,
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
        
        function detailFormatter(index, row) {
		    return "<div style='text-align:center;line-height: 24px;'>反馈信息："+row.info+"</div>";
		}
		
		function formatTime(val){
    		return new Date(val).toLocaleString(); 
		}
		
    </script>
</body>

</html>