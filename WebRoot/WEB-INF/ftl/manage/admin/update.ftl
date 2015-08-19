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
                    <input class="form-control" name="name" value="${admin.name!""}" placeholder="账号" disabled="">
                </div>
                <div class="form-group">
                    <label>昵称</label>
                    <input class="form-control" name="nickname" value="${admin.nickname!""}" placeholder="昵称">
                </div>
                <div class="form-group">
                    <label>手机</label>
                    <input class="form-control" name="phone" value="${admin.phone!""}" placeholder="手机">
                </div>
                <div class="form-group">
                    <label>邮箱</label>
                    <input class="form-control" name="email" value="${admin.email!""}" placeholder="邮箱">
                </div>
                <div class="form-group">
                    <label>头像</label>
                    <img class="head" src="${admin.portraitl!def_head}"/>
                </div>
                <div class="form-group">
                    <label>上次更新时间</label>
                    <input class="form-control" value="${exec("date", admin.updatetime)}" placeholder="上次更新时间" disabled="">
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
