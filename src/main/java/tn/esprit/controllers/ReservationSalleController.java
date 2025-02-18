package tn.esprit.controllers;

import tn.esprit.services.ReservationSalleService;
import tn.esprit.entities.Reservationsalle;

import java.sql.Date;
import java.sql.SQLException;

public class ReservationSalleController {
    public static void main(String[] args) {
        ReservationSalleService service = new ReservationSalleService();

        // Test Ajout Réservation
        try {
            Reservationsalle reservation = new Reservationsalle(0, 1, 2, Date.valueOf("2025-02-20"));
            service.ajouter(reservation);
            System.out.println("✅ Réservation ajoutée avec succès : " + reservation);
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout : " + e.getMessage());
        }

        // Test Affichage
        try {
            System.out.println("📜 Liste des réservations : " + service.afficher());
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'affichage : " + e.getMessage());
        }

        // Test Modification
        try {
            Reservationsalle reservationAModifier = service.getById(1);
            if (reservationAModifier != null) {
                reservationAModifier.setSalle(3);
                service.modifier(reservationAModifier);
                System.out.println("✅ Réservation modifiée avec succès !");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification : " + e.getMessage());
        }

        // Test Suppression
        try {
            service.supprimer(1);
            System.out.println("✅ Réservation supprimée avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
