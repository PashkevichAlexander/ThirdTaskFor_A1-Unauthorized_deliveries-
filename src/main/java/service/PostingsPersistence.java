//package service;
//
//import JDBCService.DBConnection;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class PostingsPersistence {
//    public PostingsPersistence() {
//        System.out.println("PostingsPersistence constructor called.");
//
//        public Animal findByDate(int animalId) throws SQLException {
//            String sql = "SELECT * FROM ANIMAL WHERE id=" + animalId;
//            Animal animal = null;
//            try (
//                    Connection conn = DBConnection.getInstance().getConnection();
//                    Statement statement = conn.createStatement();
//                    ResultSet resultSet = statement.executeQuery(sql);) {
//                while (resultSet.next()) {
//                    animal = getAnimalFromResultSet(resultSet);
//                }
//            }
//
//            return animal;
//        }
//
//        private Animal getAnimalFromResultSet(ResultSet resultSet) throws SQLException {
//            Animal a = new Animal();
//            a.setId(resultSet.getInt("id"));
//            a.setName(resultSet.getString("name"));
//            a.setSpecies(Species.get(resultSet.getInt("species")));
//            a.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
//            return a;
//        }
//    }
//}
