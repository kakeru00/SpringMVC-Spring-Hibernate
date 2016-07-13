import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zh.controller.UserController;
import com.zh.dao.UserDao;
import com.zh.entity.User;
import com.zh.service.UserService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring.xml","classpath:SpringMVC.xml"})
public class UserTest {
	
	@Resource(name="dataSource")
	private DataSource ds ;
	@Autowired
	UserController uc;  
	
	@Resource
	UserService us;
	
	@Ignore
	@Test 
	public void test() throws SQLException {
		
		//System.out.println(ds.getUser()+ds.getPassword());
		Connection conn = ds.getConnection();
		
		String sql = "select * from a";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs =ps.executeQuery();
		while(rs.next()){
			
			System.out.println(rs.getString(1));
		}
		
		System.out.println();
		rs.close();
		ps.close();
		conn.close();
		
	}
	@Ignore
	@Test
	public void userServiceTest(){
		
		if(us.get(3)==null)
			for (int i = 1; i <= 3; i++) {
				
				us.save(new User(i+"","",""));
			}
		//us.delete(new User(1,"email","name","password"));
		System.out.println(User.class.getName());
		List<User> users = us.find(DetachedCriteria.forClass(User.class), 1, 3);
		for(User user : users){
			System.out.println(user.getName());
		}
		
		List<User> allusers = us.find();
		for(User user : allusers){
			System.out.println(user.getName());
		}
	}

	


}
