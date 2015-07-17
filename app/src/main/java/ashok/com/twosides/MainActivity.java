package ashok.com.twosides;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import android.media.MediaPlayer;


public class MainActivity extends Activity {

    MediaPlayer player;
    Vibrator vibrator;
    Button tb1,tb2,tb3,tb4,tb5,tb6,lb1,lb2,lb3,lb4,lb5,lb6;
    TextView centerButton,timer1,timer2;
    int centerColorId = 0;
    boolean isPlayerOneSelected,isPlayerTwoSelected;
    CountDownTimer countDownTimer;
    int level=1;
    int playerOneScore,playerTwoScore;
    double levelTime;
    SharedPreferences settings,vibration,music;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        timer1 = (TextView)findViewById(R.id.timer1);
        timer2 = (TextView)findViewById(R.id.timer2);
        settings = this.getSharedPreferences("TS", 0);
        vibration = this.getSharedPreferences("TS",0);
        music = this.getSharedPreferences("TS",0);

        if (settings.getBoolean("appLaunched",false))
        setupLevel();
        else
        setupTour();

        AssetFileDescriptor afd;
        try {
        // Read the music file from the asset folder
            afd = getAssets().openFd("game_background.mp3");
        // Creation of new media player;
            player = new MediaPlayer();
        // Set the player music source.
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),afd.getLength());
        // Set the looping and play the music.
            player.setLooping(true);
            player.prepare();
            if (music.getBoolean("Music",true))
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void closeLevel()
    {
        if (countDownTimer!=null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    void endGame(){
        this.finish();
    }
    void setupLevel()
    {
        getButtons();
        enableTopButtons();
        enableBottomButtons();
        final ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(Color.BLUE);
        arrayList.add(Color.CYAN);
        arrayList.add(Color.GREEN);
        arrayList.add(Color.RED);
        arrayList.add(Color.YELLOW);
        arrayList.add(Color.MAGENTA);
        Collections.shuffle(arrayList);
        shuffleColors(arrayList);
        Log.w("LEVEL TIME", String.valueOf((50000 / level)));
        levelTime = Math.ceil(50000/level);
        timer1.setText(String.valueOf( levelTime));
        timer2.setText(String.valueOf( levelTime));
        countDownTimer = new CountDownTimer(50000/level,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isPlayerOneSelected && isPlayerTwoSelected)
                {
                    closeLevel();
                    showAlert();
                }
                timer1.setText(String.valueOf((int)millisUntilFinished/1000)+" secs");
                timer2.setText(String.valueOf((int)millisUntilFinished/1000)+" secs");
            }

            @Override
            public void onFinish() {
                showAlert();
            }

        };
        countDownTimer.start();
    }

    void setupTour(){
        ViewTarget target = new ViewTarget(R.id.show_color, this);
        new ShowcaseView.Builder(this)
                .setTarget(target)
                .setContentTitle("Match the color")
                .setContentText("Identify same color in your grid before your opponent select")
                .hideOnTouchOutside()
                .setShowcaseEventListener(new OnShowcaseEventListener() {
                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {
                        setupLevel();
                        editor = settings.edit();
                        editor.putBoolean("appLaunched", true);
                        editor.apply();
                    }

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                    }

                    @Override
                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                    }
                })
                .build();
    }
    void getButtons()
    {
        //Top buttons
        tb1 = (Button)findViewById(R.id.tb1);
        tb2 = (Button)findViewById(R.id.tb2);
        tb3 = (Button)findViewById(R.id.tb3);
        tb4 = (Button)findViewById(R.id.tb4);
        tb5 = (Button)findViewById(R.id.tb5);
        tb6 = (Button)findViewById(R.id.tb6);

        //Bottom buttons
        lb1 = (Button)findViewById(R.id.lb1);
        lb2 = (Button)findViewById(R.id.lb2);
        lb3 = (Button)findViewById(R.id.lb3);
        lb4 = (Button)findViewById(R.id.lb4);
        lb5 = (Button)findViewById(R.id.lb5);
        lb6 = (Button)findViewById(R.id.lb6);

        //Center button
        centerButton = (TextView) findViewById(R.id.show_color);
    }
    public void shuffleColors(ArrayList<Integer> colors)
    {
        //Displaying data
        Random rnd = new Random();
        int color = rnd.nextInt(5);

        centerButton.setBackgroundColor(colors.get(color));
        centerColorId = colors.get(color);
        tb1.setBackgroundColor(colors.get(0));
        lb6.setBackgroundColor(colors.get(0));

        tb2.setBackgroundColor(colors.get(2));
        lb5.setBackgroundColor(colors.get(2));

        tb3.setBackgroundColor(colors.get(1));
        lb4.setBackgroundColor(colors.get(1));

        tb4.setBackgroundColor(colors.get(3));
        lb3.setBackgroundColor(colors.get(3));

        tb5.setBackgroundColor(colors.get(4));
        lb2.setBackgroundColor(colors.get(4));

        tb6.setBackgroundColor(colors.get(5));
        lb1.setBackgroundColor(colors.get(5));

        // Set on click listener for top buttons
        tb1.setOnClickListener(topOnclickListener);
        tb2.setOnClickListener(topOnclickListener);
        tb3.setOnClickListener(topOnclickListener);
        tb4.setOnClickListener(topOnclickListener);
        tb5.setOnClickListener(topOnclickListener);
        tb6.setOnClickListener(topOnclickListener);

        // Set on click listener for bottom buttons
        lb1.setOnClickListener(bottomOnclickListener);
        lb2.setOnClickListener(bottomOnclickListener);
        lb3.setOnClickListener(bottomOnclickListener);
        lb4.setOnClickListener(bottomOnclickListener);
        lb5.setOnClickListener(bottomOnclickListener);
        lb6.setOnClickListener(bottomOnclickListener);

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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showAlert(){
        if (level==10)
        {
            Toast.makeText(this,(playerOneScore>=playerTwoScore)?"Winner is Player1":"Winner is Player2",Toast.LENGTH_SHORT).show();
            //call end game
            endGame();
            return;
        }
        //TODO: Which player wins or Draw should notify here
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Game over")
                    .setPositiveButton("Next level("+(level+1)+")->", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            level+=1;
                            Log.w("LEVEL", String.valueOf(level));
                            Log.w("PLAYER1", String.valueOf(playerOneScore));
                            Log.w("PLAYER2", String.valueOf(playerTwoScore));

                            isPlayerOneSelected = isPlayerTwoSelected = false;
                            setupLevel();
                        }
                    })
                    .setNegativeButton("<-- Home", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            endGame();
                        }
                    });
            // Create the AlertDialog object and return it
             builder.create();
        try{
            builder.show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                endGame();
            }
        });
        timer1.setText(String.valueOf("SCORE:" + playerOneScore));
        timer2.setText(String.valueOf("SCORE:" + playerTwoScore));


    }

    public View.OnClickListener topOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isPlayerOneSelected = true;
            ColorDrawable buttonColor = (ColorDrawable) v.getBackground();
            int selectedColorId = buttonColor.getColor();
            vibrator.vibrate(200);
            System.out.println();
            if (selectedColorId == centerColorId){
                playerOneScore += 1;
            }
            else{
                System.out.println("Fail");
            }
            disableTopButtons();
            v.setVisibility(View.VISIBLE);
        }
    };

    public View.OnClickListener bottomOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isPlayerTwoSelected = true;
            ColorDrawable buttonColor = (ColorDrawable) v.getBackground();
            int selectedColorId = buttonColor.getColor();
            System.out.println(selectedColorId);
            vibrator.vibrate(200);
            if (selectedColorId == centerColorId){
                playerTwoScore+=1;
            }
            else{
                System.out.println("Fail");
            }
            disableBottomButtons();
            v.setVisibility(View.VISIBLE);
        }
    };

    public void disableTopButtons(){
        tb1.setVisibility(View.INVISIBLE);
        tb2.setVisibility(View.INVISIBLE);
        tb3.setVisibility(View.INVISIBLE);
        tb4.setVisibility(View.INVISIBLE);
        tb5.setVisibility(View.INVISIBLE);
        tb6.setVisibility(View.INVISIBLE);
    }

    public void disableBottomButtons() {
        lb1.setVisibility(View.INVISIBLE);
        lb2.setVisibility(View.INVISIBLE);
        lb3.setVisibility(View.INVISIBLE);
        lb4.setVisibility(View.INVISIBLE);
        lb5.setVisibility(View.INVISIBLE);
        lb6.setVisibility(View.INVISIBLE);
    }
    public void enableTopButtons(){
        tb1.setVisibility(View.VISIBLE);
        tb2.setVisibility(View.VISIBLE);
        tb3.setVisibility(View.VISIBLE);
        tb4.setVisibility(View.VISIBLE);
        tb5.setVisibility(View.VISIBLE);
        tb6.setVisibility(View.VISIBLE);
    }

    public void enableBottomButtons() {
        lb1.setVisibility(View.VISIBLE);
        lb2.setVisibility(View.VISIBLE);
        lb3.setVisibility(View.VISIBLE);
        lb4.setVisibility(View.VISIBLE);
        lb5.setVisibility(View.VISIBLE);
        lb6.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
        player=null;
    }
}
