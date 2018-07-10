package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileMenuPanel {
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JButton SEARCHbutton;
    private JPanel footnote;
    private JPanel middle;
    private JButton agregarProductoButton;
    private JButton verDatosButton;
    private JButton logout;
    private JButton productosComprados;
    private JLabel LOGO;
    private View.ViewSwapper vs;

    public ProfileMenuPanel(final View.ViewSwapper vs, final Controller controller) {
        this.vs = vs;
        verDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vs.changeView("profilePanel", null);
            }
        });
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vs.changeView("addProductPanel", null);
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getModel().replaceUser(null);
                controller.hideAdmin();
                vs.changeView("profileLoginPanel", null);
            }
        });
        productosComprados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getView().purchasesPanel.setLoggedInConfigurations();
                vs.changeView("purchasesPanel", null);
            }
        });
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
