package tn.esprit.entities;

import java.sql.Date;

public class Reservationsalle {
    private int idReservation;
    private int salle; // Stocke uniquement l'ID de la salle
    private int evenement; // Stocke uniquement l'ID de l'événement
    private Date dateReservation;

    // Constructeur par défaut
    public Reservationsalle() {}

    // Constructeur avec paramètres
    public Reservationsalle(int idReservation, int salle, int evenement, Date dateReservation) {
        this.idReservation = idReservation;
        this.salle = salle;
        this.evenement = evenement;
        this.dateReservation = dateReservation;
    }

    // Getters
    public int getIdReservation() {
        return idReservation;
    }

    public int getSalle() {
        return salle;
    }

    public int getEvenement() {
        return evenement;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    // Setters
    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public void setSalle(int salle) {
        this.salle = salle;
    }

    public void setEvenement(int evenement) {
        this.evenement = evenement;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    @Override
    public String toString() {
        return "Reservationsalle{" +
                "idReservation=" + idReservation +
                ", salle=" + salle +
                ", evenement=" + evenement +
                ", dateReservation=" + dateReservation +
                '}';
    }
}
