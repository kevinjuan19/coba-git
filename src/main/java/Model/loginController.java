package Model;

import Dao.userDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    @FXML
    private GridPane root;


    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button LoginId;

    private userDao uDao = new userDao();
    private User newUser = new User();



    @FXML
    void   btnLogin() throws IOException {
        if(validasi() != null){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            mainController c2 = fxmlLoader.getController();
            c2.setLoginController(this);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(root.getScene().getWindow());
            root.getScene().getWindow().hide();
            stage.setTitle("Category Management");
            stage.setScene(scene);
            stage.show();
        }else{
            clearAll();
            showAlert("username atau password salah!");
        }
    }
    public void clearAll(){
        txtPassword.setText("");
        txtUsername.setText("");
    }
    public User validasi() {
        String  username = txtUsername.getText();
        String password = txtPassword.getText();
        getNewUser().setUsername(username);
        getNewUser().setPassword(password);
        User user;
        user = uDao.tampilData(getNewUser());
        return user;
    }


    public User getNewUser() {
        return newUser;
    }


    public void showAlert(String kalimat){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.show();
        alert.setContentText(kalimat);
    }
}