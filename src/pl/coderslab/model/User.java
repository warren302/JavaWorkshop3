package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	private int id = 0;
	private String username;
	private String email;
	private String password;
	private int groupId;
	
	
	//Wczytywanie z bazy
	public User() {}
	
	
	//Tworzenie nowego
	public User(String username, String email, String password, int groupId) {
		super();
		setUsername(username);
		setEmail(email);
		setPassword(password);
		setGroupId(groupId);
	}
	
	
	public String getUsername() {
		return username;
	}
	
	
	public User setUsername(String username) {
		this.username = username;
		return this;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	
	public User setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt() );
		return this;
	}
	
	
	public String getEmail() {
		return email;
	}

	
	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	
	public int getId() {
		return id;
	}

	
	private User setId(int id) {
		this.id = id;
		return this;
	}
	
	
	public int getGroupId() {
		return groupId;
	}

	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getId()).append(", ");
		sb.append(this.getUsername()).append(", ");
		sb.append(this.getEmail()).append(", ");
		sb.append(this.getPassword()).append(", ");
		sb.append(this.getGroupId());
		return sb.toString();
	}
	

	/**
	 * Method downloads all records from table users 
	 * @return User[]
	 */
	static public User[] loadAll() {
		
		ArrayList<User> users = new ArrayList<User>();
		try (Connection conn = DbUtil.getConn();
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery("Select * from users");) {
				while (res.next()) {
					User tmpUsr = new User();
					tmpUsr.setUsername( res.getString("username"));
					tmpUsr.setEmail( res.getString("email"));
					tmpUsr.password = res.getString("password");
					tmpUsr.setGroupId(res.getInt("users_group_id"));
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
		String sql = "Select * from users where id = ?;";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, id);
				try (ResultSet res = pst.executeQuery()) {
					if (res.next()) {
						User tmpUsr = new User();
						tmpUsr.setUsername(res.getString("username"));
						tmpUsr.setEmail(res.getString("email"));
						tmpUsr.password = res.getString("password");
						tmpUsr.setGroupId(res.getInt("users_group_id"));
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
	public User saveToDB() {
		if (this.getId() == 0) {
			//add to db
			String[] generatedColumns = { "id" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement("Insert into  users ( username, email, password, users_group_id) Values( ?, ?, ?, ?)", generatedColumns)) {
					pst.setString(1, this.getUsername());
					pst.setString(2, this.getEmail());
					pst.setString(3, this.getPassword());
					pst.setInt(4, this.getGroupId());
					pst.executeUpdate();
					try (ResultSet rs = pst.getGeneratedKeys()) {
							if(rs.next()) {
								this.setId(rs.getInt(1));
							}
					} 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			//update to db
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement("Update users Set username=?, email=?, password=?, users_group_id = ? Where id = ?")) {
					pst.setString(1, this.getUsername());
					pst.setString(2, this.getEmail());
					pst.setString(3, this.getPassword());
					pst.setInt(4, this.getGroupId());
					pst.setInt(5, this.getId());
					pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;
	}

	
	/**
	 * Method returns an array of objects (User) which users_group_id equals parameter groupId
	 * @param groupId
	 * @return User[]
	 */
	static public User[] loadAllByGrupId(int groupId) {
		String sql = "Select * from users where users_group_id = ?;";
		ArrayList<User> list = new ArrayList<>();
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, groupId);
				try (ResultSet res = pst.executeQuery()) {
					while (res.next()) {
						User tmpUsr = new User();
						tmpUsr.setUsername(res.getString( "username" ));
						tmpUsr.setEmail(res.getString( "email" ));
						tmpUsr.password = res.getString( "password" );
						tmpUsr.setGroupId(res.getInt("users_group_id"));
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
	public void delete() {
		if (this.getId() != 0) {
			String sql = "Delete from users where id = ?;";
			try (Connection conn = DbUtil.getConn();
			PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, this.id);
				pst.executeUpdate();
				this.id = 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
	}
		
}


