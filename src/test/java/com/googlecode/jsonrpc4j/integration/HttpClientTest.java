package com.googlecode.jsonrpc4j.integration;

import com.googlecode.jsonrpc4j.ProxyUtil;
import com.googlecode.jsonrpc4j.util.BaseRestTest;
import com.googlecode.jsonrpc4j.util.FakeServiceInterface;
import com.googlecode.jsonrpc4j.util.FakeServiceInterfaceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;

/**
 * HttpClientTest
 */
public class HttpClientTest extends BaseRestTest {
	
	private FakeServiceInterface service;
	
	@Test
	public void testGZIPRequest() throws MalformedURLException {
		service = ProxyUtil.createClientProxy(this.getClass().getClassLoader(), FakeServiceInterface.class, getHttpClient(true, false));
		int i = service.returnPrimitiveInt(2);
		Assert.assertEquals(2, i);
	}
	
	@Test
	public void testGZIPRequestAndResponse() throws MalformedURLException {
		service = ProxyUtil.createClientProxy(this.getClass().getClassLoader(), FakeServiceInterface.class, getHttpClient(true, true));
		int i = service.returnPrimitiveInt(2);
		Assert.assertEquals(2, i);
	}
	
	@Test
	public void testRequestAndResponse() throws MalformedURLException {
		service = ProxyUtil.createClientProxy(this.getClass().getClassLoader(), FakeServiceInterface.class, getHttpClient(false, false));
		int i = service.returnPrimitiveInt(2);
		Assert.assertEquals(2, i);
	}

	@Test
	public void testErrorResponseChecking() throws MalformedURLException {
		expectedEx.expectMessage("<html>\n" +
				"<head>\n" +
				"<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"/>\n" +
				"<title>Error 405 HTTP method POST is not supported by this URL</title>\n" +
				"</head>\n" +
				"<body><h2>HTTP ERROR 405</h2>\n" +
				"<p>Problem accessing /error. Reason:\n" +
				"<pre>    HTTP method POST is not supported by this URL</pre></p><hr><a href=\"http://eclipse.org/jetty\">Powered by Jetty:// 9.4.0.RC3</a><hr/>\n" +
				"\n" +
				"</body>\n" +
				"</html>\n");
		service = ProxyUtil.createClientProxy(this.getClass().getClassLoader(), FakeServiceInterface.class, getHttpClient("error"));
		service.doSomething();
	}

    @Override
	protected Class service() {
		return FakeServiceInterfaceImpl.class;
	}
}
