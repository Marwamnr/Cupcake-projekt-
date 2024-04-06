package app.persistence;

import app.entities.Orderline;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderlineMapper {

    public static Orderline createOrderline(int orderlineId, int orderId, int botttomId, int toppingId, int price, int amount, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into orderline (orderline_id, order_id, bottom_id, topping_id, price, amount) values (?,?,?,?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderlineId);
            ps.setInt(2, orderId);
            ps.setInt(3, botttomId);
            ps.setInt(4, toppingId);
            ps.setInt(5, price);
            ps.setInt(6, amount);



            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        return new Orderline(orderlineId, orderId, botttomId, toppingId, price, amount);
    }


    private void deleteOrderLine(int orderlineId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from orderline where orderline_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderlineId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en orderline");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning af en task", e.getMessage());
        }
    }
}