package online.beapp.balance;

import androidx.appcompat.app.AppCompatActivity;

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
        addPaymentButton.setOnClickListener(this);
        savePaymentButton.setOnClickListener(this);

        adapter = new PaymentAdapter(this, paymentsArray);
        paymentListView.setAdapter(adapter);
    }

    public void createPayment(Payment payment) {
        if(person == null) {
            return;
        }
        this.paymentsArray.add(payment);
        adapter.notifyDataSetChanged();
        this.payments.add(payment);
        realm.beginTransaction();
        person.setPayments(payments);
        realm.insertOrUpdate(person);
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
