import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * The {@code King} class provides utility methods to generate possible moves for white and black pawns in a chess game.
 * This class is not intended to be instantiated.
 * </p>
 *
 * @author SirPatschiii
 * @version 09.07.2024
 */
public class King {
    private King() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     *  Generates all possible moves for the white king based on the current game state.
     * </p>
     *
     * @param gameState The current state of the game represented as a HashMap of piece abbreviations and their positions.
     * @return An ArrayList of possible moves for the white king.
     */
    public static ArrayList<Move> generatePossibleWhiteKingMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long whiteKing = gameState.get(EPieceAbbreviation.WK);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long mask;
        long king;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick the king
            king = whiteKing & mask;

            // Proceed if the king is on the current observed square
            if (king != 0) {
                // Calculate all possible moves for the observed king
                long allPossibleMoves = getAllPossibleMoves(king, whitePieces);

                for (int j = 0; j < 64; j++) {
                    // Mask to check square by square
                    mask = 1L << j;

                    // generate all found possible moves
                    if ((allPossibleMoves & mask) != 0) {
                        @SuppressWarnings("unchecked")
                        Move move = new Move((byte) (63 - i), (byte) (63 - j), (HashMap<EPieceAbbreviation, Long>) gameState.clone());
                        possibleMoves.addLast(move);
                    }
                }
            }
        }

        return possibleMoves;
    }

    /**
     * <p>
     *  Generates all possible moves for the black king based on the current game state.
     * </p>
     *
     * @param gameState The current state of the game represented as a HashMap of piece abbreviations and their positions.
     * @return An ArrayList of possible moves for the black king.
     */
    public static ArrayList<Move> generatePossibleBlackKingMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long blackKing = gameState.get(EPieceAbbreviation.BK);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long king;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick the king
            king = blackKing & mask;

            // Proceed if the king is on the current observed square
            if (king != 0) {
                // Calculate all possible moves for the observed king
                long allPossibleMoves = getAllPossibleMoves(king, blackPieces);

                for (int j = 0; j < 64; j++) {
                    // Mask to check square by square
                    mask = 1L << j;

                    // generate all found possible moves
                    if ((allPossibleMoves & mask) != 0) {
                        @SuppressWarnings("unchecked")
                        Move move = new Move((byte) (63 - i), (byte) (63 - j), (HashMap<EPieceAbbreviation, Long>) gameState.clone());
                        possibleMoves.addLast(move);
                    }
                }
            }
        }

        return possibleMoves;
    }

    /**
     * <p>
     *  Calculates all possible moves for a king piece at a given position, excluding moves blocked by other pieces.
     * </p>
     *
     * @param king The current position of the king represented as a bitstream.
     * @param pieces The positions of all pieces on the board represented as a bitstream.
     * @return A bitstream representing all possible moves for the king.
     */
    private static long getAllPossibleMoves(long king, long pieces) {
        long stepN = (king << 8) & ~pieces;
        long stepNE = (king << 7) & ~pieces & ~ChessEngine.FILE_A;
        long stepE = (king >> 1) & ~pieces & ~ChessEngine.FILE_A;
        long stepSE = (king >> 9) & ~pieces & ~ChessEngine.FILE_A;
        long stepS = (king >> 8) & ~pieces;
        long stepSW = (king >> 7) & ~pieces & ~ChessEngine.FILE_H;
        long stepW = (king << 1) & ~pieces & ~ChessEngine.FILE_H;
        long stepNW = (king << 9) & ~pieces & ~ChessEngine.FILE_H;

        return stepN | stepNE | stepE | stepSE | stepS | stepSW | stepW | stepNW;
    }
}
