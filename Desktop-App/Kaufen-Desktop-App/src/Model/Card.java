package Model;

import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class Card implements Persistent{
    private int id;
    private User user;
    private String name;
    private String surname;
    private String number;
    private int year;
    private int month;
    private int code;

    private int userId;

    private static final String select = "SELECT * FROM Card ";
    private static final String update = "UPDATE Card SET idClient = %d, name = '%s', surname = '%s', number = '%s', year = %d, month = %d, code = %d WHERE id = %d";

    private Card() {}

    private Card(int id, User user, String name, String surname, String number, int year, int month, int code) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.year = year;
        this.month = month;
        this.code = code;
    }

    private Card(int id, int userId, String name, String surname, String number, int year, int month, int code) {
        this.id = id;
        this.userId = userId;
        this.user = null;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.year = year;
        this.month = month;
        this.code = code;
    }

    public static Card create(User user, String name, String surname, String number, int month, int year, int code) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery("INSERT INTO Card (idClient, name, surname, number, year, month, code) " +
                    "VALUES ("+user.getId()+", '"+name+"', '"+surname+"', '"+number+"', "+year+", "+month+", "+code+") RETURNING id");
            if(rs.next())
                return new Card(rs.getInt(1), user, name, surname, number,  month, year, code);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Card get(int id) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE id = "+id);
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Card> getByUser(User user) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE idClient = "+user.getId());
            while (rs.next())
                cards.add(fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    private static Card fromResultSet(ResultSet rs) {
        try {
            return new Card(rs.getInt("id"), rs.getInt("idClient"), rs.getString("name"),
                    rs.getString("surname"), rs.getString("number"), rs.getInt("year"),
                    rs.getInt("month"), rs.getInt("code"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save() {
        try {
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate(String.format(Locale.ROOT, update, user == null ? userId : user.getId(),
                    name, surname, number, year, month, code, id));
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        try {
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate("DELETE FROM Card WHERE id = "+id);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
