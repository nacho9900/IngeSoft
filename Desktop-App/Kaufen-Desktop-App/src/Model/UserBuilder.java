package Model;

public class UserBuilder {

    private int id;
    private String doc;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private boolean enabled;
    private boolean confirmed;
    private boolean admin;
    private DocType docType;
    private Cart cart;
    private Country country;
    private City city;
    private Province province;
    private String street;
    private int streetNumber;
    private String apartment;
    private String postCode;
    private String phone;
    private int validationCode;


    public UserBuilder(){

        id = 0;
        doc = "00000000";
        username = "default";
        password = "default";
        name = "default";
        surname = "default";
        email = "default";
        enabled = false;
        confirmed = false;
        admin = false;
        docType = null;
        cart = null;
        country = null;
        city = null;
        province = null;
        street = "default";
        streetNumber = 0;
        apartment = "default";
        postCode = "1234";
        phone = "12345678";
        validationCode = 0;
    };



    public UserBuilder addId(int id){
        this.id = id;
        return  this;
    }
    public UserBuilder addDoc(String doc){
        this.doc = doc;
        return  this;
    }
    public UserBuilder addUsername(String username){
        this.username = username;
        return this;
    }

    public UserBuilder addPassword(String password){
        this.password = password;
        return  this;
    }
    public UserBuilder addName(String name){
        this.name = name;
        return this;
    }

    public UserBuilder addSurname(String surname){
        this.surname = surname;
        return  this;
    }

    public UserBuilder addEmail(String email){
        this.email = email;
        return  this;
    }
    public UserBuilder addEnabled(boolean isEnabled){
        this.enabled = isEnabled;
        return this;
    }
    public  UserBuilder addConfirmed(boolean isConfirmed){
        this.confirmed = isConfirmed;
        return  this;
    }
    public UserBuilder addAdmin(boolean isAdmin){
        this.admin = isAdmin;
        return  this;
    }
    public UserBuilder addDocType( DocType docType){
        this.docType = docType;
        return  this;
    }
    public UserBuilder addCart(Cart cart){
        this.cart = cart;
        return  this;
    }
    public UserBuilder addCountry(Country country){
        this.country = country;
        return  this;
    }

    public  UserBuilder addCity(City city){
        this.city = city;
        return  this;
    }
    public UserBuilder addProvince(Province province){
        this.province = province;
        return  this;
    }
    public UserBuilder addStreet(String street){
        this.street = street;
        return this;
    }

    public UserBuilder addStreetNumber(int streetNumber){
        this.streetNumber = streetNumber;
        return this;
    }

    public UserBuilder addPostCode(String postCode){
        this.postCode = postCode;
        return  this;
    }
    public UserBuilder addApartment(String apartment){
        this.apartment = apartment;
        return  this;
    }
    public UserBuilder addPhone(String phone){
        this.phone= phone;
        return  this;
    }
    public UserBuilder addValidation(int validationCode){
        this.validationCode = validationCode;
        return  this;
    }

    public  User build(){
        return  User.create(doc,  username,  password,  name,  surname,  email,  enabled,
         confirmed,  admin,  docType,  cart,  country,  city,  province,  street,  streetNumber,  apartment,
                 postCode,  phone,  validationCode);
    }



}
