package ui.bean;

import javafx.util.StringConverter;
import api.bean.Student;
import api.context.GlobalContext;

public class UIStudentBean extends StringConverter<UIStudentBean> {

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

	@Override
	public UIStudentBean fromString(String textFull) {
		String text = textFull.split(":")[0];
		return new UIStudentBean(GlobalContext.getLocalStore().searchStudent(text));
	}

	@Override
	public String toString(UIStudentBean object) {
		return object.student.getRollno()+":"+object.student.getName();
	}
}
