package Model;

import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocType extends Data{
    private int id;
    private String name;

    private static final String selectDocTypes = "SELECT * FROM DocType ";

    public DocType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DocType get(int id) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(selectDocTypes + "WHERE id ="+id);
            if(rs.next())
                return fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<DocType> list() {
        ArrayList<DocType> docs = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(selectDocTypes);
            while (rs.next())
                docs.add(fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docs;
    }

    private static DocType fromResultSet(ResultSet rs) {
        try {
            return new DocType(rs.getInt("id"), rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return getName();
    }

    public String getName() {
        return name;
    }

    public static DocType getFromString(String string) {
        ArrayList<DocType> list = list();
        for(DocType doctype: list){
            if(doctype.getName().equals(string)){
                return doctype;
            }
        }
        throw new RuntimeException("wrong doctype");
    }
}
