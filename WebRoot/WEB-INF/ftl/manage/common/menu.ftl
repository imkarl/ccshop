<#macro menu menus=[] currentMenu="首页">
<script>
    $(function(){
        var currentMenu = "${currentMenu}";
        
        $("ul.nav").each(function(){
            if (currentMenu != $(this).data("name")) {
                $(this).removeClass("in");
            }
        });
        
        $("a.menu-item").each(function(){
            var href = $(this).data("href");
            $(this).attr("href", href==""?"#":basepath + href);
            
            if (currentMenu != $(this).data("name")) {
                $(this).removeClass("active");
            } else {
                $(this).addClass("active");
                $(this).parent().parent().parent().children(0).addClass("active");
                $(this).parent().parent().parent().children(0).addClass("in");
            }
        });
    });
</script>
<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">
            <#list exec("menus") as menu>
            <li>
                <a data-href="${menu.url}" data-name="${menu.name}" class="menu-item ${(currentMenu==menu.name)?string("active","")}">
                    <#if menu.children?? && menu.children?size gt 0>
                        <i class="fa fa-th-list fa-fw"></i> ${menu.name!""}<span class="fa arrow"></span>
                    <#else>
                        <i class="fa fa-th-large fa-fw"></i> ${menu.name!""}
                    </#if>
                </a>
                <#if menu.children?? && menu.children?size gt 0>
                <ul class="nav nav-second-level">
                <#list menu.children as menu>
                <li>
                    <a data-href="${menu.url}" data-name="${menu.name}" class="menu-item ${(currentMenu==menu.name)?string("active","")}">
                        <i class="fa fa-files-o fa-fw"></i> ${menu.name!""}
                    </a>
                </li>
                </#list>
                </ul>
                </#if>
            </li>
            </#list>
        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->
</#macro>
