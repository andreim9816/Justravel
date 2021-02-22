package project.justtravel.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import project.justtravel.R;
import project.justtravel.data.model.Trip;
import project.justtravel.utils.ClickInterface;

import static project.justtravel.fragments.AddTripFragment.CITY_BREAK_TYPE;
import static project.justtravel.fragments.AddTripFragment.MOUNTAINS_TYPE;
import static project.justtravel.fragments.AddTripFragment.SEASIDE_TYPE;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView tripNameTextView, tripDestinationTextView, tripPrice;
        private final CardView cardView;
        private final ImageView imageView;
        ClickInterface clickInterface;

        private TripViewHolder(View itemView, ClickInterface clickInterface) {
            super(itemView);
            this.clickInterface = clickInterface;

            tripNameTextView = itemView.findViewById(R.id.tripNameTextView);
            tripDestinationTextView = itemView.findViewById(R.id.tripDestinationTextView);
            tripPrice = itemView.findViewById(R.id.tripPriceTextView);
            cardView = itemView.findViewById(R.id.tripCardview);
            imageView = itemView.findViewById(R.id.tripImageView);

            // attach onClickListener to entire TripViewHolder
            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickInterface.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            clickInterface.onLongClick(getAdapterPosition());
            return true;
        }
    }

    private final LayoutInflater inflater;
    private List<Trip> trips; // Cached copy of trips
    private final ClickInterface clickInterface;

    public TripListAdapter(Context context, ClickInterface clickInterface) {
        inflater = LayoutInflater.from(context);
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TripViewHolder(itemView, clickInterface);
    }

    @Override
    public void onBindViewHolder(@NotNull TripViewHolder holder, int position) {
        if (trips != null) {
            Trip current = trips.get(position);
            String price = current.getPrice() + "€";
            holder.tripNameTextView.setText(current.getName());
            holder.tripDestinationTextView.setText(current.getDestination());
            holder.tripPrice.setText(price);
            switch (current.getType()) {
                case CITY_BREAK_TYPE:
                    holder.imageView.setBackgroundResource(R.drawable.citybreak_recycler_view);
                    break;
                case SEASIDE_TYPE:
                    holder.imageView.setBackgroundResource(R.drawable.seaside_recycler_view);
                    break;
                case MOUNTAINS_TYPE:
                    holder.imageView.setBackgroundResource(R.drawable.mountains_recycler_view);
                    break;
                default:
                    holder.imageView.setBackgroundResource(R.drawable.not_found);
                    break;
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.tripNameTextView.setText(R.string.name_not_set);
            holder.tripDestinationTextView.setText(R.string.destionation_not_set);
            holder.tripPrice.setText(R.string.price_not_set);
        }
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    // getItemCount() is called many times, and when it is first called,
    // trips has not been updated (means initially, it's null, and we cannot return null).
    @Override
    public int getItemCount() {
        if (trips != null)
            return trips.size();
        return 0;
    }
}
