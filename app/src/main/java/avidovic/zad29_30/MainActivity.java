package avidovic.zad29_30;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddTitle(View view) {

        //---add a book---
        ContentValues values = new ContentValues();
        values.put("title", ((EditText)
                findViewById(R.id.txtTitle)).getText().toString());
        values.put("isbn", ((EditText)
                findViewById(R.id.txtISBN)).getText().toString());
        Uri uri = getContentResolver().insert(
                Uri.parse(
                        "content://avidovic.provider.zad29_30/books"),
                values);
    }

    public void onClickRetrieveTitles(View view) {
        //---retrieve the titles---
        Uri allTitles = Uri.parse(
                "content://avidovic.provider.zad29_30/books");

        Cursor c;
        if (Build.VERSION.SDK_INT <11) {
            //---before Honeycomb---
            c = managedQuery(allTitles, null, null, null,
                    "title desc");
        } else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allTitles, null, null, null,
                    "title desc");
            c = cursorLoader.loadInBackground();
        }

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(
                                BookProvider._ID)) + ", " +
                                c.getString(c.getColumnIndex(
                                        BookProvider.TITLE)) + ", " +
                                c.getString(c.getColumnIndex(
                                        BookProvider.ISBN)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }

    public void onClickBL(View view) {
        //---retrieve the titles---
        Uri allTitles = Uri.parse(
                "content://avidovic.provider.zad29_30/books");

        Cursor c;
        if (Build.VERSION.SDK_INT <11) {
            //---before Honeycomb---
            c = managedQuery(allTitles, null, null, null,
                    "title desc");
        } else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allTitles, null, null, null,
                    "title desc");
            c = cursorLoader.loadInBackground();
        }

        if (c.moveToFirst()) {

            do{
                String s=c.getString(c.getColumnIndex(
                        BookProvider.TITLE));
                if(s.startsWith("L") || s.startsWith("B") || s.startsWith("l") || s.startsWith("b")) {
                    Toast.makeText(this,
                            c.getString(c.getColumnIndex(
                                    BookProvider._ID)) + ", " +
                                    c.getString(c.getColumnIndex(
                                            BookProvider.TITLE)) + ", " +
                                    c.getString(c.getColumnIndex(
                                            BookProvider.ISBN)),
                            Toast.LENGTH_SHORT).show();
                }

            } while (c.moveToNext());


        }
    }

    public void onClickDelete(View view) {
        String isbn = ((EditText) findViewById(R.id.txtISBN)).getText().toString();
        String [] args = {isbn};
        int uri = getContentResolver().delete(
                Uri.parse("content://avidovic.provider.zad29_30/books"),
                        BookProvider.ISBN + "= :isbn" , args);

        Toast.makeText(this, ""+uri+" - isbn: "+isbn+" deleted." , Toast.LENGTH_SHORT).show();
    }
}

