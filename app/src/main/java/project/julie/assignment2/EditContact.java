package project.julie.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContact extends AppCompatActivity {

    private EditText fname, lname, email, phone;
    private Button editBtn;

    private int id;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        databaseHelper = new DatabaseHelper(this);

        fname = findViewById(R.id.firstName_edit);
        lname = findViewById(R.id.lastName_edit);
        email = findViewById(R.id.email_edit);
        phone = findViewById(R.id.phoneNo_edit);

        Intent i  = getIntent();
        String firstName = i.getStringExtra("keyFirstName");
        String lastName = i.getStringExtra("keyLastName");
        String email1= i.getStringExtra("keyEmail");
        String phone1 = i.getStringExtra("keyPhone");
        id = i.getIntExtra("keyId", -1);

        fname.setText(firstName);
        lname.setText(lastName);
        email.setText(email1);
        phone.setText(phone1);


    }

    public void update(View view) {

        String fnameUpdated = fname.getText().toString();
        String lnameUpdated = lname.getText().toString();
        String emailUpdated = email.getText().toString();
        String phoneUpdated = phone.getText().toString();
        if (id > 0) {
            databaseHelper.update(id, fnameUpdated, lnameUpdated, emailUpdated, phoneUpdated);
        }

        if (fnameUpdated.isEmpty() || lnameUpdated.isEmpty() || emailUpdated.isEmpty() || phoneUpdated.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else if (isValidEmail(emailUpdated) == false) {
            Toast.makeText(getApplicationContext(), "Wrong email format", Toast.LENGTH_SHORT).show();
        } else if (isValidMobile(phoneUpdated) == false) {
            Toast.makeText(getApplicationContext(), "Wrong phone number format", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(view.getContext(), DisplayFriends.class);
            startActivity(i);
        }
    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
