<style>
	.well-1 {
	    min-height: 20px;
	    padding: 19px;
	    margin-bottom: 20px;
	    background-color: #ffffff;
	    border: 1px solid #e3e3e3;
	    border-radius: 4px;
	    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.05);
	    box-shadow: inset 0 1px 1px rgba(0,0,0,.05);
	}
	
	.well-red {
	    min-height: 20px;
	    padding: 19px;
	    margin-bottom: 20px;
	    background-color: #ffffff;
	    border: 1px solid #e3e3e3;
	    border-radius: 4px;
	    color: red;
	    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.05);
	    box-shadow: inset 0 1px 1px rgba(0,0,0,.05);
	}
</style>
<script src="${WEB_PATH}/resources/js/content.min.js"></script> 
<link href="http://www.ylwuliu.cn:80/yilin_logistics/resources/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
<script>
	$(function(){
		
		$("#type-form").hide();
		//$("#savetype").chosen({width:'100%'}); 
		$("#savetype").change(function(){
			  $("#type-form").find("#savetype-error").remove();
			if($(this).val()==1){
				$("#type-form").show();
			}else{
				$("#type-form").hide();
			}
		    
		});
		
		$(".inlineRadio").change(function(){
			  $("#type-form").find("#savetype-error").remove();
		})
		
	});
</script>
 <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>申请仲裁信息</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">

                     <div class="row">   
             <div class="col-sm-5"> 
                <div class="contact-box">
                 <@companyInfo accontId = applyinfo.applypersonid>
                				 <#if (userinfo.account.company.companyType.name)=='货主'>
                				 	<div id="message" class="hidden">
                				 		是否对货主：【${userinfo.account.name}】 进行赔偿  （赔偿金额¥${robOrderConfirm.turckCost?string(",##0.0#")}元）<br>
                				 		(赔偿后将进行销单处理)
                				 	</div>
                        			
                        		<#else>
                        			<div id="message" class="hidden">
                        				是否对车队：【${userinfo.account.company.name}】 进行赔偿  （赔偿金额¥${robOrderConfirm.transportationDeposit?string(",##0.0#")}元）<br>
                        				(赔偿后将进行销单处理)
                        			</div>
                        		</#if>
                 		
                        <div class="col-sm-12">
                        	<span class="label label-primary pull-right">申请仲裁商户</span>
                            <h3><strong>${userinfo.account.company.name}</strong></h3>
                            <address>
	                          	 <strong>商户类型:</strong><a href="">${userinfo.account.company.companyType.name}</a><br>
	                             <strong>管理员:</strong>${userinfo.comAccount.name}<br>
	                             <strong>联系人电话:</strong>${userinfo.comAccount.phone}<br>
	                             <strong>商户地址:</strong>${userinfo.account.company.address}<br>
                        	</address>
                        	<div class="well-1">
                        		<span class="label badge-warning pull-right">申请人信息</span>
	                        	 <address>
		                          	 <strong>姓名:</strong>${userinfo.account.name}<br>
		                             <strong>电话:</strong><a href="">${userinfo.account.phone}</a><br>
	                        	 </address>
	                        	 <div class="feed-element">
	                                    <div class="media-body ">
	                                       <small class="text-muted"> ${(applyinfo.create_time)?string("yyyy-MM-dd HH:mm:ss")} 申请仲裁</small>
	                                        <div class="well">
	                                        	${applyinfo.remark}
	                                        </div>
	                                    </div>
                               	</div>
                        	</div>
                        </div>
                        <div class="clearfix"></div>
                  </@companyInfo>
                </div>
            </div>
            <div class="col-sm-2" style="text-align: center;">
                <div style="padding-top: 50%;">
            	<i class="iconfont" style="font-size: 50px;">&#xe624;</i>
            	</div>
            </div>
            <div class="col-sm-5"> 
             <@companyInfo accontId = applyinfo.beArbitrationPersonId>
                <div class="contact-box">
                        <div class="col-sm-12">
                        	<span class="label label-danger pull-right">被仲裁商户</span>
                            <h3><strong>${userinfo.account.company.name}</strong></h3>
                            <address>
	                          	 <strong>商户类型:</strong><a href="">${userinfo.account.company.companyType.name}</a><br>
	                          	 <strong>管理员:</strong><a href="">${userinfo.comAccount.name}</a><br>
	                             <strong>联系人电话:</strong><a href="">${userinfo.comAccount.phone}</a><br>
	                             <strong>商户地址:</strong><a href="">${userinfo.account.company.address}</a><br>
                        	</address>
                        	<div class="well-1">
                        		<#if (userinfo.account.company.companyType.name)=='货主'>
                        			<span class="label badge-warning pull-right">发货人信息</span>
                        		<#else>
                        			<span class="label badge-warning pull-right">拉货司机 信息</span>
                        		</#if>
                        		
	                        	 <address>
		                          	 <strong>姓名:</strong>${userinfo.account.name}<br>
		                             <strong>电话:</strong><a href="">${userinfo.account.phone}</a><br>
	                        	 </address>
                        	</div>
                        </div>
                        <div class="clearfix"></div>
                     </@companyInfo>
                </div>
            </div>
