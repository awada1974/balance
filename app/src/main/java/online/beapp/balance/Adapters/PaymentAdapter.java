package online.beapp.balance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.RealmList;
import online.beapp.balance.R;
import online.beapp.balance.entities.Currency;
import online.beapp.balance.entities.Payment;

public class PaymentAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Payment> payments;
    LayoutInflater layoutInflater;

    public PaymentAdapter(Context context, ArrayList<Payment> payments) {
        this.context = context;
        this.payments = payments;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return payments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.payment_list_view, null);
        TextView currency = view.findViewById(R.id.paymentCurrency);
        TextView amountText = view.findViewById(R.id.textViewPaymentAmount);
        TextView dateText = view.findViewById(R.id.textViewPaymentDate);
        String amount = String.valueOf(payments.get(position).getAmount());
        amountText.setText(amount);
        dateText.setText(payments.get(position).getCreated_at());
        if (payments.get(position).getCurrency() == 0) {
            currency.setText(Currency.USD.toString());
        } else { currency.setText(Currency.LBP.toString()); }


        return view;
    }
}
