import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * The {@code Knight} class provides utility methods to generate possible moves for white and black knights in a chess game.
 * This class is not intended to be instantiated.
 * </p>
 *
 * @author SirPatschiii
 * @version 08.07.2024
 */
public class Knight {
    private Knight() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     * Generates all possible moves for white knights based on the current game state.
     * </p>
     *
     * @param gameState the current state of the game represented as a hashmap with piece abbreviations as keys and bitboards as values
     * @return an {@code ArrayList} of {@link Move} objects representing all possible moves for white knights
     */
    public static ArrayList<Move> generatePossibleWhiteKnightMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long whiteKnights = gameState.get(EPieceAbbreviation.WN);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long mask;
        long knight;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick knight by knight
            knight = whiteKnights & mask;

            // Proceed if a knight is on the current observed square
            if (knight != 0) {
                // Calculate all possible moves for the observed knight
                long allPossibleMoves = getAllPossibleMoves(knight, whitePieces);

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
     * Generates all possible moves for black knights based on the current game state.
     * </p>
     *
     * @param gameState the current state of the game represented as a hashmap with piece abbreviations as keys and bitboards as values
     * @return an {@code ArrayList} of {@link Move} objects representing all possible moves for black knights
     */
    public static ArrayList<Move> generatePossibleBlackKnightMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long blackKnights = gameState.get(EPieceAbbreviation.BN);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long knight;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick knight by knight
            knight = blackKnights & mask;

            // Proceed if a knight is on the current observed square
            if (knight != 0) {
                // Calculate all possible moves for the observed knight
                long allPossibleMoves = getAllPossibleMoves(knight, blackPieces);

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
     * Calculates all possible moves for a given knight position and the pieces on the board.
     * </p>
     *
     * @param knight the bitboard representing the knight's position
     * @param pieces the bitboard representing the pieces on the board
     * @return a bitboard representing all possible moves for the knight
     */
    private static long getAllPossibleMoves(long knight, long pieces) {
        long jumpNET = (knight << 15) & ~pieces & ~ChessEngine.FILE_H;
        long jumpNEB = (knight << 6) & ~pieces & ~ChessEngine.FILE_H & ~ChessEngine.FILE_G;
        long jumpSET = (knight >> 10) & ~pieces & ~ChessEngine.FILE_H & ~ChessEngine.FILE_G;
        long jumpSEB = (knight >> 17) & ~pieces & ~ChessEngine.FILE_H;
        long jumpSWB = (knight >> 15) & ~pieces & ~ChessEngine.FILE_A;
        long jumpSWT = (knight >> 6) & ~pieces & ~ChessEngine.FILE_A & ~ChessEngine.FILE_B;
        long jumpNWB = (knight << 10) & ~pieces & ~ChessEngine.FILE_A & ~ChessEngine.FILE_B;
        long jumpNWT = (knight << 17) & ~pieces & ~ChessEngine.FILE_A;

        return jumpNET | jumpNEB | jumpSET | jumpSEB | jumpSWB | jumpSWT | jumpNWB | jumpNWT;
    }
}
