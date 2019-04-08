package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DashboardController {


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

    ConnectionClass connClass = new ConnectionClass();
    Connection conn = connClass.connect();
    PreparedStatement pstmt;
    ResultSet rs;


    public void clear(ActionEvent actionEvent) {
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
        dBSaveRecords();
        lblNotify.setText("Record Saved Successfully");
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
