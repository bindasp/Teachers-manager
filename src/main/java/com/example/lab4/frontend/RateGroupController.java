package com.example.lab4.frontend;

import com.example.lab4.backend.DbManager;
import com.example.lab4.models.ClassTeacher;
import com.example.lab4.models.Rate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Date;

public class RateGroupController {
    private ObjectProperty<Integer> selectedRate = new SimpleObjectProperty<>();

    ClassTeacher group;
    @FXML
    private TextField comment;

    @FXML
    private DatePicker date;

    @FXML
    private SplitMenuButton rates;

    @FXML
    private Button save;

    public void init(ClassTeacher classTeacher){
        group = classTeacher;
    }

    @FXML
    public void initialize(){
        int i =1;
        rates.getItems().clear();
        for (i = 1; i <=6; i++) {
            int rate = i;
            MenuItem menuItem = new MenuItem(String.valueOf(i));
            menuItem.setOnAction(event -> {
                selectedRate.set(rate);
                rates.textProperty().bind(selectedRate.asString());
            });
            rates.getItems().add(menuItem);
        }

    }

    @FXML
    void saveRate(MouseEvent event) {

        try {
            Date data = new Date();
            Rate rate = new Rate(selectedRate.get(), group, data, comment.getText());

            DbManager.getInstance().rateGroup(rate);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błędne dane");
            alert.setHeaderText(null);
            alert.setContentText("Nie można wystawić oceny, wypełnij poprawnie wszystkie pola");
            alert.getButtonTypes().setAll(ButtonType.OK);

            alert.showAndWait();

        }
        Stage stage = (Stage) comment.getScene().getWindow();
        stage.close();

    }

}
