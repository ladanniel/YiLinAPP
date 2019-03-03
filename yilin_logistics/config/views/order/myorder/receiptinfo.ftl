
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>回执信息</title>
    <meta name="keywords" content="易林物流平台-个人信息认证">
    <meta name="description" content="易林物流平台-个人信息认证">

    <link rel="shortcut icon" href="favicon.ico"> <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/blueimp/css/blueimp-gallery.min.css" rel="stylesheet">

    <link href="${WEB_PATH}/resources/css/animate.min.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">

    <style>
        .lightBoxGallery img {
            margin: 5px;
            width: 160px;
        }
    </style>
    
    	<style>
    	.deliverGoods:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: #1AB394;
		    content: "发货人信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.deliverGoods {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.consignee:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: red;
		    content: "工作人员信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.consignee {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.goodsbasic:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: red;
		    content: "货物基本信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.goodsbasic {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		.goodsdetail:after {
	        background-color: #FFFFFF;
		    border: 1px solid #ECDBDB;
		    border-radius: 4px 0 4px 0;
		    color: red;
		    content: "回执图片信息：";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 5px 8px;
		    position: absolute;
		    top: -1px;
		}
		.goodsdetail {
		    margin-left: 0px;
		    padding:40px 15px 10px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    background-color: white;
		}
		 
    </style>

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
   		<div class="row">
   			 <div class="col-sm-12">
					<div style="" class="consignee ui-sortable" >
		                     <div class="row">
		                        <div class="col-sm-4">
		                            <div class="form-group">
		                                <label>姓名：</label>
		                                <input id="name" name="name" type="text" class="form-control" readonly value="${account.name}">
		                                <input id="userid" name="accountId" type="hidden" class="form-control" readonly>
		                                <input id="robOrderId" name="robOrderId" value="${robOrderId}" type="hidden" class="form-control" readonly>
		                                <input  name="id" value="${robConfirmId}" type="hidden" class="form-control" readonly>
		                            </div> 
		                        </div>
		                        <div class="col-sm-4"> 
		                            <div class="form-group">
		                                <label>联系电话：</label>
		                                <input id="phone" name="phone" type="text" class="form-control" readonly value="${account.phone}">
		                            </div>
		                        </div>
		                    </div>
		                </div>
   			 </div>
     	</div>
        <div class="row">
            <div class="col-lg-12" style="padding-top: 10px;">
                <div class="ibox float-e-margins">

                    <div class="ibox-content">

                        <h2>回执照片</h2>
                        <p>
                        	易林人员人传
                        </p>

                        <div class="lightBoxGallery">
                        	 <#list robOrderConfirm.imgList as img>
                        	 	 <a href="${img}" data-gallery=""><img src="${img}"></a>
                        	 </#list>  
                            <div id="blueimp-gallery" class="blueimp-gallery">
                                <div class="slides"></div>
                                <h3 class="title"></h3>
                                <a class="prev">‹</a>
                                <a class="next">›</a>
                                <a class="close">×</a>
                                <a class="play-pause"></a>
                                <ol class="indicator"></ol>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
           <div class="row">
            <div class="col-lg-12" style="padding-top: 10px;">
                <div class="ibox float-e-margins">

                    <div class="ibox-content">

                        <h2>原始图片</h2>
                        <p>
                        	司机上传
                        </p>

                        <div class="lightBoxGallery">
                        	 <#list robOrderConfirm.truckReceiptImgList as img>
                        	 	 <a href="${img}" data-gallery=""><img src="${img}"></a>
                        	 </#list>  
                            <div id="blueimp-gallery" class="blueimp-gallery">
                                <div class="slides"></div>
                                <h3 class="title"></h3>
                                <a class="prev">‹</a>
                                <a class="next">›</a>
                                <a class="close">×</a>
                                <a class="play-pause"></a>
                                <ol class="indicator"></ol>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${WEB_PATH}/resources/js/content.min.js?v=1.0.0"></script>
    <script src="${WEB_PATH}/resources/js/plugins/blueimp/jquery.blueimp-gallery.min.js"></script>
    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>

</html>