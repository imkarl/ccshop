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
    
    <@list.list titles=['<input type="checkbox" id="firstCheckbox" />', '手机号', '昵称', '邮箱', '注册日期', '邀请人', '操作']
            datas=pager.list; item>
        <td width="20"><input type="checkbox" name="ids" value="${item.id!""}"</td>
        <td nowrap="nowrap" class="text-center">${item.phone!"-"}<#--${item.name!(item.phone!"")}--></td>
        <td nowrap="nowrap" class="text-center">${item.nickname!"-"}</td>
        <td nowrap="nowrap" class="text-center">${item.email!"-"}</td>
        <td nowrap="nowrap" class="text-center">${item.createtime!"-"}</td>
        <td nowrap="nowrap" class="text-center">${item.inviteId!"-"}</td>
        <td nowrap="nowrap" class="text-center">
            <a class="btnVerify" data-id="${item.id!""}">审核通过</a>
        </td>
    </@list.list>
</form>

<script type="text/javascript">
    $(function() {
        function c1(f) {
            $(":checkbox").each(function() {
                $(this).attr("checked", f);
            });
        }
        $("#firstCheckbox").click(function() {
            if ($(this).attr("checked")) {
                c1(true);
            } else {
                c1(false);
            }
        });
        
        $(".btnVerify").attr('href','javascript:void(0);');
        $(".btnVerify").click(function() {
            var usersId = $(this).attr('data-id');
            confirm('确定要通过审核吗？',
                function() {
                    http.post('${basepath}/manage/users/verifyYes',
                        {'id':usersId, 'state':'YES'},
                        function(result) {
                            native.alert("审核成功");
                            location.reload();
                        });
                });
        });
        
    });
    function deleteSelect() {
        if ($("input:checked").size() == 0) {
            return false;
        }
        return confirm("确定删除选择的记录?");
    }
</script>

</@manageBase.manageBase>
