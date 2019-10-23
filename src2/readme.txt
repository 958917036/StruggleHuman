mybatis工作流程

获取sqlSession对象
	返回一个DefaultSqlSession对象，包含Executor和Configuration（包含所有配置文件解析结果）
	这一步会创建Executor对象（用于执行sql语句）
获取接口的代理对象（MapperProxy）
	getMapper方法，使用MapperProxyFactory创建一个MapperProxy的代理对象（mapper接口的动态代理）
	代理对象里面包含了DefaultSqlSession
执行增删改查方法
	代理对象 -> DefaultSqlSession -> Executor -> StatementHandler
	-> ParamterHandler,resultSetHandler -> TypeHandler -> Jdbc Statement
	
	StatementHandler:处理sql语句预编译，设置参数等相关工作
		parameterHandler:设置预编译参数,设置到TypeHandler中
		resultSetHandler:处理结果，使用TypeHandler来获取结果
		TypeHandler：在整个过程中进行数据库列和java bean字段类型的转换
	
总结：
	1.根据配置文件（全局，sql映射）初始化Configuration对象
	2.创建一个DefaultSqlSession对象
		里面包含configuration对象以及
		Executor（根据全局配置文件的defaultExecutorType创建出的Executor）	
	3.DefaultSqlSession.getMapper 拿到mapper对应接口的mapperProxy
	4.MapperProxy里面有DefaultSqlSession
	5.执行增删改查方法
		调用DefaultSqlSession的增删改查（Executor）
		创建一个StatementHandler和ResultSetHandler
		调用StatementHandler预编译以及设置参数值
			使用ParameterHandler来给sql设置参数
		调用StatementHandler的增删改查方法
		ResultSetHandler封装结果