import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DisplayingThreeCards extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new HBox(5);
        pane.setPadding(new Insets(5,5,5,5));

        // Creating and displaying images of three randomly selected playing cards from a deck of 54
        Image image1 = new Image(SelectRandomCard());
        ImageView image_view1 = new ImageView(image1);
        image_view1.setFitHeight(175);
        image_view1.setFitWidth(125);

        Image image2 = new Image(SelectRandomCard());
        ImageView image_view2 = new ImageView(image2);
        image_view2.setFitHeight(175);
        image_view2.setFitWidth(125);

        Image image3 = new Image(SelectRandomCard());
        ImageView image_view3 = new ImageView(image3);
        image_view3.setFitHeight(175);
        image_view3.setFitWidth(125);

        // Adding to the pane
        pane.getChildren().addAll(image_view1, image_view2, image_view3);

        // Setting the scene
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DisplayingThreeCards");
        primaryStage.show();
    }

    // A function which will select a random card from the deck of 54 playing cards
    private String SelectRandomCard() {

        // Creating a random variable which will have a random value between 1 - 54
        int random = (int) (Math.random() * 54 + 1);

        // Assigning and returning the name and location of the random card as a string
        String random_card = "/cards/" + random + ".png";
        return random_card;
    }
}
