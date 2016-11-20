package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.Event;

import static java.awt.SystemColor.window;

public class Main extends Application implements EventHandler<ActionEvent> {

    Stage window;
    Scene scene;

    private int selectedIndex = -1;

    Image[] listOfImages = {new Image("http://img-9gag-fun.9cache.com/photo/avrorQ5_700b.jpg"),
                            new Image("http://img-9gag-fun.9cache.com/photo/ab6rjPp_700b.jpg"),
                            new Image("http://img-9gag-fun.9cache.com/photo/aPM78wK_460s.jpg"),
                            new Image("http://img-9gag-fun.9cache.com/photo/agqAyK6_460s.jpg"),
                            new Image("http://img-9gag-fun.9cache.com/photo/aNdgPe3_460s_v1.jpg"),
                            new Image("http://img-9gag-fun.9cache.com/photo/avrz6rE_460s.jpg"),
                            new Image("http://img-9gag-fun.9cache.com/photo/aVDwgRd_460s_v1.jpg"),
                            new Image("http://img-9gag-fun.9cache.com/photo/a9WnYKD_460s.jpg")};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        ListView<String> listView = new ListView<String>();
        Button button = new Button("Open");
        ObservableList<String> items = FXCollections.observableArrayList ("Image 0","Image 1","Image 2","Image 3","Image 4","Image 5","Image 6","Image 7");
        listView.setItems(items);

        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedIndex = listView.getSelectionModel().getSelectedIndex();
            }
        });

        button.setOnAction(this);

        VBox box = new VBox();
        box.getChildren().addAll(listView, button);
        box.setAlignment(Pos.CENTER);
        scene = new Scene(box, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void handle(ActionEvent event) {
        if(selectedIndex != -1) {
            Secondary secondary = new Secondary();
            secondary.startStage(listOfImages[selectedIndex], selectedIndex);
        }
    }
}
