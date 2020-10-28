package pl.alx.fragmentactivity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pl.alx.fragmentactivity.R;
import pl.alx.fragmentactivity.data.CarsData;

// fragments
public class CarDetailFragment extends Fragment {

    private int position;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args!=null) {
            position = args.getInt("position", 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_detail,
                container,
                false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvDetails = view.findViewById(R.id.tvDetails);

        tvTitle.setText(CarsData.carList[position]);
        tvDetails.setText(CarsData.carDetails[position]);

    }

}
