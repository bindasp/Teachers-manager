module com.example.lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires com.opencsv;

    opens com.example.lab4 to javafx.fxml, org.hibernate.orm.core;
    exports com.example.lab4;
    exports com.example.lab4.models;
    opens com.example.lab4.models to javafx.fxml, org.hibernate.orm.core;
    exports com.example.lab4.backend;
    opens com.example.lab4.backend to javafx.fxml, org.hibernate.orm.core;
    exports com.example.lab4.frontend;
    opens com.example.lab4.frontend to javafx.fxml, org.hibernate.orm.core;
}