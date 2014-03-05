package com.scorecard;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_Basket = (Button)findViewById(R.id.btBasket);
        Button bt_Baseball = (Button)findViewById(R.id.btBaseball);
        Button bt_Football = (Button)findViewById(R.id.btFootball);
        Button bt_Soccer = (Button)findViewById(R.id.btSoccer);

        bt_Basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLogin("basketball");
            }
        });
        bt_Baseball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLogin("baseball");
            }
        });
        bt_Football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLogin("football");
            }
        });
        bt_Soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLogin("soccer");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void gotoLogin(String goToGame){
        Intent i = new Intent(getApplicationContext(),com.scorecard.LoginActivity.class);
        i.putExtra("game", goToGame);
        startActivity(i);
    }
    //if you press the back button in the main screen prompt a message box asking to comfirm the action
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
        //if key pressed is the back key
            Builder alert = new Builder(this);
            //creating the alert message
            alert.setTitle("Exit this Application?");
            alert.setMessage("Are you sure you want to exit?");

            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            //give it the YES button
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                    System.exit(0);
                    //exit the program as a whole
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
            //give the message box a NO button
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing, just close message box
                }
            });
            alert.show();
            //make the alert message box show up
            return true;
        }
        else{
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
