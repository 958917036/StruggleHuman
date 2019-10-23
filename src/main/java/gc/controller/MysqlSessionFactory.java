package gc.controller;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MysqlSessionFactory {
	public static SqlSessionFactory getSqlSessionFactory(){
		try {
			
			String resource = "mybatis_config.xml";
			InputStream in = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
			return sqlSessionFactory;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
