package View;

import Controller.CardPack;
import Controller.Controller;
import Controller.InputController;
import Model.Card;
import Model.Cart;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class PaymentOptionsPanel {
    private InputController inputController;
    private JPanel footnote;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JLabel KWIKlabel;
    private JButton CARRITObutton;
    private JButton SEARCHbutton;
    private JPanel middle;
    private JLabel loginLabel;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JButton newCardButton;
    private JComboBox cardSelector;
    private JPanel mainpanel;
    private JButton confirmPaymentButton;
    private JLabel LOGO;
    private View.ViewSwapper vs;
    private ArrayList<Card> cards = new ArrayList<>();
    private Cart cart;


    public PaymentOptionsPanel(final View.ViewSwapper vs, final InputController inputController) {
        this.vs = vs;

        confirmPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = getCard();
                if (card == null) {
                    JOptionPane.showMessageDialog(mainpanel,
                            "Se debe seleccionar una tarjeta.",
                            "",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("Closed with cart:" + cart.getId());
                    cart.setCard(card);
                    cart.close();
                    setCards();
                    vs.changeView("searchPanel", null);
                }
            }
        });

        newCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCardCreator(inputController);
                setCards();
            }
        });

        EQUIPObutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vs.changeView("adminPanel", null);
            }
        });

        VISIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vs.changeView("misProductosPanel", null);
            }
        });

        CARRITObutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vs.changeView("shoppingcartPanel", null);
            }
        });

        SEARCHbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vs.changeView("searchPanel", null);
            }
        });
    }

    public void setCards() {
        cardSelector.removeAllItems();
        cards.clear();
        for (Card card : Controller.getInstance().getCurrentUser().getCards()) {
            cards.add(card);
            cardSelector.addItem(card.getNumber());

        }
    }

    private static void displayCardCreator(InputController inputController) {
        JTextField name = new JTextField();
        JTextField surname = new JTextField();
        JTextField number = new JTextField();
        JTextField month = new JTextField();
        JTextField year = new JTextField();
        JTextField code = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(name);
        panel.add(new JLabel("Surname:"));
        panel.add(surname);
        panel.add(new JLabel("Expiration month:"));
        panel.add(month);
        panel.add(new JLabel("Expiration year:"));
        panel.add(year);
        panel.add(new JLabel("Credit card number:"));
        panel.add(number);
        panel.add(new JLabel("Security Code:"));
        panel.add(code);
        int result = JOptionPane.showConfirmDialog(null, panel, "New Card",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            CardPack cardStruct = new CardPack(name.getText(), surname.getText(), month.getText(), year.getText(),
                    number.getText(), code.getText());
            int errorcode = inputController.checkAll(cardStruct);
            if (errorcode == 0) {
                inputController.addCard(cardStruct);
                JOptionPane.showMessageDialog(null, "Added card successfully");
                System.out.println("anda");
            } else {
                createUserErrorMessage(errorcode);
                System.out.println("no");
            }
        } else {
            System.out.println("Cancelled");
        }
    }

    private static void createUserErrorMessage(int errorcode) {
        switch (errorcode) {
            case 31:
                JOptionPane.showMessageDialog(null, "Incorrect name");
                break;
            case 32:
                JOptionPane.showMessageDialog(null, "Incorrect surname");
                break;
            case 33:
                JOptionPane.showMessageDialog(null, "Incorrect month");
                break;
            case 34:
                JOptionPane.showMessageDialog(null, "Incorrect year");
                break;
            case 35:
                JOptionPane.showMessageDialog(null, "Incorrect code");
                break;
            case 36:
                JOptionPane.showMessageDialog(null, "Incorrect number");
                break;
            default:
                throw new NotImplementedException();
        }

    }

    public Card getCard() {
        if (cardSelector.getSelectedIndex() > cards.size() || cardSelector.getSelectedIndex() < 0)
            return null;
        return cards.get(cardSelector.getSelectedIndex());
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public static boolean validateCard(String name, String surname, String day, String month, String number, String code) {
        if (validateString(name) && validateString(surname) && validateInteger(day) && validateInteger(month) && validateInteger(number) && validateInteger(code)) {
            return false;
        }
        return true;
    }

    public static boolean validateString(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("")) {
            return false;
        }
        if (str.matches("[a-zA-Z]+")) {
            return true;
        }
        return false;
    }

    public static boolean validateInteger(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("")) {
            return false;
        }
        if (str.matches("[0-9]+")) {
            return true;
        }
        return false;
    }

    public JButton getPERFILButton() {
        return PERFILButton;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}
