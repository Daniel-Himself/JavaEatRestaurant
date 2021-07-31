package View.Controllers;

import Model.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.StyleClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.AccessibleRole;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class SignupPageController {
    @FXML
    private TextField fnameInput;
    @FXML
    private TextField lnameInput;
    @FXML
    private TextField usernameField;
    @FXML
    private ToggleButton toggleHidePassword;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePassField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label passStrengthLbl;
    @FXML
    private ProgressBar passwordStrengthInd;

    private static final String DARKRED_BAR    = "darkred-bar";
    private static final String RED_BAR    = "red-bar";
    private static final String ORANGE_BAR = "orange-bar";
    private static final String YELLOW_BAR = "yellow-bar";
    private static final String GREEN_BAR  = "green-bar";
    private static final String[] barColorStyleClasses = {DARKRED_BAR, RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };

    public void initialize() {
        visiblePassField.textProperty().bind(passwordField.textProperty());
        visiblePassField.visibleProperty().bind(toggleHidePassword.selectedProperty());
        passwordField.visibleProperty().bind(toggleHidePassword.selectedProperty().not());

        passwordStrengthInd.progressProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double progress = newValue == null ? 0 : newValue.doubleValue();
                if (progress < 0.2) {
                    setBarStyleClass(passwordStrengthInd, DARKRED_BAR);
                } else if (progress < 0.4) {
                    setBarStyleClass(passwordStrengthInd, RED_BAR);
                } else if (progress < 0.6) {
                    setBarStyleClass(passwordStrengthInd, ORANGE_BAR);
                } else if(progress < 0.8) {
                    setBarStyleClass(passwordStrengthInd, YELLOW_BAR);
                }
                else {
                    setBarStyleClass(passwordStrengthInd, GREEN_BAR);
                }
            }

            private void setBarStyleClass(ProgressBar bar, String barStyleClass) {
                bar.getStyleClass().removeAll(barColorStyleClasses);
                bar.getStyleClass().add(barStyleClass);
            }
        });
    }

    @FXML
    private void generateUsername(KeyEvent e){
        if(fnameInput.getText().length() > 0 && lnameInput.getText().length() > 0)
            usernameField.setText(fnameInput.getText() + lnameInput.getText() + Customer.getIdCounter()+1);
        else
            usernameField.setText("Enter your full name first");
    }

    /**
     * Determines a strength of a password.
     * The code below uses regex (regular expressions),
     * so here is a brief explanation about each of the commands:
     * [a-z]+ : the sequence has at least 1 character, and it's a lowercase english letter only
     * [a-z]* : the sequence has only lower
     * @param e
     * The key event
     *
     */
    @FXML
    private void determinePasswordStrength(KeyEvent e){
        String password = passwordField.getText();
        if(!password.matches("[A-Za-z0-9!@#$%^&*]*"))
            try {
                passwordField.setText(password.substring(0, password.length() - 1));
            }catch(StringIndexOutOfBoundsException se){
                passwordField.setText("");
            }
        if(password.length() == 0){
            passStrengthLbl.setText("");
            passwordStrengthInd.setProgress(0);
        }
        else if(password.length()<6){
            passStrengthLbl.setText("none");

            passStrengthLbl.setStyle("-fx-text-fill: black");
            passwordStrengthInd.setProgress(0.1);
        }
        else{
            if(password.matches("[0-9]*")) {
                passStrengthLbl.setText("low");
                passStrengthLbl.setStyle("-fx-text-fill: red");
                passwordStrengthInd.setProgress(0.3);
            }
            else{
                if(password.length() <=8){
                    passStrengthLbl.setText("low");
                    passStrengthLbl.setStyle("-fx-text-fill: red");
                    passwordStrengthInd.setProgress(0.3);
                }
                else if(password.length() <= 10){
                    if(password.matches("^[a-zA-Z0-9]+$")) {
                        passStrengthLbl.setText("fair");
                        passStrengthLbl.setStyle("-fx-text-fill: orange");
                        passwordStrengthInd.setProgress(0.45);
                    }
                    else{
                        passStrengthLbl.setText("good");
                        passStrengthLbl.setStyle("-fx-text-fill: yellow");
                        passwordStrengthInd.setProgress(0.65);
                    }
                }
                else{
                    if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).+$")
                    ){
                        passStrengthLbl.setText("strong");
                        passStrengthLbl.setStyle("-fx-text-fill: green");
                        passwordStrengthInd.setProgress(0.85);
                    }
                    else{
                        if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")){
                            passStrengthLbl.setText("very strong");
                            passStrengthLbl.setStyle("-fx-text-fill: darkgreen");
                            passwordStrengthInd.setProgress(0.95);
                        }
                        else{
                            passStrengthLbl.setText("good");
                            passStrengthLbl.setStyle("-fx-text-fill: yellow");
                            passwordStrengthInd.setProgress(0.65);
                        }
                    }
                }
            }
        }
    }
}
