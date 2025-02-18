
package tn.esprit.test;

import tn.esprit.entities.Reservationsalle;
import tn.esprit.services.ReservationSalleService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReservationSalleServiceTest {
    public static void main(String[] args) {
        ReservationSalleService service = new ReservationSalleService();

        // Création de quelques réservations pour les tests
        Reservationsalle reservation1 = new Reservationsalle(0, 19, 0, Date.valueOf("2025-03-10")); // Utilise un id_salle valide (1)
        Reservationsalle reservation2 = new Reservationsalle(0, 20, 0, Date.valueOf("2025-04-15")); // Utilise un id_salle valide (2)
        try {
            // Test de l'ajout d'une réservation
            service.ajouter(reservation1);
            System.out.println("Réservation 1 ajoutée avec succès !");

            // Test de l'ajout de la deuxième réservation
            service.ajouter(reservation2);
            System.out.println("Réservation 2 ajoutée avec succès !");

            // Test de la récupération de toutes les réservations
            List<Reservationsalle> reservations = service.afficher();
            if (reservations.isEmpty()) {
                System.out.println("Aucune réservation trouvée.");
            } else {
                System.out.println("Liste des réservations après ajout : ");
                for (Reservationsalle reservation : reservations) {
                    System.out.println("ID Réservation: " + reservation.getIdReservation() +
                            " | Salle: " + reservation.getSalle() +
                            " | Événement: " + reservation.getEvenement() +
                            " | Date: " + reservation.getDateReservation());
                }
            }

            // Test de la récupération d'une réservation par ID
            Reservationsalle retrievedReservation = service.getById(reservation1.getIdReservation());
            if (retrievedReservation != null) {
                System.out.println("Réservation récupérée par ID : " + retrievedReservation.getIdReservation());
            } else {
                System.out.println("Réservation non trouvée avec l'ID : " + reservation1.getIdReservation());
            }

            // Test de la mise à jour d'une réservation
            reservation1.setSalle(5);
            reservation1.setEvenement(6);
            reservation1.setDateReservation(Date.valueOf("2025-05-20"));
            service.modifier(reservation1);
            System.out.println("Réservation 1 mise à jour avec succès !");

            // Vérifier la mise à jour
            Reservationsalle updatedReservation = service.getById(reservation1.getIdReservation());
            if (updatedReservation != null) {
                System.out.println("Réservation après mise à jour : " +
                        "Salle: " + updatedReservation.getSalle() +
                        " | Événement: " + updatedReservation.getEvenement() +
                        " | Date: " + updatedReservation.getDateReservation());
            }

            // Test de la suppression d'une réservation
            service.supprimer(reservation2.getIdReservation());
            System.out.println("Réservation 2 supprimée avec succès !");

            // Vérifier que la réservation a été supprimée
            Reservationsalle deletedReservation = service.getById(reservation2.getIdReservation());
            if (deletedReservation == null) {
                System.out.println("Réservation 2 supprimée avec succès !");
            } else {
                System.out.println("Erreur : Réservation 2 non supprimée.");
            }

            // Tester la récupération de toutes les réservations après suppression
            reservations = service.afficher();
            if (reservations.isEmpty()) {
                System.out.println("Aucune réservation trouvée après suppression.");
            } else {
                System.out.println("Liste des réservations après suppression : ");
                for (Reservationsalle reservation : reservations) {
                    System.out.println("ID Réservation: " + reservation.getIdReservation() +
                            " | Salle: " + reservation.getSalle() +
                            " | Événement: " + reservation.getEvenement() +
                            " | Date: " + reservation.getDateReservation());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}