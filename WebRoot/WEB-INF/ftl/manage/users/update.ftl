<#import "/WEB-INF/ftl/manage/common/manageBase.ftl" as manageBase>
<@manageBase.manageBase>

<form action="" method="post" enctype="multipart/form-data" theme="simple" class="body">
    <div class="panel panel-success">
        <div class="panel-body">
            <fieldset>
                <#if errorMessage??>
                    <div class="alert alert-success alert-dismissable">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        ${errorMessage!""}
                    </div>
                </#if>
                <#if (users.id)??>
                <div class="form-group">
                    <label>用户 ID</label>
                    <input class="form-control" name="id" value="${(users.id)!""}" disabled="">
                </div>
                </#if>
                <div class="form-group">
                    <label>手机</label>
                    <input class="form-control" name="phone" value="${(users.phone)!""}" <#if (users.id)??>disabled=""</#if>>
                </div>
                <#if (users.authState)??>
                <div class="form-group">
                    <label>认证状态</label>
                    <input class="form-control" value="${(users.authState)!""}" disabled="">
                </div>
                </#if>
                <#if !(users.id)??>
                <div class="form-group">
                    <label>密码</label>
                    <input class="form-control" name="pass" type="password" value="${(users.pass)!""}">
                </div>
                </#if>
                <div class="form-group">
                    <label>昵称</label>
                    <input class="form-control" name="nickname" value="${(users.nickname)!""}" placeholder="昵称">
                </div>
                <div class="form-group">
                    <label>邮箱</label>
                    <input class="form-control" name="email" value="${(users.email)!""}" placeholder="邮箱">
                </div>
                <div class="form-group">
                    <label>头像</label>
                    <img class="head" src="${(users.portrait)!def_image}"/>
                    <input name="portrait" value="${(users.portrait)!""}" type="hidden">
                    <input class="form-control" name="portrait" type="file" data-filter="png,jpg,jpeg" accept="image/png,image/jpeg">
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">修改</button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </fieldset>
        </div>
    </div>
</form>

<script>
$(function() {
    $("form").submit(function(e){
      var filter = $('input[type="file"]').attr('data-filter').split(',');
      if (!filter || filter.length<=0) {
        return true;
      }
      
      var fileOk = false;
      var fileName = $('input[type="file"]').val();
      if (!fileName || fileName.length<=0) {
        return true;
      }
      for (var i=0; i<filter.length; i++) {
        if (filter[i] && filter[i].length!=0) {
            if (fileName.endsWith(filter[i])) {
                fileOk = true;
            }
        }
      }
      
      if (!fileOk) {
        alert("文件格式不正确");
        return false;
      }
      return true;
    });
});
</script>

</@manageBase.manageBase>
