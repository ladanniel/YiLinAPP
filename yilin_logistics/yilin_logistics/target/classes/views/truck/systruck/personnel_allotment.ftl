<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>车辆信息管理</title>
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
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
 <link href="${WEB_PATH}/resources/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${WEB_PATH}/resources/css/plugins/switchery/switchery.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>人员分配</h5> <span style="color: red;" id="msg_load"><h5 style="padding-left:30px;">请添加车辆和员工信息后，才能进行人员分配 </h5></span>
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
			                          </div> 
			                          <div class="row">
                            		 	  
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
			                            <div class="col-sm-6" style="padding-top: 22px;">
			                                 <button type="button" class="btn btn-primary" id="search">查询</button>
			                            </div> 
			                        </div>
			                    </div>
                                <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="btn-chain-broken">
                                        <i class="fa fa-chain-broken" aria-hidden="true"></i>&nbsp;&nbsp;解除绑定
                                    </button>
                                </div>
                                <table id="exampleTableEvents" data-height="630" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                            <th data-field="state" data-radio="true"></th>  
                                            <th data-field="id" data-visible = "false" >id</th>  
								            <th data-field="track_no" >车牌号</th>
								            <th data-field="truckStatus"  data-formatter="formatTruckStatus">车辆状态</th>
								            <th data-field="accountId"  data-formatter="formatAccount">司机人员</th>
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
    <script src="${WEB_PATH }/resources/js/plugins/fancybox/jquery.fancybox.js"></script>
	<script src="${WEB_PATH}/resources/js/plate-picker.data.js"></script>
	<script src="${WEB_PATH}/resources/js/plate-picker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/wizard/jquery.bootstrap.wizard.js"></script>
	<script src="${WEB_PATH}/resources/js/ajaxfileupload.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/cropper/cropper.min.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/switchery/switchery.js"></script>
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
	            url: "${WEB_PATH }/truck/systruck/getTruckPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
	            showRefresh:true, 
	            showColumns:true,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
	            detailView:true,
	            onCheck:onClickContacts,
	            sidePagination: "server", //设置为服务器端分页
	            queryParams: queryParams,//参数
	            minimumCountColumns: 1, 
	            search: true,    //隐藏搜索框
	            clickToSelect: true, 
	            onExpandRow:subTableFormatter
	        });
	        //点击查询搜索
			$("#search").click(function(){
				$("#exampleTableEvents").bootstrapTable('refresh');
			});
	        $('input.form-control.input-outline').attr("placeholder","请输入车牌号/车辆识别码/发动机编号").attr("size",30); //设置搜索提示信息
	        
	        
	        $('#btn-chain-broken').click(function(){
	        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
	        	var info = selectRow[0]; 
		        if(1 != selectRow.length){
				    layer.msg('<font color="red">温馨提示：请选择你需要解除绑定的车辆。</font>', {icon: 5}); 
				    return ;
		       	}
		       	layer.open({
				   content: '<font color="#ed5565;">您确定要把【'+info.account.name+'】解除绑定？</font>',
				   icon: 2,
				   btn: ['确定', '取消'],
				   yes: function(index, layero){
				  		 $.yilinAjax({
					   	  	 type:'POST',
					   	  	 url:'${WEB_PATH }/truck/systruck/updateChainBroken.do?trackId='+ info.id, 
					   	  	 data:null,
		            		 errorcallback:null,
		            		 successcallback:successChainBroken
				   	     });
				   } 
				});
	        });
        });
        
        function subTableFormatter(index, row, $detail){
			 var cur_table = $detail.html('<div><div id="info"></div><table></table></div>').find('table');
			 $(cur_table).bootstrapTable({
		            url: "${WEB_PATH}/truck/truckDistribution/getPage.do",
		            method: 'get',
		            queryParams:function(params){
		              var temp = {  
					        limit: params.limit,  
					        offset: params.offset,
					        maxrows: params.limit,
					        pageindex:params.pageNumber,
					        trackId: row.id
				        };
				        return temp;
		            },
		            clickToSelect: true,
		            uniqueId: "robOrderId",
		            pagination:false,
	                pageNumber:1,  
	                pageSize:10,   
		            sidePagination: "server", //设置为服务器端分页
		            pageSize: 10,
		            pageList: [10, 25], 
		            columns: [{
		                field: 'distributionUserName',
		                title: '司机姓名'
		            }, {
		                field: 'distributionDate',
		                title: '分配时间',
		                formatter:function(val){
		                	var tt=new Date(val).Format("yyyy年MM月dd日  hh时mm分ss秒");
    						return tt;  
		                }
		            }]
	          });
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
		function formatTruckStatus(index, row, element){
			if(row.truckStatus == 'notransit'){
				return '<span class="label label-primary">未运输</span>';
			}else if(row.truckStatus == "transit"){
				return '<span class="label label-danger">运输中</span>';
			}else if(row.truckStatus == "noauth"){
				return '<span class="label label-warning" id="'+row.id+'">审核未通过</span>';
			} 
		}
		
		function formatAccount(index, row, element){
			var id = "'"+row.id+"'";
			var element = "'"+element+"'";
			if(row.account == null){
				return '<a href="javascript:personnelAllotment('+id+')" id="'+row.id+'" data-name="weight" data-type="text" data-value="" data-source="undefined" class="editable editable-click editable-empty"><font color="red">未绑定人员</font></a>';
			}else{
				return '<a href="javascript:personnelAllotment('+id+')" id="'+row.id+'" data-name="weight" data-type="text" data-value="" data-source="undefined" class="editable editable-click editable-empty"><font color="#1ab394">'+row.account.name+'</font></a>';
			}
		}
		
		function personnelAllotment(id){
			var row_id = "#"+id;
			var row = $("#exampleTableEvents").bootstrapTable('getSelections')[0];
			if(row.truckStatus == "transit"){
				layer.msg('车辆['+row.track_no+']，已在运输中，不能更换司机！', {icon: 5}); 
			}else{
				layer.open({
			      type: 2,
			      title: '<span style="color: #ed5565"><i class="fa fa-pencil"></i>&nbsp;&nbsp;车辆【'+row.track_no+'】人员分配</span>',
			      shadeClose: true,
			      shade: 0.1,
			      maxmin: false, //开启最大化最小化按钮
			      area: ['50%', '50%'],
			      content: '${WEB_PATH}/truck/systruck/confirmPerson.do?id='+id,
			      btn: ['<i class="fa fa-check"></i>&nbsp;确认','取消'],
			      yes: function(index, layero){
				      var body = layer.getChildFrame('body', index);
				      var iframeWin = window[layero.find('iframe')[0]['name']];
				      iframeWin.confirmPerson();
				  } 
			    });
		    }
		}
		
		function onClickContacts(index, row, element){
			if(index.account == null || index.truckStatus == "transit"){
				$("#btn-chain-broken").attr("disabled",true); 
			}else{
				$("#btn-chain-broken").attr("disabled",false); 
			}
		}
		
		function successChainBroken(result) {
			layer.closeAll();
			if(result.success == true){
				swal("解绑成功！", result.msg, "success");
				$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				swal("解绑失败！", result.msg, "error");
				$("#exampleTableEvents").bootstrapTable('refresh');
			}
		}
    </script>
</body>

</html>