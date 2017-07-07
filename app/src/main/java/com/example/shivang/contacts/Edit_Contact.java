package com.example.shivang.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Edit_Contact extends AppCompatActivity {

    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        final EditText name = (EditText) findViewById(R.id.disp_name);
        final EditText number = (EditText) findViewById(R.id.number);
        final EditText email = (EditText) findViewById(R.id.email);
        Spinner category = (Spinner) findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        b = getIntent().getExtras();
        name.setText(b.getString("name"));
        email.setText(b.getString("mail"));
        number.setText(b.getString("number"));
        if(b.getString("category").compareTo("WORK")==0)
            category.setSelection(2);
        else if(b.getString("category").compareTo("HOME")==0)
            category.setSelection(1);
        else if(b.getString("category").compareTo("SCHOOL")==0)
            category.setSelection(0);
        final Contact c = new Contact();
        Log.i("ID12",""+"ID" + b.getInt("id"));
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
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString()==null)
                    name.setText("");
                if(number.getText().toString()==null)
                    number.setText("");
                if(number.getText().toString()==null)
                    number.setText("");
                c.name = name.getText().toString();
                c.number = number.getText().toString();
                c.email = email.getText().toString();
                c.id = b.getInt("id");



//                i.putExtra("name",c.name);
//                i.putExtra("mail",c.email);
//                i.putExtra("number",c.number);
//                i.putExtra("category",c.category);
//                i.putExtra("pos",b.getInt("pos"));
                Log.i("TAG2", "Found Querry in edit " + c.name + c.number + c.email + c.id);
                ContactOpenHelper contactOpenHelper = ContactOpenHelper.getOpenHelperInstance(Edit_Contact.this);
                SQLiteDatabase database = contactOpenHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ContactOpenHelper.CONTACT_NAME,c.name);
                cv.put(ContactOpenHelper.CONTACT_NUMBER, c.number);
                cv.put(ContactOpenHelper.CONTACT_EMAIL, c.email);
                cv.put(ContactOpenHelper.CONTACT_CATEGORY,c.category);
                database.update(ContactOpenHelper.CONTACT_TABLE_NAME, cv, ContactOpenHelper.CONTACT_ID + "=" + c.id, null);
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });

        Button call = (Button) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number.getText().toString()));
                startActivity(intent);
            }
        });

        Button mail = (Button) findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+email.getText().toString()));
                startActivity(intent);
            }
        });

    }

}
