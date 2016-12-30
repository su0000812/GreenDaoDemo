package com.Jsu.greendaodemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        TextView content = (TextView) findViewById(R.id.content);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteText = "测试添加";
                final java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.MEDIUM, java.text.DateFormat.MEDIUM);
                String comment = "Added on" + df.format(new Date());
                Note note = new Note(111L, noteText, comment, new Date());
                daoSession.getNoteDao().insert(note);
//                cursor.requery();
            }
        });

        findViewById(R.id.btn_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daoSession.getNoteDao().deleteByKey(111L);
            }
        });

        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = daoSession.getNoteDao().queryBuilder()
                        .where(NoteDao.Properties.Text.eq("测试添加"))
                        .orderAsc(NoteDao.Properties.Date)
                        .build();

                QueryBuilder.LOG_SQL = true;
                QueryBuilder.LOG_VALUES = true;
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "testDB", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

//        String textColumn = NoteDao.Properties.Text.columnName;
//        String orderBy = textColumn + "COLLATE LOCALIZED ASC";
//        cursor = db.query(daoSession.getNoteDao().getTablename(), daoSession.getNoteDao().getAllColumns(),
//                null, null, null, null, orderBy);
    }
}
