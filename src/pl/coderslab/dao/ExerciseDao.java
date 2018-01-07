package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExerciseDao {
	static String LOAD_ALL = "Select * from exercise;";
	static String LOAD_ONE = "Select * from exercise where id = ?;";
	static String DELETE_ONE = "Delete from exercise where id = ?;";
	static String INSERT_ONE = "Insert into exercise (title, description) values( ?, ?);";
	static String UPDATE_ONE = "Update exercise SET  title = ?, description = ? where id = ?;";
	static String LOAD_NOT_RESOLVED = "select * from exercise where id not in(select exercise_id from solution where users_id=?);";

	
	/**
	 * Method downloads all records from table exercise 
	 * @return Exercise[]
	 */
	static public Exercise[] loadAll() {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		try (Connection conn = DbUtil.getConn();
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery(LOAD_ALL)) { 
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
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(LOAD_ONE)) {
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
	static public void delete(Exercise exercise) {
		if (exercise.getId() != 0) {
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(DELETE_ONE)) {
					pst.setInt(1, exercise.getId());
					pst.executeUpdate();
					exercise.setId(0);
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
	static public void saveToDB(Exercise exercise) {
		if (exercise.getId() == 0) {
			String[] generatedColumns = {"id"};
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(INSERT_ONE, generatedColumns)) {
					pst.setString(1, exercise.getTitle());
					pst.setString(2, exercise.getDescription());
					pst.executeUpdate();
					try (ResultSet res = pst.getGeneratedKeys()) {
					if (res.next())
						exercise.setId(res.getInt(1));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(UPDATE_ONE)) {
					pst.setString(1, exercise.getTitle());
					pst.setString(2, exercise.getDescription());
					pst.setInt(3, exercise.getId());
					pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Methods returns array of exercises for which user (with id == userId) has not written any solution
	 * @param userId
	 * @return array of exercises without solutions
	 */
	static public Exercise[] loadNotResolved(int userId) {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(LOAD_NOT_RESOLVED)) {
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
