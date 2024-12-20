package application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideBar {
    public static ScrollPane getSideBar() {
        VBox sideBar = new VBox();
        sideBar.getStyleClass().add("sidebar");
        sideBar.getStylesheets().add(SideBar.class.getResource("/styleSideBar.css").toExternalForm());

        StackPane imageContainer = new StackPane();
        imageContainer.setPrefSize(180, 180); // Taille préférée du conteneur
        imageContainer.setPadding(new Insets(25)); // Espace autour de l'image
        imageContainer.setStyle("-fx-background-color: #111111;"); // Fond pour test (facultatif)

        imageContainer.getStyleClass().add("separator-bar");

        // Image à l'intérieur du bouton
        Image retourImage = new Image(SideBar.class.getResource("/SpaceJamMain.gif").toExternalForm());
        ImageView retourImageView = new ImageView(retourImage);
        retourImageView.setFitWidth(120); // Ajuster la largeur
        retourImageView.setFitHeight(120); // Ajuster la hauteur
        retourImageView.setPreserveRatio(true); // Préserver le ratio de l'image
        retourImageView.setMouseTransparent(false);


        // Appliquer l'effet de survol sur l'image
        addHoverEffect(retourImageView, 1.5, Color.BLUEVIOLET);

        imageContainer.getChildren().add(retourImageView);
        VBox.setMargin(imageContainer, new Insets(0, 0, 10, 0)); // Ajoute un espace en bas

        // Barre grise sous le bouton
        HBox separatorBar = new HBox();
        separatorBar.setPrefHeight(5); // Hauteur de la barre
        separatorBar.getStyleClass().add("separator-bar");

        String[] links1 = new String[] {"Player bios","shooting hops","lien_ssPlanete3","QTVR Jordan Dome"};
        String[] links2 = new String[] {"lien_ssPlanete1","lien_ssPlanete2","lien_ssPlanete3","lien_ssPlanete4"};
        String[] links3 = new String[] {"production notes","lien_ssPlanete2","lien_ssPlanete3","lien_ssPlanete4"};

        HBox planete1 = createPlanetUnit("/p-bball.gif", links1);
        HBox planete2 = createPlanetUnit("/p-jamcentral.gif", links2);
        HBox planete3 = createPlanetUnit("/p-jump.gif", links2);
        HBox planete4 = createPlanetUnit("/p-behind.gif", links2);
        HBox planete5 = createPlanetUnit("/p-lineup.gif", links2);
        HBox planete6 = createPlanetUnit("/p-studiostore.gif", links2);

        sideBar.getChildren().addAll(imageContainer, separatorBar, planete1, planete2, planete3, planete4, planete5, planete6);
        //return sideBar;

        // Encapsulation dans un `ScrollPane`
        ScrollPane scrollPane = new ScrollPane(sideBar);
        scrollPane.setFitToWidth(true); // Ajuste automatiquement la largeur du contenu au ScrollPane
        scrollPane.setPannable(true);   // Permet le défilement

        // Limitez la largeur et la hauteur du ScrollPane
        scrollPane.setPrefWidth(300); // Largeur souhaitée
        scrollPane.setPrefHeight(600); // Hauteur souhaitée pour activer le défilement
        scrollPane.setStyle("-fx-background: #111111;"); // Arrière-plan cohérent avec le sidebar

        return scrollPane;
    }

    public static HBox createPlanetUnit(String imagePath, String[] links) {
        HBox planetUnit = new HBox();
        planetUnit.setSpacing(20); // Augmente l'espacement entre la planète et les liens
        planetUnit.setAlignment(Pos.CENTER); // Centre les éléments dans l'HBox
        planetUnit.setPadding(new Insets(4, 5, 4, 5)); // Marge sur les cotés de 5pixel

        // Image de la planète
        Image planetImage = new Image(SideBar.class.getResource(imagePath).toExternalForm());
        ImageView planetView = new ImageView(planetImage);
        planetView.setFitWidth(80); // Augmente la taille de l'image
        planetView.setFitHeight(80); // Ajuste la hauteur d l'image
        planetView.setPreserveRatio(true); // Préserve le ratio pour éviter une déformation

        addHoverEffect(planetView, 1.2, Color.WHITE);

        // Liste des liens
        VBox linksBox = new VBox();
        linksBox.setSpacing(5); // Espacement vertical entre les liens
        linksBox.setAlignment(Pos.CENTER_LEFT); // Aligne les liens au centre verticalement
        for (String linkText : links) {
            Hyperlink link = new Hyperlink(linkText);
            link.getStyleClass().add("hyperlink");
            linksBox.getChildren().add(link);
        }

        HBox.setHgrow(linksBox , Priority.NEVER);
        HBox.setMargin(linksBox, new Insets(0, 0, 0, 20));

        planetUnit.getChildren().addAll(planetView, linksBox);
        return planetUnit;
    }

    public static void addHoverEffect(Node node, double scale, Color shadowColor) {
        EventHandler<MouseEvent> mouseEnteredHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                node.setScaleX(scale); // Appliquer l'échelle personnalisée
                node.setScaleY(scale);
                node.setEffect(new DropShadow(20, shadowColor)); // Ombre de couleur personnalisée
            }
        };

        EventHandler<MouseEvent> mouseExitedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                node.setScaleX(1.0); // Réinitialiser la taille
                node.setScaleY(1.0);
                node.setEffect(null); // Supprimer l'effet
            }
        };

        // Ajout des gestionnaires d'événements
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedHandler);
    }
}