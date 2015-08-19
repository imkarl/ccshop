<#import "/WEB-INF/ftl/manage/common/htmlBase.ftl" as htmlBase>
<#assign title>${exec("manageSetting").name}</#assign>
<@htmlBase.htmlBase
        title=title
        jsFiles=[] cssFiles=[] staticJsFiles=[] staticCssFiles=[]
        checkLogin=false>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">${title} 后台管理系统</h3>
                </div>
                <div class="panel-body">
                    <form role="form" action="${basepath}/manage/login" method="post">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" type="text"
                                        placeholder="账号" name="name" value="${exec("request", "name")}" autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" type="password"
                                        placeholder="密码" name="pass" value="${exec("request", "pass")}">
                            </div>
                            
                            <#--
                            <div class="checkbox">
                                <label>
                                    <input name="remember" type="checkbox" value="remember">记住账号
                                </label>
                            </div>
                            -->
                            
                            <div class="form-group">
                                <button type="submit" class="btn btn-lg btn-success btn-block">登录</button>
                            </div>
                            
                            <div class="form-group">
                                <#if errorMessage??>
                                <div class="alert alert-danger alert-dismissable">
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                   ${errorMessage}
                                </div>
                                </#if>
                            </div>
                            
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</@htmlBase.htmlBase>
