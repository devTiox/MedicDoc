package Data;

import Scenes.PatientsListScene;

import java.sql.*;

@SuppressWarnings("SpellCheckingInspection")
public class DataBase {
    private static Connection conn = null;

    public static void connection() throws SQLException {
        String url = "jdbc:sqlite:Pacjenci.db";
        conn = DriverManager.getConnection(url);
        System.out.println("Connected to database successfully");
    }

    public static void createTable() throws SQLException {
        String createQuery = """
                CREATE TABLE IF NOT EXISTS Pacjenci(
                    PESEL TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    lastName TEXT NOT NULL,
                    address TEXT,
                    birthDate TEXT,
                    phoneNumber TEXT,
                    email TEXT,
                    documentation TEXT
                );
                """;
        Statement statement = conn.createStatement();
        statement.execute(createQuery);
    }

    public static void insertPatient(Patient p) throws SQLException {
        String insertQuery = "INSERT INTO Pacjenci(PESEL, name, lastName, address, birthDate, phoneNumber, email, documentation)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(insertQuery);
        ps.setString(1, p.PESEL);
        ps.setString(2, p.name);
        ps.setString(3, p.lastName);
        ps.setString(4, p.address);
        ps.setString(5, p.birthDate);
        ps.setString(6, p.phoneNumber);
        ps.setString(7, p.email);
        ps.setString(8, p.documentation);
        ps.executeUpdate();
        System.out.printf("Dodano pacjenta:%s %s %s%n", p.PESEL, p.name, p.lastName);
    }

    public static void updatePatientDocumentation(Patient p) throws SQLException {
        String updateQuery = "UPDATE Pacjenci SET DOCUMENTATION = ? WHERE PESEL = ?;";
        PreparedStatement ps = conn.prepareStatement(updateQuery);
        ps.setString(1, p.documentation);
        ps.setString(2, p.PESEL);
        ps.executeUpdate();
        System.out.printf("Zaaktualizowano Dokumentacje %s%n", p.PESEL);
    }

    public static void loadPatientsList() throws SQLException {
        String query = "SELECT * FROM Pacjenci";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while(rs.next()) {
            Patient patient = new Patient(rs.getString("name"), rs.getString("lastName"),
                    rs.getString("address"), rs.getString("birthDate"),
                    rs.getString("PESEL"), rs.getString("phoneNumber"),
                    rs.getString("email"));
            PatientsListScene.patientsList.add(patient);
        }
    }

    public static void deletePatient(Patient p) throws SQLException {
        String deleteQuery = "DELETE FROM Pacjenci WHERE PESEL = ?;";
        PreparedStatement ps = conn.prepareStatement(deleteQuery);
        ps.setString(1, p.PESEL);
        ps.executeUpdate();
    }
}
