package Controller;

public class FormEntry extends MyEntry{
    private String string;
    private int code;
    public FormEntry(String string, int code){
        this.string=string;
        this.code=code;
    }
    public String getItem(){
        return string;
    }
    public int getCode(){
        return code;
    }
}
