package Model;


import Controller.DataBaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class User implements Persistent{

    private int id, streetNumber, validationCode;
    private String username, password, name, surname, email, doc;
    private boolean enabled, confirmed, admin;
    private DocType docType;
    private Country country;
    private City city;
    private Province province;
    private String street, apartment,  postCode, phone;

    private static final String select = "SELECT u.*, d.name as docName, c.name as countryName, p.name as provinceName, city.name as cityName " +
            "FROM Client u JOIN DocType d ON d.id = u.iddoctype JOIN Country c ON c.id = u.idcountry " +
            "JOIN Province p ON p.id = u.idprovince JOIN City ON city.id = u.idcity ";
    private static final String update = "UPDATE Client SET username = '%s', password = '%s', name = '%s', surname = '%s', email = '%s', iddoctype = %d, idcountry = %d, idprovince = %d, idcity = %d, postcode = '%s', phone = '%s', enabled = %b, confirmed = %b, admin = %b, doc = '%s', street = '%s', streetNum = '%d', apartment = '%s' WHERE id = %d";
    private static final String updateEnabled = "UPDATE Client SET enabled = %b WHERE id = %d";

    private User(){}

    private User(int id, String doc, String username, String password, String name, String surname, String email, boolean enabled,
                boolean confirmed, boolean admin, DocType docType, Cart cart, Country country, City city, Province province, String street, int streetNumber, String apartment,
                 String postCode, String phone, int validationCode) {
        this.id = id;
        this.doc = doc;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.enabled = enabled;
        this.confirmed = confirmed;
        this.docType = docType;
        this.country = country;
        this.city = city;
        this.province = province;
        this.street = street;
        this.streetNumber = streetNumber;
        this.apartment = apartment;
        this.postCode = postCode;
        this.phone = phone;
        this.admin = admin;
        this.validationCode = validationCode;
    }

    public static User create(String doc, String username, String password, String name, String surname, String email, boolean enabled,
                              boolean confirmed, boolean admin, DocType docType, Cart cart, Country country, City city, Province province,
                              String street, int streetNumber, String apartment, String postCode, String phone, int validationCode) { //Factory :wink:

        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery("INSERT INTO Client (username, password, name, surname," +
                    " email, iddoctype, idcountry, idprovince, idcity, postcode, phone, enabled, confirmed, admin, doc, street, streetNum, apartment, validationCode) " +
                    "VALUES ('"+username+"', '"+password+"', '"+name+"', '"+surname+"', '"+email+"', "+docType.getId()+", "+country.getId()+
                    ", "+province.getId()+", "+city.getId()+", '"+postCode+"', '"+phone+"', "+enabled+", "+confirmed+", "+admin+", '"+doc+
                    "', '"+street+"', '"+Integer.toString(streetNumber)+"', '"+apartment+"','"+Integer.toString(validationCode)+"') RETURNING id");

            if(rs.next())
                return new User(rs.getInt(1), doc, username, password, name, surname, email, enabled, confirmed, admin, docType,
                        cart, country, city, province, street, streetNumber, apartment, postCode, phone, validationCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User get(int id) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery( select + "WHERE u.id = "+ id);
            if(rs.next()) {
                return fromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static User login(String username, String password) {
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery( select +
                    "WHERE username = '"+ username +"' AND password = '"+ password +"'");
            if(rs.next()) {
                return fromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public static ArrayList<User> list() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select);
            while (rs.next()) {
                users.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static ArrayList<User> search(String text) {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + "WHERE username ILIKE '%"+text+"%'");
            while (rs.next()) {
                users.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean validate(int validationCode)
    {
       boolean result = (validationCode == validationCode);
       if(result){
           this.enabled = true;
           try {
               int count = DataBaseHandler.getInstance().getStatement().executeUpdate(String.format(Locale.ROOT, updateEnabled, true, this.id));
               if(count < 0)
                   return false;
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       return result;
    }

    private static User fromResultSet(ResultSet rs) {
        try {
            DocType docType = new DocType(rs.getInt("iddoctype"), rs.getString("docName"));
            Country country = new Country(rs.getInt("idcountry"), rs.getString("countryName"));
            Province province = new Province(rs.getInt("idprovince"), rs.getString("provinceName"), country);
            City city = new City(rs.getInt("idcity"), rs.getString("cityName"), province);

            System.out.println(province);

            return new User(rs.getInt("id"), rs.getString("doc"), rs.getString("username"),
                    rs.getString("password"), rs.getString("name"), rs.getString("surname"),
                    rs.getString("email"), rs.getBoolean("enabled"), rs.getBoolean("confirmed"),
                    rs.getBoolean("admin"), docType, null, country, city, province, rs.getString("street"), rs.getInt("streetNum"), rs.getString("apartment"),
                    rs.getString("postCode"), rs.getString("phone"), rs.getInt("validationCode"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean save() {
        try {
            System.out.println(String.format(Locale.ROOT, update, username, password, name, surname, email,
                    docType.getId(), country.getId(), province.getId(), city.getId(), postCode, phone, enabled, confirmed, admin, doc, street, streetNumber, apartment, id));
            int count = DataBaseHandler.getInstance().getStatement().executeUpdate(String.format(Locale.ROOT, update, username, password, name, surname, email,
                    docType.getId(), country.getId(), province.getId(), city.getId(), postCode, phone, enabled, confirmed, admin, doc, street, streetNumber, apartment, id));
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

    public String getdoc() {
        return doc;
    }

    public void setdoc(String doc) {
        this.doc = doc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public Cart getCart() {
        return Cart.getUserCart(this);
    }

    public ArrayList<Product> getProducts() {
        return Product.getByUser(this);
    }

    public ArrayList<Card> getCards() {
        return Card.getByUser(this);
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public String getStreet(){
        return street;
    }

    public void setStreetNumber(int streetNumber){this.streetNumber = streetNumber;}

    public int getStreetNumber(){return streetNumber;}

    public void setApartment(String apartment){this.apartment = apartment;}

    public String getApartment(){return apartment;}

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String toString(){
        return name;
    }
}
