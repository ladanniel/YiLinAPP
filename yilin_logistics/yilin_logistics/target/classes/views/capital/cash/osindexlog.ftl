<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>提现记录</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <style>
    	.demo:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "条件查询：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.demo {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		}
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>提现日志</h5>
                <div class="ibox-tools"> 
                    
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div class="example"> 
						                    <div style="" class="demo ui-sortable">
				                            		 <div class="row">
							                            <div class="col-sm-3" style="margin-left:5px;">
							                                <label>关键字：</label>
							                                <input class="form-control" type="text" placeholder="输入关键字搜索" name="search_val" id="search_val"/>
							                            </div>
							                            <div class="col-sm-4">
							                                <label>审核处理时间范围：</label>
							                                <div id="data-two">
							                                    <div class="input-daterange input-group" id="datepicker">
									                                <input type="text" class="input-sm form-control" name="tstart" id="start-two" value="" placeholder="选择开始时间"/>
									                                <span class="input-group-addon">到</span>
									                                <input type="text" class="input-sm form-control" name="tend" id="end-two" value="" placeholder="选择结束时间"/>
									                            </div>
							                                </div>
							                            </div>
							                            <div class="col-sm-3">
							                                <div class="form-group">
							                                    <label>状态：</label>
							                                    <select class="form-control" name="sstatus" id="sstatus" placeholder="选择状态搜索">
							                                    	  <option value="">全部</option>
										                              <option value="success">通过</option> 
										                              <option value="failed">未通过</option>
															 	</select>
							                                </div>
							                            </div>
							                             <div class="col-sm-1" style="padding-top: 22px;">
							                                 <button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							                            </div>
							                        </div>
							                    </div>
							                    </div>
						                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
					                                    <a href="javascript:void(0);" class="btn btn-outline btn-default" data-toggle="modal" id="export-but" >
					                                        <i class="glyphicon glyphicon-export" aria-hidden="true"></i>&nbsp;&nbsp;导出Excel
					                                    </a>
					                                </div>
						                    </div>
                                <table id="exampleTableEvents" data-height="520" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true"></th>  
		                                    <th data-field="id"  data-visible="false" >id</th>
								            <th data-field="tradeNo"  data-visible="false">提现编号</th> 
								            <th data-field="account.phone" >手机账号</th>
								            <th data-field="account.name" >姓名</th> 
								            <th data-field="bankname"  >提现银行</th>
								            <th data-field="bankcard">银行卡号</th>
								            <th data-field="openbank">开户行名称</th>
								            <th data-field="money">提现金额</th>
								            <th data-field="actualMoney">实际到账金额</th>
								            <th data-field="verifytPeopson">审核人</th>
								            <th data-field="create_time" data-formatter="formatTime">提现申请时间</th>
								            <th data-field="verifytime" data-formatter="formatTime">提现处理时间</th>
								            <th data-field="status" data-formatter="formatStatus" >状态</th>   
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
    <div id="win_verify"></div>
    
    <@import.tableManagerImportScript/> 
    <script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/switchery/switchery.js"></script>
    <script >
	    $("#data-two .input-daterange").datepicker({
	        keyboardNavigation: !1,
	        forceParse: !1,
	        autoclose: !0
	    }); {
	        var i = document.querySelector(".js-switch"),
	        t = (new Switchery(i, {
	            color: "#1AB394"
	        }), document.querySelector(".js-switch_2")),
	        a = (new Switchery(t, {
	            color: "#ED5565"
	        }), document.querySelector(".js-switch_3"));
	        new Switchery(a, {
	            color: "#1AB394"
	        })
	    } 
	    $("#start-two").change(function(){
	    	var end = $("#end-two").val();
	    	var start = $("#start-two").val();
	    	if(end == null || "" == end){
	    		$("#end-two").val(start);
	    	}else{
	    		var end1 = end.replace("-","");
	    		var start1 = start.replace("-","");
	    		if(end1 < start1){
	    			$("#end-two").val(start);
	    		}
	    	}
	    });
	    $("#end-two").change(function(){
	    	var end = $("#end-two").val();
	    	var start = $("#start-two").val();
	    	if(start == null || "" == start){
	    		$("#start-two").val(end);
	    	}else{
	    		var end1 = end.replace("-","");
	    		var start1 = start.replace("-","");
	    		if(end1 < start1){
	    			$("#start-two").val(end);
	    		}
	    	}
	    });
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        startTwo: $("#start-two").val(),
		        endTwo: $("#end-two").val(),
		        status: $("#sstatus option:selected").val(),
		        search: $("#search_val").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/capital/cashapplication/getOsLogPage.do",
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
	            search: true,    //隐藏搜索框
	            clickToSelect: true, 
	        });
	        $("#search").click(function(){
				$("#exampleTableEvents").bootstrapTable('refresh');
			});
	        $('input.form-control.input-outline').hide();
	        
	        function detailFormatter(index, row) {
	        	console.log(row);
	        	var remark = row.remark;
	        	if(null == remark || "" == remark){
	        		remark = "无";
	        	}
	        	var html = "<div style='text-align:center'>备注信息："+remark+"</div>"; 
			    return html;
			}
        });
        
        //导出Excel
        $("#export-but").click(function(){
		    var search = $("#search_val").val();
		    var startTwo = $("#start-two").val();
		    var endTwo = $("#end-two").val();
		    var status = $("#sstatus option:selected").val();
        	location.href = "${WEB_PATH }/capital/cashapplication/export.do?search="+search+"&startTwo="+startTwo+"&endTwo="+endTwo+"&status="+status+"&type=log";
        });
        
        //选择刷新
		$("#sstatus").change(function(){
			$("#exampleTableEvents").bootstrapTable('refresh');
		});
        
        function formatStatus(val){
			if(val == 'failed'){
				return '<span class="label label-danger">未通过</span>';
			}else if(val == 'success'){
				return '<span class="label label-success">通过</span>';
			}else if(val == "lock"){
				return '<span class="label label-primary">锁定</span>';
			}else{
				return '<span class="label label-default">待处理</span>';
			}
			
		}
		
		function formatTime(val){
			if(null == val || "" == val){
				return "无"; 
			}
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
    </script>
</body>

</html>