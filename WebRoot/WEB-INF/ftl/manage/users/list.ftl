<#import "/WEB-INF/ftl/manage/common/manageBase.ftl" as manageBase>
<#import "/WEB-INF/ftl/common/com.list.ftl" as list>
<@manageBase.manageBase>

<!-- datatables -->
<link rel="stylesheet" href="http://jshop.coding.io/resource/datatables/css/jquery.dataTables.css" />
<script src="http://cdn.gbtags.com/datatables/1.10.5/js/jquery.dataTables.min.js"></script> 
<script type="text/javascript" language="javascript" src="http://cdn.datatables.net/plug-ins/28e7751dbec/integration/bootstrap/3/dataTables.bootstrap.js"></script> 

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
    
    
        <#-- '<input type="checkbox" id="firstCheckbox" />', '状态' -->
    <@list.list titles=[ '用户 ID', '手机号', '昵称', '邮箱', '注册日期', '邀请人', '认证资料', '密码', '操作' ]
            datas=pager.list; item>
        <td nowrap="nowrap" class="text-center">${item.id!"-"}</td>
        <td nowrap="nowrap" class="text-center">${item.phone!"-"}<#--${item.name!(item.phone!"")}--></td>
        <td nowrap="nowrap" class="text-center">${item.nickname!"-"}</td>
        <td nowrap="nowrap" class="text-center">${item.email!"-"}</td>
        <td nowrap="nowrap" class="text-center">${exec("date", item.createtime)}</td>
        <td nowrap="nowrap" class="text-center"><a class="btnInvite" data-id="${item.id!""}"><div style="width:100%;height:100%;">${item.inviteId!"-"}</div></a></td>
        <td nowrap="nowrap" class="text-center">
        <#if item.authState??>
            <#if item.authState.name()=="YES">
                <span class="badge badge-success">已认证</span>
            <#elseif item.authState.name()=="WAIT">
                <span class="badge badge-waring">等待审核</span>
            <#elseif item.authState.name()=="INVALID">
                <span class="badge badge-waring">资料退回</span>
            <#else>
                <span class="badge badge-error">未认证</span>
            </#if>
        <#else>
            <span class="badge badge-error">未认证</span>
        </#if>
        </td>
        <td nowrap="nowrap" class="text-center">
            <a class="btnPassReset" data-id="${item.id!""}" data-phone="${item.phone!""}" data-nickname="${item.nickname!""}">修改密码</a>
        </td>
        <td nowrap="nowrap" class="text-center">
            <a target="_blank" href="${basepath}/manage/users/update?id=${item.id!""}&phone=${item.phone!""}">基本资料</a>
            <a target="_blank" href="${basepath}/manage/users/auth/detail.do?usersId=${item.id!"0"}&phone=${item.phone!""}">认证资料</a>
            <a class="btnShow" data-id="${item.id!""}" data-phone="${item.phone!""}"
                data-nickname="${item.nickname!""}">余额</a>
            <a target="_blank" href="${basepath}/manage/profit/update_users?usersId=${item.id!""}&phone=${item.phone!""}">费率</a>
            <a target="_blank" href="${basepath}/manage/users/roles/update?usersId=${item.id!""}&phone=${item.phone!""}">权限</a>
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

        $(".btnShow").attr('href','javascript:void(0);');
        $(".btnShow").click(function() {
            tips = tip('数据加载中...');
            
            var usersId = $(this).attr('data-id');
            var phone = $(this).attr('data-phone');
            var nickName = $(this).attr('data-nickname');
            
            http.post('${basepath}/app/balance/queryOne', {'id':usersId},
                function(result) {
                    tips.close();
                    
                    var data = result.data;
                    data.phone = phone;
                    data.nickName = nickName;
                    
                    dialog.dialog({
                        type:'info',
                        width:'400px',
                        title:'用户详情',
                        message:'<table class="table table-striped">'
                            +'<tbody>'
                            +'<tr>'
                            +'  <td>用户 ID</td>'
                            +'  <td>'+usersId+'</td>'
                            +'</tr>'
                            +'<tr>'
                            +'  <td>手机号</td>'
                            +'  <td>'+data.phone+'</td>'
                            +'</tr>'
                            +'<tr>'
                            +'  <td>昵称</td>'
                            +'  <td>'+(data.nickName?data.nickName:'-')+'</td>'
                            +'</tr>'
                            +'<tr>'
                            +'  <td>用户状态</td>'
                            +'  <td>'+(data.isLock?'锁定':'正常')+'</td>'
                            +'</tr>'
                            +'  <tr>'
                            +'  <td>可用余额</td>'
                            +'  <td>'+(data.money/100)+'元</td>'
                            +'</tr>'
                            +'<tr>'
                            +'  <td>不可用余额</td>'
                            +'  <td>'+(data.lockMoney/100)+'元</td>'
                            +'</tr>'
                            +'<tr>'
                            +'  <td colspan="2" class="text-center">'
                            +'  <a class="fa fa-list-alt" href="/manage/balance/changes?pager.query.fromId='+usersId+'&phone='+data.phone+'">充值记录</a>&nbsp;&nbsp;'
                            +'  </td>'
                            +'</tr>'
                            +'</tbody>'
                            +'</table>'
                    });
                },
                function(error) {
                    tips.close();
                    
                    if (error) {
                        alert(error.message);
                    } else {
                        alert('加载失败');
                    }
                });
        });
        
        
        $(".btnInvite").attr('href','javascript:void(0);');
        $(".btnInvite").click(function() {
            tips = tip('数据加载中...');
            
            var usersId = $(this).attr('data-id');
            
            http.post('${basepath}/app/users/selectOne', {'id':usersId},
                function(result) {
                    tips.close();
                    
                    var data = result.data;
                    
                    dialog.dialog({
                        type:'error',
                        width:'740px',
                        title:'更改邀请人',
                        message:'<div class="row">'
                            +'<div class="col-md-12">'
                            +'  <lable class="col-md-2 text-center">用户 ID：'+usersId+'</lable>'
                            +'  <lable class="col-md-3 text-center">手机号：'+data.phone+'</lable>'
                            +'  <lable class="col-md-3 text-center">昵称：'+(data.nickname?data.nickname:'-')+'</lable>'
                            +'  <lable class="col-md-2 text-center">邀请人：<a id="inviteId">'+(data.inviteId?data.inviteId:'-')+'</a></lable>'
                            +'  <button class="col-md-2 fa fa-save btn btn-success" onclick="updateInvite('+usersId+')">更改</button>'
                            +'</div>'
                            +'<div class="col-md-12" style="margin-top:10px;">'
                            +'<table id="dataTables" class="display stripe row-border cell-border"></table>'
                            +'</div>'
                            +'</div>'
                    });
                    
                    $('#dataTables').dataTable({
                        "bProcessing": true, // 是否显示取数据时的提示
                        "bServerSide": true,// 是否通过服务端来取数据
                        "bFilter": false, // 是否显示过来器
                        "aLengthMenu" : [3, 5, 10], //更改显示记录数选项
                        "iDisplayLength" : 10, //默认显示的记录数
                        "bJQueryUI" : true, //是否使用 jQury的UI theme
                        "oLanguage": { //国际化配置  
                            "sProcessing" : "正在获取数据，请稍后...",    
                            "sLengthMenu" : "显示 _MENU_ 条",    
                            "sZeroRecords" : "没有您要搜索的内容",    
                            "sInfo" : "第_START_到_END_条记录 / 共 _TOTAL_ 条",    
                            "sInfoEmpty" : "记录数为0",    
                            "sInfoFiltered" : "(全部记录数 _MAX_ 条)",    
                            "sInfoPostFix" : "",    
                            "sSearch" : "搜索",    
                            "sUrl" : "",    
                            "oPaginate": {    
                                "sFirst" : "第一页",    
                                "sPrevious" : "上一页",    
                                "sNext" : "下一页",    
                                "sLast" : "最后一页"    
                            }
                        },
                        "ajax": {
                            "url": "${basepath}/manage/users/selectPageList?usersId="+usersId,
                            "dataSrc":"data"
                        },
                        "aoColumns": [
                            {name:"id", title:"用户 ID", data:"id"},
                            {name:"phone", title:"手机", data:"phone"},
                            {name:"nickname", title:"昵称", data:"nickname"}
                        ]
                    });
                    $('#dataTables tbody').on('click', 'tr', function () {
                        var id = $('td', this).eq(0).text();
                        $('#inviteId').text(id);
                    });
                },
                function(error) {
                    tips.close();
                    
                    if (error) {
                        alert(error.message);
                    } else {
                        alert('加载失败');
                    }
                });
        });
        
        $(".btnPassReset").attr('href','javascript:void(0);');
        $(".btnPassReset").click(function() {
            var usersId = $(this).attr('data-id');
            var phone = $(this).attr('data-phone');
            var nickName = $(this).attr('data-nickname');
            
            dialog.dialog({
                type:'info',
                width:'400px',
                title:'更改密码',
                message:'<table class="table table-striped">'
                    +'<tbody>'
                    +'<tr>'
                    +'  <td>用户 ID</td>'
                    +'  <td>'+usersId+'</td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td>手机号</td>'
                    +'  <td>'+phone+'</td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td>昵称</td>'
                    +'  <td>'+(nickName?nickName:'-')+'</td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td>新密码</td>'
                    +'  <td><input type="password" class="input-small" name="newPass" /></td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td colspan="2" class="text-center">'
                    +'  <button class="col-md-2 fa fa-check btn btn-success" onclick="updatePass('+usersId+')">更改</button>'
                    +'  </td>'
                    +'</tr>'
                    +'</tbody>'
                    +'</table>'
            });
            
        });
    });
    function deleteSelect() {
        if ($("input:checked").size() == 0) {
            return false;
        }
        return confirm("确定删除选择的记录?");
    }
    
    function updateInvite(usersId) {
        var inviteId = $('#inviteId').text();
        if (!inviteId || inviteId.length==0 || inviteId=='-') {
            alert("邀请人ID不能为空");
            return;
        }
        
        tips = tip('数据加载中...');
        http.post('${basepath}/manage/users/update_invite', {'id':usersId, 'inviteId':inviteId},
            function(result) {
                tips.close();
                refresh();
            },
            function(error) {
                tips.close();
                
                if (error) {
                    alert(error.message);
                } else {
                    alert('加载失败');
                }
            });
    }
    function updatePass(usersId, pass) {
        if (!pass) {
            pass = $("input[name='newPass']").val();
        }
        
        if (!pass || pass.length<=0) {
            alert("新密码不能为空");
            return;
        }
        if (pass.length < 6) {
            alert("新密码长度必须大于6位");
            return;
        }
    
        tips = tip('正在修改密码...');
        http.post('${basepath}/app/users/update', {'id':usersId, 'pass':pass},
            function(result) {
                tips.close();
                native.alert('修改密码成功');
                refresh();
            },
            function(error) {
                tips.close();
                
                if (error) {
                    alert(error.message);
                } else {
                    alert('修改密码失败');
                }
            });
    }
</script>

</@manageBase.manageBase>
