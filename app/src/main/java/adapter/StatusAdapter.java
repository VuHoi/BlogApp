package adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vukhachoi.blogapp.R;

import java.util.List;

import model.Blog;

/**
 * Created by Vu Khac Hoi on 9/27/2017.
 */

public class StatusAdapter extends ArrayAdapter<Blog> {
    Activity context;
    int resource;
    List<Blog> objects;
    public StatusAdapter(Activity context, int resource, List<Blog> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView txttitle = row.findViewById(R.id.txttitle);
        Spinner spnStatus1=row.findViewById(R.id.spnStatus1);
        TextView txtDiscription = row.findViewById(R.id.txtDiscription);
        ImageView imgStatus = row.findViewById(R.id.imgStatus);




        final Blog blog = this.objects.get(position);
        txttitle.setText(blog.getTitle());
        if(blog.getStatus().equals("Draff"))
        {
            spnStatus1.setSelection(0);
        }
        else if(blog.getStatus().equals("Pending"))
        {
            spnStatus1.setSelection(1);
        }
        else if(blog.getStatus().equals("Published"))
        {
            spnStatus1.setSelection(2);
        }
        else {
            spnStatus1.setSelection(0);
        }

        txtDiscription.setText(blog.getDescription());

        byte[] decodedString = Base64.decode(blog.getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imgStatus.setImageBitmap(decodedByte);

        return row;
    }
}