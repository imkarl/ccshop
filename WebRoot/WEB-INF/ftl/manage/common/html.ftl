<#import "head.ftl" as head />
<#macro html title="" description="" keywords="" shortcuticon=""
        jsFiles=[] cssFiles=[] staticJsFiles=[] staticCssFiles=[]
        checkLogin=false>
<!DOCTYPE html>
<html>

<@head.head title=title description=description keywords=keywords shortcuticon=shortcuticon
        jsFiles=jsFiles cssFiles=cssFiles staticJsFiles=staticJsFiles staticCssFiles=staticCssFiles>
    
    <script>
        var basepath = "${basepath}";
        var staticpath = "${staticpath}";
        
        <#if exec("admin")??>
            var admin = "${exec("admin").nickname!exec("admin").name}";
        <#else>
            var admin = "";
            <#if checkLogin>
                top.location = "${basepath}/manage/logout";
            </#if>
        </#if>
    </script>
    
    
    <!-- Bootstrap Core CSS -->
    <link href="${staticpath}/sb-admin-2/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${staticpath}/sb-admin-2/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="${staticpath}/sb-admin-2/dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${staticpath}/sb-admin-2/dist/css/sb-admin-2.css" rel="stylesheet">
    <link href="${staticpath}/theme-common.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${staticpath}/sb-admin-2/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- jQuery -->
    <script src="${staticpath}/sb-admin-2/bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${staticpath}/sb-admin-2/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${staticpath}/sb-admin-2/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${staticpath}/sb-admin-2/dist/js/sb-admin-2.js"></script>

    <!-- Validation JavaScript -->
    <script src="${staticpath}/plugin/bootstrap3-validation.js"></script>
    
    
    <!-- DatePicker -->
    <script type="text/javascript" src="${staticpath}/My97DatePicker/WdatePicker.js"></script>
    <!-- Dialog -->
    <script type="text/javascript" src="${staticpath}/bootbox/bootbox.min.js"></script>
    <!-- Custom Dialog -->
    <script type="text/javascript" src="${staticpath}/common-custom.js"></script>
    
</@head.head>

<#nested />

</html>

</#macro>
