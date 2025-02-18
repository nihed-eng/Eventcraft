package tn.esprit.services;

import tn.esprit.entities.Reservationsalle;
import tn.esprit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationSalleService implements IService<Reservationsalle> {
    private Connection connection = DatabaseConnection.getConnection();

    @Override
    public void ajouter(Reservationsalle reservation) throws SQLException {
        String query = "INSERT INTO reservation (salle, evenement, date_reservation) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, reservation.getSalle());
            pst.setInt(2, reservation.getEvenement());
            pst.setDate(3, reservation.getDateReservation());
            pst.executeUpdate();

            // Récupérer l'ID généré
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setIdReservation(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void modifier(Reservationsalle reservation) throws SQLException {
        String query = "UPDATE reservation SET salle = ?, evenement = ?, date_reservation = ? WHERE id_reservation = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, reservation.getSalle());
            pst.setInt(2, reservation.getEvenement());
            pst.setDate(3, reservation.getDateReservation());
            pst.setInt(4, reservation.getIdReservation());
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
                        rs.getInt("evenement"),
                        rs.getDate("date_reservation")
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
                            rs.getInt("evenement"),
                            rs.getDate("date_reservation")
                    );
                }
            }
        }
        return null;
    }
}
