<#macro systemImportCss >
	<link rel="shortcut icon" href="favicon.ico">
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/animate.min.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/js/plugins/intro/introjs.css" rel="stylesheet"></script>
</#macro> 

<!-- 首页-->
<#macro systemImportScript >
	<script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
	<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
	<script src="${WEB_PATH}/resources/js/hplus.min.js?v=4.0.0"></script>
    <script src="${WEB_PATH}/resources/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script>
    <script src="${WEB_PATH}/resources/js/contabs.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/pace/pace.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/intro/intro.js"></script>
    <script src="${WEB_PATH}/resources/js/map.js"></script>
    <script>
    	function quite() {
    	
    		 swal({
			        title: "您确定要退出系统吗",
			        text: "退出后将离开系统后台，请谨慎操作！",
			        type: "warning",
			        showCancelButton: true,
			        confirmButtonColor: "#DD6B55",
			        confirmButtonText: "是的，我要退出！",
			        cancelButtonText: "让我再考虑一下…",
			        closeOnConfirm: false,
			        closeOnCancel: false
			    },
			    function(isConfirm) {
			        if (isConfirm) {
			        	location.href = "${WEB_PATH }/system/user/logout.do";
			        } else {
			            swal("已取消", "您取消了退出操作！", "error")
			        }
			    });
    	}
    </script>
    
</#macro>
<!-- 首页-->

<!-- 主页CSS-->
<#macro mianImportCss >
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet"> 
    <!-- Morris -->
    <link href="${WEB_PATH}/resources/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet"> 
    <!-- Gritter -->
    <link href="${WEB_PATH}/resources/js/plugins/gritter/jquery.gritter.css" rel="stylesheet"> 
    <link href="${WEB_PATH}/resources/css/animate.min.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"> 
</#macro>
<!-- 主页CSS-->



<!-- 主页JS-->
<#macro minImportScript >
	<script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.spline.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.resize.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.pie.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.symbol.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/peity/jquery.peity.min.js"></script>
    <script src="${WEB_PATH}/resources/js/demo/peity-demo.min.js"></script>
    <script src="${WEB_PATH}/resources/js/content.min.js?v=1.0.0"></script>
    <script src="${WEB_PATH}/resources/js/plugins/jquery-ui/jquery-ui.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/easypiechart/jquery.easypiechart.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/sparkline/jquery.sparkline.min.js"></script>
    <script src="${WEB_PATH}/resources/js/demo/sparkline-demo.min.js"></script>
</#macro>
<!-- 主页JS-->  

<!-- table管理CSS-->
<#macro tableManagerImportCss >
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-editable.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/animate.min.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"> 
    <link href="${WEB_PATH}/resources/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</#macro>
<!-- table管理CSS-->


<!-- table管理JS-->
<#macro tableManagerImportScript >
    <script src="${WEB_PATH}/resources/js/jquery-1.9.1.min.js"></script>
    <script src="${WEB_PATH}/resources/js/ajax.extend.js"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${WEB_PATH}/resources/js/content.min.js?v=1.0.0"></script>
    <script src="${WEB_PATH}/resources/js/plugins/chosen/chosen.jquery.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/bootstrap-table.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/bootstrap-editable.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/treeview/bootstrap-treeview.js"></script>   
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.method.js"></script>
    
</#macro>
<!-- table管理JS-->

<!-- 登陆CSS/JS -->
<#macro loginImport >
	<link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/animate.min.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
    <script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
    
</#macro>
<!-- 登陆CSS/JS --> 


