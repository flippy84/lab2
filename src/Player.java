import java.awt.*;

/**
 * Abstract player class saving player name, marker color
 * and supports getting a game move from the player.
 * @author Andreas Carlsson
 */
public abstract class Player {
    public String name;
    public Marker markerID;
    public abstract Point getMove();
    public boolean init() { return true; }
    public void close() {}
}
