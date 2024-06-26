import java.util.HashMap;

/**
 * The {@code Move} class represents a chess move, encapsulating the source square, destination square,
 * and the game state before the move was played.
 * This class also provides methods to retrieve the game state and a string representation of the move.
 *
 * @author SirPatschiii
 * @version 2024-06-26
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
     * Constructs a {@code Move} object with specified source square, destination square, and game state.
     *
     * @param squareFrom the index of the source square (0-63) before the move.
     * @param squareTo the index of the destination square (0-63) after the move.
     * @param gameState the game state before the move, represented as a map of piece abbreviations to bitboards.
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
     * Retrieves the game state before the move.
     *
     * @return a {@code HashMap} representing the game state with piece abbreviations as keys and bitboards as values.
     */
    public HashMap<EPieceAbbreviation, Long> getGameState() {
        // Returns the game state which was bevor the actual move as played
        return gameState;
    }

    /**
     * Returns a string representation of the move in algebraic notation.
     *
     * @return a {@code String} representation of the move in the format "source square to destination square".
     */
    @Override
    public String toString() {
        // Returns the played move
        return fileFrom + rankFrom + fileTo + rankTo;
    }
}
