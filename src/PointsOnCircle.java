import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PointsOnCircle extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();

        // Creating main circle
        Circle main_circle = new Circle();
        main_circle.setCenterX(150);
        main_circle.setCenterY(150);
        main_circle.setRadius(100);
        main_circle.setStroke(Color.BLACK);
        main_circle.setFill(Color.TRANSPARENT);
        pane.getChildren().add(main_circle);

        // Initializing arrays
        Circle[] points = new Circle[3];
        Line[] lines = new Line[3];
        Text[] texts = new Text[3];

        // This for loop is initializing the three points and the text angles that go with them
        for (int i = 0; i < points.length; i++) {
            texts[i] = new Text();
            points[i] = new Circle(0,0,3);
            points[i].setStroke(Color.BLACK);
            points[i].setFill(Color.RED);

            // The points are being randomly initialized on the circle's radius
            setRandomPoint(points[i], main_circle);

            // This variable is used in the lambda expression since it is final
            final int final_i = i;

            // Here we are using an event handler to move the points around the radius of the circle
            points[i].setOnMouseDragged(e -> {

                // Creating x and y values that are along the radius of the main circle
                double radian = Math.atan2(e.getY() - main_circle.getCenterY(), e.getX() - main_circle.getCenterX());
                double x = main_circle.getCenterX() + main_circle.getRadius() * Math.cos(radian);
                double y = main_circle.getCenterY() + main_circle.getRadius() * Math.sin(radian);

                // Setting the new points on the radius of the circle when the mouse drags the points
                points[final_i].setCenterX(x);
                points[final_i].setCenterY(y);

                // When the point is being moved around the radius of the circle, the lengths of the lines and the angles
                // are continuously being updated
                updateInfo(points, lines , texts);
            });
        }

        // Initializing the lines between the three points
        for (int i = 0; i < lines.length; i++) {
            if (i == 0 || i == 1) {
                lines[i] = new Line(points[i].getCenterX(),points[i].getCenterY(),
                        points[i+1].getCenterX(), points[i+1].getCenterY());
            }
            else if (i == 2) {
                lines[i] = new Line(points[i].getCenterX(),points[i].getCenterY(),
                        points[i-2].getCenterX(), points[i-2].getCenterY());
            }
        }

        // Updating points, lines and angles
        updateInfo(points, lines, texts);

        // Adding to the pane
        pane.getChildren().addAll(points);
        pane.getChildren().addAll(lines);
        pane.getChildren().addAll(texts);

        // Setting the scene
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PointsOnCircle");
        primaryStage.show();
    }

    // A function which will select random points along the radius of the main circle so we can assign random initial
    // values to the three points of the triangle we are creating
    private void setRandomPoint(Circle rand_point, Circle main_circ) {
        double angle = Math.random() * 360;
        double x = main_circ.getCenterX() + main_circ.getRadius() * Math.cos(Math.toRadians(angle));
        double y = main_circ.getCenterY() + main_circ.getRadius() * Math.sin(Math.toRadians(angle));
        rand_point.setCenterX(x);
        rand_point.setCenterY(y);
    }

    // A function which will update the triangle's information after the points are moved
    private void updateInfo(Circle[] points, Line[] lines, Text[] texts) {

        for (int i = 0; i < lines.length; i++) {

            // Setting new coordinates for the angle's texts based on where the point was moved to
            texts[i].setX(points[i].getCenterX() + 4);
            texts[i].setY(points[i].getCenterY() - 4);

            // Setting new start points for the lines
            lines[i].setStartX(points[i].getCenterX());
            lines[i].setStartY(points[i].getCenterY());

            // Setting new end points for the lines
            if (i == 0 || i == 1) {
                lines[i].setEndX(points[i+1].getCenterX());
                lines[i].setEndY(points[i+1].getCenterY());
            }

            else if (i == 2) {
                lines[i].setEndX(points[i-2].getCenterX());
                lines[i].setEndY(points[i-2].getCenterY());
            }
        }


        // Creating variables for each line's distance
        double a = distance(lines[0]);
        double b = distance(lines[1]);
        double c = distance(lines[2]);

        // Calculating the angle between each line and updating the text to the correct angle
        double angle_A = Math.toDegrees(Math.acos((a * a - b * b - c * c) / (-2 * b * c)));
        texts[2].setText(String.format("%.2f", angle_A));

        double angle_B = Math.toDegrees(Math.acos((b * b - a * a - c * c) / (-2 * a * c)));
        texts[0].setText(String.format("%.2f", angle_B));

        double angle_C = Math.toDegrees(Math.acos((c * c - b * b - a * a) / (-2 * a * b)));
        texts[1].setText(String.format("%.2f", angle_C));

    }

    // A function which will find the distance of a line given two points
    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
    }

    // A function which will find the distance of a line given the line
    private double distance(Line lines) {
        return distance(lines.getStartX(), lines.getStartY(), lines.getEndX(), lines.getEndY());
    }
}
