package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

public class MainController {
    @FXML
    private MediaView mainVideo;

    @FXML
    private TextField decimalInput,exponentInput;

    @FXML
    private ImageView logInPic;

    @FXML
    private Label enterLbl,hexOutput,signOutput,combiOutput,exponentOutput,man1Output,man2Output,man3Output,man4Output,man5Output,decimalInvalid,exponentInvalid;

    @FXML
    private Button enterBtn;

    private static final Pattern decimalPattern = Pattern.compile("[0-9]+\\.?[0-9]+");
    private static final Pattern exponentPattern = Pattern.compile("[0-9]+");

    public void initialize(){

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/media/mainVideo.mp4").toExternalForm()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);
        mainVideo.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnReady(()->{

        });

        decimalInput.setOnKeyPressed(event -> {
            decimalInvalid.setOpacity(0.0);
        });

        exponentInput.setOnKeyPressed(event -> {
            exponentInvalid.setOpacity(0.0);
        });

        enterBtn.setOnMouseEntered(event -> {
            enterLbl.setTextFill(Color.web("#323232"));
        });

        enterBtn.setOnMouseExited(event -> {
            enterLbl.setTextFill(Color.web("#FFFFFF"));
        });

        enterBtn.setOnAction(event -> {
            if(decimalPattern.matcher(decimalInput.getCharacters()).matches()){
                System.out.println("DECIMAL CORRECT");
            }else {
                System.out.println("DECIMAL INVALID");
                decimalInvalid.setOpacity(1.0);
            }
            if(exponentPattern.matcher(exponentInput.getCharacters()).matches()){
                System.out.println("EXPONENT CORRECT");
            }else {
                System.out.println("EXPONENT INVALID");
                exponentInvalid.setOpacity(1.0);
            }
        });

    }
}
