package et.put.poznan.pl.polanka;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class LecturerListActivity extends ActionBarActivity {
    private ListView lecturersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_list);

        Intent intent = getIntent();

        String way = intent.getStringExtra("way");
        String which = intent.getStringExtra("which");

        lecturersList = (ListView) findViewById(R.id.lecturers_list);
        TextView lecturers_info = (TextView) findViewById(R.id.lecturers_info);

        if(way.equals("floor")) {
            lecturers_info.setText(String.format("Wykładowcy na piętrze %s", which));
            loadByFloor(which);
        }
        else if(way.equals("chair")) {
            lecturers_info.setText(String.format("Wykładowcy w katedrze %s", which));
            loadByChair(which);
        }
        else if(way.equals("number")) {
            lecturers_info.setText(String.format("Wykładowcy w pokoju nr %s", which));
            loadByNumber(which);
        }
        else {
            finish();
        }

        final EditText lecturerName = (EditText) findViewById(R.id.lecturer_name);
        lecturersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lecturerName.setText(lecturersList.getItemAtPosition(position).toString());
            }
        });
    }

    private void loadByNumber(String which) {
        SQLiteManager manager = new SQLiteManager(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lecturer_item,
                manager.getLecturersByRoomNumber(which));
        lecturersList.setAdapter(adapter);
        manager.closeConnection();
    }

    private void loadByChair(String which) {
        SQLiteManager manager = new SQLiteManager(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lecturer_item,
                manager.getLecturersByChair(which));
        lecturersList.setAdapter(adapter);
        manager.closeConnection();
    }

    private void loadByFloor(String which) {
        SQLiteManager manager = new SQLiteManager(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lecturer_item,
                manager.getLecturersByFloor(which));
        lecturersList.setAdapter(adapter);
        manager.closeConnection();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lecturer_list, menu);
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

    public void filter(View view) {
        EditText lecturerName = (EditText) findViewById(R.id.lecturer_name);
        String name = lecturerName.getText().toString();

        if(name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Musisz kogoś wybrać.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, LecturerInfoActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
