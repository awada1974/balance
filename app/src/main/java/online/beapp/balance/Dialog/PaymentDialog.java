package online.beapp.balance.Dialog;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import online.beapp.balance.R;
import online.beapp.balance.entities.Currency;
import online.beapp.balance.entities.Payment;

public class PaymentDialog extends Dialog implements View.OnClickListener {

    final Calendar myCalendar = Calendar.getInstance();
    private Activity currentActivity;
    private EditText amountEditText, dateEditText;
    private Button saveButton, cancelButton;
    private CreatePayment interfacePayment;
    private Spinner spinnerCur;
    private Currency currency;

    public PaymentDialog(Activity currentActivity, CreatePayment interfacePayment) {
        super(currentActivity);
        this.currentActivity = currentActivity;
        this.interfacePayment = interfacePayment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.payment_custom_dialog);

        currency = Currency.USD;
        saveButton = findViewById(R.id.savePaymentDialogButton);
        cancelButton = findViewById(R.id.cancelPaymentDialogButton);

        amountEditText = findViewById(R.id.editTextPaymentBalance);
        dateEditText = findViewById(R.id.editTextPaymentDate);

        spinnerCur = findViewById(R.id.spinnerCurrency);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        spinnerCur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int
                    i, long l) {

                switch (i) {
                    case 0:
                        currency = Currency.USD;
                        break;
                    case 1:
                        currency = Currency.LBP;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    private void updateLabel() {
        String myFormat = "yyyy/mm/dd HH:MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

     private void createPayment() {
        long amount;
        String date;
        Payment payment = new Payment();
        if (amountEditText.getText().toString().isEmpty()) {
            amountEditText.setError(currentActivity.getResources().getString(R.string.amount_error));
            return;
        } else { amount = Long.parseLong(amountEditText.getText().toString()); }
        date = dateEditText.getText().toString();
         payment.setAmount(amount);
        if (!date.isEmpty()) {
            payment.setCreated_at(date);
        }
        payment.setCurrency(currency.getValue());
        interfacePayment.createPayment(payment);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.savePaymentDialogButton:
                createPayment();
                dismiss();
                break;
            case R.id.cancelPaymentDialogButton:
                dismiss();
                break;
        }
    }


}
