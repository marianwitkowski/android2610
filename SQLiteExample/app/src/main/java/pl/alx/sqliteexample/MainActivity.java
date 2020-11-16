package pl.alx.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName, etSalary;
    Spinner spDepartament;

    DatabaseManager mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicjalizacja obsługi SQLite
        mDatabase = new DatabaseManager(this);

        etName = findViewById(R.id.etName);
        etSalary = findViewById(R.id.etSalary);
        spDepartament = findViewById(R.id.spDepartament);
    }

    public void clickButton(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                addEmployee();
                break;
            case R.id.btnList:
                showList();
                break;
        }
    }

    private void showList() {
    }

    private void addEmployee() {
        String name = etName.getText().toString().trim();
        String salary = etSalary.getText().toString().trim();
        String departament = spDepartament.getSelectedItem().toString();

        if (name.isEmpty()) {
            etName.setError("Podaj nazwisko");
            etName.requestFocus();
            return;
        }

        if (salary.isEmpty()) {
            etSalary.setError("Podaj wynagrodzenie");
            etSalary.requestFocus();
            return;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joinDate = sdf.format(cal.getTime());

        mDatabase.addEmployee(name, departament, joinDate, Double.parseDouble(salary) );

    }
}