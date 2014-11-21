package ui.bean;

import api.bean.Student;

public class UIStudentBean {

	String id;
	String name;
	
	
	public UIStudentBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public UIStudentBean(Student stud) {
		this.id = stud.getRollno();
		this.name = stud.getName();
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	
}
