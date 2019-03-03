<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="img/favicon.png">

    <title>用户注册</title>
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
                    <!--<li class="dropdown ">-->
                    <!--<a href="#" class="dropdown-toggle " data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false">Feature <b class=" icon-angle-down"></b></a>-->
                    <!--<ul class="dropdown-menu">-->
                    <!--<li><a href="${WEB_PATH}/ylwl/typography.html">Typography</a></li>-->
                    <!--<li><a href="${WEB_PATH}/ylwl/button.html">Buttons</a></li>-->
                    <!--</ul>-->
                    <!--</li>-->
                    <li><a href="${WEB_PATH}/ylwl/about.html">关于我们</a></li>
                    <li><a href="${WEB_PATH}/account/register/index.do">注册</a></li>
                    <li ><a href="${WEB_PATH}/system/user/logout.do">登录</a></li>
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
        <form class="form email_form" id="js_form" action="return:false;" novalidate="novalidate">
        <div class="col-lg-7 col-sm-7 address">
            <h4>找回密码</h4>
            <div class="contact-form">
                <div class="form-group">
                    <label>手机号码</label>
                    <input type="text" placeholder="请输入手机号码" id="phone" name="phone" class="form-control jsInPut" style="color: rgb(236, 60, 60);" onblur="jsNotNull(this,11,24,5)" type="text"  >
                    <span style="display: none;">手机号码位数有误</span>
                    <span style="display: none;">手机号码有误</span>
                </div>
                <div class="form-group">
                    <label style="float: left; width: 100%;">短信验证码</label>
                    <input type="text" id="code" name="code" style="width: 20%; float: left; " class="form-control">
                    <button class="btn " data-url="${WEB_PATH}"  type="button" id="send" style="cursor:pointer;     height: 34px; margin-left: 10px;" type="submit">获取短信验证码</button>
                </div>
                <div class="form-group">
                    <label style="float: left; width: 100%;">验证码</label>
                    <input type="text" id="code-img" name="iamgeCode" style="width: 20%; float: left; " class="form-control">
                    <img style="cursor:pointer;     height: 34px; margin-left: 10px;" id="verifyImg" src="${WEB_PATH }/commonFtl/image.jsp"s	>
                </div>
                <!--<div class="form-group">-->
                <!--<label for="phone">Message</label>-->
                <!--<textarea placeholder="" rows="5" class="form-control"></textarea>-->
                <!--</div>-->
                <button class="btn btn-danger" style="margin-top: 10px;" type="submit">下一步</button>
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

<script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.method.js"></script>

<!--<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&AMP;sensor=false"></script>-->

<!--common script for all pages-->
<script src="${WEB_PATH}/ylwl/js/common-scripts.js"></script>
<script src="${WEB_PATH}/ylwl/js/jInput.js"></script>
<script src="${WEB_PATH}/resources/js/account/resetpwd.js"></script>


<script type="text/javascript">
		$(function(){
    		$("#verifyImg").on('click',function(){
    			$(this).attr("src","${WEB_PATH }/commonFtl/image.jsp?_="+new Date().getTime());
    		});
    	});
</script>
</body>
</html>