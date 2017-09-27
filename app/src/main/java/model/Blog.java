package model;

/**
 * Created by Vu Khac Hoi on 9/27/2017.
 */

public class Blog {
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
    public Blog(String title, String description, String image, int pos, String status) {
        Title = title;
        Description = description;
        Image = image;
        Pos = pos;
        Status = status;
    }

}
