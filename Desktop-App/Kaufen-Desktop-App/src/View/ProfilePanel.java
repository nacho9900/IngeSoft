package View;

import Controller.Controller;
import Model.Card;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePanel {
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JButton SEARCHbutton;
    private JPanel footnote;
    private JPanel middle;
    private JLabel registrationLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel CountryLabel;
    private JLabel streetLabel;
    private JLabel postalCodeLabel;
    private JLabel streetNumberLabel;
    private JLabel docTypeLabel;
    private JLabel documentLabel;
    private JButton modificarButton;
    private JLabel NeighbourhoodLabel;
    private JLabel mainPhoneLabel;
    private JLabel ProvinceLabel;
    private JTextField name;
    private JTextField surname;
    private JTextField username;
    private JTextField email;
    private JTextField country;
    private JTextField neighborhood;
    private JTextField street;
    private JTextField streetNumber;
    private JTextField apartment;
    private JTextField postalCode;
    private JTextField mainPhone;
    private JTextField docType;
    private JTextField document;
    private JTextField province;
    private JLabel apartmentLabel;
    private JLabel LOGO;
    private View.ViewSwapper vs;

    public ProfilePanel(final View.ViewSwapper vs, Controller controller) {
        this.vs = vs;

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vs.changeView("newUser", 0);
            }
        });
        if (Controller.getInstance().isLoggedIn())
            updateValues();

    }

    public void updateValues() {
        User currentUser = Controller.getInstance().getCurrentUser();
        username.setText(currentUser.getUsername());
        name.setText(currentUser.getName());
        surname.setText(currentUser.getSurname());
        country.setText(currentUser.getCountry().getName());
        street.setText(currentUser.getStreet());
        streetNumber.setText(Integer.toString(currentUser.getStreetNumber()));
        apartment.setText(currentUser.getApartment());
        email.setText(currentUser.getEmail());
        postalCode.setText(currentUser.getPostCode());
        neighborhood.setText(currentUser.getCity().getName());
        mainPhone.setText(currentUser.getPhone());
        docType.setText(currentUser.getDocType().getName());
        document.setText(currentUser.getdoc());
        province.setText(currentUser.getProvince().getName());
    }

    public JButton getVISIONButton() {
        return VISIONButton;
    }

    public JButton getCARRITObutton() {
        return CARRITObutton;
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public JButton getEQUIPObutton() {
        return EQUIPObutton;
    }

    public JButton getSEARCHbutton() {
        return SEARCHbutton;
    }

    public JButton getPERFILButton() {
        return PERFILButton;
    }


}
