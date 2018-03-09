package tal.leaders.ttt.api;

public class Match {
    private long id;
    private Board board;
    private MatchStatus status;
    private Player winner;
    private Long playerXsession;
    private Long playerOSession;

    public Match() {
    }

    public Match(Long id, Player player, Long session) {
        this.id = id;
        board = new Board();
        switch (player) {
            case X: {
                playerXsession = session;
                break;
            } case O: {
                playerOSession = session;
                break;
            }
        }
        status = MatchStatus.INITIATED;
    }

    public Player findPlayer(Long sessionId) {
        Player player;
        if(this.getPlayerXsession().equals(sessionId)) {
            player = Player.X;
        } else {
            player = Player.O;
        }

        return player;
    }

    public long getId() {
        return id;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Long getPlayerXsession() {
        return playerXsession;
    }

    public void setPlayerXsession(Long playerXsession) {
        this.playerXsession = playerXsession;
    }

    public Long getPlayerOSession() {
        return playerOSession;
    }

    public void setPlayerOSession(Long playerOSession) {
        this.playerOSession = playerOSession;
    }

    public Board getBoard() {
        return board;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", board=" + board +
                ", status=" + status +
                ", winner=" + winner +
                ", playerXsession=" + playerXsession +
                ", playerOSession=" + playerOSession +
                '}';
    }
}
