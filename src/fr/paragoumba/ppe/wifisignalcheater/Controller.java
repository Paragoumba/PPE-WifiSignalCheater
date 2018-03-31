package fr.paragoumba.ppe.wifisignalcheater;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    @FXML
    TextField gapTextField;

    @FXML
    Label stateLabel;

    @FXML
    Label levelLabel;

    @FXML
    Label wattLabel;

    @FXML
    LineChart<Number, Number> levelLineChart;

    @FXML
    LineChart<Number, Number> wattLineChart;

    @FXML
    ToggleButton toggleButton;

    public static String state = "";

    private static HashMap<String, XYChart.Series<Number, Number>> seriesMap = new HashMap<>();
    private static final long start = System.currentTimeMillis();
    private static Controller controller;
    private static long maxTime;
    private static long minTime;

    public void initialize(){

        stateLabel.setText(state);
        gapTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {

                gapTextField.setText(newValue.replaceAll("[^\\d]", ""));

            }
        });

        XYChart.Series<Number, Number> norm = new XYChart.Series<>(FXCollections.observableList(Arrays.asList(new XYChart.Data<>(0, 20), new XYChart.Data<>(System.currentTimeMillis() - start, 20))));

        norm.setName("Norm");
        levelLineChart.getData().add(norm);
        ((NumberAxis) levelLineChart.getXAxis()).setForceZeroInRange(false);
        levelLineChart.setAnimated(true);
        levelLineChart.getXAxis().setLabel("Time (ms)");
        levelLineChart.getYAxis().setLabel("Level (dbm)");

        ((NumberAxis) wattLineChart.getXAxis()).setForceZeroInRange(false);
        wattLineChart.setAnimated(true);
        wattLineChart.getXAxis().setLabel("Time (ms)");
        wattLineChart.getYAxis().setLabel("Power (watt)");

    }

    public static void setController(Controller controller){

        Controller.controller = controller;

    }

    public static void updateSeries(ArrayList<Wifi> list){

        for (Wifi wifi : list){

            String ssid = wifi.getSsid();
            XYChart.Series<Number, Number> series = seriesMap.get(ssid);

            if (series == null){

                series = new XYChart.Series<>();

                series.setName(ssid);
                series.getData().add(new XYChart.Data<>(System.currentTimeMillis() - start, Math.random() * wifi.getDbm()));
                seriesMap.put(ssid, series);

                XYChart.Series<Number, Number> finalSeries = series;

                Platform.runLater(() -> controller.levelLineChart.getData().add(finalSeries));

            } else {

                ObservableList<XYChart.Data<Number, Number>> datas = series.getData();
                maxTime = (long) datas.get(0).getXValue();
                minTime = (long) datas.get(0).getXValue();
                XYChart.Series<Number, Number> finalSeries = series;

                Platform.runLater(() -> {

                    datas.add(new XYChart.Data<>(System.currentTimeMillis() - start, wifi.getDbm()));
                    datas.remove(0, finalSeries.getData().size() - 20);

                    for (XYChart.Data data : datas){

                        long time = (long) data.getXValue();

                        if (time > maxTime) maxTime = time;
                        if (time < minTime) minTime = time;

                    }
                });

                ObservableList<XYChart.Data<Number, Number>> normDatas = controller.levelLineChart.getData().get(0).getData();

                normDatas.get(1).setXValue(maxTime);
                normDatas.get(0).setXValue(minTime);

                NumberAxis xAxis = ((NumberAxis) controller.levelLineChart.getXAxis());

                xAxis.setLowerBound(minTime);
                xAxis.setUpperBound(maxTime);

            }
        }

        for (Map.Entry<String, XYChart.Series<Number, Number>> entry : seriesMap.entrySet()){

            XYChart.Series<Number, Number> series = entry.getValue();
            XYChart.Series<Number, Number> wattSeries = new XYChart.Series<>(FXCollections.observableArrayList());

            Platform.runLater(() -> {

                for (XYChart.Data<Number, Number> data : series.getData()){

                    XYChart.Data<Number, Number> newData = new XYChart.Data<>(data.getXValue(), Wifi.dbmToWatt((Double) data.getYValue()));
                    wattSeries.getData().add(newData);

                }

                controller.wattLineChart.getData().add(wattSeries);

            });
        }
    }

    @FXML
    public void setGap(){

        long gap = Long.parseLong(gapTextField.getText());

        //Client.setGap(gap);

        gapTextField.setPromptText(gap + "ms");
        gapTextField.setText("");

    }

    @FXML
    public void toggleChart(){

        if (toggleButton.isSelected()){

            wattLineChart.setVisible(true);
            levelLineChart.setVisible(false);
            wattLabel.setStyle("-fx-background-color: green");
            levelLabel.setStyle("-fx-background-color: red");

        } else {

            wattLineChart.setVisible(false);
            levelLineChart.setVisible(true);
            levelLabel.setStyle("-fx-background-color: green");
            wattLabel.setStyle("-fx-background-color: red");

        }
    }
}
