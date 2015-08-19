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
                
                <div class="form-group">
                    <label>用户 ID</label>
                    <input class="form-control" name="name" value="${(auth.usersId)!""}" placeholder="用户 ID" disabled="">
                </div>
                <div class="form-group">
                    <label>认证状态</label>
                    <#assign map = {'YES':'已认证','NO':'未认证','INVALID':'资料退回','WAIT':'等待审核'}>
                    <select name="authState" class="input-medium">
                        <#list map?keys as key>
                            <option value="${key}" <#if (auth.authState)?? && auth.authState.name()==key>selected="selected"</#if>> ${map[key]}</option>
                        </#list>
                    </select>
                </div>
                <div class="form-group">
                    <label>认证照片</label>
                    <a target="_blank"<#if (auth.photo)??> href="${basepath}${(auth.photo)!def_image}"</#if>>
                        <img class="head" src="${basepath}${(auth.photo)!def_image}">
                    </a>
                    <input class="form-control" name="photo" type="file" data-filter="png,jpg,jpeg" accept="image/png,image/jpeg">
                </div>
                <div class="form-group">
                    <label>真实姓名</label>
                    <input class="form-control" name="name" value="${(auth.name)!""}" placeholder="真实姓名">
                </div>
                <div class="form-group">
                    <label>身份证</label>
                    <input class="form-control" name="idcard" value="${(auth.idcard)!""}" placeholder="身份证">
                </div>
                <div class="form-group">
                    <label>银行卡号</label>
                    <input class="form-control" name="bankCard" value="${(auth.bankCard)!""}" placeholder="银行卡号">
                </div>
                <div class="form-group">
                    <label>开户银行</label>
                    <input class="form-control" name="bankName" value="${(auth.bankName)!""}" placeholder="开户银行">
                </div>
                <div class="form-group">
                    <label>银行网点</label>
                    <input class="form-control" name="bankBranch" value="${(auth.bankBranch)!""}" placeholder="银行网点">
                </div>
                <div class="form-group">
                    <label>开户地址</label>
                    <input class="form-control" name="bankAddr" value="${(auth.bankAddr)!""}" placeholder="开户地址">
                </div>
                <#if (auth.authState)?? && auth.authState.name()=='YES'>
                <div class="form-group">
                    <label>提交认证时间</label>
                    <input class="form-control" name="createTime" value="${exec("date", (auth.createTime)!"")}" placeholder="提交认证时间" disabled="">
                </div>
                <div class="form-group">
                    <label>认证通过时间</label>
                    <input class="form-control" name="updateTime" value="${exec("date", (auth.updateTime)!"")}" placeholder="认证通过时间" disabled="">
                </div>
                </#if>
                
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
