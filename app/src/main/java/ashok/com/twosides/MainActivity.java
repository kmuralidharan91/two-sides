package ashok.com.twosides;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getButtons();
        final ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(Color.BLUE);
        arrayList.add(Color.CYAN);
        arrayList.add(Color.GREEN);
        arrayList.add(Color.RED);
        arrayList.add(Color.YELLOW);
        arrayList.add(Color.MAGENTA);

        shuffleColors(arrayList);

        CountDownTimer countDownTimer = new CountDownTimer(30000,5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Collections.shuffle(arrayList);
                shuffleColors(arrayList);
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
    }
    public void shuffleColors(ArrayList<Integer> colors)
    {
        //Displaying data
        Random rnd = new Random();
        int color = rnd.nextInt(5);

        lb6.setBackgroundColor(colors.get(color));
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
    }

    /***
     * Method to get the selected buttons id
     * @return setSelectedButtonID_User_One,setSelectedButtonID_User_Two
     */
    public int[] getSelectedButtons(){
        int setSelectedButtonID_User_One=0;
        int setSelectedButtonID_User_two=0;
        return new int[] {setSelectedButtonID_User_One,setSelectedButtonID_User_two};
    }

    /***
     * Method to disable other unselected buttons
     * @return unSelectedButtonIds
     */
    public int[] disableUnSelectedButtons(){

        int unSelectedButtonIds[]=new int[]{0};
        return unSelectedButtonIds;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /***
     * Method to calculate scores by players
     * @return
     */
    public int[] calculatePoint(){
        int scoredByUser_One=0;
        int scoredByUser_Two=0;
        return new int[]{scoredByUser_One,scoredByUser_Two};
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
    public void showAlert(){

        //TODO: Which player wins or Draw should notify here
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Game over")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
             builder.create();
             builder.show();

    }
}
