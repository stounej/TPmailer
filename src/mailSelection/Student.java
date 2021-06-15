package mailSelection;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Student {
	protected  StringProperty studentid;	

	protected  StringProperty lastname;
	protected  StringProperty firstname;
	protected  StringProperty email;
	protected  StringProperty tp;
	protected  ArrayList<Module> modules; 
	//private boolean check;
	
	public Student(String path) {}
	
	public Student() {
		this.studentid = new SimpleStringProperty ("");
		this.lastname = new SimpleStringProperty ("");
		this.firstname = new SimpleStringProperty ("");
		this.email = new SimpleStringProperty ("");
		this.tp = new SimpleStringProperty ("");
		this.modules = new ArrayList<Module>();
	}
	
	public Student(String studentid,
			String lastname, String firstname, 
			String email, String tp,
			ArrayList<Module> modules) {
		this.studentid = new SimpleStringProperty (studentid);
		this.lastname = new SimpleStringProperty (lastname);
		this.firstname = new SimpleStringProperty (firstname);
		this.email = new SimpleStringProperty (email);
		this.tp = new SimpleStringProperty (tp);
		this.modules = modules;
	}
	//getters and setters
	@XmlAttribute(name="studentid")
	public String getStudentid() {
		return studentid.get();
	}
	public StringProperty getStudentidProperty() {
		return studentid;
	}
	public void setStudentid(String studentid) {
		this.studentid = new SimpleStringProperty (studentid);
	}
	@XmlElement(name="lastname")
	public String getLastname() {
		return lastname.get();
	}
	public StringProperty getLastnameProperty() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = new SimpleStringProperty (lastname);
	}
	@XmlElement(name="firstname")
	public String getFirstname() {
		return firstname.get();
	}
	public StringProperty getFirstnameProperty() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = new SimpleStringProperty (firstname);
	}
	@XmlElement(name="email")
	public String getEmail() {
		return email.get();
	}
	public StringProperty getEmailProperty() {
		return email;
	}
	public void setEmail(String email) {
		this.email = new SimpleStringProperty (email);
	}
	@XmlElement(name="tp")
	public String getTp() {
		return tp.get();
	}
	public StringProperty getTpProperty() {
		return tp;
	}
	public void setTp(String tp) {
		this.tp = new SimpleStringProperty (tp);
	}
		
	
	
	@XmlElement(name="Module")
	public ArrayList<Module> getModules() {
		return modules;
	}
	public void setModules(ArrayList<Module> modules) {
		this.modules = modules;
	}
	
	

}
