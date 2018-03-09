package tal.leaders.ttt.resources;

import com.codahale.metrics.annotation.Timed;
import tal.leaders.ttt.api.*;
import tal.leaders.ttt.core.MatchManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/match")
@Produces(MediaType.APPLICATION_JSON)
public class MatchResource {
    private MatchManager service;



    public MatchResource() {
        init();
    }

    private void init() {
        service = new MatchManager();
    }

    private Match start(Player player, Long session) {
        return service.start(player,session);
    }

    private Match join(Long matchId, Long session) {
       return service.join(matchId,session);
    }

    private void notifyOpponentResult(MatchResult result) {
        //TODO notify opponent async call
    }

    private MatchResult makeMove(Long matchId, Long sessionId, Square square) {
        Match match = service.findRunningMatch(matchId);
        Player player = match.findPlayer(sessionId);
        Player opponent = Player.opposing(player);
        Map<Player, MatchResultType> moveResults = service.move(matchId, player, square);
        notifyOpponentResult(new MatchResult(opponent, moveResults.get(opponent), match));
        return new MatchResult(player, moveResults.get(player), match);
    }

    private MatchResult makePoll(Long matchId, Long sessionId) {
        return service.poll(matchId,sessionId);
    }


    @POST
    @Path("start")
    @Timed
    public Match startMatch(@FormParam("player") String player, @FormParam("sessionId") Long sessionId) {
        System.out.print("starting match, pl:" + player + ", sessionId:" + sessionId);
        Match match = start(Player.valueOf(player.toUpperCase()),sessionId);
        System.out.println("started match: " + match);
        return match;
    }

    @POST
    @Path("join")
    @Timed
    public Match joinMatch(@FormParam("matchId") Long matchId, @FormParam("sessionId") Long sessionId) {
        System.out.print("joining match, matchId:" + matchId + " sessionId" + sessionId);
        Match match = join(matchId, sessionId);
        System.out.println("joined match: " + match);
        return match;
    }

    @POST
    @Path("move")
    @Timed
    public MatchResult move(@FormParam("matchId") Long matchId,@FormParam("sessionId") Long sessionId, @FormParam("square") String square) {
        System.out.println("making move, matchId:" + matchId + ", sessionId:" + sessionId +", square:" + square);
        MatchResult result = makeMove(matchId, sessionId, Square.valueOf(square.toUpperCase()));
        System.out.println("made move, result:" + result);
        return result;
    }

    @POST
    @Path("poll")
    @Timed
    public MatchResult poll(@FormParam("matchId") Long matchId,@FormParam("sessionId") Long sessionId) {
        MatchResult result = makePoll(matchId, sessionId);
        return result;
    }

    @GET
    public String hello() {
        return "Hello";
    }
}
