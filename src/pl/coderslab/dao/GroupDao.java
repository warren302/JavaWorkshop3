package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GroupDao {
	static String LOAD_ALL = "Select * from users_group";
	static String LOAD_BY_ID = "Select * from users_group where id = ?;";
	static String INSERT_ONE = "Insert into  users_group (name) Values( ?)";
	static String UPDATE_ONE = "Update users_group Set name=? Where id = ?";
	static String DELETE_ONE = "Delete from users_group where id = ?;";
	
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
				ResultSet res = st.executeQuery(LOAD_ALL)) {
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

		try (Connection conn = DbUtil.getConn();
			PreparedStatement pst = conn.prepareStatement(LOAD_BY_ID)) {
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
	static public void saveToDB(Group group) {
		if (group.getId() == 0) {
			//add to db
			String[] generatedColumns = { "id" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(INSERT_ONE, generatedColumns)) {
					pst.setString(1, group.getName());
					pst.executeUpdate();
					try (ResultSet rs = pst.getGeneratedKeys()) {
						if(rs.next()) {
							group.setId(rs.getInt(1));
						}
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			//update to db
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(UPDATE_ONE)) {
					pst.setString(1, group.getName());
					pst.setInt(2, group.getId());
					pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Method deletes one record from the table (users_group)
	 * @param conn
	 * @throws SQLException
	 */
	static public void delete(Group group) {
		if (group.getId() != 0) {
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(DELETE_ONE)) {
					pst.setInt(1, group.getId());
					pst.executeUpdate();
					group.setId(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
}
