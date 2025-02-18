package tn.esprit.test;

import tn.esprit.entities.Salle;
import tn.esprit.entities.Reservationsalle;
import tn.esprit.services.SalleService;
import tn.esprit.services.ReservationSalleService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class SalleServiceTest {
    public static void main(String[] args) {
        SalleService salleService = new SalleService();
        ReservationSalleService reservationService = new ReservationSalleService();

        try {
            // Ajout de salles pour tester les réservations
            Salle salle1 = new Salle(0, "Salle A", 50, "Projecteur, WiFi", true, "salleA.jpg", "Tunis");
            Salle salle2 = new Salle(0, "Salle B", 100, "Micro, WiFi", true, "salleB.jpg", "Sousse");

            salleService.ajouter(salle1);
            salleService.ajouter(salle2);
            System.out.println("Salles ajoutées avec succès !");

            // Récupération des salles pour s'assurer qu'elles existent
            List<Salle> salles = salleService.afficher();
            if (salles.isEmpty()) {
                System.out.println("Aucune salle trouvée !");
                return;
            }

            // Récupérer les ID des salles ajoutées
            int salleId1 = salles.get(0).getIdSalle();
            int salleId2 = salles.get(1).getIdSalle();

            // Création de réservations avec des ID de salles valides
            Reservationsalle reservation1 = new Reservationsalle(0, salleId1, 2, Date.valueOf("2025-03-10"));
            Reservationsalle reservation2 = new Reservationsalle(0, salleId2, 4, Date.valueOf("2025-04-15"));

            // Ajout des réservations
            reservationService.ajouter(reservation1);
            reservationService.ajouter(reservation2);
            System.out.println("Réservations ajoutées avec succès !");

            // Affichage des réservations
            List<Reservationsalle> reservations = reservationService.afficher();
            System.out.println("Liste des réservations : ");
            for (Reservationsalle res : reservations) {
                System.out.println(res);
            }

            // Mise à jour d'une réservation
            reservation1.setSalle(salleId2); // On change la salle de la réservation 1
            reservationService.modifier(reservation1);
            System.out.println("Réservation mise à jour avec succès !");

            // Suppression d'une réservation
            reservationService.supprimer(reservation2.getIdReservation());
            System.out.println("Réservation supprimée avec succès !");

            // Affichage final après suppression
            reservations = reservationService.afficher();
            System.out.println("Liste des réservations après suppression : ");
            for (Reservationsalle res : reservations) {
                System.out.println(res);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
