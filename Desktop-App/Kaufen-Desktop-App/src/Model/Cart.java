package Model;

import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Cart implements Persistent {
    private int id;
    private User user;
    private boolean closed;
    private Card card;

    private int userId;
    private int cardId;

    private Cart() {}

    private Cart(int id, User user, boolean closed, Card card){
        this.id = id;
        this.closed = closed;
        this.card = card;
        this.user = user;
    }

    private Cart(int id, int userId, boolean closed, int cardId){
        this.id = id;
        this.closed = closed;
        this.cardId = cardId;
        this.card = null;
        this.userId = userId;
        this.user = null;
    }

    private static final String select = "SELECT * FROM Cart ";
    private static final String update = "UPDATE Cart SET idClient = %d, closed = %b, idCard = %s WHERE id = %d";


    public static Cart getOrCreate(User user) {
        Cart currentCart = getUserCart(user);
        if(currentCart != null)
            return currentCart;

        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery("INSERT INTO Cart (idclient) " +
                    "VALUES ("+user.getId()+") RETURNING id");
            if(rs.next())
                return new Cart(rs.getInt(1), user, false, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cart get(int id) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE id = "+id);
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cart getUserCart(User user) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE idClient = "+user.getId()+" AND closed = FALSE");
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Cart> closedCarts(User user) {
        ArrayList<Cart> carts = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE idClient = "+user.getId()+" AND closed = TRUE");
            while (rs.next())
                carts.add(fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }

    private static Cart fromResultSet(ResultSet rs) {
        try {
            return new Cart(rs.getInt("id"), rs.getInt("idClient"), rs.getBoolean("closed"),
                    rs.getInt("idCard"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<Product, Integer> getProducts() {
        HashMap<Product, Integer> products = new HashMap<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery("SELECT p.*, amount FROM Cart c " +
                    "JOIN CartProducts cp ON cp.idCart = c.id JOIN Product p ON p.id = cp.idProduct WHERE c.id = "+id);
            while (rs.next()) {
                products.put(Product.fromResultSet(rs), rs.getInt("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean addProduct(Product product, int amount) {
        if(amount < 1)
            return false;

        try {
            Statement st = DataBaseHandler.getInstance().getStatement();
            ResultSet rs = st.executeQuery("SELECT amount FROM CartProducts WHERE idCart = "+id+" AND idProduct = "+product.getId());
            if(rs.next()) {
                int prevAmount = rs.getInt(1);
                if(prevAmount + amount > product.getStock())
                    return false;
                st.execute("UPDATE CartProducts SET amount = amount + " + amount + " WHERE idCart = "+id+" AND idProduct = "+product.getId());
            }
            else {
                if(amount > product.getStock())
                    return false;
                st.execute("INSERT INTO CartProducts (idCart, idProduct, amount) VALUES (" + id + ", " + product.getId() + ", " + amount + ")");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeProduct(Product product) {
        try {
            Statement st = DataBaseHandler.getInstance().getStatement();
            ResultSet rs = st.executeQuery("SELECT id FROM CartProducts WHERE idCart = "+id+" AND idProduct = "+product.getId());
            if(rs.next()) {
                if(st.executeUpdate("DELETE FROM CartProducts WHERE id = "+rs.getInt(1)) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save() {
        try {
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate(String.format(Locale.ROOT, update, user == null ? userId : user.getId(),
                    closed, card == null ? (cardId == 0 ? null : cardId) : card.getId(), id));
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        try {
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate("DELETE FROM Cart WHERE id = "+id);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        if(user == null)
            user = User.get(userId);
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean close(){
        if(card != null) {
            HashMap<Product, Integer> products = getProducts();
            for (Product prod : products.keySet()) {
                if(!prod.removeStock(products.get(prod)))
                    return false;
            }
            for (Product prod : products.keySet()) {
                prod.save();
            }
            closed = true;
            return save();
        }
        return false;
    }

    public double getSubTotal() {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery("SELECT sum(amount * price) FROM CartProducts cp JOIN Product p ON p.id = cp.idProduct WHERE idCart = "+id);
            if(rs.next())
                return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Card getCard() {
        if(card == null)
            card = Card.get(cardId);
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
