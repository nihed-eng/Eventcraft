package tn.esprit.services;

import tn.esprit.entities.Reservationsalle;
import tn.esprit.entities.Salle;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> afficher() throws SQLException;
    T getById(int id) throws SQLException; // Retourne un objet de type T
}
