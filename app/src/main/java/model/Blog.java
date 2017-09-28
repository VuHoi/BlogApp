package model;

import java.io.Serializable;

/**
 * Created by Vu Khac Hoi on 9/27/2017.
 */

public class Blog implements Serializable {
   String Title;
    String Description;
    String Image;



    int Pos;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getPos() {
        return Pos;
    }

    public void setPos(int pos) {
        Pos = pos;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    String Status;


    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }



    String User;

    public Blog(String title, String description, String image, int pos, String status, String user, String ID) {
        Title = title;
        Description = description;
        Image = image;
        Pos = pos;
        Status = status;
        User = user;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    String ID;

}
