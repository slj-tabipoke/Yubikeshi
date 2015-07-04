package com.example.user.yubikeshi;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.TextView;



public class MainActivity extends ActionBarActivity {
    public game game = new game();
    public boolean firstClickCheck = false;
    public int moveCount = 0;
    public int attackCount = 0;
    public int handFirstClicked = 0; // 1=左　2=右
    public boolean isMyTurn;

    public hand myLeftHand = new hand();
    public hand myRightHand = new hand();
    public hand oppLeftHand = new hand();
    public hand oppRightHand = new hand();



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

    // ボタンのクリックメソッド

    public void clickStartBtn(View view){
        game.start();
    }


    // 自分の手をクリックしたとき
    public void clickMyHand(View view){
        if(game.isStarted == true){
            if(view.getId() == R.id.MyLeftHand){
                if(firstClickCheck == false) {
                    firstClickCheck = true;
                    handFirstClicked = 1;
                    moveCount++;
                    if(myLeftHand.getFingerCount() == 1) {
                        moveCount = 0;
                    }
                    attackCount = myLeftHand.getFingerCount();
                }else if(firstClickCheck == true){
                    if(handFirstClicked == 1){
                        moveCount++;
                        if(myLeftHand.getFingerCount() == moveCount){
                            moveCount = myLeftHand.getFingerCount() - 1;
                        }
                    }else if(handFirstClicked == 2){
                        myLeftHand.setFingerCount(myLeftHand.getFingerCount() + moveCount);
                        myRightHand.setFingerCount(myRightHand.getFingerCount() - moveCount);

                        Button mL = (Button)findViewById(R.id.MyLeftHand);
                        Button mR = (Button)findViewById(R.id.MyRightHand);
                        mL.setText(String.valueOf(myLeftHand.getFingerCount()));
                        mR.setText(String.valueOf(myRightHand.getFingerCount()));
                        firstClickCheck = false;
                        handFirstClicked = 0;
                        moveCount = 0;
                        attackCount = 0;
                        isMyTurn = false;
                    }
                }
            }else if(view.getId() == R.id.MyRightHand){
                if(firstClickCheck == false){
                    firstClickCheck = true;
                    handFirstClicked = 2;
                    moveCount++;
                    if(myRightHand.getFingerCount() == 1) {
                        moveCount = 0;
                    }
                    attackCount = myRightHand.getFingerCount();
                }else if(firstClickCheck == true){
                    if(handFirstClicked == 1){
                        myRightHand.setFingerCount(myRightHand.getFingerCount() + moveCount);
                        myLeftHand.setFingerCount(myLeftHand.getFingerCount() - moveCount);

                        Button mL = (Button)findViewById(R.id.MyLeftHand);
                        Button mR = (Button)findViewById(R.id.MyRightHand);
                        mL.setText(String.valueOf(myLeftHand.getFingerCount()));
                        mR.setText(String.valueOf(myRightHand.getFingerCount()));
                        firstClickCheck = false;
                        handFirstClicked = 0;
                        moveCount = 0;
                        attackCount = 0;
                        isMyTurn = false;
                    }else if(handFirstClicked == 2){
                        moveCount++;
                        if(myRightHand.getFingerCount() == moveCount){
                            moveCount = myRightHand.getFingerCount() - 1;
                        }
                    }
                }
            }
        }
    }

    // 相手の手をクリックしたとき
    public void clickOppHand(View view ){
        if(firstClickCheck == true){
            if(view.getId() == R.id.OppLeftHand){
                oppLeftHand.setFingerCount(oppLeftHand.getFingerCount() + attackCount);
                attackCount = 0;
                moveCount = 0;
                firstClickCheck = false;
                isMyTurn = false;

                Button oL = (Button)findViewById(view.getId());
                oL.setText(String.valueOf(oppLeftHand.getFingerCount()));
            }else if(view.getId() == R.id.OppRightHand){
                oppRightHand.setFingerCount(oppRightHand.getFingerCount() + attackCount);
                attackCount = 0;
                moveCount = 0;
                firstClickCheck = false;
                isMyTurn = false;

                Button oR = (Button)findViewById(view.getId());
                oR.setText(String.valueOf(oppRightHand.getFingerCount()));
            }
        }
    }


    public class gameTimer extends CountDownTimer{
        public gameTimer(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish(){
            // カウントダウン完了後の処理　敗北時の処理

        }

        @Override
        public void onTick(long millisUntilFinished){
            // インターバル毎に呼ばれる処理 ミリ秒を秒数に直してviewにセット
            TextView timerView = (TextView)findViewById(R.id.timerView);
            timerView.setText(Long.toString(millisUntilFinished/1000%60));
        }
    }

    // ゲームの進行状況を制御するクラス
    public class game{
        public boolean isStarted = false;

        public int winCount = 0;
        public int whichHandFirstClicked = 0; // 1は左、2は右

        // ゲームを開始する
        public void start(){
            // startGameボタンを非表示にする
            Button startBtn = (Button)MainActivity.this.findViewById(R.id.startBtn);
            startBtn.setVisibility(View.INVISIBLE);

            // startViewをセット
            TextView sv = (TextView)findViewById(R.id.startView);
            sv.setText("ゲーム中");

            // turnViewをセット
            TextView tv = (TextView)findViewById(R.id.turnView);
            tv.setText("あなたの番です");

            // timerViewをセットしカウントダウン開始
            gameTimer cdt = new gameTimer(10000, 1000);
            cdt.start();

            // isStartedにtrueをセット、isMyTurnをtrueにしてプレイヤーの番から開始
            isStarted = true;
            isMyTurn = true;


            // 手ボタンに指本数（1本）セット
            Button myL = (Button)findViewById(R.id.MyLeftHand);
            Button myR = (Button)findViewById(R.id.MyRightHand);
            Button oppL = (Button)findViewById(R.id.OppLeftHand);
            Button oppR = (Button)findViewById(R.id.OppRightHand);
            myL.setText("1");
            myR.setText("1");
            oppL.setText("1");
            oppR.setText("1");

        }

        // プレイヤーが勝ってゲーム続行するときの処理
        public void continues(){

        }

        // ゲームが終了（プレイヤーが敗北）したときの処理
        public void over(){

        }
    }

    // 指の本数を計算するクラス
    public class hand{
        public int fingerCount = 1; // 実際の指の本数

        // 指の本数を増やす
        public void setFingerCount(int finger){
            fingerCount = finger;
        }

        // 指本数を表示する
        public int getFingerCount(){
            return this.fingerCount;
        }

        // 本数が5本を超えたら操作できないようにする
        public void die(){

        }


    }
}
