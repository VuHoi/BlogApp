package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vukhachoi.blogapp.R;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.ByteArrayOutputStream;
import java.util.List;

import model.Blog;
import sql.Databasehelper;

/**
 * Created by Vu Khac Hoi on 9/27/2017.
 */

public class StatusAdapter extends ArrayAdapter<Blog> {
    Activity context;
    int resource;
    List<Blog> objects;
    Databasehelper myDatabase;
    SQLiteDatabase database;
    Button btnchonhinh,btnDang;
    EditText edtTitle,edtInput;
    Spinner spnStatus;
    Dialog dialog;
    ShareDialog shareDialog;
    ImageView imgtest;
    public StatusAdapter(Activity context, int resource, List<Blog> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView txttitle = row.findViewById(R.id.txttitle);

        TextView txtDiscription = row.findViewById(R.id.txtDiscription);
        ImageView imgStatus = row.findViewById(R.id.imgStatus);
        shareDialog=new ShareDialog(context);

        myDatabase = new Databasehelper(context);
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();

        final ImageView  xxx=row.findViewById(R.id.xxx);


        final Blog blog = this.objects.get(position);
        txttitle.setText(blog.getTitle());

        txtDiscription.setText(blog.getDescription());

        byte[] decodedString = Base64.decode(blog.getImage(), Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        imgStatus.setImageBitmap(decodedByte);
        if(blog.getUser().toString().equals("admin")) {

            xxx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context, xxx);
                    popup.getMenuInflater()
                            .inflate(R.menu.menu_item, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.itUpdate) {

                                dialog = new Dialog(context);

                                dialog.setContentView(R.layout.dialog_input);

                                dialog.setTitle("Cập Nhật");


                                btnDang = dialog.findViewById(R.id.btnDang);
                                edtTitle = dialog.findViewById(R.id.edttitle);
                                edtInput = dialog.findViewById(R.id.edtInput);
                                imgtest = dialog.findViewById(R.id.imghinhanh);
                                spnStatus = dialog.findViewById(R.id.spnStatus);
                                Button btnDang = (Button) dialog.findViewById(R.id.btnDang);
                                edtTitle.setText(blog.getTitle());

                                edtInput.setText(blog.getDescription());
                                imgtest.setImageBitmap(decodedByte);

                                if(blog.getStatus().equals("Draff"))
                                {
                                    spnStatus.setSelection(0);
                                }
                                else if(blog.getStatus().equals("Pending"))
                                {
                                    spnStatus.setSelection(1);
                                }
                                else if(blog.getStatus().equals("Published"))
                                {
                                    spnStatus.setSelection(2);
                                }
                                else {
                                    spnStatus.setSelection(0);
                                }
                                // khai báo control trong dialog để bắt sự kiện
                                btnDang.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        Bitmap bitmap = ((BitmapDrawable) imgtest.getDrawable()).getBitmap();
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                                        try {
                                            ContentValues values = new ContentValues();
                                            values.put("title", edtTitle.getText().toString());
                                            values.put("description", edtInput.getText().toString());

                                            values.put("image", encoded);
                                            values.put("pos", 1);
                                            values.put("status", spnStatus.getSelectedItem().toString());

                                            database.updateWithOnConflict("Blog", values, "id=?", new String[]{blog.getID()}, SQLiteDatabase.CONFLICT_FAIL);
                                            context.recreate();
                                            dialog.dismiss();
                                        } catch (Exception e) {
                                            Toast.makeText(context, "Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                // bắt sự kiện cho nút đăng kí
                                dialog.show();
                            } else if (item.getItemId() == R.id.itDelete) {
                                try {
                                    database.delete("Blog", "id=?", new String[]{blog.getID()});
                                    context.recreate();
                                } catch (Exception e) {
                                }

                            }

                            return true;
                        }
                    });
                    popup.show();
                }
            });
        }else if(blog.getUser().toString().equals("User"))
        {
xxx.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(context, xxx);
        popup.getMenuInflater()
                .inflate(R.menu.menu_share, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(decodedByte)
                        .setCaption("hahaha")
                        .build();

                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();

                    shareDialog.show(sharePhotoContent);

                }

                return true;
            }
        });
        popup.show();
    }
});
        }
        return row;
    }
}