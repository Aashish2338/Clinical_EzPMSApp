package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ezpms.ezpmsapp.Models.PatientListData;
import com.ezpms.ezpmsapp.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteAdapter extends ArrayAdapter<PatientListData> implements Filterable {

    private List<PatientListData> patientListData;
    private List<PatientListData> filteredPlacesList;

    public AutoCompleteAdapter(@NonNull Context context, List<PatientListData> patientList) {
        super(context, 0, patientList);
        patientListData = new ArrayList<>(patientList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_item, parent, false);
        }

        TextView placeLabel = convertView.findViewById(R.id.textPatients);

        PatientListData place = getItem(position);
        if (place != null) {
            placeLabel.setText(place.getFirstName() + " " + place.getLastName() + "/" + place.getPhoneNumber());
        }

        return convertView;
    }

    private Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            filteredPlacesList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredPlacesList.addAll(patientListData);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PatientListData place : patientListData) {
                    if (place.getFirstName().toLowerCase().contains(filterPattern)) {
                        filteredPlacesList.add(place);
                    } else if (place.getLastName().toLowerCase().contains(filterPattern)) {
                        filteredPlacesList.add(place);
                    } else if (place.getPhoneNumber().toLowerCase().contains(filterPattern)) {
                        filteredPlacesList.add(place);
                    }
                }
            }

            results.values = filteredPlacesList;
            results.count = filteredPlacesList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((PatientListData) resultValue).getFirstName() + " " + ((PatientListData) resultValue).getLastName()
                    + "/" + ((PatientListData) resultValue).getPhoneNumber();
        }
    };
}