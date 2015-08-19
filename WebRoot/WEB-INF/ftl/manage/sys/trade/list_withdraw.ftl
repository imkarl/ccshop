<#import "/WEB-INF/ftl/manage/common/manageBase.ftl" as manageBase>
<#import "/WEB-INF/ftl/common/com.list.ftl" as list>
<@manageBase.manageBase>

<form action="" method="post" theme="simple" class="body">
    <div class="panel panel-success">
        <div class="panel-heading">搜索条件：</div>
        <div class="panel-body">
            <div class="row show-grid">
                <div class="col-md-5" nowrap="nowrap">
                    交易编号：<input type="text" class="search-query input-small"
                        value="${pager.query.sn!""}" name="pager.query.sn" />
                </div>
                <div class="col-md-6" nowrap="nowrap">
                    用户 ID：<input type="text" class="search-query input-small"
                        value="${pager.query.fromId!""}" name="pager.query.fromId" />
                </div>
                <div class="col-md-5" nowrap="nowrap">
                    订单状态：<#assign radios = {'':'全部','CREATED':'待处理','SUCCESS':'已结算','FAILED':'已撤销'}>
                    <#list radios?keys as key>
                        <label>
                            <input type="radio" name="pager.query.state" value="${key}"
                                <#if pager.query.state?? && pager.query.state.name()==key>checked</#if>>${radios[key]}
                        </label>
                    </#list>
                </div>
                <div class="col-md-6" nowrap="nowrap">
                    创建日期：<input id="startTime" class="Wdate search-query input-small" type="text" name="pager.startTime"
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
    
    <@list.list titles=[ '交易编号', '用户ID', '姓名', '银行卡号', '开户银行', '开户网点', '交易名称', '交易金额', '手续费', '创建时间', '操作' ]
            datas=pager.list; item>
            <#if item.remark??>
                <#assign json=item.remark?eval />
            <#else>
                <#assign json="{}"?eval />
            </#if>
            <td nowrap="nowrap" class="text-center">${item.sn!"-"}</td>
            <td nowrap="nowrap" class="text-center">${item.fromId!"-"}</td>
            <td nowrap="nowrap" class="text-center">${json.name!"-"}</td>
            <td nowrap="nowrap" class="text-center">${json.bankCard!"-"}</td>
            <td nowrap="nowrap" class="text-center">${json.bankName!"-"}</td>
            <td nowrap="nowrap" class="text-center">${json.bankBranch!"-"}</td>
            <td nowrap="nowrap" class="text-center">${item.name!"-"}</td>
            <td nowrap="nowrap" class="text-right">${(item.money/100)!"0"}元</td>
            <td nowrap="nowrap" class="text-right"><#if item.feesMoney?? && item.feesMoney!=0>${item.feesMoney/100+"元"}<#else>-</#if></td>
            <td nowrap="nowrap" class="text-center">${exec("date", item.createTime, "yyyy-MM-dd HH:mm:ss")}</td>
            <td nowrap="nowrap" class="text-center">
                <#if item.state??>
                    <#if item.state=="成功">
                        <span class="badge badge-success">已结算</span>
                    <#elseif item.state=="失败">
                        <span class="badge badge-error">已撤销</span>
                    <#elseif item.state=="未支付">
                        <a class="btnReturn" data-id="${item.fromId!""}" data-sn="${item.sn!"-"}">撤销</a>
                        <a class="btnRestore" data-id="${item.fromId!""}" data-sn="${item.sn!"-"}">结算</a>
                    <#else>
                        <span class="badge badge-waring">${item.state}</span>
                    </#if>
                <#else>
                    未知
                </#if>
            </td>
        </td>
    </@list.list>
</form>

<script type="text/javascript">

    function restore(sn, paySn, payType) {
        var paySn = $('#paySn').val();
        
        if (!paySn || paySn.length<=0) {
            alert('结算订单不能为空');
            return;
        }
    
        confirm('确定要结算吗？',
            function() {
                http.post('${basepath}/app/balance/withdraw',
                    {'sn':sn, 'paySn':paySn, 'state':'SUCCESS'},
                    function(result) {
                        native.alert("结算成功");
                        location.reload();
                    });
            });
    }

    $(function() {
        $(".btnReturn").attr('href','javascript:void(0);');
        $(".btnReturn").click(function() {
            var sn = $(this).attr('data-sn');
            confirm('确定要撤销吗？',
                function() {
                    http.post('${basepath}/app/balance/withdraw',
                        {'sn':sn, 'state':'FAILED'},
                        function(result) {
                            native.alert("撤销成功");
                            location.reload();
                        });
                });
        });
        
        $(".btnRestore").attr('href','javascript:void(0);');
        $(".btnRestore").click(function() {
            var usersId = $(this).attr('data-id');
            var sn = $(this).attr('data-sn');
            
            if (!sn || sn.length==0) {
                alert("订单号找不到");
                return;
            }
            
            dialog.dialog({
                type:'info',
                width:'400px',
                title:'结算',
                message:'<table class="table table-striped">'
                    +'<tbody>'
                    +'<tr>'
                    +'  <td>用户 ID</td>'
                    +'  <td>'+usersId+'</td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td>订单号</td>'
                    +'  <td>'+sn+'</td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td>结算订单</td>'
                    +'  <td><input id="paySn" type="text" class="input-small" /></td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td colspan="2" class="text-center">'
                    +'  <button class="btn btn-primary btm-block" onclick="restore(\''+sn+'\')">结算</button>'
                    +'  </td>'
                    +'</tr>'
                    +'</tbody>'
                    +'</table>'
            });
        });
    });
</script>

</@manageBase.manageBase>
