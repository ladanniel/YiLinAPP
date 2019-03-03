<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="img/favicon.png">

    <title>易林物流</title>
      <link rel="shortcut icon" href="http://www.ylwuliu.cn:80/yilin_logistics/resources/img/logo.png">
    <!-- Bootstrap core CSS -->
    <link href="${WEB_PATH}/ylwl/css/bootstrap.min.css" rel="stylesheet">
    <link href="${WEB_PATH}/ylwl/css/theme.css" rel="stylesheet">
    <link href="${WEB_PATH}/ylwl/css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="${WEB_PATH}/ylwl/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link rel="stylesheet" href="${WEB_PATH}/ylwl/css/flexslider.css"/>
    <link href="${WEB_PATH}/ylwl/assets/bxslider/jquery.bxslider.css" rel="stylesheet" />


      <!-- Custom styles for this template -->
    <link href="${WEB_PATH}/ylwl/css/style.css" rel="stylesheet">
    <link href="${WEB_PATH}/ylwl/css/style-responsive.css" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>
     <!--header start-->
     <header class="header-frontend">
         <div class="navbar navbar-default navbar-static-top">
             <div class="container">
                 <div class="navbar-header">
                     <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                         <span class="icon-bar"></span>
                         <span class="icon-bar"></span>
                         <span class="icon-bar"></span>
                     </button>
                     <a class="navbar-brand" href="${WEB_PATH}/ylwl/index.html">QBG<span>logistics</span></a>
                 </div>
                <div class="navbar-collapse collapse ">
                     <ul class="nav navbar-nav">
                         <li ><a href="${WEB_PATH}/index.html">首页</a></li>
                    	 <li><a href="${WEB_PATH}/ylwl/LMS.html">LMS</a></li>
                         <li><a href="${WEB_PATH}/ylwl/app.html">APP</a></li>
                         <li><a href="${WEB_PATH}/ylwl/app.html">服务</a></li>
                         <li><a href="${WEB_PATH}/ylwl/about.html">关于我们</a></li>
                         <li><a href="${WEB_PATH}/account/register/index.do">注册</a></li>
                         <li class="active"><a href="${WEB_PATH}/system/user/logout.do">登录</a></li> 
                     </ul>
                 </div>
             </div>
         </div>
     </header>
    <!--header end-->

    <!--breadcrumbs start-->
     <div class="breadcrumbs" style="background-image: url(${WEB_PATH}/ylwl/img/back.jpg);">
         <div class="container">
             <div class="row">
                 <div class="col-lg-4 col-sm-4">
                     <h1></h1>
                 </div>
                 <div class="col-lg-8 col-sm-8">
                     <ol class="breadcrumb pull-right">
                         <li><a href="#"></a></li>
                         <!--<li><a href="#">Pages</a></li>-->
                         <li class="active"></li>
                     </ol>
                 </div>
             </div>
         </div>
     </div>
    <!--breadcrumbs end-->



     <!--container start-->
    <div class="container">


        <div class="row">
            <div class="col-lg-5 col-sm-5 address">
                <img src="${WEB_PATH}/ylwl/img/map.png" style=" width: 100%;">
            </div>
             <form class="login_form" id="loginForm">
            <div class="col-lg-7 col-sm-7 address">
                <h4>用户登录</h4>
               <div class="form-group">
	                            <label>用户名</label>
	                            <input type="text" placeholder="账号/手机" class="form-control jsInPut" style="color: rgb(236, 60, 60);" onblur="jsNotNull(this,3,50,0)" type="text" id="loginname">
	                            <span style="display: none;">用户名位数有误</span>
	                            <span style="display: none;">密码位数有误</span>
	                        </div>
	                        <div class="form-group">
	                            <label>密码</label>
	                            <input type="password" placeholder="密码" class="form-control jsInPut"  style="color: rgb(236, 60, 60);" onblur="jsNotNull(this,3,50,0)" type="text" id="password" name="password" >
	                            <span style="display: none;">密码位数有误</span>
	                            <span style="display: none;">密码位数有误</span>
	                        </div>
	                        <div class="form-group">
	                            <label style="float: left; width: 100%;">验证码</label>
	                            <input type="text" id="code" style="width: 20%; float: left; " class="form-control">
	                            <img style="cursor:pointer;     height: 34px; margin-left: 10px;" id="verifyImg" src="${WEB_PATH }/commonFtl/image.jsp">
	                        </div>
	                        <!--<div class="form-group">-->
	                            <!--<label for="phone">Message</label>-->
	                            <!--<textarea placeholder="" rows="5" class="form-control"></textarea>-->
	                        <!--</div>-->
	                        <button class="btn btn-danger" style="margin-top: 10px;" >登录</button>
	                        <a id="find_url" href="${WEB_PATH}/account/register/resetpwdview.do"><button id="pasword" class="btn" style="margin-top: 10px;">忘记密码?</button></a>
                </div>
             </form>
            </div>
        </div>

    </div>
    <!--container end-->
     <!--google map start-->
     <div class="contact-map">
         <div id="map-canvas" style="width: 100%; height: 200px"></div>
     </div>
     <!--google map end-->
     <!--footer start-->
     <footer class="footer">
         <div class="container">
             <div class="row">
                 <div class="col-lg-3 col-sm-3">
                     <h1>contact info</h1>
                     <address>
                         <p>Address: No.28-63877 street</p>
                         <p>lorem ipsum city, Country</p>

                         <p>Phone : (123) 456-7890</p>
                         <p>Fax : (123) 456-7890</p>
                         <p>Email : <a href="javascript:;">support@vectorlab.com</a></p>
                     </address>
                 </div>
                 <div class="col-lg-5 col-sm-5">
                 </div>
                 <div class="col-lg-3 col-sm-3 col-lg-offset-1">
                     <h1>stay connected</h1>
                     <ul class="social-link-footer list-unstyled">
                         <li><a href="#"><i class="icon-facebook"></i></a></li>
                         <li><a href="#"><i class="icon-google-plus"></i></a></li>
                         <li><a href="#"><i class="icon-dribbble"></i></a></li>
                         <li><a href="#"><i class="icon-linkedin"></i></a></li>
                         <li><a href="#"><i class="icon-twitter"></i></a></li>
                         <li><a href="#"><i class="icon-skype"></i></a></li>
                         <li><a href="#"><i class="icon-github"></i></a></li>
                         <li><a href="#"><i class="icon-youtube"></i></a></li>
                     </ul>
                 </div>
             </div>
         </div>
     </footer>
     <!--footer end-->

   <!-- js placed at the end of the document so the pages load faster -->
    <script src="${WEB_PATH}/ylwl/js/jquery-1.8.1.min.js"></script>
    <script src="${WEB_PATH}/ylwl/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${WEB_PATH}/ylwl/js/hover-dropdown.js"></script>
    <script type="text/javascript" src="${WEB_PATH}/ylwl/assets/bxslider/jquery.bxslider.js"></script>
 	<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 

    <!--<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&AMP;sensor=false"></script>-->

    <!--common script for all pages-->
    <script src="${WEB_PATH}/ylwl/js/common-scripts.js"></script>
    <script src="${WEB_PATH}/ylwl/js/jInput.js"></script>


     <script type="text/javascript">
     	document.onkeydown=function(event)
		{
			e = event ? event : (window.event ? window.event : null);
			if(e.keyCode==13){
				severCheck();
			}
		}
		
         $(function () {
         	
         	$("#verifyImg").on('click',function(){
    			$(this).attr("src","${WEB_PATH }/commonFtl/image.jsp?_="+new Date().getTime());
    		});
    		
    		$("#pasword").on('click',function(){
    			location.href = "${WEB_PATH}/account/register/resetpwdview.do";
    			return false;
    		});
         
             $('.btn-danger').click(function () {
                severCheck();
                return false;
             })
         })
         
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
						   jsMessagebox(data.msg)
						   $(".err_tips").text(data.msg);
						   $("#verifyImg").attr("src","${WEB_PATH }/commonFtl/image.jsp?_="+new Date().getTime());
							//layer.msg(data.msg);
						}
						layer.close(ii);
					},
					error: function(result) {
						jsMessagebox(result)
						layer.close(ii);
					}
				});
			}
			
			return false;
		}
         
        
        //客户端校验
		function check() { 
			if ($("#loginname").val() == "") { 
				 jsMessagebox('用户名不能为空!');
				$("#loginname").focus();
				return false;
			}  
			if ($("#password").val() == "") {
			    jsMessagebox('密码不能为空!');
				$("#password").focus();
				return false;
			}
			if ($("#code").val() == "") {
				jsMessagebox('验证码不能为空!')
				$("#code").focus();
				return false;
			} 
			return true;
		}
         
         
     </script>
  </body>
</html>