<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>平台车辆</title>
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
    <style>
    	.demo {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		}
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
		.truckaxle {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    margin-bottom: 10px;
		}
		.truckaxle:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "轮轴属性：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.truckcontainer {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    margin-top: 10px;
		}
		.truckcontainer:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "货箱属性：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
    </style>
<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>平台车辆</h5>
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
                            		 	  <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>车牌号:</label>
			                                    <input id="track_no1" name="track_no" class="form-control" type="text" placeholder="请输入车牌号">
			                                </div>
			                            </div>
			                            <div class="col-sm-3"> 
			                                <div class="form-group">
			                                    <label>所属商户:</label>
			                                    <input id="companyName" name="companyName" class="form-control" type="text" placeholder="请输入商户名称">
								    		</div>
			                             </div> 
			                             <div class="col-sm-3"> 
			                                <div class="form-group">
			                                    <label>车辆类型:</label>
			                                    <select data-placeholder="选择车辆类型" id="trucktype_list1" name="truckType_id" class="chosen-select form-control" multiple>
								        		<#list trucktypeList as tt>
								        			<option value="${tt.id}">${tt.name}</option>
								        		</#list>
								    		</select>
			                                </div>
			                            </div>
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>车牌类型:</label>
		                                    	<select data-placeholder="请选择车牌类型" class="chosen-select form-control"  id="truckplate_list1" name="truckPlate_id" multiple>
								        		<#list truckplateList as tp>
								        			<option value="${tp.id}">${tp.name}</option>
								        		</#list>
								    		</select>
			                                </div>
			                            </div>
			                          </div> 
			                          <div class="row">
                            		 	  <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>车牌品牌:</label>
		                                    	<select data-placeholder="请选择车辆品牌" class="chosen-select form-control"  id="truckbrand_list1" name="truckBrand_id" multiple>
								        		<#list truckbrandList as tb>
								        			<option value="${tb.id}">${tb.name}</option>
								        		</#list>
								    			</select>
			                                </div>
			                            </div>
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>车辆识别码:</label>
			                                    <input id="track_read_no1" name="track_read_no" class="form-control" type="text" placeholder="请输入车牌识别码">
			                                </div>
			                            </div>
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>发动机编号:</label>
			                                    <input id="engine_no1" name="engine_no" class="form-control" type="text" placeholder="请输入发动机编号">
			                                </div>
			                            </div>
			                            <div class="col-sm-3" style="padding-top: 22px;">
			                                 <button type="button" class="btn btn-primary" id="search">查询</button>
			                            </div> 
			                        </div>
			                    </div>
                                <table id="exampleTableEvents" data-height="630" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-checkbox="true"></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="track_no" >车牌号</th>
								            <th data-field="company.name" >所属商户</th>
								            <th data-field="truckType.name" >车辆类型</th>
								            <th data-field="truckPlate.name" >车牌类型</th>
								            <th data-field="capacity" >车辆吨位</th>
								            <th data-field="truckBrand.name" >车辆品牌</th>
								            <th data-field="track_read_no" >车辆识别码</th>
								            <th data-field="engine_no" >发动机编号</th>
								            <th data-field="horsepower" >马力</th>
								            <th data-field="tag_time" data-formatter="formatTime">上牌时间</th>
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
		function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        track_no:$("#track_no1").val(),
		        companyName:$("#companyName").val(),
		        truckTypeIds:$("#trucktype_list1").val() != null ? $("#trucktype_list1").val().join(","):$("#trucktype_list1").val(),
		        truckPlateIds:$("#truckplate_list1").val() != null ? $("#truckplate_list1").val().join(","):$("#truckplate_list1").val(),
		        truckBrandIds:$("#truckbrand_list1").val() != null ? $("#truckbrand_list1").val().join(","):$("#truckbrand_list1").val(),
		        track_read_no:$("#track_read_no1").val(),
		        engine_no:$("#engine_no1").val(),
		        search: $("input.form-control.input-outline").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	$(function(){
    		$("#trucktype_list1").chosen();//车辆类型下拉框搜索
   			$("#truckplate_list1").chosen();//车牌类型下拉框搜索
   			$("#truckbrand_list1").chosen();//车辆品牌下拉框搜索
   			
    		//加载table数据
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/truck/systruck/getTruckPageByYilin.do",
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
	        //点击查询搜索
			$("#search").click(function(){
				$("#exampleTableEvents").bootstrapTable('refresh');
			});
	        $('input.form-control.input-outline').attr("placeholder","请输入车牌号/车辆识别码/发动机编号").attr("size",30); //设置搜索提示信息
	       
        
        });
        
        function formatStatus(val){
			if(val == '0'){
				return '<span class="label label-primary">启用</span>';
			}
			return '<span class="label label-danger">停用</span>';
		}
		
		function detailFormatter(index, row) {
        	var html = ""; 
        	$.ajax({
	            url:"${WEB_PATH}/truck/systruck/getTruckDetail.do?id="+row.id,
	            type:'get',
	            dataType:'html',
	            cache:false, 
 				async:false, 
	            success:function(data){
	                 html = data
	            }
	        });
		    return html;
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
    </script>
</body>

</html>