package com.example.lab4.frontend;

import com.example.lab4.Menu;
import com.example.lab4.backend.Exporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button nauczyciele;

    @FXML
    private Label welcomeText;
    private Stage stage;

    @FXML
    protected void onTeachersClick(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("teachers.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 440);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Nauczyciele");
        stage.setScene(scene);

    }
    @FXML
    protected void onGroupsClick(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("groups.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 440);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Grupy");
        stage.setScene(scene);

    }


    public void initialize(){

    }
}