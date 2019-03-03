<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>发货管理</title> 
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
    	.goods:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "货物详细信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.goods {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    margin-top: 10px;
		} 
    	.consignor:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "联系人信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.consignor {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    margin-top: 10px;
		}
		.input-group-btn:last-child>.btn, .input-group-btn:last-child>.btn-group {
	    z-index: 2;
	    margin-left: -1px;
	    margin-top: 23px;
	    }
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>平台货物</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <div class="example-wrap"> 
                                <iframe name="right" id="rightMain" src="${WEB_PATH }/goods/goodsbasic/goodsCount.do" frameborder="false"  scrolling="no"  style="overflow: scroll;" width="100%" height="100%"  ></iframe>
                        		<div style="" class="demo ui-sortable">
                            		 <div class="row" style="margin-bottom: 10px;"> 
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>发货地区：</label>
			                                    <input id="deliveryAreaName" name="deliveryAreaName" class="form-control" readonly type="text" value="">
												<input id="deliveryAreaId" name="deliveryAreaId" type="hidden" >
			                                </div>
			                            </div> 
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                    <label>收货地区：</label>
			                                    <input id="consigneeAreaName" name="consigneeAreaName" class="form-control" readonly type="text" value="">
												<input id="consigneeAreaId" name="consigneeAreaId" type="hidden" >
			                                </div>
			                            </div> 
			                            <div class="col-sm-3">
			                                <div class="form-group">
			                                     <label><span style="color: red;">*&nbsp;&nbsp;</span>货物类型：</label>
				                                 <select data-placeholder="选择货物类型" id="goodsType_id" name="goodsType_id" class="chosen-select form-control" multiple>
				                                	<#list goodsTypeList as goodsType >
				                                		<option value="${goodsType.id}" hassubinfo="true">${goodsType.name}</option>
				                                 	</#list>
				                                 </select>
			                                </div>
			                            </div> 
			                            <div class="col-sm-2"> 
			                                <div class="form-group">
			                                    <label>是否上线：</label>
			                                    <select data-placeholder="选择上线状态" id="onLine" class="chosen-select form-control"  >
			                                    	<option value="0" hassubinfo="true" selected = "selected">全部</option>
			                                   		<option value="true" hassubinfo="true"  >上线</option>
				                                    <option value="false" hassubinfo="true"  >下线</option>
				                                </select>
			                                </div>
			                            </div>
			                            <div class="col-sm-1" style="padding-top: 22px;">
			                                 <button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
			                            </div> 
			                        </div>
			                        <div class="row">
			                             <div class="col-sm-4"> 
			                                <div class="form-group">
			                                    <label>状态：</label>
			                                    <select data-placeholder="选择认证状态" id="status" class="chosen-select form-control" multiple   >
				                                    <option value="apply" hassubinfo="true" selected = "selected">申请发货</option>
				                                    <option value="lock" hassubinfo="true" selected = "selected">正在处理</option>
				                                    <option value="back" hassubinfo="true" selected = "selected">退回</option>
				                                    <option value="success" hassubinfo="true" selected = "selected">通过</option> 
				                                </select>
			                                </div>
			                            </div>
			                            <div class="col-sm-4">
			                               <div class="input-group">
				                                <label>商户：</label>
						                        <input type="text" class="form-control" id="company" name="company">
						                        <div class="input-group-btn">
						                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
						                                <span class="caret"></span>
						                            </button>
						                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
						                            </ul>
						                        </div>
					                    	</div>
			                            </div>
			                        </div>
			                    </div>
	                            <div class="btn-group" id="exampleTableEventsToolbar" role="group">
				                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="up-but" >
				                        <font color="#1ab394"><i class="fa fa-arrow-up" aria-hidden="true"></i>&nbsp;&nbsp;上线</font>
				                    </button>
				                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="down-but" >
				                        <font color="red"><i class="fa fa-arrow-down" aria-hidden="true"></i>&nbsp;&nbsp;下线</font>
				                    </button>
                                </div>
                                <table id="exampleTableEvents" data-height="700" data-mobile-responsive="true" >
                                    <thead>
                                        <tr>
                                        	<th data-field="state" data-radio="true"></th>  
		                                    <th data-field="id"  data-visible="false" >id</th>
		                                    <th data-field="deliveryNo" >发货单号</th>
								            <th data-field="deliveryAreaName" data-formatter="formatAreaName">路线(出发地/目的地)</th>
								            <th data-field="companyName">发货商户</th>
								            <th data-field="status" data-formatter="formatStatus" >状态</th>
								            <th data-field="onLine" data-formatter="formatOnLine" >是否上线</th>
								            <th data-field="goodsType" data-formatter="formatGoodsType">货物类型</th>
								            <th data-field="unitPrice" data-formatter="formatMoney">单价</th>
								            <th data-field="totalPrice" data-formatter="formatMoney">总价</th>
								            <th data-field="totalWeight" data-formatter="formatTotalWeight">总重量/吨</th>
								            <th data-field="surplusWeight" data-formatter="formatSurplusWeight">剩余重量/吨</th>
								            <th data-field="releaseTime" data-formatter="formatTime" >发布时间</th>
								            <th data-field="finiteTime"  data-formatter="formatFiniteTime" >截至时间</th>   
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
    <script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/switchery/switchery.js"></script>
    <script src="${WEB_PATH}/resources/js/city-picker.data.js"></script>
	<script src="${WEB_PATH}/resources/js/city-picker.js"></script>
     <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=1AC8RPrl58FNLoYjoEhOiwv8SsgToWRm"></script>
    <script >
    	function queryParams(params) {
	        var temp = {  
		        limit: params.limit,  
		        offset: params.offset, 
		        search: $("input.form-control.input-outline").val(),
		        deliveryAreaName:$("#deliveryAreaName").val(),
		        consigneeAreaName:$("#consigneeAreaName").val(),
		        companyName:$("#company").val(),
		        goodsTypeId:$("#goodsType_id").val() != null ? $("#goodsType_id").val().join(","):$("#goodsType_id").val(),
		        status_serch:$("#status").val() != null ? $("#status").val().join(","):$("#status").val(),
		        onLine:$("#onLine").val() == 0 ? null:$("#onLine").val(),
		        maxrows: params.limit,
		        pageindex:params.pageNumber,
	        };
	        return temp;
		}
    	//加载用户table数据
    	$(function(){ 
    		$(window).resize(function () {
		        $('#exampleTableEvents').bootstrapTable('resetView');
		    });
		    $("#company").bsSuggest('init', {
	    	   getDataMethod: "url",   //获取数据的方式，总是从 URL 获取
	    	   autoDropup: true,       //自动判断菜单向上展开
	            url: "${WEB_PATH }/order/myorder/getCompanyList.do?conpanyTypeName=货主&keyword=",
	            effectiveFields: ["name"],
	            searchFields: [ "name"],
	            effectiveFieldsAlias:{name: "姓名"},
	            idField: "id",
	            keyField: "name",
	            fnProcessData: function (json){
			      return json;
	            }
	        }).on('onDataRequestSuccess', function (e, result) {
	            console.log('onDataRequestSuccess: ', result);
	        }).on('onSetSelectValue', function (e, keyword, data) {
	            console.log('onSetSelectValue: ', keyword, data);
	        }).on('onUnsetSelectValue', function () {
	            console.log('onUnsetSelectValue');
	        });
		    $('#deliveryAreaName').citypicker({areaId:'deliveryAreaId'}); 
    		$('#consigneeAreaName').citypicker({areaId:'consigneeAreaId'}); 
    		$("#goodsType_id").chosen({width:'100%'}); 
    		$("#status").chosen({width:'100%'}); 
	    	$("#exampleTableEvents").bootstrapTable({
	            url: "${WEB_PATH }/goods/goodsbasic/getPage.do",
	            method: 'get',
	            search:true,
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
	            showRefresh:true, 
	            showColumns:true,
	            detailView:true,
	            detailFormatter:detailFormatter,
	            onCheck:onClickContacts,
	            iconSize: "outline",
	            toolbar: "#exampleTableEventsToolbar",
	            sidePagination: "server", //设置为服务器端分页
	            queryParams: queryParams,//参数
	            minimumCountColumns: 1, 
	            search: true,    //隐藏搜索框
	            clickToSelect: true, 
	        });
	        $('input.form-control.input-outline').attr("placeholder","输入发货单号");
	        $("#search").click(function(){
				$("#exampleTableEvents").bootstrapTable('refresh');
			});
        });
        
        $('#up-but').click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条发货信息上线。</font>', {icon: 5}); 
			    return ;
	       	}
	       	if((info.totalWeight - info.embarkWeight) == 0){
	       		layer.msg('<font color="red">当前货物剩余重量为0吨，无法上线！。</font>', {icon: 5}); 
	       		return ;
	       	}
	       	layer.confirm('尊敬的用户您好：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">您确定把当前发货信息上线吗？</span>',{icon: 3, title:'发货信息上线信息提示',btn: ['确定','取消']},
    		function(){
    			   updateGoodsBasicOnLine(info.id,true,$('#up-but'));
			}, function(){
				  layer.closeAll();
			});
        });
        
        $('#down-but').click(function(){
        	var selectRow = $("#exampleTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
			    layer.msg('<font color="red">温馨提示：请选择一条发货信息下线。</font>', {icon: 5}); 
			    return ;
	       	}
	       	layer.confirm('尊敬的用户您好：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">您确定把当前发货下线吗？</span>',{icon: 3, title:'发货下线信息提示',btn: ['确定','取消']},
    		function(){
    			   updateGoodsBasicOnLine(info.id,false,$('#down-but')); 
			}, function(){
				  layer.closeAll();
			});
        });
        
        
        function detailFormatter(index, row) {
        	var html = ""; 
        	$.ajax({
	            url:"${WEB_PATH}/global/goodsdata/getgoodsinfo.do?id="+row.id,
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
		
		function onClickContacts(index, row, element){
			 if(index.status == "success"){
		 	 	if(index.onLine){
		 	 		$("#up-but").attr("disabled",true); 
		 	 		$("#down-but").attr("disabled",false); 
		 	 	} else{
		 	 		$("#up-but").attr("disabled",false); 
		 	 		$("#down-but").attr("disabled",true); 
		 	 	}
			 } else{
			 	$("#up-but").attr("disabled",true); 
		 	 	$("#down-but").attr("disabled",true); 
			 }
		}
		
		
		function updateGoodsBasicOnLine(id,onLine,btn) {
			btn.attr("disabled",true); 
        	$.yilinAjax({
		   	  	type:'POST',
		   	  	url:'${WEB_PATH }/goods/goodsbasic/uodateGoodsBasicOnLine.do', 
		   	  	data:{id:id,onLine:onLine},
        		errorcallback:null,
        		btn:btn,
        		successcallback:upodateGoodsBasicOnLineSuccess
		   	});
		}
		
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatAreaName(index, row, element){
		 	var tt =  '<span class="label label-primary">'+row.deliveryAreaName+'</span>&nbsp;&nbsp;------->&nbsp;&nbsp;<span class="label label-danger">'+row.consigneeAreaName+'</span>';
    		return tt; 
		}
		
		function formatFiniteTime(index, row, element){
			var tt = "";
			if(row.longTime){
				tt = '<span class="label label-danger"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;长期有效</span>'
			}else{
			    tt=new Date(row.finiteTime).toLocaleString(); 
			}
    		return tt; 
		}
		
		function formatStatus(val){
			if(val == 'save'){
				return '<span class="label label-info">保存</span>';
			}else if(val == 'apply'){
				return '<span class="label label-default">申请发货</span>';
			}else if(val == "lock"){
				return '<span class="label label-primary">正在处理</span>';
			}else if(val == "back"){
				return '<span class="label label-warning">退回</span>';
			}else if(val == "success"){
				return '<span class="label label-success">通过</span>';
			}else if(val == "scrap"){
				return '<span class="label label-danger">销毁</span>';
			}
		}
		
		function formatOnLine(val){
			if(val){
				return '<span class="label label-success">上线</span>';
			}else{
				return '<span class="label label-danger">下线</span>';
			}
		}
		
	 	function formatMoney(val){
	 		return '<font class="text-danger">¥&nbsp;'+val+'</font>';
	 	}
	 	function formatTotalWeight(val){
	 		return val+"&nbsp;(吨)";
	 	}
	 	
	 	function formatGoodsType(index, row, element){
    		return row.goodsType.name; 
		}
		
		function upodateGoodsBasicOnLineSuccess(result,btn) {
			btn.attr("disabled",false);
			layer.closeAll();
			if(result.success == true){
				swal("已成功设置上线！", result.msg, "success");
				$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				swal("设置上线失败！", result.msg, "error");
				$("#exampleTableEvents").bootstrapTable('refresh');
			}
			document.getElementById('rightMain').contentWindow.location.reload(true);
		}
		function formatSurplusWeight(index, row, element){
			return '<font class="text-danger">'+(row.totalWeight - row.embarkWeight)+'(吨)</font>'; 
		}
    </script>
</body>

</html>