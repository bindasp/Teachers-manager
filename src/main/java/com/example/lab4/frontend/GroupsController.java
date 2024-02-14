package com.example.lab4.frontend;

import com.example.lab4.Menu;
import com.example.lab4.backend.DbManager;
import com.example.lab4.backend.Exporter;
import com.example.lab4.models.ClassTeacher;
import com.example.lab4.models.Rate;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GroupsController {
    private Stage stage;
    @FXML
    private TableColumn<ClassTeacher, Integer> capability;

    @FXML
    private TableColumn<ClassTeacher, String> group;

    @FXML
    private TableView<ClassTeacher> table;

    @FXML
    private Button add;

    @FXML
    private TextField count;

    @FXML
    private TextField name;

    @FXML
    private Button remove;

    @FXML
    private Button back;

    @FXML
    private Button show;

    @FXML
    private TableColumn<ClassTeacher, Double> rateColumn;

    @FXML
    void showGroup(MouseEvent event) throws IOException {
        ClassTeacher selectedClass = table.getSelectionModel().getSelectedItem();

        if (selectedClass != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab4/group.fxml"));
            Parent root = loader.load();
            GroupShowController groupsController = loader.getController();
            groupsController.initData(selectedClass);

            Stage stage = new Stage();
            stage.setTitle(selectedClass.getNazwa());

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    @FXML
    void addGroup(MouseEvent event) {
        try {
            DbManager.getInstance().saveClassTeacher(new ClassTeacher(name.getText(), Integer.parseInt(count.getText())));
            update();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Nie można dodać grupy", "Wypełnij poprawnie pola");
        }

    }

    @FXML
    void removeGroup(MouseEvent event) {
        try {
            DbManager.getInstance().deleteClass(table.getSelectionModel().selectedItemProperty().get());
            update();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Nie można usunąć grupy", "Wybierz grupę, którą chcesz usunąć");
        }
    }

    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 440);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menu");
        stage.setScene(scene);
    }

    public void initialize() {

        group.setCellValueFactory(new PropertyValueFactory<ClassTeacher, String>("nazwa"));
        capability.setCellValueFactory(new PropertyValueFactory<ClassTeacher, Integer>("max"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<ClassTeacher, Double>("srednia"));
        update();


    }

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait();
    }

    public void update() {
        List<ClassTeacher> classTeacherList = DbManager.getInstance().getAllClassTeachers();

        for (ClassTeacher c : classTeacherList) {
            List<Rate> rateList = DbManager.getInstance().getRateByGroupID(c.getId());
            c.setSrednia(0);
            double srednia = 0;
            for (Rate rate : rateList) {
                if (rate.getData() != null)
                    srednia += rate.getOcena();
            }
            if (!rateList.isEmpty())
                c.setSrednia(srednia / rateList.size());
        }

        table.setItems(FXCollections.observableArrayList(classTeacherList));
    }

    @FXML
    void rateGroup(MouseEvent event) throws IOException {
        ClassTeacher selectedClass = table.getSelectionModel().getSelectedItem();

        if (selectedClass != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab4/rate-group.fxml"));
            Parent root = loader.load();
            RateGroupController rateGroupController = loader.getController();
            rateGroupController.init(selectedClass);
            Stage stage = new Stage();
            stage.setTitle(selectedClass.getNazwa());

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            update();
        }
    }

    @FXML
    void exportTable(MouseEvent event) {
        Exporter.exportToCsv("groups", "groups.csv");
        Exporter.exportToCsv("rate", "rate.csv");
    }
}
