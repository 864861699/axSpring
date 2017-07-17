package ax.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import ax.model.Test;


@Repository
public interface ITest {
	@Select("select * from test where id=#{id}")
	Test getInfo(int id);
}
