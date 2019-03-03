<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#import "/common/import.ftl" as import>
	<@import.tableManagerImportCss/>
	<link href="${WEB_PATH}/resources/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<title>车队跟踪</title>
<style>
    .clear { clear: both; }
    html,
	body{
		width:100%;
		height:100%;
		margin:0px;
		padding:0;
		font-size:13px;
	}
	#outer-box{
		height:400px;
	}
	#container{
		height:100%;
		width:100%;
	}
	h3.poi-title{
		margin:3px 0;
		font-size:13px;
	}
	.poibox .poi-info-left{
		padding-left:8px
	}
	.poi-addr{
		margin:7px 0 0;
	}
	.poibox .poi-imgbox{
		width:100px;
		height:74px;
		vertical-align:top;
		float:right;
		margin:0 8px;
		overflow:hidden
	}
	.poibox .poi-img{
		display:inline-block;
		width:100%;
		height:100%;
		background-size:cover;
		background-position:50% 50%;
	}
	.amap-simple-marker.my-marker .amap-simple-marker-label{
		font-size:12px;
		color:#eee;
		font-family:sans-serif;
	}
	.selected .amap-simple-marker.my-marker .amap-simple-marker-label{
		font-size:14px;
		color:orange;
		font-weight:700;
	}
	@-webkit-keyframes flash{
		from,
		50%,
		to{
			opacity:1;
		}
		25%,
		75%{
			opacity:0;
		}
	}
	@keyframes flash{
		from,
		50%,
		to{
			opacity:1;
		}
		25%,
		75%{
			opacity:0;
		}
	}
	.flash{
		-webkit-animation-name:flash;
		animation-name:flash;
	}
	.animated{
		-webkit-animation-duration:1s;
		animation-duration:1s;
		-webkit-animation-fill-mode:both;
		animation-fill-mode:both;
	}
    </style>
</head>
<body>

