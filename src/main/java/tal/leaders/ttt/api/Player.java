package tal.leaders.ttt.api;

public enum Player  {
    X,O;

    public static Player opposing(Player player) {
        if(player == null) {
            return null;
        }
        if(X == player) {
            return O;
        } else {
            return X;
        }
    }
}
