package com.zxf.Fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
@Component
public class ProducerFallback implements FallbackProvider{
	
	/**
	 * 
	 * 告诉Zuul它是负责哪个route定义的熔断
	 */
	public String getRoute() {
		// TODO Auto-generated method stub
		return "zxf-lancoo-feignMember";
	}

	/**
	 * fallbackResponse方法则是告诉 Zuul 断路出现时，它会提供一个什么返回值来处理请求
	 * 
	 */
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		 return new ClientHttpResponse() {
	            @Override
	            public HttpStatus getStatusCode() throws IOException {
	                return HttpStatus.OK;
	            }

	            @Override
	            public int getRawStatusCode() throws IOException {
	                return 200;
	            }

	            @Override
	            public String getStatusText() throws IOException {
	                return "OK";
	            }

	            @Override
	            public void close() {

	            }

	            @Override
	            public InputStream getBody() throws IOException {
	                return new ByteArrayInputStream("该服务不可用...".getBytes());
	            }

	            @Override
	            public HttpHeaders getHeaders() {
	                HttpHeaders headers = new HttpHeaders();
	                headers.setContentType(MediaType.APPLICATION_JSON);
	                return headers;
	            }
	        };
	}

}
