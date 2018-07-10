package Model;

import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class Review implements Persistent{
    private int id;
    private Product product;
    private String comment;
    private int rating;
    private int idCP;

    private int productId;

    private static final String select = "SELECT * FROM review ";
    private static final String update = "UPDATE review SET idproduct = %d, comment = '%s', rating = %d WHERE id = %d";

    private Review() {}

    private Review(int id, int idCP, Product product, String comment, int rating) {
        this.id = id;
        this.product = product;
        this.comment = comment;
        this.rating = rating;
        this.idCP = idCP;
    }

    private Review(int id, int idCP, int productId, String comment, int rating) {
        this.id = id;
        this.productId = productId;
        this.product = null;
        this.comment = comment;
        this.rating = rating;
        this.idCP = idCP;
    }

    public static Review create(int idCP, Product product, String comment, int rating) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery("INSERT INTO review (idproduct, idcartproduct, comment, rating) " +
                    "VALUES ("+product.getId()+", "+idCP+", '"+comment+"', "+rating+") RETURNING id");
            if(rs.next())
                return new Review(rs.getInt(1), idCP, product, comment, rating);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Review get(int idCP) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE idcartproduct = "+idCP);
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Review> list(Product product) {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE idproduct = "+product.getId());
            while (rs.next())
                reviews.add(fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    private static Review fromResultSet(ResultSet rs) {
        try {
            return new Review(rs.getInt("id"), rs.getInt("idCartProduct"), rs.getInt("idProduct"), rs.getString("comment"),
                    rs.getInt("rating"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save() {
        try {
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate(String.format(Locale.ROOT, update,
                    product == null ? productId : product.getId(), comment, rating, id));
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        try {
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate("DELETE FROM review WHERE id = "+id);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public int getIdCP() {
        return idCP;
    }

    public Product getProduct() {
        if(product == null)
            product = Product.get(productId);
        return product;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
