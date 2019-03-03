
<script>
$(function(){
	$('#btn_pass').click(function(){
		$.messager.progress();
		$('#form_pass').form('submit', {  
			url: '${WEB_PATH }/system/user/pass.do',  
			cache : false,
			onSubmit: function(){  
				return validSubmit(this);
			},  
			success:function(result){  
				$.messager.progress('close'); 
				
				var data = $.parseJSON(result);
				if(data.success == true){
					$('#win_pass').dialog('close');
					showMsg(data.msg, 'success');
				}else{
					showErrorMsg(result);
				}
			}
		});
	});
	
	
});
</script>

<div class="easyui-layout" fit="true">
	<div region="center" border="false" class="centerdiv">
		<form id="form_pass" method="post">
			<table class="centertable">
				<tr>
					<td width="30%" align="right">
						原密码：
					</td>
					<td>
						<input class="easyui-validatebox" type="password" name="pass" required="true" />
					</td>
				</tr>
				
				<tr>
					<td align="right">
						新密码：
					</td>
					<td>
						<input class="easyui-validatebox" type="password" name="passa" required="true" />
					</td>
				</tr>
				
				<tr>
					<td align="right">
						确认新密码：
					</td>
					<td>
						<input class="easyui-validatebox" type="password" name="passb" required="true" />
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div region="south" class="buttomdiv">
		<a id="btn_pass" class="easyui-linkbutton" iconCls="icon-ok"
			href="javascript:void(0)">确认</a>

		&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="easyui-linkbutton" iconCls="icon-cancel"
			href="javascript:void(0)"
			onclick="$('#win_pass').window('close');">关闭</a>
	</div>
</div>