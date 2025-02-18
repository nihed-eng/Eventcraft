package tn.esprit.services;

import tn.esprit.entities.Salle;
import tn.esprit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleService implements IService<Salle> {
    private Connection conn = DatabaseConnection.getConnection();

    @Override
    public void ajouter(Salle salle) throws SQLException {
        String query = "INSERT INTO salle (nom_salle, capacité, équipement, disponibilité, image_salle, location_salle) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, salle.getNomSalle());
            pst.setInt(2, salle.getCapacite());
            pst.setString(3, salle.getEquipement());
            pst.setBoolean(4, salle.isDisponibilite());
            pst.setString(5, salle.getImageSalle());
            pst.setString(6, salle.getLocationSalle());

            pst.executeUpdate();
        }
    }

    @Override
    public void modifier(Salle salle) throws SQLException {
        String query = "UPDATE salle SET nom_salle = ?, capacité = ?, équipement = ?, disponibilité = ?, image_salle = ?, location_salle = ? WHERE id_salle = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, salle.getNomSalle());
            pst.setInt(2, salle.getCapacite());
            pst.setString(3, salle.getEquipement());
            pst.setBoolean(4, salle.isDisponibilite());
            pst.setString(5, salle.getImageSalle());
            pst.setString(6, salle.getLocationSalle());
            pst.setInt(7, salle.getIdSalle());

            System.out.println("Requête SQL exécutée : " + pst.toString()); // Debugging
            pst.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement("DELETE FROM salle WHERE id_salle = ?")) {
            pst.setInt(1, id);
            System.out.println("Suppression de la salle avec ID : " + id); // Debugging
            pst.executeUpdate();
        }
    }

    @Override
    public List<Salle> afficher() throws SQLException {
        List<Salle> salles = new ArrayList<>();
        String sql = "SELECT * FROM salle";
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                salles.add(new Salle(
                        rs.getInt("id_salle"),
                        rs.getString("nom_salle"),
                        rs.getInt("capacité"),
                        rs.getString("équipement"),
                        rs.getBoolean("disponibilité"),
                        rs.getString("image_salle"),
                        rs.getString("location_salle")
                ));
            }
        }
        return salles;
    }

    @Override
    public Salle getById(int id) throws SQLException {
        String query = "SELECT * FROM salle WHERE id_salle = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Salle(
                            rs.getInt("id_salle"),
                            rs.getString("nom_salle"),
                            rs.getInt("capacité"),
                            rs.getString("équipement"),
                            rs.getBoolean("disponibilité"),
                            rs.getString("image_salle"),
                            rs.getString("location_salle")
                    );
                }
            }
        }
        return null;
    }
}