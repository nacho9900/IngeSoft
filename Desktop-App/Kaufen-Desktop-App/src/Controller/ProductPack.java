package Controller;

import Model.Category;
import Model.Picture;

public class ProductPack extends InputPack {
    private Picture picture;
    public ProductPack(String productName, Picture pic, String productPrice, String productAmount, String description, Category category){
        super();
        picture=pic;
        this.formEntries.put(21,new FormEntry(productName, 21));
        this.formEntries.put(22,new FormEntry(productAmount, 22));
        this.formEntries.put(23,new FormEntry(productPrice, 23));
        this.formEntries.put(24,new FormEntry(description, 24));
        this.formEntries.put(25,new DataEntry(category, 25));
    }
}
