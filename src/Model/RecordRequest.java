package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

/**
 * This class will be responsible for adding/removing/editing records from the database
 * It's used as a wrapper for the management purposes
 */
public abstract class RecordRequest implements Serializable, Comparable<RecordRequest> {
    protected final LocalDate dateOfRequest;
    protected final LocalTime timeOfRequest;
    protected final Record record;
    public RecordRequest(Record record){
        this.record = record;
        this.dateOfRequest = LocalDate.now();
        this.timeOfRequest = LocalTime.now();
    }
    public abstract boolean saveRequest();

    @Override
    public int compareTo(RecordRequest o) {
        return (dateOfRequest.compareTo(o.dateOfRequest) == 0) ?
                timeOfRequest.compareTo(o.timeOfRequest) :
                dateOfRequest.compareTo(o.dateOfRequest);
    }
}
