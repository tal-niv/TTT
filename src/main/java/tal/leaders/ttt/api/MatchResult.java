package tal.leaders.ttt.api;

public class MatchResult {
    private Player player;
    private MatchResultType result;
    private Match match;

    public MatchResult() {}

    public MatchResult(Player player, MatchResultType result, Match match) {
        this.player = player;
        this.result = result;
        this.match = match;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MatchResultType getResult() {
        return result;
    }

    public void setResult(MatchResultType result) {
        this.result = result;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "MatchResult{" +
                "player=" + player +
                ", result=" + result +
                ", match=" + match +
                '}';
    }
}
