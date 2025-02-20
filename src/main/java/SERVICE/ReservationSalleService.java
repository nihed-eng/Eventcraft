package SERVICE;


import org.example.entities.Reservationsalle;
import utilis.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationSalleService implements IService<Reservationsalle> {
    private Connection connection = DatabaseConnection.getConnection();


    @Override
    public void ajouter(Reservationsalle reservation) throws SQLException {
        String query = "INSERT INTO reservation (salle, user_id, date_debut, date_fin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, reservation.getSalle());  // Paramètre 1 : salle
            pst.setInt(2, reservation.getUser_id()); // Paramètre 2 : user_id
            pst.setDate(3, reservation.getDate_debut()); // Paramètre 3 : date_debut
            pst.setDate(4, reservation.getDate_fin());  // Paramètre 4 : date_fin
            pst.executeUpdate();

            // Récupérer l'ID généré
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId_reservation(generatedKeys.getInt(1));
                }
            }
        }
    }


    @Override
    public void modifier(Reservationsalle reservation) throws SQLException {
        String query = "UPDATE reservation SET salle = ?, user_id = ?, date_debut = ?, date_fin = ? WHERE id_reservation = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, reservation.getSalle());  // Paramètre 1 : salle
            pst.setInt(2, reservation.getUser_id()); // Paramètre 2 : user_id
            pst.setDate(3, reservation.getDate_debut()); // Paramètre 3 : date_debut
            pst.setDate(4, reservation.getDate_fin());  // Paramètre 4 : date_fin
            pst.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM reservation WHERE id_reservation = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }

    @Override
    public List<Reservationsalle> afficher() throws SQLException {
        List<Reservationsalle> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                reservations.add(new Reservationsalle(
                        rs.getInt("id_reservation"),
                        rs.getInt("salle"),
                        rs.getInt("user_id"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin")
                ));
            }
        }
        return reservations;
    }

    @Override
    public Reservationsalle getById(int id) throws SQLException {
        String query = "SELECT * FROM reservation WHERE id_reservation = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Reservationsalle(
                            rs.getInt("id_reservation"),
                            rs.getInt("salle"),
                            rs.getInt("user_id"),
                            rs.getDate("date_debut"),
                            rs.getDate("date_fin")
                    );
                }
            }
        }
        return null;
    }


    public List<Reservationsalle> getReservationsByUserId(int userId) throws SQLException {
        List<Reservationsalle> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation WHERE user_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    reservations.add(new Reservationsalle(
                            rs.getInt("id_reservation"),
                            rs.getInt("salle"),
                            rs.getInt("user_id"),
                            rs.getDate("date_debut"),
                            rs.getDate("date_fin")
                    ));
                }
            }
        }
        return reservations;
    }

}

