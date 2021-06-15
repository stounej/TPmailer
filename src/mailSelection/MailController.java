/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailSelection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;


public class MailController implements Initializable {
    
	@FXML
	private Label TPlabel,Serielabel;
	
	@FXML 
	private CheckBox selectAll;

     @FXML
    private TableView<StudentDisplay> tableview;

    @FXML
    private TableColumn<StudentDisplay, String> email;


    @FXML
    private TableColumn<StudentDisplay, String> sent;
    
    @FXML
    private TableColumn<StudentDisplay, String> module;
    
    @FXML
    private TableColumn<StudentDisplay, String> serie;

    @FXML
    TableColumn< StudentDisplay, Boolean > select = new TableColumn<>( "Select" );
    
    @FXML
    private TableColumn<StudentDisplay, String> comment;
    
    @FXML 
    private ComboBox<String> moduleCombo = new ComboBox<>();
    
    @FXML 
    private ComboBox<String> comboTP = new ComboBox<>();
    
    
    @FXML 
    private ComboBox<String> comboSerie = new ComboBox<>();
     
    @FXML ObservableList<Module> listmod = FXCollections.observableArrayList();
    
    @FXML ObservableList<Student> list  = FXCollections.observableArrayList();
    
    @FXML ObservableList<Serie> listserie  = FXCollections.observableArrayList();
    
    @FXML TextField search = new TextField();
    
    
    // This is our list of DisplayStudents that combines all our data into one model for the TableView
    @FXML ObservableList<SerieModule> SerieMod = FXCollections.observableArrayList();
    @FXML ObservableList<StudentDisplay> displayStudents = FXCollections.observableArrayList();


	public String emails;
	public String mdp;
	JAXBContext jaxbcontext;
	Students students;
	private static File file = new File("filo.xml");



