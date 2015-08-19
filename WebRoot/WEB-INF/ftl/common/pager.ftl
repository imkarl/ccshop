<#assign pg = JspTaglibs["/WEB-INF/ftl/pager-taglib.tld"]/>
<style type="text/css">
.pageLink {
    border: 1px solid #dddddd;
    padding: 4px 12px;
    text-decoration: none;
}

.selectPageLink {
    border: 1px solid #0088cc;
    padding: 4px 12px;
    color: #0088cc;
    background-color: #dddddd;
    text-decoration: none;
}
</style>
<script type="text/javascript">
// 翻页
function toPager(pageUrl) {
    $('form').attr("action", pageUrl);
    $('form').submit();
}
</script>
<!-- 分页标签 -->
<div style="text-align: right; border: 0;padding: 4px 12px;" class="pageDiv">

<#if urlparam??>
<#else>
    <#assign urlparam=""/>
</#if>

<@pg.pager items=pager.total
    export="currentPageNumber=pageNumber"
    maxPageItems=pager.pageSize maxIndexPages=5 isOffset=true>
            总共：${pager.total}条,共:${pager.pageCount}页
    <@pg.first>
        <a href="javascript:void(0);" onclick="toPager('${pageUrl+urlparam}');" class="pageLink">首页</a>
    </@pg.first>
    <@pg.prev>
        <a href="javascript:void(0);" onclick="toPager('${pageUrl+urlparam}');" class="pageLink">上一页</a>
    </@pg.prev>
    <@pg.pages>
    <#if currentPageNumber==pageNumber>
        <span class="selectPageLink">${pageNumber}</span>
    <#else >
        <a href="javascript:void(0);" onclick="toPager('${pageUrl+urlparam}');" class="pageLink">${pageNumber}</a>
    </#if>
    </@pg.pages>
    <@pg.next>
        <a href="javascript:void(0);" onclick="toPager('${pageUrl+urlparam}');" class="pageLink">下一页</a>
    </@pg.next>
    <@pg.last>
        <a href="javascript:void(0);" onclick="toPager('${pageUrl+urlparam}');" class="pageLink">尾页</a>
    </@pg.last>
</@pg.pager>
</div>