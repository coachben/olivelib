package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;


public class StudentsController {
    public JFXTextField txtLastName;
    public JFXTextField txtResidence;
    public JFXTextField txtFirstName;
    public JFXTextField txtSurname;
    public JFXTextField txtPhone;
    public JFXTextField txtStudentId;
    public JFXDatePicker dtDateOfBirth;
    public JFXDatePicker dtAdmissionDate;
    public JFXComboBox<String> cmbClass;
    public JFXComboBox<String> cmbGender;
    //TAble
    public TableView<Student> tblStudents;
    public TableColumn<Student, String> studentIdColumn;
    public TableColumn<Student, String> surnameColumn;
    public TableColumn<Student, String> firstNameColumn;
    public TableColumn<Student, String> lastNameColumn;
    public TableColumn<Student, String> admDateColumn;
    public TableColumn<Student, String> classColumn;
    public TableColumn<Student, String> birthDateColumn;


    //Establish connector
    private ConnectionClass connClass = new ConnectionClass();
    private Connection conn = connClass.connect();
    private PreparedStatement pstmt;
    private ResultSet rs;


    //populate combolist items
    public void loadDefaults() {
        cmbGender.getItems().addAll("Male", "Female");
        cmbClass.getItems().addAll("Form 1 East", " Form 2 North");
    }


    public void saveStudent() {
        String query = "INSERT INTO students(student_id,surname,firstname,lastname,admission_date," +
                "school_class,date_of_birth,residence,phone,gender) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, Integer.parseInt(txtStudentId.getText()));
            pstmt.setString(2, txtSurname.getText());
            pstmt.setString(3, txtFirstName.getText());
            pstmt.setString(4, txtLastName.getText());
            pstmt.setDate(5, Date.valueOf(dtAdmissionDate.getValue()));
            pstmt.setString(6, cmbClass.getValue());
            pstmt.setDate(7, Date.valueOf(dtDateOfBirth.getValue()));
            pstmt.setString(8, txtResidence.getText());
            pstmt.setString(9, txtPhone.getText());
            pstmt.setString(10, cmbGender.getValue());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Database Error: ");
            alert.setContentText("Error" + e.getMessage());
            alert.setHeaderText(null);
            alert.showAndWait();
            e.getMessage();
        }

    }


    public void saveStudent(ActionEvent actionEvent) {
        try {
            if (isValid() && !isDuplicate()) {
                //start by saving record upon validity test and then check duplicates
                saveStudent();
                //populate table
                populateStudentsTable();
                //clear fields
                clear();
                //notify
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Student Record Saved");
                alert.setTitle("Success!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }catch (NumberFormatException e){
            //otherwise throw a wrong format entered exception.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.setTitle("Wrong Format Entered");
            alert.setHeaderText(null);
            alert.showAndWait();
        }


    }

    public void clear(ActionEvent actionEvent) {
        clear();
    }

    private void clear() {
        loadDefaults();
        txtFirstName.setText("");
        txtLastName.setText("");
        txtSurname.setText("");
        txtPhone.setText("");
        txtResidence.setText("");
        txtStudentId.setText("");
    }


    public void populateStudentsTable(){


        //FirstColumn
        studentIdColumn = new TableColumn<>("StudentID");
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        //SecondColumn
        surnameColumn=new TableColumn<>("FirstName");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        //thirdcolumn
        firstNameColumn=new TableColumn<>("FirstName");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        //FourthColumn
        lastNameColumn=new TableColumn<>("LastName");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        //FifthColumn
        admDateColumn=new TableColumn<>("Admission Date");
        admDateColumn.setCellValueFactory(new PropertyValueFactory<>("admissionDate"));
        //6thColumn
        classColumn = new TableColumn<>("Class");
        classColumn.setCellValueFactory(new PropertyValueFactory<>("schoolClass"));
        //7thColumn
        birthDateColumn=new TableColumn<>("Birth Date");
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        //Connnection to generate data
        ConnectionClass connClass = new ConnectionClass();
        Connection conn = connClass.connect();
        ResultSet rs;

        ObservableList<Student> studentsList = FXCollections.observableArrayList();

        try {
            rs = conn.createStatement().executeQuery("select * from students");
            while(rs.next()){
                studentsList.add(new Student(rs.getString("lastname"),
                        rs.getString("residence"),
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getInt("phone"),
                        rs.getInt("student_id"),
                        rs.getString("date_of_birth"),
                        rs.getString("admission_date"),
                        rs.getString("school_class"),
                        rs.getString("gender")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tblStudents.setItems(studentsList);
        tblStudents.getColumns().addAll(studentIdColumn,surnameColumn,firstNameColumn,lastNameColumn,
                admDateColumn,classColumn,birthDateColumn);

    }

    private Boolean isValid(){
        //Return false for all empty fields
        if (txtSurname.getText().isEmpty() | txtStudentId.getText().isEmpty() | txtResidence.getText().isEmpty() |
                txtLastName.getText().isEmpty() | txtPhone.getText().isEmpty() | txtFirstName.getText().isEmpty() |
                cmbClass.getItems().isEmpty() | cmbGender.getItems().isEmpty()
        ) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter missing fields");
            alert.setTitle("Required Field Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            return false;
        }

        return true;

    }

    private Boolean isDuplicate(){

        ConnectionClass connClass = new ConnectionClass();
        Connection conn = connClass.connect();

        String duplicateQuery = "SELECT * FROM students WHERE student_id ='" + txtStudentId.getText() + "'";

        try {
                ResultSet rs;

                rs = conn.createStatement().executeQuery(duplicateQuery);

                if (rs.next()){
                    Alert status = new Alert(Alert.AlertType.WARNING);
                    status.setContentText("Student ID Already Taken");
                    status.setTitle("Duplicate Found!");
                    status.setHeaderText(null);
                    status.showAndWait();
                    return true;

                }else{

                    return false;
                }

            } catch (SQLException e) {

            Alert status = new Alert(Alert.AlertType.ERROR);
            status.setContentText(e.getMessage());
            status.setTitle("Db Record Error!");
            status.setHeaderText(null);
            status.showAndWait();
        }

        return true;
    }




}