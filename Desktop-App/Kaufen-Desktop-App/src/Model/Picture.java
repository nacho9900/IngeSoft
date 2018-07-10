package Model;

import Controller.DataBaseHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Picture implements Persistent{
    private int id;
    private int idproduct;
    private String path;
    private Image image;
    private File file;

    private static  String insert = "INSERT INTO Picture (idproduct, path, image) values (?, ?, ?) returning id";
    private  static  String select = "SELECT image, idproduct FROM Picture p join Product prod on p.idproduct = prod.id where prod.id = ";

    public Picture(int id, int idproduct, String path, File fileInputStream) {
        this.id = id;
        this.idproduct = idproduct;
        this.path = path;
        this.file =fileInputStream;

        try {
            BufferedImage bufferedImage = ImageIO.read(fileInputStream);
            ImageIcon icon = new ImageIcon(bufferedImage);
            this.image = icon.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Picture(int id, int idproduct, String path, InputStream fileInputStream) {
        this.id = id;
        this.idproduct = idproduct;
        this.path = path;


        try {
            BufferedImage bufferedImage = ImageIO.read(fileInputStream);
            ImageIcon icon = new ImageIcon(bufferedImage);
            this.image = icon.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return image;
    }





    public static Picture create(int idproduct, String path, File file) {


        try {
            FileInputStream imageInputStream = new FileInputStream(file);

            PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(insert);
            // inform the statement that first parameter in the query is of binary type
            statement.setInt(1, idproduct);
            statement.setString(2, path);
            statement.setBinaryStream(3, imageInputStream, file.length());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
            {
                int id = resultSet.getInt(1);
                statement.close();
                return new Picture(id, idproduct, path, file);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new Picture(0, idproduct, path, file);
    }


    public static  java.util.List<Picture> getProdcutPicture(int idproduct){
        List<Picture> pictures = new ArrayList<Picture>();
        try {
            ResultSet rs = DataBaseHandler.getInstance().getStatement().executeQuery(select + idproduct);
            while(rs.next())
            {

                InputStream is = rs.getBinaryStream(1);

                pictures.add(new Picture(0,rs.getInt(2), "", is));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  pictures;
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static ArrayList<Picture> list(Product product) {
        return  (ArrayList<Picture>) getProdcutPicture(product.getId());
    }

    public File getFile() {
        return file;
    }
    public int getIdproduct()
    {
        return idproduct;
    }
}
