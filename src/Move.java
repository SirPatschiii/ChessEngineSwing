import java.util.HashMap;
import java.util.Objects;

/**
 * <p>
 * The {@code Move} class represents a chess move, encapsulating the starting and ending squares of the move,
 * along with the game state before the move.
 * </p>
 *
 * @author SirPatschiii
 * @version 10.07.2024
 */
public class Move {
    private final short squareFrom;
    private final short squareTo;
    private final HashMap<EPieceAbbreviation, Long> gameState;

    private final short rankFrom;
    private final String fileFrom;
    private final short rankTo;
    private final String fileTo;

    /**
     * <p>
     * Constructs a {@code Move} object with the specified starting and ending squares, as well as the game state before the move.
     * </p>
     *
     * @param squareFrom The starting square of the move, represented as a byte.
     * @param squareTo   The ending square of the move, represented as a byte.
     * @param gameState  The game state before the move, represented as a {@code HashMap} with piece abbreviations as keys and bitboards as values.
     */
    public Move(byte squareFrom, byte squareTo, HashMap<EPieceAbbreviation, Long> gameState) {
        this.squareFrom = squareFrom;
        this.squareTo = squareTo;
        this.gameState = gameState;

        rankFrom = BoardHelper.indexToRank(squareFrom);
        fileFrom = BoardHelper.indexToFile(squareFrom).toString();
        rankTo = BoardHelper.indexToRank(squareTo);
        fileTo = BoardHelper.indexToFile(squareTo).toString();
    }

    /**
     * <p>
     * Retrieves the game state before the move.
     * </p>
     *
     * @return a {@code HashMap} representing the game state with piece abbreviations as keys and bitboards as values.
     */
    public HashMap<EPieceAbbreviation, Long> getGameState() {
        // Returns the game state which was bevor the actual move as played
        return gameState;
    }

    /**
     * <p>
     * Returns a string representation of the move in algebraic notation.
     * </p>
     *
     * @return a {@code String} representation of the move in the format "source square to destination square".
     */
    @Override
    public String toString() {
        // Returns the played move
        return fileFrom + rankFrom + fileTo + rankTo;
    }

    /**
     * <p>
     * Compares this {@code Move} object to the specified object for equality. The result is {@code true} if and only if the
     * argument is not {@code null} and is a {@code Move} object that represents the same move, with the same starting
     * and ending squares and the same game state.
     * </p>
     *
     * @param o The object to compare this {@code Move} against.
     * @return {@code true} if the given object represents a {@code Move} equivalent to this move, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return squareFrom == move.squareFrom && squareTo == move.squareTo && Objects.equals(gameState, move.gameState);
    }

    /**
     * <p>
     * Calculates a hash code value for this {@code Move}. The hash code is computed based on the starting and ending squares,
     * as well as the game state.
     * </p>
     *
     * @return A hash code value for this {@code Move}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(squareFrom, squareTo, gameState);
    }
}
