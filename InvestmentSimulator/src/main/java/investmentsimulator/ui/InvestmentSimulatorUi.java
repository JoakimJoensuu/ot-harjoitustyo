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

public class InvestmentSimulatorUi extends Application {

    private Scene mainMenu;
    private Scene simulationMenu;
    private Scene editMenu;

    private InvestmentSimulatorService iSService = new InvestmentSimulatorService();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.mainMenu = initializeMainMenu();
        this.simulationMenu = initializeSimulationMenu();
        this.editMenu = initializeEditMenu();

        stage.setTitle("Sijoitussimulaattori");
        stage.setScene(mainMenu);
        stage.show();
    }

    private Scene initializeMainMenu() {
        GridPane simulationForm = createSimulationForm();
        simulationForm.setAlignment(Pos.CENTER_LEFT);

        VBox savedSimulations = createSavedSimulationsList();
        savedSimulations.setAlignment(Pos.CENTER_RIGHT);

        HBox layout = new HBox(simulationForm, savedSimulations);
        layout.setAlignment(Pos.CENTER);

        return mainMenu = new Scene(layout, 1600, 900);
    }

    private GridPane createSimulationForm() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));

        Text newText = new Text("Uusi");
        newText.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        form.add(newText, 0, 0, 2, 1);

        Label sum = new Label("Summa:");
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
            iSService.GenerateSimulation(sumField.getText(), dateField.getValue(), periodTypeField.getValue(), periodsField.getText(), variationField.getValue());
        });

        return form;
    }

    private VBox createSavedSimulationsList() {
        Text loadLabel = new Text("Lataa simulaatio");
        loadLabel.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));

        ScrollPane listOfSimulations = new ScrollPane();
        Button delete = new Button("Poista");
        Button load = new Button("Lataa");
        return new VBox(loadLabel, listOfSimulations, delete, load);
    }

    private Scene initializeSimulationMenu() {
        //TODO
        return null;
    }

    private Scene initializeEditMenu() {
        //TODO
        return null;
    }

}
