package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DashboardController{


    @FXML
    public TextField txtEdition;
    @FXML
    public TextField txtBookName;
    @FXML
    public TextField txtPublisher;
    @FXML
    public Label lblNotify;
    @FXML
    public TextField txtIsbn;
    @FXML
    public TextField txtCategory;
    @FXML
    public TextField txtBookId;
    @FXML
    public TextField txtQty;
    @FXML
    public TextField txtAuthor;
    @FXML
    public TextField txtShelfName;
    @FXML
    public JFXButton btnAdd;
    @FXML
    public JFXButton btnClear;

    ConnectionClass connClass = new ConnectionClass();
    Connection conn = connClass.connect();
    PreparedStatement pstmt;
    ResultSet rs;




    public void clear(ActionEvent actionEvent) {
        clear();
    }

    private void clear() {
        txtBookName.setText("");
        txtIsbn.setText("");
        txtEdition.setText("");
        txtPublisher.setText("");
        lblNotify.setText("");
        txtCategory.setText("");
        txtBookId.setText("");
        txtQty.setText("");
        txtAuthor.setText("");
        txtShelfName.setText("");
    }

    public void save(ActionEvent actionEvent) {
        try {
            //Test for input validity first
            if(isValid()){
                dBSaveRecords();
                lblNotify.setText("Record Saved Successfully");

                Alert status = new Alert(Alert.AlertType.INFORMATION);
                status.setContentText("Record Saved Successfully");
                status.setTitle("Success!");
                status.setHeaderText(null);
                status.showAndWait();
                //clear buttons
                clear();
            }
        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.setTitle("Wrong Format Entered");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

    }

    private Boolean isValid(){
        //Return false for all empty fields
            if (txtBookName.getText().isEmpty() | txtShelfName.getText().isEmpty() | txtQty.getText().isEmpty() |
                    txtPublisher.getText().isEmpty() | txtIsbn.getText().isEmpty() | txtEdition.getText().isEmpty() |
                    txtCategory.getText().isEmpty() | txtBookId.getText().isEmpty() | txtAuthor.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please enter missing fields");
                alert.setTitle("Required Field Error");
                alert.setHeaderText(null);
                alert.showAndWait();
                return false;
            }

        return true;

    }



    public void dBSaveRecords(){

        String sqlQuery =
                 "INSERT INTO books(author,book_id,category,date_time,edition,isbn,publisher,quantity,shelf_name, title)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sqlQuery);
            }catch(SQLException e){
                e.printStackTrace();
            }

        try {
            //pre-compiled sql query
            pstmt.setString(1,txtAuthor.getText().trim());
            pstmt.setInt(2,(Integer.parseInt(txtBookId.getText().trim())));
            pstmt.setString(3,txtCategory.getText().trim());
            pstmt.setString(4, currentTime());
            pstmt.setString(5,txtEdition.getText().trim());
            pstmt.setString(6,txtIsbn.getText().trim());
            pstmt.setString(7,txtPublisher.getText().trim());
            pstmt.setInt(8,(Integer.parseInt(txtQty.getText().trim())));
            pstmt.setString(9,txtShelfName.getText().trim());
            pstmt.setString(10,txtBookName.getText().trim());

            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String currentTime(){
        //Get current time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
