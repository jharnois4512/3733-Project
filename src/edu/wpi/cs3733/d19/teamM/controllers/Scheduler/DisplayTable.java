package edu.wpi.cs3733.d19.teamM.controllers.Scheduler;

import javafx.beans.property.StringProperty;
import javafx.beans.property.*;

/**
 * This controller deals with the display tables
 */
public class DisplayTable {

    //Create the instances of the different aspects of the table
    private StringProperty room;
    private StringProperty capacity;
    private StringProperty notes;
    private StringProperty roomType;
    private StringProperty type;
    private IntegerProperty requestid;
    private StringProperty date;
    private StringProperty filledBy;
    private StringProperty roomid;
    private StringProperty starttime;
    private StringProperty endtime;
    private StringProperty user;


    /**
     * The constructor for the display table that creates the new properties
     */
    public DisplayTable(){
        this.room = new SimpleStringProperty();
        this.capacity = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
        this.roomType = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.requestid = new SimpleIntegerProperty();
        this.date = new SimpleStringProperty();
        this.filledBy = new SimpleStringProperty();
        this.starttime = new  SimpleStringProperty();
        this.endtime = new  SimpleStringProperty();
        this.user = new SimpleStringProperty();


    }

    /**
     * This is for getting the room property
     * @return StringProperty: The room string
     */
    public StringProperty roomProperty(){
        return room;
    }

    /**
     * This is for getting the notes property
     * @return StringProperty: The notes string
     */
    public StringProperty notesProperty(){
        return notes;
    }

    /**
     * This is for getting the type property
     * @return StringProperty: The type string
     */
    public StringProperty typeProperty(){
        return type;
    }
    public StringProperty roomTypeProperty(){
        return roomType;
    }
    public StringProperty capacityProperty(){
        return capacity;
    }
    
    public IntegerProperty idProperty(){return requestid;}
    public StringProperty dateProperty(){return date;}
    public StringProperty filledByProperty(){return filledBy;}
    public StringProperty starttimeProperty() { return starttime;}
    public StringProperty endtimeProperty() { return endtime;}
    public StringProperty userProperty(){ return user;}


    //getters
    public String getRoom(){
        return roomProperty().get();
    }
    public String getCapacity(){
        return capacityProperty().get();
    }
    public String getNotes(){
        return notesProperty().get();
    }
    public String setRoomType(){
        return roomTypeProperty().get();
    }

    public String setType(){
        return roomTypeProperty().get();
    }

    public Integer  getID(){
        return idProperty().get();
    }
    public String getDate(){return dateProperty().get();}
    public String getFilledBy(){return filledByProperty().get();}
    public String getUser(){return userProperty().get();}




    //setters
    public void setRoom(String room){
        this.roomProperty().set(room);
    }
    public void setCapacity(String capacity){
        this.capacityProperty().set(capacity);
    }
    public void setRoomType(String type){
        this.roomTypeProperty().set(type);
    }
    public void setType(String type){
        this.typeProperty().set(type);
    }

    public void setNotes(String notes){
        this.notesProperty().set(notes);
    }

    public void setId(Integer id ){this.idProperty().set(id);}

    public void setDate(String Date){this.dateProperty().set(Date);}
    public void setStartTime(String start){this.starttime.set(start);}
    public void setEndTime(String end){this.endtime.set(end);}

    public void setFilledBy(String filledBy){this.filledByProperty().set(filledBy);}

    public void setUser(String user){this.user.set(user);}

}
