
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>商户信息认证</title>
    <meta name="keywords" content="齐帛谷物流平台-商户信息认证">
    <meta name="description" content="齐帛谷物流平台-商户信息认证">
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
	<link href="${WEB_PATH}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-title">
                  	  商户认证信息填写
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="panel panel-success">
                                <div class="panel-heading" style="font-size: 22px;">
                                	<i class="fa fa-info-circle"></i>
                                   	提示
                                </div>
                                <div class="panel-body" <#if company.audit == "waitProcess">style="text-align: center;"</#if>>
                                    <#if company.audit == "waitProcess">
                                    <div style="font-size: 29px;color: red;padding-top: 8%;padding-bottom: 8%;">
	                                	<i class="fa fa-clock-o" style="color: red;"></i>
	                                   	认证信息已成功提交，请等待管理员审核！
	                                </div>
	                                </#if>
	                                <#if company.audit == "failed">
	                                	 
	                                <div class="ibox-content" style="color: red;font-size: 14px;  padding: 10px; line-height: 30px;">
				                        <h2>尊敬的用户您好：</h2>
				                        <p style="padding-left: 40px;">
				                            	您所提交的认证资料，未通过管理员审核，原因如下：
				                            <br/>
				                            <span style="color: #14AF85;">${company.failed_msg}</span>
				                            <a href="${WEB_PATH}/aut/authenticat/failedIndex.do" id="idcard_back_img_view_btn"  class="btn btn-w-m btn-danger" target="yiling_iframe">
												<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">重新提交</span>
											</a>
				                        </p>
				                    </div>
				                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/ajax.extend.js"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
</body>

</html>