<#import "/WEB-INF/ftl/front/common/htmlBase.ftl" as htmlBase />

<#-- jsFiles=jsFiles cssFiles=cssFiles staticJsFiles=staticJsFiles staticCssFiles=staticCssFiles -->

<@htmlBase.htmlBase title=title
        checkLogin=false>

<style>
*{margin:0; padding:0;}
body {
  background-color:#FFF;
}

img{max-width: 100%; height: auto;}

#weixin-tip{display:none;position:fixed;left:0;top:0;background:rgba(0,0,0,0.8);filter:alpha(opacity=80);width:100%;height:100%;z-index:100;}
#weixin-tip p{text-align:center;margin-top:10%;padding:0 5%;position:relative;}
#weixin-tip .close{color:#fff;padding:5px;font:bold 20px/24px simsun;text-shadow:0 1px 0 #ddd;position:absolute;top:0;left:5%;}
</style>

<div id="weixin-tip">
    <p>
    <img src="${staticpath}/images/live_weixin.png" alt="微信打开"/>
    <span id="close" title="关闭" class="close">×</span>
    </p>
</div>

<div class="text-center">
    <h1>淘金站</h1>
    <span class="text-orange" style="font-size:18px">恭喜你，注册成功！<br/>立即下载APP，即刻开始冒险之旅～</span>
    <div class="text-center" style="margin-top:20px;">
        <div class="text-center row">
            <a class="col-xs-1"></a>
            <a class="download" href="${basepath}/apk/toooj_last.apk">
                <img class="text-center col-xs-10" src="/resource/images/ic_img_qrcode.png" usemap="#planetmap">
            </a>
        </div>
        <#-- 
        <div>
        http://app.toooj.com/apk/toooj_last.apk
        </div>
        -->
    </div>
</div>

<script type="text/javascript">
    $(window).on("load",function(){
        function is_weixin() {
            // 判断微信UA
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        };
        var isWeixin = is_weixin();
        var tip = document.getElementById('weixin-tip');
        var close = document.getElementById('close');
        if(isWeixin){
            $('.download').click(function() {
                var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight; //兼容IOS
                tip.style.height = winHeight + 'px'; //兼容IOS弹窗整屏
                tip.style.display = 'block';
                return false;
            });
            close.onclick = function() {
                tip.style.display = 'none';
            }
        }
    })
</script>

</@htmlBase.htmlBase>
