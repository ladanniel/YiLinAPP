<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>交易顺序排列</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        

        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>设置交易顺序</h5>
                <div class="ibox-tools"> 
                    
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">  
                    <div class="col-sm-12">
                        <!-- Example Events -->
                        <div class="example-wrap"> 
                            <div class="example"> 
                                <table class="footable table table-stripped toggle-arrow-tiny" data-page-size="8">
                                <thead>
                                <tr>

                                    <th data-toggle="true">交易账号</th>
                                    <th>交易类型</th>
                                    <th>是否充值</th>
                                    <th>是否转账</th>
                                    <th>是否提现</th>
                                    <th>交易顺序</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list list as vo>
                                	<tr>
	                                    <td>${vo.sourceAccount}</td>
	                                    <td>${vo.sourcType}</td>
	                                    <td><span class="label <#if vo.hasRecharge?string('true','false') == 'true'>label-primary<#else>label-warning</#if>">${vo.hasRecharge?string("是","否")}</span></td>
	                                    <td><span class="label <#if vo.hasTransfer?string('true','false') == 'true'>label-primary<#else>label-warning</#if>">${vo.hasTransfer?string("是","否")}</span></td>
	                                    <td><span class="label <#if vo.hasCash?string('true','false') == 'true'>label-primary<#else>label-warning</#if>">${vo.hasCash?string("是","否")}</span></td>
	                                    <td><span class="label label-success">${vo.sequenceNo}</span></td>
	                                    <td>${vo.create_time?string("yyyy-MM-dd HH:mm:ss")} </td>
	                                    <td>
	                                    	<#if vo_index == (list?size - 1)>
	                                    		<#if list?size == 1>
	                                    			<span class="label label-default">无</span>
	                                    		<#else>
	                                    			<a href="javascript:void(0)" onclick="submitService('${WEB_PATH }/capital/tradesequence/updateSequence.do?id=${vo.id}&sort=up')" title="上移"><i class="glyphicon glyphicon-arrow-up"></i></a>
	                                    		</#if>
	                                    	<#elseif vo_index == 0 >
	                                    		<a href="javascript:void(0)" onclick="submitService('${WEB_PATH }/capital/tradesequence/updateSequence.do?id=${vo.id}&sort=down')" title="下移"><i class="glyphicon glyphicon-arrow-down"></i></a>
	                                    	<#else>
	                                    		<a href="javascript:void(0)" onclick="submitService('${WEB_PATH }/capital/tradesequence/updateSequence.do?id=${vo.id}&sort=up')" title="上移"><i class="glyphicon glyphicon-arrow-up"></i></a>
	                                    		<a href="javascript:void(0)"  onclick="submitService('${WEB_PATH }/capital/tradesequence/updateSequence.do?id=${vo.id}&sort=down')" title="下移"><i class="glyphicon glyphicon-arrow-down"></i></a>
	                                    	</#if>
	                                    </td>
	                                </tr>
                                </#list>
                                </tbody>
                                
                            </table>
                            </div>
                        </div>
                        <!-- End Example Events -->
                    </div>
                </div>
            </div>
        </div>
        <!-- End Panel Other -->
    </div>
    <@import.tableManagerImportScript/> 
    <script>
    	function submitService(url_val){
    		swal({
				    title: "调整交易顺序",
				    text: "你是否要对交易顺序调整？",
				    type: "warning",
				    showCancelButton: true,
				    confirmButtonColor: "#DD6B55",
				    confirmButtonText: "是的，我要调整！",
				    cancelButtonText: "我点错了…",
				    closeOnConfirm: false,
				    closeOnCancel: false
			},function(isConfirm) {
				    if(isConfirm) { 
						$.ajax({
								  type: 'GET',
								  url: url_val,  
								  data: {},
								  dataType: "json",
								  success:function(result){  
										if(result.success == true){
											swal("", result.msg, "success");
											setTimeout(function () { 
										        window.location.reload();
										    }, 1000);
										}
								   },
								   error:function(data){
				                        var data = $.parseJSON(data.responseText);
								        swal("", data.msg, "error");
			                       }
						});
					}else {
			            swal("已取消", "您取消了操作！", "error")
			        }
				});
    	
    	}
    </script>
</body>

</html>