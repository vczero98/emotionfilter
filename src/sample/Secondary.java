package sample;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by barabasicsongor on 20/11/2016.
 */
public class Secondary {

    int index;

    public void startStage(Image image, int selectedIndex) {
        index = selectedIndex;

        Stage stage = new Stage();
        VBox bx = new VBox();
        ImageView img = new ImageView(image);
        img.fitHeightProperty();
        img.fitWidthProperty();

        Scene scene = new Scene(bx,700,600);
        bx.getChildren().add(img);

        stage.setScene(scene);
        stage.show();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        try {
                            Camera.startCamera(index);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                100
        );
    }

}
