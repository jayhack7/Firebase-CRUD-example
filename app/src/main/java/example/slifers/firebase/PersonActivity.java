package example.slifers.firebase;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PersonActivity extends AppCompatActivity {
    private static final String EXTRA_Person = "Person";
    private DatabaseReference database;

    private TextView first;
    private TextView last;
    private TextView dob;
    private TextView zip;
    private TextView phone;
    boolean error_first;
    boolean error_last;
    boolean error_dob;
    boolean error_zip;
    boolean et_error;
    Calendar myCalendar = Calendar.getInstance();
    private Person Person;

    public static Intent newInstance(Context context, Person Person) {
        Intent intent = new Intent(context, PersonActivity.class);
        if (Person != null) {
            intent.putExtra(EXTRA_Person, Person);
        }

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        database = FirebaseDatabase.getInstance().getReference();



        first = (TextView) findViewById(R.id.et_first);
        last = (TextView) findViewById(R.id.et_last);
        dob = (TextView) findViewById(R.id.et_dob);
        zip = (TextView) findViewById(R.id.et_zip);
        phone = (TextView) findViewById(R.id.et_phone);

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
        dob.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PersonActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Person = getIntent().getParcelableExtra(EXTRA_Person);
        if (Person != null) {
            first.setText(Person.getFirst());
            last.setText(Person.getLast());
            dob.setText(Person.getDob());
            zip.setText(Person.getZipcode());
            phone.setText(Person.getPhone());

        }
        zip.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    return true;
                }
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation shake = AnimationUtils.loadAnimation(getBaseContext(), R.anim.edittext_shake);


                if ((first.getText().toString().trim().isEmpty()) ||
                        (last.getText().toString().trim().isEmpty()) ||
                        (dob.getText().toString().trim().isEmpty()) ||
                        (zip.getText().toString().trim().isEmpty()) ||
                    (phone.getText().toString().trim().isEmpty()))

                {
                    vibrate();
                    Toast.makeText(getBaseContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    et_error = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.fab_error, getApplicationContext().getTheme()));
                        fab.setBackgroundTintList(getResources().getColorStateList(R.color.error));
                    } else {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.fab_error));
                        fab.setBackgroundTintList(getResources().getColorStateList(R.color.error));

                    }


                }else{
                    et_error = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_plus, getApplicationContext().getTheme()));
                        fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                    } else {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_plus));
                        fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));

                    }

                }
                int length = zip.getText().length();
                if (length <= 4){
                    zip.setError("Zip Code needs 5 digits.");
                    vibrate();
                    et_error = true;


                }else{
                    et_error = false;


                }
                int length2 = phone.getText().length();
                if (length2 <= 9){
                    phone.setError("Phone Number needs 10 digits.");
                    vibrate();
                    et_error = true;


                }else{
                    et_error = false;


                }

                if (et_error == false) {

                if (Person == null) {
                    Person = new Person();
                    Person.setUid(database.child("Persons").push().getKey());
                    Toast.makeText(getBaseContext(), "Created a new entry!", Toast.LENGTH_SHORT).show();

                }

                    Person.setFirst(first.getText().toString());
                    Person.setLast(last.getText().toString());
                    Person.setDob(dob.getText().toString());
                    Person.setZipcode(zip.getText().toString());
                    Person.setPhone(phone.getText().toString());

                    database.child("Persons").child(Person.getUid()).setValue(Person);

                    finish();

                }
            }
        });
    }
    private void updateLabel() {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        DateFormat dateInstance = SimpleDateFormat.getDateInstance();

      //  dob.setText(sdf.format(myCalendar.getTime()));

        dob.setText( dateInstance.format(myCalendar.getTime()));
    }
    public void vibrate() {
        Vibrator vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrate.vibrate(50);
    }

}
