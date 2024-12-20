package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LunarTunesPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lunar Tunes - Space Jam");

        // Création d'un BorderPane pour contenir la sidebar et le contenu principal
        BorderPane root = new BorderPane();

        // Ajout de la SideBar sur le côté gauche
        ScrollPane sideBar = SideBar.getSideBar();
        root.setLeft(sideBar);

        // Conteneur principal pour le contenu de la page
        StackPane mainContent = new StackPane();

        // Arrière-plan étoilé
        Image backgroundImage = new Image(getClass().getResource("/application/img/stars-1837306_1280.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600);
        mainContent.getChildren().add(backgroundView);

        // VBox pour l'image principale et le texte
        VBox vbox = new VBox(10); // Espacement de 10px entre les éléments
        vbox.setAlignment(javafx.geometry.Pos.CENTER); // Centrer les éléments horizontalement et verticalement

        // Image principale (comme "Lunar Tunes")
        Image mainImage = new Image(getClass().getResource("/application/img/lunar_t.jpg").toExternalForm());
        ImageView mainImageView = new ImageView(mainImage);
        mainImageView.setPreserveRatio(true);
        mainImageView.setFitWidth(400); // Taille fixe pour l'image
        vbox.getChildren().add(mainImageView);

        // Texte descriptif sous l'image
        Text descriptionText = new Text(
                "When you've got the world's greatest basketball superstars and the world's greatest cartoon characters\n" +
                        "facing off against space aliens, you can't skimp on the jams -- and sure enough, Warner Sunset/Atlantic Records'\n" +
                        "Space Jam: Music From The Motion Picture is one of the most powerful collections in the universe."
        );
        descriptionText.setFont(Font.font("Arial", 14));
        descriptionText.setFill(Color.WHITE);
        descriptionText.setWrappingWidth(600); // Limite la largeur du texte
        vbox.getChildren().add(descriptionText);

        // Conteneur pour tout
        Pane contentPane = new Pane();
        contentPane.getChildren().add(vbox);

        // Boutons invisibles sur les zones spécifiques
        Button airWavesButton = new Button();
        airWavesButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        airWavesButton.setPrefSize(100, 50);
        airWavesButton.setLayoutX(200);
        airWavesButton.setLayoutY(150);
        airWavesButton.setOnMouseEntered(event -> airWavesButton.setStyle("-fx-background-color: transparent; -fx-border-color: cyan; -fx-border-width: 2px;"));
        airWavesButton.setOnMouseExited(event -> airWavesButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"));

        Button soundtrackButton = new Button();
        soundtrackButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        soundtrackButton.setPrefSize(120, 50);
        soundtrackButton.setLayoutX(400);
        soundtrackButton.setLayoutY(200);
        soundtrackButton.setOnMouseEntered(event -> soundtrackButton.setStyle("-fx-background-color: transparent; -fx-border-color: cyan; -fx-border-width: 2px;"));
        soundtrackButton.setOnMouseExited(event -> soundtrackButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"));

        contentPane.getChildren().addAll(airWavesButton, soundtrackButton);

        // Crédits en bas de la page
        Text creditsText = new Text("© 1996 Warner Bros. All Rights Reserved.");
        creditsText.setFont(Font.font("Arial", 12));
        creditsText.setFill(Color.WHITE);
        creditsText.setLayoutX(300);
        creditsText.setLayoutY(580);
        contentPane.getChildren().add(creditsText);

        // Ajouter la VBox au centre
        mainContent.getChildren().addAll(contentPane);

        // Ajout du contenu principal au centre du BorderPane
        root.setCenter(mainContent);

        // Créer la scène
        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
