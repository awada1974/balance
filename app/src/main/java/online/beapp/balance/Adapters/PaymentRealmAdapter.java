package online.beapp.balance.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import online.beapp.balance.PaymentsActivity;
import online.beapp.balance.R;
import online.beapp.balance.entities.Currency;
import online.beapp.balance.entities.Payment;

public class PaymentRealmAdapter extends RealmBaseAdapter<Payment> implements ListAdapter {



    private PaymentsActivity activity;
    private static class ViewHolder {
        TextView currencyText;
        TextView personTextView;
        TextView amountTextView;
        TextView createTextView;
    }

    public PaymentRealmAdapter(PaymentsActivity activity, OrderedRealmCollection<Payment> data) {
        super(data);
        this.activity = activity;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.payment_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.currencyText = convertView.findViewById(R.id.currencyAmount);
            viewHolder.personTextView = convertView.findViewById(R.id.personNameText);
            viewHolder.createTextView = convertView.findViewById(R.id.dateText);
            viewHolder.amountTextView = convertView.findViewById(R.id.amountText);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            Payment payment = adapterData.get(position);
            if (payment.getCurrency() == 0) {
                viewHolder.currencyText.setText(Currency.USD.toString());
            } else { viewHolder.currencyText.setText(Currency.LBP.toString()); }

            viewHolder.amountTextView.setText(String.valueOf(payment.getAmount()));
            viewHolder.createTextView.setText(payment.getCreated_at());
            viewHolder.personTextView.setText(payment.getPersonName());
        }

        return convertView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            if (adapterData != null) {
                Payment payment = adapterData.get(position);

            }
        }
    };
}
