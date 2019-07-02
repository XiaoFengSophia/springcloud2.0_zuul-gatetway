package com.zxf.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
/**
 * 实现自定义Filter，需要继承ZuulFilter的类，并覆盖其中的4个方法
 * @author lancoo
 *
 */
@Component
public class TokenFilter extends ZuulFilter {
	@Value("${server.port}")
	private String serverPort;

	//编写过滤器拦截业务逻辑代码
	@Override
	public Object run() throws ZuulException {
		// 获取上下文
		RequestContext currentContext = RequestContext.getCurrentContext();
		//获取requesst
		HttpServletRequest request = currentContext.getRequest();
		//获取token的时候从请求中获取
		String userToken = request.getParameter("userToken");
		if (StringUtils.isEmpty(userToken)) {
			//不会继续执行，不回去调用服务接口，网关服务直接响应给客户端
			currentContext.setSendZuulResponse(false);
			currentContext.setResponseStatusCode(401);
			currentContext.setResponseBody("userToken is null");
			return null;
			//返回一个错误提示
		}
		// 否则正常执行业务逻辑.....
		System.out.println(serverPort);
		return null;

	}
	//表示是否需要执行该filter，true表示执行，false表示不执行
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}
	//定义filter的顺序，数字越小表示顺序越高，越先执行
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * PRE： 这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
	 * ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
	 * POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
	 * ERROR：在其他阶段发生错误时执行该过滤器。 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。
	 */
	//定义filter的类型，有pre、route、post、error四种
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

}
