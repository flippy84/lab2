import java.awt.*;

public abstract class Player {
    public String name;
    public Marker markerID;
    public abstract Point getMove();
}