<!-- 左边菜单 --> 
<#macro leftMenuImportHtml > 
 <nav class="navbar-default navbar-static-side" role="navigation" id="div1">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse" >
                <ul class="nav" id="side-menu">
                    <li class="nav-header" style="text-align:center;">
                        <div class="dropup profile-element">
                            <span><img alt="image" class="img-circle" src="${WEB_PATH}/resources/img/profile_small.jpg" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle user_menu"  href="#">
                                <span class="clear" >
                               <span id="userNameId" class="block m-t-xs"><strong class="font-bold">欢迎您：
							    <#if (user.name)?? &&  user.name != "" >
							     ${user.name}
								<#elseif (user.account)??>
								  ${user.account}
								<#else>   
								  ${user.phone}
								</#if>
                               </strong> <b class="caret"></b></span> 
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs  user_menu_count" >
                                <li><a class="J_menuItem" href="${WEB_PATH }/system/user/view/accountinfo.do" data_false = "true">账户信息</a>
                                </li>
                                <li><a class="J_menuItem" href="${WEB_PATH }/system/company/companyinfo.do" data_false = "true">商户信息</a>
                                
                                <li><a class="J_menuItem" href="${WEB_PATH }/system/user/view/editpassword.do" data_false = "true">密码修改</a>  
                                </li>
								<#if user.role.is_aut && user.authentication != "auth">
                                <li><a class="J_menuItem" href="${WEB_PATH }/aut/authenticat/index.do" data_false = "true">证件认证申请</a>  
                                </li>
                                </#if>  
                                <li class="divider"></li>
                                <li><a href="${WEB_PATH }/system/user/logout.do" >退出</a>
                                
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element" >易林
                        </div>
                    </li>
                    <#if user.authentication?string != "notAuth">
                    <li>
                        <a href="${WEB_PATH }/system/admin/main.do" target="_blank">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">首页</span> 
                        </a> 
                    </li> 
                    </#if>
                    <#list moduleList as module>
                    <li>
                        <a href="
                        <#if user.company.audit?string == "auth" || user.company.audit?string == "supplement" || user.company.audit?string == "waitProcessSupplement"> 
                        ${WEB_PATH}/${module.resource.url}" 
                        </#if>
                        <#if user.userType?string == "user" && user.capitalStatus != "open"> 
                        ${WEB_PATH}/${module.resource.url}" 
                        </#if>
                        <#if user.capitalStatus != "open" && module.resource.url != "#">class="J_menuItem" data_false = "true"</#if>>
                            <i class="<#if module.iconcls>${module.iconcls}<#else>fa fa-cog</#if>"></i>
                            <span class="nav-label">${module.name}</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                        	<@menuPidInfo pid = module.id>
	                    	<#list menuviews as menu >
                            <li>
                                <a class="J_menuItem" 
                                <#if user.userType?string == "company"> 
                                	<#if user.company.companyType.is_aut?string("true","false") == true> 
                                		<#if user.company.audit?string == "auth" || user.company.audit?string == "supplement" || user.company.audit?string == "waitProcessSupplement"> 
                                			href="${WEB_PATH}/${menu.resource.url}"
                                		</#if>
                                	<#else>
                                		href="${WEB_PATH}/${menu.resource.url}"
                                	</#if>
                                <#else>
                                	href="${WEB_PATH}/${menu.resource.url}"
                                </#if>
                                >${menu.name}</a>
                            </li>
                            </#list>
							</@menuPidInfo>
                        </ul>
                    </li>
					</#list>  
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        

</#macro>
<!-- 左边菜单 --> 

