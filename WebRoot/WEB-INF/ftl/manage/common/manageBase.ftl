<#import "htmlBase.ftl" as htmlBase />
<#import "menu.ftl" as menu />
<#assign title>${exec("manageSetting").name}</#assign>
<#if currentMenu??>
    <#assign currentMenu>${currentMenu}</#assign>
</#if>
<#macro manageBase
        jsFiles=[] cssFiles=[] staticJsFiles=[] staticCssFiles=[]
        checkLogin=true>
    
<@htmlBase.htmlBase title=title
        jsFiles=jsFiles cssFiles=cssFiles staticJsFiles=staticJsFiles staticCssFiles=staticCssFiles
        checkLogin=checkLogin>

    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${managepath}">${title} 后台管理系统</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <#--
                <li><a href="/" target="_blank"><i class="glyphicon glyphicon-globe"></i> 访问前台</a></li>
                -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> ${exec("admin", "nickname")} <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="${basepath}/manage/update"><i class="fa fa-user fa-fw"></i> 账号信息</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> 用户设置</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="${basepath}/manage/logout"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <@menu.menu currentMenu=currentMenu>
            </@menu.menu>
            
        </nav>

        <div id="page-wrapper" style="margin-top:51px;">
            <#if (currentMenu?? && currentMenu!="首页")>
            <div>
                <ol class="breadcrumb bootstrap-admin-breadcrumb">
                    <li>
                        <a href="${managepath}">首页</a>
                    </li>
                    <li class="active"><a href="javascript:refresh();">${currentMenu}</a></li>
                </ol>
            </div>
            </#if>
            
            <#nested />
            
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

</@htmlBase.htmlBase>
</#macro>
