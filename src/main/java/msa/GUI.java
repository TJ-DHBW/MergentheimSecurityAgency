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
import msa.db.HibernateUtil;
import msa.db.model.Algorithm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

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
        CQLManager cqlManager = new CQLManager(new HSQLDatabase());

        //TODO Testing - REMOVE PLS
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new Algorithm("rsa"));
        session.save(new Algorithm("shift"));
        transaction.commit();

        List<Algorithm> algs = session.createQuery("from Algorithm", Algorithm.class).list();
        algs.forEach(alg -> System.out.println(alg.getName()));

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
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}