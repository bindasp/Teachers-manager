package com.example.lab4.frontend;

import com.example.lab4.Menu;
import com.example.lab4.backend.DbManager;
import com.example.lab4.backend.Exporter;
import com.example.lab4.models.ClassTeacher;
import com.example.lab4.models.Teacher;
import com.example.lab4.models.TeacherCondition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TeachersController{

    private static TeachersController instance;

    public static TeachersController getInstance() {
        return instance;
    }

    private Stage stage;
    private TeacherCondition[] conditions;
    List<ClassTeacher> classTeachers;
    @FXML
    private Button addTeacher;

    @FXML
    private Button test;

    @FXML
    private TableColumn<Teacher, Integer> age;

    @FXML
    private TableColumn<Teacher, String> firstName;

    @FXML
    private TableColumn<Teacher, String> name;

    @FXML
    private Button remove;

    @FXML
    private TableColumn<Teacher, Double> salary;

    @FXML
    private TableColumn<Teacher, String> className;

    @FXML
    private TableView<Teacher> table;
    @FXML
    private TextField teacherText;

    @FXML
    private TextField ageT;

    @FXML
    private SplitMenuButton groupT;

    @FXML
    private TextField lastNameT;

    @FXML
    private TextField nameT;

    @FXML
    private TextField salaryT;

    @FXML
    private TableColumn<Teacher, TeacherCondition> condition;

    @FXML
    private SplitMenuButton conditionT;

    @FXML
    private TextField searchPartial;

    @FXML
    private CheckBox checkbox;

    public String getSelectedOption() {
        return selectedOption.get();
    }

    public ObjectProperty<String> selectedOptionProperty() {
        return selectedOption;
    }

    @FXML
    private Button back;
    @FXML
    private Button edit;

    private ObjectProperty<String> selectedOption = new SimpleObjectProperty<>("grupa");
    private ObjectProperty<String> selectedCondition = new SimpleObjectProperty<>("stan");
    private ObjectProperty<ClassTeacher> selectedGroup = new SimpleObjectProperty<>();

    @FXML
    private void editTeacher() throws IOException {
        Teacher selectedTeacher = table.getSelectionModel().getSelectedItem();

        if (selectedTeacher != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab4/teacher-edit.fxml"));
            Parent root = loader.load();
            TeacherEditController editController = loader.getController();
            editController.initData(selectedTeacher);

            Stage stage = new Stage();
            stage.setTitle(selectedTeacher.getImie() + " " + selectedTeacher.getNazwisko());

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            update();
        }

    }

    @FXML
    void addTeacher(MouseEvent event) {

       try {
        List<Teacher> list = DbManager.getInstance().getTeacherByGroupName(selectedGroup.get().getNazwa());
            if (list.size() < selectedGroup.get().getMax()) {

                Teacher teacher = new Teacher(nameT.getText(), lastNameT.getText(), TeacherCondition.valueOf(selectedCondition.get()),
                        Integer.parseInt(ageT.getText()), Double.parseDouble(salaryT.getText()), selectedGroup.get());
                DbManager.getInstance().saveTeacher(teacher);
                update();
            } else
                showAlert(Alert.AlertType.INFORMATION, "Grupa zapełniona", "Nie można dodać nauczyciela do grupy, grupa została zapełniona");
        }
        catch (IllegalStateException e){
            showAlert(Alert.AlertType.INFORMATION, "Błędne dane", "Nie można dodać nauczyciela do grupy, " + nameT.getText() + " " + lastNameT.getText() + " istnieje w bazie danych");
        }
       catch (Exception e) {
           showAlert(Alert.AlertType.INFORMATION, "Błędne dane", "Nie można dodać nauczyciela do grupy, wypełnij poprawnie wszystkie pola");
       }

    }

    @FXML
    void removeTeacher(MouseEvent event) {
        try {
            DbManager.getInstance().deleteTeacher(table.getSelectionModel().selectedItemProperty().get());
            update();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Nie można usunąć nauczyciela", "Wybierz nauczyciela, którego chcesz usunąć");
        }
    }

    @FXML
    void backToMenu(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 440);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menu");
        stage.setScene(scene);
    }

    @FXML
    void searchPartial(KeyEvent event) {
        ObservableList<Teacher> list = FXCollections.observableArrayList();
        String partialText = "";

        if(event.getCode() == KeyCode.ENTER)
        {
            partialText = searchPartial.getText();
            for (Teacher t : DbManager.getInstance().getAllTeachers()) {
                if(t.getNazwisko().contains(partialText)) {
                    list.add(t);
                }
            }
            table.setItems(list);
        }

    }

    public void initialize() {

        instance = this;

        groupT.getItems().clear();
        conditionT.getItems().clear();
        conditions = TeacherCondition.values();

        classTeachers =  DbManager.getInstance().getAllClassTeachers();

        classTeachers.forEach((c)->{
            MenuItem menuItem = new MenuItem(c.getNazwa());
            menuItem.setOnAction(event -> {
                selectedOption.set((c.getNazwa()));
                selectedGroup.set(c);

          });
            groupT.getItems().add(menuItem);
        });


        for (TeacherCondition c : conditions) {
            MenuItem menuItem = new MenuItem(c.name());
            menuItem.setOnAction(event -> {
                selectedCondition.set(c.name());

            });
            conditionT.getItems().add(menuItem);
        }

        groupT.textProperty().bind(selectedOption);
        conditionT.textProperty().bind(selectedCondition);

        firstName.setCellValueFactory(new PropertyValueFactory<Teacher, String>("imie"));
        name.setCellValueFactory(new PropertyValueFactory<Teacher, String>("nazwisko"));
        age.setCellValueFactory(new PropertyValueFactory<Teacher, Integer>("rokUrodzenia"));
        salary.setCellValueFactory(new PropertyValueFactory<Teacher, Double>("wynagrodzenie"));
        className.setCellValueFactory(new PropertyValueFactory<Teacher, String>("groupName"));
        condition.setCellValueFactory(new PropertyValueFactory<Teacher, TeacherCondition>("stanNauczyciela"));

        update();

    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    public void update() {
        try {
            table.setItems(DbManager.getInstance().getAllTeachers());
            table.refresh();
        } catch (Exception e) {

        }
    }

    @FXML
    void exportTable(MouseEvent event) {
        Exporter.exportToCsv("teachers", "teachers.csv");
    }

}
