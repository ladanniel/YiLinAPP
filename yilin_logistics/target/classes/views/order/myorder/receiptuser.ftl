
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>货物信息增加</title>
    <meta name="keywords" content="易林物流平台-个人信息认证">
    <meta name="description" content="易林物流平台-个人信息认证">
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/js/plugins/stickup/stickup.css" rel="stylesheet">    
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-editable.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
	<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
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
		    content: "收货人信息：";
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
    <div class="wrapper wrapper-content animated fadeInRight">
    
    	      
        <div class="row">
         
	        	<form id="goodsbasic_detail_info_form">
		            <div class="col-sm-12">
		            	<div class="zero-clipboard">
		            	    <div class="btn-clipboard">
		                       	<button type="button" class="btn btn-danger btn-xs" id="consignee_btn" data-parameter = "consignee"><i class="fa fa-table"></i>&nbsp;回执人员选择</button>
		                    </div> 
						</div>
		            	<div style="" class="consignee ui-sortable">
		                     <div class="row">
		                        <div class="col-sm-4">
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>姓名：</label>
		                                <input id="name" name="name" type="text" class="form-control" readonly>
		                                <input id="userid" name="accountId" type="hidden" class="form-control" readonly>
		                                <input id="robOrderId" name="robOrderId" value="${robOrderId}" type="hidden" class="form-control" readonly>
		                                <input  name="id" value="${robConfirmId}" type="hidden" class="form-control" readonly>
		                            </div> 
		                        </div>
		                        <div class="col-sm-4"> 
		                            <div class="form-group">
		                                <label><span style="color: red;">*&nbsp;&nbsp;</span>联系电话：</label>
		                                <input id="phone" name="phone" type="text" class="form-control" readonly>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-sm-12" style="padding-top: 10px;">
		            	 <div class="row">

                        <div class="col-lg-12">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>原始照片</h5>
                                    <div class="ibox-tools">
                                        <a class="collapse-link">
                                            <i class="fa fa-chevron-up"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="ibox-content">
                              		  <div class="wrapper wrapper-content">
								        <div class="row">
								            <div class="col-lg-12" style="padding-top: 10px;">
								                <div class="ibox float-e-margins">
								
								                    <div class="ibox-content">
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
                                </div>
                            </div>
                        </div>

                    </div>
		            </div>
	            
	            <div class="col-sm-12" style="padding-top: 10px;">
	            	<div style="" class="goodsdetail ui-sortable" >
	            	
	            		<div class="row">
	            		  <div class="col-sm-12">
	                               <div class="page-container">
										<div id="uploader" class="wu-example">
										    <div class="queueList">
										        <div id="dndArea" class="placeholder">
										            <div id="filePicker"></div>
										            <p>将回执照片拖到这里，单次最多可选20张</p>
										        </div>
										    </div>
										    <div class="statusBar" style="display:none;">
										        <div class="progress">
										            <span class="text">0%</span>
										            <span class="percentage"></span>
										        </div><div class="info"></div>
										        <div class="btns">
										            <div id="filePicker2"></div>
										        </div>
										    </div>
										</div>
								    </div>  
	                      </div>
	                 </div>
	             </div>
		         <div class="modal-footer" style="z-index:99995;text-align: center;">
		        	<button type="button" class="btn btn-primary btn-w-m" id="saveBut"><i class="fa fa-save"></i>&nbsp;保存</button>
		            <button type="button" class="btn btn-white btn-w-m" id="close-but-goods"><i class="fa fa-close"></i>&nbsp;关闭</button>
		        </div>
		        
		        </form>
            </div>
        </div>
    </div>
    <!-- 模态框（Modal） -->
	<div class="modal fade" id="selectcontacts" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title">
	              	人员选择
	            </h4>
	         </div>
	         <div class="modal-body" style="max-height:450px;overflow-y: auto;">
	        	<table></table>
	         </div>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" 
	               data-dismiss="modal">取消
	            </button>
	            <button type="button" class="btn btn-primary">
	              	确定
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
	</div><!-- /.modal -->
    <script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/ajax.extend.js"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${WEB_PATH}/resources/js/plugins/wizard/jquery.bootstrap.wizard.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/bootstrap-table.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/bootstrap-editable.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.method.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
	<script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/chosen/chosen.jquery.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${WEB_PATH}/resources/js/plugins/webuploader/webuploader.css">
	<link rel="stylesheet" type="text/css" href="http://fex.baidu.com/webuploader/css/demo.css">
	
	<script type="text/javascript" src="${WEB_PATH}/resources/js/plugins/webuploader/webuploader.js"></script>
	  <script src="${WEB_PATH}/resources/js/plugins/blueimp/jquery.blueimp-gallery.min.js"></script>
	
	<script >
	var table ='';
	
	function success(result){  
			if(result.success == true){
		    	layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
				  	skin: 'layui-layer-molv',
				  	icon: 1,
				  	closeBtn: 0
				},function(){
					parent.$("#exampleTableEvents").bootstrapTable('refresh');
				   	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); 
					
				});
			}else{
				$('#saveBut').attr("disabled",false); 
			 	$('#subBut').attr("disabled",false);       
		    	layer.alert('<font color="red">'+result.msg+'</font>', {
				  skin: 'layui-layer-molv' ,
				  closeBtn: 0,
				  icon: 5,
				});
			}
		}
	$(function(){
		
	   $(".ibox-content").show();
	   $(".collapse-link").on('click',function(){
	   		if($(".collapse-link i").attr("class")=="fa fa-chevron-down"){
	   			$(".collapse-link i").addClass("fa-chevron-up").removeClass("fa-chevron-down");
	   			$(".ibox-content").show();
	   		}else{
	   			$(".collapse-link i").addClass("fa-chevron-down").removeClass("fa-chevron-up");
	   			$(".ibox-content").hide();
	   		}
	   });
	   
	   
	   $("#close-but-goods").click(function(){
				layer.open({
					  content: '<font color="#ed5565;">您可能有填写的信息未保存，确定关闭当前窗口吗？</font>',
					  icon: 2,
					  btn: ['确定', '取消'],
					  yes: function(index, layero){
					     	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(index); 
					  } 
				});
	        });
	
		$("#saveBut").click(function(){
	        var $valid = $("#goodsbasic_detail_info_form").valid();
  			if(!$valid) {
  				 var speed=200;//滑动的速度
        		 $('body,html').animate({ scrollTop: 0 }, speed);
  				return false;
  			}
  			var input = $('.filelist li').find("input[name='path']");
  			if(input.length<=0){
  				layer.msg('<font color="red">请择回执图片</font>', {icon: 5});
  				return false;
  			}
  			
  			var goodsBasic = $('#goodsbasic_detail_info_form').serializeJson();
  			console.log($.isArray(goodsBasic.path));
  			if(!$.isArray(goodsBasic.path)){
  			    var path = new Array();
  			    path.push(goodsBasic.path)
  			    goodsBasic.path = path;
  			}
  			 $.yilinAjax({
		   	  	type:'POST',
		   	  	loadmsg:'保存中......',
		   	  	url:'${WEB_PATH }/order/myorder/savereceiptuser.do',
		   	  	data:goodsBasic,
		   	  	btn:null,
        		errorcallback:null,
        		successcallback:success
		   	 });
	    });
	    
	    
	    
	    $('#goodsbasic_detail_info_form').validate({
				  rules: {
				    name: {
				      required: true
				    },
				    phone: {
				      required: true
				    }
				  },
				 messages: {
		            name: {
		                remote:e+"请选择用户！",
		                required:"请选择用户！"
		            },
		            phone: {
		                remote:e+"请选择用户！",
		                required:"请选择用户！"
		            }
		            
		          },
			})
	
      	$('#selectcontacts').on('show.bs.modal', function (e) {  
			$("#selectcontacts").find('.modal-dialog').css({  
		        'margin-top': function () {  
		            var modalHeight = $('#selectcontacts').find('.modal-dialog').height();  
		            return ($(window).height() / 4 - (modalHeight / 4));  
		        }  
		    })    
		});  

		$('#consignee_btn').on('click',function(){
		  var cur_table = $('#selectcontacts').find('table');
		  if(table==''){
		  	 initTable(cur_table)
		  }
		  $('#selectcontacts').modal('show');
 		})
 		
 		$('.btn-primary').on('click',function(){
 		    var cur_table = $('#selectcontacts').find('table');
 			var selectRow = $(cur_table).bootstrapTable('getSelections');
            if(selectRow.length <= 0){
             layer.msg('<font color="red">请选择数据。</font>', {icon: 5});
             return false;
            }
            var info = selectRow[0];
             console.log(info);
           	$('#phone').val(info.phone)
           	$('#name').val(info.name)
            $('#userid').val(info.id)
           	var $valid = $("#phone").valid();
            var $valid = $("#name").valid();
            
           	$('#selectcontacts').modal('hide');
           
          
 		});
 		
 		
 		
 		
 		
	})
	
	
 function initTable(cur_table){
 	 table = $(cur_table).bootstrapTable({
		            url: "${WEB_PATH }/order/myorder/getPage.do",
		            method: 'get',
		            queryParams:function(params){
		              var temp = {  
					        limit: params.limit,  
					        offset: params.offset,
					        maxrows: params.limit,
					        pageindex:params.pageNumber,
				        };
				        return temp;
		            },
		            clickToSelect: true,
		            detailView: false,//父子表
		            uniqueId: "robOrderId",
		            pagination:false,
	                pageNumber:1,  
	                pageSize:10,   
		            sidePagination: "server", //设置为服务器端分页
		            pageSize: 10,
		            pageList: [10, 25],
		            columns: [{
		                radio: true // 使用复选框
		              }, {
		                field: 'name',
		                title: '姓名'
		            }, {
		                field: 'phone',
		                title: '手机号'
		            }, {
		                field: 'companySection.name',
		                title: '组织机构'
		            }]
	          });
 }
	
 jQuery(function() {
    var $ = jQuery,    // just in case. Make sure it's not an other libaray.

        $wrap = $('#uploader'),

        // 图片容器
        $queue = $('<ul class="filelist"></ul>')
            .appendTo( $wrap.find('.queueList') ),

        // 状态栏，包括进度和控制按钮
        $statusBar = $wrap.find('.statusBar'),

        // 文件总体选择信息。
        $info = $statusBar.find('.info'),

        // 上传按钮
        $upload = $wrap.find('.uploadBtn'),

        // 没选择文件之前的内容。
        $placeHolder = $wrap.find('.placeholder'),

        // 总体进度条
        $progress = $statusBar.find('.progress').hide(),

        // 添加的文件数量
        fileCount = 0,

        // 添加的文件总大小
        fileSize = 0,

        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth = 110 * ratio,
        thumbnailHeight = 110 * ratio,

        // 可能有pedding, ready, uploading, confirm, done.
        state = 'pedding',

        // 所有文件的进度信息，key为file id
        percentages = {},

        supportTransition = (function(){
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                      'WebkitTransition' in s ||
                      'MozTransition' in s ||
                      'msTransition' in s ||
                      'OTransition' in s;
            s = null;
            return r;
        })(),

        // WebUploader
        uploader;

    if ( !WebUploader.Uploader.support() ) {
        alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
        throw new Error( 'WebUploader does not support the browser you are using.' );
    }

    // 实例化
    uploader = WebUploader.create({
        pick: {
            id: '#filePicker',
            label: '点击选择图片'
        },
        dnd: '#uploader .queueList',
        paste: document.body,

        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        },
	    auto:true,
        // swf文件路径
        swf: '${WEB_PATH}/resources/js/plugins/webuploader/Uploader.swf',

        disableGlobalDnd: true,

        chunked: true,
        // server: 'http://webuploader.duapp.com/server/fileupload.php',
        server: '${WEB_PATH}/order/myorder/uploadimg.do',
        fileNumLimit: 20,
        fileSizeLimit: 5 * 1024 * 1024,    // 200 M
        fileSingleSizeLimit: 1 * 1024 * 1024    // 50 M
    });
    
    uploader.on('uploadSuccess',function(file,response){
     var $li = $('#'+file.id);
     $li.find("input[name='path']").val(response.imgpath)
    });

    // 添加“添加文件”的按钮，
    uploader.addButton({
        id: '#filePicker2',
        label: '继续添加'
    });

    // 当有文件添加进来时执行，负责view的创建
    function addFile( file ) {
        var $li = $( '<li id="' + file.id + '">' +
                '<p class="title">' + file.name + '</p>' +
                '<p class="imgWrap"></p>'+
                '<input name="path" type=hidden value="订单编号">'+
                '<p class="progress"><span></span></p>' +
                '</li>' ),

            $btns = $('<div class="file-panel">' +
                '<span class="cancel">删除</span>' +
                '<span class="rotateRight">向右旋转</span>' +
                '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
            $prgress = $li.find('p.progress span'),
            $wrap = $li.find( 'p.imgWrap' ),
            $info = $('<p class="error"></p>'),

            showError = function( code ) {
                switch( code ) {
                    case 'exceed_size':
                        text = '文件大小超出';
                        break;

                    case 'interrupt':
                        text = '上传暂停';
                        break;

                    default:
                        text = '上传失败，请重试';
                        break;
                }

                $info.text( text ).appendTo( $li );
            };

        if ( file.getStatus() === 'invalid' ) {
            showError( file.statusText );
        } else {
            // @todo lazyload
            $wrap.text( '预览中' );
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $wrap.text( '不能预览' );
                    return;
                }

                var img = $('<img src="'+src+'">');
                $wrap.empty().append( img );
            }, thumbnailWidth, thumbnailHeight );

            percentages[ file.id ] = [ file.size, 0 ];
            file.rotation = 0;
        }

        file.on('statuschange', function( cur, prev ) {
            if ( prev === 'progress' ) {
                $prgress.hide().width(0);
            } else if ( prev === 'queued' ) {
                $li.off( 'mouseenter mouseleave' );
                $btns.remove();
            }

            // 成功
            if ( cur === 'error' || cur === 'invalid' ) {
               
                showError( file.statusText );
                percentages[ file.id ][ 1 ] = 1;
            } else if ( cur === 'interrupt' ) {
                showError( 'interrupt' );
            } else if ( cur === 'queued' ) {
                percentages[ file.id ][ 1 ] = 0;
            } else if ( cur === 'progress' ) {
                $info.remove();
                $prgress.css('display', 'block');
            } else if ( cur === 'complete' ) {
                $li.append( '<span class="success"></span>' );
            }

            $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
        });

        $li.on( 'mouseenter', function() {
            $btns.stop().animate({height: 30});
        });

        $li.on( 'mouseleave', function() {
            $btns.stop().animate({height: 0});
        });

        $btns.on( 'click', 'span', function() {
            var index = $(this).index(),
                deg;

            switch ( index ) {
                case 0:
                    uploader.removeFile( file );
                    return;

                case 1:
                    file.rotation += 90;
                    break;

                case 2:
                    file.rotation -= 90;
                    break;
            }

            if ( supportTransition ) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $wrap.css({
                    '-webkit-transform': deg,
                    '-mos-transform': deg,
                    '-o-transform': deg,
                    'transform': deg
                });
            } else {
                $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                // use jquery animate to rotation
                // $({
                //     rotation: rotation
                // }).animate({
                //     rotation: file.rotation
                // }, {
                //     easing: 'linear',
                //     step: function( now ) {
                //         now = now * Math.PI / 180;

                //         var cos = Math.cos( now ),
                //             sin = Math.sin( now );

                //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                //     }
                // });
            }


        });

        $li.appendTo( $queue );
    }

    // 负责view的销毁
    function removeFile( file ) {
        var $li = $('#'+file.id);

        delete percentages[ file.id ];
        updateTotalProgress();
        $li.off().find('.file-panel').off().end().remove();
    }

    function updateTotalProgress() {
        var loaded = 0,
            total = 0,
            spans = $progress.children(),
            percent;

        $.each( percentages, function( k, v ) {
            total += v[ 0 ];
            loaded += v[ 0 ] * v[ 1 ];
        } );

        percent = total ? loaded / total : 0;

        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
        updateStatus();
    }

    function updateStatus() {
        var text = '', stats;

        if ( state === 'ready' ) {
            text = '选中' + fileCount + '张图片，共' +
                    WebUploader.formatSize( fileSize ) + '。';
        } else if ( state === 'confirm' ) {
            stats = uploader.getStats();
            if ( stats.uploadFailNum ) {
                text = '已成功上传' + stats.successNum+ '张照片至XX相册，'+
                    stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
            }

        } else {
            stats = uploader.getStats();
            text = '共' + fileCount + '张（' +
                    WebUploader.formatSize( fileSize )  +
                    '），已上传' + stats.successNum + '张';

            if ( stats.uploadFailNum ) {
                text += '，失败' + stats.uploadFailNum + '张';
            }
        }

        $info.html( text );
    }

    function setState( val ) {
        var file, stats;

        if ( val === state ) {
            return;
        }

        $upload.removeClass( 'state-' + state );
        $upload.addClass( 'state-' + val );
        state = val;

        switch ( state ) {
            case 'pedding':
                $placeHolder.removeClass( 'element-invisible' );
                $queue.parent().removeClass('filled');
                $queue.hide();
                //$statusBar.addClass( 'element-invisible' );
                uploader.refresh();
                break;

            case 'ready':
                //$placeHolder.addClass( 'element-invisible' );
                $( '#filePicker2' ).removeClass( 'element-invisible');
                $queue.parent().addClass('filled');
                $queue.show();
                $statusBar.removeClass('element-invisible');
                uploader.refresh();
                break;

            case 'uploading':
                //$( '#filePicker2' ).addClass( 'element-invisible' );
                $progress.show();
                $upload.text( '暂停上传' );
                break;

            case 'paused':
                $progress.show();
                $upload.text( '继续上传' );
                break;

            case 'confirm':
                $progress.hide();
                $upload.text( '开始上传' ).addClass( 'disabled' );

                stats = uploader.getStats();
                if ( stats.successNum && !stats.uploadFailNum ) {
                    setState( 'finish' );
                    return;
                }
                break;
            case 'finish':
                stats = uploader.getStats();
                if ( stats.successNum ) {
                
                } else {
                    // 没有成功的图片，重设
                    state = 'done';
                    location.reload();
                }
                break;
        }

        updateStatus();
    }

    uploader.onUploadProgress = function( file, percentage ) {
        var $li = $('#'+file.id),
            $percent = $li.find('.progress span');

        $percent.css( 'width', percentage * 100 + '%' );
        percentages[ file.id ][ 1 ] = percentage;
        updateTotalProgress();
    };

    uploader.onFileQueued = function( file ) {
        fileCount++;
        fileSize += file.size;

        if ( fileCount === 1 ) {
            $placeHolder.addClass( 'element-invisible' );
            $statusBar.show();
        }

        addFile( file );
        setState( 'ready' );
        updateTotalProgress();
    };

    uploader.onFileDequeued = function( file ) {
        fileCount--;
        fileSize -= file.size;

        if ( !fileCount ) {
            setState( 'pedding' );
        }

        removeFile( file );
        updateTotalProgress();

    };

    uploader.on( 'all', function( type ) {
        var stats;
        switch( type ) {
            case 'uploadFinished':
                setState( 'confirm' );
                break;

            case 'startUpload':
                setState( 'uploading' );
                break;

            case 'stopUpload':
                setState( 'paused' );
                break;

        }
    });

    uploader.onError = function( code ) {
        alert( 'Eroor: ' + code );
    };

    $upload.on('click', function() {
        if ( $(this).hasClass( 'disabled' ) ) {
            return false;
        }

        if ( state === 'ready' ) {
            uploader.upload();
        } else if ( state === 'paused' ) {
            uploader.upload();
        } else if ( state === 'uploading' ) {
            uploader.stop();
        }
    });

    $info.on( 'click', '.retry', function() {
        uploader.retry();
    } );

    $info.on( 'click', '.ignore', function() {
        alert( 'todo' );
    } );

    $upload.addClass( 'state-' + state );
    updateTotalProgress();
});
       
    </script>
</body>

</html>