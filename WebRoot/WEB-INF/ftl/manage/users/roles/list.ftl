<#import "/WEB-INF/ftl/manage/common/manageBase.ftl" as manageBase>
<#import "/WEB-INF/ftl/common/com.list.ftl" as list>
<@manageBase.manageBase>

<form action="" method="post" theme="simple" class="body">
    <div class="panel panel-success">
        <div class="panel-heading">搜索条件：</div>
        <div class="panel-body">
            <div class="row  show-grid">
                <div class="col-md-4" nowrap="nowrap">
                    手　机：<input type="text" class="search-query input-small"
                        value="${pager.query.phone!""}" name="pager.query.phone" />
                </div>
                <div class="col-md-4" nowrap="nowrap">
                    昵　称：<input type="text" class="search-query input-small"
                        value="${pager.query.nickname!""}" name="pager.query.nickname" />
                </div>
                <div class="col-md-4" nowrap="nowrap">
                    邮　箱：<input type="text" class="search-query input-small"
                        value="${pager.query.email!""}" name="pager.query.email" />
                </div>
            </div>
            <div class="row show-grid">
                <div class="col-md-4" nowrap="nowrap">
                    邀请人：<input type="text" class="search-query input-small"
                            value="${pager.query.inviteId!""}" name="pager.query.inviteId" />
                </div>
                <div class="col-md-8" nowrap="nowrap">
                    注册日期：<input id="startTime" class="Wdate search-query input-small" type="text" name="pager.startTime"
                        value="${exec("date", pager.startTime, "yyyy-MM-dd")}"
                        onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')||\'2020-10-01\'}'})"/>
                    ~ <input id="endTime" class="Wdate search-query input-small" type="text" name="pager.endTime"
                        value="${exec("date", pager.endTime, "yyyy-MM-dd")}"
                        onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'2020-10-01'})"/>
                </div>
                <#--
                <div class="col-md-2" nowrap="nowrap">
                    状态：<#assign map = {'':' - - ','YES':'可用','NO':'不可用'}>
                    <select name="pager.query.state" class="input-medium">
                        <#list map?keys as key>
                            <option value="${key}" <#if pager.query.state?? && pager.query.state==key>selected="selected"</#if>> ${map[key]}</option>
                        </#list>
                    </select>
                </div>
                -->
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
    
    <@list.list titles=[ '用户 ID', '手机号码', '邀请他人', '开通微店', '理财', '话费充值', '操作' ]
            datas=pager.list; item>
        <td nowrap="nowrap" class="text-center">${item.usersId!"-"}</td>
        <td nowrap="nowrap" class="text-center">${item.phone!"-"}</td>
        <td nowrap="nowrap" class="text-center">
            <#if item.hasInvite?? && item.hasInvite==1>
                <span class="fa fa-unlock badge badge-success">可用</span>
            <#else>
                <span class="fa fa-lock badge badge-waring">不可用</span>
            </#if>
        </td>
        <td nowrap="nowrap" class="text-center">
            <#if item.hasShop?? && item.hasShop==1>
                <span class="fa fa-unlock badge badge-success">可用</span>
            <#else>
                <span class="fa fa-lock badge badge-waring">不可用</span>
            </#if>
        </td>
        <td nowrap="nowrap" class="text-center">
            <#if item.hasP2p?? && item.hasP2p==1>
                <span class="fa fa-unlock badge badge-success">可用</span>
            <#else>
                <span class="fa fa-lock badge badge-waring">不可用</span>
            </#if>
        </td>
        <td nowrap="nowrap" class="text-center">
            <#if item.hasCall?? && item.hasCall==1>
                <span class="fa fa-unlock badge badge-success">可用</span>
            <#else>
                <span class="fa fa-lock badge badge-waring">不可用</span>
            </#if>
        </td>
        <td nowrap="nowrap" class="text-center">
            <a target="_blank" href="${basepath}/manage/users/roles/update?usersId=${item.usersId!""}&phone=${item.phone!""}">查看详情</a>
        </td>
    </@list.list>
</form>

</@manageBase.manageBase>
