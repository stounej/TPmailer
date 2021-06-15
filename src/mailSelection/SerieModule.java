package mailSelection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SerieModule {
	 private final ObjectProperty<Module> modules = new SimpleObjectProperty<>();
	 private final ObjectProperty<Serie> series = new SimpleObjectProperty<>();
	    
	    
	    
	    
	    public SerieModule(Module modules, Serie series) {
	        this.modules.set(modules);
	        this.series.set(series);
	    }
	    
	 

	    public Module getModule() {
	        return modules.get();
	    }

	    public ObjectProperty<Module> moduleProperty() {
	        return modules;
	    }

	    public void setModule(Module module) {
	        this.modules.set(module);
	    }

		public ObjectProperty<Serie> getSeries() {
			return series;
		}
		
		public Serie getSerie() {
			return series.get();
		}
		
		   public void setSerie(Serie serie) {
		        this.series.set(serie);
		    }

	}

