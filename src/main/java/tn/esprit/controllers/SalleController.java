package tn.esprit.controllers;

import tn.esprit.services.SalleService;
import tn.esprit.entities.Salle;
import java.sql.SQLException;
import java.util.List;

public class SalleController {
    private SalleService salleService = new SalleService();

    public void afficherSalles() {
        try {
            List<Salle> salles = salleService.afficher();
            for (Salle s : salles) {
                System.out.println("Salle: " + s.getNomSalle() + ", Capacité: " + s.getCapacite());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
