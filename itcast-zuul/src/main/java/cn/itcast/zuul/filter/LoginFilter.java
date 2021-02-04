package cn.itcast.zuul.filter;

import com.ctc.wstx.util.StringUtil;
import com.netflix.zuul.IZuulFilter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/4 6:01 下午
 */

@Component //注入Spring容器
public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的业务逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
//        初始化context上下文对象，servlet spring
        RequestContext context = RequestContext.getCurrentContext();

//        获取Request对象
        HttpServletRequest request = context.getRequest();

//        获取参数
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
//            拦截 不转发请求
            context.setSendZuulResponse(false);
//            响应状态码 401身份未认证
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
//            设置响应提示
            context.setResponseBody("request error");
        }

        return null;
    }
}
