package ax.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.alibaba.druid.pool.DruidDataSource;

@ComponentScan(basePackages={"ax.api","ax.config","ax.dao","ax.model"})
@EnableWebMvc
public class WebConfig {

	public WebConfig() {
		System.out.println("WebConfig");
	}
	
	@Bean
	public DataSource getDataSource(){
		System.out.println("getDataSource");
		//return getDruid("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/axtest","root", "123456");
		return getDruid("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/axtest?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
				"root", "123456");
	}
	
	public static DataSource getDruid(String driver,String url,String username,String password){
		System.out.println("Create DruidDataSource:"+url);
		
		DruidDataSource dds = new DruidDataSource();
		dds.setUrl(url);
		dds.setUsername(username);
		dds.setPassword(password);
		dds.setDriverClassName(driver);
		dds.setInitialSize(0);//初始化连接大小
		dds.setMaxActive(1500);//连接池最大使用连接数量
		dds.setMinIdle(0);//连接池最小空闲
		dds.setMaxWait(60000);//获取连接最大等待时间
		dds.setValidationQuery("select 1");//验证数据库连接有效性，要求查询语句
		
		//建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
		dds.setTestWhileIdle(true);
		
		//申请连接时执行validationQuery检测连接是否有效，配置true会降低性能。
		dds.setTestOnBorrow(false);
		
		//归还连接时执行validationQuery检测连接是否有效，配置true会降低性能
		dds.setTestOnReturn(false);
		
		//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dds.setTimeBetweenEvictionRunsMillis(60000);
		
		//配置一个连接在池中最小生存的时间，单位是毫秒
		dds.setMinEvictableIdleTimeMillis(25200000);
		
		//对于长时间不使用的连接强制关闭
		dds.setRemoveAbandoned(true);
		
		//关闭超过30分钟的空闲连接，1800秒，也就是30分钟
		dds.setRemoveAbandonedTimeout(1800);
		
		//关闭abanded连接时输出错误日志
		dds.setLogAbandoned(true);
		
		//监控数据库 
		//dds.setFilters("mergeStat");
		try {
			dds.setFilters("stat,wall");
		} catch (SQLException e) {
			System.out.println("error:"+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(dds.getDriverClassName());
		return dds;
	}
	
	@Bean("sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(DataSource ds){
		SqlSessionFactoryBean b=new SqlSessionFactoryBean();
		b.setDataSource(ds);
		
		
//		@SuppressWarnings("resource")
//		ApplicationContext ctx=new  FileSystemXmlApplicationContext();
		
//		try {
//			b.setMapperLocations(ctx.getResources("classpath*:sx/domain/dao/*.xml"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		try {
			return b.getObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Bean
	public MapperScannerConfigurer getMapperScannerConfigurer(){
		MapperScannerConfigurer m =new MapperScannerConfigurer();
		m.setSqlSessionFactoryBeanName("sqlSessionFactory");
		m.setBasePackage("ax.dao");
		return m;
	}
}
