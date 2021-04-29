/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.uni.fcys.programacion2.javafxdemo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author UNI
 */
public class FXMLMediaPlayerController implements Initializable {

    @FXML
    private Button btnPlayer;
    @FXML
    private Slider sldPlayer;
    private Slider sldVol;
    @FXML
    private MediaView MVPlayer;

    private MediaPlayer player;
    private Media media;
    @FXML
    private MenuItem mniOPen;
    @FXML
    private MenuItem mniSalir;
    @FXML
    private MenuButton MnBtnVol;
    @FXML
    private MenuItem mniPopupMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sldVol = new Slider();
        sldVol.setMax(100);
        sldVol.setMin(0);
        sldVol.setOrientation(Orientation.VERTICAL);
        mniPopupMenu.setGraphic(createPopupContent());
    }


    protected void updatesValues() {
        Platform.runLater(new Runnable() {
            public void run() {
                // Updating to the new time value 
                // This will move the slider while running your video 
                sldPlayer.setValue(player.getCurrentTime().toMillis()
                        / player.getTotalDuration()
                                .toMillis()
                        * 100
                );
            }
        });
    }

    @FXML
    private void openChooser(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        int answer = chooser.showOpenDialog(null);
        if (answer == JFileChooser.APPROVE_OPTION) {
            File fileMedia = chooser.getSelectedFile();
            media = new Media(fileMedia.toURI().toString());
            player = new MediaPlayer(media);
            MVPlayer.setMediaPlayer(player);
            //player.play();

            player.currentTimeProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    updatesValues();
                }
            });

            sldPlayer.valueProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    if (sldPlayer.isPressed()) { // It would set the time 
                        // as specified by user by pressing 
                        player.seek(player.getMedia().getDuration().multiply(sldPlayer.getValue() / 100));
                    }
                }
            });
            
             sldVol.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (sldVol.isPressed()) {
                    player.setVolume(sldVol.getValue() / 100); // It would set the volume 
                    // as specified by user by pressing 
                }
            }
        });
        }
    }

    @FXML
    private void btnPlayerAction() {
        Status status = player.getStatus(); // To get the status of Player 
        System.out.println(status.toString());
        if (status == status.PLAYING) {

            // If the status is Video playing 
            if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) {

                // If the player is at the end of video 
                player.seek(player.getStartTime()); // Restart the video 
                player.play();
            } else {
                // Pausing the player 
                player.pause();

                btnPlayer.setText("Play");
            }
        } // If the video is stopped, halted or paused 
        if (status == Status.READY || status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) {
            player.play(); // Start the video 
            btnPlayer.setText("Pause");
        }
    }

    @FXML
    private void showPopupPane(ActionEvent event) {
        
    }

    private VBox createPopupContent() {
        final VBox wizBox = new VBox(5);
        wizBox.setAlignment(Pos.CENTER);
        wizBox.getChildren().setAll(                
                sldVol                
        );       
        
        return wizBox;
    }

}
