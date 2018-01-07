package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

public class SolutionDao {
	static String LOAD_ONE = "Select * from solution where id =?;";
	static String LOAD_ALL = "Select * from solution;";
	static String LOAD_SELECTED = "Select * from solution Order by updated DESC LIMIT ?;";
	static String DELETE_ONE = "Delete from solution where id = ?;";
	static String INSERT_ONE = "Insert into solution (created, updated, description, users_id, exercise_id) Values (?, ?, ?, ?, ?);";
	static String UPDATE_ONE = "Update solution SET created = ?, updated = ?, description = ?, users_id = ?, exercise_id = ? where id = ?;";
	static String LOAD_BY_USERSID = "Select * from solution where users_id =?;";
	static String LOAD_BY_EXERCISEID = "Select * from solution where exercise_id =?;";

	
	/**
	 * Method returns the record form table solution with field id == param id 
	 * @param id
	 * @return
	 */
	static public Solution loadById(int id) {
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(LOAD_ONE)) {
				pst.setInt(1, id);
				try (ResultSet res = pst.executeQuery()) {
					if (res.next()) {
						Solution tmpSol = new Solution();
						tmpSol.setId(res.getInt("id"));
						tmpSol.setCreated(res.getString("created"));
						tmpSol.setUpdated(res.getString("updated"));
						tmpSol.setDescription(res.getString("description"));
						tmpSol.setUsers(UserDao.loadById(res.getInt("users_id")));
						tmpSol.setExercise(ExerciseDao.loadById(res.getInt("exercise_id")));
						return tmpSol;
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	static public Solution[] loadAll() {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		try (Connection conn = DbUtil.getConn();
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery(LOAD_ALL)) {
				while (res.next()) {
					Solution tmpSol = new Solution();
					tmpSol.setId(res.getInt("id"));
					tmpSol.setCreated(res.getString("created"));
					tmpSol.setUpdated(res.getString("updated"));
					tmpSol.setDescription(res.getString("description"));
					tmpSol.setUsers(UserDao.loadById(res.getInt("users_id")));
					tmpSol.setExercise(ExerciseDao.loadById(res.getInt("exercise_id")));
					solutions.add(tmpSol);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Solution[] solArr = new Solution[solutions.size()];
		solutions.toArray(solArr);
		return solArr;
	}


	static public Solution[] loadAll(int numberOfSolutions) {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(LOAD_SELECTED)) {
				pst.setInt(1, numberOfSolutions);
				try (ResultSet res = pst.executeQuery()) {
					while (res.next()) {
						Solution tmpSol = new Solution();
						tmpSol.setId(res.getInt("id"));
						tmpSol.setCreated(res.getString("created"));
						tmpSol.setUpdated(res.getString("updated"));
						tmpSol.setDescription(res.getString("description"));
						tmpSol.setUsers(UserDao.loadById(res.getInt("users_id")));
						tmpSol.setExercise(ExerciseDao.loadById(res.getInt("exercise_id")));
						solutions.add(tmpSol);
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Solution[] solArr = new Solution[solutions.size()];
		solutions.toArray(solArr);
		return solArr;
	}

	
	static public void delete(Solution solution) {
		if (solution.getId() != 0) {
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(DELETE_ONE)) {
					pst.setInt(1, solution.getId());
					pst.executeUpdate();
					solution.setId(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Method writes one object as a record to the table (solution)
	 * if it is a new one or overwrites data if such exists  
	 * @return Solution
	 */
	static public void saveToDB(Solution solution) {
		if (solution.getId() == 0) {
			String[] generatedColumns = { "id" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(INSERT_ONE, generatedColumns)) {
					pst.setString(1, solution.getCreated());
					pst.setString(2, solution.getUpdated());
					pst.setString(3, solution.getDescription());
					pst.setInt(4, solution.getUser().getId());
					pst.setInt(5, solution.getExercise().getId());
					pst.executeUpdate();
					try (ResultSet res = pst.getGeneratedKeys()) {
						if (res.next())
							solution.setId(res.getInt(1));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(UPDATE_ONE)) {
					pst.setString(1, solution.getCreated());
					pst.setString(2, solution.getUpdated());
					pst.setString(3, solution.getDescription());
					pst.setInt(4, solution.getUser().getId());
					pst.setInt(5, solution.getExercise().getId());
					pst.setInt(6, solution.getId());
					pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Method returns all solutions of a user 
	 * @param id
	 * @return Array of solution for given userId
	 */
	static public Solution[] loadAllByUserId(int id) {
		ArrayList<Solution> list = new ArrayList<Solution>();
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(LOAD_BY_USERSID)) {
				pst.setInt(1, id);
				try (ResultSet res = pst.executeQuery()) {
					while (res.next()) {
						Solution tmpSol = new Solution();
						tmpSol.setId(res.getInt("id"));
						tmpSol.setCreated(res.getString("created"));
						tmpSol.setUpdated(res.getString("updated"));
						tmpSol.setDescription(res.getString("description"));
						tmpSol.setUsers(UserDao.loadById(res.getInt("users_id")));
						tmpSol.setExercise(ExerciseDao.loadById(res.getInt("exercise_id")));
						list.add(tmpSol);	
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Solution[] tmpArr = new Solution[list.size()];
		list.toArray(tmpArr);
		return tmpArr;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	static public Solution[] loadAllByExcerciseId(int id) {
		ArrayList<Solution> list = new ArrayList<Solution>();
		try (Connection conn =DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(LOAD_BY_EXERCISEID)) {
				pst.setInt(1, id);
				try (ResultSet res = pst.executeQuery()) {
					while (res.next()) {
						Solution tmpSol = new Solution();
						tmpSol.setId(res.getInt("id"));
						tmpSol.setCreated(res.getString("created"));
						tmpSol.setUpdated(res.getString("updated"));
						tmpSol.setDescription(res.getString("description"));
						tmpSol.setUsers(UserDao.loadById(res.getInt("users_id")));
						tmpSol.setExercise(ExerciseDao.loadById(res.getInt("exercise_id")));
						list.add(tmpSol);	
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		list.sort(new Comparator<Solution>() {
		    @Override
		    public int compare(Solution o1, Solution o2) {
		        return o2.getCreated().compareTo(o1.getCreated());
		    }
		});
		Solution[] tmpArr = new Solution[list.size()];
		list.toArray(tmpArr);
		return tmpArr;
	}
	
	
}
