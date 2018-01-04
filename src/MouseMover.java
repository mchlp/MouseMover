/*
 * Michael Pu
 * MouseMover - MouseMover
 * January 04, 2018
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MouseMover extends Application {

    private static final int INTERVAL = 10000;
    private static final int MAX_MOVE = 50;

    private double prevMouseX;
    private double prevMouseY;
    private Robot robot;
    private boolean paused;
    private Timer timer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        paused = false;
        robot = new Robot();
        prevMouseX = MouseInfo.getPointerInfo().getLocation().getX();
        prevMouseY = MouseInfo.getPointerInfo().getLocation().getY();

        VBox vBox = new VBox(10);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);

        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label("Mouse Mover");
        vBox.getChildren().add(label);

        Button pauseBtn = new Button("Pause");
        vBox.getChildren().add(pauseBtn);

        Button stopBtn = new Button("Stop");
        vBox.getChildren().add(stopBtn);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                moveMouse();
            }
        }, INTERVAL, INTERVAL);

        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.exit();
                    System.exit(1);
                }
            }
        });

        pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                paused = !paused;
                if (paused) {
                    pauseBtn.setText("Unpause");
                } else {
                    pauseBtn.setText("Pause");
                }
            }
        });

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        timer.cancel();
        Platform.exit();
        System.exit(0);
        super.stop();
    }

    private void moveMouse() {
        if (!paused) {
            double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            //System.out.println(mouseX + " " + mouseY);
            if (mouseX == prevMouseX && mouseY == prevMouseY) {
                int newX = (int) (mouseX + ((Math.random() * 2) - 1.0) * MAX_MOVE);
                int newY = (int) (mouseY + ((Math.random() * 2) - 1.0) * MAX_MOVE);
                robot.mouseMove(newX, newY);
                mouseX = MouseInfo.getPointerInfo().getLocation().getX();
                mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            }
            prevMouseX = mouseX;
            prevMouseY = mouseY;
        }
    }
}
