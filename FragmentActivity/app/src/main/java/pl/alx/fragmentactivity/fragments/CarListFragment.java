package pl.alx.fragmentactivity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pl.alx.fragmentactivity.R;
import pl.alx.fragmentactivity.data.CarsData;

// fragments
public class CarListFragment extends Fragment {

    ArrayAdapter<String> itemsAdapter;

    public interface OnItemSelectedListener {
        void onCarItemSelected(int position);
    }

    private OnItemSelectedListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //listener = (OnItemSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsAdapter = new ArrayAdapter<String>(
                getContext(), //context
                android.R.layout.simple_list_item_1, //layout dla elementu listy
                CarsData.carList // dane
        );
        listener = (OnItemSelectedListener) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_list,
                container,
                false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView lvItems = view.findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onCarItemSelected(position);
            }
        });
    }

}
