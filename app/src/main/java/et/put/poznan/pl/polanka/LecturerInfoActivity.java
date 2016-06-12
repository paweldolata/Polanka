package et.put.poznan.pl.polanka;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class LecturerInfoActivity extends ActionBarActivity {
    private Lecturer lec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_info);
        Intent data = getIntent();
        String surname = data.getStringExtra("name");
        System.out.println(surname);

        SQLiteManager manager = new SQLiteManager(getApplicationContext());

        lec = manager.getLecturerBySurname(surname);
        initLecturer(lec);
        manager.closeConnection();
    }

    private void initLecturer(Lecturer lec) {
        TextView lecturerName = (TextView) findViewById(R.id.lecturer);
        TextView lecturerRoomNumber = (TextView) findViewById(R.id.lecturerRoomNumber);
        TextView lecturerChair = (TextView) findViewById(R.id.lecturerChair);
        TextView lecturerFloor = (TextView) findViewById(R.id.lecturerFloor);

        lecturerName.setText(lec.getName() + " " + lec.getSurname());
        lecturerRoomNumber.setText(String.format("Pokój numer: %s", lec.getRoomNumber()));
        lecturerChair.setText(String.format("Katedra: %s", lec.getChair()));
        lecturerFloor.setText(String.format("Piętro: %s", lec.getFloor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lecturer_info, menu);
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

    public void goBack(View view) {
        finish();
    }

    public void moreFromFloor(View view) {
        Intent intent = new Intent(this, LecturerListActivity.class);

        intent.putExtra("way", "floor");
        intent.putExtra("which", lec.getFloor());

        startActivity(intent);
    }

    public void moreFromRoom(View view) {
        Intent intent = new Intent(this, LecturerListActivity.class);

        intent.putExtra("way", "number");
        intent.putExtra("which", lec.getRoomNumber());

        startActivity(intent);
    }

    public void moreFromChair(View view) {
        Intent intent = new Intent(this, LecturerListActivity.class);

        intent.putExtra("way", "chair");
        intent.putExtra("which", lec.getChair());

        startActivity(intent);
    }
}
