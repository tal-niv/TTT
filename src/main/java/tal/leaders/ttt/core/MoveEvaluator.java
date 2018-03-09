package tal.leaders.ttt.core;

import tal.leaders.ttt.api.Board;
import tal.leaders.ttt.api.MatchResultType;
import tal.leaders.ttt.api.Player;
import tal.leaders.ttt.api.Square;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MoveEvaluator {
    private Set<Square> a_horizontal = new HashSet<>(Arrays.asList(Square.A1, Square.A2, Square.A3));
    private Set<Square> b_horizontal = new HashSet<>(Arrays.asList(Square.B1, Square.B2, Square.B3));
    private Set<Square> c_horizontal = new HashSet<>(Arrays.asList(Square.C1, Square.C2, Square.C3));

    private Set<Square> one_vertical = new HashSet<>(Arrays.asList(Square.A1, Square.B1, Square.C1));
    private Set<Square> two_vertical = new HashSet<>(Arrays.asList(Square.A2, Square.B2, Square.C3));
    private Set<Square> three_vertical = new HashSet<>(Arrays.asList(Square.A3, Square.B3, Square.C3));

    private Set<Square> a1_c3_diagonal = new HashSet<>(Arrays.asList(Square.A1, Square.B2, Square.C3));
    private Set<Square> a3_c1_diagonal = new HashSet<>(Arrays.asList(Square.A3, Square.B2, Square.C1));

    private Map<Square, Set<Set<Square>>> wins = populateWins();

    // For each square, assign (in bootstrap) which lines can achieve a win
    private Map<Square, Set<Set<Square>>> populateWins() {
        Map<Square, Set<Set<Square>>> winnings = new ConcurrentHashMap<>();
        winnings.put(Square.A1, new HashSet<>(Arrays.asList(a_horizontal, one_vertical, a1_c3_diagonal)));
        winnings.put(Square.A2, new HashSet<>(Arrays.asList(a_horizontal, two_vertical)));
        winnings.put(Square.A3, new HashSet<>(Arrays.asList(a_horizontal, three_vertical, a3_c1_diagonal)));
        winnings.put(Square.B1, new HashSet<>(Arrays.asList(b_horizontal, one_vertical)));
        winnings.put(Square.B2, new HashSet<>(Arrays.asList(b_horizontal, two_vertical, a1_c3_diagonal, a3_c1_diagonal)));
        winnings.put(Square.B3, new HashSet<>(Arrays.asList(b_horizontal, three_vertical)));
        winnings.put(Square.C1, new HashSet<>(Arrays.asList(c_horizontal, one_vertical, a3_c1_diagonal)));
        winnings.put(Square.C2, new HashSet<>(Arrays.asList(c_horizontal, two_vertical)));
        winnings.put(Square.C3, new HashSet<>(Arrays.asList(c_horizontal, three_vertical, a1_c3_diagonal)));
        return winnings;
    }

    public Map<Player,MatchResultType> evaluate(Player player, Board board, Square newSquare) {
        Set<Square> playerSquares = board.get(player);
        Set<Square> vacantSquares = board.getVacant();
        Map<Player, MatchResultType> matchResultMap = new HashMap<>();
        Set<Set<Square>> moveWins = wins.get(newSquare);
        if(playerSquares.size() >= 3) {
            for (Set<Square> line : moveWins) {
                if (playerSquares.containsAll(line)){
                    matchResultMap.put(player, MatchResultType.WIN);
                    matchResultMap.put(Player.opposing(player), MatchResultType.LOSE);
                    return matchResultMap;
                }
            }
        }

        if(vacantSquares.isEmpty()) {
            matchResultMap.put(player, MatchResultType.DRAW);
            matchResultMap.put(Player.opposing(player), MatchResultType.DRAW);
            return matchResultMap;
        }

        matchResultMap.put(player, MatchResultType.ONGOING);
        matchResultMap.put(Player.opposing(player), MatchResultType.ONGOING);
        return matchResultMap;
    }
}
