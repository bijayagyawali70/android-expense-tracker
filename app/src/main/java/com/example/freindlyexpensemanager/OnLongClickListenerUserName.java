package com.example.freindlyexpensemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OnLongClickListenerUserName implements View.OnLongClickListener {

    Context context;
    String id;
    @Override
    public boolean onLongClick(View view) {
       context = view.getContext();
       id = view.getTag().toString();

        //Add an AlertDialog with simple list view for ‘Edit’ and ‘Delete’ options
        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Student Record")
                .setItems(items, (dialog, item) -> {
                    if (item == 0) {
                        editUser(Integer.parseInt(id));    /*"Edit"  has an item index of 0*/
                    }
                    //deleting part
                    else if(item == 1) {
                        boolean deleteSuccessful = new TableControllerUser(context).delete(Integer.parseInt(id));
                        if (deleteSuccessful){
                            Toast.makeText(context, "User name was deleted.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Unable to delete user name", Toast.LENGTH_SHORT).show();
                        }

                    }
                    ((MainActivity) context).countUsers();
                    ((MainActivity) context).readUsers();
                    dialog.dismiss();
                }).show();

        return false;
    }

    public void editUser(final int userId){
          /*we will use the following code to read single record.
        Data will be used to fill up the student form for updating it.*/
        final TableControllerUser tableControllerUser= new TableControllerUser(context);
        ObjectUser objectUser = tableControllerUser.readSingleUserName(userId);

        //inflate take_username_form.xml, this time we will use it for updating a record.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.take_username_form, null, false);

        //list down form elements.
        final EditText editTextUserName = formElementsView.findViewById(R.id.userNameInput);
        final EditText editTextUserAmount = formElementsView.findViewById(R.id.userAmountInput);

        //set single record values to the EditText form elements.
        editTextUserName.setText(objectUser.username);
        editTextUserAmount.setText((String.valueOf(objectUser.amount)));

        //Show an AlertDialog with the form and single record filling it up
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        (dialog, id) -> {
                            //create the object with the updated value
                            ObjectUser objectuser= new ObjectUser();
                            objectuser.id = userId;
                            objectuser.username = editTextUserName.getText().toString();
                            objectuser.setAmount(Float.parseFloat(editTextUserAmount.getText().toString()));
                            //update the record and tell the user whether it was updated or not
                            boolean updateSuccessful = tableControllerUser.update(objectuser);

                            if(updateSuccessful){
                                Toast.makeText(context, "user name was updated.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to update user name.", Toast.LENGTH_SHORT).show();
                            }
                            dialog.cancel();
                            MainActivity.getInstance().countUsers();
                            MainActivity.getInstance().readUsers();
                        }).show();



    }
}
