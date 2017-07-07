package com.example.shivang.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Add_Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__contact);
        final EditText name = (EditText) findViewById(R.id.disp_name1);
        final EditText number = (EditText) findViewById(R.id.number1);
        final EditText email = (EditText) findViewById(R.id.email1);
        Spinner category = (Spinner) findViewById(R.id.category1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        final Contact c = new Contact();


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    c.category = "SCHOOL";
                else if (position == 1)
                    c.category = "HOME";
                else
                    c.category = "WORK";
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button submit = (Button) findViewById(R.id.submit1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString()==null)
                    name.setText("");
                if(number.getText().toString()==null)
                    number.setText("");
                if(email.getText().toString()==null)
                    number.setText("");
                c.name = name.getText().toString();
                c.number = number.getText().toString();
                c.email = email.getText().toString();
//                c.id = MainActivity.ctr;
//                MainActivity.ctr++;


                ContactOpenHelper contactOpenHelper = ContactOpenHelper.getOpenHelperInstance(Add_Contact.this);
                SQLiteDatabase database = contactOpenHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ContactOpenHelper.CONTACT_NAME,c.name);
                cv.put(ContactOpenHelper.CONTACT_NUMBER, c.number);
                cv.put(ContactOpenHelper.CONTACT_EMAIL, c.email);
                cv.put(ContactOpenHelper.CONTACT_CATEGORY,c.category);
                //cv.put(ContactOpenHelper.CONTACT_ID,c.id);
                database.insert(ContactOpenHelper.CONTACT_TABLE_NAME,null,cv);
                //Log.i("TAG2", "Inserting Querry " + c.name + c.number + c.email + c.id);

                Intent i = new Intent();
//                Log.i("TAG1", "Name Picked " + c.name);
//                i.putExtra("name",c.name);
//                i.putExtra("mail",c.email);
//                i.putExtra("number",c.number);
//                i.putExtra("category",c.category);
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }
}
