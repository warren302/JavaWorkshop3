package pl.coderslab.dao;



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


	protected Exercise setId(int id) {
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
	
}
