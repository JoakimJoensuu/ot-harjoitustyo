/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package investmentsimulator.ui;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import investmentsimulator.domain.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.*;

public class InvestmentSimulatorUi extends Application {

    private Stage stage;
    private Scene mainMenu;
    private Scene simulationMenu;
    private Scene editMenu;

    private BorderPane simulationLayout;
    private GridPane infoBelowChart;

    private VBox simulationNodes;

    private InvestmentSimulatorService iSService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws SQLException {
        this.iSService = new InvestmentSimulatorService();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.mainMenu = initializeMainMenu();
        this.simulationMenu = initializeSimulationMenu();
        this.editMenu = initializeEditMenu();
        this.stage = stage;
        stage.setTitle("Sijoitussimulaattori");
        stage.setScene(mainMenu);
        stage.show();
    }

    private Scene initializeMainMenu() {
        GridPane simulationForm = createSimulationForm();
        simulationForm.setAlignment(Pos.CENTER_LEFT);

        ScrollPane savedSimulations = createSavedSimulationsList();

        BorderPane layout = new BorderPane();
        layout.setLeft(simulationForm);
        layout.setCenter(savedSimulations);

        return mainMenu = new Scene(layout, 1920, 1080);
    }

    private GridPane createSimulationForm() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));

        Text newText = new Text("Uusi");
        newText.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        form.add(newText, 0, 0, 2, 1);

        Label sum = new Label("Periodeittain sijtoitettava summa:");
        form.add(sum, 0, 1);
        Label startDate = new Label("Alkupäivä:");
        form.add(startDate, 0, 2);
        Label typeOfPeriod = new Label("Periodin tyyppi:");
        form.add(typeOfPeriod, 0, 3);
        Label periods = new Label("Periodeja:");
        form.add(periods, 0, 4);
        Label variation = new Label("Vaihtelutaso:");
        form.add(variation, 0, 5);

        TextField sumField = new TextField();
        form.add(sumField, 1, 1);
        DatePicker dateField = new DatePicker();
        form.add(dateField, 1, 2);
        ComboBox periodTypeField = new ComboBox();
        periodTypeField.getItems().add("Päivä");
        periodTypeField.getItems().add("Viikko");
        periodTypeField.getItems().add("Kuukausi");
        periodTypeField.getItems().add("Vuosi");
        form.add(periodTypeField, 1, 3);
        TextField periodsField = new TextField();
        form.add(periodsField, 1, 4);
        Slider variationField = new Slider();
        variationField.setMin(0);
        variationField.setMax(100);
        variationField.setValue(50);
        variationField.setShowTickLabels(true);
        variationField.setShowTickMarks(true);
        variationField.setMajorTickUnit(50);
        variationField.setMinorTickCount(4);
        variationField.setBlockIncrement(10);
        form.add(variationField, 1, 5);

        Button createManually = new Button("Luo manuaalisesti");
        form.add(createManually, 1, 6);
        Button generate = new Button("Generoi");
        form.add(generate, 1, 7);

        generate.setOnAction((event) -> {
            iSService.generateSimulation(sumField.getText(), dateField.getValue(), periodTypeField.getValue(), periodsField.getText(), variationField.getValue());
            showSimulation();
        });

        return form;
    }

    private ScrollPane createSavedSimulationsList() {
        Text loadLabel = new Text("Lataa simulaatio");
        loadLabel.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));

        simulationNodes = new VBox(10);
        simulationNodes.setMaxWidth(300);
        simulationNodes.setMinWidth(300);
        simulationNodes.setMaxHeight(500);
        simulationNodes.setMinHeight(500);
        redrawSimulationslist();

        ScrollPane listOfSimulations = new ScrollPane();
        listOfSimulations.setContent(simulationNodes);
        return listOfSimulations;
    }

    public void redrawSimulationslist() {
        simulationNodes.getChildren().clear();

        List<Simulation> savedSimulations = iSService.getSavedSimulations();
        savedSimulations.forEach(simulation -> {
            simulationNodes.getChildren().add(createSimulationNode(simulation));
        });
    }

    public Node createSimulationNode(Simulation simulation) {
        HBox box = new HBox(10);
        Label label = new Label(simulation.getName());
        label.setMinHeight(28);
        Button button = new Button("Lataa");
        button.setOnAction(e -> {
            iSService.setLoadedSimulationSelected(simulation);
            showSimulation();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0, 5, 0, 5));

        box.getChildren().addAll(label, spacer, button);
        return box;
    }

    private Scene initializeSimulationMenu() {
        simulationLayout = new BorderPane();

        simulationLayout.setBottom(infoBelowChart);

        return simulationMenu = new Scene(simulationLayout, 1920, 1080);
    }

    private LineChart<String, Number> createSimulationChart() {
        CategoryAxis xAkseli = new CategoryAxis();
        NumberAxis yAkseli = new NumberAxis();
        xAkseli.setLabel("Päivä");
        yAkseli.setLabel("ROI %");
        LineChart<String, Number> chart = new LineChart<>(xAkseli, yAkseli);

        XYChart.Series valueAverageROI = iSService.valueAverageROIsForChart();
        XYChart.Series costAverageROI = iSService.costAverageROIsForChart();

        chart.getData().add(valueAverageROI);
        chart.getData().add(costAverageROI);

        chart.setTitle("Value Average vs Cost Average ROI");
        return chart;
    }

    private void showSimulation() {
        DecimalFormat df = new DecimalFormat("#%");
        LineChart<String, Number> simulationChart = createSimulationChart();
        Simulation selectedSimulation = iSService.getSelectedSimulation();

        Label dateFromChart = new Label("");
        Label costAveraging = new Label("Cost Averaging");
        Label valueAveraging = new Label("Value Averaging");
        Button back = new Button("Takaisin");
        back.setOnAction((event) -> {
            stage.setScene(mainMenu);
        });
        TextField simulationName = new TextField();
        simulationName.setText(iSService.getSelectedSimulation().getName());
        Label errorLabel = new Label();
        Button save = new Button("Tallenna");

        save.setOnAction((event) -> {
            if (simulationName.getText() != null && simulationName.getText().length() > 0) {
                try {
                    iSService.saveSimulation(simulationName.getText());
                    redrawSimulationslist();
                } catch (SQLException ex) {
                    Logger.getLogger(InvestmentSimulatorUi.class.getName()).log(Level.SEVERE, null, ex);
                }
                errorLabel.setText("");
                simulationName.clear();
            } else {
                errorLabel.setText("Tässä pitäisi olla tekstiä");

            }
        });

        Label invested = new Label("Investoitu");
        Label ROI = new Label("Tuottoprosentti");
        Label Profit = new Label("Tuotto");
        Label amountToInvest = new Label("Sijoitettava tässä periodissa");
        Label value = new Label("Arvo");
        Label valueAveragingInvested = new Label("");
        Label valueAveragingROI = new Label("");
        Label valueAveragingProfit = new Label("");
        Label valueAveragingAmountToInvest = new Label("");
        Label costAveragingInvested = new Label("");
        Label costAveragingROI = new Label("");
        Label costAveragingProfit = new Label("");
        Label costAveragingAmountToInvest = new Label("");

        Label valueAveragingValue = new Label("");
        Label costAveragingValue = new Label("");
        Label currentPrice = new Label("");

        this.infoBelowChart = new GridPane();
        infoBelowChart.setHgap(50);
        infoBelowChart.setVgap(10);
        infoBelowChart.setPadding(new Insets(25, 25, 25, 25));
        infoBelowChart.add(dateFromChart, 0, 0);
        infoBelowChart.add(costAveraging, 0, 1);
        infoBelowChart.add(valueAveraging, 0, 2);
        infoBelowChart.add(back, 0, 3);
        infoBelowChart.add(invested, 1, 0);
        infoBelowChart.add(ROI, 2, 0);
        infoBelowChart.add(Profit, 3, 0);
        infoBelowChart.add(amountToInvest, 4, 0);
        infoBelowChart.add(value, 5, 0);
        infoBelowChart.add(valueAveragingInvested, 1, 2);
        infoBelowChart.add(valueAveragingROI, 2, 2);
        infoBelowChart.add(valueAveragingProfit, 3, 2);
        infoBelowChart.add(valueAveragingAmountToInvest, 4, 2);
        infoBelowChart.add(valueAveragingValue, 5, 2);
        infoBelowChart.add(costAveragingInvested, 1, 1);
        infoBelowChart.add(costAveragingROI, 2, 1);
        infoBelowChart.add(costAveragingProfit, 3, 1);
        infoBelowChart.add(costAveragingAmountToInvest, 4, 1);
        infoBelowChart.add(costAveragingValue, 5, 1);
        infoBelowChart.add(currentPrice, 6, 1);
        infoBelowChart.add(simulationName, 7, 2);
        infoBelowChart.add(save, 7, 3);
        infoBelowChart.add(errorLabel, 8, 2);

        final Axis<String> xAxis = simulationChart.getXAxis();

        final Node chartBackground = simulationChart.lookup(".chart-plot-background");
        chartBackground.getParent().getChildrenUnmodifiable().stream().filter((n) -> (n != chartBackground && n != xAxis)).forEachOrdered((n) -> {
            n.setMouseTransparent(true);
        });

        chartBackground.setOnMouseMoved((MouseEvent mouseEvent) -> {
            if (xAxis.getValueForDisplay(mouseEvent.getX()) != null) {
                dateFromChart.setText(xAxis.getValueForDisplay(mouseEvent.getX()));
                LocalDate date = LocalDate.parse(dateFromChart.getText());
                int index = selectedSimulation.getIndexOfTheDate(date);

                valueAveragingInvested.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageInvested()[index]));
                valueAveragingROI.setText("" + selectedSimulation.getValueAverageROI()[index]);
                valueAveragingProfit.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageProfit()[index]));
                valueAveragingAmountToInvest.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAveragePurchases()[index]));
                valueAveragingValue.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageValues()[index]));
                costAveragingInvested.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageInvested()[index]));
                costAveragingROI.setText("" + selectedSimulation.getCostAverageROI()[index]);
                costAveragingProfit.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageProfit()[index]));
                costAveragingAmountToInvest.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAveragePurchases()[index]));
                costAveragingValue.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageValues()[index]));
                currentPrice.setText(selectedSimulation.centsToEuroString(selectedSimulation.getPrices()[index]));
            }
        });

        xAxis.setOnMouseMoved((MouseEvent mouseEvent) -> {

            if (xAxis.getValueForDisplay(mouseEvent.getX()) != null) {
                dateFromChart.setText(xAxis.getValueForDisplay(mouseEvent.getX()));
                LocalDate date = LocalDate.parse(dateFromChart.getText());
                int index = selectedSimulation.getIndexOfTheDate(date);

                valueAveragingInvested.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageInvested()[index]));
                valueAveragingROI.setText("" + selectedSimulation.getValueAverageROI()[index]);
                valueAveragingProfit.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageProfit()[index]));
                valueAveragingAmountToInvest.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAveragePurchases()[index]));
                valueAveragingValue.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageValues()[index]));
                costAveragingInvested.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageInvested()[index]));
                costAveragingROI.setText("" + selectedSimulation.getCostAverageROI()[index]);
                costAveragingProfit.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageProfit()[index]));
                costAveragingAmountToInvest.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAveragePurchases()[index]));
                costAveragingValue.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageValues()[index]));
                currentPrice.setText(selectedSimulation.centsToEuroString(selectedSimulation.getPrices()[index]));
            }
        });

        simulationLayout.setCenter(simulationChart);
        simulationLayout.setBottom(infoBelowChart);
        stage.setScene(simulationMenu);
    }

    private Scene initializeEditMenu() {
        //TODO
        return null;
    }

}
