
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="renderer" content="webkit">
        <meta charset="utf-8">
        <meta name="baidu-site-verification" content="DLH0DxvBfi">
        <title>易林物流平台</title>
        
      	<link href="${WEB_PATH}/resources/css/web/login.css?v=4.0.0" rel="stylesheet"> 
      	<#import "/common/import.ftl" as import>
    	<@import.loginImport/>
    	
    	
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
    <script type="text/javascript">
    
    	$(function(){
    		$("#verifyImg").on('click',function(){
    			$(this).attr("src","${WEB_PATH }/commonFtl/image.jsp?_="+new Date().getTime());
    		});
    	});
	
		//服务器校验
		function severCheck(){
			if(check()){
				var ii = layer.load(); 
				var loginname = $("#loginname").val();
				var password = $("#password").val();
				var code = $("#code").val();
				$.ajax({
					url : '${WEB_PATH }/system/user/login.do',  
					cache : false,
					type : 'POST',
					data: {account: loginname, pass: password, code: code},
					success : function(result) { 
						var data = $.parseJSON(result);
						if(data.success == true){ 
							top.location.href="${WEB_PATH }/system/admin/index.do";
						}else{
						   $(".err_tips").text(data.msg);
							//layer.msg(data.msg);
						}
						layer.close(ii);
					},
					error: function(result) {
						showErrorMsg(result);
					}
				});
			}
		}
	
		 
		 
 
		//客户端校验
		function check() { 
			if ($("#loginname").val() == "") { 
				$(".err_tips").text("用户名不能为空!");
				//layer.tips('用户名不能为空!', '#loginname');
				$("#loginname").focus();
				return false;
			}  
			if ($("#password").val() == "") {
			    $(".err_tips").text("密码不能为空!");
 				//layer.tips('密码不能为空!', '#password');
				$("#password").focus();
				return false;
			}
			if ($("#code").val() == "") {
			    $(".err_tips").text("验证码不能为空!");
 				//ayer.tips('验证码不能为空!', '#code'); 
				$("#code").focus();
				return false;
			} 
			return true;
		}

	 function _submit(){     
		if (event.keyCode == 13) 
		{      
			severCheck();
		}
	 }
		 
	</script>
    	
    	
    </head>
    <body class="zh_CN">
        <div class="head" id="header">
            <div class="head_box">
                <div class="inner wrp">
                    <h1 class="logo">
                        <a href="#" title="易林物流平台">易林物流平台</a>
                    </h1>
                    <div class="account">
                        <div class="account_meta account_faq">
                            第一次使用易林物流平台？                            <a href='${WEB_PATH}/account/register/index.do'>立即注册</a><i class="account_line">|</i><a href="#" target="_blank">使用帮助</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="banner">
                <div class="inner wrp">
                    <div class="login_frame">
                            <h3>登录</h3>
                            <div class="login_err_panel" style="" id="err"><i class="icon18_common error"></i><span class="err_tips"></span></div>
                            <form class="login_form" id="loginForm">
                                <div class="login_input_panel" id="js_mainContent">
                                    <div class="login_input">
                                        <i class="icon_login un"> </i>
                                        <input type="text" placeholder="账号/手机" id="loginname">
                                    </div>
                                    <div class="login_input">
                                        <i class="icon_login pwd"> </i>
                                        <input type="password" placeholder="密码" id="password" name="password">
                                    </div>
                                </div>
                                <div class="verifycode" style="" id="verifyDiv">
                                    <span class="frm_input_box">
                                        <input class="frm_input" type="text" id="code" name="verify" onkeypress="_submit()">
                                    </span>
                                    <img id="verifyImg" src="${WEB_PATH }/commonFtl/image.jsp">
                                   <!-- <a href="javascript:;" id="verifyChange">换一张</a>-->
                                </div>
                                <div class="login_help_panel">
                                    <!-- <label class="frm_checkbox_label" for="rememberCheck">
                                        <i class="icon_checkbox"></i>
                                        <input type="checkbox" class="frm_checkbox" id="rememberCheck">
                                        记住帐号
                                    </label> -->
                                   <a class="login_forget_pwd" href="${WEB_PATH}/account/register/resetpwdview.do">无法登录？</a>
                                </div>
                                <div class="login_btn_panel">
                                    <a class="btn_login" title="点击登录"  onClick="severCheck()" href="javascript:" id="loginBt">登录</a>
                                </div>
                            </form>
                        </div>
                </div>
            </div>
        </div>

        <div id="body" class="body page_login">

            
     <!--        <dl class="notices_box">
                <dt>
                    <i class="icon_login speaker"></i>系统公告
                </dt>
                                       
                                <dd>
                    <i>●</i>
                    <a class="notices_title" href="/cgi-bin/announce?action=getannouncement&key=1461899678&version=3&lang=zh_CN" target="_blank" >
                        用户管理优化，支持标签功能                    </a>
                    <span class="label_new"></span>
                </dd>
                
                                <dd>
                    <i>●</i>
                    <a class="notices_title" href="/cgi-bin/announce?action=getannouncement&key=1461077225&version=1&lang=zh_CN" target="_blank" >
                        微信浏览器全面升级至X5&nbsp;Blink内核，支持远程调试                    </a>
                    <span class="label_new"></span>
                </dd>
                
                                  
                <dd class="extra">
                    <a href="/cgi-bin/announce?action=getannouncementlist&lang=zh_CN" target="_blank" >
                        查看更多                        <i class="icon_arrow"></i>
                    </a>
                </dd>
            </dl>
 -->
            
            <div class="mp_kind_mod">
                <div class="mp_kind_mod_hd">
                    <h3>帐号分类</h3>
                </div>
                <div class="mp_kind_mod_bd group">
                    <a href="#" target="_blank" class="mp_kind_wrp">
                        <dl class="mp_kind">
                            <dt class="name"><span class="icon_mp_kind service"></span>个人</dt>
                            <dd>给企业和组织提供更强大的业务服务与用户管理能力，帮助企业快速实现全新的公众号服务平台。 </dd>
                        </dl>
                    </a>
                    <a href="#" target="_blank" class="mp_kind_wrp">
                        <dl class="mp_kind">
                            <dt class="name"><span class="icon_mp_kind subscribe"></span>货主</dt>
                            <dd>为媒体和个人提供一种新的信息传播方式，构建与读者之间更好的沟通与管理模式。</dd>
                        </dl>
                    </a>
                    <a href="#" class="mp_kind_wrp">
                        <dl class="mp_kind">
                            <dt class="name"><span class="icon_mp_kind enterprise"></span>车队</dt>
                            <dd>为企业或组织提供移动应用入口，帮助企业建立与员工、上下游供应链及企业应用间的连接。</dd>
                        </dl>
                    </a>
                </div>
            </div>

            
        <!--     <div class="mp_case_mod">
                <div class="mp_case_mod_hd">
                    <h3 class="mp_case_mod_t">成功案例</h3>
                </div>
                <div class="mp_case_mod_bd js_mpCaseModBd">
                    <ul class="mp_case_list" id="iconList">
                        <li class="mp_case_item jsIconItem" name="zshyh" data-index="0">
                            <span class="icon_wrapper">
                                <i class="icon_mp_case zshyh jsIcon"></i>
                            </span>
                            <h4 class="mp_case_t">招行信用卡</h4>
                        </li>
                        <li class="mp_case_item jsIconItem" name="nfhk" data-index="1">
                            <span class="icon_wrapper">
                                <i class="icon_mp_case nfhk jsIcon"></i>
                            </span>
                            <h4 class="mp_case_t">南方航空</h4>
                        </li>
                        <li class="mp_case_item jsIconItem" name="gdlt" data-index="2">
                            <span class="icon_wrapper">
                                <i class="icon_mp_case gdlt jsIcon"></i>
                            </span>
                            <h4 class="mp_case_t">广东联通</h4>
                        </li>
                        <li class="mp_case_item jsIconItem" name="ysxw" data-index="3">
                            <span class="icon_wrapper">
                                <i class="icon_mp_case ysxw jsIcon"></i>
                            </span>
                            <h4 class="mp_case_t">央视新闻</h4>
                        </li>
                        <li class="mp_case_item jsIconItem" name="police" data-index="4">
                            <span class="icon_wrapper">
                                <i class="icon_mp_case police jsIcon"></i>
                            </span>
                            <h4 class="mp_case_t">广州公安</h4>
                        </li>
                        <li class="mp_case_item jsIconItem" name="hwzncd" data-index="5">
                            <span class="icon_wrapper">
                                <i class="icon_mp_case hwzncd jsIcon"></i>
                            </span>
                            <h4 class="mp_case_t">华为运动健康</h4>
                        </li>
                        <li class="mp_case_item jsIconItem" name="midea" data-index="6" style="margin-right:0px;">
                            <span class="icon_wrapper">
                                <i class="icon_mp_case midea jsIcon"></i>
                            </span>
                            <h4 class="mp_case_t">美的</h4>
                        </li>
                    </ul>

                    <div class="default_wrapper mp_case_desc group">

                        
                        <div class="mp_case_desc_c jsImg">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/zshyh_l23b6fe.jpg"class="mp_case_desc_img">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/zshyh_r23b6fe.jpg"class="mp_case_desc_img extra">
                            <p class="mp_case_desc_text">
                                <strong class="case_desc_title">招行信用卡<span class="case_desc_title_info">服务号</span></strong>
                                <span class="case_desc_intro"> 如果你是持卡人，可快捷查询信用卡账单、额度及积分；快速还款、申请账单分期；微信转接人工服务；信用卡消费，微信免费笔笔提醒。如果不是持卡人，可以微信办卡！                                </span>
                            </p>
                            <span class="arrow">
                                <i class="arrow_out"></i>
                                <i class="arrow_in"></i>
                            </span>
                        </div>

                        
                        <div class="mp_case_desc_c jsImg">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/nfhk_l23b6fe.jpg"class="mp_case_desc_img">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/nfhk_r23b6fe.jpg"class="mp_case_desc_img extra">
                            <p class="mp_case_desc_text">
                                <strong class="case_desc_title">南方航空<span class="case_desc_title_info">服务号</span></strong>
                                <span class="case_desc_intro">
                                    你可以办理值机手续，挑选座位，查询航班信息，查询目的地城市天气，并为明珠会员提供专业的服务。                                </span>
                            </p>
                            <span class="arrow" style="left:188px;">
                                <i class="arrow_out"></i>
                                <i class="arrow_in"></i>
                            </span>
                        </div>

                        
                        <div class="mp_case_desc_c jsImg">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/gdlt_l23b6fe.jpg"class="mp_case_desc_img">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/gdlt_r23b6fe.jpg"class="mp_case_desc_img extra">
                            <p class="mp_case_desc_text">
                                <strong class="case_desc_title">广东联通<span class="case_desc_title_info">服务号</span></strong>
                                <span class="case_desc_intro">
                                    你可以在微信里绑定手机号、积分流量，套餐余量、手机上网流量，微信专属流量查询，客服咨询。                                </span>
                            </p>
                            <span class="arrow" style="left:339px;">
                                <i class="arrow_out"></i>
                                <i class="arrow_in"></i>
                            </span>
                        </div>

                        
                        <div class="mp_case_desc_c jsImg">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/ysxw_l23b6fe.jpg"class="mp_case_desc_img">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/ysxw_r23b6fe.jpg"class="mp_case_desc_img extra">
                            <p class="mp_case_desc_text">
                                <strong class="case_desc_title">央视新闻<span class="case_desc_title_info">订阅号</span></strong>
                                <span class="case_desc_intro">    中央电视台新闻中心官方公众帐号，负责央视新闻频道、综合频道、中文国际频道的资讯及新闻性专栏节目以及英语新闻频道、西班牙语、法语等频道的采制、编播。                                </span>
                            </p>
                            <span class="arrow" style="left:491px">
                                <i class="arrow_out"></i>
                                <i class="arrow_in"></i>
                            </span>
                        </div>

                        
                        <div class="mp_case_desc_c jsImg">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/police_l23b6fe.jpg"class="mp_case_desc_img">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/police_r23b6fe.jpg"class="mp_case_desc_img extra">
                            <p class="mp_case_desc_text">
                                <strong class="case_desc_title">广州公安<span class="case_desc_title_info">服务号</span></strong>
                                <span class="case_desc_intro">    广州公安微信平台为您提供最新最快警务资讯、办事指南，您可在此查询交通违法信息、业务办理进度、路况动态资讯，预约出入境和户政业务办理，还可直接办理往来港澳通行证再次签注。                                </span>
                            </p>
                            <span class="arrow" style="left:641px;">
                                <i class="arrow_out"></i>
                                <i class="arrow_in"></i>
                            </span>
                        </div>

                        
                        <div class="mp_case_desc_c jsImg">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/hwzncd_l23b6fe.jpg"class="mp_case_desc_img">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/hwzncd_r23b6fe.jpg"class="mp_case_desc_img extra">
                            <p class="mp_case_desc_text">
                                <strong class="case_desc_title">华为运动健康<span class="case_desc_title_info">服务号</span></strong>
                                <span class="case_desc_intro">
                                    华为荣耀手环 能通话的健康手环！全球首创蓝牙耳机与运动手环的完美结合。荣耀手环不仅可以进行步数统计、卡路里计算、自动睡眠监测、久坐提醒，更能通过微信读取手环运动数据,与好友一起刷运动排行榜；独创的分离式设计，取下耳机部分即可接听电话。想了解华为在可穿戴设备领域的最新进展和产品，欢迎您关注“华为运动健康”！                                </span>
                            </p>
                            <span class="arrow" style="left:792px;">
                                <i class="arrow_out"></i>
                                <i class="arrow_in"></i>
                            </span>
                        </div>
                        
                        
                        <div class="mp_case_desc_c jsImg">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/midea_l23b6fe.png"class="mp_case_desc_img">
                            <img data-src="https://res.wx.qq.com/mpres/htmledition/images/pic/case-detail/midea_r23b6fe.png"class="mp_case_desc_img extra">
                            <p class="mp_case_desc_text">
                                <strong class="case_desc_title">美的<span class="case_desc_title_info">企业号</span></strong>
                                <span class="case_desc_intro">
                                    通过企业号优化美的售前、售中、售后的服务管理流程。售后功能上线1个月，日处理工单量超过1万，占总单量的25%。<br />售后工程师：接收工单、查询产品维修记录、现场申请配件、完善服务档案，展示产品保修期、收费政策等信息，引导消费者使用二维码对服务进行现场评价。<br />导购员：上报销量、管理库存、申请调货、管理陈列等，提升终端销售工作效率。<br />门店老板：上报销量、承接家电售后服务，帮助门店老板为消费者提供更好的服务，并提升企业对终端市场的管理。                                </span>
                            </p>
                            <span class="arrow" style="left:939px;">
                                <i class="arrow_out"></i>
                                <i class="arrow_in"></i>
                            </span>
                        </div>

                    </div>
                </div>
            </div> -->

        </div>

                
   <!-- 前台页面底部-->
    <@import.webFoot/> 
   <!-- 前台页面底部-->	
</body>

</html>