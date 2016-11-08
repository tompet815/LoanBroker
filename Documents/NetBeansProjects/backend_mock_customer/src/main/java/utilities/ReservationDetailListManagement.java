package utilities;

import generalstuff.ReservationDetail;
import java.util.HashMap;
import java.util.Map;

public class ReservationDetailListManagement {

    private static Map<Long, ReservationDetail> reservationDetailMap = new HashMap<>();
    private static long nextIdreservationDetail = 0;

    public void addReservationDetail( ReservationDetail reservationDetail ) {
        reservationDetailMap.put( nextIdreservationDetail, reservationDetail );
    }

    public Map<Long, ReservationDetail> getReservationDetails() {
        return reservationDetailMap;
    }

    public static long getNextIdReservationDetail() {
        return nextIdreservationDetail++;
    }
}
