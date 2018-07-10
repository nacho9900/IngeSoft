package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class InputPack {
    protected Map<Integer,MyEntry> formEntries;
    public InputPack(){
        formEntries= new HashMap<Integer,MyEntry>();
    }

    public Map<Integer,MyEntry> getFormEntries() {
        return formEntries;
    }
    @Override
    public String toString(){
        String ret = new String();

        for(Integer i: formEntries.keySet()){
            ret+=(formEntries.get(i).getCode()+": "+formEntries.get(i).getItem().toString()+" ");
        }
        return ret;
    }
}