<div class="modal inmodal" id="user-modal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x-more">&times;</span><span class="sr-only" >关闭</span>
                    </button>
					<div id="allmap">
						<div id="outer-box">
						    <div id="container" tabindex="0">a</div>
						</div>
    					<script type="text/javascript">
    						$('#close-x-more').click(function(){
							     $('#user-modal').remove();
							});
	    					var map = new AMap.Map('container', {
						        zoom: 9
						    });
						    AMap.plugin(['AMap.ToolBar','AMap.Scale','AMap.OverView'],
							    function(){
							        map.addControl(new AMap.ToolBar());
							        map.addControl(new AMap.Scale());
							        map.addControl(new AMap.OverView({isOpen:true}));
							});
					    	AMapUI.loadUI(['misc/MarkerList', 'overlay/SimpleMarker',  'overlay/AwesomeMarker','overlay/SimpleInfoWindow'],
					        	function(MarkerList, SimpleMarker,AwesomeMarker, SimpleInfoWindow) {
					            //即jQuery/Zepto
					            var $ = MarkerList.utils.$;
					            var defaultIconStyle = 'red', //默认的图标样式
					                hoverIconStyle = 'green', //鼠标hover时的样式
					                selectedIconStyle = 'purple' //选中时的图标样式
					            ;
					            var markerList = new MarkerList({
					                map: map,
					                //ListElement对应的父节点或者ID
					                //选中后显示
					                //从数据中读取位置, 返回lngLat
					                getPosition: function(item) {
					                    return [item.longitude, item.latitude];
					                },
					                //数据ID，如果不提供，默认使用数组索引，即index
					                getDataId: function(item, index) {
					                    return item.track_no;
					                },
					                getInfoWindow: function(data, context, recycledInfoWindow) {
										var title = data.track_no+"("+data.track_user_name+")";
										var body = data.consignee_area_name+"-"+data.consignee_address
										var distance = data.distance;
										var totalDis = data.totalDis;
										var direction = "";
										if(data.direct > 27 && data.direct <= 72){
											direction="东北";
										}else if(data.direct > 72 && data.direct <= 117){
											direction="东";
										}
										else if(data.direct > 117 && data.direct <= 162){
											direction="东南";
										}
										else if(data.direct > 162 && data.direct <= 207){
											direction="南";
										}
										else if(data.direct > 207 && data.direct <= 252){
											direction="西南";
										}
										else if(data.direct > 252 && data.direct <= 297){
											direction="西";
										}
										else if(data.direct > 297 && data.direct <= 342){
											direction="西北";
										}
										else {
											direction="北";
										}
										var innerHTML = MarkerList.utils.template(
					                        '<div class="poi-info-left">' +
					                        '    <div class="poi-info">' +
					                        '        <p class="poi-addr"><span class="label label-success" style="padding:2px 2px;">速度：</span>&nbsp;<%- data.speed %>km/h' +
					                        '        </p>'+
					                        '        <p class="poi-addr"><span class="label label-success" style="padding:2px 2px;">方向：</span>&nbsp;'+direction+''+
					                        '        </p>'+
					                        '        <p class="poi-addr"><span class="label label-success" style="padding:2px 2px;">状态：</span>&nbsp;<%- data.state %>'+
					                        '        </p>'+
					                        '        <p class="poi-addr"><span class="label label-success" style="padding:2px 2px;">位置：</span>&nbsp;<%- data.addr %>'+
					                        '        </p>'+
					                        '        <p class="poi-addr"><span class="label label-success" style="padding:2px 2px;">当日里程：</span>&nbsp;'+distance+'km'+
					                        '        </p>'+
					                        '        <p class="poi-addr"><span class="label label-success" style="padding:2px 2px;">总里程：</span>&nbsp;'+totalDis+'km'+
					                        '        </p>'+
					                        '    </div>' +
					                        '</div>' +
					                        '<div class="clear"></div>', {
					                            data: data
					                    });
					                    if (recycledInfoWindow) {
					                        recycledInfoWindow.setInfoTitle(title);
					                        recycledInfoWindow.setInfoBody(innerHTML);
					                        return recycledInfoWindow;
					                    }
					
					                    return new SimpleInfoWindow({
					                        infoTitle: data.name,
					                        infoBody: innerHTML,
					                        offset: new AMap.Pixel(0, -37)
					                    });
					                },
					                //构造marker用的options对象, content和title支持模板，也可以是函数，返回marker实例，或者返回options对象
					                getMarker: function(data, context, recycledMarker) {
					                    var label = String.fromCharCode('A'.charCodeAt(0) + context.index);;
					                    if (recycledMarker) {
					                        recycledMarker.setIconLabel(label);
					                        return;
					                    }
					                   return new SimpleMarker({
					                        containerClassNames: 'my-marker',
					                        iconStyle: defaultIconStyle,
					                        iconLabel: label
					                    }); 
					                },
					                //marker上监听的事件
					                markerEvents: ['click', 'mouseover', 'mouseout'],
					                selectedClassNames: 'selected',
					                autoSetFitView: true
					            });
					
					            window.markerList = markerList;
					
					            markerList.on('selectedChanged', function(event, info) {
					                checkBtnStats();
					                if (info.selected) {
					                    if (info.selected.marker) {
					                        //更新为选中样式
					                        info.selected.marker.setIconStyle(selectedIconStyle);
					                    }
					                    //选中并非由列表节点上的事件触发，将关联的列表节点移动到视野内
					                    if (!info.sourceEventInfo.isListElementEvent) {
					
					                        if (info.selected.listElement) {
					                            scrollListElementIntoView($(info.selected.listElement));
					                        }
					                    }
					                }
					                if (info.unSelected && info.unSelected.marker) {
					                    //更新为默认样式
					                    info.unSelected.marker.setIconStyle(defaultIconStyle);
					                }
					            });
					
					            markerList.on('listElementMouseenter markerMouseover', function(event, record) {
					                if (record && record.marker) {
					                    forcusMarker(record.marker);
					                    //this.openInfoWindowOnRecord(record);
					                    //非选中的id
					                    if (!this.isSelectedDataId(record.id)) {
					                        //设置为hover样式
					                        record.marker.setIconStyle(hoverIconStyle);
					                        //this.closeInfoWindow();
					                    }
					                }
					            });
					
					            markerList.on('listElementMouseleave markerMouseout', function(event, record) {
					                if (record && record.marker) {
					                    if (!this.isSelectedDataId(record.id)) {
					                        //恢复默认样式
					                        record.marker.setIconStyle(defaultIconStyle);
					                    }
					                }
					            });
					
					            //数据输出完成
					            markerList.on('renderComplete', function(event, records) {
					                checkBtnStats();
					            });
					
					            //加载数据
					            function loadData(src, callback) {
					                $.getJSON(src, function(data) {
					                    markerList._dataSrc = src;
										if(data.success==false){
											return;
										}
										var data = data.data;
										$(data).each(function(idx,info){
											info.consigneeAddr =info.consignee_area_name==null||info.consignee_address==null?"": info.consignee_area_name.replace(new RegExp('-',"gm"),"")+info.consignee_address;
											info.deliveryAddr = info.delivery_area_name==null||info.delivery_address==null?"":info.delivery_area_name.replace(new RegExp('-',"gm"),"")+info.delivery_address;
										});
					                    //渲染数据
					                    markerList.render(data);
					                    if(data!=null)
					                    	markerList.selectByDataIndex(0);
					                    if (callback) {
					                        callback(null, data);
					                    }
					                    
					                });
					            }
					
					            var $btns = $('#btnList input[data-path]');
					
					            /**
					             * 检测各个button的状态
					             */
					            function checkBtnStats() {
					                $('#btnList input[data-enable]').each(function() {
					                    var $input = $(this),
					                        codeEval = $input.attr('data-enable');
					                    $input.prop({
					                        disabled: !eval(codeEval)
					                    });
					                });
					            }
					
					            $('#btnList').on('click', 'input', function() {
					                var $input = $(this),
					                    dataPath = $input.attr('data-path'),
					                    codeEval = $input.attr('data-eval');
					
					                if (dataPath) {
					                    loadData(dataPath);
					                } else if (codeEval) {
					                    eval(codeEval);
					                }
					                checkBtnStats();
					            });
					            loadData('${WEB_PATH}/goods/contacts/gps.do?robOrderId=${robOrderId}');
					
					            function forcusMarker(marker) {
					                marker.setTop(true);
					                //不在地图视野内
					                if (!(map.getBounds().contains(marker.getPosition()))) {
					                    //移动到中心
					                    map.setCenter(marker.getPosition());
					                }
					            }
					
					            function isElementInViewport(el) {
					                var rect = el.getBoundingClientRect();
					                return (
					                    rect.top >= 0 &&
					                    rect.left >= 0 &&
					                    rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && /*or $(window).height() */
					                    rect.right <= (window.innerWidth || document.documentElement.clientWidth) /*or $(window).width() */
					                );
					            }
					
					            function scrollListElementIntoView($listEle) {
					                if (!isElementInViewport($listEle.get(0))) {
					                    $('#panel').scrollTop($listEle.offset().top - $listEle.parent().offset().top);
					                }
					                //闪动一下
					                $listEle
					                    .one('webkitAnimationEnd oanimationend msAnimationEnd animationend',
					                        function(e) {
					                            $(this).removeClass('flash animated');
					                        }).addClass('flash animated');
					            }
					        });
    					</script>
					</div>
                </div>
            </div>
        </div> 
    </div>
</div>
</body>
</html>