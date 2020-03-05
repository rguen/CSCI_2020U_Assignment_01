import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class InvestmentValueCalculator extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        LoanInvestCalculator calcPane = new LoanInvestCalculator();

        primaryStage.setScene(new Scene(calcPane, calcPane.getPrefWidth(), calcPane.getPrefHeight()));
        primaryStage.setTitle("Investment-Value Calculator");
        primaryStage.show();
    }

    private class LoanInvestCalculator extends GridPane {

        //label of the first text field
        Label amountLabel = new Label("Investment Amount");
        TextField amountText = new TextField();

        //label of the second text field
        Label yearsLabel = new Label("Years");
        TextField yearsText = new TextField();

        //label of the third text field
        Label rateLabel = new Label("Annual Interest Rate");
        TextField rateText = new TextField();

        //label of the fourth text field
        Label futureValueLabel = new Label("Future Value");
        TextField futureValueText = new TextField();

        //setting up a calculate button
        Button btCalc = new Button("Calculate");

        private LoanInvestCalculator() {

            //create a calculator layout
            setPadding(new Insets(10, 10, 10, 10));
            setAlignment(Pos.CENTER);
            setHgap(10);
            setVgap(10);

            add(amountLabel, 0, 0);
            add(amountText, 1, 0);

            add(yearsLabel, 0, 1);
            add(yearsText, 1, 1);

            add(rateLabel, 0, 2);
            add(rateText, 1, 2);

            add(futureValueLabel, 0, 3);
            add(futureValueText, 1, 3);

            HBox buttons = new HBox();
            buttons.getChildren().add(btCalc);
            buttons.setAlignment(Pos.BOTTOM_RIGHT);//button position
            add(buttons, 1, 4);
            btCalc.setOnAction(e-> calcFutureValue());

            // text field settings
            TextField[] textFields = (TextField[])getArray(
                    amountText, yearsText, rateText, futureValueText);

            for (TextField tf : textFields) {
                tf.setAlignment(Pos.BASELINE_RIGHT);//set alignment of text fields to right
            }
            // to disable the ability to write in the last text field
            futureValueText.setDisable(true);
        }

        private Object[] getArray(Object... objects) {
            Object[] temp = new TextField[objects.length];
            for (int i = 0; i < objects.length; i++) {
                temp[i] = objects[i];
            }
            return temp;
        }

        // function to calculate the future value
        public void calcFutureValue() {
            double investmentAmount = Double.parseDouble(amountText.getText());
            double years = Double.parseDouble(yearsText.getText());
            double monthInterestRate = Double.parseDouble(rateText.getText()) / 12 / 100;

            double futureValue = investmentAmount * Math.pow(1 + monthInterestRate, years * 12);
            // translate the calculated value to a text
            futureValueText.setText(String.format("$%.2f", futureValue));
        }
    }
}
