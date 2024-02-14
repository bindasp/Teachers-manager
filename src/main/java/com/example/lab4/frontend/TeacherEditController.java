package com.example.lab4.frontend;

import com.example.lab4.backend.DbManager;
import com.example.lab4.models.Teacher;
import com.example.lab4.models.TeacherCondition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;

public class TeacherEditController {
    TeacherCondition[] conditions = TeacherCondition.values();
    private ObjectProperty<String> selectedCondition = new SimpleObjectProperty<>("stan");

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField salaryField;

    @FXML
    private ComboBox<TeacherCondition> conditionComboBox;

    @FXML
    private Button save;

    private Teacher selectedTeacher;

    public void initData(Teacher teacher) {
        this.selectedTeacher = teacher;
        populateFields();
    }

    private void populateFields() {
        firstNameField.setText(selectedTeacher.getImie());
        lastNameField.setText(selectedTeacher.getNazwisko());
        ageField.setText(String.valueOf(selectedTeacher.getRokUrodzenia()));
        salaryField.setText(String.valueOf(selectedTeacher.getWynagrodzenie()));

        conditionComboBox.setValue(selectedTeacher.getStanNauczyciela());

        for (TeacherCondition c : conditions) {
            MenuItem menuItem = new MenuItem(c.name());
            menuItem.setOnAction(event -> {
                selectedCondition.set(c.name());
            });
            conditionComboBox.getItems().add(c);
        }
    }

    @FXML
    private void saveChanges() throws IOException {

        selectedTeacher.setImie(firstNameField.getText());
        selectedTeacher.setNazwisko(lastNameField.getText());
        selectedTeacher.setRokUrodzenia(Integer.parseInt(ageField.getText()));
        selectedTeacher.setWynagrodzenie(Double.parseDouble(salaryField.getText()));
        selectedTeacher.setStanNauczyciela(conditionComboBox.getValue());
        try {
            DbManager.getInstance().updateTeacher(selectedTeacher);
        } catch (ConstraintViolationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błędne dane");
            alert.setHeaderText(null);
            alert.setContentText("Nie można edytować nauczyciela, " + firstNameField.getText() + " " + lastNameField.getText() + " istnieje w bazie danych");
            alert.getButtonTypes().setAll(ButtonType.OK);

            alert.showAndWait();
            System.out.println("Błąd");
        }


        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();

    }
}
