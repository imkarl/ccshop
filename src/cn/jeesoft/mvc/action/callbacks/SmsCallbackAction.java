package cn.jeesoft.mvc.action.callbacks;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jeesoft.core.utils.ResponseUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.outapi.SmsHelper;

/**
 * 短信回调
 * @author king
 */
@RestController("sms")
@RequestMapping("sms")
public class SmsCallbackAction {

//    /**
//     * 检验验证码是否正确
//     */
//    @ResponseBody
//    @RequestMapping(value="verify")
//	public String verify(HttpServletRequest request) {
//    	String code = request.getParameter("code");
//    	
//		synchronized (this) {
//			CodeStatus status = SmsHelper.verify(code);
//    		if (status == CodeStatus.YES) {
//    			return ResponseUtils.toSuccess();
//    		} else if (status == CodeStatus.NO) {
//    			return ResponseUtils.toFailure(ResultCode.ERROE_PARAMETER, "验证码不正确");
//    		} else if (status == CodeStatus.EXPIRE) {
//    			return ResponseUtils.toFailure(ResultCode.ERROE_PARAMETER, "验证码已过期");
//    		}
//		}
//    	
//		return ResponseUtils.toFailure(ResultCode.ERROE_PARAMETER, "验证码不正确");
//	}
    
    /**
     * 检验验证码是否正确
     */
    @ResponseBody
    @RequestMapping(value="send")
    public String send(HttpServletRequest request) {
    	String phone = request.getParameter("phone");
    	if (StringUtils.isPhone(phone)) {
    		int result = SmsHelper.sendMessage(phone);
    		if (result == 1) {
    			return ResponseUtils.toSuccess();
    		} else if (result == -1) {
            	return ResponseUtils.toFailure(ResultCode.ERROE_PARAMETER, "验证码服务余额不足");
        	} else {
            	return ResponseUtils.toFailure(ResultCode.ERROE_PARAMETER, "发送验证码失败");
    		}
    	} else {
        	return ResponseUtils.toFailure(ResultCode.ERROE_PARAMETER, "手机号格式不正确");
		}
    }
	
}
