package com.example.btlltdd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.btlltdd.Book;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bookDB";
    private static final int DATABASE_VERSION = 1;
    //bang book
    private static final String TABLE_BOOK = "book";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE_ID = "image_id";
    private static final String COLUMN_TITLE = "title";
    //bang user
    private static final String TABLE_USER = "users";
    private static final String COLUMN_USERS_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_BOOK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_IMAGE_ID + " INTEGER, "
                + COLUMN_TITLE + " TEXT)";
        db.execSQL(createTable);

        String CreateTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CreateTable);

        addUser(db, "admin", "12345");
        addUser(db, "user1", "password1");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);

    }

    // Thêm truyện vào database
    public void addBook(int imageId, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_ID, imageId);
        values.put(COLUMN_TITLE, title);

        db.insert(TABLE_BOOK, null, values);
        db.close();
    }

    // Lấy danh sách truyện từ database
    public ArrayList<Book> getAllBook() {
        ArrayList<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOK, null);

        if (cursor.moveToFirst()) {
            do {

                int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));

                Book book = new Book(imageId, title);
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }
    public List<String> searchTruyen(String keyword) {
        List<String> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String query = "SELECT * FROM " + TABLE_BOOK + " WHERE " + COLUMN_TITLE + " LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%"});

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                bookList.add(title);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {COLUMN_USERS_ID};
//        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
//        String[] selectionArgs = {username, password};
//
//        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
//        int count = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        return count > 0; // Trả về true nếu tìm thấy người dùng
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=" +username
                    + "AND" +COLUMN_PASSWORD +"="+password;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }
    private void addUser(SQLiteDatabase db, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USER, null, values);
    }

}
