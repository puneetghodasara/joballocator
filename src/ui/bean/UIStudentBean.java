package ui.bean;

import api.bean.Student;

public class UIStudentBean {

	private Student student;
	
	public UIStudentBean(Student student) {
		this.student = student;
	}
	
	public Student getStudent() {
		return student;
	}

	@Override
	public String toString() {
		return student.toString();
	}
}
