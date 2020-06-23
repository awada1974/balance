package online.beapp.balance.entities;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private String phoneNumber;
    private long due;
    private long amount;
    private String note;
    private RealmList<Payment> payments;
    private String create_at;
    private String updated_at;

    public Person() {
        id = UUID.randomUUID().getMostSignificantBits();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        this.create_at = dateFormat.format(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getDue() {
        return due;
    }

    public void setDue(long due) {
        this.due = due;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(RealmList<Payment> payments) {
        this.payments = payments;
    }
}
