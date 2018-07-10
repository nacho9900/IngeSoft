package Model;

import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Country extends Data {
    private String name;

    private static final String select = "SELECT * FROM Country ";

    private Country() {}

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Country get(int id) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE id ="+id);
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Country> list() {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select);
            while (rs.next())
                countries.add(fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    private static Country fromResultSet(ResultSet rs) {
        try {
            return new Country(rs.getInt("id"), rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString(){
        return getName();
    }

    public String getName() {
        return name;
    }
}
