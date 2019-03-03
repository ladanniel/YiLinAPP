<div style="" class="company ui-sortable"> 
    <div class="row">
       <div class="col-sm-12" style="padding-left: 3%;">
	      <p><i class="fa green fa-map-marker"></i><strong>详细地址：</strong><span>${((company.address)?? && company.address != '')?string(company.address,'<span class="label label-danger">无</span>')}</span></p>
	   </div>
	   <div class="col-sm-6" style="padding-left: 3%;">
		  <p><i class="fa green fa-user"></i><strong>创建人：</strong><span>${((createUser.name)?? && createUser.name != '')?string(createUser.name,createUser.phone)}</span></p>
		  <p><i class="fa green fa-user"></i><strong>修改人：</strong><span>${((updateUser.name)?? && updateUser.name != '')?string(updateUser.name,'<span class="label label-danger">无</span>')}</span></p> 
		  <p><i class="fa green fa-user"></i><strong>审核人：</strong><span>${((autUser.name)?? && autUser.name != '')?string(autUser.name,'<span class="label label-danger">无</span>')}</span></p> 
	   </div>
	   <div class="col-sm-6" style="padding-left: 3%;">
		  <p><i class="fa green fa-clock-o"></i><strong>创建时间：</strong><span>${company.create_time?string("yyyy-MM-dd HH:mm:ss")}</span></p>
		  <p><i class="fa green fa-clock-o"></i><strong>修改时间：</strong><span>
		  	<#if company.update_time?? && company.update_time != ''>
		  		${company.update_time?string("yyyy-MM-dd HH:mm:ss")}
		  	<#else>
		  		<span class="label label-danger">无</span>
		  	</#if>
		  </span></p> 
		  <p><i class="fa green fa-clock-o"></i><strong>审核时间：</strong><span>
		  	<#if company.aut_time?? && company.aut_time != ''>
		  		${company.aut_time?string("yyyy-MM-dd HH:mm:ss")}
		  	<#else>
		  		<span class="label label-danger">无</span>
		  	</#if>
		  </span></p>
		  
	   </div>
	   <#if company.audit?? && company.audit == 'notAuth'>
	   <div class="col-sm-12" style="padding-left: 3%;">
	      <p><i class="fa green fa-expeditedssl"></i><strong>原因：</strong><span style="color: red;">${((company.failed_msg)?? && company.failed_msg != '')?string(company.failed_msg,'<span class="label label-danger">无</span>')}</span></p>
	   </div>
	   </#if>
	</div>
</div>
<div style="" class="account ui-sortable"> 
	<div class="row">
		<div class="col-sm-6" style="padding-left: 3%;">
		  <p><i class="fa green fa-user"  ></i><strong>账号：</strong><span>${user.account}</span></p>
		  <p><i class="fa green fa-tablet"></i><strong>绑定手机：</strong><span>${user.phone}</span></p>
		  <p><i class="fa green fa-creative-commons"></i><strong>所属部门：</strong><span>${((user.companySection)?? && user.companySection.name != '')?string(user.companySection.name,'<span class="label label-danger">无</span>')}</span></p>
		  <p><i class="fa green fa-pied-piper"></i><strong>登陆ip：</strong><span>${((user.login_ip)?? && user.login_ip != '')?string(user.login_ip,'<span class="label label-danger">无</span>')}</span></p>
	    </div>
		<div class="col-sm-6" style="padding-left: 3%;">
		  <p><i class="fa green fa-user"></i><strong>姓名：</strong><span>${((user.name)?? && user.name != '')?string(user.name,'<span class="label label-danger">无</span>')}</span></p>
		  <p><i class="fa green fa-clock-o"></i><strong>注册时间：</strong><span>${user.create_time?string("yyyy-MM-dd HH:mm:ss")}</span></p>
		  <p><i class="fa green fa-group"></i><strong>所属角色：</strong><span>${user.role.name}</span></p>
		  <p><i class="fa green fa-truck"></i><strong>认证状态：</strong><span>
		    <#if user.authentication == "notapply">
           	 	<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;未提交认证审核</span>
            </#if>
            <#if user.authentication == "waitProcess">
           	 	<span class="label label-primary"><i class="fa fa-clock-o"></i>&nbsp;&nbsp;等待审核</span>
            </#if>
            <#if user.authentication == "notAuth">
            	<span class="label label-danger"><i class="fa fa-close"></i>&nbsp;&nbsp;审核未通过</span>
            </#if>
            <#if user.authentication == "auth">
            	<span class="label label-success"><i class="fa fa-truck"></i>&nbsp;&nbsp;审核通过</span>
            </#if>
            <#if user.authentication == "supplement">
            	<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;认证信息补录</span>
            </#if> 
            <#if user.authentication == "waitProcessSupplement">
            	<span class="label"><i class="fa fa-edit"></i>&nbsp;&nbsp;补录等待审核</span>
            </#if> 
		  </span></p>
	    </div>
	</div>
</div>