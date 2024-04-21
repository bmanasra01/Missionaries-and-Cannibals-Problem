module com.example.ai_pro1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ai_pro1 to javafx.fxml;
    exports com.example.ai_pro1;
}