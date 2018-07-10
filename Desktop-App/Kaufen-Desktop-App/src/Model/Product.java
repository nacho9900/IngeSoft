package Model;

import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Product implements Persistent{
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private User user;
    private Category category;
    private String thumbnail;
    private int visits;
    private int sold;

    private int userId;
    private int categoryId;
    private float rating;
    private boolean cached;

    private Product() {}

    private Product(int id, String name, String description, double price, User user, String thumbnail, int stock, Category category){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.user = user;
        this.category = category;
        this.thumbnail = thumbnail;
        this.stock = stock;
        this.visits=0;
        this.sold=0;
        cached = false;
    }

    private Product(int id, String name, String description, double price, int userId, String thumbnail, int stock, int visits, int sold, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.userId = userId;
        this.user = null;
        this.category = null;
        this.categoryId = categoryId;
        this.thumbnail = thumbnail;
        this.stock = stock;
        this.visits = visits;
        this.sold = sold;
        cached = false;
    }

    private static final String select = "SELECT * FROM Product ";
    private static final String update = "UPDATE Product SET name = '%s', description = '%s', price = %f, idclient = %d, thumbnail = '%s', " +
            "stock = %d, visits = %d, sold = %d, idCategory = %d WHERE id = %d";

    public static Product create(String name, String description, double price, User user, String thumbnail, int stock, Category category) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery("INSERT INTO Product (name, description, price, stock, idclient, thumbnail, idCategory) " +
                    "VALUES ('"+name+"', '"+description+"', "+price+", "+stock+", "+user.getId()+", "+(thumbnail==null?"NULL":"'"+thumbnail+"'")+", "+category.getId()+") " +
                    "RETURNING id");
            if(rs.next())
                return new Product(rs.getInt(1), name, description, price, user, thumbnail, stock, category);
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Product get(int id) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select+"WHERE id = "+id);
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Product> listByCriteria(String criteria) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + criteria);
            while (rs.next()) {
                products.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static ArrayList<Product> getRealatedProducts(Product product) {
        return listByCriteria(", Category WHERE Product.idCategory = Category.id AND stock > 0 AND Product.id != "+Integer.toString(product.getId())+" AND Category.description ILIKE '%"+product.getCategory().toString()+"%'");
    }

    public static ArrayList<Product> getByUser(User user) {
        return listByCriteria("WHERE idclient = "+user.getId());
    }

    public static ArrayList<Product> list() {
        return listByCriteria("");
    }

    public static ArrayList<Product> search(String text) {
        return listByCriteria("WHERE stock > 0 AND Product.name ILIKE '%"+text+"%'");
    }

    public static ArrayList<Product> search(String text, Category category) {
        return listByCriteria(", Category WHERE Product.idCategory = Category.id AND stock > 0 AND Product.name ILIKE '%"+text+"%' AND Category.description ILIKE '%"+category.toString()+"%'");
    }

    public static ArrayList<Product> search(String text, Country country, Province province, City city) {
        return listByCriteria(", Client WHERE Product.idClient = Client.id AND stock > 0 AND Product.name ILIKE '%"+text+"%' AND Client.idCountry = "+Integer.toString(country.getId())+" AND Client.idProvince = "+Integer.toString(province.getId())+" AND Client.idCity = "+Integer.toString(city.getId()));
    }

    public static ArrayList<Product> search(String text, Category category, Country country, Province province, City city) {
        return listByCriteria(", Category, Client WHERE Product.idClient = Client.id AND Product.idCategory = Category.id AND stock > 0 AND Product.name ILIKE '%"+text+"%' AND Category.description ILIKE '%"+category.toString()+"%' AND Client.idCountry = "+Integer.toString(country.getId())+" AND Client.idProvince = "+Integer.toString(province.getId())+" AND Client.idCity = "+Integer.toString(city.getId()));
    }


    public static HashMap<Product, Integer> listBoughtProducts(User user) {
        HashMap<Product, Integer> products = new HashMap<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery("SELECT p.*, cp.id as idcp FROM Cart c " +
                    "JOIN CartProducts cp ON cp.idCart = c.id JOIN Product p ON p.id = cp.idProduct WHERE c.closed = TRUE " +
                    "AND c.idClient = "+user.getId());
            while (rs.next()) {
                products.put(fromResultSet(rs), rs.getInt("idcp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static Product fromResultSet(ResultSet rs) {
        try {
            return new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                    rs.getDouble("price"), rs.getInt("idClient"), rs.getString("thumbnail"),
                    rs.getInt("stock"), rs.getInt("visits"), rs.getInt("sold"), rs.getInt("idCategory"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save() {
        try {
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate(String.format(Locale.ROOT, update, name, description, price,
                    user == null ? userId : user.getId(), thumbnail, stock, visits, sold, category == null ? categoryId : category.getId(), id));
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        try {
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate("DELETE FROM Product WHERE id = "+id);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteStock() {
        stock = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean removeStock(int amount) {
        if(stock >= amount) {
            stock -= amount;
            sold += amount;
            return true;
        }
        return false;
    }

    public User getUser() {
        if(user == null)
            user = User.get(userId);
        return user;
    }

    public Category getCategory() {
        if(category == null)
            category = Category.get(categoryId);
        return category;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public float getRating() {
        if(cached)
            return rating;

        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery("SELECT avg(rating) FROM Review WHERE idProduct = "+id);
            if(rs.next()) {
                rating = rs.getFloat(1);
                cached = true;
                return rating;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean incrementVisits() {
        try {
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate("UPDATE Product SET visits = visits + 1 WHERE id = "+id);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Review> getReviews() {
        return Review.list(this);
    }

    public ArrayList<Picture> getPictures() {
        return Picture.list(this);
    }

    @Override
    public int hashCode() {
        return 17*id;
    }

}