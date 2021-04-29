module ni.edu.uni.fcys.programacion2.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    opens ni.edu.uni.fcys.programacion2.javafxdemo to javafx.fxml;
    exports ni.edu.uni.fcys.programacion2.javafxdemo;
    requires javafx.mediaEmpty;
}
