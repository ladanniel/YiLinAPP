<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title">添加银行卡</h6></br>
                    <form class="form-horizontal validate big" id="form-card" action="${WEB_PATH }/capital/bankcard/bandCard.do" target="_blank"> 
						          <div class="form-group">
						            <label class="control-label col-md-3">真实姓名：</label>
						            <div class="col-md-8">
					            		<p class="form-control-static">
						            	<#if user.name != null && user.name != "">
						            		*${(user.name?substring(1,user.name?length))!}
					               		<#else>
						               		你还没有通过实名认证
						            	</#if>
						            	</p>
						            </div>
						          </div>
						          <div class="form-group">
		                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>银行卡号：</label>
		                                <div class="col-sm-8 card-number-content">
		                                   <input type="text" id="card-number" class="form-control" required="" aria-required="true" name="bankCard" maxlength="32"/>
		                                </div>
		                           </div>
						          <div class="form-group">
						            <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>开户行名称：</label>
						            <div class="col-md-8">
						               <input type="text" required="" aria-required="true" class="form-control" id="openBank" name="openBank" maxlength="128"/>
						            </div>
						          </div>
						           <div class="form-group">
						            <div class="col-md-8 col-md-offset-3">
						            	<p class="help-block">
						            	为保证提现及时收款，请填写中国**银行**省**市**支行或分行**分理处(如：中国农业银行浙江省杭州市西湖支行)
						            	</p>
						            </div>
						          </div>
					          <div class="form-group">
					          	<label class="control-label col-md-4"></label>
					            <div class="col-md-7">
					              <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
					              <button type="submit" class="btn btn-primary" onclick="onPayForm()" id="saveBut">
					               	<span class="glyphicon glyphicon-ok"></span> 
					               	确认添加
					              </button>
					            </div>
					          </div>
					          
					        </form>
					        <div class="alert alert-info big">
					        <div class="col-md-1">
					        </div>
		     	 		 	<h4>温馨提示</h4>
		     	 		 	<ol class="padding-left-20">
							  <li>请您输入正确的开户行以及银行卡号。</li>
							  <li>系统会根据输入的卡号信息智能识别银行以及卡种。</li>
							  <li>如果您填写的银行信息不正确可能会导致提现失败。</li>
							  <li>如果您的银行卡信息系统不能识别，请及时联系客服。</li>
							</ol>
	     	 		 </div>
                </div>
            </div>
        </div> 
    </div>
    
 <script src="${WEB_PATH}/resources/js/capital/bankinput.js"></script>   
 <script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script>
 <script>
 $.validator.setDefaults({
    highlight: function(e) {
        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
    },
    success: function(e) {
        e.closest(".form-group").removeClass("has-error").addClass("has-success")
    },
    errorElement: "span",
    errorPlacement: function(e, r) {
        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
    },
    errorClass: "help-block m-b-none",
    validClass: "help-block m-b-none"
});
function onPayForm(){
	if($("#form-card").valid()){ 
       $("#form-card").submit();
       $('#saveBut').attr("disabled",true); 
       setTimeout(function () { 
			layer.confirm('正在执行绑定银行卡操作', {
			  btn: ['已完成银行卡绑定', '银行卡绑定遇见问题？']
			}, function(index, layero){
				window.location.reload();
			  	layer.closeAll();
			}, function(index){
				 $('#saveBut').attr("disabled",false); 
			  	layer.closeAll();
			});
	   }, 3000);
	}
}
 $(function(){ 
 		//$('#saveBut').attr("disabled",true);
 		$("#card-number").bankInput();//银行卡格式化
 		var e = "<i class='fa fa-times-circle'></i> ";
        	 $("#form-card").validate({
        	 	rules: {
		        	bankCard: {
		                required: !0,
		            },
		            openBank: {
		                required: !0,
		            }
		        },
		        messages: {
		            bankCard: {
		               required: e + "请输入银行卡号。",
		            },
		            openBank: {
		               required: e + "请输入开户行名称。",
		            }
		        }
		});
        
       //关闭层
       $('#close-but').click(function(){
       		$('#userModal').remove();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').remove();
       }); 
       
       
	$('#card-number').blur(function(){
		var number = $("#card-number").val();
		var html = '<span id="alert">正在检测....</span>' 
		$("#card-number").after(html);
		$.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/capital/bankcard/verify.do',  
					  data: {"number":number},
					  dataType: "json",
						 success:function(data){  
							$("#alert").remove();
							$(".card-number-content").empty();
							var charname = new Array();
							var number = data.bankCard;
							$("#bankCard").val(number);
							var newNumber = '';
							for(var i=0;i<number.length;i++){
								if(i > 3 && i< (number.length-3) ){
									charname.push('*');
								}else{
									charname.push(number[i]);
								}
							}
							for(var i=0;i<charname.length;i++){
								newNumber = newNumber + charname[i];
							}
							var result = '<div class="form-control-static">' + newNumber + '('+ data.cnName + ')</div>';
							result += '<input type="hidden" class="form-control" id="bankCard" name="bankCard" value="'+number+'"/>';
							$(".card-number-content").append(result);
						},
						error:function(data){
							$("#alert").remove();
							var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						}
		});
	});
	
  });   
  </script>   
