package com.example.freindlyexpensemanager;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        Button buttonAddUser = findViewById(R.id.addUserButton);
        buttonAddUser.setOnClickListener(new OnClickListenerAddUser() );

        countUsers();
        readUsers();

    }

    public void  countUsers(){
        //calling the count() from TableControllerUser class
        int userCount = new TableControllerUser(this).count();
        //display the count to the text view
        TextView textViewUserCount = findViewById(R.id.textViewUserCount);
        textViewUserCount.setText(String.format("%d%s", userCount, getString(R.string.user_count_label)));
    }
    //this will display database username to user interface
    public void readUsers(){
        LinearLayout linearLayoutUser = findViewById(R.id.linearLayoutUserRecords);
        linearLayoutUser.removeAllViews();

        List<ObjectUser> users = new TableControllerUser(this).read();
        if (users.size() > 0) {

            float averageCostPP = 0;
            int userCount = new TableControllerUser(this).count();
            float totalSum = new TableControllerUser(this).amountSum();
            averageCostPP = totalSum/userCount;

            for (ObjectUser obj : users) {
                int id = obj.id;
                float amount = obj.amount;
                TextView textViewUserItem= new TextView(this);

                String textViewContents = "Name: " + obj.username;
                textViewContents += "\n" + "Total Spent Amount: " + amount;
                textViewContents += "\n" + "Cost per person: " + averageCostPP;
                float adjustCost = 0;
                if (amount > averageCostPP){
//                    refund condition
                    adjustCost = amount - averageCostPP;
                    textViewContents += "\n" + "Refund Amount: " + adjustCost;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textViewUserItem.setTextColor(getColor(R.color.green));
                    }
                } else {
                    //                    pay condition
                    adjustCost = averageCostPP - amount;
                    textViewContents += "\n" + "Remaining to pay: " + adjustCost;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textViewUserItem.setTextColor(getColor(R.color.pink));
                    }

                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textViewUserItem.setBackgroundColor(getColor(R.color.pale));
                }
                textViewUserItem.setPadding(18, 8, 0, 10);
                textViewUserItem.setText(textViewContents);
                textViewUserItem.setTextSize(24);
                textViewUserItem.setTag(Integer.toString(id));

                // long press TextView for edit and delete action
                textViewUserItem.setOnLongClickListener(new OnLongClickListenerUserName());

                //Click TextView for enter expenses amount each
                textViewUserItem.setOnClickListener(new OnClickListenerUserNameToEnterExpense());

//                RelativeLayout itemView = findViewById(R.layout.stats_item);
                linearLayoutUser.addView(textViewUserItem);
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText(R.string.no_user_message);

            linearLayoutUser.addView(locationItem);
        }


    }

    public static MainActivity getInstance(){
        return   mainActivity;
    }
}