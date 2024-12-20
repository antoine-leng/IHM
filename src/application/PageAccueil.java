package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PageAccueil extends Application {

    private int currentStep = 0; // Étape actuelle du guide
    private StackPane overlayPane; // Superposition pour les explications
    private Text explanationText; // Texte explicatif de la superposition
    private Runnable[] planetActions; // Actions des planètes

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Space Jam Orbital Planets");

        // Conteneur principal
        Pane root = new Pane();

        // Charger la page d'accueil avec les planètes
        loadMainPage(root, primaryStage);

        // Ajouter la superposition explicative
        overlayPane = createOverlayPane(root);

        // Bouton pour revoir les instructions
        Button helpButton = new Button("Help");
        helpButton.setFont(Font.font("Arial", 14));
        helpButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #007acc, #003d66);" +
                "-fx-text-fill: white;" +
                "-fx-border-color: #003d66;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 5px 15px;"
        );
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentStep = 0;
                explanationText.setText(getExplanationForStep(currentStep));
                overlayPane.setVisible(true);
            }
        });
        helpButton.setLayoutX(20);
        helpButton.setLayoutY(20);
        root.getChildren().add(helpButton);

        // Conteneur principal avec superposition
        StackPane mainPane = new StackPane(root, overlayPane);

        // Créer la scène
        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadMainPage(Pane root, Stage primaryStage) {
        // Arrière-plan
        Image backgroundImage = new Image(getClass().getResource("/application/img/stars-1837306_1280.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false);
        backgroundView.fitWidthProperty().bind(root.widthProperty());
        backgroundView.fitHeightProperty().bind(root.heightProperty());
        root.getChildren().add(backgroundView);

        // Planète centrale
        Image mainImage = new Image(getClass().getResource("/application/img/SpaceJamMain.gif").toExternalForm());
        ImageView mainImageView = new ImageView(mainImage);
        mainImageView.setPreserveRatio(true);
        mainImageView.fitWidthProperty().bind(root.widthProperty().multiply(0.27));
        mainImageView.fitHeightProperty().bind(root.heightProperty().multiply(0.27));
        mainImageView.layoutXProperty().bind(root.widthProperty().divide(2).subtract(mainImageView.fitWidthProperty().divide(2)));
        mainImageView.layoutYProperty().bind(root.heightProperty().divide(2).subtract(mainImageView.fitHeightProperty().divide(2)));
        root.getChildren().add(mainImageView);

        // Planètes en orbite
        String[] planetImages = {
                "/application/img/p-jamcentral.gif",
                "/application/img/p-bball.gif",
                "/application/img/p-lunartunes.gif",
                "/application/img/p-junior.gif",
                "/application/img/p-lineup.gif",
                "/application/img/p-souvenirs.gif",
                "/application/img/p-jump.gif",
                "/application/img/p-behind.gif",
                "/application/img/p-pressbox.gif",
                "/application/img/p-studiostore.gif",
                "/application/img/p-sitemap.gif"
        };

        planetActions = new Runnable[11];
        planetActions[2] = new Runnable() {
            public void run() {
                loadLunarTunesPage(primaryStage);
            }
        };

        double baseRadius = 0.3;
        for (int i = 0; i < planetImages.length; i++) {
            double angle = 2 * Math.PI / planetImages.length * i;

            ImageView planetImageView = createPlanet(planetImages[i]);
            planetImageView.fitWidthProperty().bind(root.widthProperty().multiply(0.12));
            planetImageView.fitHeightProperty().bind(root.heightProperty().multiply(0.12));
            planetImageView.layoutXProperty().bind(
                    root.widthProperty().divide(2).add(root.widthProperty().multiply(baseRadius).multiply(Math.cos(angle))).subtract(planetImageView.fitWidthProperty().divide(2))
            );
            planetImageView.layoutYProperty().bind(
                    root.heightProperty().divide(2).add(root.heightProperty().multiply(baseRadius).multiply(Math.sin(angle))).subtract(planetImageView.fitHeightProperty().divide(2))
            );

            Tooltip planetTooltip = new Tooltip("Planet Tooltip!");
            Tooltip.install(planetImageView, planetTooltip);

            DropShadow glowEffect = new DropShadow();
            glowEffect.setColor(Color.CYAN);
            glowEffect.setRadius(20);
            glowEffect.setSpread(0.3);

            final int index = i;
            planetImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    planetImageView.setEffect(glowEffect);
                    planetImageView.setScaleX(1.1);
                    planetImageView.setScaleY(1.1);
                }
            });

            planetImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    planetImageView.setEffect(null);
                    planetImageView.setScaleX(1.0);
                    planetImageView.setScaleY(1.0);
                }
            });

            planetImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (planetActions[index] != null) {
                        planetActions[index].run();
                    }
                }
            });

            root.getChildren().add(planetImageView);
        }
    }

    private void loadLunarTunesPage(Stage primaryStage) {
        LunarTunesPage lunarTunesPage = new LunarTunesPage();
        lunarTunesPage.start(primaryStage);
    }

    private StackPane createOverlayPane(Pane root) {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #001f3f, #00102f);" +
                "-fx-border-color: #00ffff;" +
                "-fx-border-width: 3px;" +
                "-fx-padding: 20px;" +
                "-fx-background-radius: 10px;" +
                "-fx-border-radius: 10px;"
        );
        contentBox.setPrefSize(560, 420);

        explanationText = new Text(getExplanationForStep(currentStep));
        explanationText.setFont(Font.font("Arial", 18));
        explanationText.setFill(Color.CYAN);
        explanationText.setTextAlignment(TextAlignment.CENTER);
        explanationText.setWrappingWidth(500);

        DropShadow glowEffect = new DropShadow();
        glowEffect.setColor(Color.CYAN);
        glowEffect.setRadius(20);
        glowEffect.setSpread(0.3);
        contentBox.setEffect(glowEffect);

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Arial", 14));
        backButton.setDisable(true);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentStep > 0) {
                    currentStep--;
                    explanationText.setText(getExplanationForStep(currentStep));
                }
                if (currentStep == 0) {
                    backButton.setDisable(true);
                }
            }
        });

        Button nextButton = new Button("Next");
        nextButton.setFont(Font.font("Arial", 14));
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentStep++;
                explanationText.setText(getExplanationForStep(currentStep));
                backButton.setDisable(false);
                if (currentStep >= 3) {
                    overlay.setVisible(false);
                }
            }
        });

        HBox navigationButtons = new HBox(10, backButton, nextButton);
        navigationButtons.setAlignment(Pos.CENTER);

        Button skipButton = new Button("Skip");
        skipButton.setFont(Font.font("Arial", 14));
        skipButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff4500, #ff6347);" +
                "-fx-text-fill: white;" +
                "-fx-border-color: #ff6347;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 5px 15px;"
        );
        skipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                overlay.setVisible(false);
            }
        });

        VBox buttonsBox = new VBox(10, navigationButtons, skipButton);
        buttonsBox.setAlignment(Pos.CENTER);

        contentBox.getChildren().addAll(explanationText, buttonsBox);
        overlay.getChildren().add(contentBox);
        return overlay;
    }

    private String getExplanationForStep(int step) {
        switch (step) {
            case 0:
                return "Welcome to Space Jam Orbital Planets!\nExplore the planets representing sections of the site.";
            case 1:
                return "Hover over a planet to see its description.\nClick to explore the section.";
            case 2:
                return "Navigate deeper into each section.\nEnjoy your journey!";
            default:
                return "";
        }
    }

    private ImageView createPlanet(String imagePath) {
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
