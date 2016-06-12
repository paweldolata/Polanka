package et.put.poznan.pl.polanka;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StartActivity extends ActionBarActivity {
    public static String DB_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        importDatabase();

        //SQLiteManager manager = new SQLiteManager(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button welcomeButton = (Button) findViewById(R.id.welcomeButton);

        Animation animation = AnimationUtils.makeInAnimation(this, true);
        animation.setDuration(1500);
        welcomeButton.startAnimation(animation);
        welcomeButton.setVisibility(View.VISIBLE);
    }

    public void importDatabase() {
        String packageName = getApplicationContext().getPackageName();
        String appDatabaseDirectory = String.format("/data/data/%s/databases", packageName);
        InputStream input = getResources().openRawResource(R.raw.wykladowcy);
        (new File(appDatabaseDirectory)).mkdir();
        DB_PATH = appDatabaseDirectory + "/wykladowcy";

        try {
            OutputStream output = new FileOutputStream(DB_PATH);
            byte[] buffer = new byte[1024];
            while(input.read(buffer) > 0) {
                output.write(buffer);
            }
            output.close();
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSearching(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
