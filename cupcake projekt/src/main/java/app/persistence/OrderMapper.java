package app.persistence;

import app.entities.Orderline;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static List<Orderline> getOrders(ConnectionPool connectionPool) {
        String sql = "SELECT * FROM public.orderline";
        List<Orderline> orderLineList = new ArrayList<>();
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int price = rs.getInt("price");
                int orderLineId = rs.getInt("orderline_id");
                int orderId = rs.getInt("order_id");
                int bundId  = rs.getInt("bund_id");
                int toppingId = rs.getInt("topping_id");
                int amount = rs.getInt("antal");

                Orderline orderLine = new Orderline(price, orderLineId, orderId, bundId, toppingId, amount);
                orderLineList.add(orderLine);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderLineList;
    }
}

