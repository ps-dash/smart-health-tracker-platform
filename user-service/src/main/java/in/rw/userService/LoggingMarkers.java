package in.rw.userService;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LoggingMarkers {
    public static final Marker SECURITY = MarkerFactory.getMarker("SECURITY");
    public static final Marker ERROR = MarkerFactory.getMarker("ERROR");
    public static final Marker AUDIT = MarkerFactory.getMarker("AUDIT");
}
