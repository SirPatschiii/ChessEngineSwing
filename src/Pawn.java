import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * The {@code Pawn} class provides utility methods to generate possible moves for white and black pawns in a chess game.
 * This class is not intended to be instantiated.
 * </p>
 *
 * @author SirPatschiii
 * @version 09.07.2024
 */
public class Pawn {
    private Pawn() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     * Generates all possible moves for white pawns based on the current game state.
     * </p>
     *
     * @param gameState the current state of the game represented as a hashmap with piece abbreviations as keys and bitboards as values
     * @return an {@code ArrayList} of {@link Move} objects representing all possible moves for white pawns
     */
    public static ArrayList<Move> generatePossibleWhitePawnMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long whitePawns = gameState.get(EPieceAbbreviation.WP);
        long allPieces = BoardHelper.getCompletePossession(gameState);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long pawn;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick pawn by pawn
            pawn = whitePawns & mask;

            // Proceed if a pawn is on the current observed square
            if (pawn != 0) {
                // Calculate all possible moves for the observed pawn
                long singleStep = (pawn << 8) & ~allPieces;
                long doubleStep = (((pawn << 8) & ~allPieces) << 8) & ~allPieces & ChessEngine.RANK_4;
                long attackLeft = ((pawn << 9) & ~ChessEngine.FILE_H) & ~whitePieces & blackPieces;
                long attackRight = ((pawn << 7) & ~ChessEngine.FILE_A) & ~whitePieces & blackPieces;

                long allPossibleMoves = singleStep | doubleStep | attackLeft | attackRight;

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
     * Generates all possible moves for black pawns based on the current game state.
     * </p>
     *
     * @param gameState the current state of the game represented as a hashmap with piece abbreviations as keys and bitboards as values
     * @return an {@code ArrayList} of {@link Move} objects representing all possible moves for black pawns
     */
    public static ArrayList<Move> generatePossibleBlackPawnMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long blackPawns = gameState.get(EPieceAbbreviation.BP);
        long allPieces = BoardHelper.getCompletePossession(gameState);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long pawn;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick pawn by pawn
            pawn = blackPawns & mask;

            // Proceed if a pawn is on the current observed square
            if (pawn != 0) {
                // Calculate all possible moves for the observed pawn
                long singleStep = (pawn >> 8) & ~allPieces;
                long doubleStep = (((pawn >> 8) & ~allPieces) >> 8) & ~allPieces & ChessEngine.RANK_5;
                long attackLeft = ((pawn >> 7) & ~ChessEngine.FILE_H) & ~blackPieces & whitePieces;
                long attackRight = ((pawn >> 9) & ~ChessEngine.FILE_A) & ~blackPieces & whitePieces;

                long allPossibleMoves = singleStep | doubleStep | attackLeft | attackRight;

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
}
