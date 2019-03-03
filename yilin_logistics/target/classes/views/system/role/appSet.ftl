
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>角色APP资源设置</title>
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
			 <div class="col-sm-12">
                 <table id="menuAppGrids" >
                    <thead>
                        <tr>
                        	<th data-field="state" data-checkbox="true" ></th>  
                            <th data-field="id" data-visible = "false" >id</th>  
				            <th data-field="name"  >资源名称</th> 
				            <th data-field="type" data-formatter="formatterTypeName">类型</th> 
				            <th data-field="path" >资源地址</th> 
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
		var menuAppIds = "${menuAppIds}"; 
		var loadCount = 0;
    	$(function(){
    		$(window).resize(function () {
		        $('#menuAppGrids').bootstrapTable('resetView');
			});  
			//加载table数据
	    	var companyAccountTruckGrid = $("#menuAppGrids").bootstrapTable({
	    		url:"${WEB_PATH}/system/menuapp/getMenuAppList.do", 
	            uniqueId:"id",
	            iconSize: "outline",
	            minimumCountColumns: 1, 
	            onLoadSuccess:menuAppGridsLoadSuccess,
	            clickToSelect: true
	        }); 
        }); 
        
		 
		
		function roleSetMenuApp(){
			 var rows = $("#menuAppGrids").bootstrapTable('getSelections');
			 var row = parent.$("#exampleTableEvents").bootstrapTable('getSelections')[0];
			 console.log(row);
			 var menuAppIds = "";
	         for(var i=0;i<rows.length;i++){
	        		menuAppIds +=  rows[i].id +",";
			 }
			 $.yilinAjax({
		   	  	type:'POST',
		   	  	url:'${WEB_PATH }/system/role/setApp.do',
		   	  	data:{roleId:row.id, menuAppIds: menuAppIds},
		   	  	btn:null,
        		errorcallback:null,
        		successcallback:success
	   	    });
		}
		
		function success(result){  
			if(result.success == true){
		    	layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
				  	skin: 'layui-layer-molv',
				  	icon: 1,
				  	closeBtn: 0
				},function(){
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
		
		function menuAppGridsLoadSuccess(data){
			var arr = menuAppIds.split(",");
			$("#menuAppGrids").bootstrapTable('checkBy', {field:'id', values:arr});
			loadCount++;
		}
		
		 
		
		function formatterTypeName(val) {
			if(val == 'Native'){
				 return '<span class="label label-success">原生</span>';
			}else if(val == 'Html5'){
				 return '<span class="label label-success">HIML5</span>';
			}
		}
		 
    </script>
</body>

</html>