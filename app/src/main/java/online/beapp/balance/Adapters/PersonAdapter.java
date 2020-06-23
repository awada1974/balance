package online.beapp.balance.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmResults;
import online.beapp.balance.R;
import online.beapp.balance.entities.Person;

public class PersonAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Person> personArrayList;
    LayoutInflater layoutInflater;
    public PersonAdapter(Context context, RealmResults<Person> personArrayList) {
        this.context = context;
        this.personArrayList = personArrayList;
        layoutInflater = LayoutInflater.from(context);
        Log.w("TAG", personArrayList.size() + "");
    }

    @Override
    public int getCount() {
        return personArrayList.size();
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
        view = layoutInflater.inflate(R.layout.person_list_view, null);
        TextView nameText = view.findViewById(R.id.textViewPersonName);
        TextView phoneText = view.findViewById(R.id.textViewPersonPhone);
        TextView dueText = view.findViewById(R.id.textViewPersonDue);
        Person person = personArrayList.get(position);


        String name = person.getName();
        String amount = String.valueOf(person.getAmount());
        String due = String.valueOf(person.getDue());


        nameText.setText(name);
        dueText.setText(due);
        phoneText.setText(person.getPhoneNumber());

        return view;
    }
}