    @FXML private void searchName() {
    	SerieMod.clear();
    	displayStudents.clear();
    	tableview.getItems().clear();
    	String se = search.getText().toLowerCase();
    	System.out.println(se);
       	for (Serie serie :
     		listserie) {
         // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
         // Java 8 Streams API to do this
     		listmod.stream().filter(c -> c.getModuleid() == serie.getModuleid() )
             .findFirst()
             // Add the new DisplayStudent to the list
             .ifPresent(s -> {
             	//System.out.println(module.getName());
                 SerieMod.add(new SerieModule(
                         s,
                         serie
                 ));});
                 // Check if the students list contains a Student with this ID
                 
     }
       	
       	for (SerieModule serie :
     		SerieMod) {
         // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
         // Java 8 Streams API to do this
         list.stream()
                 // Check if the students list contains a Student with this ID
                 .filter(p -> p.getStudentid() == serie.getSerie().getStudentid() && p.getLastname().toLowerCase().equals(se))
                 .findFirst()
                 // Add the new DisplayStudent to the list
                 .ifPresent(s -> {
                 	displayStudents.add(new StudentDisplay(
                             s,
                             serie,
                             false
                     ));});
         
         
        
         
        
     }
       	
       	email.setCellValueFactory(f -> f.getValue().getStudent().getEmailProperty());
        comment.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getCommentProperty());
        sent.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getAlreadySentProperty());
        module.setCellValueFactory(f -> f.getValue().getSerieModule().getModule().getNameProperty());
        serie.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getTitleProperty());

        tableview.setItems(displayStudents);
     	 
     	
    	
    }
    
    @FXML private void selectAll() {
    	
    	for(StudentDisplay student :displayStudents) {
    		if(selectAll.isSelected()) {
    		student.setSelectProperty(true);
    	}
    	
    	else{
    		student.setSelectProperty(false);
    	}
    	}}
    @FXML private void sendEmails() {
        //selection.add(select);
            final Set<StudentDisplay> del = new HashSet<>();
            if (tableview.getItems()!=null) {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error Dialog");
        		alert.setContentText("Ooops, you should enter a valid parameters");

        		alert.showAndWait();
            }
            for( final StudentDisplay os : tableview.getItems()) {
               if( os.selectProperty().get()) {
                  del.add( os );

				   if (emails == null && mdp == null ) {
					   EmailLogin();
				   }

				   MailSender.send(
//                            "projet.appli.info.2020@gmail.com",
//                            "Xb.PzS5;-G!jn,Q538k2",
						   emails,
						   mdp,
						   os.getStudent().getEmail(),
						   os.getSerieModule().getModule().getName(),
//						   coreEmail.getText() + "<br> ----------------- <br>" + new String(Files.readAllBytes(Paths.get(files[j].getAbsolutePath())))
						   os.getSerieModule().getSerie().getComment()
				   );

				   System.out.println(emails +" "+" "+mdp+" "+os.getSerieModule().getSerie().getComment());
                  System.out.println(os.getStudent().getEmail() +" "+ os.getSerieModule().getModule().getName() + " "+ os.getSerieModule().getSerie().getTitle());
                  os.getSerieModule().getSerie().setAlreadySent("yes");
                  Marshaller m;
      			try {
      				 jaxbcontext = JAXBContext.newInstance(Students.class);
      				 Unmarshaller jaxbUnmarshaller = jaxbcontext.createUnmarshaller();
      				 students = (Students) jaxbUnmarshaller.unmarshal(file); 
      				 for(int i=0; i<students.getStudents().size(); i++) {
      					 if(students.getStudents().get(i).getStudentid().equals(os.getStudent().getStudentid())) {
      						 for (int j =0; j<students.getStudents().get(i).getModules().size(); j++) {
      							 if (students.getStudents().get(i).getModules().get(j).getName().equals(os.getSerieModule().getModule().getName()) ) {
      								 for(int k=0; k<students.getStudents().get(i).getModules().get(j).getSeries().size();k++) {
      									 if (students.getStudents().get(i).getModules().get(j).getSeries().get(k).getPath().equals(os.getSerieModule().getSerie().getPath())) {
      										 students.getStudents().get(i).getModules().get(j).getSeries().get(k).setAlreadySent("yes");
      									 }
      								 }
      							 }
      						 }
      					 }
      					
      				 }
      				 
      				m = jaxbcontext.createMarshaller();
      				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      				m.marshal(students,  file);
      				
      				 } catch (JAXBException e1) {
      				// TODO Auto-generated catch block
      				e1.printStackTrace();
      			}



               }
            }
          //  view.getItems().removeAll( del );

    }
    @FXML private void ModuleView() {
    	SerieMod.clear();
    	displayStudents.clear();
    	tableview.getItems().clear();
    	String m = moduleCombo.getValue();
    	 

     	for (Serie serie :
     		listserie) {
         // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
         // Java 8 Streams API to do this
     		listmod.stream().filter(c -> c.getModuleid() == serie.getModuleid() && c.getName() == m )
             .findFirst()
             // Add the new DisplayStudent to the list
             .ifPresent(s -> {
             	//System.out.println(module.getName());
                 SerieMod.add(new SerieModule(
                         s,
                         serie
                 ));});
                 // Check if the students list contains a Student with this ID
                 
     }
     	
     	for (SerieModule serie :
     		SerieMod) {
         // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
         // Java 8 Streams API to do this
         list.stream()
                 // Check if the students list contains a Student with this ID
                 .filter(p -> p.getStudentid() == serie.getSerie().getStudentid())
                 .findFirst()
                 // Add the new DisplayStudent to the list
                 .ifPresent(s -> {
                 	displayStudents.add(new StudentDisplay(
                             s,
                             serie,
                             false
                     ));});
         
         
        
         
        
     }
     	
     	 
     	ObservableList<String> listtp  = FXCollections.observableArrayList();
     	listtp.add("ALL");
        for(StudentDisplay s : displayStudents) {
       	
       		 if(!listtp.contains(s.getStudent().getTp()) ) {
       		 System.out.println(s.getStudent().getTp());
       		 listtp.add(s.getStudent().getTp());}
       }
     	
     	email.setCellValueFactory(f -> f.getValue().getStudent().getEmailProperty());
        comment.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getCommentProperty());
        sent.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getAlreadySentProperty());
        module.setCellValueFactory(f -> f.getValue().getSerieModule().getModule().getNameProperty());
        serie.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getTitleProperty());

        comboTP.setItems(listtp);
        TPlabel.setVisible(true);
        comboTP.setVisible(true);
        tableview.setItems(displayStudents);
     	 
     	
    }

    @FXML private void TPView() {
    	SerieMod.clear();
    	displayStudents.clear();
    	tableview.getItems().clear();
    	String m = moduleCombo.getValue();
    	String tp = comboTP.getValue();
    	 

     	for (Serie serie :
     		listserie) {
         // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
         // Java 8 Streams API to do this
     		listmod.stream().filter(c -> c.getModuleid() == serie.getModuleid() && c.getName() == m )
             .findFirst()
             // Add the new DisplayStudent to the list
             .ifPresent(s -> {
             	//System.out.println(module.getName());
                 SerieMod.add(new SerieModule(
                         s,
                         serie
                 ));});
                 // Check if the students list contains a Student with this ID
                 
     }
     	if(!tp.equals("ALL")) {
     	for (SerieModule serie :
     		SerieMod) {
         // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
         // Java 8 Streams API to do this
         list.stream()
                 // Check if the students list contains a Student with this ID
                 .filter(p -> p.getStudentid() == serie.getSerie().getStudentid() && p.getTp() == tp)
                 .findFirst()
                 // Add the new DisplayStudent to the list
                 .ifPresent(s -> {
                 	displayStudents.add(new StudentDisplay(
                             s,
                             serie,
                             false
                     ));});
         
         
        }}
     	if(tp.equals("ALL")) {
         	for (SerieModule serie :
         		SerieMod) {
             // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
             // Java 8 Streams API to do this
             list.stream()
                     // Check if the students list contains a Student with this ID
                     .filter(p -> p.getStudentid() == serie.getSerie().getStudentid() )
                     .findFirst()
                     // Add the new DisplayStudent to the list
                     .ifPresent(s -> {
                     	displayStudents.add(new StudentDisplay(
                                 s,
                                 serie,
                                 false
                         ));});
             
             
            }}
         
         
 
     	ObservableList<String> listse  = FXCollections.observableArrayList();
   	 
        for(SerieModule serie1: SerieMod) {
       	//	 System.out.println(s.getTp());
        	if(!listse.contains(serie1.getSerie().getTitle())) {
       	 listse.add(serie1.getSerie().getTitle());}
        }
     	
     	email.setCellValueFactory(f -> f.getValue().getStudent().getEmailProperty());
        comment.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getCommentProperty());
        sent.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getAlreadySentProperty());
        module.setCellValueFactory(f -> f.getValue().getSerieModule().getModule().getNameProperty());
        serie.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getTitleProperty());

        comboSerie.setItems(listse);
        Serielabel.setVisible(true);
        comboSerie.setVisible(true);
        tableview.setItems(displayStudents);
     	 
     	
    }
    
    @FXML private void SerieView() {
    	SerieMod.clear();
    	displayStudents.clear();
    	tableview.getItems().clear();
    	String m = moduleCombo.getValue();
    	String tp = comboTP.getValue();
    	String serieSelected = comboSerie.getValue();
    	 
    	System.out.println(serieSelected);
     	for (Serie serie :
     		listserie) {
         // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
         // Java 8 Streams API to do this
     		listmod.stream().filter(c -> c.getModuleid() == serie.getModuleid() && c.getName() == m && serie.getTitle().equals(serieSelected)  )
             .findFirst()
             // Add the new DisplayStudent to the list
             .ifPresent(s -> {
             	//System.out.println(module.getName());
                 SerieMod.add(new SerieModule(
                         s,
                         serie
                 ));});
                 // Check if the students list contains a Student with this ID
                 
     }
     	
     	if(!tp.equals("ALL")) {
         	
         	for (SerieModule serie :
         		SerieMod) {
             // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
             // Java 8 Streams API to do this
             list.stream()
                     // Check if the students list contains a Student with this ID
                     .filter(p -> p.getStudentid() == serie.getSerie().getStudentid() && p.getTp() == tp)
                     .findFirst()
                     // Add the new DisplayStudent to the list
                     .ifPresent(s -> {
                     	displayStudents.add(new StudentDisplay(
                                 s,
                                 serie,
                                 false
                         ));});
             
             
            }}
     	
     	if(tp.equals("ALL")) {
     	
     	for (SerieModule serie :
     		SerieMod) {
         // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
         // Java 8 Streams API to do this
         list.stream()
                 // Check if the students list contains a Student with this ID
                 .filter(p -> p.getStudentid() == serie.getSerie().getStudentid() )
                 .findFirst()
                 // Add the new DisplayStudent to the list
                 .ifPresent(s -> {
                 	displayStudents.add(new StudentDisplay(
                             s,
                             serie,
                             false
                     ));});
         
         
        }}
         
         
     	
     	email.setCellValueFactory(f -> f.getValue().getStudent().getEmailProperty());
        comment.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getCommentProperty());
        sent.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getAlreadySentProperty());
        module.setCellValueFactory(f -> f.getValue().getSerieModule().getModule().getNameProperty());
        serie.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getTitleProperty());

        tableview.setItems(displayStudents);
     	 
     	
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	File file = new File("filo.xml");
		 List<Module> mds = new ArrayList<Module>() ;
		 List<Student> stds = new ArrayList<Student>() ;
		 List<Serie> sris = new ArrayList<Serie>() ;
		try {
			JAXBContext jaxbcontext = JAXBContext.newInstance(Students.class);
			Unmarshaller jaxbUnmarshaller = jaxbcontext.createUnmarshaller();
			Students students = (Students) jaxbUnmarshaller.unmarshal(file);

			for(int i=0;i<students.getStudents().size();i++) {
				 stds.add(new Student(students.getStudents().get(i).getStudentid(),
		        		  students.getStudents().get(i).getLastname(),
		        		  students.getStudents().get(i).getFirstname(),
		        		  students.getStudents().get(i).getEmail(),
		        		  students.getStudents().get(i).getTp(),
		        		  students.getStudents().get(i).getModules()));
				          
				    
				 for (int j=0; j<students.getStudents().get(i).getModules().size();j++) {
					 mds.add(new Module(students.getStudents().get(i).getStudentid(),
							 	students.getStudents().get(i).getModules().get(j).getModuleid(),
					    		students.getStudents().get(i).getModules().get(j).getName(),
					    		students.getStudents().get(i).getModules().get(j).getSeries()
					    		));	   
					 
					 for(int k = 0; k<students.getStudents().get(i).getModules().get(j).getSeries().size();k++){
						 sris.add(new Serie (students.getStudents().get(i).getStudentid(),
								 	students.getStudents().get(i).getModules().get(j).getModuleid(),
								 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getTitle(),
								 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getComment(),
								 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getCompleted(),
								 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getAlreadySent(),
								 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getPath()
								 	));
					 }
							    
				 }
				
			}
			 list = FXCollections.observableArrayList(stds);
			 listmod = FXCollections.observableArrayList(mds);
			 listserie = FXCollections.observableArrayList(sris);
			

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		
		ObservableList<String> listmod1 = FXCollections.observableArrayList();
		for(Module m: listmod) {
				if(!listmod1.contains(m.getName())){
					listmod1.add(m.getName());
				}
				
				}		
		moduleCombo.setItems(listmod1);

    	for (Serie serie :
    		listserie) {
        // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
        // Java 8 Streams API to do this
    		listmod.stream().filter(m -> m.getModuleid() == serie.getModuleid())
            .findFirst()
            // Add the new DisplayStudent to the list
            .ifPresent(s -> {
            	//System.out.println(module.getName());
                SerieMod.add(new SerieModule(
                        s,
                        serie
                ));});
                // Check if the students list contains a Student with this ID
                
    }
    	

    	for (SerieModule serie :
    		SerieMod) {
        // For each Times, we want to retrieve the corresponding Student from the students list. We'll use the
        // Java 8 Streams API to do this
        list.stream()
                // Check if the students list contains a Student with this ID
                .filter(p -> p.getStudentid() == serie.getSerie().getStudentid())
                .findFirst()
                // Add the new DisplayStudent to the list
                .ifPresent(s -> {
                	displayStudents.add(new StudentDisplay(
                            s,
                            serie,
                            false
                    ));});
    }
    	 
    	
		
    	email.setCellValueFactory(f -> f.getValue().getStudent().getEmailProperty());
        comment.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getCommentProperty());
        sent.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getAlreadySentProperty());
        module.setCellValueFactory(f -> f.getValue().getSerieModule().getModule().getNameProperty());
        serie.setCellValueFactory(f -> f.getValue().getSerieModule().getSerie().getTitleProperty());
        select.setCellValueFactory( new PropertyValueFactory<>( "select" ));
        select.setCellFactory( f -> new CheckBoxTableCell<>());
        


        tableview.setItems(displayStudents);
        
        
        
        
       
    }

	public void EmailLogin() {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Look, a Custom Login Dialog");

		ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);


		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);


		Platform.runLater(() -> username.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();


		result.ifPresent(usernamePassword -> {
			emails = usernamePassword.getKey();
			mdp = usernamePassword.getValue();
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});
	}
    
}
