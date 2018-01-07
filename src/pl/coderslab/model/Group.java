package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Group {
	private int id = 0;
	private String name;
	
	
	/**
	 * Constructor for downloading from the table
	 */
	public Group () {};

	
	/**
	 * Constructor for creating a new one 
	 * @param name
	 */
	public Group(String name) {
		super();
		setName(name);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getId() {
		return id;
	}

	
	private Group setId(int id) {
		this.id = id;
		return this;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getId()).append(", ");
		sb.append(this.getName());
		return sb.toString();
	}
	
	
	/**
	 * Method downloads all records from table users_group 
	 * @param conn
	 * @return Group[]
	 * @throws SQLException
	 */
	static public Group[] loadAll() {
		ArrayList<Group> groups = new ArrayList<Group>();
		try (Connection conn = DbUtil.getConn();
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery("Select * from users_group")) {
				while (res.next()) {
					Group tmpGrp = new Group();
					tmpGrp.setName(res.getString("name"));
					tmpGrp.setId(res.getInt("id"));
					groups.add(tmpGrp);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Group[] groupArr = new Group[groups.size()];
		groups.toArray(groupArr);
		return groupArr;
	}

	
	/**
	 * Method downloads one record which id is equal with given parameter
	 * @param conn
	 * @param id
	 * @return Group
	 * @throws SQLException
	 */
	static public Group loadById(int id) {
		String sql = "Select * from users_group where id = ?;";
		try (Connection conn = DbUtil.getConn();
			PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, id);
			try (ResultSet res = pst.executeQuery()) {
				if (res.next()) {
					Group tmpGroup = new Group();
					tmpGroup.setName(res.getString("name"));
					tmpGroup.setId(res.getInt("id"));
					return tmpGroup;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Method writes one object as a record to the database (users_group)
	 * if it is a new one or overwrites data if such exists  
	 * @param conn
	 * @return Group
	 * @throws SQLException
	 */
	public Group saveToDB() {
		if( this.getId() == 0) {
			//add to db
			String[] generatedColumns = { "id" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement("Insert into  users_group (name) Values( ?)", generatedColumns)) {
					pst.setString(1, this.getName());
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
					PreparedStatement pst = conn.prepareStatement("Update users_group Set name=? Where id = ?")) {
					pst.setString(1, this.getName());
					pst.setInt(2, this.getId());
					pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;
	}

	
	/**
	 * Method deletes one record from the table (users_group)
	 * @param conn
	 * @throws SQLException
	 */
	public void delete() {
		if (this.getId() != 0) {
			String sql = "Delete from users_group where id = ?;";
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
