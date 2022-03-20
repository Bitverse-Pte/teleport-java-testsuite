# bug

### client error1:

```go
2022-01-04 07:59:35.970  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : sleeping...
2022-01-04 07:59:38.970  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : we will get the stored data from leveldb
2022-01-04 07:59:39.021  INFO 1 --- [io-12000-exec-3] com.db.LevelDBUtils                      : we have closed the db
2022-01-04 07:59:39.021  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : we have get the stored data from leveldb
2022-01-04 07:59:39.024  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : send seq:0
2022-01-04 07:59:39.117  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : send seq:50
2022-01-04 07:59:39.298  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : send seq:100
2022-01-04 07:59:39.474  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : send seq:150
2022-01-04 07:59:39.645  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : send seq:200
2022-01-04 07:59:40.225  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : invalid nonce; got 1246, expected 1236: invalid sequence: invalid sequence
2022-01-04 08:00:10.228  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : current nonce is1062
2022-01-04 08:00:10.230  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : invalid nonce; got 1246, expected 1062: invalid sequence: invalid sequence
2022-01-04 08:00:40.232  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : current nonce is1062
2022-01-04 08:00:40.234  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : invalid nonce; got 1246, expected 1062: invalid sequence: invalid sequence
2022-01-04 08:01:10.236  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : current nonce is1062
2022-01-04 08:01:10.238  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : invalid nonce; got 1246, expected 1062: invalid sequence: invalid sequence
2022-01-04 08:01:40.241  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : current nonce is1062
2022-01-04 08:01:40.243  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : invalid nonce; got 1246, expected 1062: invalid sequence: invalid sequence
2022-01-04 08:02:10.246  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : current nonce is1062
2022-01-04 08:02:10.248  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : invalid nonce; got 1246, expected 1062: invalid sequence: invalid sequence
2022-01-04 08:02:40.250  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : current nonce is1062
2022-01-04 08:02:40.252  INFO 1 --- [io-12000-exec-3] com.service.PerfTestService              : invalid nonce; got 1246, expected 1062: invalid sequence: invalid sequence
```

### client error2:

```
java.net.SocketTimeoutException: timeout
	at okio.SocketAsyncTimeout.newTimeoutException(JvmOkio.kt:143)
	at okio.AsyncTimeout.access$newTimeoutException(AsyncTimeout.kt:162)
	at okio.AsyncTimeout$source$1.read(AsyncTimeout.kt:335)
	at okio.RealBufferedSource.indexOf(RealBufferedSource.kt:427)
	at okio.RealBufferedSource.readUtf8LineStrict(RealBufferedSource.kt:320)
	at okhttp3.internal.http1.HeadersReader.readLine(HeadersReader.kt:29)
	at okhttp3.internal.http1.Http1ExchangeCodec.readResponseHeaders(Http1ExchangeCodec.kt:178)
	at okhttp3.internal.connection.Exchange.readResponseHeaders(Exchange.kt:106)
	at okhttp3.internal.http.CallServerInterceptor.intercept(CallServerInterceptor.kt:79)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.connection.ConnectInterceptor.intercept(ConnectInterceptor.kt:34)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.cache.CacheInterceptor.intercept(CacheInterceptor.kt:95)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.http.BridgeInterceptor.intercept(BridgeInterceptor.kt:83)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(RetryAndFollowUpInterceptor.kt:76)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.connection.RealCall.getResponseWithInterceptorChain$okhttp(RealCall.kt:201)
	at okhttp3.internal.connection.RealCall.execute(RealCall.kt:154)
	at org.web3j.protocol.http.HttpService.performIO(HttpService.java:165)
	at org.web3j.protocol.Service.send(Service.java:48)
	at org.web3j.protocol.core.Request.send(Request.java:87)
	at com.service.PerfTestService.sendTxs(PerfTestService.java:146)
	at com.controller.PerfTestController.send_txs(PerfTestController.java:40)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:221)
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:137)
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:110)
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:777)
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:706)
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:85)
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:943)
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:877)
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:966)
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:857)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:618)
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:842)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:725)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:291)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:239)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)
	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:77)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:239)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:219)
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:106)
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:142)
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:79)
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:88)
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:537)
	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1085)
	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:658)
	at org.apache.coyote.http11.Http11NioProtocol$Http11ConnectionHandler.process(Http11NioProtocol.java:222)
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1556)
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.run(NioEndpoint.java:1513)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.net.SocketTimeoutException: Read timed out
	at java.net.SocketInputStream.socketRead0(Native Method)
	at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
	at java.net.SocketInputStream.read(SocketInputStream.java:171)
	at java.net.SocketInputStream.read(SocketInputStream.java:141)
	at okio.InputStreamSource.read(JvmOkio.kt:90)
	at okio.AsyncTimeout$source$1.read(AsyncTimeout.kt:129)
	... 65 common frames omitted

```

### client bug3:
多节点发送的时候类似这类的问题，比较影响测试。
```
2022-01-05 03:16:43.819  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : invalid nonce; got 1986, expected 1991: invalid sequence: invalid sequence
2022-01-05 03:16:43.829  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current pending nonce is: 1986
2022-01-05 03:16:43.829  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current tx seq: 1986
2022-01-05 03:16:43.850  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : invalid nonce; got 1986, expected 1991: invalid sequence: invalid sequence
2022-01-05 03:16:43.879  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current pending nonce is: 1986
2022-01-05 03:16:43.880  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current tx seq: 1986
2022-01-05 03:16:43.897  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : invalid nonce; got 1986, expected 1991: invalid sequence: invalid sequence
2022-01-05 03:16:43.939  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current pending nonce is: 1986
2022-01-05 03:16:43.940  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current tx seq: 1986
2022-01-05 03:16:43.950  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : invalid nonce; got 1986, expected 1991: invalid sequence: invalid sequence
2022-01-05 03:16:43.979  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current pending nonce is: 1986
2022-01-05 03:16:43.979  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current tx seq: 1986
2022-01-05 03:16:44.049  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : invalid nonce; got 1986, expected 1955: invalid sequence: invalid sequence
2022-01-05 03:16:44.058  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current pending nonce is: 1955
2022-01-05 03:16:44.058  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : current tx seq: 1955
2022-01-05 03:16:44.071  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : send seq:700
2022-01-05 03:16:44.479  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : send seq:750
2022-01-05 03:16:47.001  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : send seq:800
2022-01-05 03:16:47.445  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : send seq:850
2022-01-05 03:16:47.854  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : send seq:900
2022-01-05 03:16:48.729  INFO 1 --- [io-12000-exec-7] com.service.PerfTestService              : send seq:950

```

### mempool nonce 下滑

目前观察对测试有影响。

扩展账户数量是否能够获取更为准确的数值？