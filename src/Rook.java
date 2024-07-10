import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * The {@code Rook} class provides utility methods to generate possible moves for white and black pawns in a chess game.
 * This class is not intended to be instantiated.
 * </p>
 *
 * @author SirPatschiii
 * @version 10.07.2024
 */
public class Rook {
    private Rook() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     * Generates all possible moves for the white rooks based on the current game state.
     * </p>
     *
     * @param gameState The current state of the game represented as a HashMap of piece abbreviations and their positions.
     * @return An ArrayList of possible moves for the white rooks.
     */
    public static ArrayList<Move> generatePossibleWhiteRookMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long whiteRooks = gameState.get(EPieceAbbreviation.WR);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long rook;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick rook by rook
            rook = whiteRooks & mask;

            // Proceed if a rook is on the current observed square
            if (rook != 0) {
                // Calculate all possible moves for the observed rook
                long allPossibleMoves = getAllPossibleMoves(rook, whitePieces, blackPieces);

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
     * Generates all possible moves for the black rooks based on the current game state.
     * </p>
     *
     * @param gameState The current state of the game represented as a HashMap of piece abbreviations and their positions.
     * @return An ArrayList of possible moves for the black rooks.
     */
    public static ArrayList<Move> generatePossibleBlackRookMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long blackRooks = gameState.get(EPieceAbbreviation.BR);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long rook;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick rook by rook
            rook = blackRooks & mask;

            // Proceed if a rook is on the current observed square
            if (rook != 0) {
                // Calculate all possible moves for the observed rook
                long allPossibleMoves = getAllPossibleMoves(rook, blackPieces, whitePieces);

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
     * Calculates all possible moves for a rook piece at a given position, excluding moves blocked by other pieces.
     * </p>
     *
     * @param rook             The current position of the rook represented as a bitstream.
     * @param piecesRookColor  The positions of all pieces of the rook's color represented as a bitstream.
     * @param piecesEnemyColor The positions of all pieces of the enemy color represented as a bitstream.
     * @return A bitstream representing all possible moves for the rook.
     */
    private static long getAllPossibleMoves(long rook, long piecesRookColor, long piecesEnemyColor) {
        long allPossibleMoves = 0;
        long pos = rook;

        // Calculates move northbound
        while (true) {
            pos = pos << 8;
            if ((pos & piecesRookColor) != 0 || pos == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }
        // Calculates move eastbound
        pos = rook;
        while (true) {
            pos = pos >> 1;
            if ((pos & piecesRookColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_A) == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }
        // Calculates move southbound
        pos = rook;
        while (true) {
            pos = pos >> 8;
            if ((pos & piecesRookColor) != 0 || pos == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }
        // Calculates move westbound
        pos = rook;
        while (true) {
            pos = pos << 1;
            if ((pos & piecesRookColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_H) == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }

        return allPossibleMoves;
    }
}
