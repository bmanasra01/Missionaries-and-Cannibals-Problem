package com.example.ai_pro1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    static long executionTime;
    static long endTime;

    static long startTime;

    public static void main(String[] args) {
       launch(args);



        System.out.println("Execution Time: " + executionTime + " milliseconds");

//        long startTime2 = System.currentTimeMillis();
//        DFS.solve();
//        long endTime2 = System.currentTimeMillis();
//        long executionTime2 = endTime2 - startTime2;
//        System.out.println("Execution Time: " + executionTime + " milliseconds");



    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Missionaries and Cannibals Problem");

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightblue;");

        Button DFS_but = new Button("DFS solution");
        DFS_but.setLayoutX(96.0);
        DFS_but.setLayoutY(40.0);
        DFS_but.setPrefHeight(54.0);
        DFS_but.setPrefWidth(156.0);
        DFS_but.setStyle("-fx-font: 20px \"Serif\"; -fx-border-color: black; -fx-background-color:tan ");
        pane.getChildren().add(DFS_but);

        Button BFS_but = new Button("BFS solution");
        BFS_but.setLayoutX(462.0);
        BFS_but.setLayoutY(40.0);
        BFS_but.setPrefHeight(54.0);
        BFS_but.setPrefWidth(146.0);
        BFS_but.setStyle("-fx-font: 20px \"Serif\"; -fx-border-color: black; -fx-background-color:tan ");
        pane.getChildren().add(BFS_but);

        Line line = new Line();
        line.setEndX(40.0);
        line.setEndY(-136.00001525878906);
        line.setLayoutX(340.0);
        line.setLayoutY(136.0);
        line.setStartX(40.0);
        line.setStartY(351.0);
        pane.getChildren().add(line);

        TextArea DFS_text = new TextArea();
        DFS_text.setLayoutX(37.0);
        DFS_text.setLayoutY(125.0);
        DFS_text.setPrefHeight(348.0);
        DFS_text.setPrefWidth(333.0);
        DFS_text.setEditable(false);
        DFS_text.setStyle("-fx-border-color : black;");
        Font font = Font.font("Monospaced", 16);
        DFS_text.setFont(font);
        pane.getChildren().add(DFS_text);

        DFS_but.setOnAction(e -> {

            DFS_text.setText(DFS.solve());
        });

        TextArea BFS_text = new TextArea();
        BFS_text.setLayoutX(392.0);
        BFS_text.setLayoutY(125.0);
        BFS_text.setPrefHeight(348.0);
        BFS_text.setPrefWidth(333.0);
        BFS_text.setEditable(false);
        BFS_text.setStyle("-fx-border-color : black;");
        BFS_text.setFont(font);
        pane.getChildren().add(BFS_text);


        //long startTime = System.currentTimeMillis();




        BFS_but.setOnAction(e -> {


            startTime = System.currentTimeMillis();
            BFS_text.appendText(BFS.solve());
            endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;

            BFS_text.appendText("Step 12: (0, 0, right, 3, 3)\n\n");





            BFS_text.appendText("Execution Time: " + executionTime + " milliseconds");




        });

        Scene scene = new Scene(pane, 750, 487);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
