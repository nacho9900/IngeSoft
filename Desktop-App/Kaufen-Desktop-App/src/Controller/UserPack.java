package Controller;

import Model.City;
import Model.Country;
import Model.DocType;
import Model.Province;

public class UserPack extends InputPack {
    public UserPack(String name, String userName, String password, Country country, City neighbourhood,
                    String street, String streetNumber, String apartment, String phoneNumber, String surname, String email, String confirmPassword, Province state,
                    String postalCode, DocType documentType, String document){
        super();
        this.formEntries.put(1,new FormEntry(name, 1));
        this.formEntries.put(2,new FormEntry(userName, 2));
        this.formEntries.put(3,new FormEntry(password, 3));
        this.formEntries.put(4,new DataEntry(country, 4));
        this.formEntries.put(5,new DataEntry(neighbourhood, 5));
        this.formEntries.put(6,new FormEntry(street, 6));
        this.formEntries.put(7,new FormEntry(streetNumber, 7));
        this.formEntries.put(8,new FormEntry(apartment, 8));
        this.formEntries.put(9,new FormEntry(phoneNumber, 9));
        this.formEntries.put(10,new FormEntry(surname, 10));
        this.formEntries.put(11,new FormEntry(email, 11));
        this.formEntries.put(12,new DataEntry(state, 12));
        this.formEntries.put(13,new DataEntry(documentType, 13));
        this.formEntries.put(14,new FormEntry(document, 14));
        this.formEntries.put(15,new FormEntry(postalCode, 15));
        this.formEntries.put(16,new FormEntry(confirmPassword, 16));
    }
}
