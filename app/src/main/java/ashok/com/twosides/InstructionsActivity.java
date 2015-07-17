package ashok.com.twosides;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class InstructionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        TextView textViewInstructions = (TextView) findViewById(R.id.tv_instructions);
        textViewInstructions.setText("Instructions");
        TextView textViewBulletPoints = (TextView) findViewById(R.id.tv_bullet);
        String bulletPoints="\n1. This is a two player game\n2. Place the device in the middle\n3. Two players has to sit opposite\n4. For each player there will be six boxes in their sides\n5. In the middle of two sides, one magic box is present.\n6. So, the magic box will display a color for each level\n7. And, the players has to choose the color from their six boxes to match the magic box color ";
        textViewBulletPoints.setText(bulletPoints);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instructions, menu);
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
}
