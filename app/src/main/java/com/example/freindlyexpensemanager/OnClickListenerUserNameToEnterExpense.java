package com.example.freindlyexpensemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Logger;

public class OnClickListenerUserNameToEnterExpense implements View.OnClickListener {
    int uid;
    @Override
    public void onClick(View view) {
        //Get the application context, this is needed to inflate an XML layout file
        Context context = view.getRootView().getContext();
        uid = Integer.parseInt(String.valueOf(view.getTag()));

        //Inflate the take_user_expense_input.xml, this will make UI elements or widgets accessible using code
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.take_user_expense_input, null, false);

        //List down form widgets inside take_user_expense_input.xml as “final” variables. This is because we will use them inside an AlertDialog
        final EditText editTextUserExpense = formElementsView.findViewById(R.id.userExpenseInput);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Enter Current Expense")
                .setPositiveButton("Accept",
                        (dialog, id) -> {
                            //Get the user inputted values using the following code
                            float add_user_expense= Float.parseFloat(String.valueOf(editTextUserExpense.getText()));
                            Log.d("DEBUG", "user expenses:" + add_user_expense);
                            //set the input values as a object, so we can save it to the database.
                            ObjectUser objectUser= new ObjectUser();
                            objectUser.setAmount(add_user_expense);
                            objectUser.id = uid;
                            //call the create() method of TableControllerStudent class
                            boolean updateAmountSuccessful = new TableControllerUser(context).updateAmount(objectUser);

                            //Tell the user whether insert was a success or failure. We will use Android Toast to do this task.
                            if(updateAmountSuccessful){
                                Toast.makeText(context,"Current Expense Accepted.", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(context,"Unable to Accept Current Expense.",Toast.LENGTH_SHORT).show();
                            }
                            MainActivity.getInstance().readUsers();

                        }).show();
    }
}
