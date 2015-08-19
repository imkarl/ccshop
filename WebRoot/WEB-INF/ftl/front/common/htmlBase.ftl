<#import "html.ftl" as html />
<#macro htmlBase
        title=""
        jsFiles=[] cssFiles=[] staticJsFiles=[] staticCssFiles=[]
        checkLogin=false>
<@html.html
        title=title
        description=exec("manageSetting").description
        keywords=exec("manageSetting").keywords
        shortcuticon=exec("manageSetting").shortcuticon
        jsFiles=jsFiles cssFiles=cssFiles staticJsFiles=staticJsFiles staticCssFiles=staticCssFiles
        checkLogin=checkLogin>

<#global currentpath="${ request.getPathToServlet() }" />

<body>
    <#nested />
</body>

</@html.html>
</#macro>
