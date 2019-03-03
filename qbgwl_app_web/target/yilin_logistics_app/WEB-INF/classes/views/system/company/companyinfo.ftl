<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户信息</title>
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
     <@import.tableManagerImportScript/>
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

 <script>
 	$(function(){
 		$('.nav-tabs a:first').tab('show') 
 	})
 </script>   
<body class="gray-bg">
    <div class="row">
        <div class="col-sm-12">
            <div class="wrapper wrapper-content animated fadeInUp">
                <div class="ibox">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-10">
                                <div class="m-b-md">
                                   
                                   
                                </div>
                            </div>
                        </div>
                         <@userInfo >
                        <div class="row yico" style="font-size:18px;">
                            <div class="col-sm-5">
                                <dl class="dl-horizontal">
                                	<dt>
                                	 <div class="row">
                                	 	 <div class="col-sm-2">
                                	 	 		<i class="fa green fa-user"></i>
                                	 	 </div>
                                	 	 <div class="col-sm-8">
                                	 	 		商户名称：
                                	 	 </div>
                                	 </div>
                                	</dt>
                                    <dd>
                                     ${((USER.company.name)??)?string(USER.company.name,'<span class="label label-danger">无</span>')}
                                    </dd>

                                    <dt>
                                     <div class="row">
                                         <div class="col-sm-2">
                                	 	 		 <i class="fa green fa-group"></i>
                                	 	 </div>
                                	 	 <div class="col-sm-8">
                                	 	 		商户类型：
                                	 	 </div>
                                	 </div
                                    </dt>
                                    <dd>
                                     ${((USER.company.companyType.name)??)?string(USER.company.companyType.name,'<span class="label label-danger">无</span>')}
                                    </dd>
                                    <dt>
                                      <div class="row">
                                         <div class="col-sm-2">
                                	 	 		 <i class="fa green fa-map"></i>
                                	 	 </div>
                                	 	 <div class="col-sm-8">
                                	 	 		所在区域：
                                	 	 </div>
                                	 </div>
                                    </dt>
                                    <dd>
                                    ${((USER.company.areaFullName)??)?string(USER.company.areaFullName,'<span class="label label-danger">无</span>')}
                                    </dd>
                                    <dt>
                                    <div class="row">
                                         <div class="col-sm-2">
                                	 	 		<i class="fa green fa-map-marker" style="font-size: 21px;"></i>
                                	 	 </div>
                                	 	 <div class="col-sm-8">
                                	 	 		详细地址：
                                	 	 </div>
                                	 </div>
                                    </dt>
                                    <dd>
                                    ${((USER.company.address)??)?string(USER.company.address,'<span class="label label-danger">无</span>')}
                                    </dd>
                                </dl>
                            </div>
                            <div class="col-sm-7" id="cluster_info">
                                <dl class="dl-horizontal">

                                    <dt>
	                                     <div class="row">
	                                         <div class="col-sm-2">
	                                	 	 		<i class="fa green fa-user-secret"></i>
	                                	 	 </div>
	                                	 	 <div class="col-sm-8">
	                                	 	 		认证状态：
	                                	 	 </div>
	                                	 </div>
                                    </dt>
                                    <dd>
                                    	 <#if USER.company.audit == "notapply">
			                           	 	<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;未提交认证审核</span>
			                            </#if>
                                        <#if USER.company.audit == "waitProcess">
			                           	 	<span class="label label-primary"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;等待审核</span>
			                            </#if>
			                            <#if USER.company.audit == "notAuth">
			                            	<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;审核未通过</span>
			                            </#if>
			                            <#if USER.company.audit == "auth">
			                            	<span class="label label-success"><i class="fa fa-truck"></i>&nbsp;&nbsp;审核通过</span>
			                            </#if>
			                            <#if USER.company.audit == "supplement">
			                            	<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;认证信息补录</span>;
			                            </#if> 
			                            <#if USER.company.audit == "waitProcessSupplement">
			                            	<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;补录待审核</span>;
			                            </#if> 
                                    </dd>
                                    <dt>
                                    	 <div class="row">
	                                         <div class="col-sm-2">
	                                	 	 		<i class="fa green fa-user"></i>
	                                	 	 </div>
	                                	 	 <div class="col-sm-8">
	                                	 	 		    联系人姓名：
	                                	 	 </div>
	                                	 </div>
                                    </dt>
                                    <dd>${((USER.company.contactName)?? && USER.company.contactName != '')?string(USER.company.contactName,'<span class="label label-danger">无</span>')}</dd>
                                    <dt>
                                    	<div class="row">
	                                         <div class="col-sm-2">
	                                	 	 		<i class="fa green fa-phone"></i>
	                                	 	 </div>
	                                	 	 <div class="col-sm-8">
	                                	 	 		联系人电话：
	                                	 	 </div>
	                                	 </div>
                                    </dt>
                                    <dd>
                                       ${((USER.company.contactTel)??)?string(USER.company.contactTel,'<span class="label label-danger">无</span>')}
                                    </dd>
                                </dl>
                            </div>
                        </div>
                       
                        <#if USER.userType == "company" && USER.company.audit!="notapply">
			            	<@import.company_aut_info/>
			            </#if>
			            
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