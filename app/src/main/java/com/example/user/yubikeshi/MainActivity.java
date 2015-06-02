package com.example.user.yubikeshi;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;



public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickTopButton(View view){
        switch(view.getId()){
            case R.id.start:
                RelativeLayout layout = (RelativeLayout)findViewById(R.id.baseLayout);
                layout.removeAllViews();
                getLayoutInflater().inflate(R.layout.activity_game, layout);
                break;
            case R.id.ranking:
                AlertDialog.Builder rankingDialog = new AlertDialog.Builder(this);
                rankingDialog.setTitle(R.string.rankingTitle);
                rankingDialog.setMessage(R.string.rankingContent);
                rankingDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        // okボタン処理
                    }
                });
                rankingDialog.show();

                break;
            case R.id.setting:
                AlertDialog.Builder settingDialog = new AlertDialog.Builder(this);
                settingDialog.setTitle(R.string.settingTitle);
                settingDialog.setMessage(R.string.settingContent);
                settingDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        // okボタン処理
                    }
                });
                settingDialog.show();

                break;
        }
    }
}
