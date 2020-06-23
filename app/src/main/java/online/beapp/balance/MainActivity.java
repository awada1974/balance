package online.beapp.balance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import online.beapp.balance.Adapters.PersonAdapter;
import online.beapp.balance.entities.Person;

public class MainActivity extends AppCompatActivity {

    private Button addPerson;
    private ListView personListView;
    private Realm realm;
    RealmResults<Person> personRealmResults;
    PersonAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        addPerson = findViewById(R.id.buttonAddRecord);
        personListView = findViewById(R.id.personListView);

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPerson.class);
                startActivity(intent);
            }
        });

        loadPerson();
    }

    private void loadPerson() {
        personRealmResults = realm.where(Person.class).findAll();
        adapter = new PersonAdapter(this, personRealmResults);
        Log.w("TAG", "" + personRealmResults.size());
        Log.w("TAG", personRealmResults.asJSON());
        personListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
