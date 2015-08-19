<#macro head title="" description="" keywords="" shortcuticon=""
        jsFiles=[] cssFiles=[] staticJsFiles=[] staticCssFiles=[]>
<head>
    <#-- <base href="${(basepath?? && basepath!="")?string(basepath, "/")}" /> -->
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <title>${title}</title>
    <meta name="description" content="${description}"/>
    <meta name="keywords" content="${keywords}"/>
    <link rel="shortcut icon" type="image/x-icon" href="${basepath}${shortcuticon}">
    
    <#list staticJsFiles as jsFile>
        <script src="${staticpath}/${jsFile}"></script>
    </#list>
    <#list staticCssFiles as cssFile>
        <link rel="stylesheet" href="${staticpath}/${cssFile}" />
    </#list>
    <#list jsFiles as jsFile>
        <script src="${jsFile}"></script>
    </#list>
    <#list cssFiles as cssFile>
        <link rel="stylesheet" href="${cssFile}" />
    </#list>

<#nested />

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
</#macro>