package pl.alx.androiddialogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void genericDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Infromacja");
        builder.setMessage("Wystąpił błąd podczas pobierania danych");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("Wciśnięto OK");
            }
        });

        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("Wciśnięto ANULUJ");
            }
        });

        builder.setNeutralButton("I co z tego?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("Wciśnięto przycisk neutralny");
            }
        });

        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                showToast("Cancel dialog");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    String sites[] = {"Facebook","Instagram","Tiktok","Youtube"};
    boolean checkedSites[] = {false, true, false, true};
    public void multiOptionDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ulubiona strona/aplikacja");

        builder.setMultiChoiceItems(sites, checkedSites, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedSites[which] = isChecked;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder sb = new StringBuilder();
                for (int i=0;i<checkedSites.length;i++) {
                    if (checkedSites[i]) {
                        sb.append(sites[i]+" ");
                    }
                }
                if (sb.length()>0) {
                    showToast(sb.toString());
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void singleOptionDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wybierz język");
        String lng[] = {"C++", "Java", "Kotlin","PHP","Swift"};
        builder.setSingleChoiceItems(lng, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast(lng[which]);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void datePickerDialog(View view) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        showToast( sdf.format(c.getTime()) );
                    }
                }, year, month, day );
        pickerDialog.setTitle("Podaj datę");
        pickerDialog.show();
    }

    public void timePickerDialog(View view) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog pickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        showToast(String.format("%02d:%02d", hourOfDay, minute));
                    }
                },hour, minute, true);
        pickerDialog.setTitle("Podaj czas");
        pickerDialog.show();
    }

    public void customDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(customLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et = customLayout.findViewById(R.id.editName);
                showToast( et.getText().toString() );
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}