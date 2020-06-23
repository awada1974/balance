package online.beapp.balance.Dialog;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import online.beapp.balance.R;
import online.beapp.balance.entities.Payment;

public class PaymentDialog extends Dialog implements View.OnClickListener {

    private Activity currentActivity;
    private EditText amountEditText, dateEditText;
    private Button saveButton, cancelButton;
    private CreatePayment interfacePayment;

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

        saveButton = findViewById(R.id.savePaymentDialogButton);
        cancelButton = findViewById(R.id.cancelPaymentDialogButton);

        amountEditText = findViewById(R.id.editTextPaymentBalance);
        dateEditText = findViewById(R.id.editTextPaymentDate);

        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

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
        if (date.isEmpty()) {
            payment.setAmount(amount);
        } else {
            payment.setCreated_at(date);
        }
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
