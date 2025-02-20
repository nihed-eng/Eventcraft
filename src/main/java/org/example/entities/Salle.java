package org.example.entities;


public class Salle {
    private int idSalle;
    private String nomSalle;
    private int capacite;
    private String equipement;
    private boolean disponibilite;
    private String imageSalle;
    private String locationSalle;
    private int userId; // Ajout du champ user_id

    // Constructeur avec paramètres
    public Salle(int idSalle, String nomSalle, int capacite, String equipement, boolean disponibilite, String imageSalle, String locationSalle, int userId) {
        this.idSalle = idSalle;
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.equipement = equipement;
        this.disponibilite = disponibilite;
        this.imageSalle = imageSalle;
        this.locationSalle = locationSalle;
        this.userId = userId; // Initialisation de userId
    }

    // Constructeur sans ID (pour l'ajout)
    public Salle(String nomSalle, int capacite, String equipement, boolean disponibilite, String imageSalle, String locationSalle, int userId) {
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.equipement = equipement;
        this.disponibilite = disponibilite;
        this.imageSalle = imageSalle;
        this.locationSalle = locationSalle;
        this.userId = userId; // Initialisation de userId
    }

    // Getters
    public int getIdSalle() {
        return idSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public int getCapacite() {
        return capacite;
    }

    public String getEquipement() {
        return equipement;
    }

    public boolean isDisponibilite() {
        return disponibilite;
    }

    public String getImageSalle() {
        return imageSalle;
    }

    public String getLocationSalle() {
        return locationSalle;
    }

    public int getUserId() {
        return userId;
    }

    // Setters
    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setEquipement(String equipement) {
        this.equipement = equipement;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public void setImageSalle(String imageSalle) {
        this.imageSalle = imageSalle;
    }

    public void setLocationSalle(String locationSalle) {
        this.locationSalle = locationSalle;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Salle{" +
                "idSalle=" + idSalle +
                ", nomSalle='" + nomSalle + '\'' +
                ", capacite=" + capacite +
                ", equipement='" + equipement + '\'' +
                ", disponibilite=" + disponibilite +
                ", imageSalle='" + imageSalle + '\'' +
                ", locationSalle='" + locationSalle + '\'' +
                ", userId=" + userId + // Affichage de userId
                '}';
    }
}
