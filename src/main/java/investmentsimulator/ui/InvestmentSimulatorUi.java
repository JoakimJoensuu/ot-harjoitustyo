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
import java.time.LocalDate;
import java.util.*;
import javafx.beans.value.*;
import javafx.scene.chart.*;
import javafx.scene.input.*;

/**
 * Luokka tarjoaa graafisen käyttöliittymän sovellukselle ja toimii sovelluksen
 * käynnistävänä luokkana.
 *
 * @author Joakim Joensuu
 */
public class InvestmentSimulatorUi extends Application {

    private Stage stage;
    private Scene mainMenu;
    private Scene simulationMenu;
    private Scene editMenu;

    private BorderPane simulationLayout;
    private GridPane infoBelowChart;

    private VBox simulationNodes;
    private VBox priceNodes;
    private List<TextField> manualPrices;

    private InvestmentSimulatorService iSService;

    /**
     * Ohjelman käynnistävä metodi.
     *
     * @param args ohjelman käynnistykseen tarvittava parametri
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * Määrittelee luokkamuuttujan "iSService" sovelluksen käynnistämisen
     * alussa.
     *
     * @throws SQLException mikäli tietokannan luominen epäonnistuu
     */
    @Override
    public void init() throws SQLException {
        this.iSService = new InvestmentSimulatorService();
    }

    /**
     *
     * Käynnistää käyttöliittymän, määrittelee luokkamuuttujat "mainMenu",
     * "SimulationMenu", "editMenu" ja "stage", asettaa ikkunan otsikon ja
     * asettaa näkyville päävalikon.
     *
     * @param stage käyttöliittymän ikkuna
     * @throws Exception jos käyttöliittymän käynnistäminen ei onnistu
     */
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

    /**
     * Luo päävalikon.
     *
     * @return päävalikkonäkymä
     */
    private Scene initializeMainMenu() {
        GridPane simulationForm = createSimulationForm();
        simulationForm.setAlignment(Pos.CENTER_LEFT);

        ScrollPane savedSimulations = createSavedSimulationsList();

        BorderPane layout = new BorderPane();
        layout.setLeft(simulationForm);
        layout.setCenter(savedSimulations);

        return new Scene(layout, 1600, 900);
    }

