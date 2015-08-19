<#macro list titles=[] datas=[]>

<div class="table-responsive">
    <table class="table table-hover table-striped panel panel-success">
        <tr class="bg-green">
            <#list titles as title>
                <th nowrap="nowrap" class="text-center">${title}</th>
            </#list>
        </tr>
        <#if datas?? && (datas?size>0)>
            <#list datas as item>
                <tr>
                    <#nested item />
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="${titles?size}" class="text-center">
                    没有数据
                </td>
            </tr>
        </#if>
        <tr>
            <td colspan="${titles?size}" class="text-right" style="background-color:#ECECEC">
                <#include "/WEB-INF/ftl/common/pager.ftl"/>
            </td>
        </tr>
    </table>
</div>

</#macro>
