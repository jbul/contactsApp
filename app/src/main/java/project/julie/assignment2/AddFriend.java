package project.julie.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddFriend extends AppCompatActivity {

    EditText firstName, lastName, email, phone;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phoneNo);
        addBtn = findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = firstName.getText().toString();
                String lname = lastName.getText().toString();
                String emailAdd = email.getText().toString();
                String tel = phone.getText().toString();
                DatabaseHelper dbHelper = new DatabaseHelper(AddFriend.this);

                if (fname.isEmpty() || lname.isEmpty() || emailAdd.isEmpty() || tel.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(emailAdd)) {
                    Toast.makeText(getApplicationContext(), "Wrong email format", Toast.LENGTH_SHORT).show();
                } else if (!isValidMobile(tel)) {
                    Toast.makeText(getApplicationContext(), "Wrong phone number format", Toast.LENGTH_SHORT).show();
                } else {
                    String insertResult = dbHelper.insertFriend(fname, lname, emailAdd, tel);

                    if (insertResult.startsWith("User successfully")) {
                        Intent i = new Intent(view.getContext(), DisplayFriends.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), insertResult, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

}
