<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;z-index:999999!important;">
        <div class="modal-dialog" style="width:70%">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title" style="text-align: center;">联系人选择</h1>
                    <form class="form-horizontal m-t" id="addInfo">
                    <div class="modal-body" style="max-height:450px;overflow-y: auto;"> 
                        <table id="contactsTableEvents" data-mobile-responsive="true" >
                            <thead>
                                <tr>
                                	<th data-field="state" data-radio="true"></th>  
                                    <th data-field="id"  data-visible="false" >id</th>
						            <th data-field="name">姓名</th>
						            <th data-field="mobile">手机号</th>
						            <th data-field="email">邮箱</th>
						            <th data-field="areaFullName">区域</th>
						            <th data-field="address">详细地址</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
                        <button type="button" class="btn btn-danger" id="selectBut"><i class="fa fa-check"></i>&nbsp;&nbsp;确定</button>
                    </div>
                 	</form> 
                </div>
            </div>
        </div> 
    </div> 
 <script>
 	var contactsType="${contactsType}";
 	function queryParams(params) {
	    var temp = {  
	        limit: params.limit,  
	        offset: params.offset, 
	        search: $("input.form-control.input-outline").val(),
	        maxrows: params.limit,
	        pageindex:params.pageNumber,
	    };
	    return temp;
	}
	//加载用户table数据
	$(function(){ 
		$(window).resize(function () {
		     $('#contactsTableEvents').bootstrapTable('resetView');
		});
		$("#contactsTableEvents").bootstrapTable({
	        url: "${WEB_PATH }/goods/contacts/getPage.do?contactsType=${contactsType}",
	        method: 'get',
	        search:true,
	        pagination:true,
	        pageNumber:1,  
	        pageSize:15,   
	        showRefresh:true, 
	        showColumns:true,
	        iconSize: "outline",
	        toolbar: "#exampleTableEventsToolbar",
	        sidePagination: "server", //设置为服务器端分页
	        queryParams: queryParams,//参数
	        searchText:"",   //设置搜索框文本初始值
	        minimumCountColumns: 1,  
	       	onDblClickRow:onDblClickContacts,
	        showToggle:false, 
	        clickToSelect: true,  
	        icons: {
	            refresh: "glyphicon-repeat", 
	            columns: "glyphicon-list"
	        }
	    });
	    $('input.form-control.input-outline').attr("placeholder","输入关键字搜索");
	    //关闭层
        $('#close-but').click(function(){
       		$('#userModal').remove();
        }); 
        $('#close-x').click(function(){
       		$('#userModal').remove();
        }); 
        $('#selectBut').click(function(){
       		var selectRow = $("#contactsTableEvents").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
        	if(selectRow.length == 0){
			    layer.msg('<font color="red">温馨提示：请选择联系人！。</font>', {icon: 5}); 
			    return ;
	       	}else{
	       		setFromValue(info);
	       		
	       	}
        }); 
        
	});
	
	function onDblClickContacts(row){
        setFromValue(row);
    }
	
	function setFromValue(row){
		if(contactsType == "consignor"){
   			$("#deliveryAreaName").citypicker("setValue",row.areaFullName);
   			$("#deliveryAreaName").citypicker("removeError");
   			$("#deliveryAreaName").val(row.areaFullName);
   			$("#deliveryAreaId").val(row.areaId);
   			$("#deliveryAddress").val(row.address);
   			$("#deliveryCoordinate").val(row.point);
   			//$("#deliveryName").val(row.name);
   			//$("#deliveryMobile").val(row.mobile);
   			$("#deliveryEmail").val(row.email);
   			if(validatCount > 0){
   				$("#goodsbasic_detail_info_form").valid();
   			}
   		}else if(contactsType == "consignee"){
   		    $("#consigneeAreaName").citypicker("setValue",row.areaFullName);
   		    $("#consigneeAreaName").citypicker("removeError");
   			$("#consigneeAreaName").val(row.areaFullName);
   			$("#consigneeAreaId").val(row.areaId);
   			$("#consigneeAddress").val(row.address);
   			$("#consigneeCoordinate").val(row.point);
   			$("#consigneeName").val(row.name);
   			$("#consigneeMobile").val(row.mobile);
   			$("#consigneeEmail").val(row.email);
   			if(validatCount > 0){
   				$("#goodsbasic_detail_info_form").valid();
   			}
   		}
   		var consigneeCoordinate = $('#consigneeCoordinate').val();
	    var deliveryCoordinate = $('#deliveryCoordinate').val();
	    if(consigneeCoordinate != "" && deliveryCoordinate != ""){	
	      mapDrivingRoute(deliveryCoordinate,consigneeCoordinate);
	    }
   		$('#userModal').remove();
	}
	
	function formatTime(val){
	 	var tt=new Date(val).toLocaleString(); 
		return tt; 
	}
	 
  </script>   
