<#import "/WEB-INF/ftl/manage/common/manageBase.ftl" as manageBase>
<#import "/WEB-INF/ftl/common/com.list.ftl" as list>
<@manageBase.manageBase>

<form action="" method="post" theme="simple" class="body">
    <input class="hide" value="${pager.query.fromId!""}" name="pager.query.fromId" />
    <input class="hide" value="${exec("request", "phone")}" name="phone" />
    <div class="panel panel-success">
        <div class="panel-heading">搜索条件：(<a>${exec("request", "phone")}</a>)</div>
        <div class="panel-body">
            <div class="row  show-grid">
                <div class="col-md-4" nowrap="nowrap">
                    编号：<input type="text" class="search-query input-small"
                        value="${pager.query.sn!""}" name="pager.query.sn" />
                </div>
                <div class="col-md-2" nowrap="nowrap">
                    状态：<#assign map = {'':' - - ','true':'成功','false':'待处理'}>
                    <select name="pager.query.isArrival" class="input-medium">
                        <#list map?keys as key>
                            <option value="${key}" <#if pager.query.isArrival?? && exec("text", pager.query.isArrival)=key>selected="selected"</#if>> ${map[key]}</option>
                        </#list>
                    </select>
                </div>
                <div class="col-md-6" nowrap="nowrap">
                    日期：<input id="startTime" class="Wdate search-query input-small" type="text" name="pager.startTime"
                        value="${exec("date", pager.startTime, "yyyy-MM-dd")}"
                        onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')||\'2020-10-01\'}'})"/>
                    ~ <input id="endTime" class="Wdate search-query input-small" type="text" name="pager.endTime"
                        value="${exec("date", pager.endTime, "yyyy-MM-dd")}"
                        onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'2020-10-01'})"/>
                </div>
            </div>
            <div class="row" style="margin-top:10px;">
                <div class="col-md-12" nowrap="nowrap">
                    <button class="btn btn-primary btm-block" style="min-width:140px;" onclick="selectList(this)">
                        <i class="fa fa-search"></i> 查询 
                    </button>
                </div>
            </div>
        </div>
    </div>
    
    <@list.list titles=[ '交易编号', '交易金额', '创建时间', '类型', '关联订单', '状态' ]
            datas=pager.list; item>
            <td nowrap="nowrap" class="text-center">${item.sn!"-"}</td>
            <td nowrap="nowrap" class="text-center">${(item.money/100)!"0"}元</td>
            <td nowrap="nowrap" class="text-center">${item.createtime!"-"}</td>
            <td nowrap="nowrap" class="text-center">${(item.isAdd?? && item.isAdd)?string("充值", "取款")}</td>
            <td nowrap="nowrap" class="text-center">${item.linkSn!"-"}</td>
            <td nowrap="nowrap" class="text-center">
                <#if item.isArrival?? && item.isArrival>
                    <span class="fa fa-unlock badge badge-success">交易成功</span>
                <#else>
                    <span class="fa fa-lock badge badge-error">待处理</span>
                </#if>
            </td>
        </td>
    </@list.list>
</form>

</@manageBase.manageBase>
