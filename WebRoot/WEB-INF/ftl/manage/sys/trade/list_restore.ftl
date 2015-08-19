<#import "/WEB-INF/ftl/manage/common/manageBase.ftl" as manageBase>
<#import "/WEB-INF/ftl/common/com.list.ftl" as list>
<@manageBase.manageBase>

<form action="" method="post" theme="simple" class="body">
    <div class="panel panel-success">
        <div class="panel-heading">搜索条件：</div>
        <div class="panel-body">
            <div class="row show-grid">
                <div class="col-md-4" nowrap="nowrap">
                    交易编号：<input type="text" class="search-query input-small"
                        value="${pager.query.sn!""}" name="pager.query.sn" />
                </div>
                <div class="col-md-4" nowrap="nowrap">
                    用户 ID：<input type="text" class="search-query input-small"
                        value="${pager.query.fromId!""}" name="pager.query.fromId" />
                </div>
                <div class="col-md-4" nowrap="nowrap">
                    交易类型：<#assign map = {'':' - - ','CALL':'话费充值','TRANSFER':'转账','RECHARGE':'余额充值',
                        'WITHDRAW':'余额提现','SHOP':'购物','P2P':'理财','PROFIT':'收益','FEES':'手续费'}>
                    <select name="pager.query.type" class="input-medium">
                        <#list map?keys as key>
                            <option value="${key}" <#if pager.query.type?? && pager.query.type.name()==key>selected="selected"</#if>> ${map[key]}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="row show-grid">
                <div class="col-md-4" nowrap="nowrap">
                    交易状态：<#assign map = {'':' - - ','CREATED':'未支付','FAILED':'失败','PAYOFF':'已支付'}>
                    <select name="pager.query.state" class="input-medium">
                        <#list map?keys as key>
                            <option value="${key}" <#if pager.query.state?? && pager.query.state.name()==key>selected="selected"</#if>> ${map[key]}</option>
                        </#list>
                    </select>
                </div>
                <div class="col-md-8" nowrap="nowrap">
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
    
    <@list.list titles=[ '交易编号', '用户ID', '交易类型', '交易名称', '交易金额', '创建时间', '交易状态', '操作' ]
            datas=pager.list; item>
            <td nowrap="nowrap" class="text-center">${item.sn!"-"}</td>
            <td nowrap="nowrap" class="text-center">${item.fromId!"-"}</td>
            <td nowrap="nowrap" class="text-center">${item.type!"-"}</td>
            <td nowrap="nowrap" class="text-center">${item.name!"-"}</td>
            <td nowrap="nowrap" class="text-center">${(item.money/100)!"0"}元</td>
            <td nowrap="nowrap" class="text-center">${exec("date", item.createTime, "yyyy-MM-dd HH:mm:ss")}</td>
            <td nowrap="nowrap" class="text-center">
                <#if item.state??>
                    <#if item.state=="成功">
                        <span class="badge badge-success">交易成功</span>
                    <#elseif item.state=="失败">
                        <span class="badge badge-error">交易失败</span>
                    <#else>
                        <span class="badge badge-waring">${item.state}</span>
                    </#if>
                <#else>
                    未知
                </#if>
            </td>
            <td nowrap="nowrap" class="text-center">
                <#if item.state??>
                    <#if item.state=="成功">
                        交易成功
                    <#elseif item.state=="失败">
                        交易失败
                    <#else>
                        <#if item.type=="话费充值">
                            <a class="btnRestoreCall" data-id="${item.fromId!""}" data-sn="${item.sn!"-"}" data-name="${item.name!"-"}">补单</a>
                        <#else>
                            <a class="btnRestore" data-id="${item.fromId!""}" data-sn="${item.sn!"-"}">补单</a>
                        </#if>
                    </#if>
                <#else>
                    未知
                </#if>
            </td>
        </td>
    </@list.list>
</form>

<script type="text/javascript">
    function restore(sn) {
        var payType = $('#payType').val();
        var paySn = $('#paySn').val();
        
        if (!paySn || paySn.length<=0) {
            alert('支付订单不能为空');
            return;
        }
    
        confirm('确定要补单吗？',
            function() {
                http.post('${basepath}/pay/restore.do',
                    {'sn':sn, 'paySn':paySn, 'payType':payType},
                    function(result) {
                        native.alert("补单成功");
                        location.reload();
                    });
            });
    }
    function restoreCall(sn) {
        confirm('确定要充值话费吗？',
            function() {
                http.post('${basepath}/pay/restore.do',
                    {'sn':sn},
                    function(result) {
                        native.alert("话费充值成功");
                        location.reload();
                    });
            });
    }

    $(function() {
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
                title:'补单',
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
                    +'  <td>支付方式</td>'
                    +'  <td><select id="payType" class="input-medium">'
                    +'   <option value="BALANCE">余额</option>'
                    +'   <option value="ALIPAY">支付宝</option>'
                    +'   <option value="BANK">银行卡</option>'
                    +'   <option value="KKLPAY" selected>卡卡联NFC</option>'
                    +'   <option value="AUTH">认证支付</option>'
                    +'   <option value="ALLINPAY">通联支付</option>'
                    +'  </select></td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td>支付订单</td>'
                    +'  <td><input id="paySn" type="text" class="input-small" /></td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td colspan="2" class="text-center">'
                    +'  <button class="btn btn-primary btm-block" onclick="restore(\''+sn+'\')">补单</button>'
                    +'  </td>'
                    +'</tr>'
                    +'</tbody>'
                    +'</table>'
            });
        });
        
        $(".btnRestoreCall").attr('href','javascript:void(0);');
        $(".btnRestoreCall").click(function() {
            var usersId = $(this).attr('data-id');
            var sn = $(this).attr('data-sn');
            var name = $(this).attr('data-name');
            
            if (!sn || sn.length==0) {
                alert("订单号找不到");
                return;
            }
            
            dialog.dialog({
                type:'info',
                width:'400px',
                title:'补单',
                message:'<table class="table table-striped">'
                    +'<tbody>'
                    +'<tr>'
                    +'  <td>用户 ID</td>'
                    +'  <td>'+usersId+'</td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td>订单名称</td>'
                    +'  <td>'+name+'</td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td>订单号</td>'
                    +'  <td>'+sn+'</td>'
                    +'</tr>'
                    +'<tr>'
                    +'  <td colspan="2" class="text-center">'
                    +'  <button class="btn btn-primary btm-block" onclick="restoreCall(\''+sn+'\')">重新充值话费</button>'
                    +'  </td>'
                    +'</tr>'
                    +'</tbody>'
                    +'</table>'
            });
        });
    });
</script>

</@manageBase.manageBase>
