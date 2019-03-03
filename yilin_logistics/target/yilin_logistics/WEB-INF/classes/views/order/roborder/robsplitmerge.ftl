<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;z-index:999999!important;">
        <div class="modal-dialog" style="width:70%">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h2 class="modal-title" style="font-size: 16px;">
                    	货物拆分【货物类型：<span style="color:#00bb9c;">${robOrderRecordInfo.goodsDetail.goodsType.name}</span>----货物名称：<span style="color:#00bb9c;">${robOrderRecordInfo.goodsDetail.goodsName}</span>----货物重量：<span style="font-size: 16px;color: red;" id="weight_span">${robOrderRecordInfo.weight}</span>/吨】
                    </h2>
                    <div class="modal-body" style="max-height:350px;overflow-y: auto;"> 
                    	<div class="btn-group" id="robOrderInfo_grid_toolbar" role="group">
		                    <button type="button" class="btn btn-outline btn-default" id="mergeBut"> <i class="iconfont" style="color:#00bb9c;font-size:16px;font-style:normal;" >&#xe61d;</i>&nbsp;&nbsp;合并</button>
		                    <button type="button" class="btn btn-outline btn-default" id="deleteBut"> <i class="iconfont" style="color:red;font-size:16px;font-style:normal;" >&#xe616;</i>&nbsp;&nbsp;删除</button>
                        </div>
                        <table id="robOrderInfo_grid" data-height="242px" data-mobile-responsive="true" >
                            <thead>
                                <tr>
                                	<th data-field="state" data-checkbox="true"></th>  
                                	<th data-field="id"  data-visible="false" >id</th>
                                    <th data-field="parentId"  data-visible="false" >parentId</th>
                                    <th data-field="goodsDetailId"  data-visible="false" >goodsDetailId</th>
						            <th data-field="goodsTypeName" >货物类型</th> 
						            <th data-field="goodsName" > 货物名称</th> 
						            <th data-field="weight"> 重量/吨</th> 
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div class="modal-footer">
                    	<div class="col-sm-12">
                    		<div class="col-sm-6" style="text-align: left;">
		                        <form id="split_form">
		                    	<input type="number" name="split_weight" id="split_weight" style="background-color: #FFF; background-image: none;border: 1px solid #e5e6e7;border-radius: 1px;color: inherit;padding: 6px 12px;" placeholder="请输入需要拆分的吨位数" onkeypress="_submit()"/>
		                    	<input type="number" name="test" id="test" style="display:none" />
		                        <button type="button" class="btn btn-danger" id="splitBut"><i class="iconfont" style="color:#ffffff;font-size:16px;font-style:normal;" >&#xe61c;</i>&nbsp;&nbsp;拆分</button>
		                        </form>
		                    </div>
		                    <div class="col-sm-6">
	                        	<button type="button" class="btn btn-primary" id="saveSplitBut"><i class="iconfont" style="color:#ffffff;font-size:16px;font-style:normal;" >&#xe61a;</i>&nbsp;&nbsp;确定</button>
	                        </div>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div> 
    </div> 
 <script>
 	var goodsTypeName="${robOrderRecordInfo.goodsDetail.goodsType.name}";
 	var goodsTypeId="${robOrderRecordInfo.goodsDetail.goodsType.id}";
 	var goodsDetailId="${robOrderRecordInfo.goodsDetail.id}";
 	var robOrderRecordId = "${robOrderRecordInfo.robOrderRecord.id}"
 	var parentId = "${robOrderRecordInfo.id}";
 	var goodsName="${robOrderRecordInfo.goodsDetail.goodsName}";
 	var y_weight = "${robOrderRecordInfo.weight}";
 	var e = "<i class='fa fa-times-circle'></i> ";
	//加载已拆分的货物数据
	$(function(){ 
		$(window).resize(function () {
		     $('#robOrderInfo_grid').bootstrapTable('resetView');
		});
		$("#robOrderInfo_grid").bootstrapTable({
	        url: "${WEB_PATH }/order/roborderinfo/getListGoodsDetailByPid.do?robOrderId=${robOrderRecordInfo.robOrderRecord.id}&pid=${robOrderRecordInfo.id}",
	        method: 'get',
	        iconSize: "outline",
	        toolbar: "#robOrderInfo_grid_toolbar",
	        minimumCountColumns: 1,  
	        uniqueId:"id",
	        showToggle:false, 
	        clickToSelect: true,
	        onLoadSuccess:robOrderInfoLoadSuccess
	    });
	    var $split_form =  $('#split_form').validate({
		  rules: {
		    split_weight: {
		      required: true,
		      positiveInteger:true
		    } 
		  },
		  errorPlacement: function(error, element) {
		     layer.tips(error[0].innerHTML, '#'+element[0].id, {
			  	tips: [4, '#ed5565'],
			  	time: 1000
			 });
		  }
		});
		
	    //关闭层
        $('#close-but').click(function(){
       		$('#userModal').remove();
        }); 
        $('#close-x').click(function(){
       		$('#userModal').remove();
        }); 
        $('#splitBut').click(function(){
       		splitWight();
        }); 
        
        $('#mergeBut').click(function(){
       		var selectRow = $("#robOrderInfo_grid").bootstrapTable('getSelections');
       		var info = selectRow[0];
       		var merge_weight = 0;
	        if(selectRow.length < 2){
			    layer.msg('<font color="red">温馨提示：请选择两条已拆分的货物信息，进行合并！</font>', {icon: 5,time: 3000}); 
			    return ;
	       	}
	       	for(var i = 1; i < selectRow.length; i++) {
		        var robOrderInfo = selectRow[i];
		        merge_weight += parseFloat(robOrderInfo.weight);
	           $("#robOrderInfo_grid").bootstrapTable('removeByUniqueId', robOrderInfo.id);
		    }
		    info.weight = parseFloat(info.weight) + parseFloat(merge_weight);
		    $("#robOrderInfo_grid").bootstrapTable('updateByUniqueId', {
		        id:info.id,
                row:info
            });
            $("#robOrderInfo_grid").bootstrapTable('uncheckBy', {field:'id', values:[info.id]});
        }); 
        $('#deleteBut').click(function(){
       		var selectRow = $("#robOrderInfo_grid").bootstrapTable('getSelections');
       		var del_weight = 0;
	        if(selectRow.length == 0){
			    layer.msg('<font color="red">温馨提示：请选择一条已拆分的货物信息，进行删除！</font>', {icon: 5,time: 3000}); 
			    return ;
	       	}
	       	for(var i = 0; i < selectRow.length; i++) {
		        var robOrderInfo = selectRow[i];
		        del_weight += parseFloat(robOrderInfo.weight);
	            $("#robOrderInfo_grid").bootstrapTable('removeByUniqueId', robOrderInfo.id);
		    }
	       	$("#weight_span").html(del_weight+parseFloat(y_weight));
	       	y_weight = del_weight+parseFloat(y_weight)
        }); 
        
        $('#saveSplitBut').click(function(){
       		var data = $("#robOrderInfo_grid").bootstrapTable('getData');
       		var robOrderInfoSplitData = JSON.stringify(data);
   		    $.yilinAjax({
		   	  	type:'POST',
		   	  	loadmsg:'拆分确认中......',
		   	  	url:'${WEB_PATH }/order/roborderinfo/saveRobOrderSplit.do',
		   	  	data:{robOrderId:robOrderRecordId,robOrderRecordInfoId:parentId,surplusWeight:y_weight,splitRobOrderRecordInfo:robOrderInfoSplitData},
		   	  	btn:null,
	    		errorcallback:null,
	    		successcallback:splitsuccess
	   	    });
        }); 
        
        
	}); 
	function robOrderInfoLoadSuccess(){
		 $("#table_not_result").css("padding","5%");
	}
	 
	function splitWight(){
		var $valid = $("#split_form").valid();
   		if($valid){
   		    var split_weight = parseFloat($("#split_weight").val());
   		    if(split_weight <= 0 ){
   		    	 layer.tips(e+'拆分重量必须大于0，请重新输入！', '#split_weight', {
				  	tips: [4, '#ed5565'],
				  	time: 4000
				 });
				 return false;
   		    }else if(split_weight >  y_weight){
   		    	 layer.tips(e+'拆分重量不能大于原有货物重量（'+y_weight+'/吨），请重新输入！', '#split_weight', {
				  	tips: [4, '#ed5565'],
				  	time: 4000
				 });
				 return false;
   		    }else if((split_weight -  y_weight) == 0){
   		    	 layer.tips(e+'原货物不能被拆分为0吨，请重新输入！', '#split_weight', {
				  	tips: [4, '#ed5565'],
				  	time: 4000
				 });
				 $("#split_weight").val("")
				 return false;
   		    }
   			var randomId = 100 + ~~(Math.random() * 100);
   			$("#robOrderInfo_grid").bootstrapTable('insertRow', {
           		index: 0,
                row: {
                    id: randomId,
                    parentId:parentId,
                    goodsDetailId:goodsDetailId,
                    goodsTypeName: goodsTypeName,
                    goodsName: goodsName,
                    weight: $("#split_weight").val(),
                    actualWeight: $("#split_weight").val()
                }
            });
            y_weight =  parseFloat(y_weight) - parseFloat($("#split_weight").val());
            $("#weight_span").html(y_weight);
   		}
	}
	function splitsuccess(result){  
		if(result.success == true){
	    	layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
			  	skin: 'layui-layer-molv',
			  	icon: 1,
			  	closeBtn: 0
			},function(index){
				$("#roborderinfo_grid").bootstrapTable('refresh');
				$('#userModal').remove();
				layer.close(index);
			});
		}else{
	    	layer.alert('<font color="red">'+result.msg+'</font>', {
			  skin: 'layui-layer-molv' ,
			  closeBtn: 0,
			  icon: 5
			});
		}
	}
	function _submit(){     
		if (event.keyCode == 13) 
		{      
			splitWight();
		}
	 }
  </script>   
