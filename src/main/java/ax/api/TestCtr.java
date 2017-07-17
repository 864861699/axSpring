package ax.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ax.dao.ITest;
import ax.model.Test;


@RequestMapping("test")
@Controller
public class TestCtr {
	public TestCtr() {
		System.out.println("TestCtrl");
	}
	
//	@Autowired
//	DataSource ds;
//	
//	@RequestMapping("home")
//	@ResponseBody
//	public String home(){
//		JdbcTemplate jdbc=new JdbcTemplate(ds);
//		List  list = jdbc.queryForList("select * from test");
//		System.out.println(list);
//		return new Date().getTime()+"";
//	}
	
	@Autowired
	ITest test;
	
	
	@RequestMapping("getTest")
	@ResponseBody
	public String getTest(){
		Test t = test.getInfo(2);
		System.out.println(t.getTitle());
		
		return "11";
	}
}
