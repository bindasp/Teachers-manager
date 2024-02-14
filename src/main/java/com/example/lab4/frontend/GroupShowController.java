package com.example.lab4.frontend;

import com.example.lab4.backend.DbManager;
import com.example.lab4.models.Rate;
import com.example.lab4.models.Teacher;
import com.example.lab4.models.TeacherCondition;
import com.example.lab4.models.ClassTeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;

public class GroupShowController {
    private ClassTeacher selectedClass;
    ObservableList<Teacher> list = FXCollections.observableArrayList();
    private List<Rate> rateList;

    @FXML
    private BarChart<String, Number> ratesBarChart;

    @FXML
    private TableColumn<Rate, String> comment;

    @FXML
    private TableColumn<Rate, Date> date;

    @FXML
    private TableColumn<Rate, Integer> rate;

    @FXML
    private TableView<Rate> rates;

    @FXML
    private TableColumn<Teacher, Integer> age;

    @FXML
    private TableColumn<Teacher, TeacherCondition> condition;

    @FXML
    private TableColumn<Teacher, String> lastName;

    @FXML
    private TableColumn<Teacher, String> name;

    @FXML
    private TableColumn<Teacher, Double> salary;

    @FXML
    private TableView<Teacher> table;

    @FXML
    private PieChart piechart;

    @FXML
    private BarChart<String, Number> barchart;

    public void initData(ClassTeacher classTeacher) {
        selectedClass = classTeacher;
        rateList =  DbManager.getInstance().getRateByGroupID(selectedClass.getId());
        list.addAll(selectedClass.getNauczyciele());

        setBarchart();
        setPiechart();
        setRates();
        populateFields();
    }

    private void setRates(){
        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();

        int jeden = 0;
        int dwa = 0;
        int trzy = 0;
        int cztery = 0;
        int piec = 0;
        int szesc = 0;

        for(Rate r : rateList){
            if(r.getOcena() == 1){
                jeden++;
            }
            if(r.getOcena() == 2){
                dwa++;
            }
            if(r.getOcena() == 3){
                trzy++;
            }
            if(r.getOcena() == 4){
                cztery++;
            }
            if(r.getOcena() == 5){
                piec++;
            }
            if(r.getOcena() == 6){
                szesc++;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("1", jeden));
        series.getData().add(new XYChart.Data<>("2", dwa));
        series.getData().add(new XYChart.Data<>("3", trzy));
        series.getData().add(new XYChart.Data<>("4", cztery));
        series.getData().add(new XYChart.Data<>("5", piec));
        series.getData().add(new XYChart.Data<>("6", szesc));
        barChartData.add(series);
        ratesBarChart.setLegendVisible(false);
        ratesBarChart.setData(barChartData);
    }

    private void setBarchart() {
        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();

        int chory = 0;
        int obecny = 0;
        int nieobecny = 0;
        int delegacja = 0;

        for (Teacher c : list) {
            if (c.getStanNauczyciela().name().equals("CHORY")) {
                chory++;
            }
            if (c.getStanNauczyciela().name().equals("OBECNY")) {
                obecny++;
            }
            if (c.getStanNauczyciela().name().equals("NIEOBECNY")) {
                nieobecny++;
            }
            if (c.getStanNauczyciela().name().equals("DELEGACJA")) {
                delegacja++;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("chory", chory));
        series.getData().add(new XYChart.Data<>("obecny", obecny));
        series.getData().add(new XYChart.Data<>("nieobecny", nieobecny));
        series.getData().add(new XYChart.Data<>("delegacja", delegacja));

        barChartData.add(series);
        barchart.setLegendVisible(false);
        barchart.setData(barChartData);

    }

    private void setPiechart() {
        ObservableList<PieChart.Data> pieChartdata = FXCollections.observableArrayList();
        double count = list.size();
        double max = selectedClass.getMax();

        pieChartdata.add(new PieChart.Data("Zatrudnieni", count));
        pieChartdata.add(new PieChart.Data("Wolne stanowiska", max - count));
        piechart.setLegendSide(Side.BOTTOM);
        piechart.setLegendVisible(true);
        piechart.setData(pieChartdata);
    }

    private void populateFields() {

        name.setCellValueFactory(new PropertyValueFactory<Teacher, String>("imie"));
        lastName.setCellValueFactory(new PropertyValueFactory<Teacher, String>("nazwisko"));
        age.setCellValueFactory(new PropertyValueFactory<Teacher, Integer>("rokUrodzenia"));
        salary.setCellValueFactory(new PropertyValueFactory<Teacher, Double>("wynagrodzenie"));
        condition.setCellValueFactory(new PropertyValueFactory<Teacher, TeacherCondition>("stanNauczyciela"));

        table.setItems(list);

        rate.setCellValueFactory(new PropertyValueFactory<Rate,Integer>("ocena"));
        date.setCellValueFactory(new PropertyValueFactory<Rate, Date>("data"));
        comment.setCellValueFactory(new PropertyValueFactory<Rate, String>("komentarz"));

        rates.setItems(FXCollections.observableArrayList(rateList));
    }
}
