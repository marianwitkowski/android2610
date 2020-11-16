package pl.alx.sqliteexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {

    Context ctx;
    List<Employee> employeeList;
    int layoutRes;
    DatabaseManager mDatabase;

    public EmployeeAdapter(@NonNull Context context, int resource,
                           List<Employee> employeeList, DatabaseManager mDatabase ) {
        super(context, resource, employeeList);
        ctx = context;
        layoutRes = resource;
        this.employeeList = employeeList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(layoutRes, null);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDepartament = view.findViewById(R.id.tvDepartament);
        TextView tvSalary = view.findViewById(R.id.tvSalary);
        TextView tvTs = view.findViewById(R.id.tvTs);

        Employee employee = employeeList.get(position);

        tvName.setText(employee.getName());
        tvDepartament.setText(employee.getDepartament());
        tvSalary.setText( String.valueOf(employee.getSalary()) );
        tvTs.setText( employee.getJoiningdate() );

        //usuń
        view.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employee);
            }
        });

        return view;
    }

    private void deleteEmployee(Employee employee) {
        // dobrze żeby było potwierdzenie
        mDatabase.deleteEmployee(employee.getId());

        //refresh danych z bazy

        //notyfikacja zmian danych w adapterze
        notifyDataSetChanged();
    }
}
