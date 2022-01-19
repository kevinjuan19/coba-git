package Model;

import Dao.labDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class labController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCapacity;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<Laboratorium> tableLab;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colCapacity;

    private mainController MainController;
    private labDao ldao;
    private Laboratorium selected;

    public void setMainController(mainController mainController) {
        MainController = mainController;
        tableLab.setItems(MainController.getlList());
    }
    public void initialize() {
        ldao = new labDao();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        tableLab.setOnMouseClicked(e -> tableUpdate());
    }
    public void tableUpdate(){
        selected = tableLab.getSelectionModel().getSelectedItem();
        if (selected != null){
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            txtId.setText(String.valueOf(selected.getId()));
            txtId.setDisable(true);

            txtName.setText(selected.getName());
            txtCapacity.setText(String.valueOf(selected.getCapacity()));
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void add(ActionEvent event) {
        if (txtId.getText().isEmpty()){
            showAlert("Please fill in Id Field");
        }else if(txtName.getText().isEmpty()){
            showAlert("Please fill in Name Field");
        }else if(txtCapacity.getText().isEmpty()){
            showAlert("Please fill in Capacity Field");
        } else{
            try {
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                int capacity = Integer.parseInt(txtCapacity.getText());
                boolean ada = false;
                for (int i = 0; i < MainController.getlList().size(); i++){
                    if(name.equals( MainController.getlList().get(i).getName())) {
                        ada = true;
                        break;
                    }
                }
                if (ada){
                    showAlert("Data Already Exist!");
                }else{
                    Laboratorium laboratorium = new Laboratorium(id,name,capacity);
                    ldao.addData(laboratorium);
                    MainController.getlList().clear();
                    MainController.getlList().addAll(ldao.showData());
                    tableLab.refresh();
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
        if (selected != null){
            int result = ldao.delData(selected);
            if (result != 0){
                System.out.println("DELETE SUCCESS!");
            }
            MainController.getlList().clear();
            MainController.getlList().addAll(ldao.showData());
            tableLab.refresh();
            clearAll();
        }
    }

    @FXML
    void update(ActionEvent event) {
        if (selected != null) {
            if (txtId.getText().isEmpty()) {
                showAlert("Please fill in Id field!");
            } else if (txtName.getText().isEmpty()) {
                showAlert("Please fill in Name field!");
            } else if(txtCapacity.getText().isEmpty()){
                showAlert("Please fill in Capacity Field");
            }else {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String name = txtName.getText();
                    int capacity = Integer.parseInt(txtCapacity.getText());
                    Laboratorium laboratorium = new Laboratorium(id, name, capacity);
                    int result = ldao.updateData(laboratorium);
                    if (result != 0) {
                        System.out.println("UPDATE SUCCESS");
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Please fill Id or Price with number");
                }
                MainController.getlList().clear();
                MainController.getlList().addAll(ldao.showData());
                tableLab.refresh();
                clearAll();
            }
        }
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
        txtCapacity.clear();
    }

}
