package Model;

import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Category extends Data {
    private String description;

    private static final String select = "SELECT * FROM Category ";

    private Category() {}

    public Category(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Category get(int id) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE id ="+id);
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Category> list() {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select);
            while (rs.next())
                categories.add(fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    private static Category fromResultSet(ResultSet rs) {
        try {
            return new Category(rs.getInt("id"), rs.getString("description"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString(){
        return getName();
    }

    public String getName() {
        return description;
    }
}
