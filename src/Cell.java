/**
 * Created by hanshenrik on 23/10/14.
 */
public class Cell {
    private Disc disc;

    public Cell() {
        this.disc = null;
    }

    public Disc getDisc() {
        return this.disc;
    }

    public void setDisc(Disc disc) {
        this.disc = disc;
    }

    public boolean hasDisc() {
        return this.disc != null;
    }
}
