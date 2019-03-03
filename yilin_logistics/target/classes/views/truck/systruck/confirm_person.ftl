
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>人员确认</title>
    <meta name="keywords" content="易林物流平台-个人信息认证">
    <meta name="description" content="易林物流平台-个人信息认证">
    <link rel="shortcut icon" href="${WEB_PATH}/resources/img/logo.png">
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/js/plugins/stickup/stickup.css" rel="stylesheet">    
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-editable.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
	<link href="${WEB_PATH}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body>
    <div class="wrapper wrapper-content animated fadeInRight"> 
        <div class="row">
			 <div class="col-sm-12" >
			 	 <div style="text-align: center;padding-bottom: 10px;"><span class="label label-danger">正在运输中的司机不能选择</span></div>
                 <table id="companyAccountTruckGrid" >
                    <thead>
                        <tr>
                        	<th data-field="state" data-radio="true" data-formatter="stateFormatter"></th>  
                            <th data-field="id" data-visible = "false" >id</th>  
				            <th data-field="name"  >姓名</th> 
				            <th data-field="track_no" data-formatter="formatTrackNo"> 车辆</th> 
				            <th data-field="truck_status" data-formatter="formatTruckStatus"> 车辆</th> 
                        </tr>
                    </thead>
                </table>
            </div> 
        </div>
    </div>
    <script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/ajax.extend.js"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/bootstrap-table.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script> 
	<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
	<script src="${WEB_PATH}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
	
	<script >
		var truck_id = '${truck.id}';
		var truck_no = '${truck.track_no}';
		var truck_account_id = '${truck.account.id}';
		var loadCount = 0;
    	$(function(){
    		$(window).resize(function () {
		        $('#companyAccountTruckGrid').bootstrapTable('resetView');
			});  
			//加载table数据
	    	var companyAccountTruckGrid = $("#companyAccountTruckGrid").bootstrapTable({
	    		url:"${WEB_PATH}/truck/systruck/getCompantAccountTruckList.do",
	            uniqueId:"id",
	            iconSize: "outline",
	            minimumCountColumns: 1, 
	            onLoadSuccess:companyAccountTruckGridLoadSuccess,
	            clickToSelect: true
	        }); 
	         
        }); 
        
		 
		
		function confirmPerson(){
			 var row = $("#companyAccountTruckGrid").bootstrapTable('getSelections')[0];
			 if(row.track_no != null && row.track_no != "" && row.track_no != "null"){
			 	layer.open({
				   content: '<font color="#ed5565;">【'+row.name+'】司机已分配到货车【'+row.track_no+'】,是否重新分配到货车【'+truck_no+'】？</font>',
				   icon: 2,
				   btn: ['确定', '取消'],
				   yes: function(index, layero){
				    	$.yilinAjax({
					   	  	type:'POST',
					   	  	url:'${WEB_PATH}/truck/systruck/updateConfirmPerson.do',
					   	  	data:{trackId:row.trackId,distributionTrackId:truck_id,distributionUserId:row.id,distributionUserName:row.name},
					   	  	btn:null,
			        		errorcallback:null,
			        		successcallback:success
					   	 });
				   },
				   cancel: function(){  
				   		layer.closeAll();
						return false;
				   } 
				});
			 } else{
			 	$.yilinAjax({
			   	  	type:'POST',
			   	  	url:'${WEB_PATH}/truck/systruck/updateConfirmPerson.do',
			   	  	data:{trackId:row.trackId,distributionTrackId:truck_id,distributionUserId:row.id,distributionUserName:row.name},
			   	  	btn:null,
	        		errorcallback:null,
	        		successcallback:success
			   	 });
			 }
			 
		}
		
		function success(result){  
			if(result.success == true){
		    	layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
				  	skin: 'layui-layer-molv',
				  	icon: 1,
				  	closeBtn: 0
				},function(){
					parent.$("#exampleTableEvents").bootstrapTable('refresh');
				   	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引 
					parent.layer.close(index); 
					
				});
			}else{
		    	layer.alert('<font color="red">'+result.msg+'</font>', {
				  skin: 'layui-layer-molv' ,
				  closeBtn: 0,
				  icon: 5
				});
			}
		}
		
		function companyAccountTruckGridLoadSuccess(data){
			$("#companyAccountTruckGrid").bootstrapTable('checkBy', {field:'id', values:[truck_account_id]});
			loadCount++;
		}
		
		 
		function formatTrackNo(val){
			if(val == null || val == "" || val == "null"){
    			return '<span class="label label-danger">未分配车辆</span>';
    		}else{
    			return '<span class="label label-primary"><i class="fa fa-truck" aria-hidden="true"></i>&nbsp;&nbsp;'+val+'</span>';
    		}
		}
		
		function formatTruckStatus(val){
			if(val == '1'){
				return '<span class="label label-danger">运输中</span>';
			}else if(val == "0"){
				return '<span class="label label-primary">未运输</span>';
			} 
		}
		
		function stateFormatter(value, row, index) {
	        if (row.truck_status == 1) {
	            return {
	                disabled: true
	            };
	        }
	        return value;
	    }
		 
    </script>
</body>

</html>