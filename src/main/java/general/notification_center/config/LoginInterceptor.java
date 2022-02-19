package general.notification_center.config;

import general.notification_center.utils.HttpResult;
import general.notification_center.utils.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 拦截未认证的请求
 *
 * @author 小乐乐
 * @date 2022/2/18 21:28
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final String PREFIX_BEARER = "Bearer ";

    private void returnErrorJson(HttpServletResponse response) throws IOException {
        String errorResult = HttpResult.error(401, "认证失败").toString();
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(errorResult);
        }
    }

    /**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义 Controller
     * 返回值：
     * true 表示继续流程（如调用下一个拦截器或处理器）；
     * false 表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过 response 来产生响应。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Method method = handlerMethod.getMethod();
        // 检查是否有 @LoginExcept 注解
        if (method.isAnnotationPresent(LoginExcept.class)) {
            return true;
        }
        String token;
        try {
            token = request.getHeader(JwtUtil.tokenHeader).replace(PREFIX_BEARER, "");
        } catch (Exception e) {
            token = null;
        }

        Integer userId = JwtUtil.verifyToken(token);

        if (null == userId) {
            returnErrorJson(response);
            return false;
        }
        return true;
    }
}
