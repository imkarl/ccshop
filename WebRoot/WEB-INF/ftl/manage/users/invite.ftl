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
                    邀请人ID：<input type="text" class="search-query input-small"
                            value="${pager.query.inviteId!""}" name="pager.query.inviteId" />
                </div>
            </div>
            
            <div class="row" style="margin-top:10px;">
                <div class="col-md-12" nowrap="nowrap">
                    <button class="btn btn-primary btm-block" style="min-width:140px;" onclick="select_invite()">
                        <i class="fa fa-search"></i> 查询 
                    </button>
                </div>
            </div>
        </div>
    </div>
    
    
        <#-- '<input type="checkbox" id="firstCheckbox" />', '状态' -->
    <@list.list titles=[ '用户 ID', '手机号', '昵称', '邮箱', '注册日期', '邀请人', '认证资料' ]
            datas=pager.list; item>
        <td nowrap="nowrap" class="text-center">${item.id!"-"}</td>
        <td nowrap="nowrap" class="text-center">${item.phone!"-"}<#--${item.name!(item.phone!"")}--></td>
        <td nowrap="nowrap" class="text-center">${item.nickname!"-"}</td>
        <td nowrap="nowrap" class="text-center">${item.email!"-"}</td>
        <td nowrap="nowrap" class="text-center">${exec("date", item.createtime)}</td>
        <td nowrap="nowrap" class="text-center">${item.inviteId!"-"}</td>
        <td nowrap="nowrap" class="text-center">
        <#if item.authState??>
            <#if item.authState=="YES">
                <span class="badge badge-success">已认证</span>
                <a target="_blank" href="${basepath}/manage/users/auth/detail.do?usersId=${item.id!"0"}">查看</a>
            <#elseif item.authState=="WAIT">
                <span class="badge badge-waring">等待审核</span>
            <#elseif item.authState=="INVALID">
                <span class="badge badge-waring">资料退回</span>
            <#else>
                <span class="badge badge-error">未认证</span>
            </#if>
        <#else>
            <span class="badge badge-error">未认证</span>
        </#if>
        </td>
        
       <!-- 
       <td nowrap="nowrap" class="text-center">
            <a class="btnShow" data-id="${item.id!""}" data-phone="${item.phone!""}"
                data-nickname="${item.nickname!""}">查看详情</a>
        </td>
        -->
    </@list.list>
</form>

<script type="text/javascript">
   
</script>

</@manageBase.manageBase>
