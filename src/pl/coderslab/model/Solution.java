package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;



public class Solution {
	private int id = 0;
	private LocalDateTime created;
	private LocalDateTime updated;
	private String description;
	private int usersId;
	private int exerciseId;
	
	public Solution() {}
	
	
	/**
	 * 
	 * @param created
	 * @param updated
	 * @param description
	 * @param usersId
	 * @param exerciseId
	 */
	public Solution(String created, String updated, String description, int usersId, int exerciseId) {
		super();
		this.setCreated(created);
		this.setUpdated(updated);
		this.setDescription(description);
		this.setUsersId(usersId);
		this.setExerciseId(exerciseId);
	}


	public int getId() {
		return id;
	}


	private void setId(int id) {
		this.id = id;
	}


	private static LocalDateTime StringToLocalDateTime(String dateTimeStr) {
	       if (dateTimeStr == null) {
	           return null;
	       } else {
	           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.[S]");
	           return LocalDateTime.parse(dateTimeStr, formatter);
	       }
	   }
	
	
	private static String LocalDateTimeToString(LocalDateTime dateTime) {
	       if (dateTime == null) {
	           return null;
	       } else {
	           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	           return dateTime.format(formatter);
	       }
	   }
	
	
	public String getCreated() {
		return LocalDateTimeToString(created);
	}


	public void setCreated(String created) {
		this.created = StringToLocalDateTime(created);
	}


	public String getUpdated() {
		return LocalDateTimeToString(updated);
	}


	public void setUpdated(String updated) {
		this.updated = StringToLocalDateTime(updated);
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getUsersId() {
		return usersId;
	}


	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}


	public int getExerciseId() {
		return exerciseId;
	}


	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getId()).append(", ");
		sb.append(this.getCreated()).append(", ");
		if (this.updated != null) {
			sb.append(this.getUpdated()).append(", ");
		} else {
			sb.append(" , ");
		}
		sb.append(this.getDescription()).append(", ");
		sb.append(this.getUsersId()).append(", ");
		sb.append(this.getExerciseId());
		return sb.toString();		
	}
	
	/**
	 * Method returns the record form table solution with field id == param id 
	 * @param id
	 * @return
	 */
	static public Solution loadById(int id) {
		String sql = "Select * from solution where id =?;";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, id);
				try (ResultSet res = pst.executeQuery()) {
					if (res.next()) {
						Solution tmpSol = new Solution();
						tmpSol.setId(res.getInt("id"));
						tmpSol.setCreated(res.getString("created"));
						tmpSol.setUpdated(res.getString("updated"));
						tmpSol.setDescription(res.getString("description"));
						tmpSol.setUsersId(res.getInt("users_id"));
						tmpSol.setExerciseId(res.getInt("exercise_id"));
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
				ResultSet res = st.executeQuery("Select * from solution;")) {
				while (res.next()) {
					Solution tmpSol = new Solution();
					tmpSol.setId(res.getInt("id"));
					tmpSol.setCreated(res.getString("created"));
					tmpSol.setUpdated(res.getString("updated"));
					tmpSol.setDescription(res.getString("description"));
					tmpSol.setUsersId(res.getInt("users_id"));
					tmpSol.setExerciseId(res.getInt("exercise_id"));
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
		String sql = "Select * from solution Order by updated DESC LIMIT ?;";
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, numberOfSolutions);
				try (ResultSet res = pst.executeQuery()) {
					while (res.next()) {
						Solution tmpSol = new Solution();
						tmpSol.setId(res.getInt("id"));
						tmpSol.setCreated(res.getString("created"));
						tmpSol.setUpdated(res.getString("updated"));
						tmpSol.setDescription(res.getString("description"));
						tmpSol.setUsersId(res.getInt("users_id"));
						tmpSol.setExerciseId(res.getInt("exercise_id"));
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

	
	public void delete() {
		if (this.getId() != 0) {
			String sql = "Delete from solution where id = ?;";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(sql)) {
					pst.setInt(1, this.getId());
					pst.executeUpdate();
					this.setId(0);
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
	public Solution saveToDB() {
		if (this.getId() == 0) {
			String[] generatedColumns = { "id" };
			String sql = "Insert into solution (created, updated, description, users_id, exercise_id) Values (?, ?, ?, ?, ?);";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(sql, generatedColumns)) {
					pst.setString(1, this.getCreated());
					pst.setString(2, this.getUpdated());
					pst.setString(3, this.getDescription());
					pst.setInt(4, this.getUsersId());
					pst.setInt(5, this.getExerciseId());
					pst.executeUpdate();
					try (ResultSet res = pst.getGeneratedKeys()) {
						if (res.next())
							this.setId(res.getInt(1));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = "Update solution SET created = ?, updated = ?, description = ?, users_id = ?, exercise_id = ? where id = ?;";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement pst = conn.prepareStatement(sql)) {
					pst.setString(1, this.getCreated());
					pst.setString(2, this.getUpdated());
					pst.setString(3, this.getDescription());
					pst.setInt(4, this.getUsersId());
					pst.setInt(5, this.getExerciseId());
					pst.setInt(6, this.getId());
					pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	
	/**
	 * Method returns all solutions of a user 
	 * @param id
	 * @return Array of solution for given userId
	 */
	static public Solution[] loadAllByUserId(int id) {
		String sql = "Select * from solution where users_id =?;";
		ArrayList<Solution> list = new ArrayList<Solution>();
		try (Connection conn = DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, id);
				try (ResultSet res = pst.executeQuery()) {
					while (res.next()) {
						Solution tmpSol = new Solution();
						tmpSol.setId(res.getInt("id"));
						tmpSol.setCreated(res.getString("created"));
						tmpSol.setUpdated(res.getString("updated"));
						tmpSol.setDescription(res.getString("description"));
						tmpSol.setUsersId(res.getInt("users_id"));
						tmpSol.setExerciseId(res.getInt("exercise_id"));
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
		String sql = "Select * from solution where exercise_id =?;";
		ArrayList<Solution> list = new ArrayList<Solution>();
		try (Connection conn =DbUtil.getConn();
				PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, id);
				try (ResultSet res = pst.executeQuery()) {
					while (res.next()) {
						Solution tmpSol = new Solution();
						tmpSol.setId(res.getInt("id"));
						tmpSol.setCreated(res.getString("created"));
						tmpSol.setUpdated(res.getString("updated"));
						tmpSol.setDescription(res.getString("description"));
						tmpSol.setUsersId(res.getInt("users_id"));
						tmpSol.setExerciseId(res.getInt("exercise_id"));
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
