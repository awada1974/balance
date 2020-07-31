package online.beapp.balance;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import online.beapp.balance.Adapters.PaymentAdapter;
import online.beapp.balance.Dialog.CreatePayment;
import online.beapp.balance.Dialog.PaymentDialog;
import online.beapp.balance.entities.Payment;
import online.beapp.balance.entities.Person;

public class AddPerson extends AppCompatActivity implements CreatePayment, View.OnClickListener {

    private final String TAG = AddPerson.class.getSimpleName();
    private Realm realm;
    private EditText fullNameEditText, phoneNumberEditText, totalAmountEditText, noteEditText;
    private ArrayList<Payment> paymentsArray = new ArrayList<>();
    private RealmList<Payment> payments = new RealmList<>();
    private Button addPaymentButton, savePaymentButton;
    PaymentAdapter adapter;
    Person person = new Person();
    ListView paymentListView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        realm = Realm.getDefaultInstance();
        fullNameEditText = findViewById(R.id.editTextName);
        phoneNumberEditText = findViewById(R.id.editTextPhoneNumber);
        totalAmountEditText = findViewById(R.id.amountEditText);
        noteEditText = findViewById(R.id.editTextNote);
        addPaymentButton = findViewById(R.id.buttonAddPayment);
        savePaymentButton = findViewById(R.id.buttonSave);
        paymentListView = findViewById(R.id.paymentListView);
        paymentListView.setNestedScrollingEnabled(true);
        addPaymentButton.setOnClickListener(this);
        savePaymentButton.setOnClickListener(this);

        adapter = new PaymentAdapter(this, paymentsArray);
        paymentListView.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("person")) {
            long id = intent.getLongExtra("person", 0);
            Log.e(TAG, String.valueOf(person.getId()));
            person = realm.where(Person.class).equalTo("id",id).findFirst();
            fullNameEditText.setText(person.getName());
            phoneNumberEditText.setText(person.getPhoneNumber());
            totalAmountEditText.setText(String.valueOf(person.getAmount()));
            noteEditText.setText(person.getNote());
            paymentsArray.addAll(person.getPayments());
            adapter.notifyDataSetChanged();

        }
    }

    public void createPayment(Payment payment) {

        if(person == null) {
            return;
        }
        payment.setPersonName(fullNameEditText.getText().toString());
        this.paymentsArray.add(payment);
        adapter.notifyDataSetChanged();
        this.payments.add(payment);

        realm.beginTransaction();
        if (person.getPayments() != null) {
            person.getPayments().add(payment);
        } else {
            person.setPayments(payments);
        }

        realm.copyToRealmOrUpdate(person);
        realm.commitTransaction();
    }

    private void createPerson() {
        String name, phone, note;
        long amount = 0;
        if (fullNameEditText.getText().toString().isEmpty()) {
            fullNameEditText.setError(getResources().getString(R.string.name_error));
            return;
        } else { name = fullNameEditText.getText().toString(); }
        if (phoneNumberEditText.getText().toString().isEmpty()){
            phoneNumberEditText.setError(getResources().getString(R.string.phone_error));
            return;
        } else { phone = phoneNumberEditText.getText().toString(); }
        if (totalAmountEditText.getText().toString().isEmpty()) {
            totalAmountEditText.setError(getResources().getString(R.string.amount_error));
        } else { amount = Long.parseLong(totalAmountEditText.getText().toString()); }

        note = noteEditText.getText().toString();

        realm.beginTransaction();

        person.setName(name);
        person.setPhoneNumber(phone);
        person.setAmount(amount);
        person.setNote(note);
        realm.insertOrUpdate(person);
        realm.commitTransaction();
        Log.w(TAG, person.getName());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonAddPayment) {
            PaymentDialog paymentDialog = new PaymentDialog(this, this);
            paymentDialog.show();
        } else {
            createPerson();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
