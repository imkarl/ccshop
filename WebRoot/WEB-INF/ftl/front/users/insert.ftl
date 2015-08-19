<#import "/WEB-INF/ftl/front/common/htmlBase.ftl" as htmlBase />

<#-- jsFiles=jsFiles cssFiles=cssFiles staticJsFiles=staticJsFiles staticCssFiles=staticCssFiles -->


<@htmlBase.htmlBase title=title
        checkLogin=false>

<form action="" method="post" theme="simple" class="body">
    <div class="panel panel-success">
        <div class="panel-heading">欢迎注册成为 ${title} 的会员</div>
        <div class="panel-body">
            <fieldset>
                <#if errorMessage??>
                    <div class="alert alert-warning alert-dismissable">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        ${errorMessage!""}
                    </div>
                </#if>
                <div class="form-group">
                    <label>手机</label>
                    <input class="form-control" name="phone" value="${phone!""}" placeholder="手机">
                </div>
                <div class="form-group">
                    <label>密码</label>
                    <input class="form-control" type="password" name="pass" value="${pass!""}" placeholder="密码">
                </div>
                <div class="form-group">
                    <label>验证码</label>
                    <div class="form-group input-group">
                        <input type="text" class="form-control" name="verify" placeholder="验证码">
                        <span class="input-group-btn">
                            <button class="btn btn-primary btn-verify" type="button" data-loading-text="正在发送验证码..." autocomplete="off">发送验证码</button>
                        </span>
                    </div>
                </div>
                <div class="form-group">
                    <label>邀请码</label>
                    <input class="form-control" name="inviteCode" value="${exec("request", "inviteCode")}"
                        placeholder="邀请码" <#if exec("request", "inviteCode")?length gt 0>disabled=""</#if>>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary col-xs-5 col-xs-offset-1">注册</button>
                    <button type="reset" class="btn btn-default col-xs-4 col-xs-offset-1">重置</button>
                </div>
            </fieldset>
        </div>
    </div>
</form>

<script>
$(function() {
    function countdown(num) {
        var btnVerify = $('.btn-verify');
        btnVerify.text(num + "秒后可重发");
        
        if (num <= 0) {
            btnVerify.button('reset');
            return;
        }
        
        setTimeout(function () {
            countdown(num-1);
        }, 1000);
    }
    
    $('.btn-verify').on("click", function() {
        $(this).button('loading');
    
        var phone = $('input[name="phone"]').val();
        if (!phone) {
            alert("手机号不能为空");
            $(this).button('reset');
            return;
        }
        if (!(phone && phone.length==11)) {
            alert("手机号格式不正确");
            $(this).button('reset');
            return;
        }
    
        var url = '/sms/send';
        http.post(url, {'phone':phone},
            function(result){
                // 发送成功，开始倒计时
                countdown(60);
            },
            function(result){
                if (result && result.message) {
                    alert(result.message);
                } else {
                    alert("发送失败，请重试");
                }
                $('.btn-verify').button('reset');
            });
    });
});
</script>
</@htmlBase.htmlBase>
