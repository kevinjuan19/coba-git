package Model;

import Dao.userDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class userController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUsername;

    @FXML
    private ComboBox<String> cbPosition;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private TableColumn<?, ?> colPosition;
    private mainController MainController;
    private userDao uDao;

    public void setMainController(mainController mainController) {
        MainController = mainController;
        tableUser.setItems(mainController.getuList());
    }

    public void initialize() {
        uDao = new userDao();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        ObservableList<String> cbList = FXCollections.observableArrayList();
        cbList.add("Koordinator");
        cbList.add("staff");
        cbPosition.setItems(cbList);
        cbPosition.getSelectionModel().select(0);
    }

    @FXML
    void add(ActionEvent event) {
        if (txtId.getText().isEmpty()){
            showAlert("Please fill in Id Field");
        }else if(txtName.getText().isEmpty()){
            showAlert("Please fill in Name Field");
        }else if(txtUsername.getText().isEmpty()){
            showAlert("Please fill in Username Field");
        } else if(txtPassword.getText().isEmpty()){
            showAlert("Please fill in Password Field");
        } else if(cbPosition.getSelectionModel().isEmpty()){
            showAlert("Please fill in Position Field");
        } else{
            try {
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                String position = cbPosition.getValue();
                boolean ada = false;
                for (int i = 0; i < MainController.getuList().size(); i++){
                    if(name.equals( MainController.getuList().get(i).getName())) {
                        ada = true;
                        break;
                    }
                }
                if (ada){
                    showAlert("Data Already Exist!");
                }else{
                    User user = new User(id,name,username,password,position);
                    uDao.addData(user);
                    MainController.getuList().clear();
                    MainController.getuList().addAll(uDao.showData());
                    tableUser.refresh();
                }
                clearAll();
            }
            catch (NumberFormatException ex){
                showAlert("Please Input ID field with Number!");
            }

        }
    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) {

    }

    public void showAlert(String kalimat){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.show();
        alert.setContentText(kalimat);
    }
    public void clearAll(){
        txtId.clear();
        txtName.clear();
        txtPassword.clear();
        cbPosition.setValue(null);
    }

}
