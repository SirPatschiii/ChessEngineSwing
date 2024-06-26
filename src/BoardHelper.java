/**
 * Utility class providing helper methods for chess board operations.
 * This class includes methods to convert board indices to ranks and files.
 *
 * <p>
 * The {@code BoardHelper} class offers static methods for converting:
 * <ul>
 *     <li>Board index to rank (1 through 8).</li>
 *     <li>Board index to chess file (a through h).</li>
 * </ul>
 * </p>
 *
 * @author SirPatschiii
 * @version 2024-06-26
 */
public class BoardHelper {
    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private BoardHelper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a board index to its corresponding rank on the chessboard.
     *
     * @param index The board index (0 through 63).
     * @return The rank on the chessboard (1 through 8).
     */
    public static short indexToRank(short index) {
        return (short) (7 - index / 8 + 1);
    }

    /**
     * Converts a board index to its corresponding chess file (column) on the chessboard.
     *
     * @param index The board index (0 through 63).
     * @return The chess file (a through h).
     */
    public static EChessFile indexToFile(short index) {
        EChessFile[] chessFiles = EChessFile.values();
        return chessFiles[index % 8];
    }
}
