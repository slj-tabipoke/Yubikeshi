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
import java.util.Random;



public class MainActivity extends ActionBarActivity {
    public game game = new game();
    public boolean firstClickCheck = false;
    public int moveCount = 0;
    public int attackCount = 0;
    public int handFirstClicked = 0; // 1=左　2=右

    public hand myLeftHand = new hand();
    public hand myRightHand = new hand();
    public hand oppLeftHand = new hand();
    public hand oppRightHand = new hand();
    public opponent opp = new opponent();
    public gameTimer timer = new gameTimer(10000, 1000);

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
/*            case R.id.ranking:
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
*/
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
                        timer.cancel();
                        opp.action();
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
                        timer.cancel();
                        opp.action();
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
                Button oL = (Button)findViewById(R.id.OppLeftHand);
                timer.cancel();
                if(oppLeftHand.getFingerCount() >= 5){
                    oppLeftHand.die(3);
                    oppLeftHand.setFingerCount(5);
                    if(oppRightHand.getFingerCount() == 5){
                        // プレイヤー勝利　continue処理
                        game.continues();
                        timer.cancel();
                    }else{
                        opp.action();
                    }
                }else{
                    oL.setText(String.valueOf(oppLeftHand.getFingerCount()));
                    opp.action();
                }
            }else if(view.getId() == R.id.OppRightHand){
                oppRightHand.setFingerCount(oppRightHand.getFingerCount() + attackCount);
                attackCount = 0;
                moveCount = 0;
                firstClickCheck = false;
                Button oR = (Button)findViewById(R.id.OppRightHand);
                timer.cancel();
                if(oppRightHand.getFingerCount() >= 5){
                    oppRightHand.die(4);
                    oppRightHand.setFingerCount(5);
                    if(oppLeftHand.getFingerCount() == 5){
                        // プレイヤー勝利　continue処理
                        game.continues();
                        timer.cancel();
                    }else{
                        opp.action();
                    }
                }else{
                    opp.action();
                }
            }
        }
    }


    public class gameTimer extends CountDownTimer{
        public gameTimer(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish(){
            // カウントダウン完了後の処理　
            game.over();

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

        // public int winCount = 0;
        // public int whichHandFirstClicked = 0; // 1は左、2は右

        // ゲームを開始する
        public void start(){
            this.isStarted = true;

            // startGameボタンを非表示にする
            Button startBtn = (Button)MainActivity.this.findViewById(R.id.startBtn);
            startBtn.setVisibility(View.INVISIBLE);

            // startViewをセット
            TextView sv = (TextView)findViewById(R.id.startView);
            sv.setText("ゲーム中");

            // turnViewをセット
            // TextView tv = (TextView)findViewById(R.id.turnView);
            // tv.setText("あなたの番です");

            // カウントダウン開始
            timer.start();

            attackCount = 0;
            moveCount = 0;
            firstClickCheck = false;

            myLeftHand.setFingerCount(1);
            myRightHand.setFingerCount(1);
            oppLeftHand.setFingerCount(1);
            oppRightHand.setFingerCount(1);

            // 手ボタンに指本数（1本）セット
            Button myL = (Button)findViewById(R.id.MyLeftHand);
            Button myR = (Button)findViewById(R.id.MyRightHand);
            Button oppL = (Button)findViewById(R.id.OppLeftHand);
            Button oppR = (Button)findViewById(R.id.OppRightHand);
            myL.setText(String.valueOf(myLeftHand.getFingerCount()));
            myR.setText(String.valueOf(myRightHand.getFingerCount()));
            oppL.setText(String.valueOf(oppLeftHand.getFingerCount()));
            oppR.setText(String.valueOf(oppRightHand.getFingerCount()));

        }

        // プレイヤーが勝ってゲーム続行するときの処理
        public void continues(){
            // 勝ち抜き回数winCountに１足して他は初期化
/* 継続処理はあとで
            winCount++;
            myLeftHand.setFingerCount(1);
            myRightHand.setFingerCount(1);
            oppLeftHand.setFingerCount(1);
            oppRightHand.setFingerCount(1);
            Button mL = (Button)findViewById(R.id.MyLeftHand);
            Button mR = (Button)findViewById(R.id.MyRightHand);
            Button oL = (Button)findViewById(R.id.OppLeftHand);
            Button oR = (Button)findViewById(R.id.OppRightHand);
            mL.setText("1");
            mR.setText("1");
            oL.setText("1");
            oR.setText("1");
            attackCount = 0;
            moveCount = 0;
*/
            // 初期化
            this.isStarted = false;
            Button mL = (Button)findViewById(R.id.MyLeftHand);
            Button mR = (Button)findViewById(R.id.MyRightHand);
            Button oL = (Button)findViewById(R.id.OppLeftHand);
            Button oR = (Button)findViewById(R.id.OppRightHand);
            mL.setEnabled(true);
            mL.setText(" ");
            mR.setEnabled(true);
            mR.setText(" ");
            oL.setEnabled(true);
            oL.setText(" ");
            oR.setEnabled(true);
            oR.setText(" ");

            AlertDialog.Builder winDialog = new AlertDialog.Builder(MainActivity.this);
            winDialog.setTitle("勝利！");
            winDialog.setMessage("かちました");
            winDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    // okボタン処理
                    Button startBtn = (Button)MainActivity.this.findViewById(R.id.startBtn);
                    startBtn.setVisibility(View.VISIBLE);

                    TextView sv = (TextView)findViewById(R.id.startView);
                    sv.setText("  ");

                    TextView tv = (TextView)findViewById(R.id.turnView);
                    tv.setText("  ");
                }
            });
            winDialog.show();
        }

        // ゲームが終了（プレイヤーが敗北）したときの処理
        public void over(){
            // 初期化
            timer.cancel();
            this.isStarted = false;

            Button mL = (Button)findViewById(R.id.MyLeftHand);
            Button mR = (Button)findViewById(R.id.MyRightHand);
            Button oL = (Button)findViewById(R.id.OppLeftHand);
            Button oR = (Button)findViewById(R.id.OppRightHand);
            mL.setEnabled(true);
            mL.setText(" ");
            mR.setEnabled(true);
            mR.setText(" ");
            oL.setEnabled(true);
            oL.setText(" ");
            oR.setEnabled(true);
            oR.setText(" ");
            // 勝ち抜き継続処理を作るまでの暫定処理　
            AlertDialog.Builder loseDialog = new AlertDialog.Builder(MainActivity.this);
            loseDialog.setTitle("敗北・・・");
            loseDialog.setMessage("まけました");
            loseDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    // okボタン処理
                    Button startBtn = (Button)MainActivity.this.findViewById(R.id.startBtn);
                    startBtn.setVisibility(View.VISIBLE);

                    TextView sv = (TextView)findViewById(R.id.startView);
                    sv.setText("  ");

                    TextView tv = (TextView)findViewById(R.id.turnView);
                    tv.setText("  ");
                }
            });
            loseDialog.show();
        }
    }

    // 指の本数を制御するクラス
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
        public void die(int hand){
            switch(hand){
                case 1: // プレイヤー左手
                    Button mL = (Button)findViewById(R.id.MyLeftHand);
                    mL.setText("死亡");
                    mL.setEnabled(false);
                    break;
                case 2: // プレイヤー右手
                    Button mR = (Button)findViewById(R.id.MyRightHand);
                    mR.setText("死亡");
                    mR.setEnabled(false);
                    break;
                case 3: // 敵左手
                    Button oL = (Button)findViewById(R.id.OppLeftHand);
                    oL.setText("死亡");
                    oL.setEnabled(false);
                    break;
                case 4: // 敵右手
                    Button oR = (Button)findViewById(R.id.OppRightHand);
                    oR.setText("死亡");
                    oR.setEnabled(false);
                    break;
            }
        }
    }

    // 敵の行動を処理するクラス
    public class opponent{
        public void action(){
            try {
                Thread.sleep(1500);
            }catch(InterruptedException e){

            }
            // ランダムに生成された値に対応する行動パターンを敵のターンの行動として実行する
            // 0 => プレイヤーに攻撃（敵の左手からプレイヤーの左手へ）
            // 1 => プレイヤーに攻撃（敵の左手からプレイヤーの右手へ）
            // 2 => プレイヤーに攻撃（敵の右手からプレイヤーの左手へ）
            // 3 => プレイヤーに攻撃（敵の右手からプレイヤーの右手へ）
            // 4 => 右手から左手に指を移す（右手が１本の時は右手からプレイヤーに攻撃）
            // 5 => 左手から右手に指を移す（左手が１本の時は左手からプレイヤーに攻撃）
            Random r = new Random();
            int n = r.nextInt(6);

            if(oppLeftHand.getFingerCount() == 5){
                if(n != 2 && n != 3){
                    n = r.nextInt(2) + 2;
                }
            }else if(oppRightHand.getFingerCount() == 5){
                if(n != 0 && n != 1){
                    n = r.nextInt(2);
                }
            }
            switch(n){
                case 0:
                    if(oppLeftHand.getFingerCount() != 5) {
                        myLeftHand.setFingerCount(myLeftHand.getFingerCount() + oppLeftHand.getFingerCount());
                        if (myLeftHand.getFingerCount() >= 5) {
                            myLeftHand.die(1);
                            myLeftHand.setFingerCount(5);
                            if (myRightHand.getFingerCount() == 5) {
                                // プレイヤー敗北処理
                                game.over();
                            }
                        }
                    }
                    break;
                case 1:
                    myRightHand.setFingerCount(myRightHand.getFingerCount() + oppLeftHand.getFingerCount());
                    if(myRightHand.getFingerCount() >= 5){
                        myRightHand.die(2);
                        myRightHand.setFingerCount(5);
                        if(myLeftHand.getFingerCount() == 5){
                            // プレイヤー敗北処理
                            game.over();
                        }
                    }
                    break;
                case 2:
                    myLeftHand.setFingerCount(myLeftHand.getFingerCount() + oppRightHand.getFingerCount());
                    if(myLeftHand.getFingerCount() >= 5){
                        myLeftHand.die(1);
                        myLeftHand.setFingerCount(5);
                        if(myRightHand.getFingerCount() == 5){
                            // プレイヤー敗北処理
                            game.over();
                        }
                    }
                    break;
                case 3:
                    myRightHand.setFingerCount(myRightHand.getFingerCount() + oppRightHand.getFingerCount());
                    if(myRightHand.getFingerCount() >= 5){
                        myRightHand.die(2);
                        myRightHand.setFingerCount(5);
                        if(myLeftHand.getFingerCount() == 5){
                            // プレイヤー敗北処理
                            game.over();
                        }
                    }
                    break;
                case 4:
                    if(oppRightHand.getFingerCount() == 1){
                        int na = r.nextInt(2);
                        switch(na){
                            case 0:
                                myLeftHand.setFingerCount(myLeftHand.getFingerCount() + oppRightHand.getFingerCount());
                                if(myLeftHand.getFingerCount() >= 5){
                                    myLeftHand.die(1);
                                    myLeftHand.setFingerCount(5);
                                    if(myRightHand.getFingerCount() == 5){
                                        // プレイヤー敗北処理
                                        game.over();
                                    }
                                }
                                break;
                            case 1:
                                myRightHand.setFingerCount(myRightHand.getFingerCount() + oppRightHand.getFingerCount());
                                if(myRightHand.getFingerCount() >= 5){
                                    myRightHand.die(2);
                                    myRightHand.setFingerCount(5);
                                    if(myLeftHand.getFingerCount() == 5){
                                        // プレイヤー敗北処理
                                        game.over();
                                    }
                                }
                                break;
                        }
                    }else{
                        oppLeftHand.setFingerCount(oppLeftHand.getFingerCount() + 1);
                        oppRightHand.setFingerCount(oppRightHand.getFingerCount() - 1);
                    }
                    break;
                case 5:
                    if(oppLeftHand.getFingerCount() == 1){
                        int na = r.nextInt(2);
                        switch(na){
                            case 0:
                                myRightHand.setFingerCount(myRightHand.getFingerCount() + oppLeftHand.getFingerCount());
                                if(myRightHand.getFingerCount() >= 5){
                                    myRightHand.die(1);
                                    myRightHand.setFingerCount(5);
                                    if(myLeftHand.getFingerCount() == 5){
                                        // プレイヤー敗北処理
                                        game.over();
                                    }
                                }
                                break;
                            case 1:
                                myLeftHand.setFingerCount(myLeftHand.getFingerCount() + oppLeftHand.getFingerCount());
                                if(myLeftHand.getFingerCount() >= 5){
                                    myLeftHand.die(2);
                                    myLeftHand.setFingerCount(5);
                                    if(myRightHand.getFingerCount() == 5){
                                        // プレイヤー敗北処理
                                        game.over();
                                    }
                                }
                                break;
                        }
                    }else{
                        oppRightHand.setFingerCount(oppLeftHand.getFingerCount() + 1);
                        oppLeftHand.setFingerCount(oppRightHand.getFingerCount() - 1);
                    }
                    break;
            }
            Button mL = (Button)findViewById(R.id.MyLeftHand);
            Button mR = (Button)findViewById(R.id.MyRightHand);
            Button oL = (Button)findViewById(R.id.OppLeftHand);
            Button oR = (Button)findViewById(R.id.OppRightHand);
            mL.setText(String.valueOf(myLeftHand.getFingerCount()));
            mR.setText(String.valueOf(myRightHand.getFingerCount()));
            oL.setText(String.valueOf(oppLeftHand.getFingerCount()));
            oR.setText(String.valueOf(oppRightHand.getFingerCount()));


            timer.start();
        }
    }
}
