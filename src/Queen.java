import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * The {@code Queen} class provides utility methods to generate possible moves for white and black pawns in a chess game.
 * This class is not intended to be instantiated.
 * </p>
 *
 * @author SirPatschiii
 * @version 14.07.2024
 */
public class Queen {
    private Queen() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     * Generates all possible moves for the white queen based on the current game state.
     * </p>
     *
     * @param gameState The current state of the game represented as a map with piece abbreviations and their bitboard positions.
     * @return An {@code ArrayList} of possible {@code Move} objects for the white queen.
     */
    public static ArrayList<Move> generatePossibleWhiteQueenMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long whiteQueen = gameState.get(EPieceAbbreviation.WQ);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long queen;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick the queen
            queen = whiteQueen & mask;

            // Proceed if the queen is on the current observed square
            if (queen != 0) {
                // Calculate all possible moves for the observed queen
                long allPossibleMoves = getAllPossibleStraightMoves(queen, whitePieces, blackPieces);
                allPossibleMoves |= getAllPossibleDiagonalMoves(queen, whitePieces, blackPieces);

                for (int j = 0; j < 64; j++) {
                    // Mask to check square by square
                    mask = 1L << j;

                    // generate all found possible moves
                    if ((allPossibleMoves & mask) != 0) {
                        @SuppressWarnings("unchecked")
                        Move move = new Move((byte) i, (byte) j, (HashMap<EPieceAbbreviation, Long>) gameState.clone());
                        possibleMoves.addLast(move);
                    }
                }
            }
        }

        return possibleMoves;
    }

    /**
     * <p>
     * Generates all possible moves for the black queen based on the current game state.
     * </p>
     *
     * @param gameState The current state of the game represented as a map with piece abbreviations and their bitboard positions.
     * @return An {@code ArrayList} of possible {@code Move} objects for the black queen.
     */
    public static ArrayList<Move> generatePossibleBlackQueenMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        long blackQueen = gameState.get(EPieceAbbreviation.BQ);
        long whitePieces = BoardHelper.getWhitePossession(gameState);
        long blackPieces = BoardHelper.getBlackPossession(gameState);
        long mask;
        long queen;

        for (int i = 0; i < 64; i++) {
            // Mask to check square by square
            mask = 1L << i;
            // Pick the queen
            queen = blackQueen & mask;

            // Proceed if the queen is on the current observed square
            if (queen != 0) {
                // Calculate all possible moves for the observed queen
                long allPossibleMoves = getAllPossibleStraightMoves(queen, blackPieces, whitePieces);
                allPossibleMoves |= getAllPossibleDiagonalMoves(queen, blackPieces, whitePieces);

                for (int j = 0; j < 64; j++) {
                    // Mask to check square by square
                    mask = 1L << j;

                    // generate all found possible moves
                    if ((allPossibleMoves & mask) != 0) {
                        @SuppressWarnings("unchecked")
                        Move move = new Move((byte) i, (byte) j, (HashMap<EPieceAbbreviation, Long>) gameState.clone());
                        possibleMoves.addLast(move);
                    }
                }
            }
        }

        return possibleMoves;
    }

    /**
     * <p>
     * Calculates all possible straight moves for a queen piece at a given position, excluding moves blocked by other pieces.
     * </p>
     *
     * @param queen            The current position of the queen represented as a bitstream.
     * @param piecesQueenColor The positions of all pieces of the queen's color represented as a bitstream.
     * @param piecesEnemyColor The positions of all pieces of the enemy color represented as a bitstream.
     * @return A bitstream representing all possible straight moves for the queen.
     */
    private static long getAllPossibleStraightMoves(long queen, long piecesQueenColor, long piecesEnemyColor) {
        long allPossibleMoves = 0;
        long pos = queen;

        // Calculates move northbound
        while (true) {
            pos = pos << 8;
            if ((pos & piecesQueenColor) != 0 || pos == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }
        // Calculates move eastbound
        pos = queen;
        while (true) {
            pos = pos >> 1;
            if ((pos & piecesQueenColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_A) == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }
        // Calculates move southbound
        pos = queen;
        while (true) {
            pos = pos >> 8;
            if ((pos & piecesQueenColor) != 0 || pos == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }
        // Calculates move westbound
        pos = queen;
        while (true) {
            pos = pos << 1;
            if ((pos & piecesQueenColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_H) == 0) {
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

    /**
     * <p>
     * Calculates all possible diagonal moves for a queen piece at a given position, excluding moves blocked by other pieces.
     * </p>
     *
     * @param queen            The current position of the queen represented as a bitstream.
     * @param piecesQueenColor The positions of all pieces of the queen's color represented as a bitstream.
     * @param piecesEnemyColor The positions of all pieces of the enemy color represented as a bitstream.
     * @return A bitstream representing all possible diagonal moves for the queen.
     */
    private static long getAllPossibleDiagonalMoves(long queen, long piecesQueenColor, long piecesEnemyColor) {
        long allPossibleMoves = 0;
        long pos = queen;

        // Calculates move north-east-bound
        while (true) {
            pos = pos << 7;
            if ((pos & piecesQueenColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_A) == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }
        // Calculates move south-east-bound
        pos = queen;
        while (true) {
            pos = pos >> 9;
            if ((pos & piecesQueenColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_A) == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }
        // Calculates move south-west-bound
        pos = queen;
        while (true) {
            pos = pos >> 7;
            if ((pos & piecesQueenColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_H) == 0) {
                break;
            }
            if ((pos & piecesEnemyColor) != 0) {
                allPossibleMoves = allPossibleMoves | pos;
                break;
            }
            allPossibleMoves = allPossibleMoves | pos;
        }
        // Calculates move north-west-bound
        pos = queen;
        while (true) {
            pos = pos << 9;
            if ((pos & piecesQueenColor) != 0 || pos == 0 || (pos & ~ChessEngine.FILE_H) == 0) {
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
