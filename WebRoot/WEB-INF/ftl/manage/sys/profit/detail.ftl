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
                
                <#if users??>
                    <div class="alert alert-success-light">
                        <div class="col-lg-2">
                            <label>用户ID：</label><label style="color:blue">${users.id!""}</label>
                        </div>
                        <div class="col-lg-offset-1">
                            <label>手机号：</label><label style="color:blue">${users.phone!""}</label>
                        </div>
                    </div>
                </#if>
                
                <div class="panel panel-default">
                    <div class="panel-heading">代理收益</div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#balance" data-toggle="tab" aria-expanded="false">余额充值</a></li>
                            <li><a href="#withdraw" data-toggle="tab" aria-expanded="false">余额提现</a></li>
                            <li><a href="#transfer" data-toggle="tab" aria-expanded="false">转账汇款</a></li>
                            <li><a href="#call" data-toggle="tab" aria-expanded="false">话费充值</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div class="tab-pane fade active in" id="balance">
                                <p></p>
                                <div class="form-group">
                                    <label>一级代理收益 (0%-100%)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="oneBalance" value="<#if (profit.oneBalance)??>${profit.oneBalance*0.01}<#else>0</#if>" placeholder="一级代理收益">
                                </div>
                                <div class="form-group">
                                    <label>二级代理收益 (0%-100%)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="twoBalance" value="<#if (profit.twoBalance)??>${profit.twoBalance*0.01}<#else>0</#if>" placeholder="二级代理收益">
                                </div>
                            </div>
                            <div class="tab-pane fade" id="withdraw">
                                <p></p>
                                <div class="form-group">
                                    <label>一级代理收益 (单位：元)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="oneWithdraw" value="<#if (profit.oneWithdraw)??>${profit.oneWithdraw*0.01}<#else>0</#if>" placeholder="一级代理收益">
                                </div>
                                <div class="form-group">
                                    <label>二级代理收益 (单位：元)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="twoWithdraw" value="<#if (profit.twoWithdraw)??>${profit.twoWithdraw*0.01}<#else>0</#if>" placeholder="二级代理收益">
                                </div>
                            </div>
                            <div class="tab-pane fade" id="transfer">
                                <p></p>
                                <div class="form-group">
                                    <label>一级代理收益 (单位：元)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="oneTransfer" value="<#if (profit.oneTransfer)??>${profit.oneTransfer*0.01}<#else>0</#if>" placeholder="一级代理收益">
                                </div>
                                <div class="form-group">
                                    <label>二级代理收益 (单位：元)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="twoTransfer" value="<#if (profit.twoTransfer)??>${profit.twoTransfer*0.01}<#else>0</#if>" placeholder="二级代理收益">
                                </div>
                            </div>
                            <div class="tab-pane fade" id="call">
                                <p></p>
                                <div class="col-lg-6">
                                    <div class="well">
                                        <label>一级代理 (单位：元)</label>
                                        <#assign oneCall=(profit.oneCall)?eval />
                                        
                                        <div class="form-group">
                                            <label>面额 50元</label>
                                            <input class="form-control" range="0~100" check-type="required number"
                                                name="oneCall50"  value="${oneCall["5000"]*0.01}">
                                        </div>
                                        <div class="form-group">
                                            <label>面额 100元</label>
                                            <input class="form-control" range="0~100" check-type="required number"
                                                name="oneCall100"  value="${oneCall["10000"]*0.01}">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="well">
                                        <label>二级代理 (单位：元)</label>
                                        <#assign twoCall=(profit.twoCall)?eval />
                                        
                                        <div class="form-group">
                                            <label>面额 50元</label>
                                            <input class="form-control" range="0~100" check-type="required number"
                                                name="twoCall50"  value="${twoCall["5000"]*0.01}">
                                        </div>
                                        <div class="form-group">
                                            <label>面额 100元</label>
                                            <input class="form-control" range="0~100" check-type="required number"
                                                name="twoCall100"  value="${twoCall["10000"]*0.01}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                
                
                <div class="panel panel-default">
                    <div class="panel-heading">手续费率</div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#f_transfer" data-toggle="tab" aria-expanded="false">转账汇款</a></li>
                            <li><a href="#withwrad" data-toggle="tab" aria-expanded="false">余额提现</a></li>
                            <li><a href="#recharge" data-toggle="tab" aria-expanded="false">余额充值</a></li>
                            <li><a href="#call_fees" data-toggle="tab" aria-expanded="false">话费充值</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div class="tab-pane fade active in" id="f_transfer">
                                <p></p>
                                <div class="form-group">
                                    <label>转账汇款费率 (0%-100%)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="transfer" value="<#if (profit.transfer)??>${profit.transfer*0.01}<#else>0</#if>" placeholder="转账汇款费率">
                                </div>
                            </div>
                            <div class="tab-pane fade" id="withwrad">
                                <p></p>
                                <div class="form-group">
                                    <label>余额提现手续费 (单位：元)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="withdraw" value="<#if (profit.withdraw)??>${profit.withdraw*0.01}<#else>0</#if>" placeholder="余额提现费率">
                                </div>
                            </div>
                            <div class="tab-pane fade" id="recharge">
                                <p></p>
                                <div class="form-group">
                                    <label>卡卡联NFC支付 (0%-100%)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="kklpay" value="<#if (profit.kklpay)??>${profit.kklpay*0.01}<#else>0</#if>" placeholder="卡卡联NFC">
                                </div>
                                <div class="form-group">
                                    <label>卡卡联认证支付 (0%-100%)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="auth" value="<#if (profit.auth)??>${profit.auth*0.01}<#else>0</#if>" placeholder="卡卡联认证支付">
                                </div>
                                <div class="form-group">
                                    <label>通联支付 (0%-100%)</label>
                                    <input class="form-control" range="0~100" check-type="required number"
                                        name="allinpay" value="<#if (profit.allinpay)??>${profit.allinpay*0.01}<#else>0</#if>">
                                </div>
                            </div>
                            <div class="tab-pane fade" id="call_fees">
                                <p></p>
                                <label>话费充值费率 (单位：元) 【负数表示优惠】</label>
                                <div class="form-group">
                                    <label>面额 50元</label>
                                    <input class="form-control" range="-50~50" check-type="required number"
                                        name="call_50" value="<#if (profit.call_50)??>${profit.call_50*0.01}<#else>0</#if>" placeholder="话费充值费率">
                                </div>
                                <div class="form-group">
                                    <label>面额 100元</label>
                                    <input class="form-control" range="-100~100" check-type="required number"
                                        name="call_100" value="<#if (profit.call_100)??>${profit.call_100*0.01}<#else>0</#if>" placeholder="话费充值费率">
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">修改</button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </fieldset>
        </div>
    </div>
</form>

<script type="text/javascript">
 $(function(){
   $("form").validation();
   $("button[type='submit']").on('click',function(event){
     if ($("form").valid(this, "请检查输入是否有误")==true){
       var fields = $('.form-control');
       for (var i=0; i<fields.length; i++) {
         var val = parseInt(accMul($(fields[i]).val(), 100)) + '';
         if (val.indexOf('.')>0) {
           val = val.substring(0, val.indexOf('.'));
         }
         
         $(fields[i]).val(val);
       }
       return true;
     } else {
       return false;
     }
   })
})
</script>

</@manageBase.manageBase>
