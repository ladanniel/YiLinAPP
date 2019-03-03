<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户信息</title>
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
    <style>
    	.yico dt {
    		margin:5px;
    		padding-left:25px;
    	}
    	dd {
    		margin:10px;
    		padding-top: 5px;
    	}
    </style>
    <link href="${WEB_PATH}/resources/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="row">
        <div class="col-sm-12">
            <div class="wrapper wrapper-content animated fadeInUp">
                <div class="ibox">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="m-b-md">
                                	<#if !USER.name?? || USER.name == "" > 
                                    	<a href="javascript:editName();" class="btn btn-success btn-xs pull-right">填写真实姓名</a>
                                    </#if>
                                    <h2>
                                    	真实姓名：${((USER.name)?? && USER.name != "")?string(USER.name,'<span class="label label-danger" style="font-size: 18px;">无</span>')}
									</h2>
                                </div>
                            </div>
                        </div>
                         <@userInfo >
                        <div class="row yico" style="font-size:18px;">
                            <div class="col-sm-5">
                                <dl class="dl-horizontal">
                                	<dt style="background: url('${WEB_PATH}/resources/img/icon/account_img.png') no-repeat 0 0;background-size: 25px;">帐号：</dt>
                                    <dd>${USER.account}</dd>

                                    <dt style="background: url('${WEB_PATH}/resources/img/icon/status_img.png') no-repeat 0 0;background-size: 25px;">启用状态：</dt>
                                    <dd>
                                    	<#if USER.status == 1 >
                                    		<span class="label label-danger">关闭</span>
                                    	<#else>
                                    		<span class="label label-primary">启用</span>
                                    	</#if>
                                    </dd>
                                    <dt style="background: url('${WEB_PATH}/resources/img/icon/ip_img.png') no-repeat 0 0;background-size: 25px;" >登陆ip：</dt>
                                    <dd>${((USER.login_ip)?? && USER.login_ip != '')?string(USER.login_ip,'<span class="label label-danger">无</span>')}</dd>
                                    <dt  style="background: url('${WEB_PATH}/resources/img/icon/count_img.png') no-repeat 0 0;background-size: 25px;">登陆次数：</dt>
                                    <dd>${((USER.login_count)?? && USER.login_count != 0)?string(USER.login_count+"次",'<span class="label label-danger">无</span>')}</dd>
                                    <dt  style="background: url('${WEB_PATH}/resources/img/icon/phone_img.png') no-repeat 0 0;background-size: 25px;">绑定手机：</dt>
                                    <dd>
										<#if (USER.phone)?? && USER.phone != 0 >
											${USER.phone} <a href="javascript:editPhone()"  class="btn btn-success btn-xs pull-right">更换手机</a>
										<#else>
											<span class="label label-danger">无</span>
										</#if>
                                    	
										
									</dd>
                                </dl>
                            </div>
                            <div class="col-sm-7" id="cluster_info">
                                <dl class="dl-horizontal">

                                    <dt  style="background: url('${WEB_PATH}/resources/img/icon/time_img.png') no-repeat 0 0;background-size: 25px;">注册时间：</dt>
                                    <dd>
                                    	<#if USER.create_time?? >
                                    		${(USER.create_time)!?string("yyyy-MM-dd")!}
                                    	<#else>
                                    		<span class="label label-danger">无</span>
                                    	</#if>
                                    </dd>
                                    <dt  style="background: url('${WEB_PATH}/resources/img/icon/dept_img.png') no-repeat 0 0;background-size: 25px;">所属部门：</dt>
                                    <dd>${((userinfo.companySection.name)?? && userinfo.companySection.name != '')?string(userinfo.companySection.name,'<span class="label label-danger">无</span>')}</dd>
                                    <dt  style="background: url('${WEB_PATH}/resources/img/icon/role_img.png') no-repeat 0 0;background-size: 25px;">角色名称：</dt>
                                    <dd>
                                    	${((userinfo.role.name)?? && userinfo.role.name != '')?string(userinfo.role.name,'<span class="label label-danger">无</span>')}
                                    </dd>
                                    <dt style="background: url('${WEB_PATH}/resources/img/icon/aut_img.png') no-repeat 0 0;background-size: 25px;">认证状态：</dt>
                                    <dd>
                                    	<#if userinfo.account.authentication == "notapply">
			                           	 	<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;未提交认证审核</span>
			                            </#if>
                                        <#if userinfo.account.authentication == "waitProcess">
			                           	 	<span class="label label-primary"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;等待审核</span>
			                            </#if>
			                            <#if userinfo.account.authentication == "notAuth">
			                            	<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;审核未通过</span>
			                            </#if>
			                            <#if userinfo.account.authentication == "auth">
			                            	<span class="label label-success"><i class="fa fa-truck"></i>&nbsp;&nbsp;审核通过</span>
			                            </#if>
			                            <#if userinfo.account.authentication == "supplement">
			                            	<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;认证信息补录</span>
			                            </#if> 
			                            <#if userinfo.account.authentication == "waitProcessSupplement">
			                            	<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;补录等待审核</span>
			                            </#if> 
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <dl class="dl-horizontal">
                                    <dt style="font-size:18px;padding-right:15px;background: url('${WEB_PATH}/resources/img/icon/comple_img.png') no-repeat 0 0;background-size: 25px;padding-left:25px;">信息完成度：</dt>
                                    <dd>
                                        <div class="progress progress-striped active m-b-sm">
                                            <div style="width: 100%;" class="progress-bar"></div>
                                        </div>
                                        <small>当前完成信息进度的 <strong>100%</strong></small>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </@userInfo>
        <div class="col-sm-1">
            <div class="wrapper wrapper-content project-manager" >
            	
            </div>
        </div>
        <div id="win_edit"></div>
    </div>
    <@import.tableManagerImportScript/>
	<script src="${WEB_PATH }/resources/js/plugins/fancybox/jquery.fancybox.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
	<script>
		$(function(){
			$(".fancybox").fancybox({openEffect:"none",closeEffect:"none"}); 
		});  
		function editName() {
			$("#win_edit").load("${WEB_PATH }/system/user/view/editname.do");
		}
		function editPhone() {
			$("#win_edit").load("${WEB_PATH }/system/user/view/editphone.do");
		}
	</script>
</body>

</html>