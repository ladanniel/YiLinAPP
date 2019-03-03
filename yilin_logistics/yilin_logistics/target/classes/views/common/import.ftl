<#macro systemImportCss >
	<link rel="shortcut icon" href="${WEB_PATH}/resources/img/logo.png">
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
    <script src="${WEB_PATH}/resources/js/plugins/echarts/echarts-all.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/countUp/countUp.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.spline.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.resize.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.pie.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/flot/jquery.flot.symbol.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/peity/jquery.peity.min.js"></script>
    <script src="${WEB_PATH}/resources/js/content.min.js?v=1.0.0"></script>
    <script src="${WEB_PATH}/resources/js/plugins/jquery-ui/jquery-ui.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/easypiechart/jquery.easypiechart.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/sparkline/jquery.sparkline.min.js"></script>
    
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
	<!--[if lte IE 9]>
	<script src="${WEB_PATH}/resources/js/respond.js"></script>
	<script src="${WEB_PATH}/resources/js/html5shiv.js"></script>
	<![endif]-->
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
    <script src="${WEB_PATH}/resources/js/plugins/suggest/bootstrap-suggest.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script>
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
                            <span><i class="iconfont" style="color:#e7eaec;font-size:60px;font-style:normal;" >&#xe622;</i></span>
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
                                <#if user.capitalStatus == 'open'>
                                	<li><a class="J_menuItem" href="${WEB_PATH }/system/user/view/editPayPassword.do" data_false = "true">支付密码修改</a></li>
                                </#if>
                                 <#if user.role.is_aut && (user.authentication == "notAuth" || user.authentication == "notapply")>
	                            	 <li id="hideli_id"><a class="J_menuItem" href="${WEB_PATH }/aut/authenticat/index.do" data_false = "true">证件认证申请</a>  
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
                        <a class="J_menuItem"  href="${WEB_PATH }/system/admin/main.do">
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
                        <#else>
                        "
                        </#if>
                        <#if user.userType?string == "user" && user.capitalStatus != "open"> 
                        ${WEB_PATH}/${module.resource.url}" 
                        </#if>
                        <#if user.capitalStatus != "open" && module.resource.url != "#">class="J_menuItem" data_false = "true"</#if>>
                            <i class="iconfont" style="font-size:14px;font-style:normal;" ><#if module.iconcls>${module.iconcls}<#else>&#xe62d;</#if></i>
                            <span class="nav-label">${module.name}</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                        	<@menuPidInfo pid = module.id>
	                    	<#list menuviews as menu >
                            <li>
                            	<#if menu.name == '访问统计'>
                            		<a class="J_menuItem" target="_blank" href="javascript:void();" onclick="targetBlank('${menu.resource.url}')"   >${menu.name}</a>
                            	<#else>
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
                            	</#if>
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
        <script>
        	function targetBlank(url) {
        		window.open(url);
        	}
        </script>

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
				                                    		 	<a class="fancybox" href="${autinfo.idcard.idcard_front_img!}" title="身份证反面">
					                                         	<img id="idcard_front_img_view" src="${autinfo.idcard.idcard_front_img!}" style="width: 100%;border: 1px solid #e5e6e7;">
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
					                                         	<a class="fancybox" href="${autinfo.idcard.idcard_back_img!}" title="身份证反面">
					                                         	<img id="idcard_back_img_view" src="${autinfo.idcard.idcard_back_img!}" style="width: 100%;border: 1px solid #e5e6e7;">
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
				                                    		 	<a class="fancybox" href="<#if autinfo.idcard.idcard_persoin_img??>${WEB_PATH}/resources/img/img_no.jpg<#else>${idcard.idcard_persoin_img}</#if>" title="手持身份证照片">
					                                         	<img id="idcard_persoin_img_view" src="<#if autinfo.idcard.idcard_persoin_img??>${WEB_PATH}/resources/img/img_no.jpg<#else>${idcard.idcard_persoin_img}</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
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
				                                    		 	<a class="fancybox" href="${autinfo.drivingLicense.driving_license_img!}" title="驾驶证图片">
					                                         	<img id="idcard_front_img_view" src="${autinfo.drivingLicense.driving_license_img!}" style="width: 100%;border: 1px solid #e5e6e7;">
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
				                                    		 	<a class="fancybox" href="${autinfo.businessLicense.company_img!}" title="营业执照照片">
					                                         	<img id="idcard_front_img_view" src="${autinfo.businessLicense.company_img!}" style="width: 100%;border: 1px solid #e5e6e7;">
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
			                        <div  class="col-sm-6" style="padding-left: 3%;">
			                            <p><i class="fa green fa-user"></i> <strong>姓名：</strong><span>
			                            ${((autinfo.idcard.name)??)?string(autinfo.idcard.name,'<span class="label label-danger">无</span>')}
			                            </span></p>
		                            </div>
		                            <div  class="col-sm-6" style="padding-left: 18%;">
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
				                                    		 	<a class="fancybox" href="<#if autinfo.idcard.idcard_front_img??>${autinfo.idcard.idcard_front_img}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" title="身份证正面">
					                                         	<img id="idcard_front_img_view" src="<#if autinfo.idcard.idcard_front_img??>${autinfo.idcard.idcard_front_img}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
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
					                                         	<a class="fancybox" href="<#if autinfo.idcard.idcard_back_img??>${autinfo.idcard.idcard_back_img}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" title="身份证反面">
					                                         	<img id="idcard_back_img_view" src="<#if autinfo.idcard.idcard_back_img??>${autinfo.idcard.idcard_back_img}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
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
				                                    		 	<a class="fancybox" href="<#if autinfo.idcard.idcard_persoin_img?? && autinfo.idcard.idcard_persoin_img != "">${autinfo.idcard.idcard_persoin_img}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" title="手持身份证照片">
					                                         	<img id="idcard_persoin_img_view" src="<#if autinfo.idcard.idcard_persoin_img?? && autinfo.idcard.idcard_persoin_img != "">${autinfo.idcard.idcard_persoin_img}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
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
				                                    		 	<a class="fancybox" href="<#if autinfo.drivingLicense.driving_license_img??>${autinfo.drivingLicense.driving_license_img!}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" title="驾驶证图片">
					                                         	<img id="idcard_front_img_view" src="<#if autinfo.drivingLicense.driving_license_img??>${autinfo.drivingLicense.driving_license_img!}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
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
				                                    		 	<a class="fancybox" href="<#if autinfo.businessLicense.company_img??>${autinfo.businessLicense.company_img!}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" title="营业执照照片">
					                                         	<img id="idcard_front_img_view" src="<#if autinfo.businessLicense.company_img??>${autinfo.businessLicense.company_img!}<#else>${WEB_PATH}/resources/img/img_no.jpg</#if>" style="width: 100%;border: 1px solid #e5e6e7;">
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
 
 <#macro robordercount >
    <#if USER.userType == "company" && (USER.company.companyType.name == "车队" || USER.company.companyType.name == "个人司机")>
 	 <@robOrderCount >
	 <div class="col-sm-12" id="div3">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>抢单统计${user.id}</h5>
            </div>
            <div class="ibox-content" > 
                <div class="row">
                    <div class="col-md-7">
                          <ul class="stat-list m-t-lg">
                            <li class="text-center">
                                    <h1 style="  font-size: 60px; color:#00bb9c;" id="toolCount">
	                            		 0
	                            	</h1>
	                                <div class="font-bold"><i class="iconfont" style="color:#00bb9c;font-size:14px;font-style:normal;" >&#xe612;</i>抢单总量</div>
                            </li>
                            <li >
                                <div class="row " style="padding-top:20px; padding-left:20px;">
		                            <div class="col-sm-3  ">
		                                <h2 style="color:#00bb9c;" id="applyCount">0</h2>
		                                <div class="font-bold"><i class="iconfont" style="color:#00bb9c;font-size:14px;font-style:normal;" >&#xe618;</i>等待货主处理数量</div>
		                            </div>
		                            <div class="col-sm-3 ">
		                               <h2 style="color:#00bb9c;" id="dealingCount">0</h2>
		                                <div class="font-bold"><i class="iconfont" style="color:#00bb9c;font-size:14px;font-style:normal;" >&#xe614;</i>货主处理中数量 </div>
		                            </div>
		                            <div class="col-sm-3 ">
		                            	<h2 style="color:#00bb9c;" id="backCount">0</h2>
		                                <div class="font-bold"><i class="iconfont" style="color:#00bb9c;font-size:14px;font-style:normal;" >&#xe615;</i>退回数量</div>
		                            </div>
		                            <div class="col-sm-3 ">
		                                <h2 style="color:#00bb9c;" id="successCount">0</h2>
		                                <div class="font-bold"><i class="iconfont" style="color:#00bb9c;font-size:14px;font-style:normal;" >&#xe617;</i>货主确认成功数量</div>
		                            </div> 
		                        </div>
                            </li>
                            <li >
                                <div class="row " style="padding-top:20px;padding-left:20px;">
		                            <div class="col-sm-3 ">
		                               <h2 style="color:#00bb9c;" id="scrapCount">0</h2>
		                                <div class="font-bold"><i class="iconfont" style="color:#00bb9c;font-size:14px;font-style:normal;" >&#xe616;</i>作废数量</div>
		                            </div>
		                            <div class="col-sm-3 ">
		                            	<h2 style="color:#00bb9c;" id="withdrawCount">0</h2>
		                                <div class="font-bold"><i class="iconfont" style="color:#00bb9c;font-size:14px;font-style:normal;" >&#xe619;</i>撤回数量</div>
		                            </div>
		                            <div class="col-sm-3 ">
		                            	<h2 style="color:#00bb9c;" id="endCount">0</h2>
		                                <div class="font-bold"><i class="iconfont" style="color:#00bb9c;font-size:14px;font-style:normal;" >&#xe617;</i>已生成订单数量</div>
		                            </div>
		                        </div>
                            </li>
                        </ul>
                    </div>
                    <div class="col-md-5" style="padding-top:20px; ">
                         <div class="row text-center">
                         	<div   id="echarts-robordercount"  style="width: 100%;height:330px;padding-left:25px;"></div>
                         </div>
                    </div>
                </div>
            </div>
        </div> 
    </div>
    
    <script>
    window.onload = function() { 
    	 var l = echarts.init(document.getElementById("echarts-robordercount")),
	     u = {
	        title: {
	            text: "抢单统计占比",
	            x: "center"
	        },
	        tooltip: {
	            trigger: "item",
	            formatter: "{a} <br/>{b} : {c} ({d}%)"
	        },
	        color: [ '#C1232B', '#B5C334', '#FCCE10',
									'#E87C25', '#76EE00', '#FE8463', '#9BCA63',
									'#FAD860', '#F3A43B', '#60C0DD', '#D7504B',
									'#C6E579', '#F4E001', '#F0805A', '#26C0C0',
									'#FF00FF', '#C67171', '#97FFFF', '#3CB371'],
	        legend: {
	            orient: "vertical",
	            x: "left",
	            data: ["等待货主处理", "货主处理中","退回","货主确认成功","作废","撤回","抢单成功"]
	        },
	        calculable: !0,
	        series: [{
	            name: "抢单占比",
	            type: "pie", 
	            data: [{
	                value: ${robOrderCount.robcount.applyCount},
	                name: "等待货主处理"
	            },
	            {
	                value: ${robOrderCount.robcount.dealingCount},
	                name: "货主处理中"
	            },
	            {
	                value: ${robOrderCount.robcount.backCount},
	                name: "退回"
	            },
	            {
	                value: ${robOrderCount.robcount.successCount},
	                name: "货主确认成功"
	            },
	            {
	                value: ${robOrderCount.robcount.scrapCount},
	                name: "作废"
	            },
	            {
	                value: ${robOrderCount.robcount.withdrawCount},
	                name: "撤回"
	            },
	            {
	                value: ${robOrderCount.robcount.endCount},
	                name: "订单生成数量"
	            }]
	        }]
	    };
	    l.setOption(u), 
        $(window).resize(l.resize);
        var options = {
		  useEasing : true, 
		  useGrouping : true, 
		  separator : ',', 
		  decimal : '.', 
		  prefix : '', 
		  suffix : '' 
		};
        var toolCount = new CountUp("toolCount", 0, ${robOrderCount.robcount.toolCount}, 0, 2.5);
		toolCount.start();
		var applyCount = new CountUp("applyCount", 0, ${robOrderCount.robcount.applyCount}, 0, 2.5);
		applyCount.start();
		var dealingCount = new CountUp("dealingCount", 0, ${robOrderCount.robcount.dealingCount}, 0, 2.5);
		dealingCount.start();
		var backCount = new CountUp("backCount", 0, ${robOrderCount.robcount.backCount}, 0, 2.);
		backCount.start();
		var scrapCount = new CountUp("scrapCount", 0, ${robOrderCount.robcount.scrapCount}, 0, 2.5);
		scrapCount.start();
		var successCount = new CountUp("successCount", 0, ${robOrderCount.robcount.successCount}, 0, 2.5);
		successCount.start();
		var withdrawCount = new CountUp("withdrawCount", 0, ${robOrderCount.robcount.withdrawCount}, 0, 2.5);
		withdrawCount.start();
		var endCount = new CountUp("endCount", 0, ${robOrderCount.robcount.endCount}, 0, 2.5);
		endCount.start();
	}
    </script> 
    </@robOrderCount>
    </#if>
