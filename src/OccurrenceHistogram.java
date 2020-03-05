import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OccurrenceHistogram extends Application {

    public void start(Stage primaryStage) {

        Histogram histogram = new Histogram();

        // creating a textfile that shows the file path
        TextField tfFile = new TextField("/Users/user/Documents/CSCI2020U/csci2020u/intellij_tips.txt");
        tfFile.setOnAction(e -> histogram.setCounts(tfFile.getText()));
        tfFile.setPrefColumnCount(30);

        Label lblFile = new Label("Filename", tfFile);
        lblFile.setContentDisplay(ContentDisplay.RIGHT);

        // initializing the view button and assigning the setCounts function
        Button btnView = new Button("View");
        btnView.setOnAction(e -> histogram.setCounts(tfFile.getText()));

        // setting up the bottom positions
        HBox botBox = new HBox(10);
        botBox.getChildren().addAll(tfFile, lblFile, btnView);

        // creating the pane space for histogram, text field and button
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(histogram);
        mainPane.setBottom(botBox);

        // initializing the window and setting up a title
        Scene scene = new Scene(mainPane);
        primaryStage.setTitle("Letter Occurrence Histogram");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public class Histogram extends Pane {

        // creating an array to stock the counts of each letter in alphabet
        private double[] counts = new double[26];

        // setting up the histogram size
        public Histogram() {
            setPrefWidth(800);
            setPrefHeight(300);
        }

        // function to read the file and count the letter occurrences
        public void setCounts(String filename) {
            try (Scanner input = new Scanner(new File(filename))) {
                while(input.hasNext()) {
                    String word = input.next();
                    for(int i = 0; i < word.length(); i++) {
                        char c = Character.toUpperCase(word.charAt(i));
                        if (c >= 'A' && c <= 'Z') {
                            counts[c - 'A']++;
                        }
                    }
                }
                redraw();
            } catch(FileNotFoundException ex) { }
        }

        // function to scale the counts into possible sizes to fit properly
        private void normalize() {
            double sum = 0;
            for(int i = 0; i < counts.length; i++) {
                sum += counts[i];
            }

            for(int i = 0; i < counts.length; i++) {
                counts[i] = counts[i] / sum;
            }
        }

        // applying the scale and creating the histogram rectangles
        // for each letter that has been counted in the counts array
        private void redraw() {
            getChildren().clear();
            normalize();
            int x = 10;
            double scale = 5;

            for(int i = 0; i < counts.length; i++) {
                Rectangle rec = new Rectangle(20, scale * getHeight() * counts[i]);
                rec.setX(x);
                rec.setY(getHeight() - rec.getHeight() - 30);
                rec.setFill(Color.WHITE);
                rec.setStroke(Color.BLACK);
                Text txt = new Text(x + rec.getWidth() / 2 - 3,
                        rec.getY() + rec.getHeight() + 20, Character.toString((char)(i + 'A')));

                getChildren().addAll(rec, txt);
                x += 30;
            }
        }
    }
}