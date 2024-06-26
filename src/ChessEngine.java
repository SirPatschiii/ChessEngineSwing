import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * The {@code ChessEngine} class represents the core logic of a chess engine.
 *
 * <p>
 * It manages the game state, validates moves, handles move history, and interacts with the graphical user interface (GUI).
 * </p>
 *
 * <p>
 * The game state is stored using a {@link HashMap} where each {@link EPieceAbbreviation} is mapped to its corresponding bitboard.
 * </p>
 *
 * <p>
 * This class initializes the game with default positions and provides methods to check move validity, execute moves,
 * and undo the last move.
 * </p>
 *
 * <p>
 * Note: This class assumes a 64-square chessboard represented by bitboards for efficient move generation and validation.
 * </p>
 *
 * @author SirPatschiii
 * @version 2024-06-26
 */
public class ChessEngine {
    private final GUI cGUI;
    private final MouseKeyboard cMouseKeyboard;

    private final HashMap<EPieceAbbreviation, Long> gameState;
    private final Stack<Move> moveHistory;

    // Constant bitboards for default position
    private static final long DEFAULT_BB_WP = 0x000000000000FF00L;
    private static final long DEFAULT_BB_WN = 0x0000000000000042L;
    private static final long DEFAULT_BB_WB = 0x0000000000000024L;
    private static final long DEFAULT_BB_WR = 0x0000000000000081L;
    private static final long DEFAULT_BB_WQ = 0x0000000000000010L;
    private static final long DEFAULT_BB_WK = 0x0000000000000008L;
    private static final long DEFAULT_BB_BP = 0x00FF000000000000L;
    private static final long DEFAULT_BB_BN = 0x4200000000000000L;
    private static final long DEFAULT_BB_BB = 0x2400000000000000L;
    private static final long DEFAULT_BB_BR = 0x8100000000000000L;
    private static final long DEFAULT_BB_BQ = 0x1000000000000000L;
    private static final long DEFAULT_BB_BK = 0x0800000000000000L;

    /**
     * Constructs a new {@code ChessEngine} instance.
     *
     * <p>
     * Initializes the game state, move history, and GUI components.
     * </p>
     */
    public ChessEngine() {
        gameState = new HashMap<>();
        moveHistory = new Stack<>();
        initializeGameState();

        cMouseKeyboard = new MouseKeyboard(this);
        cGUI = new GUI(this, cMouseKeyboard);
    }

    /**
     * Initializes the game state with default chess piece positions.
     *
     * <p>
     * Each chess piece is mapped to its corresponding bitboard value in the game state.
     * </p>
     */
    public void initializeGameState() {
        // Initializes the game state as a hashmap with the piece abbreviation as the key and the bitboard as the value
        EPieceAbbreviation[] pieces = EPieceAbbreviation.values();

        gameState.put(pieces[0], DEFAULT_BB_WP);
        gameState.put(pieces[1], DEFAULT_BB_WN);
        gameState.put(pieces[2], DEFAULT_BB_WB);
        gameState.put(pieces[3], DEFAULT_BB_WR);
        gameState.put(pieces[4], DEFAULT_BB_WQ);
        gameState.put(pieces[5], DEFAULT_BB_WK);
        gameState.put(pieces[6], DEFAULT_BB_BP);
        gameState.put(pieces[7], DEFAULT_BB_BN);
        gameState.put(pieces[8], DEFAULT_BB_BB);
        gameState.put(pieces[9], DEFAULT_BB_BR);
        gameState.put(pieces[10], DEFAULT_BB_BQ);
        gameState.put(pieces[11], DEFAULT_BB_BK);
    }

    /**
     * Validates and executes a move from one square to another.
     *
     * <p>
     * Checks if the move is valid, updates the game state, adds the move to history, and refreshes the GUI.
     * </p>
     *
     * @param squareFrom The starting square index of the move.
     * @param squareTo   The target square index of the move.
     */
    public void checkMove(byte squareFrom, byte squareTo) {
        // Create new move with entered data from the user and the game state bevor the move is executed
        @SuppressWarnings("unchecked")
        Move move = new Move(squareFrom, squareTo, (HashMap<EPieceAbbreviation, Long>) gameState.clone());

        // Checks if move is valid
        if (isMoveValid()) {
            // Adds move to move history
            moveHistory.push(move);
            System.out.println(move);
            // Recalculates index to bitboard index
            squareFrom = (byte) (63 - squareFrom);
            squareTo = (byte) (63 - squareTo);
            for (Map.Entry<EPieceAbbreviation, Long> entry : getGameState().entrySet()) {
                // Deletes piece on target square if existent
                if (BitHelper.isBitSet(entry.getValue(), squareTo)) {
                    entry.setValue(BitHelper.clearBit(entry.getValue(), squareTo));
                }
                // Moves moved pieve to target square
                if (BitHelper.isBitSet(entry.getValue(), squareFrom)) {
                    entry.setValue(BitHelper.clearBit(entry.getValue(), squareFrom));
                    entry.setValue(BitHelper.setBit(entry.getValue(), squareTo));
                }
            }
            // Refreshes graphics
            cGUI.render();
        }
    }

    /**
     * Checks if the current move is valid.
     *
     * <p>
     * Placeholder method for move validation logic.
     * </p>
     *
     * @return {@code true} if the move is valid; {@code false} otherwise.
     */
    public boolean isMoveValid() {
        // Placeholder implementation
        return true;
    }

    /**
     * Undoes the last move.
     *
     * <p>
     * Restores the game state to the state before the last move, updates the GUI, and removes the move from history.
     * </p>
     */
    public void undoMove() {
        // Checks if there are moves to take back
        if (!moveHistory.empty()) {
            // Gets latest move and the game state bevor the move was executed
            Move move = moveHistory.pop();
            HashMap<EPieceAbbreviation, Long> lastGameState = move.getGameState();

            // Replaces current game state with game state move - 1
            for (Map.Entry<EPieceAbbreviation, Long> entry : lastGameState.entrySet()) {
                gameState.replace(entry.getKey(), entry.getValue());
            }

            // Refreshes graphics
            cGUI.render();
        }
    }

    // Getter methods for default bitboards
    // (These methods are static because they access constants)
    public static long getDefaultBbWp() {
        return DEFAULT_BB_WP;
    }

    public static long getDefaultBbWn() {
        return DEFAULT_BB_WN;
    }

    public static long getDefaultBbWb() {
        return DEFAULT_BB_WB;
    }

    public static long getDefaultBbWr() {
        return DEFAULT_BB_WR;
    }

    public static long getDefaultBbWq() {
        return DEFAULT_BB_WQ;
    }

    public static long getDefaultBbWk() {
        return DEFAULT_BB_WK;
    }

    public static long getDefaultBbBp() {
        return DEFAULT_BB_BP;
    }

    public static long getDefaultBbBn() {
        return DEFAULT_BB_BN;
    }

    public static long getDefaultBbBb() {
        return DEFAULT_BB_BB;
    }

    public static long getDefaultBbBr() {
        return DEFAULT_BB_BR;
    }

    public static long getDefaultBbBq() {
        return DEFAULT_BB_BQ;
    }

    public static long getDefaultBbBk() {
        return DEFAULT_BB_BK;
    }

    /**
     * Retrieves the current game state.
     *
     * <p>
     * Returns the {@link HashMap} representing the current game state where each piece is mapped to its bitboard.
     * </p>
     *
     * @return The current game state as a {@link HashMap} of {@link EPieceAbbreviation} to {@code long} values.
     */
    public HashMap<EPieceAbbreviation, Long> getGameState() {
        return gameState;
    }
}
