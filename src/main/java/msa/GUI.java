package msa;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import msa.cql.CQLManager;
import msa.db.HSQLDatabase;

public class GUI extends Application {
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MSA | Mergentheim/Mosbach Security Agency");

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: #336699;");

        Button executeButton = new Button("Execute");
        executeButton.setPrefSize(100, 20);

        Button closeButton = new Button("Close");
        closeButton.setPrefSize(100, 20);

        TextArea commandLineArea = new TextArea();
        commandLineArea.setWrapText(true);

        TextArea outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);

        //TODO Make the creation of the database a bit more beautiful O.O
        HSQLDatabase database = new HSQLDatabase();
        database.init();                                                // Fill database with available algorithms etc.
        CQLManager cqlManager = new CQLManager(database);
        cqlManager.handleAll(Configuration.instance.simulationQueries); // Simulation

        executeButton.setOnAction(event -> {
            System.out.println("[execute] pressed");
            cqlManager.handle(commandLineArea.getText());
            String queryResult = cqlManager.getContext().pullQueryResult();
            commandLineArea.clear();
            if (queryResult.equals("")) {
                // Not a valid query
                outputArea.setText("Your input was not a valid query.");
            } else if (queryResult.startsWith(" ")) {
                // No info to return
                //TODO reset output Area?
                outputArea.clear();
            } else {
                // Has info to return
                outputArea.setText(queryResult);
            }
        });

        closeButton.setOnAction(actionEvent -> {
            System.out.println("[close] pressed");
            System.exit(0);
        });

        hBox.getChildren().addAll(executeButton, closeButton);

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(25, 25, 25, 25));
        vbox.getChildren().addAll(hBox, commandLineArea, outputArea);

        Scene scene = new Scene(vbox, 950, 500);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case F3:
                    if (Configuration.instance.verbose) System.out.println("F3 pressed");
                    cqlManager.getContext().toggleDebug();
                    break;
                case F5:
                    if (Configuration.instance.verbose) System.out.println("F5 pressed");
                    executeButton.fire();
                    break;
                case F8:
                    if (Configuration.instance.verbose) System.out.println("F8 pressed");
                    cqlManager.getContext().displayMostCurrentLogFile();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}