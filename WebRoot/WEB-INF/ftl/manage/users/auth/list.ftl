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
    <@list.list titles=[ '用户 ID', '照片', '姓名', '身份证', '银行卡号', '开户银行', '银行网点', '开户地址', '提交时间', '操作' ]
            datas=pager.list; item>
        <td nowrap="nowrap" class="text-center" style="line-height:60px">${item.usersId!"-"}</td>
        <td nowrap="nowrap" class="text-center">
            <a target="_blank"<#if item.photo??> href="${basepath}${item.photo!def_image}"</#if>>
                <img class="head" src="${basepath}${item.photo!def_image}">
            </a>
        </td>
        <td nowrap="nowrap" class="text-center" style="line-height:60px">${item.name!"-"}</td>
        <td nowrap="nowrap" class="text-center" style="line-height:60px">${item.idcard!"-"}</td>
        <td nowrap="nowrap" class="text-center" style="line-height:60px">${item.bankCard!"-"}</td>
        <td nowrap="nowrap" class="text-center" style="line-height:60px">${item.bankName!"-"}</td>
        <td nowrap="nowrap" class="text-center" style="line-height:60px">${item.bankBranch!"-"}</td>
        <td nowrap="nowrap" class="text-center" style="line-height:60px">${item.bankAddr!"-"}</td>
        <td nowrap="nowrap" class="text-center" style="line-height:60px">${exec("date", item.createTime)}</td>
        <td nowrap="nowrap" class="text-center" style="line-height:60px">
            <a class="btnReturn" data-id="${item.usersId!""}">资料退回</a>
            <a class="btnVerify" data-id="${item.usersId!""}">审核通过</a>
        </td>
    </@list.list>
</form>

<script type="text/javascript">
    $(function() {
        $(".btnReturn").attr('href','javascript:void(0);');
        $(".btnReturn").click(function() {
            var usersId = $(this).attr('data-id');
            confirm('确定要退回资料吗？',
                function() {
                    http.post('${basepath}/app/users/auth/invalid',
                        {'usersId':usersId},
                        function(result) {
                            native.alert("资料退回成功");
                            location.reload();
                        });
                });
        });
        
        $(".btnVerify").attr('href','javascript:void(0);');
        $(".btnVerify").click(function() {
            var usersId = $(this).attr('data-id');
            confirm('确定要通过审核吗？',
                function() {
                    http.post('${basepath}/app/users/auth/finish',
                        {'usersId':usersId},
                        function(result) {
                            native.alert("审核成功");
                            location.reload();
                        });
                });
        });
    });
</script>

</@manageBase.manageBase>
