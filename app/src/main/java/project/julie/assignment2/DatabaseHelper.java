package project.julie.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "friends_db";
    private static final int DB_VERSION = 3;
    private static final String TABLE_NAME = "friends";
    private static final String KEY_ID = "id";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private SQLiteDatabase db;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlstatement = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " integer primary key autoincrement unique, " + FIRSTNAME + " text ," + LASTNAME + " text," +
                EMAIL + " text unique," + PHONE + " text unique" + ", UNIQUE(" + FIRSTNAME + "," + LASTNAME + "))";
        sqLiteDatabase.execSQL(sqlstatement);
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " (" + FIRSTNAME + ", " + LASTNAME + ", " + EMAIL + ", " + PHONE + ") VALUES (\"olivier\", \"donnadei\", \"oooo@oo.com\", 12345)");
        Log.i("", "Insert stuff");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public String insertFriend(String firstName, String lastName, String email, String phone) {

        db = this.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(FIRSTNAME, firstName);
            contentValues.put(LASTNAME, lastName);
            contentValues.put(EMAIL, email);
            contentValues.put(PHONE, phone);
            db.insertOrThrow(TABLE_NAME, null, contentValues);
        } catch (SQLiteConstraintException e) {
            return "Name, email or phone already exist";
        } finally {
            db.close();
        }
        return "User successfully added";
    }

    public String update(int id, String fname, String lastName, String email, String phone) {
        db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.FIRSTNAME, fname);
            contentValues.put(DatabaseHelper.LASTNAME, lastName);
            contentValues.put(DatabaseHelper.EMAIL, email);
            contentValues.put(DatabaseHelper.PHONE, phone);
            int i = db.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.KEY_ID + " = " + id, null);
        } catch (SQLiteConstraintException e) {
            return "Name, email or phone already exist";
        } finally {
            db.close();
        }
        return "User successfully added";
    }


    public List<Friend> allFriends() {
        List<Friend> friends = new ArrayList<>();
        String selectQuery = "SELECT * FROM friends";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Friend friend = new Friend();
                friend.setId(cursor.getInt(0));
                friend.setFirstName(cursor.getString(1));
                friend.setLastName(cursor.getString(2));
                friend.setEmail(cursor.getString(3));
                friend.setPhone(cursor.getString(4));
                friends.add(friend);
            } while (cursor.moveToNext());
        }

        db.close();
        return friends;
    }

    public List<Friend> search(String keyword) {
        List<Friend> friends = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " where " + LASTNAME + " like ? or " + EMAIL + " like ? or " + PHONE + " like ? or " + FIRSTNAME + " like ?"
                    , new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"});
            if (cursor.moveToFirst()) {
                friends = new ArrayList<>();
                do {
                    Friend friend = new Friend();
                    friend.setId(cursor.getInt(0));
                    friend.setFirstName(cursor.getString(1));
                    friend.setLastName(cursor.getString(2));
                    friend.setEmail(cursor.getString(3));
                    friend.setPhone(cursor.getString(4));
                    friends.add(friend);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            friends = null;
        }
        return friends;
    }


}

