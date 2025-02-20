package org.example.controller;


import SERVICE.ReservationSalleService;
import org.example.entities.Reservationsalle;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReservationSalleController {

    private ReservationSalleService reservationService;

    public ReservationSalleController() {
        this.reservationService = new ReservationSalleService();
    }

    // Ajouter une nouvelle réservation
    public void ajouterReservation(int salle, int user_id, Date date_debut, Date date_fin) {
        Reservationsalle reservation = new Reservationsalle(0, salle,  user_id, date_debut, date_fin);
        try {
            reservationService.ajouter(reservation);
            System.out.println("Réservation ajoutée avec succès : " + reservation);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la réservation.");
        }
    }

    // Modifier une réservation existante
    public void modifierReservation(int id_reservation, int salle, int user_id, Date date_debut, Date date_fin) {
        Reservationsalle reservation = new Reservationsalle(id_reservation, salle, user_id, date_debut, date_fin);
        try {
            reservationService.modifier(reservation);
            System.out.println("Réservation modifiée avec succès : " + reservation);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de la réservation.");
        }
    }

    // Supprimer une réservation par son ID
    public void supprimerReservation(int id_reservation) {
        try {
            reservationService.supprimer(id_reservation);
            System.out.println("Réservation supprimée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de la réservation.");
        }
    }

    // Afficher toutes les réservations
    public void afficherToutesReservations() {
        try {
            List<Reservationsalle> reservations = reservationService.afficher();
            for (Reservationsalle reservation : reservations) {
                System.out.println(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'affichage des réservations.");
        }
    }

    // Obtenir une réservation par son ID
    public void afficherReservationParId(int id_reservation) {
        try {
            Reservationsalle reservation = reservationService.getById(id_reservation);
            if (reservation != null) {
                System.out.println(reservation);
            } else {
                System.out.println("Réservation introuvable avec l'ID : " + id_reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche de la réservation.");
        }
    }
}
