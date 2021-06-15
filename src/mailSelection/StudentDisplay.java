package mailSelection;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class StudentDisplay {
	private final ObjectProperty<Student> student = new SimpleObjectProperty<>();
    private final ObjectProperty<SerieModule> series = new SimpleObjectProperty<>();
    private final BooleanProperty select = new SimpleBooleanProperty();
    
    public StudentDisplay(Student student, SerieModule series, boolean selected) {
    	this.select.set(selected);
        this.student.set(student);
        this.series.set(series);
    }
    
    public Student getStudent() {
        return student.get();
    }

    public ObjectProperty<Student> studentProperty() {
        return student;
    }

    public void setStudent(Student student) {
        this.student.set(student);
    }

    public SerieModule getSerieModule() {
        return series.get();
    }

    public ObjectProperty<SerieModule> SerieModuleProperty() {
        return series;
    }

    public void setSerieModule(SerieModule series) {
        this.series.set(series);
    }

    public BooleanProperty selectProperty() { return select; }
    
    public void setSelectProperty(boolean select) {  this.select.set(select); }

}

