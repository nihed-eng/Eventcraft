package SERVICE;


import org.example.entities.Salle;
import utilis.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleService implements IService<Salle> {
    private Connection conn = DatabaseConnection.getConnection();

    @Override
    public void ajouter(Salle salle) throws SQLException {
        String query = "INSERT INTO salle (nom_salle, capacité, équipement, disponibilité, image_salle, location_salle, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, salle.getNomSalle());
            pst.setInt(2, salle.getCapacite());
            pst.setString(3, salle.getEquipement());
            pst.setBoolean(4, salle.isDisponibilite());
            pst.setString(5, salle.getImageSalle());
            pst.setString(6, salle.getLocationSalle());
            pst.setInt(7, salle.getUserId());

            pst.executeUpdate();
        }
    }

    @Override
    public void modifier(Salle salle) throws SQLException {
        String query = "UPDATE salle SET nom_salle = ?, capacité = ?, équipement = ?, disponibilité = ?, image_salle = ?, location_salle = ?, user_id = ? WHERE id_salle = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, salle.getNomSalle());
            pst.setInt(2, salle.getCapacite());
            pst.setString(3, salle.getEquipement());
            pst.setBoolean(4, salle.isDisponibilite());
            pst.setString(5, salle.getImageSalle());
            pst.setString(6, salle.getLocationSalle());
            pst.setInt(7, salle.getUserId());
            pst.setInt(8, salle.getIdSalle());

            pst.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement("DELETE FROM salle WHERE id_salle = ?")) {
            pst.setInt(1, id);
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
                // Récupérer la disponibilité sous forme de chaîne de caractères
                String disponibiliteStr = rs.getString("disponibilité");

                // Convertir la chaîne en boolean
                boolean disponibilite = "Disponible".equalsIgnoreCase(disponibiliteStr);

                // Créer un objet Salle
                salles.add(new Salle(
                        rs.getInt("id_salle"),
                        rs.getString("nom_salle"),
                        rs.getInt("capacité"),
                        rs.getString("équipement"),
                        disponibilite, // Utiliser la valeur convertie
                        rs.getString("image_salle"),
                        rs.getString("location_salle"),
                        rs.getInt("user_id")
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
                    // Récupérer la disponibilité sous forme de chaîne de caractères
                    String disponibiliteStr = rs.getString("disponibilité");

                    // Convertir la chaîne en boolean
                    boolean disponibilite = "Disponible".equalsIgnoreCase(disponibiliteStr);

                    return new Salle(
                            rs.getInt("id_salle"),
                            rs.getString("nom_salle"),
                            rs.getInt("capacité"),
                            rs.getString("équipement"),
                            disponibilite, // Utiliser la valeur convertie
                            rs.getString("image_salle"),
                            rs.getString("location_salle"),
                            rs.getInt("user_id")
                    );
                }
            }
        }
        return null;
    }

    public Salle getByName(String nomSalle) throws SQLException {
        String query = "SELECT * FROM salles WHERE nomSalle = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nomSalle);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Salle(
                        resultSet.getInt("idSalle"),
                        resultSet.getString("nomSalle"),
                        resultSet.getInt("capacite"),
                        resultSet.getString("equipement"),
                        resultSet.getBoolean("disponibilite"),
                        resultSet.getString("imageSalle"),
                        resultSet.getString("locationSalle"),
                        resultSet.getInt("userId")
                );
            }
        }
        return null; // Si aucune salle n'est trouvée
    }
}