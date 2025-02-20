package org.example.controller;


import SERVICE.SalleService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.example.entities.Salle;

import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public class HelloSalleController {

    @FXML
    private ListView<Salle> listViewSalles;

    private final SalleService salleService = new SalleService();

    @FXML
    public void initialize() {
        loadSallesFromDatabase();
    }

    private void loadSallesFromDatabase() {
        try {
            // Charger les salles depuis la base de données
            List<Salle> sallesFromDB = salleService.afficher();
            ObservableList<Salle> salles = FXCollections.observableArrayList(sallesFromDB);
            listViewSalles.setItems(salles);

            // Définir l'affichage personnalisé des cellules
            listViewSalles.setCellFactory(param -> new ListCell<Salle>() {
                private final VBox vbox = new VBox(5);
                private final Label nameLabel = new Label();
                private final Label detailsLabel = new Label();
                private final HBox buttonBox = new HBox(10);
                private final Button favorisButton = new Button("❤ Favoris");
                private final Button reserverButton = new Button("Réserver");

                {
                    // Définir les dimensions et le style
                    nameLabel.setStyle("-fx-font-weight: bold;");
                    buttonBox.setAlignment(Pos.CENTER);
                    buttonBox.getChildren().addAll(favorisButton, reserverButton);
                    reserverButton.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white;");
                    favorisButton.setStyle("-fx-background-color: #ddd;");
                    vbox.setAlignment(Pos.CENTER);
                    vbox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: #ddd;");
                    vbox.getChildren().addAll(nameLabel, detailsLabel, buttonBox);

                    // Action du bouton "Réserver"
                    reserverButton.setOnAction(event -> {
                        // Récupérer la salle sélectionnée
                        Salle selectedSalle = listViewSalles.getSelectionModel().getSelectedItem();
                        if (selectedSalle != null) {
                            // Ouvrir la fenêtre de réservation
                            openReservationWindow(selectedSalle);
                        }
                    });
                }

                @Override
                protected void updateItem(Salle salle, boolean empty) {
                    super.updateItem(salle, empty);

                    if (empty || salle == null) {
                        setGraphic(null);
                    } else {
                        nameLabel.setText(salle.getNomSalle());
                        detailsLabel.setText("📍 " + salle.getLocationSalle() + "  |  👥 " + salle.getCapacite());

                        // Nettoyage du contenu précédent dans la VBox pour éviter la duplication
                        vbox.getChildren().clear();  // Clear previous content
                        vbox.getChildren().addAll(nameLabel, detailsLabel, buttonBox);  // Ajouter les nouveaux éléments

                        setGraphic(vbox);  // Assigner la VBox à la cellule
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement des salles : " + e.getMessage());
        }
    }

    private void openReservationWindow(Salle salle) {
        try {
            // Charger la scène de réservation depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(".fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la fenêtre de réservation
            HelloReservation controller = loader.getController();
            controller.setSalle(salle);  // Passer la salle sélectionnée au contrôleur de la réservation

            // Créer une nouvelle scène et l'afficher
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Réservation de la Salle");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture de la fenêtre de réservation : " + e.getMessage());
        }
    }
}
