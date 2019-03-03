<div id="vertical-timeline" class="vertical-container light-timeline">
     <div class="vertical-timeline-block">
          <div class="vertical-timeline-icon blue-bg">
                  <i class="glyphicon glyphicon-time"></i>
          </div>
          <#if list?size == 0>
          	<div class="vertical-timeline-content">
          		<h2>无记录</h2>
          	</div>
          <#else>
          		<#if type = "goods">
          			<#list list as vo>
		           		 <div class="vertical-timeline-content">
			                 <h2>${vo.title}</h2>
			                 <p>当前状态：
	                 			 <#if vo.beforeStatus == 0>
	                 				<span class="label label-default">保存</span>
	                 			 <#elseif vo.beforeStatus == 1>
	                 				<span class="label label-default">申请发货</span>
	                 			 <#elseif vo.beforeStatus == 2>
	                 				<span class="label label-primary">正在处理</span>
	                 			 <#elseif vo.beforeStatus == 3>
	                 				<span class="label label-warning">退回</span>
	                 			 <#elseif vo.beforeStatus == 4>
	                 				<span class="label label-success">通过</span>
	                 			 <#elseif vo.beforeStatus == 5>
	                 				<span class="label label-danger">作废</span>
	                 			 </#if>
			                 </p>
			                 <@companyInfo accontId = vo.audit_person_id>
					                 <p>商户名：${userinfo.account.company.name}</p>
								     <p>商户类型：${userinfo.account.company.companyType.name}</p>
						     </@companyInfo>
			                 <p>操作人：${vo.audit_person}</p>
			                 <p>备注：${vo.remark}</p>
			                 <span class="vertical-date">
					               <small>${vo.create_time?string("yyyy-MM-dd HH:mm:ss")}</small>
					         </span>
			           </div>
		           </#list>
		        <#elseif type = "order">
		        	<#list list as vo>
	           		 <div class="vertical-timeline-content">
		                 <h2>${vo.title}</h2>
		                 <p>当前状态：
                 			 <#if vo.afterStatus == 0>
                 				<span class="label label-default">提交申请</span>
                 			 <#elseif vo.afterStatus == 1>
                 				<span class="label label-primary">处理中</span>
                 			 <#elseif vo.afterStatus == 2>
                 				<span class="label label-warning">退回</span>
                 			 <#elseif vo.afterStatus == 3>
                 				<span class="label label-success">成功</span>
                 			 <#elseif vo.afterStatus == 4>
                 				<span class="label label-danger">作废</span>
                 			 <#elseif vo.afterStatus == 5>
                 				<span class="label label-danger">撤回</span>
                 			 <#elseif vo.afterStatus == 6>
                 				<span class="label label-danger">结束</span>
                 			 </#if>
		                 </p>
		                 <@companyInfo accontId = vo.audit_person_id>
				                 <p>商户名：${userinfo.account.company.name}</p>
							     <p>商户类型：${userinfo.account.company.companyType.name}</p>
					     </@companyInfo>
		                 <p>操作人：${vo.audit_person}</p>
		                 <p>备注：${vo.remark}</p>
		                 <span class="vertical-date">
				               <small>${vo.create_time?string("yyyy-MM-dd HH:mm:ss")}</small>
				         </span>
		           </div>
	           </#list>
          		</#if>
          </#if>
     </div>
</div>