    /**
     * Luo simulaation luomiseen tarvittavan lomakkeen käyttäjän täytettäväksi."
     *
     * @return simulaatiolomake
     */
    private GridPane createSimulationForm() {
        //form pane
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));

        //"new" label
        Text newText = new Text("Uusi");
        newText.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        form.add(newText, 0, 0, 2, 1);

        //form labels
        Label sum = new Label("Periodeittain sijoitettava summa:");
        form.add(sum, 0, 1);
        Label startDate = new Label("Alkupäivä:");
        form.add(startDate, 0, 2);
        Label typeOfPeriod = new Label("Periodin tyyppi:");
        form.add(typeOfPeriod, 0, 3);
        Label periods = new Label("Periodeja:");
        form.add(periods, 0, 4);
        Label variation = new Label("Vaihtelutaso:");
        form.add(variation, 0, 5);
        Label error = new Label("");
        form.add(error, 0, 6);

        //form fields
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

        //form numberfields listeners
        sumField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                sumField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        periodsField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                periodsField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Buttons under form
        Button createManually = new Button("Luo manuaalisesti");
        form.add(createManually, 1, 6);
        createManually.setOnAction((event) -> {
            if (iSService.validateFormFields(sumField.getText(), dateField.getValue(), periodTypeField.getValue(), periodsField.getText(), 0.0)) {
                iSService.generateSimulation(sumField.getText(), dateField.getValue(), periodTypeField.getValue(), periodsField.getText(), 0.0);
                redrawPriceslist();
                error.setText("");
                stage.setScene(editMenu);
            } else {
                error.setText("Tarkista lomakkeen kohdat.");
            }
        });

        Button generate = new Button("Generoi");
        form.add(generate, 1, 7);
        generate.setOnAction((event) -> {
            if (iSService.validateFormFields(sumField.getText(), dateField.getValue(), periodTypeField.getValue(), periodsField.getText(), variationField.getValue())) {
                iSService.generateSimulation(sumField.getText(), dateField.getValue(), periodTypeField.getValue(), periodsField.getText(), variationField.getValue());
                error.setText("");
                showSimulation();
            } else {
                error.setText("Tarkista lomakkeen kohdat.");
            }
        });

        return form;
    }

    /**
     * Luo listauksen tietokantaan tallennetuista simulaatioista, josta
     * tallennettu simulaatio voidaan valita näytettäväksi.
     *
     * @return tallennetut simulaatiot listana
     */
    private ScrollPane createSavedSimulationsList() {
        Text loadLabel = new Text("Lataa simulaatio");
        loadLabel.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));

        simulationNodes = new VBox(10);
        simulationNodes.setMaxWidth(300);
        simulationNodes.setMinWidth(300);
        simulationNodes.setMaxHeight(Integer.MAX_VALUE);
        simulationNodes.setMinHeight(905);
        redrawSimulationslist();

        ScrollPane listOfSimulations = new ScrollPane();
        listOfSimulations.setContent(simulationNodes);
        return listOfSimulations;
    }

    /**
     * Piirtää uudelleen listauksen tietokantaan tallennetuista simulaatioista.
     */
    public void redrawSimulationslist() {
        simulationNodes.getChildren().clear();

        List<Simulation> savedSimulations = iSService.getSavedSimulations();
        savedSimulations.forEach(simulation -> {
            simulationNodes.getChildren().add(createSimulationNode(simulation));
        });
    }

    /**
     * Luo sille annetusta simulaatiosta HBox-olion joka pitää sisällään
     * simulaation nimen ja napin simulaation lataamista varten.
     *
     * @param simulation simulaatio josta näytettävä HBox olio luodaan
     * @return simulaatio ja nappi näytettävänä HBoxina
     */
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

    /**
     * Rakentaa alustavan simulaationäkymän.
     *
     * @return simulaationäkymä
     */
    private Scene initializeSimulationMenu() {
        simulationLayout = new BorderPane();

        simulationLayout.setBottom(infoBelowChart);

        return simulationMenu = new Scene(simulationLayout, 1600, 900);
    }

    /**
     * Rakentaa loput simulaationäkymästä ja asettaa sen näkyville.
     */
    private void showSimulation() {
        //create simulation Linechart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Päivä");
        yAxis.setLabel("ROI %");

        LineChart<String, Number> simulationChart = new LineChart<>(xAxis, yAxis);

        iSService.chartXAxisToPrice(simulationChart);

        simulationChart.setTitle("Value Average vs Cost Average");

        //create back-button shown under linechart
        Button back = new Button("Takaisin");
        back.setOnAction((event) -> {
            stage.setScene(mainMenu);
        });

        //create save-form shown under linechart
        TextField simulationName = new TextField();
        simulationName.setText(iSService.getSelectedSimulation().getName());
        Label errorLabel = new Label();
        Button save = new Button("Tallenna");

        save.setOnAction((event) -> {
            if (simulationName.getText() != null && simulationName.getText().length() > 0) {

                iSService.saveSimulation(simulationName.getText());
                redrawSimulationslist();

                errorLabel.setText("");
                simulationName.clear();
            } else {
                errorLabel.setText("Tässä pitäisi olla tekstiä");

            }
        });

        //create buttons for selecting what is shown in chart
        Button showROI = new Button("Näytä ROI");
        showROI.setOnAction((event) -> {
            iSService.chartXAxisToROI(simulationChart);
        });

        Button showPrice = new Button("Näytä hinnankehitys");
        showPrice.setOnAction((event) -> {
            iSService.chartXAxisToPrice(simulationChart);
        });

        Button showValues = new Button("Näytä arvo");
        showValues.setOnAction((event) -> {
            iSService.chartXAxisToValue(simulationChart);
        });

        Button showProfit = new Button("Näytä tuotto");
        showProfit.setOnAction((event) -> {
            iSService.chartXAxisToProfit(simulationChart);
        });

        Button showPurchases = new Button("Näytä ostot");
        showPurchases.setOnAction((event) -> {
            iSService.chartXAxisToPurchases(simulationChart);
        });

        //Create labels shown under linechart
        Label dateFromChart = new Label("");
        Label costAveraging = new Label("Cost Averaging");
        Label valueAveraging = new Label("Value Averaging");
        Label invested = new Label("Investoitu");
        Label ROI = new Label("Tuottoprosentti");
        Label Profit = new Label("Tuotto");
        Label amountToInvest = new Label("Sijoitettava tässä periodissa");
        Label value = new Label("Arvo       ");
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
        Label priceLabel = new Label("Kohteen hinta");

        //add buttons and labels under chart
        this.infoBelowChart = new GridPane();
        infoBelowChart.setHgap(50);
        infoBelowChart.setVgap(10);
        infoBelowChart.setPadding(new Insets(25, 25, 25, 25));

        //1st row
        infoBelowChart.add(showPrice, 0, 0);
        infoBelowChart.add(showValues, 1, 0);
        infoBelowChart.add(showProfit, 2, 0);
        infoBelowChart.add(showROI, 3, 0);
        infoBelowChart.add(showPurchases, 4, 0);
        infoBelowChart.add(priceLabel, 7, 0);
        infoBelowChart.add(currentPrice, 8, 0);

        //2nd row
        infoBelowChart.add(dateFromChart, 0, 1);
        infoBelowChart.add(invested, 1, 1);
        infoBelowChart.add(ROI, 2, 1);
        infoBelowChart.add(Profit, 3, 1);
        infoBelowChart.add(amountToInvest, 4, 1);
        infoBelowChart.add(value, 5, 1);

        //3rd row
        infoBelowChart.add(costAveraging, 0, 2);
        infoBelowChart.add(costAveragingInvested, 1, 2);
        infoBelowChart.add(costAveragingROI, 2, 2);
        infoBelowChart.add(costAveragingProfit, 3, 2);
        infoBelowChart.add(costAveragingAmountToInvest, 4, 2);
        infoBelowChart.add(costAveragingValue, 5, 2);

        //4th row
        infoBelowChart.add(valueAveraging, 0, 3);

        infoBelowChart.add(valueAveragingInvested, 1, 3);
        infoBelowChart.add(valueAveragingROI, 2, 3);
        infoBelowChart.add(valueAveragingProfit, 3, 3);
        infoBelowChart.add(valueAveragingAmountToInvest, 4, 3);
        infoBelowChart.add(valueAveragingValue, 5, 3);
        infoBelowChart.add(simulationName, 7, 3);
        infoBelowChart.add(errorLabel, 8, 3);

        //5th row
        infoBelowChart.add(back, 0, 4);
        infoBelowChart.add(save, 7, 4);

        //make chart responsive so labels under the chart change their value according to mouse location on chart 
        Simulation selectedSimulation = iSService.getSelectedSimulation();
        final Axis<String> Axis = simulationChart.getXAxis();

        final Node chartBackground = simulationChart.lookup(".chart-plot-background");
        chartBackground.getParent().getChildrenUnmodifiable().stream().filter((n) -> (n != chartBackground && n != Axis)).forEachOrdered((n) -> {
            n.setMouseTransparent(true);
        });

        chartBackground.setOnMouseMoved((MouseEvent mouseEvent) -> {
            if (Axis.getValueForDisplay(mouseEvent.getX()) != null) {
                dateFromChart.setText(Axis.getValueForDisplay(mouseEvent.getX()));
                LocalDate date = LocalDate.parse(dateFromChart.getText());
                int index = selectedSimulation.getIndexOfTheDate(date);

                valueAveragingInvested.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageInvested()[index]));
                valueAveragingROI.setText("" + selectedSimulation.getValueAverageROI()[index] + " %");
                valueAveragingProfit.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageProfit()[index]));
                valueAveragingAmountToInvest.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAveragePurchases()[index]));
                valueAveragingValue.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageValues()[index]));
                costAveragingInvested.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageInvested()[index]));
                costAveragingROI.setText("" + selectedSimulation.getCostAverageROI()[index] + " %");
                costAveragingProfit.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageProfit()[index]));
                costAveragingAmountToInvest.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAveragePurchases()[index]));
                costAveragingValue.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageValues()[index]));
                currentPrice.setText(selectedSimulation.centsToEuroString(selectedSimulation.getPrices()[index]));
            }
        });

        Axis.setOnMouseMoved((MouseEvent mouseEvent) -> {

            if (Axis.getValueForDisplay(mouseEvent.getX()) != null) {
                dateFromChart.setText(Axis.getValueForDisplay(mouseEvent.getX()));
                LocalDate date = LocalDate.parse(dateFromChart.getText());
                int index = selectedSimulation.getIndexOfTheDate(date);

                valueAveragingInvested.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageInvested()[index]));
                valueAveragingROI.setText("" + selectedSimulation.getValueAverageROI()[index] + " %");
                valueAveragingProfit.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageProfit()[index]));
                valueAveragingAmountToInvest.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAveragePurchases()[index]));
                valueAveragingValue.setText(selectedSimulation.centsToEuroString(selectedSimulation.getValueAverageValues()[index]));
                costAveragingInvested.setText(selectedSimulation.centsToEuroString(selectedSimulation.getCostAverageInvested()[index]));
                costAveragingROI.setText("" + selectedSimulation.getCostAverageROI()[index] + " %");
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

    /**
     * Luo näkymän, jossa käyttäjä voi syöttää manuaalisesti kohteen hinnat eri
     * periodeille.
     *
     * @return näkymä hintojen manuaaliselle syöttämiselle
     */
    private Scene initializeEditMenu() {
        ScrollPane priceForm = createPriceForm();
        HBox editMenuButtons = createEditMenuButtons();

        BorderPane layout = new BorderPane();

        layout.setCenter(priceForm);
        layout.setBottom(editMenuButtons);

        return new Scene(layout, 1600, 900);
    }

    /**
     * Luo listauksen, jossa eri päivämäärät ja niille syötettävät hinnat.
     *
     * @return lista hintojen syöttämistä varten
     */
    private ScrollPane createPriceForm() {
        manualPrices = new ArrayList<>();

        priceNodes = new VBox(10);
        priceNodes.setMaxWidth(300);
        priceNodes.setMinWidth(300);
        priceNodes.setMaxHeight(Integer.MAX_VALUE);
        priceNodes.setMinHeight(905);

        ScrollPane listOfSimulations = new ScrollPane();
        listOfSimulations.setContent(priceNodes);
        return listOfSimulations;
    }

    /**
     * HBox-olion joka pitää sisällään hintojen syöttämisnäkymään liittyvät
     * alalaidan painikkeet.
     *
     * @return alalaidan painikkeet
     */
    private HBox createEditMenuButtons() {
        Button back = new Button("Takaisin");
        back.setOnAction((event) -> {
            stage.setScene(mainMenu);
        });

        Label error = new Label("");

        Button create = new Button("Luo");
        create.setOnAction((event) -> {
            if (iSService.validateManualPrices(manualPrices)) {

                iSService.setSimulationPrices(manualPrices);
                iSService.getSelectedSimulation().initializeArrays();
                error.setText("");
                showSimulation();
            } else {
                error.setText("Tarkista hinnat.");
            }
        });

        HBox buttons = new HBox(back, create, error);

        buttons.setPadding(new Insets(10, 10, 10, 10));

        return buttons;
    }

    /**
     * Piirtää hintojen syöttämiseen tarkoitetun listan päivämäärät ja
     * tekstikentät.
     */
    private void redrawPriceslist() {
        priceNodes.getChildren().clear();
        manualPrices.clear();

        List<LocalDate> dates = iSService.getDates();

        dates.forEach(date -> {
            priceNodes.getChildren().add(createPriceNode(date));
        });
    }

    /**
     * Luo sille annetusta päivämäärästä HBox-olion joka pitää sisällään
     * päivämäärän ja tekstikentän hinnan syöttämiseen kyseiselle päivälle.
     *
     * @param date päivämäärä, josta näytettävä HBox olio luodaan
     * @return päivämäärä ja tekstikenttä näytettävänä HBox-oliona
     */
    public Node createPriceNode(LocalDate date) {
        HBox box = new HBox(10);

        Label label = new Label(date.toString());
        label.setMinHeight(28);

        TextField priceField = new TextField();
        //form numberfields listener
        priceField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                priceField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        manualPrices.add(priceField);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0, 5, 0, 5));

        Label euroLabel = new Label("€");
        euroLabel.setMinHeight(28);

        box.getChildren().addAll(label, spacer, priceField, euroLabel);

        return box;
    }

}
