package hospital.hospital_management_system2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainAppController {
    @FXML
    private Label welcomeText;
    @FXML private BorderPane mainPane;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML public void showDepartment(){
        loadView("DepartmentView.fxml");
    }
    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hospital/hospital_management_system2/" + fxmlFile));
            Parent view = loader.load();
            if (mainPane != null) {
                mainPane.setCenter(view);
            } else {
                System.err.println("mainPane is null!");
            }
        } catch (IOException e) {
            System.err.println("Error loading view: " + fxmlFile);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
