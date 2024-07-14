import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * The {@code Move} class represents a chess move, encapsulating the starting and ending squares of the move,
 * along with the game state before the move.
 * </p>
 *
 * @author SirPatschiii
 * @version 14.07.2024
 */
public class Move implements Cloneable {
    private final short squareFrom;
    private final short squareTo;
    private HashMap<EPieceAbbreviation, Long> gameState;
    private EPieceAbbreviation piece;

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

        calculateMovedPiece();
    }

    /**
     * <p>
     * Calculates the piece that has been moved from a specific square in the current game state.
     * It iterates through the game state and checks which piece is present at the source square defined by {@code squareFrom}.
     * </p>
     *
     * @throws IllegalStateException if no piece is found at the specified square.
     */
    private void calculateMovedPiece() {
        for (Map.Entry<EPieceAbbreviation, Long> entry : gameState.entrySet()) {
            long bitboard = entry.getValue();
            if (BitHelper.isBitSet(bitboard, squareFrom)) {
                piece = entry.getKey();
            }
        }
    }

    /**
     * <p>
     * Retrieves the starting square of this move.
     * </p>
     *
     * @return the short value representing the starting square.
     */
    public short getSquareFrom() {
        return squareFrom;
    }

    /**
     * <p>
     * Retrieves the destination square of this move.
     * </p>
     *
     * @return the short value representing the destination square.
     */
    public short getSquareTo() {
        return squareTo;
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

    public EPieceAbbreviation getPiece() {
        return piece;
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

    /**
     * <p>
     * Creates a clone of the current {@code Move} object.
     * </p>
     * <p>
     * This method overrides the {@code clone} method to provide a deep copy of the {@code Move} object,
     * ensuring that the internal game state is also cloned to prevent shared references. The cloning process
     * involves invoking the superclass's {@code clone} method and then cloning the {@code gameState} map.
     *
     * @return a new {@code Move} object that is a copy of this instance.
     * @throws AssertionError if the cloning process fails, which should not happen as the class implements {@link Cloneable}.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Move clone() {
        Move cloned = null;
        try {
            cloned = (Move) super.clone();
            cloned.gameState = (HashMap<EPieceAbbreviation, Long>) gameState.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
