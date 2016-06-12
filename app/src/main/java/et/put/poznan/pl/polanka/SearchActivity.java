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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends ActionBarActivity {
    private int chosenLayout = 0;

    private void hideAllLayoutsExcept(RelativeLayout[] layouts, int index) {
        for(int i = 0; i < layouts.length; ++i) {
            if(i == index)
                layouts[i].setVisibility(View.VISIBLE);
            else
                layouts[i].setVisibility(View.GONE);
        }

        chosenLayout = index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Spinner spinner = (Spinner) findViewById(R.id.wayToFilter);

        final RelativeLayout[] layouts = new RelativeLayout[] {
                (RelativeLayout) findViewById(R.id.floorFiltering),
                (RelativeLayout) findViewById(R.id.chairFiltering),
                (RelativeLayout) findViewById(R.id.numberFiltering),
                (RelativeLayout) findViewById(R.id.surnameFiltering)
        };

        SQLiteManager manager = new SQLiteManager(getApplicationContext());

        final ListView surnamesListView = (ListView) findViewById(R.id.filterSurname);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lecturer_item, manager.getSurnames());
        surnamesListView.setAdapter(adapter);
        final EditText chooseSurname = (EditText) findViewById(R.id.chooseSurname);
        surnamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseSurname.setText(surnamesListView.getItemAtPosition(position).toString());
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hideAllLayoutsExcept(layouts, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    public void startFiltering(View view) {
        Intent intent;

        switch(chosenLayout) {
            case 0: // wyswietlamy liste wykladowcow
                intent = new Intent(this, LecturerListActivity.class);
                intent.putExtra("way", "floor");
                Spinner chooseFloorSpinner = (Spinner) findViewById(R.id.chooseFloor);
                intent.putExtra("which", String.valueOf(chooseFloorSpinner.getSelectedItemPosition()));
                break;
            case 1: // wyswietlamy liste wykladowcow
                intent = new Intent(this, LecturerListActivity.class);
                intent.putExtra("way", "chair");
                Spinner chooseChairSpinner = (Spinner) findViewById(R.id.chooseChair);
                intent.putExtra("which", chooseChairSpinner.getSelectedItem().toString().replace("Katedra ", ""));
                break;
            case 2: // wyswietlamy konkretnego wykladowce
                intent = new Intent(this, LecturerListActivity.class);
                intent.putExtra("way", "number");
                EditText chooseRoom = (EditText) findViewById(R.id.chooseNumber);
                intent.putExtra("which", chooseRoom.getText().toString());
                break;
            case 3: // wyswietlamy liste wykladowcow
            default:
                intent = new Intent(this, LecturerInfoActivity.class);

                EditText chooseSurname = (EditText) findViewById(R.id.chooseSurname);
                String name = chooseSurname.getText().toString();

                if(name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Musisz kogoś wybrać.", Toast.LENGTH_SHORT).show();
                    return;
                }

                intent.putExtra("name", name);
                break;
        }

        startActivity(intent);
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