<div>
 <#if (applyinfo.emergencyImgList)??>
	                                        <div class="row">

					                        <div class="col-lg-12">
					                            <div class="ibox float-e-margins">
					                                <div class="ibox-title">
					                                    <h5>照片</h5>
					                                    <div class="ibox-tools">
					                                        <a class="collapse-link">
					                                            <i class="fa fa-chevron-down"></i>
					                                        </a>
					                                    </div>
					                                </div>
					                                <div class="ibox-content">
					                              		  <div class="wrapper wrapper-content">
													        <div class="row">
													            <div class="col-lg-12" style="padding-top: 10px;">
													                <div class="ibox float-e-margins">
													
													                    <div class="ibox-content">
													                        <div class="lightBoxGallery">
													                        	 <#list applyinfo.emergencyImgList as img>
													                        	 	 <a class="fancybox" href="${img}">
						                                         						<img  src="${img}">
						                                         					 </a>
													                        	 </#list>  
													                        </div>
													                    </div>
													                </div>
													            </div>
													        </div>
													    </div>
					                                </div>
					                            </div>
					                        </div>
					
					                    </div>
							            </div>
	                                    </div>
	          							</#if>

<#if applyinfo.specialStatus !='suchprocessing'>

 <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>处理信息</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
						  <div>
                            <div class="feed-activity-list">
                              <#list logList as log >
	                              	<#if log_index != 0>
	                              			<div class="feed-element feed-element-1">
					                             <a href="#" class="pull-left">
			                                       <i class="iconfont" style="font-size:25px;">&#xe622;</i>
			                                    </a>
					                            <div class="media-body">
					                              	<strong>${log.dealtPersonName}</strong> 
					                                <br/>
					                                <small class="text-muted">${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</small>
					                                  <div class="well">
					                                      ${log.remark}
					                                  </div>
					                            </div>
					                        </div>
	                              	</#if>
                             </#list>
                             
                             <#if applyinfo.specialStatus =='success'>
	                             <div class="feed-element feed-element-1">
						         	<div class="media-body">
						            	<strong>处理结果</strong> 
						                 <br/>
						                 <div class="well well-red">
						                  	<#if applyinfo.rocessingResult =='noperation'>
						                  		无操作
						                  		<#else>
						                  		 <@companyInfo accontId = applyinfo.applypersonid>
						                  		 	 <#if (userinfo.account.company.companyType.name)=='货主'>
					                				 	【${userinfo.account.name}】 获得赔偿  （赔偿金额¥${applyinfo.indemnity?string(",##0.0#")}元元）
					                        		<#else>
					                        			【${userinfo.account.company.name}】 获得赔偿  （赔偿金额¥${applyinfo.indemnity?string(",##0.0#")}元）<br>
					                    			</div>
					                        		</#if>
						                  		 </@companyInfo>
						                  	</#if>
						      	     	</div>
								     </div>
							     </div>
					         </#if>
            	  	 </div>
         		</div>
	</div>


</div>

         	    </div>
    	    </div>
</#if>

<#if show>
<div class="row">
	 <div class="col-sm-12"> 
    	 <div class="contact-box">
    	 	 <form id="log_info_form">
    	 	 					 <div class="row">
    	 	 					 	  <div class="col-sm-4">
    	 	 					 	  		 <div class="form-group">
    	 	 					 	  		    <label>操作步骤：</label>
			                                    <select data-placeholder="请选择处理步骤" id="savetype" class="chosen-select form-control" name="savetype">
			                                        <option value="" hassubinfo="true" ></option>
				                                   <!-- <option value="0" hassubinfo="true">继续处理</option> -->
				                                    <option value="1" hassubinfo="true" >处理完成</option>
				                                </select>
			                     			 </div>
    	 	 					 	  </div>
    	 	 					 </div>
    	 	 				
    	 	 				  <#if applyinfo.specialStatus!="success">
    	 	 					 <div class="form-group" id="type-form">
    	 	 						<p><strong>处理类型</strong></p>
                                    <div class="radio radio-info radio-inline">
                                        <input type="radio" id="inlineRadio1" value="noperation" class="inlineRadio" name="rocessingResult">
                                        <label for="inlineRadio1">无操作 </label>
                                    </div>
                                    <div class="radio radio-inline">
                                        <input type="radio" id="inlineRadio2" value="indemnify" class="inlineRadio" name="rocessingResult">
                                        <label for="inlineRadio2">进行赔偿</label>
                                    </div>
                                    <div class="radio radio-inline">
                                        <input type="radio" id="inlineRadio3" value="singlepin" class="inlineRadio" name="rocessingResult">
                                        <label for="inlineRadio3">消单</label>
                                    </div>
                                 </div>
    	 	  				    </#if>
                                 <div class="feed-element">
                                 	<br>
                                    <div class="media-body ">
                                        <strong>处理说明</strong>
                                        <br>
                                        <div>
                                         <input type="text" value="${applyinfo.robOrderConfirmId}" class="hidden" name='robConfirmId' />
                                         <textarea name="remark" class="form-control" placeholder="填写处理说明..."></textarea>
                                        </div>
                                        <#if applyinfo.specialStatus=="suchprocessing">
	                                        <div class="pull-right" style="margin-top: 10px;">
	                                        	<button type="button" class="btn btn-w-m btn-primary" id="saveBtn">确认处理</button>
	                                        </div>
                                        <#elseif applyinfo.specialStatus=="processing">
                                        	 <div class="pull-right" style="margin-top: 10px;">
	                                        	<button type="button" class="btn btn-w-m btn-primary" id="saveBtn">保存</button>
	                                        </div>
                                        </#if>
                                    </div>
                                </div>
                               </form>
    	 </div>
     </div>
</div>
</#if>