<#macro miniChartImportHtml >
 <!--右侧边栏开始--> 
  <div id="right-sidebar"> 
   <div class="sidebar-container"> 
    <ul class="nav nav-tabs navs-3"> 
     <li class="active"> <a data-toggle="tab" href="#tab-1"> <i class="fa fa-gear"></i> 主题 </a> </li> 
    </ul> 
    <div class="tab-content"> 
     <div id="tab-1" class="tab-pane active"> 
      <div class="sidebar-title"> 
       <h3> <i class="fa fa-comments-o"></i> 主题设置</h3> 
       <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small> 
      </div> 
      <div class="skin-setttings"> 
       <div class="title">
       	         主题设置
       </div> 
       <div class="setings-item"> 
        <span>收起左侧菜单</span> 
        <div class="switch"> 
         <div class="onoffswitch"> 
          <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu" /> 
          <label class="onoffswitch-label" for="collapsemenu"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span> </label> 
         </div> 
        </div> 
       </div> 
       <div class="setings-item"> 
        <span>固定顶部</span> 
        <div class="switch"> 
         <div class="onoffswitch"> 
          <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar" /> 
          <label class="onoffswitch-label" for="fixednavbar"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span> </label> 
         </div> 
        </div> 
       </div>  
      </div> 
     </div> 
    </div> 
   </div> 
  </div> 
  <!--右侧边栏结束--> 
  <!--mini聊天窗口开始--> 
  <div class="small-chat-box fadeInRight animated"> 
   <div class="heading" draggable="true"> 
    <small class="chat-date pull-right"> 2015.9.1 </small> 与 Beau-zihan 聊天中 
   </div> 
   <div class="content"> 
    <div class="left"> 
     <div class="author-name">
       Beau-zihan 
      <small class="chat-date"> 10:02 </small> 
     </div> 
     <div class="chat-message active">
       你好 
     </div> 
    </div> 
    <div class="right"> 
     <div class="author-name">
       游客 
      <small class="chat-date"> 11:24 </small> 
     </div> 
     <div class="chat-message">
       你好，请问H+有帮助文档吗？ 
     </div> 
    </div> 
    <div class="left"> 
     <div class="author-name">
       Beau-zihan 
      <small class="chat-date"> 08:45 </small> 
     </div> 
     <div class="chat-message active">
       有，购买的H+源码包中有帮助文档，位于docs文件夹下 
     </div> 
    </div> 
    <div class="right"> 
     <div class="author-name">
       游客 
      <small class="chat-date"> 11:24 </small> 
     </div> 
     <div class="chat-message">
       那除了帮助文档还提供什么样的服务？ 
     </div> 
    </div> 
    <div class="left"> 
     <div class="author-name">
       Beau-zihan 
      <small class="chat-date"> 08:45 </small> 
     </div> 
     <div class="chat-message active">
       1.所有源码(未压缩、带注释版本)； 
      <br /> 2.说明文档； 
      <br /> 3.终身免费升级服务； 
      <br /> 4.必要的技术支持； 
      <br /> 5.付费二次开发服务； 
      <br /> 6.授权许可； 
      <br /> …… 
      <br /> 
     </div> 
    </div> 
   </div> 
   <div class="form-chat"> 
    <div class="input-group input-group-sm"> 
     <input type="text" class="form-control" /> 
     <span class="input-group-btn"> <button class="btn btn-primary" type="button">发送 </button> </span> 
    </div> 
   </div> 
  </div> 
  <div id="small-chat"> 
   <span class="badge badge-warning pull-right">5</span> 
   <a class="open-small-chat"> <i class="fa fa-comments"></i> </a> 
  </div>  
 </#macro>
 
 
 <#macro area_select>
  	<select data-placeholder="请选择资源地址..." class="chosen-select form-control" required="" id="resource_list" name="resource.id">
        <#list resourceList as vo>
        	<option value="${vo.id}" <#if menu.resource.id == vo.id>selected = "selected"</#if>>${vo.name}</option>
        </#list>
    </select>省-
    <select data-placeholder="请选择资源地址..." class="chosen-select form-control" required="" id="resource_list" name="resource.id">
        	<option value="null" >请先择省</option>
    </select>市-
    <select data-placeholder="请选择资源地址..." class="chosen-select form-control" required="" id="resource_list" name="resource.id">
        	<option value="null" >请先择xian</option>
    </select>
 </#macro>
 
 
 
  <#macro user_aut_info>
    <#if USER.userType == "company">
    <@autInfo >
    <#if autinfo.company.status != "admin">
    <#if autinfo.company.audit != "notapply">
  	<div class="row" style="padding-top: 30px;">
	  	<div class="col-sm-12">
	        <div class="tabs-container">
	            <ul class="nav nav-tabs" style="font-size: 18px;">
	            	<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">商户信息</a> </li>
	            	<#if autinfo.companyType.is_aut == true>
				  		<#if autinfo.companyType.idcard == true>
						<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">身份证信息</a> </li>
						</#if>
						<#if autinfo.companyType.driving_license == true>
						<li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">驾驶证信息</a> </li>
						</#if>
						<#if autinfo.companyType.business_license == true>
						<li class=""><a data-toggle="tab" href="#tab-4" aria-expanded="false">营业执照信息</a> </li>
						</#if>
					</#if> 
	            </ul>
	            <div class="tab-content">
		                <div id="tab-1" class="tab-pane active">
		                    <div class="ibox-content">
		                    	<div class="row" style="font-size: 18px;">
			                        <div  class="col-sm-4" style="padding-left: 3%;">
			                            <p><i class="fa green fa-user"></i><strong>商户名称：</strong><span>${autinfo.company.name!}</span></p>
			                            <p><i class="fa green fa-group"></i> <strong>商户类型：</strong><span>${autinfo.company.companyType.name!}</span></p>
			                            <p><i class="fa green fa-map"></i> <strong>所在区域：</strong><span>${autinfo.company.areaFullName!}</span></p>
			                            
		                            </div>
		                            <div  class="col-sm-4" style="padding-left: 3%;">
			                            <p><i class="fa green fa-user-secret"></i> <strong>认证状态：</strong>
			                            <#if autinfo.company.audit == "notapply">
			                           	 	<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;未提交认证审核</span>
			                            </#if>
                                        <#if autinfo.company.audit == "waitProcess">
			                           	 	<span class="label label-primary"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;等待审核</span>
			                            </#if>
			                            <#if autinfo.company.audit == "notAuth">
			                            	<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;审核未通过</span>
			                            </#if>
			                            <#if autinfo.company.audit == "auth">
			                            	<span class="label label-success"><i class="fa fa-truck"></i>&nbsp;&nbsp;审核通过</span>
			                            </#if>
			                            <#if autinfo.company.audit == "supplement">
			                            	<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;认证信息补录</span>;
			                            </#if> 
			                            <#if autinfo.company.audit == "waitProcessSupplement">
			                            	<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;补录等待审核</span>;
			                            </#if> 
			                            <p><i class="fa green fa-user"></i> <strong>联系人姓名：</strong><span >${autinfo.company.contactName!}</span></p>
			                            <p><i class="fa green fa-phone"></i> <strong>联系人电话：</strong><span >${autinfo.company.contactTel!}</span></p>
		                            </div>
		                            <div  class="col-sm-12" style="padding-left: 3%;">
		                            	<p><i class="fa green fa-map-marker" style="font-size: 21px;"></i><strong>详细地址：</strong><span>${autinfo.company.address!}</span></p>
		                            </div>
	                            </div>
	                        </div>
		                </div>
		                <#if autinfo.companyType.is_aut == true>
		                <#if autinfo.companyType.idcard == true>
		                <div id="tab-2" class="tab-pane">
		                    <div class="ibox-content">
		                    	<div class="row" style="font-size: 18px;">
			                        <div  class="col-sm-6" style="padding-left: 13%;">
			                            <p><i class="fa green fa-user"></i> <strong>姓名：</strong><span>${autinfo.idcard.name!}</span></p>
		                            </div>
		                            <div  class="col-sm-6" style="padding-left: 13%;">
			                            <p><i class="fa green fa-credit-card"></i> <strong>身份证号码：</strong><span>${autinfo.idcard.idcard_no!}</span></p>
		                            </div>
		                            <div class="col-sm-12">
		                                <div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_front_img_view_panel">
				                                    <div class="panel-heading">
				                                      	身份证正面
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div >
				                                    		 <div >
				                                    		 	<a class="fancybox" href="${WEB_PATH}${autinfo.idcard.idcard_front_img!}" title="身份证反面">
					                                         	<img id="idcard_front_img_view" src="${WEB_PATH}${autinfo.idcard.idcard_front_img!}" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
					                                         </div>
						                                </div>
				                                    </div>
				                                </div>
			                                </div>
		                            	</div>
		                            	<div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_back_img_view_panel">
				                                    <div class="panel-heading">
				                                      	身份证反面
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div>
					                                         <div >
					                                         	<a class="fancybox" href="${WEB_PATH}${autinfo.idcard.idcard_back_img!}" title="身份证反面">
					                                         	<img id="idcard_back_img_view" src="${WEB_PATH}${autinfo.idcard.idcard_back_img!}" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
					                                         </div>
						                                </div>
				                                    </div>
				                                </div>
			                                </div>
		                            	</div>
		                            	<div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_persoin_img_view_panel">
				                                    <div class="panel-heading">
				                                      	手持身份证照片
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div>
				                                    		 <div >
				                                    		 	<a class="fancybox" href="${WEB_PATH}<#if autinfo.idcard.idcard_persoin_img??>/resources/img/img_no.jpg<#else>${idcard.idcard_persoin_img}</#if>" title="手持身份证照片">
					                                         	<img id="idcard_persoin_img_view" src="${WEB_PATH}<#if autinfo.idcard.idcard_persoin_img??>/resources/img/img_no.jpg<#else>${idcard.idcard_persoin_img}</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
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
		                <#if autinfo.companyType.driving_license == true>
		                <div id="tab-3" class="tab-pane">
		                    <div class="ibox-content">
		                    	<div class="row" style="font-size: 18px;">
			                        <div  class="col-sm-4" style="padding-left: 3%;">
			                            <p><i class="fa green fa-user"></i> <strong>驾驶证姓名：</strong><span>${autinfo.drivingLicense.name!}</span></p>
			                            <p><i class="fa green fa-credit-card"></i> <strong>驾驶证编号：</strong><span>${autinfo.drivingLicense.driving_license_no!}</span></p>
		                            </div>
		                             <div  class="col-sm-4" style="padding-left: 3%;">
			                            <p><i class="fa green fa-intersex"></i> <strong>性别：</strong><span>${(autinfo.drivingLicense.sex == 1)?string('男','女')}</span></p>
			                            <p><i class="fa green fa-truck"></i> <strong> 准驾车型：</strong><span class="label label-success" ><i class="fa fa-truck"></i>&nbsp;${autinfo.drivingLicense.drivingLicenseType.no!}</span></p>
		                            </div>
		                            <div  class="col-sm-4" style="padding-left: 3%;">
			                            <p><i class="fa green fa-calendar"></i> <strong>有效起始日期：</strong><span>${autinfo.drivingLicense.valid_start_date!}</span></p>
			                            <p><i class="fa green fa-calendar"></i> <strong>有效年限：</strong><span>${autinfo.drivingLicense.valid_year!}</span></p>
		                            </div>
		                            <div  class="col-sm-12" style="padding-left: 3%;">
		                            	<p><i class="fa green fa-map-marker" style="font-size: 23px;"></i><strong>地址：</strong><span>${autinfo.drivingLicense.address!}</span></p>
		                            </div>
		                            <div class="col-sm-12">
		                                <div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_front_img_view_panel">
				                                    <div class="panel-heading">
				                                      	驾驶证图片
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div >
				                                    		 <div >
				                                    		 	<a class="fancybox" href="${WEB_PATH}${autinfo.drivingLicense.driving_license_img!}" title="驾驶证图片">
					                                         	<img id="idcard_front_img_view" src="${WEB_PATH}${autinfo.drivingLicense.driving_license_img!}" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
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
		                <#if autinfo.companyType.business_license == true>
		                <div id="tab-4" class="tab-pane">
		                    <div class="ibox-content">
		                    	<div class="row" style="font-size: 18px;">
			                        <div  class="col-sm-4" style="padding-left:3%;">
			                            <p><i class="fa green fa-bank"></i> <strong>企业名称：</strong><span>${autinfo.businessLicense.name!}</span></p>
			                            <p><i class="fa green fa-certificate"></i> <strong>营业执照编号：</strong><span>${autinfo.businessLicense.company_no!}</span></p>
		                            </div>
		                            <div  class="col-sm-4" style="padding-left: 3%;">
		                            	<p><i class="fa green fa-calendar"></i> <strong>有效截止日期：</strong><span>${autinfo.businessLicense.company_validity_date!}</span></p>
			                            
			                            <p><i class="fa green fa-calendar"></i> <strong>有效起始日期：</strong><span>${autinfo.businessLicense.company_create_date!}</span></p>
		                            </div>
		                            <div  class="col-sm-12" style="padding-left: 3%;">
			                             <p><i class="fa green fa-map-marker" style="font-size: 23px;"></i><strong>地址：</strong><span>${autinfo.businessLicense.company_address!}</span></p>
		                            </div>
		                            <div class="col-sm-12">
		                                <div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_front_img_view_panel">
				                                    <div class="panel-heading">
				                                      	营业执照照片
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div >
				                                    		 <div >
				                                    		 	<a class="fancybox" href="${WEB_PATH}${autinfo.businessLicense.company_img!}" title="营业执照照片">
					                                         	<img id="idcard_front_img_view" src="${WEB_PATH}${autinfo.businessLicense.company_img!}" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
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
	                </#if>
	            </div>
	        </div>
	    </div>
	</div>
	</#if>
	</#if>
	</@autInfo>
	</#if>
 </#macro>
 
 <#macro company_aut_info>
    <#if USER.userType == "company">
    <@autInfo >
  	<div class="row" style="padding-top: 30px;">
	  	<div class="col-sm-12">
	        <div class="tabs-container">
	            <ul class="nav nav-tabs" style="font-size: 18px;">
	            	<#if autinfo.companyType.is_aut == true>
				  		<#if autinfo.companyType.idcard == true>
						<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">身份证信息 
							<#if autinfo.company.aut_supplement_type?? && autinfo.company.aut_supplement_type?index_of("idcard")!=-1>
							<span class="label label-danger">补录信息</span>
							</#if>
						
						</a> </li>
						
						</#if>
						<#if autinfo.companyType.driving_license == true>
						<li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">驾驶证信息
							<#if autinfo.company.aut_supplement_type?? && autinfo.company.aut_supplement_type?index_of("driving")!=-1>
							<span class="label label-danger">补录信息</span>
							</#if>
						</a> </li>
						
						</#if>
						<#if autinfo.companyType.business_license == true>
						<li class=""><a data-toggle="tab" href="#tab-4" aria-expanded="false">营业执照信息
							<#if autinfo.company.aut_supplement_type?? && autinfo.company.aut_supplement_type?index_of("business")!=-1>
								<span class="label label-danger">补录信息</span>
							</#if>
						</a> </li>
						
						</#if>
					</#if> 
	            </ul>
	            <div class="tab-content">
		                <#if autinfo.companyType.is_aut == true>
		                <#if autinfo.companyType.idcard == true>
		                <div id="tab-2" class="tab-pane">
		                    <div class="ibox-content">
		                    	<div class="row" style="font-size: 18px;">
			                        <div  class="col-sm-6" style="padding-left: 13%;">
			                            <p><i class="fa green fa-user"></i> <strong>姓名：</strong><span>
			                            ${((autinfo.idcard.name)??)?string(autinfo.idcard.name,'<span class="label label-danger">无</span>')}
			                            </span></p>
		                            </div>
		                            <div  class="col-sm-6" style="padding-left: 13%;">
			                            <p><i class="fa green fa-credit-card"></i> <strong>身份证号码：</strong><span>
			                             ${((autinfo.idcard.idcard_no)??)?string(autinfo.idcard.idcard_no,'<span class="label label-danger">无</span>')}
			                            </span></p>
		                            </div>
		                            <div class="col-sm-12">
		                                <div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_front_img_view_panel">
				                                    <div class="panel-heading">
				                                      	身份证正面
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div >
				                                    		 <div >
				                                    		 	<a class="fancybox" href="${WEB_PATH}<#if autinfo.idcard.idcard_front_img??>${autinfo.idcard.idcard_front_img}<#else>/resources/img/img_no.jpg</#if>" title="身份证反面">
					                                         	<img id="idcard_front_img_view" src="${WEB_PATH}<#if autinfo.idcard.idcard_front_img??>${autinfo.idcard.idcard_front_img}<#else>/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
					                                         </div>
						                                </div>
				                                    </div>
				                                </div>
			                                </div>
		                            	</div>
		                            	<div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_back_img_view_panel">
				                                    <div class="panel-heading">
				                                      	身份证反面
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div>
					                                         <div >
					                                         	<a class="fancybox" href="${WEB_PATH}<#if autinfo.idcard.idcard_back_img??>${autinfo.idcard.idcard_back_img}<#else>/resources/img/img_no.jpg</#if>" title="身份证反面">
					                                         	<img id="idcard_back_img_view" src="${WEB_PATH}<#if autinfo.idcard.idcard_back_img??>${autinfo.idcard.idcard_back_img}<#else>/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
					                                         </div>
						                                </div>
				                                    </div>
				                                </div>
			                                </div>
		                            	</div>
		                            	<div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_persoin_img_view_panel">
				                                    <div class="panel-heading">
				                                      	手持身份证照片
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div>
				                                    		 <div >
				                                    		 	<a class="fancybox" href="${WEB_PATH}<#if autinfo.idcard.idcard_persoin_img?? && autinfo.idcard.idcard_persoin_img != "">${autinfo.idcard.idcard_persoin_img}<#else>/resources/img/img_no.jpg</#if>" title="手持身份证照片">
					                                         	<img id="idcard_persoin_img_view" src="${WEB_PATH}<#if autinfo.idcard.idcard_persoin_img?? && autinfo.idcard.idcard_persoin_img != "">${autinfo.idcard.idcard_persoin_img}<#else>/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
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
		                <#if autinfo.companyType.driving_license == true>
		                <div id="tab-3" class="tab-pane">
		                    <div class="ibox-content">
		                    	<div class="row" style="font-size: 18px;">
			                        <div  class="col-sm-4" style="padding-left: 3%;">
			                            <p><i class="fa green fa-user"></i> <strong>驾驶证姓名：</strong><span>
			                             ${((autinfo.drivingLicense.name)??)?string(autinfo.drivingLicense.name,'<span class="label label-danger">无</span>')}
			                            </span></p>
			                            <p><i class="fa green fa-credit-card"></i> <strong>驾驶证编号：</strong><span>
			                             ${((autinfo.drivingLicense.driving_license_no)??)?string(autinfo.drivingLicense.driving_license_no,'<span class="label label-danger">无</span>')}
			                            </span></p>
		                            </div>
		                             <div  class="col-sm-4" style="padding-left: 3%;">
			                            <p><i class="fa green fa-intersex"></i> <strong>性别：</strong><span>
			                              <#if (autinfo.drivingLicense.sex)??>
			                              	  ${(autinfo.drivingLicense.sex == 1)?string('男','女')}
			                              <#else>
			                              	  <span class="label label-danger">无</span>
			                              </#if>
			                          
			                            
			                            </span></p>
			                            <p><i class="fa green fa-truck"></i> <strong> 准驾车型：</strong><span class="label label-success" ><i class="fa fa-truck"></i>&nbsp;
			                             ${((autinfo.drivingLicense.drivingLicenseType.no)??)?string(autinfo.drivingLicense.drivingLicenseType.no,'<span class="label label-danger">无</span>')}
			                            </span></p>
		                            </div>
		                            <div  class="col-sm-4" style="padding-left: 3%;">
			                            <p><i class="fa green fa-calendar"></i> <strong>有效起始日期：</strong><span>
			                             ${((autinfo.drivingLicense.valid_start_date)??)?string(autinfo.drivingLicense.valid_start_date,'<span class="label label-danger">无</span>')}
			                            </span></p>
			                            <p><i class="fa green fa-calendar"></i> <strong>有效年限：</strong><span>
			                            ${((autinfo.drivingLicense.valid_year)??)?string(autinfo.drivingLicense.valid_year,'<span class="label label-danger">无</span>')}
			                            </span></p>
		                            </div>
		                            <div  class="col-sm-12" style="padding-left: 3%;">
		                            	<p><i class="fa green fa-map-marker" style="font-size: 23px;"></i><strong>地址：</strong><span>
		                            	${((autinfo.drivingLicense.address)??)?string(autinfo.drivingLicense.address,'<span class="label label-danger">无</span>')}
		                            	</span></p>
		                            </div>
		                            <div class="col-sm-12">
		                                <div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_front_img_view_panel">
				                                    <div class="panel-heading">
				                                      	驾驶证图片
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div >
				                                    		 <div >
				                                    		 	<a class="fancybox" href="${WEB_PATH}<#if autinfo.drivingLicense.driving_license_img??>${autinfo.drivingLicense.driving_license_img!}<#else>/resources/img/img_no.jpg</#if>" title="驾驶证图片">
					                                         	<img id="idcard_front_img_view" src="${WEB_PATH}<#if autinfo.drivingLicense.driving_license_img??>${autinfo.drivingLicense.driving_license_img!}<#else>/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
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
		                <#if autinfo.companyType.business_license == true>
		                <div id="tab-4" class="tab-pane">
		                    <div class="ibox-content">
		                    	<div class="row" style="font-size: 18px;">
			                        <div  class="col-sm-4" style="padding-left:3%;">
			                            <p><i class="fa green fa-bank"></i> <strong>企业名称：</strong><span>
			                            ${((autinfo.businessLicense.name)??)?string(autinfo.businessLicense.name,'<span class="label label-danger">无</span>')}
			                            </span></p>
			                            <p><i class="fa green fa-certificate"></i> <strong>营业执照编号：</strong><span>
			                            ${((autinfo.businessLicense.company_no)??)?string(autinfo.businessLicense.company_no,'<span class="label label-danger">无</span>')}
			                            </span></p>
		                            </div>
		                            <div  class="col-sm-4" style="padding-left: 3%;">
		                            	<p><i class="fa green fa-calendar"></i> <strong>有效截止日期：</strong><span>
		                            	   ${((autinfo.businessLicense.company_validity_date)??)?string(autinfo.businessLicense.company_validity_date,'<span class="label label-danger">无</span>')}
		                            	</span></p>
			                            
			                            <p><i class="fa green fa-calendar"></i> <strong>有效起始日期：</strong><span>
			                             ${((autinfo.businessLicense.company_create_date)??)?string(autinfo.businessLicense.company_create_date,'<span class="label label-danger">无</span>')}
			                            </span></p>
		                            </div>
		                            <div  class="col-sm-12" style="padding-left: 3%;">
			                             <p><i class="fa green fa-map-marker" style="font-size: 23px;"></i><strong>地址：</strong><span>
			                             ${((autinfo.businessLicense.company_address)??)?string(autinfo.businessLicense.company_address,'<span class="label label-danger">无</span>')}
			                             </span></p>
		                            </div>
		                            <div class="col-sm-12">
		                                <div class="col-sm-4">
		                                	<div class="form-group">
			                                    <div class="panel panel-primary" id="idcard_front_img_view_panel">
				                                    <div class="panel-heading">
				                                      	营业执照照片
				                                    </div>
				                                    <div class="panel-body">
				                                    	<div >
				                                    		 <div >
				                                    		 	<a class="fancybox" href="${WEB_PATH}<#if autinfo.businessLicense.company_img??>${autinfo.businessLicense.company_img!}<#else>/resources/img/img_no.jpg</#if>" title="营业执照照片">
					                                         	<img id="idcard_front_img_view" src="${WEB_PATH}<#if autinfo.businessLicense.company_img??>${autinfo.businessLicense.company_img!}<#else>/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
					                                         	</a>
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
	                </#if>
	            </div>
	        </div>
	    </div>
	</div>
	</@autInfo>
	</#if>
 </#macro>
 
 
 
 <!-- 前台页面底部-->
<#macro webFoot >
	<div class="foot" id="footer">
	    	<ul class="links ft">
	        	<li class="links_item no_extra"><a href="#" target="_blank">关于易林物流</a></li>
	            <li class="links_item"><a href="#" target="_blank">服务协议</a></li>
	            <li class="links_item"><a href="#">运营规范</a></li>
	            <li class="links_item"><a href="#" target="_blank">辟谣中心</a></li>
	            <li class="links_item"><a href="#" target="_blank">客服中心</a></li>
	            <li class="links_item"><a href="#" target="_blank">联系邮箱</a></li>
	            <li class="links_item"><a href="#" target="_blank">侵权投诉</a></li>
	            <li class="links_item"><p class="copyright">Copyright &copy; 2012-2016 Tencent. All Rights Reserved.</p> </li>
	    </ul>
	</div>
</#macro>
<!-- 前台页 面底部-->
 
 