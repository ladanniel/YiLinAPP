<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>车辆信息管理</title>
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
		    content: "车辆信息：";
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
                <h5>我的车辆</h5>
                <div class="ibox-tools">
                </div>
            </div>
            
            <div class="ibox-content">
            	<div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
            		<#if truck??>
                     <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="addTruck">
                          <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;&nbsp;新增
                     </button>
                    </#if>
                     <button type="button" class="btn btn-outline btn-default" id="editTruck" >
                     <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>&nbsp;&nbsp;修改
                     </button>
                     <button type="button" class="btn btn-outline btn-default" id="delTruck">
                     <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
                     </button> 
                     <input type="hidden" id="truckid" value="${truck.id}">
                     <button type="button" class="btn btn-outline btn-default"  id="track_axle" >
                     <i class="fa fa-truck" aria-hidden="true"></i>&nbsp;&nbsp;轮轴属性
                     </button>
                     <button type="button" class="btn btn-outline btn-default"  id="container" >
                     <i class="fa fa-truck" aria-hidden="true"></i>&nbsp;&nbsp;货箱属性
                     </button>
                </div>
                <div class="row row-lg" style="height:400px;">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div class="example"> 
                            <div style="" class="demo ui-sortable">
                            				<#if null == truck>
                            				<div id="tab-1" class="tab-pane active">
								                    <div class="ibox-content">
								                    	<div class="row" style="font-size: 14px;height:200px;">
									                       <p align="center" style="padding:100px;"><i class="fa green fa-truck"></i>&nbsp;<font color="red"><strong>您尚未绑定车辆！</strong></font></p>
							                            </div>
							                        </div>
								                </div>
                            				<#else>
                            				<div id="tab-1" class="tab-pane active">
								                    <div class="ibox-content">
								                    	<div class="row" style="font-size: 14px;">
									                        <div  class="col-sm-7" style="padding-left: 3%;">
									                            <p><i class="fa green fa-truck"></i>&nbsp;<strong>车牌号码：</strong><span>${truck.track_no}</span></p>
									                            <p><i class="fa green fa-user"></i>&nbsp;&nbsp;<strong>所属商户：</strong><span>${truck.company.name}</span></p>
									                            <p><i class="fa green fa-book"></i>&nbsp;<strong>车辆类型：</strong><span>${truck.truckType.name}</span></p>
								                            </div>
								                            <div  class="col-sm-3">
								                             	<p><i class="fa green fa-object-group"></i>&nbsp;<strong>车牌类型：</strong><span>${truck.truckPlate.name}</span></p>
									                            <p><i class="fa green fa-cc"></i>&nbsp;<strong>车辆品牌：</strong><span>${truck.truckBrand.name}</span></p>
									                            <p><i class="fa green fa-arrows-h"></i>&nbsp;<strong>车辆长度：</strong><span>${truck.track_long}米</span></p>
								                            </div>
							                            </div>
							                            
								                    	<div class="row" style="font-size: 14px;">
									                        <div  class="col-sm-7" style="padding-left: 3%;">
									                        	<p><i class="fa green fa-balance-scale"></i><strong>车辆载重：</strong><span>${truck.capacity}吨</span></p>
									                            <p><i class="fa green fa-barcode"></i>&nbsp;<strong>车辆识别码：</strong><span>${truck.track_read_no}</span></p>
									                            <p><i class="fa green fa-cc"></i>&nbsp;<strong>发动机品牌：</strong><span>${truck.engineBrand.name}</span></p>
								                            </div>
								                            <div  class="col-sm-3">
								                            	<p><i class="fa green fa-tasks"></i>&nbsp;<strong>发动机编号：</strong><span>${truck.engine_no}</span></p>
									                            <p><i class="fa green fa-gears"></i>&nbsp;<strong>发动机马力：</strong><span>${truck.horsepower}</span></p>
									                            <p><i class="fa green fa-calendar-check-o"></i>&nbsp;<strong>上牌时间：</strong><span>${truck.tag_time}</span></p>
								                            </div>
							                            </div>
								                    	
							                        </div>
								                </div>
                            				</#if>
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
	    	$("#aa-1").bootstrapTable({
	            url: "${WEB_PATH }/truck/systruck/getTruckByAccountno.do",
	           
	        });
	        //删除
	        $('#delTruck').click(function () {
	        	var truckid=$("#truckid").val();
	        	if(truckid==""){
				     layer.msg('没有可删除的车辆信息。', {icon: 5}); 
				    return ;
	        	}
			    swal({
			        title: "您确定要删除该车辆信息吗",
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
					  	type: 'GET',
					 	url:'${WEB_PATH }/truck/systruck/remove.do?ids=' + truckid,
					  	dataType: "json",
					 	 success:function(result){  
					 	 	var mytitle="";
					 	 	var mytype="";
					 	 	if(result.success == true){
					 	 		mytitle="操作成功";
					 	 		mytype="success";
					 	 	}else{
					 	 		mytitle="操作失败";
					 	 		mytype="error";
					 	 	}
					 	 	swal({
						        title:mytitle,
						        text:result.msg ,
						        type: mytype,
						        confirmButtonColor: "#DD6B55",
						        confirmButtonText: "确定",
						    },
						    function(isCon){
						    	if(isCon){
						    		window.location="${WEB_PATH }/truck/systruck/getTruckByAccountno.do";
						    	}
						    })
						},
						error:function(data){
						 	var data = $.parseJSON(data.responseText);
						 	swal({
			       	 		title: "操作失败",
			       	 		text: data.msg
		    				});
					        //swal("", data.msg, "error");
						 }
					});
			        } else {
			            swal("已取消", "您取消了删除操作！", "error")
			        }
			    })
			});
        });
        
        //加载添加页面
        $('#addTruck').click(function(){
        	$("#win_add").load("${WEB_PATH }/truck/systruck/view/add.do");
        });
        
        //加载轮轴属性页面
        $("#track_axle").click(function(){
        	var truckid=$("#truckid").val();
	        	if(truckid==""){
				     layer.msg('车辆信息存在，无法查看轮轴属性。', {icon: 5}); 
				    return ;
	        	}
	       	$("#win_edit").load("${WEB_PATH }/truck/systruck/view/track_axle.do?id="+truckid);
        });
        
         //加载货箱属性页面
        $("#container").click(function(){
        	var truckid=$("#truckid").val();
	        	if(truckid==""){
				     layer.msg('车辆信息存在，无法查看货箱属性。', {icon: 5}); 
				    return ;
	        	}
	       	$("#win_edit").load("${WEB_PATH }/truck/systruck/view/container.do?id="+truckid);
        });
        
        //加载修改页面
        $("#editTruck").click(function(){
        	var truckid=$("#truckid").val();
	        	if(truckid==""){
				     layer.msg('没有可修改的车辆信息。', {icon: 5}); 
				    return ;
	        	}
	       	$("#win_edit").load("${WEB_PATH }/truck/systruck/view/edit.do?id="+truckid);
        });
        
        function formatStatus(val){
			if(val == '0'){
				return '<span class="label label-primary">启用</span>';
			}
			return '<span class="label label-danger">停用</span>';
		}
		
		function success(result) {
			var data = $.parseJSON(result);
			if(data.success == true){
				swal("删除成功！", "您已经永久删除了该资源。", "success")
				$("#resourceTableEvents").bootstrapTable('refresh');
			}else{
				swal("删除失败！", data.msg, "error")
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
    </script>
</body>
</html>