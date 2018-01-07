package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDao {
	static String LOAD_ALL = "Select * from users";
	static String LOAD_ONE = "Select * from users where id = ?;";
	static String INSERT_ONE = "Insert into  users ( username, email, password, users_group_id) Values( ?, ?, ?, ?)"; 
	static String UPDATE_ONE = "Update users Set username=?, email=?, password=?, users_group_id = ? Where id = ?";
	static String DELETE_ONE = "Select * from users where users_group_id = ?;";

	
	/**
	 * Method downloads all records from table users 
	 * @return User[]
	 */
	static public User[] loadAll() {
		
		ArrayList<User> users = new ArrayList<User>();
		try (Connection conn = DbUtil.getConn();
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery(LOAD_ALL);) {
				while (res.next()) {
					User tmpUsr = new User();
					tmpUsr.setUsername( res.getString("username"));
					tmpUsr.setEmail( res.getString("email"));
					tmpUsr.password = res.getString("password");
					tmpUsr.setGroup(GroupDao.loadById(res.getInt("users_group_id")));
					tmpUsr.setId( res.getInt("id"));
					users.add(tmpUsr);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		User[] userArr =  new User[users.size()];
		users.toArray(userArr);
		return userArr;
	}

	
	/**
	 * Method downloads one record which id is equal with given parameter
	 * @param id
	 * @return User
	 */
	static public User loadById(int id) {
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(LOAD_ONE)) {
				pst.setInt(1, id);
				try (ResultSet res = pst.executeQuery()) {
					if (res.next()) {
						User tmpUsr = new User();
						tmpUsr.setUsername(res.getString("username"));
						tmpUsr.setEmail(res.getString("email"));
						tmpUsr.password = res.getString("password");
						tmpUsr.setGroup(GroupDao.loadById(res.getInt("users_group_id")));
						tmpUsr.setId(res.getInt("id"));
						return tmpUsr;
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Method writes one object as a record to the table (users)
	 * if it is a new one or overwrites data if such exists  
	 * @return User
	 */
	static public void saveToDB(User user) {
		if (user.getId() == 0) {
			//add to db
			String[] generatedColumns = { "id" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(INSERT_ONE, generatedColumns)) {
					pst.setString(1, user.getUsername());
					pst.setString(2, user.getEmail());
					pst.setString(3, user.getPassword());
					pst.setInt(4, user.getGroup().getId());
					pst.executeUpdate();
					try (ResultSet rs = pst.getGeneratedKeys()) {
							if(rs.next()) {
								user.setId(rs.getInt(1));
							}
					} 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			//update to db
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(UPDATE_ONE)) {
					pst.setString(1, user.getUsername());
					pst.setString(2, user.getEmail());
					pst.setString(3, user.getPassword());
					pst.setInt(4, user.getGroup().getId());
					pst.setInt(5, user.getId());
					pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Method returns an array of objects (User) which users_group_id equals parameter groupId
	 * @param groupId
	 * @return User[]
	 */
	static public User[] loadAllByGrupId(int groupId) {
		ArrayList<User> list = new ArrayList<>();
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(DELETE_ONE)) {
				pst.setInt(1, groupId);
				try (ResultSet res = pst.executeQuery()) {
					while (res.next()) {
						User tmpUsr = new User();
						tmpUsr.setUsername(res.getString( "username" ));
						tmpUsr.setEmail(res.getString( "email" ));
						tmpUsr.password = res.getString( "password" );
						tmpUsr.setGroup(GroupDao.loadById(res.getInt("users_group_id")));
						tmpUsr.setId( res.getInt( "id" ) );
						list.add(tmpUsr);
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		User[] tmpArr = new User[list.size()];
		list.toArray(tmpArr);
		return tmpArr;
	}
	
	
	/**
	 * Method deletes one record from the table (users)
	 */
	static public void delete(User user) {
		if (user.getId() != 0) {
			String sql = "Delete from users where id = ?;";
			try (Connection conn = DbUtil.getConn();
			PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, user.getId());
				pst.executeUpdate();
				user.setId(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
	}
	
	
	
}
