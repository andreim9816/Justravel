package project.justtravel.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.justtravel.R;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    class TripViewHolder extends RecyclerView.ViewHolder {
        private final TextView tripNameTextView, tripDestinationTextView, tripPrice;

        private TripViewHolder(View itemView) {
            super(itemView);
            tripNameTextView = itemView.findViewById(R.id.tripNameTextView);
            tripDestinationTextView = itemView.findViewById(R.id.tripDestinationTextView);
            tripPrice = itemView.findViewById(R.id.tripPriceTextView);
        }
    }

    private final LayoutInflater inflater;
    private List<Trip> trips; // Cached copy of trips

    public TripListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TripViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        if (trips != null) {
            Trip current = trips.get(position);
            holder.tripNameTextView.setText(current.getName());
            holder.tripDestinationTextView.setText(current.getDestination());
            holder.tripPrice.setText(current.getPrice() + "");
        } else {
            // Covers the case of data not being ready yet.
            holder.tripNameTextView.setText("not set");
            holder.tripDestinationTextView.setText("not set");
            holder.tripPrice.setText("not set");
        }
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // trips has not been updated (means initially, it's null, and we cannot return null).
    @Override
    public int getItemCount() {
        if (trips != null)
            return trips.size();
        else return 0;
    }
}
