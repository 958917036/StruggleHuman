package gc.study.base.generator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/*
 * 使用generotro生成mybatis逆向工程
 */
public class TestGenerator {

	public static void main(String[] args) throws Exception {
		test();
	}
	/*
	 * 运行这个测试方法前
	 * 	gc.study.base.generator.bean
	 * 	gc.study.base.generator.dao
	 * conf目录下gc.study.base.generator.dao  都是空的，只需要提前建立package即可
	 */
	private static void test() throws Exception {
		List<String> warnings = new ArrayList<String>();
		   boolean overwrite = true;
		   File configFile = new File("generator.xml");
		   if(configFile.exists()) {
			   System.out.println(configFile.getPath());
		   }
		   ConfigurationParser cp = new ConfigurationParser(warnings);
		   Configuration config = cp.parseConfiguration(configFile);
		   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		   myBatisGenerator.generate(null);
	}
	
}
