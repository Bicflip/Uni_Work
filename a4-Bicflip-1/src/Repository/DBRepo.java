package Repository;

import Entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBRepo<T extends Entity> extends Repo<T> {
    private final String tableName;
    private final Connection connection;

    public DBRepo(String tableName, Connection connection) {
        this.tableName = tableName;
        this.connection = connection;
    }


    @Override
    public void addEntity(T entity) {
        if (entity instanceof Pacient pacient) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO " + tableName + " (id, nume, prenume, anul_nasterii) VALUES (?, ?, ?, ?)")) {
                stmt.setInt(1, pacient.getID());
                stmt.setString(2, pacient.getNumePacient());
                stmt.setString(3, pacient.getPrenumePacient());
                stmt.setInt(4, pacient.getAnulNasterii());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la adăugarea pacientului în baza de date.", e);
            }
        } else {
            throw new UnsupportedOperationException("Entitatea nu este suportată.");
        }
    }

    @Override
    public void removeEntityByID(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM " + tableName + " WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la ștergerea entității din baza de date.", e);
        }
    }

    @Override
    public void updateEntity(int idToBeUpdated,T entity) {
        if (entity instanceof Pacient pacient) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE " + tableName + " SET nume = ?, prenume = ?, anul_nasterii = ? WHERE id = ?")) {
                stmt.setString(1, pacient.getNumePacient());
                stmt.setString(2, pacient.getPrenumePacient());
                stmt.setInt(3, pacient.getAnulNasterii());
                stmt.setInt(4, pacient.getID());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la actualizarea pacientului în baza de date.", e);
            }
        } else {
            throw new UnsupportedOperationException("Entitatea nu este suportată.");
        }
    }

    @Override
    public T findByID(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM " + tableName + " WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return (T) new Pacient(rs.getInt("id"),rs.getString("nume"),
                        rs.getString("prenume"),rs.getInt("anul_nasterii"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la găsirea entității în baza de date.", e);
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {
            while (rs.next()) {
                entities.add((T) new Pacient(rs.getInt("id"), rs.getString("nume"),
                        rs.getString("prenume"),rs.getInt("anul_nasterii")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la găsirea entităților în baza de date.", e);
        }
        return entities;
    }


}
