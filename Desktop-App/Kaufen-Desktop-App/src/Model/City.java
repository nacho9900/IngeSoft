package Model;

import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class City extends Data {
    private String name;
    private int provinceId;
    private Province province;

    private static final String select = "SELECT * FROM City ";

    private City() {}

    public City(int id, String name, Province province) {
        this.id = id;
        this.name = name;
        this.province = province;
    }

    private City(int id, String name, int provinceId) {
        this.id = id;
        this.name = name;
        this.provinceId = provinceId;
        this.province = null;
    }

    public static City get(int id) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE id ="+id);
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<City> list(Province province) {
        ArrayList<City> cities = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select+"WHERE idProvince = "+province.getId());
            while (rs.next())
                cities.add(fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private static City fromResultSet(ResultSet rs) {
        try {
            return new City(rs.getInt("id"), rs.getString("name"), rs.getInt("idProvince"));
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

    public Province getProvince() {
        if(province == null)
            province = Province.get(provinceId);
        return province;
    }
}
