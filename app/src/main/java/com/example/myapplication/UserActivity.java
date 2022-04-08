package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

class User {
    private int mauser;
    private String ten;

    public User(String ten) {
        this.ten = ten;
    }

    public int getMauser() {
        return mauser;
    }

    public void setMauser(int mauser) {
        this.mauser = mauser;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public User(int mauser, String ten) {
        this.mauser = mauser;
        this.ten = ten;
    }

    @Override
    public String toString() {
        return ten;
    }
}

class CSDL extends SQLiteOpenHelper {

    public CSDL(@Nullable Context context) {
        super(context, "CSDL.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USER(" +
                "MAUSER INTEGER primary key AUTOINCREMENT," +
                "TEN NVARCHAR(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<User> dsUser() {
        ArrayList<User> userArrayList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM USER", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            userArrayList.add(new User(cursor.getInt(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        return userArrayList;
    }

    public void themUser(User user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TEN", user.getTen());
        database.insert("USER", null, values);
        database.close();
    }

    public void xoaUser(int id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("USER", "MAUSER=?", new String[]{id + ""});
        database.close();
    }

    public void capNhat(User user, int id) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TEN", user.getTen());
        values.put("MAUSER", id);
        database.update("USER", values, "MAUSER = ?", new String[]{id + ""});
    }
}

public class UserActivity extends AppCompatActivity {

    EditText edt_ten;
    Button btn_them, btn_sua, btn_xoa;
    ListView listView;
    ArrayAdapter<User> adapter;
    int mauser = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        CSDL csdl = new CSDL(getApplicationContext());
        edt_ten = findViewById(R.id.edt_ten);
        listView = findViewById(R.id.lv_user);

//đổ dữ liệu ra listview
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, csdl.dsUser());
        listView.setAdapter(adapter);

        //toast nội dung khi chọn 1 người
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User nd = csdl.dsUser().get(i);
                Toast.makeText(getApplicationContext(), nd.toString(), Toast.LENGTH_LONG).show();
                mauser = nd.getMauser();
            }
        });


        btn_them = findViewById(R.id.btn_them);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                csdl.themUser(new User(edt_ten.getText().toString()));
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, csdl.dsUser());
                listView.setAdapter(adapter);
            }
        });


        btn_sua = findViewById(R.id.btn_sua);
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mauser != -1) {
                    csdl.capNhat(new User(edt_ten.getText().toString()), mauser);
                    adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, csdl.dsUser());
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "chưa chọn user cần sửa", Toast.LENGTH_LONG).show();
                }
            }
        });


        btn_xoa = findViewById(R.id.btn_xoa);
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mauser != -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                    builder.setTitle("Bạn có muốn xóa không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            csdl.xoaUser(mauser);
                            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, csdl.dsUser());
                            listView.setAdapter(adapter);
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog= builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "chưa chọn user cần sửa", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}