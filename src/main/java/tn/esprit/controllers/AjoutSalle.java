package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AjoutSalle {

    @FXML
    private TextField titreField; // Champ Titre

    @FXML
    private TextField capaciteField; // Champ Capacité

    @FXML
    private TextField equipementsField; // Champ Équipements

    @FXML
    private TextField localisationField; // Champ Localisation

    @FXML
    private Button browseButton;  // Référence au bouton "Parcourir"

    @FXML
    private ImageView imageView;  // Référence à l'ImageView où l'image sera affichée

    @FXML
    private void handleBrowseAction(ActionEvent event) {
        // Créer une boîte de dialogue pour choisir un fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"));

        // Ouvrir la boîte de dialogue
        Stage stage = (Stage) browseButton.getScene().getWindow(); // Obtenir la fenêtre principale
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Si un fichier est sélectionné, affichez-le dans l'ImageView
            Image image = new Image("file:" + selectedFile.getAbsolutePath());
            imageView.setImage(image); // Mettre l'image dans l'ImageView
        } else {
            // Si aucun fichier n'est sélectionné, afficher une alerte
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune image sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une image.");
            alert.showAndWait();
        }
    }
}
