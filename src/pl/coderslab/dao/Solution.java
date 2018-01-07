package pl.coderslab.dao;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Solution {
	private int id = 0;
	private LocalDateTime created;
	private LocalDateTime updated;
	private String description;
	private User user;
	private Exercise exercise;
	
	
	public Solution() {
		super();
		this.user = new User();
		this.exercise = new Exercise();
	}
	
	
	/**
	 * 
	 * @param created
	 * @param updated
	 * @param description
	 * @param usersId
	 * @param exerciseId
	 */
	public Solution(String created, String updated, String description, User user, Exercise exercise) {
		super();
		this.setCreated(created);
		this.setUpdated(updated);
		this.setDescription(description);
		this.user = new User();
		this.setUsers(user);
		this.exercise = new Exercise();
		this.setExercise(exercise);
	}


	public int getId() {
		return id;
	}


	protected void setId(int id) {
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


	public User getUser() {
		return this.user;
	}


	public void setUsers(User user) {
		this.user = user;
	}


	public Exercise getExercise() {
		return this.exercise;
	}


	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
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
		sb.append(this.getUser().getUsername()).append(", ");
		sb.append(this.getExercise().getTitle());
		return sb.toString();		
	}
	

	
}
