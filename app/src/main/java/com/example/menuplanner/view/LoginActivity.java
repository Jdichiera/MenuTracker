package com.example.menuplanner.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menuplanner.R;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.input_login_username);
        password = findViewById(R.id.input_login_password);
        Button login = findViewById(R.id.button_login);
        database = SQLiteDatabase.openOrCreateDatabase(getApplicationContext()
                .getDatabasePath("menu_planner_database"), null);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_sample_data:
                Toast.makeText(this,
                        "Adding sample data and test user",
                        Toast.LENGTH_SHORT).show();
                addData();
                return true;
            case R.id.menu_delete_all_data:
                Toast.makeText(this,
                        "Deleting sample data and test user",
                        Toast.LENGTH_SHORT).show();
                deleteData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void login() {
        if (isValidCredential()) {
            Toast.makeText(this,
                    "Login yeah",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Login naah",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidCredential() {
        boolean isValid;
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(getApplicationContext()
                .getDatabasePath("menu_planner_database"), null);
        String name = username.getText().toString();
        String pass = password.getText().toString();
        Cursor cursor = database.query("users_table", new String[]{"userName"},
                "userName = ? AND userPassword = ?", new String[]{name, pass},
                null, null, null, null);

        isValid = (cursor != null) && (cursor.getCount() == 1);

        cursor.close();
        return isValid;
    }

    private void addData() {
        addUserData();
        addDayData();
    }

    private void deleteData() {
        deleteUserData();
        deleteDayData();
    }

    private void addUserData() {
        database.execSQL("CREATE TABLE IF NOT EXISTS users_table " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, userPassword TEXT)");
        database.execSQL("INSERT INTO 'users_table' (userName, userPassword) VALUES ('TestUser', '123')");
    }

    private void deleteUserData() {
        database.execSQL("DELETE FROM users_table");
        database.execSQL("DELETE FROM sqlite_sequence where name = 'users_table'");
    }

    private void addDayData() {

    }

    private void deleteDayData() {
        database.execSQL("DELETE FROM days_table");
        database.execSQL("DELETE FROM sqlite_sequence where name = 'days_table'");
    }
}
