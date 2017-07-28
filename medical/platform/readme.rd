声明：
	本人提供这个 SpringMVC + Mybatis + Redis 的Demo 本着学习的态度，如果有欠缺和不足的地方，给予指正，并且多多包涵.
	PS:最近发现Spring Boot十分火但是了解了几次都觉得没意思，大家能给我说说为什么么？
	
框架使用环境：

		Maven:3.3.9		Eclipse:MARS.2
		JDK:1.8				Tomcat:8.0.36
		
框架包含内容：

	一、权限控制
	
		权限曾一度打算采用Shiro来实现，确实了解之后也发现了Shiro的强大，但是强大之余也发现自己并用不了那么多东西，其实现在权限大都采用RBAC(Role-Based Access Control)这种模型来实现的访问控制，本项目也是采用这种模型
		来实现的访问控制。在这之前先介绍一下三个自定义注解
		@RequiresRoles:在本系统代表必须具备某角色才能访问该方法 如果满足角色要求将不会继续判断权限
		@RequiresPermission:在本系统代表必须具备某权限才能访问该方法 如果满足权限要求将不会继续判断操作
		@RequiresOperation:在本系统代表必须具备某操作权限才能访问该方法 这是目前权限控制的最小颗粒，至于跟业务相关的权限只能在业务逻辑中判断了
		而这些注解全作用于方法上
		至于实现思路很简单，利用拦截器接管所有请求，然后根据用户携带的token(登录时会返回一个token作为用户的合法令牌)从缓存中获取用户信息并从数据库加载出改用户的角色和权限数据然后根据反射获取访问该方法的注解的值
		比较是否满足 满足则继续执行 不满足则提示相关操作无权限之类的，这里我是把权限信息首次加载后放入缓存，系统内的高热点数据建议大家都放入缓存效率高得多，然后有地方修改对应用户的权限时清除改用户的权限缓存，具体
		的请参照Spring Cache这里完全把Redis和Spring Cache结合在一起了 完全不需要代码来操作redis(除了业务中需要特殊处理的东西)

		PS:关于RBAC附上一个博客地址，这里介绍得很好http://blog.csdn.net/painsonline/article/details/7183613/
		
	二、日志异常提醒
	
		日志采用的是slf4j,为啥配置文件就是log4j.xml大家可以百度一下这两个的关系，要求高一点的可以使用logback,基本的配置没啥技术难度，无非就是集成了网络上很多大咖的建议的综合体。
		日志我除了控制台打印的之外，其他对INFO、WARN、ERROR三种日志进行了分类操作 每种日志分类写入并按天分割
		然后对于错误级别及以上日志进行了特殊处理，发生错误及以上级别的日志都会进行邮件提醒，这里踩了很多坑，网络上也有教程 但是大多数都是同步发送邮件（SMTP其实超慢滴，同步你就死了）还有就是网络上的配置都是一个错误
		发送一封邮件（这要是错误率低的系统还好，要是不成熟的话，每天报个千儿八百的错那你就哭了），你去百度一定会百度到很多说啥配置BufferSize就可以控制了之类的问题，跟你们打包票的说，这都是胡乱转载人家的帖子，人家原文
		说的是写入日志的配置是这个意思（xml内我打了注释那几个）但是发送日志源码的意思是缓冲多少个Event事件（一个异常一个事件）然后超过这个BufferSize(默认512)设定的值之后覆盖之前的事件，就这个意思而已，人家判断发送邮件
		的条件是event的级别是否为error。
		所以这里我重写了他的SMTPAppender类，实现了ErrorSize参数，控制当多少个event事件发送一次，具体的请参考源码
		
	三、Spring+Redis整合
	
		这里就简单带过就是，重写了spring-data-redis里面的几个类，目的为了实现redis的访问自动延期，和为单用户登录提供了些方法
		
	四、单用户登录（同一个用户只能在一个地方登录）
	
		这里大家最好看源码，我登录里面调用了，自己跟踪去看看怎实现的（轮询缓存也可以实现，但是呵呵）
		
	五、RSA分段加密
	
		这个为啥要单独提出来呢！目前JDK中提供的最牛的加密算法了，虽然ECC比这更牛，可是JDK没整完，具体类在utils项目的rsa包内，类中提供了密钥对的生成、加密、解密、和转化为.Net格式的密钥等方法，并采用分段加密解密技术
		突破了加密内容的长度限制，但是内容越多加密越慢！视场景而使用。
		
	六、Spring Mail
	
		这个网络上都有配置，我只是包装了些实体类简化调用
		
	七、实体类校验
	
		采用的是Hibernate Validator校验，但是它使用都尼玛要在方法中每个都去判断，这对我这类懒得XX的人来说肯定受不了，于是就用拦截器统一处理了，具体参照org.system.intercept.ValidInterceptor.java
		
	八、自动分页
	
		org.system.intercept.PaginationInterceptor.java 不多说拦截的是mybatis，如果设置了page和rows这两个分页参数那么我就和先查询总数放入实体类的total属性中并对接下了的查询limit操作,就这样
		
	九、全局异常处理
	
		org.system.exception.ExceptionResolver.java拦截所以异常并封装成json格式，没做全局页面的配置，拦截器中是根据返回是否是json来判断的，不是json原来该怎么做就怎么做不影响
		
	十、多数据源配置
	
		系统还实现了多数据源配置，使用起来可能会让你觉得异常简单，自定义了一个注解@DataSource 里面只需要填数据源名称就能切换了，可作用于类、方法上，还有就是提供了DataSourceContextHolder类，里面有相应方法可以在
		业务逻辑中随时切换数据源 他们之前的优先级关系是DataSourceContextHolder>@DataSource(方法)>@DataSource(类)
		
	十一、其他
	
		好像没啥可以拿出来说的了，哦，对了，json解析器换成了fastjson,原因嘛百度fastjson和jackson就知道了，这里不是快了一点点，还有也配置了Spring Task 简单的定时任务，org.service.task.ServiceTask.java这里面有使用demo
		