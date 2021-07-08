package com.example.freindlyexpensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerUser extends DatabaseHandler {
    public TableControllerUser(Context context) {
        super(context);
    }


    //Do the create() method for creating new record.
    public boolean create(ObjectUser objectUser){
        ContentValues values = new ContentValues();
        values.put("name",objectUser.username);
        values.put("total_expense_amount", objectUser.getAmount());

        SQLiteDatabase db =this.getWritableDatabase();

        boolean createSuccessful = db.insert("users", null, values) > 0;
        db.close();

        return createSuccessful;

    }
    public int count(){
        SQLiteDatabase db = this.getWritableDatabase() ;

        String sql = "SELECT * FROM users";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;
    }

    public float amountSum(){
        SQLiteDatabase db = this.getWritableDatabase() ;
        float amountSum = 0;
        String sql = "SELECT SUM(total_expense_amount) as Total FROM users";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            amountSum = cursor.getFloat(cursor.getColumnIndex("Total"));
        }
        db.close();
        return amountSum;
    }

  //read purpose
    public List<ObjectUser> read() {
        List<ObjectUser> userList = new ArrayList<ObjectUser>();

        String sql = "SELECT * FROM users ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String readUserName = cursor.getString(cursor.getColumnIndex("name"));
                float readAmount = Float.parseFloat(cursor.getString(cursor.getColumnIndex("total_expense_amount")));

                ObjectUser objectUser = new ObjectUser();
                objectUser.id = id;
                objectUser.username = readUserName;
                objectUser.amount = readAmount;
                userList.add(objectUser);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;

    }

    public ObjectUser readSingleUserName(int userId){
        ObjectUser objectUser = null;

        String sql = "SELECT * FROM users WHERE id = " + userId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            float amount = Float.parseFloat(cursor.getString(cursor.getColumnIndex("total_expense_amount")));


            objectUser = new ObjectUser();
            objectUser.id = id;
            objectUser.username = name;
            objectUser.setAmount(amount);
        }

        cursor.close();
        db.close();

        return objectUser;
    }
    //update username and amount
    public boolean update(ObjectUser objectUser) {

        ContentValues values = new ContentValues();

        values.put("name", objectUser.username);
        values.put("total_expense_amount", objectUser.getAmount());

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectUser.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("users", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }
    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("users", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
     //amount update
    public boolean updateAmount(ObjectUser objectUser) {
        ContentValues values = new ContentValues();

        ObjectUser currObj = readSingleUserName(objectUser.id);

        float totalAmount = currObj.amount + objectUser.amount;
        values.put("total_expense_amount", totalAmount);

        String where = "id = ?";
        String[] whereArgs = { Integer.toString(objectUser.id) };
        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateAmountSuccessful = db.update("users", values, where, whereArgs) > 0;
        db.close();

        return updateAmountSuccessful;

    }
}
