package pl.coderslab.dao;



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

	
	protected Group setId(int id) {
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
	
	

	
}
