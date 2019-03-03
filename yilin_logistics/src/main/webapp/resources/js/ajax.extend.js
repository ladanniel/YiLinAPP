
(function($){
	$.fn.serializeJson=function(){ 
        var serializeObj={}; // 目标对象 
        var array=this.serializeArray(); // 转换数组格式 
        // var str=this.serialize(); 
        $(array).each(function(){ // 遍历数组的每个元素 {name : xx , value : xxx} 
                if(serializeObj[this.name]){ // 判断对象中是否已经存在 name，如果存在name 
                      if($.isArray(serializeObj[this.name])){ 
                             serializeObj[this.name].push(this.value); // 追加一个值 hobby : ['音乐','体育'] 
                      }else{ 
                              // 将元素变为 数组 ，hobby : ['音乐','体育'] 
                              serializeObj[this.name]=[serializeObj[this.name],this.value]; 
                      } 
                }else{ 
                        serializeObj[this.name]=this.value; // 如果元素name不存在，添加一个属性 name:value 
                } 
        }); 
        return serializeObj; 
	};  
	$.extend({
        yilinAjax: function (options) {
            var defaults = {
            	type:"Charles",
            	url:"22",
            	data:null,
            	cache:true,
            	async:true,
            	btn:null,
            	errorcallback:null,
            	successcallback:null,
            	contentType:"application/json"
            };
            var opts = $.extend(defaults, options);
            var loadmsg = options.loadmsg == undefined || options.loadmsg == null ? "正在处理中......":options.loadmsg;
            var index =  layer.msg(loadmsg, {icon: 16,shade: 0.3,time:0});
            var postData = {
				  type: options.type,
				  url: options.url,  
				  data: options.data,
				  cache: options.cache != null ? options.cache:defaults.cache,
				  async:options.async != null ? options.cache:defaults.async,
				  dataType: "json",
				  contentType :options.contentType,
				  success:function(result){ 
					 layer.close(index);
					 if(typeof options.successcallback === 'function'){
						 if(options.btn == undefined || options.btn == null){
							 options.successcallback(result); // 给callback赋值，callback是个函数变量
					 	 }else{
					 		 options.successcallback(result,options.btn); 
					 	 }
					 }
				  },
				  error: function(XMLHttpRequest) {  //#3这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
					  layer.close(index);
					  if(typeof options.errorcallback === 'function' && options.errorcallback != undefined){
						  options.errorcallback(XMLHttpRequest); // 给callback赋值，callback是个函数变量
					  }else{
						  if(XMLHttpRequest.status == "403"){
	                        	layer.msg('<font color="red">温馨提示：尊敬的用户您好！你没有权限访问该资源。</font>', {icon: 5}); 
	                      }
						  if(XMLHttpRequest.status == "500"){
							    var data = $.parseJSON(XMLHttpRequest.responseText);
	                        	layer.msg('<font color="red">系统错误：'+data.msg+'</font>', {icon: 5}); 
	                      }
					  }
					  if(options.btn != undefined && options.btn != null){
						  options.btn.attr("disabled",false);    
					  }
	             }
			};
            $.ajax(postData);
        } 
    });
})(jQuery);