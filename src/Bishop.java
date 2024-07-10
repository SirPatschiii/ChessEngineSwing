import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * The {@code Bishop} class provides utility methods to generate possible moves for white and black pawns in a chess game.
 * This class is not intended to be instantiated.
 * </p>
 *
 * @author SirPatschiii
 * @version 10.07.2024
 */
public class Bishop {
    private Bishop() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     * Generates all possible moves for the white bishops based on the current game state.
     * </p>
     *
     * @param gameState The current state of the game represented as a HashMap of piece abbreviations and their positions.
     * @return An ArrayList of possible moves for the white bishops.
     */
    public static ArrayList<Move> generatePossibleWhiteBishopMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long whiteBishops = gameState.get(EPieceAbbreviation.WB);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long bishop;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick bishop by bishop
            bishop = whiteBishops & mask;

            // Proceed if a bishop is on the current observed square
            if (bishop != 0) {
                // Calculate all possible moves for the observed bishop
                long allPossibleMoves = getAllPossibleMoves(bishop, whitePieces, blackPieces);

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
     * Generates all possible moves for the black bishops based on the current game state.
     * </p>
     *
     * @param gameState The current state of the game represented as a HashMap of piece abbreviations and their positions.
     * @return An ArrayList of possible moves for the black bishops.
     */
    public static ArrayList<Move> generatePossibleBlackBishopMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long blackBishops = gameState.get(EPieceAbbreviation.BB);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long bishop;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick bishop by bishop
            bishop = blackBishops & mask;

            // Proceed if a bishop is on the current observed square
            if (bishop != 0) {
                // Calculate all possible moves for the observed bishop
                long allPossibleMoves = getAllPossibleMoves(bishop, blackPieces, whitePieces);

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
     * Calculates all possible moves for a bishop piece at a given position, excluding moves blocked by other pieces.
     * </p>
     *
     * @param bishop            The current position of the bishop represented as a bitstream.
     * @param piecesBishopColor The positions of all pieces of the bishop's color represented as a bitstream.
     * @param piecesEnemyColor  The positions of all pieces of the enemy color represented as a bitstream.
     * @return A bitstream representing all possible moves for the bishop.
     */
    private static long getAllPossibleMoves(long bishop, long piecesBishopColor, long piecesEnemyColor) {
        long allPossibleMoves = 0;
        long pos = bishop;

        // Calculates move north-east-bound
        while (true) {
            pos = pos << 7;
            if ((pos & piecesBishopColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_A) == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }

        // Calculates move south-east-bound
        pos = bishop;
        while (true) {
            pos = pos >> 9;
            if ((pos & piecesBishopColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_A) == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }

        // Calculates move south-west-bound
        pos = bishop;
        while (true) {
            pos = pos >> 7;
            if ((pos & piecesBishopColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_H) == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }

        // Calculates move north-west-bound
        pos = bishop;
        while (true) {
            pos = pos << 9;
            if ((pos & piecesBishopColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_H) == 0) {
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
