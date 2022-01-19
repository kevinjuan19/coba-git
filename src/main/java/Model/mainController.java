package Model;

import Dao.labDao;
import Dao.maintenanceDao;
import Dao.userDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class mainController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtUser;

    @FXML
    private ComboBox<Laboratorium> cbLab;

    @FXML
    private TextArea txtTask;

    @FXML
    private DatePicker txtDate;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<Maintenance> table1;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colUser;

    @FXML
    private TableColumn<?, ?> colLab;

    @FXML
    private TableColumn<?, ?> colTask;

    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private MenuItem userManagement;
    @FXML
    private BorderPane utama;


    private maintenanceDao mDao = new maintenanceDao();
    private ObservableList<Maintenance> mList;
    private userDao uDao = new userDao();
    private ObservableList<User> uList = uDao.showData();
    private User newUser;
    private Maintenance maintenance= new Maintenance();
    private labDao lDao = new labDao();
    private  ObservableList<Laboratorium> lList = lDao.showData();

    public ObservableList<Laboratorium> getlList() {
        return lList;
    }

    public ObservableList<User> getuList() {
        return uList;
    }

    private loginController loginController;

    public void setLoginController(Model.loginController loginController) {
        this.loginController = loginController;
        newUser = loginController.validasi();
        maintenance.setUser(newUser);
        mList = mDao.fetchData(maintenance);
        table1.setItems(mList);
        txtUser.setText(maintenance.getUser().getName());
        if(newUser.getPosition().equals("staff")){
            userManagement.setDisable(true);
        }
        System.out.println(loginController.validasi().getId());
    }

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        colLab.setCellValueFactory(new PropertyValueFactory<>("laboratorium"));
        colTask.setCellValueFactory(new PropertyValueFactory<>("task"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        cbLab.setItems(lList);
        cbLab.getSelectionModel().select(0);
    }

    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void simpan(ActionEvent event) {
        Maintenance maintenance = new Maintenance();
        maintenance.setDate(Date.valueOf(txtDate.getValue()));
        maintenance.setUser(newUser);
        maintenance.setLaboratorium(cbLab.getValue());
        maintenance.setTask(txtTask.getText());
        mDao.addData(maintenance);
        mList.clear();
        mList.addAll(mDao.fetchData(maintenance));
    }

    @FXML
    void toLabManagement(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("lab-management-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        labController c2 = fxmlLoader.getController();
        c2.setMainController(this);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(utama.getScene().getWindow());
        stage.setTitle("Category Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void toUserManagement(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("user-management-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        userController c2 = fxmlLoader.getController();
        c2.setMainController(this);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(utama.getScene().getWindow());
        stage.setTitle("Category Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void update(ActionEvent event) {

    }

}
