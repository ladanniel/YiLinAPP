<div style="" class="order ui-sortable" >
       <div class="row">
            <div class="col-sm-12">
            	<div class="btn-group" id="exampleTableEventsToolbar" role="group">
				                    <button type="button" class="btn btn-outline btn-default" data-toggle="modal" id="${goodsId_return}" >
				                        <i class="glyphicon glyphicon-user" aria-hidden="true"></i>&nbsp;&nbsp;抢单处理
				                    </button>
                                </div>
            	<table id="${goodsId}" data-mobile-responsive="true" >	
            		<thead>
                        <tr>
                            	<th data-field="state" data-radio="true"></th>  
		                        <th data-field="id"  data-visible="false" >id</th>
		                        <th data-field="robOrderNo">单号</th>
								<th data-field="account.name">抢单人姓名</th>
								<th data-field="account.phone">抢单人电话</th>
								<th data-field="unitPrice" data-formatter="formatMoney">竞价单价</th>
								<th data-field="weight" data-formatter="formatTotalWeight">竞载重量</th>
								<th data-field="totalPrice" data-formatter="formatMoney">总价(单价*重量)</th>
								<th data-field="goodsBasic.goodsType.name">货物类型</th>
								<th data-field="status" data-formatter="formatStatus">状态</th>
							    <th data-field="create_time" data-formatter="formatTime">抢单时间</th>
                        </tr>
                    </thead>
                </table>	 
            </div>
      </div>
</div>

                                    
<script >
	//加载用户table数据
    	$(function(){ 
    		$(window).resize(function () {
		        $('#${goodsId}').bootstrapTable('resetView');
			});
	    	$("#${goodsId}").bootstrapTable({
	            url: "${WEB_PATH }/order/ordermanage/getPage.do?id=${goodsId}",
	            method: 'get',
	            pagination:true,
	            pageNumber:1,  
	            pageSize:10,   
	            iconSize: "outline",
	            sidePagination: "server", //设置为服务器端分页
	            minimumCountColumns: 1, 
	            clickToSelect: true, 
	        });
	        $('input.form-control.input-outline').attr("placeholder","输入关键字搜索");
        });
        
		//审核信息
		$("#${goodsId_return}").click(function(){
			$('#${goodsId_return}').attr("disabled",true);
        	var selectRow = $("#${goodsId}").bootstrapTable('getSelections');
        	var info = selectRow[0]; 
	        if(1 != selectRow.length){
	        	$('#${goodsId_return}').attr("disabled",false);
			    layer.msg('<font color="red">温馨提示：请选择处理中或正在申请的数据进行操作。</font>', {icon: 5}); 
			    return ;
	       	}
	       	if(info.status != "apply" && info.status != "dealing"){
	       			$('#${goodsId_return}').attr("disabled",false);
	        		layer.msg('<font color="red">温馨提示：请选择处理中或正在申请的数据进行操作。</font>', {icon: 5}); 
			    	return ;
	        }
	        $('#${goodsId_return}').attr("disabled",false);
	        layer.open({
		      type: 2,
		      title: '<span style="color: #ed5565">抢单信息审查</span>',
		      shadeClose: true,
		      shade: false,
		      maxmin: false, //开启最大化最小化按钮
		      area: ['95%', '95%'],
		      content: '${WEB_PATH }/order/ordermanage/view/verify.do?id='+info.id,
		      btn: ['确定','取消'],
			  yes: function(index, layero){
			      var body = layer.getChildFrame('body', index);
			      var iframeWin = window[layero.find('iframe')[0]['name']];
			      iframeWin.saveInfo();
			  },
			  cancel: function(){
			  	 $("#${goodsId}").bootstrapTable('refresh');
		  	  	  $('#${goodsId_return}').attr("disabled",false); 
				  layer.closeAll();
			  }
		    });
		});
		
		function formatTime(val){
		 	var tt=new Date(val).toLocaleString(); 
    		return tt; 
		}
		
		function formatStatus(val){
			if(val == 'apply'){
				return '<span class="label label-default">提交申请</span>';
			}else if(val == "dealing"){
				return '<span class="label label-primary">处理中</span>';
			}else if(val == "back"){
				return '<span class="label label-warning">协商中</span>';
			}else if(val == "success"){
				return '<span class="label label-success">成功</span>';
			}else if(val == "scrap"){
				return '<span class="label label-danger">作废</span>';
			}
		}
		
	 	function formatMoney(val){
	 		return '<font class="text-danger">¥&nbsp;'+val+'</font>';
	 	}
	 	function formatTotalWeight(val){
	 		return val+"&nbsp;(吨)";
	 	}
    </script>