import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Utility class for board-related operations in a chess game. This class provides methods to
 * convert board indices to ranks and files, and to calculate the possession bitboards for white, black, and all pieces.
 * It is implemented using the singleton pattern to prevent instantiation.
 * </p>
 *
 * @author SirPatschiii
 * @version 15.07.2024
 */
public class BoardHelper {
    private BoardHelper() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     * Converts a board index to a rank.
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
     * Converts a board index to a file.
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
     * Calculates the bitboard representing all squares occupied by white pieces.
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
     * Calculates the bitboard representing all squares occupied by black pieces.
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
     * Calculates the bitboard representing all squares occupied by any pieces.
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

    /**
     * <p>
     *  Calculates and returns a bitboard representing all the squares attacked by white pieces.
     * </p>
     *
     * @param gameState A {@code HashMap} with {@link EPieceAbbreviation} keys and {@code Long} values representing the current game state.
     * @return A {@code Long} representing the bitboard of all squares attacked by white pieces.
     */
    public static Long getWhiteAttackRays(HashMap<EPieceAbbreviation, Long> gameState) {
        long attacks = 0L;
        ArrayList<Move> possibleMoves = new ArrayList<>();

        // Get all possible Moves
        for (Move move : Pawn.generatePossibleWhitePawnMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : Knight.generatePossibleWhiteKnightMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : Bishop.generatePossibleWhiteBishopMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : Rook.generatePossibleWhiteRookMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : Queen.generatePossibleWhiteQueenMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : King.generatePossibleWhiteKingMoves(gameState)) {
            possibleMoves.addLast(move);
        }

        // Calculate the attack rays
        for (Move move : possibleMoves) {
            attacks = BitHelper.setBit(attacks, move.getSquareTo());
        }

        return attacks;
    }

    /**
     * <p>
     *  Calculates and returns a bitboard representing all the squares attacked by black pieces.
     * </p>
     *
     * @param gameState A {@code HashMap} with {@link EPieceAbbreviation} keys and {@code Long} values representing the current game state.
     * @return A {@code Long} representing the bitboard of all squares attacked by black pieces.
     */
    public static Long getBlackAttackRays(HashMap<EPieceAbbreviation, Long> gameState) {
        long attacks = 0L;
        ArrayList<Move> possibleMoves = new ArrayList<>();

        // Get all possible Moves
        for (Move move : Pawn.generatePossibleBlackPawnMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : Knight.generatePossibleBlackKnightMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : Bishop.generatePossibleBlackBishopMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : Rook.generatePossibleBlackRookMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : Queen.generatePossibleBlackQueenMoves(gameState)) {
            possibleMoves.addLast(move);
        }
        for (Move move : King.generatePossibleBlackKingMoves(gameState)) {
            possibleMoves.addLast(move);
        }

        // Calculate the attack rays
        for (Move move : possibleMoves) {
            attacks = BitHelper.setBit(attacks, move.getSquareTo());
        }

        return attacks;
    }
}
