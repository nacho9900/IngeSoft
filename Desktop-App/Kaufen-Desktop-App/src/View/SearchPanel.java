package View;

import Model.Category;
import Model.City;
import Model.Country;
import Model.Province;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPanel {
    private JPanel mainpanel;
    private JLabel LOGO;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JPanel footnote;
    private JPanel header;
    private JTextField searchBox;
    private JPanel middle;
    private JComboBox categories;
    private JComboBox cities;
    private JComboBox provinces;
    private JComboBox countries;
    private JCheckBox searchByLocationCheckBox;
    private JCheckBox searchByCategoryCheckBox;
    private JButton SEARCHbutton;

    public SearchPanel() {
        searchByCategoryCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchByCategoryCheckBox.isSelected()) {
                    categories.setEnabled(true);
                } else {
                    categories.setEnabled(false);
                }
            }
        });

        searchByLocationCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchByLocationCheckBox.isSelected()) {
                    countries.setEnabled(true);
                    provinces.setEnabled(true);
                    cities.setEnabled(true);
                } else {
                    countries.setEnabled(false);
                    provinces.setEnabled(false);
                    cities.setEnabled(false);
                }

            }
        });

        for (Country c : Country.list()) {
            countries.addItem(c);
        }

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

        for (Category c : Category.list()) {
            categories.addItem(c);
        }
    }


    public JButton getVISIONButton() {
        return VISIONButton;
    }

    public JButton getCARRITObutton() {
        return CARRITObutton;
    }

    public JTextField getSearchBox() {
        return searchBox;
    }

    public Category getSearchCategory() {
        return (Category) categories.getSelectedItem();
    }

    public Country getSearchCountry() {
        return (Country) countries.getSelectedItem();
    }

    public Province getSearchProvince() {
        return (Province) provinces.getSelectedItem();
    }

    public City getSearchCity() {
        return (City) cities.getSelectedItem();
    }

    public boolean isfilterByLocation() {
        return searchByLocationCheckBox.isSelected();
    }

    public boolean isfilterByCategory() {
        return searchByCategoryCheckBox.isSelected();
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
