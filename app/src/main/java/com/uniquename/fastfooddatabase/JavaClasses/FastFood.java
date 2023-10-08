package com.uniquename.fastfooddatabase.JavaClasses;

public class FastFood {
    private int id;
    private String title;
    private String description;

    public FastFood(int id, String title, String description){
        this.id=id;
        this.title=title;
        this.description=description;
    }

    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }
}
