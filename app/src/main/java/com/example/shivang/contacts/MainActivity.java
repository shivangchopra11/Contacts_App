package com.example.shivang.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Contact> contactList;
    CustomArrayAdapter contactAdapter;
    //static int ctr = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listview);
        contactList = new ArrayList<>();
//        for(int i=0;i<5;i++) {
//            Contact c = new Contact();
//            c.name = "SHIVANG";
//            c.category = "WORK";
//            c.number = "7838826129";
//            contactList.add(c);
//        }
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("DELETE");
                builder.setMessage("Are you sure you want to delete ??");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("TAG", "Removing position " + position);
//                        contactList.remove(position);
//                        contactAdapter.notifyDataSetChanged();
                        String name = contactList.get(position).name;
                        ContactOpenHelper contactOpenHelper = ContactOpenHelper.getOpenHelperInstance(MainActivity.this);
                        SQLiteDatabase database = contactOpenHelper.getWritableDatabase();
                        String whereClause = ContactOpenHelper.CONTACT_NAME + "=?";
                        String[] whereArgs = new String[] {name};
                        database.delete(contactOpenHelper.CONTACT_TABLE_NAME, whereClause, whereArgs);
                        updateExpenseList();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,Edit_Contact.class);
                i.putExtra("name",contactList.get(position).name);
                i.putExtra("mail",contactList.get(position).email);
                i.putExtra("number",contactList.get(position).number);
                i.putExtra("category",contactList.get(position).category);
                i.putExtra("id",contactList.get(position).id);
                i.putExtra("pos",position);
                Log.i("TAG","Sent numbers" + contactList.get(position).name + contactList.get(position).id);
                MainActivity.this.startActivityForResult(i,1);
                //MainActivity.this.startActivity(i);
            }
        });

        contactAdapter = new CustomArrayAdapter(this,contactList);
        listView.setAdapter(contactAdapter);
        updateExpenseList();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,Add_Contact.class);
                MainActivity.this.startActivityForResult(i,2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1) {
            if (resultCode == RESULT_OK) {
                //startActivity(new Intent(data));

//                int pos = data.getIntExtra("pos",0);
//
//                Contact c1 = new Contact();
//                c1.name = data.getStringExtra("name");
//                c1.email = data.getStringExtra("mail");
//                c1.number = data.getStringExtra("number");
//                c1.category = data.getStringExtra("category");
//
//                contactList.set(pos,c1);
//                contactAdapter.notifyDataSetChanged();
                updateExpenseList();
            }
        }
        if(requestCode==2) {
            if (resultCode == RESULT_OK) {
                //startActivity(new Intent(data));

//                Contact c1 = new Contact();
//                c1.name = data.getStringExtra("name");
//                c1.email = data.getStringExtra("mail");
//                c1.number = data.getStringExtra("number");
//                c1.category = data.getStringExtra("category");
//                Log.i("TAG1", "Editing position " + c1.name);
//                contactList.add(c1);
//                contactAdapter.notifyDataSetChanged();
                updateExpenseList();

            }
        }
    }

    private void updateExpenseList() {

        ContactOpenHelper contactOpenHelper = ContactOpenHelper.getOpenHelperInstance(this);
        contactList.clear();
        SQLiteDatabase database = contactOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(ContactOpenHelper.CONTACT_TABLE_NAME,null,null,null,null, null, null);
        Log.i("TAG1", "Found Querry ");
        if(cursor != null) {
            while(cursor.moveToNext()){

                String name = cursor.getString(cursor.getColumnIndex(contactOpenHelper.CONTACT_NAME));
                String number = cursor.getString(cursor.getColumnIndex(contactOpenHelper.CONTACT_NUMBER));
                int id = cursor.getInt(cursor.getColumnIndex(contactOpenHelper.CONTACT_ID));
                String email = cursor.getString(cursor.getColumnIndex(contactOpenHelper.CONTACT_EMAIL));
                String category = cursor.getString(cursor.getColumnIndex(contactOpenHelper.CONTACT_CATEGORY));
                Log.i("TAG1", "Found Querry " + name + number + email + id);
                Contact e = new Contact();
                e.name = name;
                e.number = number;
                e.email = email;
                e.category = category;
                e.id =id;
                contactList.add(e);
            }

            contactAdapter.notifyDataSetChanged();
        }
    }

}
