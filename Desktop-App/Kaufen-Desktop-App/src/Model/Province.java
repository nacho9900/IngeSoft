package Model;

import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Province extends Data {
    private String name;
    private int countryId;
    private Country country;

    private static final String select = "SELECT * FROM Province ";

    public Province(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    private Province(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.country = null;
    }

    public static Province get(int id) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE id ="+id);
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Province> list(Country country) {
        ArrayList<Province> provinces = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select+"WHERE idCountry = "+country.getId());
            while (rs.next())
                provinces.add(fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinces;
    }

    private static Province fromResultSet(ResultSet rs) {
        try {
            return new Province(rs.getInt("id"), rs.getString("name"), rs.getInt("idCountry"));
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

    public Country getCountry() {
        if(country == null)
            country = Country.get(countryId);
        return country;
    }
}
