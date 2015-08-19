<#import "/WEB-INF/ftl/manage/common/manageBase.ftl" as manageBase>
<@manageBase.manageBase>

<form action="" method="post" theme="simple" class="body">
    <div class="panel panel-success">
        <div class="panel-body">
            <fieldset>
                <#if errorMessage??>
                    <div class="alert alert-success alert-dismissable">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        ${errorMessage!""}
                    </div>
                </#if>
                <div class="form-group">
                    <label>账号</label>
                    <input class="form-control" name="usersId" value="${roles.usersId!"-"}" placeholder="账号" disabled="">
                </div>
                <div class="form-group">
                    <label>手机号</label>
                    <input class="form-control" name="usersId" value="${exec("request", "phone")}" placeholder="手机号" disabled="">
                </div>
                <#if roles.updateTime??>
                <div class="form-group">
                    <label>上次更新时间</label>
                    <input class="form-control" value="${exec("date", roles.updateTime)}" placeholder="上次更新时间" disabled="">
                </div>
                </#if>
                <div class="form-group checkbox">
                    <span>邀请他人：</span>
                    <label style="margin:10px"><input type="checkbox" name="hasInvite" value="1"
                        <#if roles.hasInvite?? && roles.hasInvite==1>checked</#if>>开通</label>
                </div>
                <div class="form-group checkbox">
                    <span>开通微店：</span>
                    <label style="margin:10px"><input type="checkbox" name="hasShop" value="1"
                        <#if roles.hasShop?? && roles.hasShop==1>checked</#if>>开通</label>
                </div>
                <div class="form-group checkbox">
                    <span>理　　财：</span>
                    <label style="margin:10px"><input type="checkbox" name="hasP2p" value="1"
                        <#if roles.hasP2p?? && roles.hasP2p==1>checked</#if>>开通</label>
                </div>
                <div class="form-group checkbox">
                    <span>话费充值：</span>
                    <label style="margin:10px"><input type="checkbox" name="hasCall" value="1"
                        <#if roles.hasCall?? && roles.hasCall==1>checked</#if>>开通</label>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">修改</button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </fieldset>
        </div>
    </div>
</form>

</@manageBase.manageBase>
