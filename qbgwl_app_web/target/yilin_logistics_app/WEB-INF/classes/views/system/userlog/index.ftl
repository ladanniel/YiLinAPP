<#import "/common/import.ftl" as import>
<@import.systemImport/>
<script>
$(function(){
	var toolbar = [{
		text:'刷新',
		iconCls:'icon-reload',
		handler:function(){
			$('#grid_userlog').datagrid('reload');
		}
	}, {
		text:'查询',
		iconCls:'icon-query',
		handler:function(){
			var win = $('#win_query').dialog({  
				title: '用户查询'
			});
			win.dialog('open');
		}
	}];
	
	//用户查询
	$('#btn_query').click(function(){
		var account = $('#q_account').val();
		var date = $('#q_date').val();
		$('#grid_userlog').datagrid('load', {account: account, date: date});
		$('#win_query').window('close');
	});
	
	
	$('#grid_userlog').datagrid({
		toolbar: toolbar,
		onDblClickRow: function(rowIndex, data){
			
		},
		onLoadError: function(data){
			showErrorMsg(data);
		}
	});
	
});
</script>


<script>
function formatIsLog(val,row){
	if(val == '1'){
		return '是';
	}
	return '';
}
</script>


<table id="grid_userlog"
	 url="${WEB_PATH }/system/userlog/getPage.do" 
	 pagination="true"
	 fit="true" 
	 border="false" 
	 fitColumns="true" 
	 rownumbers="true" 
	 singleSelect="true" 
	 pageSize="${PAGE_SIZE }"
	 pageList="${PAGE_LIST }"
 >   
    <thead>  
        <tr>  
            <th field="id" width="120" hidden="true">id</th>  
            <th field="account" width="120">用户账号</th>  
            <th field="userName" width="120">姓名</th> 
            <th field="resourceName" width="120">操作功能</th>
			<th field="url" width="120">功能路径</th> 			
            <th field="ip" width="120" >访问地址</th> 
			<th field="date" width="120"  formatter="formatTime">操作时间</th> 
        </tr>  
    </thead>  
</table>


<div id="win_query" style="width:340px;height:200px;padding:5px;" data-options="cache:false,modal:true">
	<div region="center" border="false" class="centerdiv" style="height:90px">
			<table class="centertable">
				<tr>
					<td width="30%" align="right">
						帐号：
					</td>
					<td>
						<input class="easyui-validatebox" type="text" id="q_account" name="q_account"/>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						操作日期：
					</td>
					<td>
						<input
							class="easyui-validatebox" width="180px"
							style="border: 1px solid #A4BED4;" id="q_date" name="date"
							onclick="WdatePicker()" />
					</td>
				</tr>
			</table>
	</div>

	<div region="south" class="buttomdiv">
		<a id="btn_query" class="easyui-linkbutton" iconCls="icon-ok"
			href="javascript:void(0)">查询</a>

		&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="easyui-linkbutton" iconCls="icon-cancel"
			href="javascript:void(0)"
			onclick="$('#win_query').window('close');">关闭</a>
	</div>
</div>
