package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Exercise {
	private int id = 0;
	private String title;
	private String description;

	
	/**
	 * Constructor for downloading records from the table
	 */
	public Exercise() {};

	
	/**
	 * Constructor for creating a new one 
	 * @param name
	 */	
	public Exercise(String title, String description) {
		super();
		this.setTitle(title);
		this.setDescription(description);
	}


	public int getId() {
		return id;
	}


	private Exercise setId(int id) {
		this.id = id;
		return this;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.id).append(", ");
		sb.append(this.title).append(", ");
		sb.append(this.description);
		return sb.toString();
	}
	
	
	/**
	 * Method downloads all records from table exercise 
	 * @return Exercise[]
	 */
	static public Exercise[] loadAll() {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		try (Connection conn = DbUtil.getConn();
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery("Select * from exercise;")) { 
				while (res.next()) {
					Exercise tmpEx = new Exercise();
					tmpEx.setId(res.getInt("id"));
					tmpEx.setTitle(res.getString("title"));
					tmpEx.setDescription(res.getString("description"));
					exercises.add(tmpEx);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Exercise[] exArr = new Exercise[exercises.size()];
		exercises.toArray(exArr);
		return exArr;
	}
	
	
	/**
	 * Method downloads one record which id is equal with given parameter
	 * @param conn
	 * @param id
	 * @return Exercise
	 * @throws SQLException
	 */
	static public Exercise loadById(int id) {
		String sql = "Select * from exercise where id = ?;";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, id);
				try (ResultSet res = pst.executeQuery()) {
					if (res.next()) {
						Exercise ex = new Exercise();
						ex.setId(res.getInt("id"));
						ex.setTitle(res.getString("title"));
						ex.setDescription(res.getString("description"));
						return ex;
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Method deletes one record from the table (exercise)
	 */
	public void delete() {
		if (this.id != 0) {
			String sql = "Delete from exercise where id = ?;";
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
	
	
	/**
	 * Method writes one object as a record to the table (exercise)
	 * if it is a new one or overwrites data if such exists  
	 * @return Exercise
	 */
	public Exercise saveToDB() {
		if (this.getId() == 0) {
			String[] generatedColumns = {"id"};
			String sql = "Insert into exercise (title, description) values( ?, ?);";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(sql, generatedColumns)) {
					pst.setString(1, this.getTitle());
					pst.setString(2, this.getDescription());
					pst.executeUpdate();
					try (ResultSet res = pst.getGeneratedKeys()) {
					if (res.next())
						this.setId(res.getInt(1));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = "Update exercise SET  title = ?, description = ? where id = ?;";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(sql)) {
					pst.setString(1, this.getTitle());
					pst.setString(2, this.getDescription());
					pst.setInt(3, this.getId());
					pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	/**
	 * Methods returns array of exercises for which user (with id == userId) has not written any solution
	 * @param userId
	 * @return array of exercises without solutions
	 */
	static public Exercise[] loadNotResolved(int userId) {
		String sql = "select * from exercise where id not in(select exercise_id from solution where users_id=?);";
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, userId);
				try (ResultSet res = pst.executeQuery()) {
					while (res.next()) {
						Exercise tmpEx = new Exercise();
						tmpEx.setId(res.getInt("id"));
						tmpEx.setTitle(res.getString("title"));
						tmpEx.setDescription(res.getString("description"));
						exercises.add(tmpEx);
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Exercise[] exArr = new Exercise[exercises.size()];
		exercises.toArray(exArr);
		return exArr;
	}
	
	
}
