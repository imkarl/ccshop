var nalert = alert;
var nconfirm = confirm;

// 保留原始弹窗
native = {
	alert : function(message) { nalert(message); },
	confirm : function(message, func) {
		nconfirm(message,
			function(result) {
		    	if (result && func) {
		    		func();
		    	}
	    	})
    }
};

//自定义弹窗
dialog = {
    alert : function(message, func){
    	if (!message) {
    		return;
    	}
    	
        var d = bootbox.alert(message, function(){
        	if (func) {
        		func();
        	}
        });
        
        d.close = function() {
        	d.modal("hide");
        };
        return d;
    },
    confirm : function(message, func){
    	if (!message) {
    		return;
    	}
    	
    	var d = bootbox.confirm(message, function(result){
        	if (result && func) {
        		func();
        	}
        });

        d.close = function() {
        	d.modal("hide");
        };
        return d;
    },
    // type, width, title, message, buttons
    dialog : function(args){
    	if (!args && !args.message) {
    		return;
    	}

        var d = bootbox.dialog(args);
    	
        // 设置弹窗类型（default、info、error）
        if (args.type) {
        	if (args.type == 'info') {
                $('.modal-header').addClass('alert-info');
        	} else if (args.type == 'error') {
                $('.modal-header').addClass('alert-error');
        	}
        }
        
        // 设置弹窗宽度
        if (args.width) {
    		$('.modal-dialog').css('width', args.width);
        }
        
        d.close = function() {
        	d.modal("hide");
        };
        return d;
    },
    tip : function(message){
    	if (!message) {
    		return;
    	}

        return dialog.dialog({message:message});
    }
};

// 替换原始弹窗
alert = dialog.alert
confirm = dialog.confirm;
tip = dialog.tip;



// 网络请求
http = {
	post : function(url, data, success, error) {
		var errorMsg = "请求失败，请重试";
		if (error) {
			if (!$.isFunction(error)) {
				errorMsg = ""+error;
			}
		}
		if (!$.isFunction(error)) {
			error = function(result) {
				if (result && result.message) {
					alert(result.message);
				} else {
					alert(errorMsg);
				}
			};
		}
		
		$.post(url, data,
            function(result) {
                if (result != null) {
                    if (result.status == 1010) {
                    	native.alert("登陆失效，请重新登陆");
                        location = basepath;
                    } else if (result.status == 1000) {
                    	if (success) {
                    		success(result);
                    	}
                    } else {
                    	if (error) {
                    		error(result);
                    	}
                    }
                } else {
                	if (error) {
                		error();
                	}
                }
            },
            'json').error(function() {
            	if (error) {
            		error();
            	}
            });
	}
};


// 日志打印
log = function(args) {
	console.log(args);
};

// 写入内容到网页
write = function(html) {
	document.write(html);
};

// 写入内容到网页
refresh = function() {
	location.href = location.href;
};

/**
 * 乘法
 * @param arg1 数字
 * @param arg2 数字
 * @returns {Number} 相乘的结果
 */
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch (e) { }
	try {
		m += s2.split(".")[1].length
	} catch (e) { }
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}


util = {};

var chars = ['0','1','2','3','4','5','6','7','8','9',
             'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
             'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
/**
 * 获取随机字符串
 */
util.random = function(n) {
     var res = "";
     for(var i = 0; i < n ; i ++) {
         var id = Math.ceil(Math.random() * (chars.length-1));
         res += chars[id];
     }
     return res;
}


