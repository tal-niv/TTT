package tal.leaders.ttt.api;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board {

    private Set<Square> vacant;
    private Set<Square> x;
    private Set<Square> o;
    private int moveId;
    private Square latestMove;
    private Player nextMove;

    public Board() {
        this.vacant = new HashSet<>(Arrays.asList(Square.values()));
        this.x = new HashSet<>();
        this.o = new HashSet<>();
        moveId = 0;
        nextMove = Player.X;
    }

    public void move(Player player, Square square) {
        Set<Square> playerSquares = this.get(player);
        Set<Square> vacantSquares = this.getVacant();
        playerSquares.add(square);
        vacantSquares.remove(square);
        moveId++;
        latestMove = square;
        nextMove = Player.opposing(player);
    }

    public Set<Square> getVacant() {
        return vacant;
    }

    public Set<Square> getX() {
        return x;
    }

    public Set<Square> getO() {
        return o;
    }

    public int getMoveId() {
        return moveId;
    }

    public Square getLatestMove() {
        return latestMove;
    }

    public Player getNextMove() {
        return nextMove;
    }

    public void setNextMove(Player nextMove) {
        this.nextMove = nextMove;
    }

    public Set<Square> get(Player player) {
        switch(player) {
            case X: {
                return getX();
            }
            case O: {
                return getO();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Board{" +
                "vacant=" + vacant +
                ", x=" + x +
                ", o=" + o +
                ", moveId=" + moveId +
                ", latestMove=" + latestMove +
                ", nextMove=" + nextMove +
                '}';
    }
}
