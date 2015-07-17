package ashok.com.twosides;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class MainActivity extends Activity {

    Button tb1;
    Button tb2;
    Button tb3;
    Button tb4;
    Button tb5;
    Button tb6;
    Button lb1;
    Button lb2;
    Button lb3;
    Button lb4;
    Button lb5;
    Button lb6;
    TextView centerButton;
    TextView timer1;
    TextView timer2;
    int centerColorId = 0;
    boolean isPlayerOneSelected;
    boolean isPlayerTwoSelected;
    CountDownTimer countDownTimer;
    int level=1;
    int playerOneScore;
    int playerTwoScore;
    double levelTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer1 = (TextView)findViewById(R.id.timer1);
        timer2 = (TextView)findViewById(R.id.timer2);
        setupLevel();
    }

    void closeLevel()
    {
        countDownTimer.cancel();
        countDownTimer = null;
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
             builder.show();
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
}
