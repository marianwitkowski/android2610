package pl.alx.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    ListView listView;
    List<Employee> employeeList = new ArrayList<Employee>();
    DatabaseManager mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_employee);

        mDatabase = new DatabaseManager(this);

        listView = findViewById(R.id.listViewEmployees);

        loadData();
    }

    private void loadData() {
        Cursor cursor = mDatabase.getAllEmployees();
        if (cursor.moveToFirst()) {
            //odczyt sekwencyjny danych
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String dept = cursor.getString(2);
                String ts = cursor.getString(3);
                double salary = cursor.getDouble(4);

                Employee employee = new Employee(id, name, dept, ts, salary);
                employeeList.add(employee);
            } while (cursor.moveToNext());

            EmployeeAdapter employeeAdapter = new EmployeeAdapter(this, R.layout.row_employee,
                                                            employeeList, mDatabase);
            listView.setAdapter(employeeAdapter);
        }

    }
}