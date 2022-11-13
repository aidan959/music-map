/*****************************************************************************************
*
*     Class to store a class entity
*     This class wasn't required for the lab, but it;s a better class
*     design to have a class for your data entity so that you have getters/ setters to retrieve values etc
*     Oct 2022
*
*******************************************************************************************/
package com.aidand.musicmap.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Listen implements Parcelable
{
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Listen createFromParcel(Parcel in) {
            return new Listen(in);
        }

        public Listen[] newArray(int size) {
            return new Listen[size];
        }
    };
    public Listen(Parcel in){
        this._name = in.readString();
        this._description = in.readString();
        this._status =  in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._name);
        dest.writeString(this._description);
        dest.writeString(this._status);
    }
    int _id;  
    String _name;  
    String _description;
    String _status;

    public Listen(){   }

    public Listen(String name, String description, String status){
        this._name = name;
        this._description= _description;
        this._status= status;
    }
  

    public int getID(){  
        return this._id;  
    }  
  
    public void setID(int id){  
        this._id = id;  
    }

    public void setDescription(String description)
    {
        this._description = description;

    }

    public void setStatus(String status)
    {
        this._status = status;

    }

    public String getName()
    {

        return this._name;  
    }

    public String getDescription()
    {

        return this._description;
    }

    public String getStatus()
    {

        return this._status;
    }

    public void setName(String name){  
        this._name = name;  
    }  
  
    }