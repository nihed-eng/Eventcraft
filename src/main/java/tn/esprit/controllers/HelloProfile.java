package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import tn.esprit.entities.Salle;
import tn.esprit.services.SalleService;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class HelloProfile {

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
    private VBox formulaireVBox;
    private String imagePath; // Variable to store the full image path

    private SalleService salleService = new SalleService();
    private Salle salleEnCours = null;
    @FXML
    private void handleAjouterSalleClick(ActionEvent event) {
        // Afficher ou masquer le formulaire
        boolean isVisible = formulaireVBox.isVisible();
        formulaireVBox.setVisible(!isVisible);  // Inverser la visibilité actuelle
    }


    @FXML
    private void initialize() {
        try {
            // Charger les images depuis le dossier resources/images
            Image image1 = new Image(getClass().getResource("/images/s1.jpg").toExternalForm());
            Image image2 = new Image(getClass().getResource("/images/s2.jpg").toExternalForm());
            Image image3 = new Image(getClass().getResource("/images/s3.jpg").toExternalForm());
            Image image4 = new Image(getClass().getResource("/images/s4.jpg").toExternalForm());


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
        }
    }

    @FXML
    private void handleAjouterOuEditerSalle() {
        String titre = titreField.getText();
        String capaciteText = capaciteField.getText();
        String equipements = equipementsField.getText();
        String localisation = localisationField.getText();
        boolean disponibilite = disponibiliteCheckBox.isSelected();

        if (titre.isEmpty() || capaciteText.isEmpty() || equipements.isEmpty() || imagePath == null || localisation.isEmpty()) {
            showErrorDialog("Tous les champs doivent être remplis.");
            return;
        }

        int capacite;
        try {
            capacite = Integer.parseInt(capaciteText);
        } catch (NumberFormatException e) {
            showErrorDialog("La capacité doit être un nombre valide.");
            return;
        }

        try {
            if (salleEnCours == null) {
                // Mode AJOUT
                Salle nouvelleSalle = new Salle(titre, capacite, equipements, disponibilite, imagePath, localisation);
                salleService.ajouter(nouvelleSalle);
                showSuccessDialog("Salle ajoutée avec succès !");
            } else {
                // Mode MODIFICATION
                salleEnCours.setNomSalle(titre);
                salleEnCours.setCapacite(capacite);
                salleEnCours.setEquipement(equipements);
                salleEnCours.setLocationSalle(localisation);
                salleEnCours.setDisponibilite(disponibilite);
                salleEnCours.setImageSalle(imagePath);

                salleService.modifier(salleEnCours);
                showSuccessDialog("Salle modifiée avec succès !");
                salleEnCours = null; // Réinitialiser après modification
            }

            resetFormFields();
            loadSallesInListView(); // Mettre à jour l'affichage
        } catch (SQLException e) {
            showErrorDialog("Erreur lors de l'opération : " + e.getMessage());
        }
    }

    @FXML
    private void handleEditSalle(Salle salle) {
        salleEnCours = salle; // On sauvegarde la salle en cours d'édition

        titreField.setText(salle.getNomSalle());
        capaciteField.setText(String.valueOf(salle.getCapacite()));
        equipementsField.setText(salle.getEquipement());
        localisationField.setText(salle.getLocationSalle());
        disponibiliteCheckBox.setSelected(salle.isDisponibilite());
        imagePath = salle.getImageSalle();
        imageField.setText(new File(salle.getImageSalle()).getName());

        formulaireVBox.setVisible(true);
    }



    private void resetFormFields() {
        titreField.clear();
        capaciteField.clear();
        equipementsField.clear();
        localisationField.clear();
        disponibiliteCheckBox.setSelected(false);
        imageField.clear();
        imagePath = null;
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
    } @FXML
    private ListView<VBox> listViewSalles;  // ListView pour afficher les salles


    @FXML
    private void handleAfficherSallesClick() {
        loadSallesInListView();
    }
    @FXML

    private void handleDeleteSalle(Salle salle, VBox salleVBox) {
        // Demander confirmation avant de supprimer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette salle ?");
        alert.setContentText("Cette action est irréversible.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                // Supprimer la salle de la base de données via le service
                salleService.supprimer(salle.getIdSalle());  // Passer l'ID de la salle à supprimer

                // Supprimer la salle de la ListView
                listViewSalles.getItems().remove(salleVBox);

                // Afficher un message de succès
                showSuccessDialog("Salle supprimée avec succès !");
            } catch (SQLException e) {
                // En cas d'erreur dans la suppression
                showErrorDialog("Erreur lors de la suppression de la salle : " + e.getMessage());
            }
        }
    }


    private void loadSallesInListView() {
        try {
            // Récupérer la liste des salles depuis la base de données
            List<Salle> salles = salleService.afficher();

            // Clear la ListView avant d'ajouter de nouveaux éléments
            listViewSalles.getItems().clear();

            // Ajouter chaque salle à la ListView
            for (Salle salle : salles) {
                VBox salleVBox = createSalleVBox(salle);
                listViewSalles.getItems().add(salleVBox);
            }
        } catch (SQLException e) {
            // Gestion d'erreur si problème avec la base de données
            e.printStackTrace();
        }
    }

    private VBox createSalleVBox(Salle salle) {
        VBox salleVBox = new VBox(5);
        salleVBox.setStyle("-fx-border-color: #8D598F; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");

        // ImageView pour afficher l'image de la salle
        ImageView imageView = new ImageView();
        Image image = new Image(new File(salle.getImageSalle()).toURI().toString());
        imageView.setImage(image);
        imageView.setFitHeight(112);
        imageView.setFitWidth(213);
        imageView.setPreserveRatio(true);

        // Label pour le nom de la salle
        Label nomSalleLabel = new Label(salle.getNomSalle());
        nomSalleLabel.setStyle("-fx-font-weight: bold;");

        // Label pour la localisation
        Label localisationLabel = new Label(salle.getLocationSalle());

        // Label pour la capacité
        Label capaciteLabel = new Label("Capacité: " + salle.getCapacite() + " personnes");

        // HBox pour les boutons
        HBox buttonBox = new HBox(10);
        Button editButton = new Button("Editer");
        Button deleteButton = new Button("Supprimer");

        // Style des boutons
        editButton.setStyle("-fx-background-color: #8D598F; -fx-text-fill: #FFFFFF;");
        deleteButton.setStyle("-fx-background-color: #8D598F; -fx-text-fill: #FFFFFF;");

        // Ajouter l'événement de suppression au bouton "Supprimer"
        deleteButton.setOnAction(event -> handleDeleteSalle(salle, salleVBox));

        // Ajouter les boutons à l'HBox
        buttonBox.getChildren().addAll(editButton, deleteButton);

        // Ajouter tous les éléments au VBox
        salleVBox.getChildren().addAll(imageView, nomSalleLabel, localisationLabel, capaciteLabel, buttonBox);
        editButton.setOnAction(event -> handleEditSalle(salle));

        return salleVBox;
    }

}
