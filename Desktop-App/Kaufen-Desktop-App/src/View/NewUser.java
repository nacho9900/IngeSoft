package View;

import Controller.InputController;
import Controller.UserPack;
import Model.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewUser {
    private InputController inputController;
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JPanel middle;
    private JLabel registrationLabel;
    private JPanel footnote;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel usernameLabel;
    private JButton SEARCHbutton;
    private JButton crearButton;
    private JLabel mainPhoneLabel;
    private JLabel steetNumberLabel;
    private JLabel documentLabel;
    private JLabel postalCodeLabel;
    private JLabel docTypeLabel;
    private JLabel passwordLabel;
    private JLabel confirmedPasswordLabel;
    private JLabel emailLabel;
    private JLabel ProvinceLabel;
    private JLabel CountryLabel;
    private JLabel NeighbourhoodLabel;
    private JTextField username;
    private JTextField name;
    private JTextField password;
    private JTextField surname;
    private JTextField country;
    private JTextField street;
    private JTextField streetNumber;
    private JComboBox apartment;
    private JTextField email;
    private JTextField postalCode;
    private JTextField neighborhood;
    private JTextField mainPhone;
    private JTextField confirmedPassword;
    private JTextField document;
    private JTextField province;
    private JComboBox<Country> countries;
    private JComboBox<Province> provinces;
    private JComboBox<City> cities;
    private JComboBox<DocType> docType;
    private JLabel streetLabel;
    private JLabel apartmentLabel;
    private JLabel LOGO;
    private boolean isCreating;
    private ActionListener createListener;

    public void setCreating(boolean creating) {
        isCreating = creating;
        System.out.println("Creating is: " + creating);
        if (creating) {
            crearButton.setText("Create");
            crearButton.removeActionListener(modifyListener);
            crearButton.addActionListener(createListener);

        } else {
            setPreviousValues();
            crearButton.setText("Modify");
            crearButton.removeActionListener(createListener);
            crearButton.addActionListener(modifyListener);
        }
    }

    private void setPreviousValues() {
        User currentUser = inputController.getController().getCurrentUser();
        username.setText(currentUser.getUsername());
        name.setText(currentUser.getName());
        password.setText(currentUser.getPassword());
        surname.setText(currentUser.getSurname());
        street.setText(currentUser.getStreet());
        streetNumber.setText(Integer.toString(currentUser.getStreetNumber()));
        apartment.setSelectedItem(currentUser.getApartment());
        email.setText(currentUser.getEmail());
        postalCode.setText(currentUser.getPostCode());
        mainPhone.setText(currentUser.getPhone());
        confirmedPassword.setText(currentUser.getPassword());
        document.setText(currentUser.getdoc());
    }


    ActionListener modifyListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserPack userPack = new UserPack(name.getText(), username.getText(), password.getText(),
                    (Country) countries.getSelectedItem(), (City) cities.getSelectedItem(), street.getText(), streetNumber.getText(), (String)apartment.getSelectedItem(), mainPhone.getText(), surname.getText(),
                    email.getText(), confirmedPassword.getText(), (Province) provinces.getSelectedItem(), postalCode.getText(), (DocType) docType.getSelectedItem(),
                    document.getText());
            int errorcode = inputController.checkAll(userPack);
            if (errorcode == 0) {
                inputController.modifyUser(userPack, inputController.getController().getCurrentUser());
                JOptionPane.showMessageDialog(null, "User modified successfully!");
            } else {
                createUserErrorMessage(errorcode);
            }
        }
    };

    public NewUser(final InputController inputController, final View.ViewSwapper vs) {

        createListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserPack userPack = new UserPack(name.getText(), username.getText(), password.getText(),
                        (Country) (countries.getSelectedItem()), (City) cities.getSelectedItem(), street.getText(), streetNumber.getText(), (String)apartment.getSelectedItem(), mainPhone.getText(), surname.getText(),
                        email.getText(), confirmedPassword.getText(), (Province) provinces.getSelectedItem(), postalCode.getText(),
                        (DocType) (docType.getSelectedItem()), document.getText());
                int errorcode=inputController.checkAll(userPack);
                if (errorcode == 0) {
                    if (inputController.addUser(userPack) != null) {
                        vs.changeView("confirmUserPanel",null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Username or document taken, please try another one :(");
                    }
                } else {
                    createUserErrorMessage(errorcode);
                }

            }
        };

        for (DocType d : DocType.list()) {
            docType.addItem(d);
        }
        for (Country c : Country.list()) {
            countries.addItem(c);
        }
        apartment.addItem("Home number");
        apartment.addItem("Mobile phone number");
        this.inputController = inputController;
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name.getText();
            }
        });
        surname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surname.getText();
            }
        });
        username.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username.getText();
            }
        });
        password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                password.getText();
            }
        });
        countries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countries.getSelectedItem() != null) {
                    cities.removeAllItems();
                    provinces.removeAllItems();
                    for (Province province : Province.list((Country) countries.getSelectedItem())) {
                        provinces.addItem(province);
                    }
                }
            }
        });
        provinces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (provinces.getSelectedItem() != null) {
                    cities.removeAllItems();
                    for (City city : City.list((Province) provinces.getSelectedItem())) {
                        cities.addItem(city);
                    }
                }
            }
        });
        street.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                street.getText();
            }
        });

        streetNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                streetNumber.getText();
            }
        });

        apartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apartment.getSelectedItem();
            }
        });
        email.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                email.getText();
            }
        });
        postalCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postalCode.getText();
            }
        });
    }

    public void createUserErrorMessage(int errorcode) {
        switch (errorcode) {
            case 1:
                JOptionPane.showMessageDialog(null, "Incorrect name");
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Incorrect username");
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Incorrect password");
                break;
            case 4:
                JOptionPane.showMessageDialog(null, "Incorrect country");
                break;
            case 5:
                JOptionPane.showMessageDialog(null, "Incorrect city");
                break;
            case 6:
                JOptionPane.showMessageDialog(null, "Incorrect street");
                break;
            case 7:
                JOptionPane.showMessageDialog(null, "Incorrect street number");
                break;
            case 8:
                JOptionPane.showMessageDialog(null, "Incorrect phone type");
                break;
            case 9:
                JOptionPane.showMessageDialog(null, "Incorrect phone number");
                break;
            case 10:
                JOptionPane.showMessageDialog(null, "Incorrect surname");
                break;
            case 11:
                JOptionPane.showMessageDialog(null, "Incorrect email");
                break;
            case 12:
                JOptionPane.showMessageDialog(null, "Incorrect province");
                break;
            case 13:
                JOptionPane.showMessageDialog(null, "Incorrect document type");
                break;
            case 14:
                JOptionPane.showMessageDialog(null, "Incorrect document");
                break;
            case 15:
                JOptionPane.showMessageDialog(null, "Incorrect postal code");
                break;
            case 16:
                JOptionPane.showMessageDialog(null, "Incorrect confirm password");
                break;
            default:
                throw new NotImplementedException();
        }
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public void clearText() {
        name.setText("");
        username.setText("");
        password.setText("");
        street.setText("");
        streetNumber.setText("");
        apartment.setSelectedItem("");
        email.setText("");
        postalCode.setText("");
        surname.setText("");
        cities.removeAllItems();
        mainPhone.setText("");
        confirmedPassword.setText("");
        document.setText("");
        provinces.removeAllItems();

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

}
