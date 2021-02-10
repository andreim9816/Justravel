package project.justtravel.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import project.justtravel.utils.DateConverter;

@Entity(tableName = "trip_table")
public class Trip {
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
}
