package com.example.shivang.contacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivang on 30/06/17.
 */

public class CustomArrayAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Contact> contactArrayList;
    CustomArrayAdapter a = this;

    static class contactViewHolder {
        ImageView categoryImageView;
        TextView nameTextView;
        TextView detailsTextView;
        contactViewHolder(ImageView categoryImageView, TextView nameTextView,TextView detailsTextView) {
            this.categoryImageView = categoryImageView;
            this.nameTextView = nameTextView;
            this.detailsTextView = detailsTextView;
        }
    }

    public CustomArrayAdapter(@NonNull Context context, @NonNull ArrayList<Contact> contactArrayList) {
        super(context, 0, contactArrayList);
        this.context = context;
        this.contactArrayList=contactArrayList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item , null);
            final TextView nameTextView = (TextView) convertView.findViewById(R.id.name);
            final TextView detailsTextView = (TextView) convertView.findViewById(R.id.details);
            ImageView categoryImageView = (ImageView) convertView.findViewById(R.id.photo);
//            convertView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("DELETE");
//                    builder.setMessage("Are you sure you want to delete ??");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Log.i("TAG", "Removing position " + position);
//                            contactArrayList.remove(position);
//                            a.notifyDataSetChanged();
//                        }
//                    });
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                    return true;
//                }
//            });
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent();
//                    Contact c = new Contact();
//                    c.name = nameTextView.getText().toString();
//                    c.number = detailsTextView.getText().toString();
//                    i.setClass(context,Edit_Contact.class);
//                    i.putExtra("contact",c);
//                    context.startActivity(i);
//                }
//            });
            contactViewHolder viewHolder = new contactViewHolder(categoryImageView,nameTextView,detailsTextView);
            convertView.setTag(viewHolder);
        }
        Contact c = contactArrayList.get(position);
        contactViewHolder viewHolder = (contactViewHolder)convertView.getTag();
        viewHolder.nameTextView.setText(c.name);
        viewHolder.detailsTextView.setText(c.number);
        if(c.category.compareTo("SCHOOL")==0)
            viewHolder.categoryImageView.setBackgroundResource(R.drawable.school);
        else if(c.category.compareTo("HOME")==0)
            viewHolder.categoryImageView.setBackgroundResource(R.drawable.home);
        else if(c.category.compareTo("WORK")==0)
            viewHolder.categoryImageView.setBackgroundResource(R.drawable.work);
        convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.black));
        return convertView;
    }
}
