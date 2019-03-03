 	   <style>
		.feed-activity-list .feed-element-1 {
		    border-bottom: 1px solid #ffffff;
		}
   </style>
   <script src="${WEB_PATH}/resources/js/content.min.js"></script> 

       <link href="${WEB_PATH}/resources/css/animate.min.css" rel="stylesheet">
       <link href="http://www.ylwuliu.cn:80/yilin_logistics/resources/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
   <div class="row">    
           <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>信息</h5>
                    </div>
                    <div class="ibox-content">

                        <div>
                            <div class="feed-activity-list">
								
								<div class="feed-element">
	                                    <a href="#" class="pull-left">
	                                       <i class="iconfont" style="font-size:25px;">&#xe623;</i>
	                                    </a>
	                                    <div class="media-body ">
	                                        <small class="pull-right"></small>
	                                        <strong>${applyinfo.applypersonName}</strong> 
	                                        <br>
	                                        <small class="text-muted"> ${(applyinfo.create_time)?string("yyyy-MM-dd HH:mm:ss")} 请救急救</small>
	                                        <div class="well">
	                                        	${applyinfo.remark}
	                                        </div>
	                                        
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
                               	</div>
							  <#list logList as log >
							  	<#if log_index!=0>
	                               	     <div class="feed-element feed-element-1">
				                             <a href="#" class="pull-left">
		                                       <i class="iconfont" style="font-size:25px;">&#xe622;</i>
		                                    </a>
				                            <div class="media-body">
				                              	<strong>${log.dealtPersonName}</strong> 
				                                <br/>
				                                <small class="text-muted"> ${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</small>
				                                  <div class="well">
				                                      ${log.remark}
				                                  </div>
				                            </div>
				                        </div>
							  	</#if>
				              </#list>
				              
								<#if show>
                                <form id="log_info_form">
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
	                                        	<button type="button" class="btn btn-w-m btn-primary" id="saveBtn">继续处理</button>
	                                        	<button type="button" class="btn btn-w-m btn-warning" id="confirmSaveBtn">处理完成</button>
	                                        </div>
                                        </#if>
                                    </div>
                                </div>
                               </form>
								</#if>
                            </div>


                        </div>

                    </div>
                </div>
                
                
                

            </div>
         <div> 