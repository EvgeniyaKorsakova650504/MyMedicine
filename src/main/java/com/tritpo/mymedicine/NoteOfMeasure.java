package com.tritpo.mymedicine;

import java.util.List;

class NoteOfMeasure {

    private String name;
    private int id;
    private List<String> time;
    private List<String> date;
    private String description;

    NoteOfMeasure (String name, List<String> time, List<String> date,String description){
        this.name=name;
        this.time=time;
        this.date=date;
        this.description=description;
    }


    public List<String> getDate(){
        return date;
    }


    public void show(){}  //TODO

}
