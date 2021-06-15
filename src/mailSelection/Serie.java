package mailSelection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlRootElement(name = "Serie")
@XmlAccessorType (XmlAccessType.PROPERTY)
public class Serie {
	protected  StringProperty studentid;
	protected  IntegerProperty moduleid;
	protected  StringProperty title;
	protected  StringProperty comment;
	protected  StringProperty completed;
	protected  StringProperty alreadySent;
	protected  StringProperty path;
	
	public Serie() {}
	
	public Serie(String studentid,int moduleid , String title, String comment, String completed,
			String alreadySent, String path) {
		this.studentid = new SimpleStringProperty(studentid);
		this.moduleid = new SimpleIntegerProperty(moduleid);
		this.title = new SimpleStringProperty (title);
		this.comment = new SimpleStringProperty (comment);
		this.completed = new SimpleStringProperty (completed);
		this.alreadySent = new SimpleStringProperty (alreadySent);
		this.path = new SimpleStringProperty (path);
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
	
	@XmlElement(name="title")
	public String getTitle() {
		return title.get();
	}
	public void setTitle(String title) {
		this.title = new SimpleStringProperty (title);
	}
	public StringProperty getTitleProperty() {
		return title;
	}
	
	
	@XmlElement(name="comment")
	public String getComment() {
		return comment.get();
	}
	public void setComment(String comment) {
		this.comment = new SimpleStringProperty (comment);
	}
	public StringProperty getCommentProperty() {
		return comment;
	}
	
	@XmlElement(name="completed")
	public String getCompleted() {
		return completed.get();
	}
	public void setCompleted(String completed) {
		this.completed = new SimpleStringProperty (completed);
	}
	public StringProperty getCompletedProperty() {
		return completed;
	}
	
	@XmlElement(name="sent")
	public String getAlreadySent() {
		return alreadySent.get();
	}
	public void setAlreadySent(String alreadySent) {
		this.alreadySent = new SimpleStringProperty (alreadySent);
	}
	public StringProperty getAlreadySentProperty() {
		return alreadySent;
	}
	
	@XmlElement(name="path")
	public String getPath() {
		return path.get();
	}
	public void setPath(String path) {
		this.path = new SimpleStringProperty (path);
	}
	public StringProperty getPathProperty() {
		return path;
	}

	public int getModuleid() {
		return moduleid.get();
	}
	public IntegerProperty getModuleidProperty() {
		return moduleid;
	}

	public void setModuleid(IntegerProperty moduleid) {
		this.moduleid = moduleid;
	}
	

}
