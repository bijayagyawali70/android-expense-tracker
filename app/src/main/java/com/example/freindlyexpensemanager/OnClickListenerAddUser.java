package com.example.freindlyexpensemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OnClickListenerAddUser implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        //Get the application context, this is needed to inflate an XML layout file
        Context context = view.getRootView().getContext();

        //Inflate the take_username_form.xml, this will make UI elements or widgets accessible using code
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.take_username_form, null, false);

        //List down form widgets inside take_username_form.xml as “final” variables. This is because we will use them inside an AlertDialog
        final EditText editTextUsername = formElementsView.findViewById(R.id.userNameInput);
        final EditText editTexUserAmount = formElementsView.findViewById(R.id.userAmountInput);

        //Create an AlertDialog with the inflated take_username_form.xml and an “Add” button.
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Add User")
                .setPositiveButton("Add",
                        (dialog, id) -> {

                            //Get the user inputted values using the following code
                            String add_user_name = editTextUsername.getText().toString();
                            String add_user_amount = editTexUserAmount.getText().toString();

                            //set the input values as a object, so we can save it to the database.
                            ObjectUser objectUser = new ObjectUser();
                            objectUser.username = add_user_name;
                            objectUser.amount = Float.parseFloat(add_user_amount);

                            //call the create() method of TableControllerStudent class
                            boolean createSuccessful = new TableControllerUser(context).create(objectUser);

                            //Tell the user whether insert was a success or failure. We will use Android Toast to do this task.
                            if(createSuccessful){
                                Toast.makeText(context,"username name added.", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(context,"unable to add username.",Toast.LENGTH_SHORT).show();
                            }
                            dialog.cancel();
                            //Refresh the count and record list
                            MainActivity.getInstance().countUsers();
                            MainActivity.getInstance().readUsers();
                        }).show();

    }
}
