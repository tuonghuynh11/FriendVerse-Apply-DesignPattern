package com.example.friendverse.Models.PrototypePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRegistry {
    private Map<String, PostGeneral> items;
    public PostRegistry(){
        this.items= new HashMap<>();
    }
    public void addItem(String id, PostGeneral postGeneral){
        if(items.containsKey(id)){
            System.out.println("Item is existed");
        }
        else{
            this.items.put(id,postGeneral);
        }
    }
    public PostGeneral getById(String id){
        if(!items.containsKey(id)){
            System.out.println("Item is not existed");
            return null;
        }
        return this.items.get(id);

    }

}
