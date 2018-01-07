package pl.coderslab.dao;



import org.mindrot.jbcrypt.BCrypt;

public class User {
	private int id = 0;
	private String username;
	private String email;
	protected String password;
	private Group group;
	
	
	//Wczytywanie z bazy
	public User() {
		super();
		this.group = new Group();
	}
	
	
	//Tworzenie nowego
	public User(String username, String email, String password, Group group) {
		super();
		setUsername(username);
		setEmail(email);
		setPassword(password);
		this.group = new Group();
		setGroup(group);
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

	
	protected User setId(int id) {
		this.id = id;
		return this;
	}
	
	
	public Group getGroup() {
		return this.group;
	}

	
	public void setGroup(Group group) {
		this.group = group;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getId()).append(", ");
		sb.append(this.getUsername()).append(", ");
		sb.append(this.getEmail()).append(", ");
		sb.append(this.getPassword()).append(", ");
		sb.append(this.getGroup().getName());
		return sb.toString();
	}
	

		
}


