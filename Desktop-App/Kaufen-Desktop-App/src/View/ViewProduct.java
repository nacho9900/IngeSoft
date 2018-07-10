package View;

import Controller.Controller;
import Model.Cart;
import Model.Picture;
import Model.Product;
import Model.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewProduct {
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JButton SEARCHbutton;
    private JPanel footnote;
    private JPanel middle;
    private JTextPane description;
    private JTextField thumbnail;
    private JLabel precio;
    private JTextArea name;
    private JButton verOpinionesButton;
    private JLabel rating;
    private JLabel stock;
    private JLabel vendidos;
    private JButton addToCartButton;
    private JLabel LOGO;
    private JLabel name1;
    private JLabel name2;
    private JLabel name3;
    private JButton relatedPicture1;
    private JButton relatedPicture2;
    private JButton relatedPicture3;
    private JLabel picture1;
    private JLabel picture2;
    private JPanel relatedProducts;
    private JPanel related1;
    private JPanel related2;
    private JPanel related3;
    private Product product;

    private  List<Product> relatedProductsList;
    private  List<Picture> pictures;

    public JButton getSEARCHbutton() {
        return SEARCHbutton;
    }

    public JButton getCARRITObutton() {
        return CARRITObutton;
    }

    public JButton getPERFILButton() {
        return PERFILButton;
    }

    public JButton getVISIONButton() {
        return VISIONButton;
    }

    public JButton getEQUIPObutton() {
        return EQUIPObutton;
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public ViewProduct(){
        super();

        pictures = new ArrayList<>();
        relatedProductsList = new ArrayList<>();
        setActionListners();

    }

    public void printProduct() {
        commonPrint();
    }

    public void printProduct(Product product, List<Picture> pictures) {
        cleanProduct();
        this.product = product;
        name.setText(product.getName());
        precio.setText("$" + ((Double) product.getPrice()).toString());
        this.pictures = pictures;

        if (addToCartButton.getActionListeners().length > 0) {
            addToCartButton.removeActionListener(addToCartButton.getActionListeners()[0]);
        }

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.getInstance().getCurrentUser() == null) {
                    JOptionPane.showMessageDialog(null, "You are not logged in");
                    return;
                }
                Cart.getOrCreate(Controller.getInstance().getCurrentUser()).addProduct(product, 1);
                JOptionPane.showMessageDialog(null, "The product was added to cart");
            }
        });

        relatedProductsList = Product.getRealatedProducts(product);
        commonPrint();
    }

    private void commonPrint()
    {
        Map<Integer,List<Picture>> relatedProductsPictures = new HashMap<Integer, List<Picture>>();
        for(Product p : relatedProductsList)
            relatedProductsPictures.put(p.getId(), Picture.getProdcutPicture(p.getId()));

        int qty = relatedProductsList.size();
        if (qty-- > 0){
            name1.setText(relatedProductsList.get(0).getName());
            if(relatedProductsPictures.containsKey(relatedProductsList.get(0).getId())
                    && (relatedProductsPictures.get(relatedProductsList.get(0).getId()).size() > 0)) {

                Image img1 = relatedProductsPictures.get(relatedProductsList.get(0).getId()).get(0).getImage();
                Image scaled1 = img1.getScaledInstance(150, 150, Image.SCALE_AREA_AVERAGING);
                relatedPicture1.setIcon(new ImageIcon(scaled1));

            }

        }
        else{
            related1.setVisible(false);
            relatedProducts.setVisible(false);
        }

        if (qty-- > 0){
            name2.setText(relatedProductsList.get(1).getName());


            if(relatedProductsPictures.containsKey(relatedProductsList.get(1).getId())
                    && (relatedProductsPictures.get(relatedProductsList.get(1).getId()).size() > 0)) {
                Image img2 = relatedProductsPictures.get(relatedProductsList.get(1).getId()).get(0).getImage();
                Image scaled2 = img2.getScaledInstance(150, 150, Image.SCALE_AREA_AVERAGING);
                relatedPicture2.setIcon(new ImageIcon(scaled2));
            }
        }
        else{
            related2.setVisible(false);
        }

        if (qty > 0){
            name3.setText(relatedProductsList.get(2).getName());


            if(relatedProductsPictures.containsKey(relatedProductsList.get(2).getId())
                    && (relatedProductsPictures.get(relatedProductsList.get(2).getId()).size() > 0)) {
                Image img3 = relatedProductsPictures.get(relatedProductsList.get(2).getId()).get(0).getImage();
                Image scaled3 = img3.getScaledInstance(150, 150, Image.SCALE_AREA_AVERAGING);
                relatedPicture3.setIcon(new ImageIcon(scaled3));
            }
        }
        else{
            related3.setVisible(false);
        }

        description.setText(product.getDescription());
        vendidos.setText(product.getSold() + " Sold units");


        if(pictures.size() > 0) {
            Image img1 = pictures.get(0).getImage();
            Image scaled1 = img1.getScaledInstance(picture1.getWidth(), picture1.getHeight(), Image.SCALE_AREA_AVERAGING);

            picture1.setIcon(new ImageIcon(scaled1));
        }

        if(pictures.size() > 1) {
            Image img2 = pictures.get(1).getImage();
            Image scaled2 = img2.getScaledInstance(picture2.getWidth(), picture2.getHeight(), Image.SCALE_AREA_AVERAGING);

            picture2.setIcon(new ImageIcon(scaled2));
        }


        this.product = product;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        stock.setText(product.getStock() + " units left                        ");
        rating.setText("Rate: " + (df.format(product.getRating())) + "/5               ");


    }

    private void setActionListners(){
        relatedPicture1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product prod = relatedProductsList.get(0);
                System.out.println("the prodcut is " + prod.getId());
                Controller.getInstance().getView().changeProduct(prod);

            }
        });

        relatedPicture2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product prod = relatedProductsList.get(1);
                System.out.println("the prodcut is " + prod.getId());
                Controller.getInstance().getView().changeProduct(prod);

            }
        });


        relatedPicture3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product prod = relatedProductsList.get(2);
                System.out.println("the prodcut is " + prod.getId());
                Controller.getInstance().getView().changeProduct(prod);
            }
        });
    }





    public void cleanProduct() {
        name.setText("");
        precio.setText("");
        description.setText("");
        stock.setText("");
        rating.setText("");
        vendidos.setText("");

        picture1.setIcon(null);
        picture2.setIcon(null);

        relatedPicture1.setIcon(null);
        relatedPicture2.setIcon(null);
        relatedPicture3.setIcon(null);

        name1.setText("");
        name2.setText("");
        name3.setText("");
    }

    public JButton getREVIEWSButton() {
        return verOpinionesButton;
    }

    public Product getProduct() {
        return product;
    }

}
