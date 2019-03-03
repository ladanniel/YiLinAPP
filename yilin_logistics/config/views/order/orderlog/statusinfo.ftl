<div id="vertical-timeline" class="vertical-container light-timeline">  
   <div class="vertical-timeline-block">
	   <div class="vertical-timeline-icon blue-bg">
	                  <i class="glyphicon glyphicon-time"></i>
	    </div>
	    <#list logList as log >
		    <div class="vertical-timeline-content">
		    					<#switch log.confirmStatus>
									  <#case "receiving">
										   <h2>${log.title}</h2>
								                 <p>当前状态：
						                 			<span class="label label-success">待装货</span>
								                 </p>
								                 <@companyInfo accontId = log.auditPersonId>
										                 <p>商户名：${userinfo.account.company.name}</p>
													     <p>商户类型：${userinfo.account.company.companyType.name}</p>
											     </@companyInfo>
								                 <p>操作人：${log.auditPerson}</p>
								                 <span class="vertical-date">
										               <small>${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</small>
										         </span>
									    <#break>
									    <#case "transit">
										   <h2>${log.title}</h2>
								                 <p>当前状态：
						                 			<span class="label label-success">运输中</span>
								                 </p>
								                 <@companyInfo accontId = log.auditPersonId>
										                 <p>商户名：${userinfo.account.company.name}</p>
													     <p>商户类型：${userinfo.account.company.companyType.name}</p>
											     </@companyInfo>
								                 <p>操作人：${log.auditPerson}</p>
								                 <span class="vertical-date">
										               <small>${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</small>
										         </span>
									    <#break>
								        <#case "delivered">
										   <h2>${log.title}</h2>
								                 <p>当前状态：
						                 			<span class="label label-success">送达</span>
								                 </p>
								                 <@companyInfo accontId = log.auditPersonId>
										                 <p>商户名：${userinfo.account.company.name}</p>
													     <p>商户类型：${userinfo.account.company.companyType.name}</p>
											     </@companyInfo>
								                 <p>操作人：${log.auditPerson}</p>
								                 <span class="vertical-date">
										               <small>${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</small>
										         </span>
									    <#break>
									    <#case "receipt">
										   <h2>${log.title}</h2>
								                 <p>当前状态：
						                 			<span class="label label-success">回执发还中</span>
								                 </p>
								                 <@companyInfo accontId = log.auditPersonId>
										                 <p>商户名：${userinfo.account.company.name}</p>
													     <p>商户类型：${userinfo.account.company.companyType.name}</p>
											     </@companyInfo>
								                 <p>操作人：${log.auditPerson}</p>
								                 <span class="vertical-date">
										               <small>${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</small>
										         </span>
									    <#break>
									   <#case "confirmreceipt">
										   <h2>${log.title}</h2>
								                 <p>当前状态：
						                 			<span class="label label-success">送还回执中</span>
								                 </p>
								                 <@companyInfo accontId = log.auditPersonId>
										                 <p>商户名：${userinfo.account.company.name}</p>
													     <p>商户类型：${userinfo.account.company.companyType.name}</p>
											     </@companyInfo>
								                 <p>操作人：${log.auditPerson}</p>
								                 <span class="vertical-date">
										               <small>${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</small>
										         </span>
									    <#break>
									      <#case "ordercompletion">
										   <h2>${log.title}</h2>
								                 <p>当前状态：
						                 			<span class="label label-success">订单完结</span>
								                 </p>
								                 <@companyInfo accontId = log.auditPersonId>
										                 <p>商户名：${userinfo.account.company.name}</p>
													     <p>商户类型：${userinfo.account.company.companyType.name}</p>
											     </@companyInfo>
								                 <p>操作人：${log.auditPerson}</p>
								                 <span class="vertical-date">
										               <small>${(log.create_time)?string("yyyy-MM-dd HH:mm:ss")}</small>
										         </span>
									    <#break>
									  <#default>
									</#switch>
			</div>
		</#list>
	</div>
</div>