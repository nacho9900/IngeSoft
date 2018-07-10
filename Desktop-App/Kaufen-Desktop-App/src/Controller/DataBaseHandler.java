package Controller;

import java.sql.*;

public class DataBaseHandler {

    private static final String dataBaseUrl = "jdbc:postgresql://ingesoft.czhg2l6czm8t.sa-east-1.rds.amazonaws.com:5432/postgres";

    private static DataBaseHandler singletonDataBaseHandler;

    private Connection connection;
    private Statement statement;

    private DataBaseHandler(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dataBaseUrl, "postgres", "ca210946");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataBaseHandler getInstance() {
        if(singletonDataBaseHandler == null) {
            singletonDataBaseHandler = new DataBaseHandler();
        }
        return singletonDataBaseHandler;
    }

    public boolean initializeDataBase() {
        try {
            statement.clearWarnings();
            statement.execute(createDocTypeTable);
            statement.execute(createCountryTable);
            statement.execute(createProvinceTable);
            statement.execute(createCityTable);
            statement.execute(createUserTable);
            statement.execute(createCategoryTable);
            statement.execute(createProductTable);
            statement.execute(createPictureTable);
            statement.execute(createCardTable);
            statement.execute(createCartTable);
            statement.execute(createCartProductsTable);
            statement.execute(createReviewTable);
            SQLWarning warning = statement.getWarnings();
            return warning == null || warning.getSQLState().equals("42P07");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public Connection getConnection() {
        return connection;
    }

    private static final String createUserTable = "CREATE TABLE IF NOT EXISTS Client (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  username VARCHAR(255) UNIQUE NOT NULL,\n" +
            "  password VARCHAR(255) NOT NULL,\n" +
            "  name VARCHAR(255) NOT NULL,\n" +
            "  surname VARCHAR(255) NOT NULL,\n" +
            "  email VARCHAR(255) NOT NULL,\n" +
            "  idDocType INT NOT NULL REFERENCES DocType(id),\n" +
            "  doc VARCHAR(255) NOT NULL,\n" +
            "  idCountry INT REFERENCES Country(id) ON DELETE SET NULL,\n" +
            "  idProvince INT REFERENCES Province(id) ON DELETE SET NULL,\n" +
            "  idCity INT REFERENCES City(id) ON DELETE SET NULL,\n" +
            "  street VARCHAR(255) NOT NULL,\n" +
            "  streetNum INT NOT NULL,\n" +
            "  apartment VARCHAR(255) NOT NULL,\n" +
            "  postCode VARCHAR(255) NOT NULL,\n" +
            "  phone INT NOT NULL,\n" +
            "  enabled BOOL NOT NULL,\n" +
            "  confirmed BOOL NOT NULL,\n" +
            "  admin BOOL NOT NULL,\n" +
            "  validationCode INT NOT NULL,\n"+
            "  UNIQUE (idDocType, doc)\n" +
            ");";

    private static final String createDocTypeTable = "CREATE TABLE IF NOT EXISTS DocType (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  name VARCHAR(255) UNIQUE NOT NULL\n" +
            ");";

    private static final String createCountryTable = "CREATE TABLE IF NOT EXISTS Country (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  name VARCHAR(255) UNIQUE NOT NULL\n" +
            ");";

    private static final String createProvinceTable = "CREATE TABLE IF NOT EXISTS Province (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  name VARCHAR(255) NOT NULL,\n" +
            "  idCountry INT NOT NULL REFERENCES Country(id) ON DELETE CASCADE ,\n" +
            "  UNIQUE (name, idCountry)\n" +
            ");";

    private static final String createCityTable = "CREATE TABLE IF NOT EXISTS City (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  name VARCHAR(255) NOT NULL,\n" +
            "  idProvince INT NOT NULL REFERENCES Province(id) ON DELETE CASCADE,\n" +
            "  UNIQUE (name, idProvince)\n" +
            ");";

    private static final String createCategoryTable = "CREATE TABLE IF NOT EXISTS Category (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  description VARCHAR(255) NOT NULL\n" +
            ");";

    private static final String createProductTable = "CREATE TABLE IF NOT EXISTS Product (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  name VARCHAR(255) NOT NULL,\n" +
            "  description VARCHAR(255) NOT NULL,\n" +
            "  price DOUBLE PRECISION NOT NULL,\n" +
            "  stock INT NOT NULL,\n" +
            "  idClient INT NOT NULL REFERENCES Client(id) ON DELETE CASCADE,\n" +
            "  idCategory INT REFERENCES Category(id) ON DELETE SET NULL,\n" +
            "  thumbnail VARCHAR(255) NOT NULL DEFAULT '',\n" +
            "  visits INT NOT NULL DEFAULT 0,\n" +
            "  sold INT NOT NULL DEFAULT 0\n" +
            ");";

    private static final String createReviewTable = "CREATE TABLE IF NOT EXISTS Review (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  idProduct INT NOT NULL REFERENCES Product(id) ON DELETE CASCADE,\n" +
            "  comment VARCHAR(2048) NOT NULL,\n" +
            "  rating INT NOT NULL,\n" +
            "  idCartProduct INT REFERENCES CartProducts(id) ON DELETE SET NULL\n"+
            ");";

    private static final String createPictureTable = "CREATE TABLE IF NOT EXISTS Picture (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  idProduct INT NOT NULL REFERENCES Product(id) ON DELETE CASCADE ,\n" +
            "  path VARCHAR(255) NOT NULL\n" +
            ");";

    private static final String createCardTable = "CREATE TABLE IF NOT EXISTS Card (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  idClient INT NOT NULL REFERENCES Client(id) ON DELETE CASCADE ,\n" +
            "  name VARCHAR(255) NOT NULL,\n" +
            "  surname VARCHAR(255) NOT NULL,\n" +
            "  number BIGINT NOT NULL,\n" +
            "  YEAR INT NOT NULL,\n" +
            "  month INT NOT NULL,\n" +
            "  code INT NOT NULL,\n" +
            "  UNIQUE (idClient, number)\n" +
            ");";

    private static final String createCartTable = "CREATE TABLE IF NOT EXISTS Cart (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  idClient INT REFERENCES Client(id) ON DELETE SET NULL ,\n" +
            "  closed BOOL NOT NULL DEFAULT FALSE,\n" +
            "  subtotal DOUBLE PRECISION NOT NULL DEFAULT 0,\n" +
            "  idCard INT REFERENCES Card(id) ON DELETE SET NULL\n" +
            ");";

    private static final String createCartProductsTable = "CREATE TABLE IF NOT EXISTS CartProducts (\n" +
            "  id SERIAL PRIMARY KEY,\n" +
            "  idCart INT NOT NULL REFERENCES Cart(id) ON DELETE CASCADE ,\n" +
            "  idProduct INT REFERENCES Product(id) ON DELETE SET NULL ,\n" +
            "  amount INT NOT NULL,\n" +
            "  UNIQUE (idCart, idProduct)\n" +
            ");";

}
