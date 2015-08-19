<#import "/WEB-INF/ftl/manage/common/manageBase.ftl" as manageBase>
<#import "/WEB-INF/ftl/common/com.list.ftl" as list>
<@manageBase.manageBase>

<form action="" method="post" theme="simple" class="body">
    <div class="panel panel-success">
        <div class="panel-heading">搜索条件：</div>
        <div class="panel-body">
            <div class="row show-grid">
                <div class="col-md-4" nowrap="nowrap">
                    交易编号：<input type="text" class="search-query input-small"
                        value="${pager.query.sn!""}" name="pager.query.sn" />
                </div>
                <div class="col-md-6" nowrap="nowrap">
                    用户 ID：<input type="text" class="search-query input-small"
                        value="${pager.query.fromId!""}" name="pager.query.fromId" />
                </div>
            </div>
            <div class="row show-grid">
                <div class="col-md-4" nowrap="nowrap">
                    交易状态：<#assign map = {'':' - - ','CREATED':'未支付','FAILED':'失败','SUCCESS':'成功','PAYOFF':'已支付'}>
                    <select name="pager.query.state" class="input-medium">
                        <#list map?keys as key>
                            <option value="${key}" <#if pager.query.state?? && pager.query.state.name()==key>selected="selected"</#if>> ${map[key]}</option>
                        </#list>
                    </select>
                </div>
                <div class="col-md-6" nowrap="nowrap">
                    创建日期：<input id="startTime" class="Wdate search-query input-small" type="text" name="pager.startTime"
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
    
    <@list.list titles=[ '交易编号', '用户ID', '交易名称', '交易金额', '手续费', '创建时间', '交易状态' ]
            datas=pager.list; item>
            <td nowrap="nowrap" class="text-center">${item.sn!"-"}</td>
            <td nowrap="nowrap" class="text-center">${item.fromId!"-"}</td>
            <#--<td nowrap="nowrap" class="text-center">${item.type!"-"}</td>-->
            <td nowrap="nowrap" class="text-left">${item.name!"-"}</td>
            <td nowrap="nowrap" class="text-right">${(item.money/100)!"0"}元</td>
            <td nowrap="nowrap" class="text-right"><#if item.feesMoney?? && item.feesMoney!=0>${item.feesMoney/100+"元"}<#else>-</#if></td>
            <td nowrap="nowrap" class="text-center">${exec("date", item.createTime, "yyyy-MM-dd HH:mm:ss")}</td>
            <td nowrap="nowrap" class="text-center">
                <#if item.state??>
                    <#if item.state=="成功">
                        <span class="badge badge-success">交易成功</span>
                    <#elseif item.state=="失败">
                        <span class="badge badge-error">交易失败</span>
                    <#elseif item.state=="已支付">
                        <span class="badge badge-info">已支付</span>
                    <#else>
                        <#if item.state=="未支付" && item.type=="余额提现">
                            <span class="badge badge-waring">等待处理</span>
                        <#else>
                            <span class="badge badge-waring">${item.state}</span>
                        </#if>
                    </#if>
                <#else>
                    未知
                </#if>
            </td>
        </td>
    </@list.list>
</form>

</@manageBase.manageBase>
