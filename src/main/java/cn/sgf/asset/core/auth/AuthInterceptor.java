package cn.sgf.asset.core.auth;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dto.UserDTO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * 拦截器
 * @ 用来判断用户的
 *1. 当preHandle方法返回false时，从当前拦截器往回执行所有拦截器的afterCompletion方法，再退出拦截器链。也就是说，请求不继续往下传了，直接沿着来的链往回跑。
 2.当preHandle方法全为true时，执行下一个拦截器,直到所有拦截器执行完。再运行被拦截的Controller。然后进入拦截器链，运
 行所有拦截器的postHandle方法,完后从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
 */
 
@Component
public class AuthInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
 
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	logger.debug("拦截器preHandle方法");
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)) {
        	tokenError(response);
            return false;
        }
        if(!AuthUtil.validToken(token)) {
        	tokenError(response);
        	return false;
        }
        return true;
    }
    
    private void tokenError(HttpServletResponse response) {
    	 response.setCharacterEncoding("UTF-8");
    	 response.setContentType("text/html; charset=utf-8");
    	 response.setStatus(HttpStatus.UNAUTHORIZED.value());
         try {
        	 ServletOutputStream out = response.getOutputStream();
        	 String error = "token失效";
             out.write(error.getBytes());
             out.flush();
         } catch (IOException e) {
        	 logger.error("response error",e);
         } 
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
 
}
