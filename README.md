#TaoShopping
> * 前台：http://jeesoft.ngrok.io/
> * 后台：http://jeesoft.ngrok.io/manage/
>   后台管理员账号：admin # admin888


##接口

#### 用户模块
> * 101、用户注册：[POST] /users/insert? phone=手机号&pass=MD5密码
> * 102、用户更新：[POST] /users/update? id=1 [&name=用户名&pass=MD5密码&phone=手机号&email=邮箱&nickname=昵称&inviteId=推荐人ID&state=YES]
> * 103、用户查询：[GET] /users/selectOne? name=用户名&pass=MD5密码&phone=手机号&email=邮箱&nickname=昵称&inviteId=推荐人ID&state=YES
> * 104、用户登陆：[GET] /users/login? name=用户名 [&phone=手机号] [&nickname=昵称] &pass=MD5密码
> * 105、用户退出：[GET] /users/loginout

#### 话费模块
> * 201、创建充值订单：[POST] /call/insert? phone＝手机号&callMoney＝面额&remark＝备注
> * 202、订单付款完成：[POST] /call/update? sn＝订单号&payType＝支付方式&paySn＝支付订单号
> * 203、查询充值记录：[GET] /call/selectList? phone＝手机号&sn＝订单号

#### 余额模块
> * 301、创建充值订单：[POST] /balance/insert/recharge.do? linkSn=关联订单
> * 302、创建取现订单：[POST] /balance/insert/withdraw.do? money=费用&remark＝备注
> * 303、订单付款完成：[POST] /balance/payoff? sn=充值订单号&payType＝支付方式&paySn＝支付订单号
> * 304、查询订单记录：[GET] /balance/selectList? sn=充值订单号&isAdd＝是否余额充值
> * 305、查询账户余额：[GET] /balance/queryOne? id=用户ID





##管理后台

#### 登陆
> * 管理员登陆           /manage/login
> * 后台首页            /manage/index

#### 用户管理
> * 用户注册审核
> * 用户查询
> * 查看用户详情
> * 掉单补单            /pay/restore.do? sn=充值订单号&paySn=支付方订单号&payType=支付方式




