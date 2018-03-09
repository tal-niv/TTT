package tal.leaders.ttt.core;

import tal.leaders.ttt.api.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatchManager {
    private long id;
    private Map<Long,Match> initiatedMatches;
    private Map<Long,Match> runningMatches;
    private MoveEvaluator evaluator;

    public MatchManager() {
        init();
    }

    private void init() {
        id = 0l;
        initiatedMatches = new ConcurrentHashMap<>();
        runningMatches = new ConcurrentHashMap<>();
        evaluator = new MoveEvaluator();
    }

    public Match start(Player player, Long session) {
        Long matchId = id++;
        initiatedMatches.put(matchId, new Match(matchId, player,session));
        return initiatedMatches.get(matchId);
    }

    public Match join(Long matchId, Long session) {
        Match match = initiatedMatches.get(matchId);
        if(match != null) {
            if(match.getPlayerOSession() == null) {
                match.setPlayerOSession(session);
            } else {
                match.setPlayerXsession(session);
            }
            match.setStatus(MatchStatus.RUNNING);
            runningMatches.put(matchId,match);
            initiatedMatches.remove(matchId);
        }
        return match;
    }

    private void setWinner(Match match, Player player, MatchResultType res) {
        match.setStatus(MatchStatus.TERMINATED);
        match.getBoard().setNextMove(null);
        if(MatchResultType.WIN == res) {
            match.setWinner(player);
        } else if(MatchResultType.LOSE == res) {
            match.setWinner(Player.opposing(player));
        }
    }

    public Map<Player, MatchResultType> move(Long matchId, Player player, Square newSquare) {
        Match match = runningMatches.get(matchId);
        match.getBoard().move(player, newSquare);
        Map<Player,MatchResultType> moveResult = evaluator.evaluate(player, match.getBoard(), newSquare);
        MatchResultType res = moveResult.get(player);
        if(MatchResultType.ONGOING != res) {
            setWinner(match,player,res);
        }
        return moveResult;
    }

    public Match findRunningMatch(Long matchId) {
        return runningMatches.get(matchId);
    }

    public MatchResult poll(Long matchId, Long sessionId) {
        Match match = findRunningMatch(matchId);
        Player player = match.findPlayer(sessionId);
        MatchResult result;
        if(MatchStatus.RUNNING == match.getStatus()) {
            result = new MatchResult(player, MatchResultType.ONGOING, match);
        } else {
            if(match.getWinner() == null) {
                result = new MatchResult(player, MatchResultType.DRAW, match);
            } else if(match.getWinner() == player) {
                result = new MatchResult(player, MatchResultType.WIN, match);
            } else {
                result =  new MatchResult(player, MatchResultType.LOSE, match);
            }
        }
        return result;
    }
}
