package com.example.iotools.model;


import android.os.Parcel;
import android.os.Parcelable;

public class gridList implements Parcelable {

    String id;
    String picture;
    String created_date;
    String note;
    String topic;
    int type;

    public gridList(String note, String id, String picture, String created_date, String topic, int type) {

        this.id = id;
        this.picture = picture;
        this.created_date = created_date;
        this.note = note;
        this.topic = topic;
        this.type = type;
    }

    public gridList() {

        this.id = null;
        this.picture = null;
        this.created_date = null;
        this.note = null;
        this.topic = null;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) { this.note = note; }

    public String get_id() {
        return id;
    }

    public void set_id(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public gridList(Parcel in){
        String[] data = new String[6];

        in.readStringArray(data);
        this.note = data[0];
        this.id = data[1];
        this.picture = data[2];
        this.created_date = data[3];
        this.topic = data[4];
        this.type = Integer.parseInt(data[5]);

    }


    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { dest.writeStringArray(new String[] {
                this.note,
                this.id,
                this.picture,
                this.created_date,
                this.topic,
                String.valueOf(this.type)});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public gridList createFromParcel(Parcel in) {
            return new gridList(in);
        }

        public gridList[] newArray(int size) {
            return new gridList[size];
        }
    };
}


