package Controller;


import Model.Data;

public class DataEntry extends MyEntry{
    private Data data;
    private int code;
    public DataEntry(Data data, int code){
        this.data = data;
        this.code=code;
    }
    public Data getItem(){
        return data;
    }
    public int getCode(){
        return code;
    }
}
