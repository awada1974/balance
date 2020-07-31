package online.beapp.balance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;
import online.beapp.balance.Adapters.PersonAdapter;
import online.beapp.balance.entities.Person;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = DashboardActivity.class.getSimpleName();
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
        Button paymentList = findViewById(R.id.buttonPaymentList);
        paymentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PaymentsActivity.class);
                startActivity(intent);
            }
        });

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AddPerson.class);
                startActivity(intent);
            }
        });

        loadPerson();
        personListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent personDetailsIntent = new Intent(DashboardActivity.this, AddPerson.class);
                Person person = personRealmResults.get(position);
                personDetailsIntent.putExtra("person", person.getId());

                String idString = String.valueOf(person.getId());
                Log.e(TAG, String.valueOf(person.getId()));
                startActivity(personDetailsIntent);
            }
        });
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
