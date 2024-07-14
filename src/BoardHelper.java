import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  Utility class for board-related operations in a chess game. This class provides methods to
 *  convert board indices to ranks and files, and to calculate the possession bitboards for white, black, and all pieces.
 *  It is implemented using the singleton pattern to prevent instantiation.
 * </p>
 *
 * @author SirPatschiii
 * @version 14.07.2024
 */
public class BoardHelper {
    private BoardHelper() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     *  Converts a board index to a rank.
     * </p>
     *
     * @param index the board index (0-63)
     * @return the rank (1-8) corresponding to the given index
     */
    public static short indexToRank(short index) {
        return (short) ((index / 8) + 1);
    }

    /**
     * <p>
     *  Converts a board index to a file.
     * </p>
     *
     * @param index the board index (0-63)
     * @return the file (A-H) corresponding to the given index
     */
    public static EChessFile indexToFile(short index) {
        EChessFile[] chessFiles = EChessFile.values();
        return chessFiles[7 - index % 8];
    }

    /**
     * <p>
     *  Calculates the bitboard representing all squares occupied by white pieces.
     * </p>
     *
     * @param gameState the current game state as a hashmap of piece abbreviations and their bitboards
     * @return a long value representing the bitboard of all squares occupied by white pieces
     */
    public static Long getWhitePossession(HashMap<EPieceAbbreviation, Long> gameState) {
        long possession = 0L;

        for (Map.Entry<EPieceAbbreviation, Long> entry : gameState.entrySet()) {
            if (entry.getKey().toString().contains("W")) {
                possession = BitHelper.bitwiseOR(possession, entry.getValue());
            }
        }

        return possession;
    }

    /**
     * <p>
     *  Calculates the bitboard representing all squares occupied by black pieces.
     * </p>
     *
     * @param gameState the current game state as a hashmap of piece abbreviations and their bitboards
     * @return a long value representing the bitboard of all squares occupied by black pieces
     */
    public static Long getBlackPossession(HashMap<EPieceAbbreviation, Long> gameState) {
        long possession = 0L;

        for (Map.Entry<EPieceAbbreviation, Long> entry : gameState.entrySet()) {
            if (!entry.getKey().toString().contains("W")) {
                possession = BitHelper.bitwiseOR(possession, entry.getValue());
            }
        }

        return possession;
    }

    /**
     * <p>
     *  Calculates the bitboard representing all squares occupied by any pieces.
     * </p>
     *
     * @param gameState the current game state as a hashmap of piece abbreviations and their bitboards
     * @return a long value representing the bitboard of all squares occupied by any pieces
     */
    public static Long getCompletePossession(HashMap<EPieceAbbreviation, Long> gameState) {
        long possession = 0L;

        for (Map.Entry<EPieceAbbreviation, Long> entry : gameState.entrySet()) {
            possession = BitHelper.bitwiseOR(possession, entry.getValue());
        }

        return possession;
    }
}