</#macro>
 <!-- 用户资金统计-->
<#macro capitalAccountInfo >
	<@capitalManage>
		<#if capitalManage.type == "open">
			<div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>平台资金概况</h5>
                    </div>
                    <div class="ibox-content">  
                    	<div style="padding-left:25px;font-size: 16px;margin-top: 12px;">
                    		<span class="col-sm-4">总额：<i class="fa fa-cny"></i>&nbsp;<#if capitalManage.capitalInfo.total == "" || null == capitalManage.capitalInfo.total>0<#else>${capitalManage.capitalInfo.total}</#if></span>
	                    	<span class="col-sm-4">余额：<i class="fa fa-cny"></i>&nbsp;<#if capitalManage.capitalInfo.avaiable == "" || null == capitalManage.capitalInfo.avaiable>0<#else>${capitalManage.capitalInfo.avaiable}</#if></span>
	                    	<span class="col-sm-4">冻结资金：<i class="fa fa-cny"></i>&nbsp;<#if capitalManage.capitalInfo.frozen == "" || null == capitalManage.capitalInfo.frozen>0<#else>${capitalManage.capitalInfo.frozen}</#if></span>
                    	</div>
		                <div id="echarts-capital-manage"  style="width: 100%;height:330px;padding-left:25px;margin-top: 58px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>平台交易统计</h5>
                    </div>
                    <div class="ibox-content">  
                    	<#if capitalManage.moneyRecord.names?size == 0>
                    		<div   style="width: 100%;height:330px;padding-left:25px;margin-top: 58px;">
                    			<div style="font-size: 18px;color: red;margin-left:40%;">无交易记录</div>
                    		</div>
                    	<#else>	
                    		<div id="echarts-moneyrecord-manage"  style="width: 100%;height:330px;padding-left:25px;margin-top: 58px;"></div>
                    	</#if>
                    </div>
                </div>
         </div>
            <script>
     			$(function() {
     				
     				var reports = echarts.init(document.getElementById("echarts-capital-manage")),
					     options = {
					        title: {
					            text: "平台资金统计占比",
					            x: "center"
					        },
					        tooltip: {
					            trigger: "item",
					            formatter: "{a} <br/>{b} : {c} ({d}%)"
					        },
					        legend: {
					            orient: "vertical",
					            x: "left",
					            data: ["平台用户可用余额", "平台用户冻结资金"]
					        },
					        color:['#4f9090','#8E8E8E'] ,
					        calculable: !0,
					        series: [{
					            name: "资金比例",
					            type: "pie", 
					            data: [{
					                value: <#if capitalManage.capitalInfo.avaiable == "" || null == capitalManage.capitalInfo.avaiable>0<#else>${capitalManage.capitalInfo.avaiable}</#if>,
					                name: "平台用户可用余额"
					            },
					            {
					                value: <#if capitalManage.capitalInfo.frozen == "" || null == capitalManage.capitalInfo.frozen>0<#else>${capitalManage.capitalInfo.frozen}</#if>,
					                name: "平台用户冻结资金"
					            }]
					        }]
					    };
					    reports.setOption(options), 
				        $(window).resize(reports.resize);
				        var reports_moneyrecord = echarts.init(document.getElementById("echarts-moneyrecord-manage")),
					     option_moneyrecord = {
						    tooltip: {
						        trigger: 'item',
						        formatter: "{a} <br/>{b}: {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        x: 'left',
						        data:${capitalManage.moneyRecord.names}
						    },
						    series: [
						        {
						            name:'平台用户交易额',
						            type:'pie',
						            radius: ['50%', '70%'],
						            avoidLabelOverlap: true,
						            label: {
						                normal: {
						                    show: true,
						                    position: 'center',
						                    textStyle: {
						                        fontSize: '16',
						                        fontWeight: 'bold'
						                    }
						                },
						                emphasis: {
						                    show: true,
						                    textStyle: {
						                        fontSize: '20',
						                        fontWeight: 'bold'
						                    }
						                }
						            },
						            labelLine: {
						                normal: {
						                    show: false
						                }
						            },
						            data:${capitalManage.moneyRecord.vals}
						        }
						    ]
						};
					    reports_moneyrecord.setOption(option_moneyrecord), 
				        $(window).resize(reports_moneyrecord.resize);
     			});
     		</script>
		</#if>
	</@capitalManage>
	<@capitalAccountDirective >
	<#if capitalAccountDirective.status == "open">
	<div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>资金概况</h5>
                    </div>
                    <div class="ibox-content">  
                    	<div style="padding-left:25px;font-size: 16px;margin-top: 12px;">
                    		<span class="col-sm-4">账户总额：<i class="fa fa-cny"></i>&nbsp;${capitalAccountDirective.capitalAccount.total}</span>
	                    	<span class="col-sm-4">可用余额：<i class="fa fa-cny"></i>&nbsp;${capitalAccountDirective.capitalAccount.avaiable}</span>
	                    	<span class="col-sm-4">冻结资金：<i class="fa fa-cny"></i>&nbsp;${capitalAccountDirective.capitalAccount.frozen}</span>
                    	</div>
		                <div id="echarts-capital"  style="width: 100%;height:330px;padding-left:25px;margin-top: 58px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>交易统计</h5>
                    </div>
                    <div class="ibox-content"> 
                    	<#if capitalAccountDirective.moneyRecord.names?size == 0>
                    		<div   style="width: 100%;height:330px;padding-left:25px;margin-top: 58px;">
                    			<div style="font-size: 18px;color: red;margin-left:40%;">无交易记录</div>
                    		</div>
                    	<#else>	
                    		<div id="echarts-moneyrecord"  style="width: 100%;height:330px;padding-left:25px;margin-top: 58px;"></div> 
                    	</#if>
                    </div>
                </div>
         </div>
         <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>交易月统计</h5>
                    </div>
                    <div class="ibox-content"> 
                    	<div id="echarts-moneyrecord-month"  style="width: 100%;height:330px;padding-left:25px;margin-top: 58px;"></div> 
                    </div>
                </div>
         </div>
     <script>
     	$(function() {
     		 var reports = echarts.init(document.getElementById("echarts-capital")),
		     options = {
		        title: {
		            text: "资金统计占比",
		            x: "center"
		        },
		        tooltip: {
		            trigger: "item",
		            formatter: "{a} <br/>{b} : {c} ({d}%)"
		        },
		        legend: {
		            orient: "vertical",
		            x: "left",
		            data: ["可用余额", "冻结资金"]
		        },
		        color:['#4f9090','#8E8E8E'] ,
		        calculable: !0,
		        series: [{
		            name: "资金比例",
		            type: "pie", 
		            data: [{
		                value: ${capitalAccountDirective.capitalAccount.avaiable},
		                name: "可用余额"
		            },
		            {
		                value: ${capitalAccountDirective.capitalAccount.frozen},
		                name: "冻结资金"
		            }]
		        }]
		    };
		    reports.setOption(options), 
	        $(window).resize(reports.resize);
	        var reports_moneyrecord = echarts.init(document.getElementById("echarts-moneyrecord")),
		     option_moneyrecord = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data:${capitalAccountDirective.moneyRecord.names}
    },
    series: [
        {
            name:'交易额',
            type:'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: true,
            label: {
                normal: {
                    show: true,
                    position: 'center',
                    textStyle: {
                        fontSize: '24',
                        fontWeight: 'bold'
                    }
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:${capitalAccountDirective.moneyRecord.vals}
        }
    ]
};
		    reports_moneyrecord.setOption(option_moneyrecord), 
	        $(window).resize(reports_moneyrecord.resize);
	        var reports_moneyrecord_month = echarts.init(document.getElementById("echarts-moneyrecord-month")),
		     option_moneyrecord_month = {
		     	 title : {
					text: '近半年的交易记录统计',
		            subtext: ''
				},
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['充值','提现','转账','收款','运输款','仲裁赔付']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : ${capitalAccountDirective.moneyRecordTypes.dataType}
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'充值',
			            type:'bar',
			            data:${capitalAccountDirective.moneyRecordTypes.dataType1},
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            }
			        },
			        {
			            name:'提现',
			            type:'bar',
			            data:${capitalAccountDirective.moneyRecordTypes.dataType2},
			            markPoint : {
			                data : [
			                     {type : 'max', name: '最大值'},
			                     {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
			        },
			        {
			            name:'转账',
			            type:'bar',
			            data:${capitalAccountDirective.moneyRecordTypes.dataType3},
			            markPoint : {
			                data : [
			                     {type : 'max', name: '最大值'},
			                     {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
			        },
			        {
			            name:'收款',
			            type:'bar',
			            data:${capitalAccountDirective.moneyRecordTypes.dataType4},
			            markPoint : {
			                data : [
			                     {type : 'max', name: '最大值'},
			                     {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
			        },
			        {
			            name:'运输款',
			            type:'bar',
			            data:${capitalAccountDirective.moneyRecordTypes.dataType5},
			            markPoint : {
			                data : [
			                     {type : 'max', name: '最大值'},
			                     {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
			        },
			        {
			            name:'仲裁赔付',
			            type:'bar',
			            data:${capitalAccountDirective.moneyRecordTypes.dataType6},
			            markPoint : {
			                data : [
			                     {type : 'max', name: '最大值'},
			                     {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
			        }
			    ]
			};
		    reports_moneyrecord_month.setOption(option_moneyrecord_month), 
	        $(window).resize(reports_moneyrecord_month.resize);
	    });
    </script>
    </#if>
    </@capitalAccountDirective>
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
	            <li class="links_item"><script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1259976653'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1259976653%26show%3Dpic1' type='text/javascript'%3E%3C/script%3E"));</script></li>
	    </ul>
	</div>
</#macro>
<!-- 前台页 面底部-->
 
 