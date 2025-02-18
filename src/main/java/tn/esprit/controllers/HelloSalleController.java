package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.entities.Salle;
import tn.esprit.services.SalleService;

import java.io.File;
import java.sql.SQLException;

public class HelloSalleController {

    @FXML
    private TextField titreField;

    @FXML
    private TextField capaciteField;

    @FXML
    private TextArea equipementsField;

    @FXML
    private TextField localisationField;

    @FXML
    private CheckBox disponibiliteCheckBox;

    @FXML
    private TextField imageField; // TextField to display the image file name

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;

    private String imagePath; // Variable to store the full image path

    private SalleService salleService = new SalleService();

    @FXML
    private void initialize() {
        try {
            // Charger les images depuis le dossier resources/images
            Image image1 = new Image(getClass().getResource("images/s1.jpg").toExternalForm());
            Image image2 = new Image(getClass().getResource("images/s2.jpg").toExternalForm());
            Image image3 = new Image(getClass().getResource("images/s3.jpg").toExternalForm());
            Image image4 = new Image(getClass().getResource("images/s4.jpg").toExternalForm());

            imageView1.setImage(image1);
            imageView2.setImage(image2);
            imageView3.setImage(image3);
            imageView4.setImage(image4);

            System.out.println("Images chargées avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur de chargement des images : " + e.getMessage());
            e.printStackTrace(); // Afficher la stack trace pour plus de détails
        }
    }

    @FXML
    private void handleBrowseAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        // Open the file chooser dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            // Get the file name (without the full path)
            String fileName = file.getName();

            // Update the TextField with the file name
            imageField.setText(fileName);

            // Store the full image path for later use (e.g., saving to the database)
            imagePath = file.getAbsolutePath();

            // Afficher l'image sélectionnée dans l'ImageView
            Image image = new Image(file.toURI().toString());
            imageView1.setImage(image); // Vous pouvez choisir l'ImageView à mettre à jour
        }
    }

    @FXML
    private void handleAjouterAction() {
        String titre = titreField.getText();
        int capacite = Integer.parseInt(capaciteField.getText());
        String equipements = equipementsField.getText();
        String localisation = localisationField.getText();
        boolean disponibilite = disponibiliteCheckBox.isSelected();

        if (titre.isEmpty() || capaciteField.getText().isEmpty() || equipements.isEmpty() || imagePath == null || localisation.isEmpty()) {
            showErrorDialog("Tous les champs doivent être remplis.");
            return;
        }

        // Create a new Salle object
        Salle salle = new Salle(titre, capacite, equipements, disponibilite, imagePath, localisation);

        try {
            // Save the Salle object to the database
            salleService.ajouter(salle);
            showSuccessDialog("Salle ajoutée avec succès !");
        } catch (SQLException e) {
            showErrorDialog("Erreur lors de l'ajout de la salle : " + e.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}