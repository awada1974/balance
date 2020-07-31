package online.beapp.balance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import online.beapp.balance.Adapters.PaymentAdapter;
import online.beapp.balance.Adapters.PaymentRealmAdapter;
import online.beapp.balance.entities.Payment;

public class PaymentsActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<Payment> paymentsResult;
    private PaymentRealmAdapter adapter;
    private ListView listView;
    private RealmResults<Payment> paymentsArray;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        realm = Realm.getDefaultInstance();
        listView = findViewById(R.id.paymentListView2);
        searchView = findViewById(R.id.paymentSearchView);

        paymentsArray = realm.where(Payment.class).findAll();
        adapter = new PaymentRealmAdapter(this, paymentsArray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Payment payment = (Payment) parent.getAdapter().getItem(position);
                final EditText paymentNameEditText = new EditText(PaymentsActivity.this);
                paymentNameEditText.setText(payment.getPersonName());

                AlertDialog dialog = new AlertDialog.Builder(PaymentsActivity.this)
                        .setTitle("Edit Task")
                        .setView(paymentNameEditText)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                changePaymentName(payment.getId(),String.valueOf(paymentNameEditText.getText()));
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTask(payment.getId());
                            }
                        })
                        .create();
                dialog.show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    paymentsArray = realm.where(Payment.class).findAll();
                }else {
                    paymentsArray = realm.where(Payment.class).equalTo("personName", newText).findAllAsync();
                }
                Log.e("SEARCH", newText + " Count " + paymentsArray.size());
                adapter = new PaymentRealmAdapter(PaymentsActivity.this, paymentsArray);
                return true;
            }
        });




    }

    private void changePaymentName(final Long taskId, final String name) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Payment payment = realm.where(Payment.class).equalTo("id", taskId).findFirst();
                assert payment != null;
                payment.setPersonName(name);
            }
        });
    }

    private void deleteTask(final Long taskId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Payment.class).equalTo("id", taskId)
                        .findFirst()
                        .deleteFromRealm();
            }
        });
    }




}
