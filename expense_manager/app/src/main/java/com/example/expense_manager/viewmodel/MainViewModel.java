package com.example.expense_manager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.expense_manager.models.Transaction;
import com.example.expense_manager.utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {
    Realm realm;
   public  MutableLiveData<RealmResults<Transaction> > transaction = new MutableLiveData<>();
   public  MutableLiveData<RealmResults<Transaction> > categoriestransaction = new MutableLiveData<>();
   public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
   public MutableLiveData<Double> totalExpense= new MutableLiveData<>();
   public MutableLiveData<Double> totalAmount = new MutableLiveData<>();
   Calendar calendar;
    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setUpDatabase();

    }
    void setUpDatabase(){
        realm = Realm.getDefaultInstance();
    }
    public void addTransaction(){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Loan","Khalti","some note here",new Date(),1500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE,"Others","Esewa","some note here",new Date(),-1500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Rent","Cash","some note here",new Date(),1500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction((Constants.EXPENSE),"Investment","Esewa","sonme note here",new Date(),2400,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Business","Cash","some note here",new Date(),1500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE,"Others","Esewa","some note here",new Date(),-1500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Rent","Cash","some note here",new Date(),1500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction((Constants.EXPENSE),"Investment","Esewa","sonme note here",new Date(),2400,new Date().getTime()));
        realm.commitTransaction();

    }
    public void addTransaction( Transaction transaction){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(transaction);
        realm.commitTransaction();

    }
    public void getTransaction(Calendar calendar,String type){
        this.calendar=calendar;
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        RealmResults<Transaction> newtransactions=null;
        if(Constants.SELECTED_STATS==Constants.DAILY){

            newtransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",calendar.getTime())
                    .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                    .equalTo("type",type)
                    .findAll();


        } else if (Constants.SELECTED_STATS == Constants.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH,0);
            Date startTime = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime() ;

            newtransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .equalTo("type",type)
                    .findAll();



        }

        categoriestransaction.setValue(newtransactions);


    }
    public void getTransaction(Calendar calendar){
        this.calendar=calendar;
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        double income=0;
        double expense=0;
        double totalamount=0;
        RealmResults<Transaction> newtransactions=null;
        if(Constants.SELECTED_TAB==Constants.DAILY){

            newtransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",calendar.getTime())
                    .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                    .findAll();
             income =realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",calendar.getTime())
                    .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                    .equalTo("type",Constants.INCOME)
                    .sum("amount")

                    .doubleValue();

           expense =realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",calendar.getTime())
                    .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                    .equalTo("type",Constants.EXPENSE)
                    .sum("amount")

                    .doubleValue();

          totalamount =realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",calendar.getTime())
                    .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                    .sum("amount")
                    .doubleValue();

        } else if (Constants.SELECTED_TAB==Constants.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH,0);
            Date startTime = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime() ;

             newtransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .findAll();
            income =realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .equalTo("type",Constants.INCOME)
                    .sum("amount")

                    .doubleValue();

            expense =realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .equalTo("type",Constants.EXPENSE)
                    .sum("amount")

                    .doubleValue();

            totalamount =realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .sum("amount")
                    .doubleValue();


        }

        totalIncome.setValue(income);
        totalAmount.setValue(totalamount);
        totalExpense.setValue(expense);
//        RealmResults<Transaction> newtransactions = realm.where(Transaction.class)
//                .equalTo("date",calendar.getTime())
//                .findAll();
        transaction.setValue(newtransactions);


    }
    public void deleteTransaction(Transaction transaction) {
        realm.beginTransaction();
        transaction.deleteFromRealm();
        realm.commitTransaction();
        getTransaction(calendar);
    }





}
