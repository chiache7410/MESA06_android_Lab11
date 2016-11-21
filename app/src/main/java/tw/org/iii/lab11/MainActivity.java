package tw.org.iii.lab11;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private MyDBHelper dbHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.tv);
        dbHelper = new MyDBHelper(this,"iii",null,1);
        db = dbHelper.getReadableDatabase();
    }
    public void insert(View v){
        // INSERT INTO cust (cname,birthday,tel) VALUES ('Brad','1999-09-08','123');
        ContentValues data = new ContentValues();
        data.put("cname", "Brad");
        data.put("birthday", "1999-09-08");
        data.put("tel", "123");
        db.insert("cust",null,data);
        query(null);
    }
    public void delete(View v){
        db.delete("cust","id=? AND cname=?",new String[]{"3","Brad"});
        query(null);
    }
    public void update(View v){
        ContentValues data = new ContentValues();
        data.put("cname", "Mary");
        data.put("birthday", "2000-11-21");
        data.put("tel", "321");
        db.update("cust",data,"id=?",new String[]{"4"});
        query(null);
    }
    public void query(View v){
        textView.setText("");
        // SELECT * FROM cust ORDER BY cname DESC
        // db.execSQL("SELECT * FROM cust");
        Cursor cursor = db.query("cust",null,"id>?",new String[]{"3"},null,null,"cname DESC, birthday Desc");
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String cname = cursor.getString(cursor.getColumnIndex("cname"));
            String birthday = cursor.getString(cursor.getColumnIndex("birthday"));
            String tel = cursor.getString(cursor.getColumnIndex("tel"));
            textView.append(id +":"+ cname + ":" + birthday + ":" + tel + "\n");
        }
    }
}