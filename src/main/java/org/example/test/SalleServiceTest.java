package org.example.test;

import SERVICE.ReservationSalleService;
import SERVICE.SalleService;
import org.example.entities.Salle;

import java.sql.SQLException;
import java.util.List;

    public class SalleServiceTest {
        public static void main(String[] args) {
            SalleService salleService = new SalleService();
            ReservationSalleService reservationService = new ReservationSalleService();

            try {
                // Ajout de salles pour tester les réservations
                // Assurez-vous de spécifier un userId valide pour chaque salle (par exemple, 1 et 2)
                Salle salle1 = new Salle(0, "Salle A", 50, "Projecteur, WiFi", true, "salleA.jpg", "Tunis", 1); // userId = 1
                Salle salle2 = new Salle(0, "Salle B", 100, "Micro, WiFi", true, "salleB.jpg", "Sousse", 1); // userId = 2

                salleService.ajouter(salle1);
                salleService.ajouter(salle2);
                System.out.println("Salles ajoutées avec succès !");

                // Récupération des salles pour s'assurer qu'elles existent
                List<Salle> salles = salleService.afficher();
                if (salles.isEmpty()) {
                    System.out.println("Aucune salle trouvée !");
                    return;
                }




            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

