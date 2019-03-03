<!-- MarkerList完整示例 -->
<!doctype html>
<html lang="zh-CN">

<head>
    <!-- 原始地址：//webapi.amap.com/ui/1.0/ui/misc/MarkerList/examples/index.html 
    <base href="//webapi.amap.com/ui/1.0/ui/misc/MarkerList/examples/" />-->
    <meta charset="utf-8">
    <title>车队跟踪</title>
    <#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
    
    <link href="${WEB_PATH}/resources/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/steps/jquery.steps.css" rel="stylesheet">
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
ul,
li{
padding:0;
margin:0;
list-style:none;
}
#outer-box{
height:100%;
padding-right:300px;
}
#container{
height:100%;
width:100%;
}
#panel{
position:absolute;
top:0;
bottom:0;
right:0;
height:100%;
overflow:auto;
width:300px;
z-index:999;
border-left:1px solid #eaeaea;
background:#fff;
}
#btnList{
position:absolute;
right:300px;
top:0;
padding:0;
margin:0;
z-index:999;
}
#btnList li{
padding:5px;
}
#btnList input{
padding:3px 10px;
min-width:120px;
}
li.poibox{
border-bottom:1px solid #eaeaea;
border-left:2px solid rgba(0,0,0,0);
padding:10px 3px;
cursor:pointer;
}
li.poibox.selected{
border-left-color:#f00;
background:#f6f6f6;
}
li.poibox:hover{
background:#f6f6f6;
}
li.poibox:last-child{
border-bottom:none;
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
.scrollbar1::-webkit-scrollbar-track{
-webkit-box-shadow:inset 0 0 6px rgba(0,0,0,0.3);
background-color:#fff;
}
.scrollbar1::-webkit-scrollbar{
width:6px;
background-color:#fff;
}
.scrollbar1::-webkit-scrollbar-thumb{
background-color:#aaa;
}
    </style>
</head>

<body>
    <div id="outer-box">
        <div id="container" tabindex="0"></div>
        <div id="panel" class="scrollbar1">
            <ul id="myList">
            </ul>
        </div>
        <!--<ul id="btnList">
            <li>
                <input value="美食数据" type="button" data-path="http://a.amap.com/amap-ui/static/data/restaurant.json" />
            </li>
            <li>
                <input value="酒店数据" type="button" data-path="http://a.amap.com/amap-ui/static/data/hotel.json" />
            </li>
            <li>
                <input value="选中第一个" type="button" data-enable='markerList.getData().length>0' data-eval='markerList.selectByDataIndex(0)' />
            </li>
            <li>
                <input value="选中最后一个" type="button" data-enable='markerList.getData().length>0' data-eval='markerList.selectByDataReverseIndex(0)' />
            </li>
            <li>
                <input value="清除选中" type="button" data-enable='!!markerList.getSelectedDataId()' data-eval='markerList.clearSelected()' />
            </li>
            <li>
                <input value="清除数据" type="button" data-enable='markerList.getData().length>0' data-eval='markerList.clearData()' />
            </li>
        </ul>
        -->
    </div>
    <script type="text/javascript" src='http://webapi.amap.com/maps?v=1.3&key=${gaoDeCfg.key}'></script>
    <!-- UI组件库 1.0 -->
    <script type="text/javascript"  src="http://webapi.amap.com/ui/1.0/main.js"></script>
    <script type="text/javascript">
    //"fa-truck"
    //创建地图
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
                listContainer: "myList", //document.getElementById("myList"),
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
                //构造列表元素，与getMarker类似，可以是函数，返回一个dom元素，或者模板 html string
                getListElement: function(data, context, recycledListElement) {
                    var label = String.fromCharCode('A'.charCodeAt(0) + context.index);
					 //'<span class="label label-primary">'+row.deliveryAreaName+'</span>----><span class="label label-danger">'+row.consigneeAreaName+'</span>';
                    //使用模板创建
                    var innerHTML = MarkerList.utils.template(
                        '<div class="poi-info-left">' +
                        '    <h3 class="poi-title">' +
                        '        <%- label %>. <%- data.track_user_name %>' +
                        '            <%= data.track_no %>' +
                        '    </h3>' +
                        '    <div class="poi-info">' +
                        '		 <p class="poi-addr"><span class="label label-primary" style="padding:2px 2px;">出</span> <span class=""><%- data.consignee_addr %></span></p>'+
                        '        <p class="poi-addr"><span class="label label-danger" style="padding:2px 2px;">达</span><span class="" style="margin-left:4px"><%- data.delivery_addr %></span></p>' +
                        '        <p class="poi-addr"><span class="label label-info" style="padding:2px 2px;">当</span><span class="" style="margin-left:4px"><%- data.addr %></span></p>' +
                        '    </div>' +
                        '</div>' +
                        '<div class="clear"></div>', {
                            data: data,
                            label: label
                        });

                    if (recycledListElement) {
                        recycledListElement.innerHTML = innerHTML;
                        return recycledListElement;
                    }

                    return '<li class="poibox">' +
                        innerHTML +
                        '</li>';
                },
                //列表节点上监听的事件
                listElementEvents: ['click', 'mouseenter', 'mouseleave'],
                //marker上监听的事件
                markerEvents: ['click', 'mouseover', 'mouseout'],
                //makeSelectedEvents:false,
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

            // markerList.on('*', function(type, event, res) {
            //     console.log(type, event, res);
            // });

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
            loadData('${WEB_PATH}/trace/myTraceCar.do');

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
</body>

</html>