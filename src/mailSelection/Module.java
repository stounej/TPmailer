package mailSelection;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlRootElement(name = "Module")
@XmlAccessorType (XmlAccessType.PROPERTY)
public class Module {
	protected  StringProperty studentid;
	protected  IntegerProperty moduleid;
	protected  StringProperty name;
	protected  ArrayList<Serie> series;
	
	public Module() {}
	public Module(String studentid, int moduleid ,String name, ArrayList<Serie> series) {
		this.studentid = new SimpleStringProperty(studentid);
		this.moduleid = new SimpleIntegerProperty(moduleid);
		this.name = new SimpleStringProperty(name);
		this.setSeries(series);
	}
	
	
	
	public StringProperty getStudentidProperty() {
		return studentid;
	}
	public String getStudentid() {
		return studentid.get();
	}
	public void setStudentid(StringProperty studentid) {
		this.studentid = studentid;
	}
	
	
	@XmlElement(name="name")
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty (name);
	}
	public StringProperty getNameProperty() {
		return this.name;
	}
	
	@XmlElement(name="Serie")
	public ArrayList<Serie> getSeries() {
		return series;
	}
	public void setSeries(ArrayList<Serie> series) {
		this.series = series;
	}
	
	@XmlElement(name="moduleid")
	public int getModuleid() {
		return moduleid.get();
	}
	@Override
	public String toString() {
		return this.name.get();
	}
	public IntegerProperty getModuleidProperty() {
		return moduleid;
	}
	public void setModuleid(int moduleid) {
		this.moduleid =  new SimpleIntegerProperty (moduleid);
	}



}
