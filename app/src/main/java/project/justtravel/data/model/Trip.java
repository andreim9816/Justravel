package project.justtravel.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import project.justtravel.utils.DateConverter;

@Entity(tableName = "trip_table")
public class Trip implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private final int id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "destination")
    private final String destination;

    @ColumnInfo(name = "type")
    private final int type;

    @ColumnInfo(name = "price")
    private final int price;

    @ColumnInfo(name = "start_date")
    @TypeConverters(DateConverter.class)
    private final Date startDate;

    @ColumnInfo(name = "end_date")
    @TypeConverters(DateConverter.class)
    private final Date endDate;

    @ColumnInfo(name = "rating")
    private final float rating;

    public Trip(int id, String name, String destination, int type, int price, Date startDate, Date endDate, float rating) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
    }

    protected Trip(Parcel in) {
        id = in.readInt();
        name = in.readString();
        destination = in.readString();
        type = in.readInt();
        price = in.readInt();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        rating = in.readFloat();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public int getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getRating() {
        return rating;
    }

    @NonNull
    @Override
    public String toString() {
        return "id: " + id +
                "name: " + name +
                "destination: " + destination +
                "type: " + type +
                "price: " + price +
                "startDate: " + startDate +
                "endDate: " + endDate +
                "rating: " + rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(destination);
        dest.writeInt(type);
        dest.writeLong(startDate.getTime());
        dest.writeLong(endDate.getTime());
        dest.writeInt(price);
        dest.writeFloat(rating);
    }
}
