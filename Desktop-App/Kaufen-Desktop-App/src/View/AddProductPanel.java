package View;

import Controller.Controller;
import Controller.InputController;
import Controller.ProductPack;
import Model.Category;
import Model.Picture;
import Model.Product;
import Model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddProductPanel {
    private InputController inputController;
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JButton SEARCHbutton;
    private JPanel footnote;
    private JPanel middle;
    private JTextField nombre;
    private JTextField cantidad;
    private JTextField descripcion;
    private JTextField precio;
    private JButton foto2;
    private JButton foto1;
    private JButton publishButton;
    private JButton cancelButton;
    private JLabel LOGO;
    private JPanel buttons;
    private JComboBox categories;
    private View.ViewSwapper vs;
    private List<Picture> pictures;


    public AddProductPanel(final View.ViewSwapper vs, final InputController inputController) {
        this.vs = vs;
        pictures = new ArrayList<>();
        for (Category c : Category.list()) {
            categories.addItem(c);
        }

        foto1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j=new JFileChooser();
                j.setFileSelectionMode(JFileChooser.FILES_ONLY);//solo archivos y no carpetas
                int estado=j.showOpenDialog(null);
                if(estado== JFileChooser.APPROVE_OPTION){

                    Picture pic = new Picture(0, 0, "", j.getSelectedFile());
                    pictures.add(pic);
                }
            }
        });
        foto2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j=new JFileChooser();
                j.setFileSelectionMode(JFileChooser.FILES_ONLY);//solo archivos y no carpetas
                int estado=j.showOpenDialog(null);
                if(estado== JFileChooser.APPROVE_OPTION){

                        Picture pic = new Picture(0, 0, "", j.getSelectedFile());
                        pictures.add(pic);
                }
            }
        });
        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Category category = (Category) categories.getSelectedItem();
                ProductPack productPack = new ProductPack(nombre.getText(), null, precio.getText(), cantidad.getText()
                        , descripcion.getText(), category);
                int errorCode = inputController.checkAll(productPack);
                if (errorCode == 0) {
                    String name = nombre.getText();
                    Double price = Double.parseDouble(precio.getText());
                    Integer stock = Integer.parseInt(cantidad.getText());
                    String description = descripcion.getText();
                    User user = Controller.getInstance().getModel().getUser();
                    Product product = Product.create(name, description, price, user, "thumbnail", stock, category);
                    for(Picture p : pictures)
                        Picture.create(product.getId(), p.getPath(), p.getFile() );
                    JOptionPane.showMessageDialog(null, "The product was published");
                    pictures  = new ArrayList<>();
                    vs.changeView("profileMenuPanel", null);
                } else {
                    createProductErrorMessage(errorCode);
                    //vs.changeView("profileMenuPanel",null);

                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pictures  = new ArrayList<>();
                vs.changeView("profileMenuPanel", null);
            }
        });
    }

    public void createProductErrorMessage(int errorCode) {
        switch (errorCode) {
            case 21:
                JOptionPane.showMessageDialog(null, "Incorrect name");
                break;
            case 22:
                JOptionPane.showMessageDialog(null, "Incorrect quantity");
                break;
            case 23:
                JOptionPane.showMessageDialog(null, "Incorrect price");
                break;
            case 24:
                JOptionPane.showMessageDialog(null, "Incorrect description");
                break;
            case 25:
                JOptionPane.showMessageDialog(null, "Incorrect category");
                break;
            default:
                throw new NotImplementedException();
        }
    }

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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
