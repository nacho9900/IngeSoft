package Controller;

public class CardPack extends InputPack {
    public CardPack(String name, String surname, String exp_month, String exp_year, String number, String secCode){
        this.formEntries.put(31,new FormEntry(name, 31));
        this.formEntries.put(32,new FormEntry(surname, 32));
        this.formEntries.put(33,new FormEntry(exp_month, 33));
        this.formEntries.put(34,new FormEntry(exp_year, 34));
        this.formEntries.put(35,new FormEntry(secCode, 35));
        this.formEntries.put(36,new FormEntry(number, 36));
    }
}
