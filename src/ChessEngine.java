import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * <p>
 * The {@code ChessEngine} class represents the core logic of the chess engine.
 * </p>
 * <p>
 * It manages the game state, validates moves, handles move history, and interacts with the graphical user interface (GUI).
 * The game state is stored using a {@code HashMap} where each {@link EPieceAbbreviation} is mapped to its corresponding bitboard.
 * This class initializes the game with default positions and provides methods to check move validity, execute moves,
 * and undo the last move.
 * </p>
 * <p>
 * Note: This class assumes a 64-square chessboard represented by bitboards for efficient move generation and validation.
 * </p>
 *
 * @author SirPatschiii
 * @version 15.07.2024
 */
@SuppressWarnings("unused")
public class ChessEngine {
    private final GUI cGUI;

    private final HashMap<EPieceAbbreviation, Long> gameState;
    private boolean whiteToMove;
    private final Stack<Move> moveHistory;

    // Constant bitboards for default position
    public static final long DEFAULT_BB_WP = 0x000000000000FF00L;
    public static final long DEFAULT_BB_WN = 0x0000000000000042L;
    public static final long DEFAULT_BB_WB = 0x0000000000000024L;
    public static final long DEFAULT_BB_WR = 0x0000000000000081L;
    public static final long DEFAULT_BB_WQ = 0x0000000000000010L;
    public static final long DEFAULT_BB_WK = 0x0000000000000008L;
    public static final long DEFAULT_BB_BP = 0x00FF000000000000L;
    public static final long DEFAULT_BB_BN = 0x4200000000000000L;
    public static final long DEFAULT_BB_BB = 0x2400000000000000L;
    public static final long DEFAULT_BB_BR = 0x8100000000000000L;
    public static final long DEFAULT_BB_BQ = 0x1000000000000000L;
    public static final long DEFAULT_BB_BK = 0x0800000000000000L;

    // Constant bitboards for ranks and files
    public static final long RANK_1 = 0x00000000000000FFL;
    public static final long RANK_2 = 0x000000000000FF00L;
    public static final long RANK_3 = 0x0000000000FF0000L;
    public static final long RANK_4 = 0x00000000FF000000L;
    public static final long RANK_5 = 0x000000FF00000000L;
    public static final long RANK_6 = 0x0000FF0000000000L;
    public static final long RANK_7 = 0x00FF000000000000L;
    public static final long RANK_8 = 0xFF00000000000000L;
    public static final long FILE_A = 0x8080808080808080L;
    public static final long FILE_B = 0x4040404040404040L;
    public static final long FILE_C = 0x2020202020202020L;
    public static final long FILE_D = 0x1010101010101010L;
    public static final long FILE_E = 0x0808080808080808L;
    public static final long FILE_F = 0x0404040404040404L;
    public static final long FILE_G = 0x0202020202020202L;
    public static final long FILE_H = 0x0101010101010101L;

    /**
     * Constructs a new {@code ChessEngine} instance.
     *
     * <p>
     * Initializes the game state, move history, and GUI components.
     * </p>
     */
    public ChessEngine() {
        gameState = new HashMap<>();
        whiteToMove = true;
        moveHistory = new Stack<>();
        initializeGameState();

        MouseKeyboard cMouseKeyboard = new MouseKeyboard(this);
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
        // Create a new move with entered data from the user, and the game state bevor the move is executed
        @SuppressWarnings("unchecked")
        Move movePlayed = new Move(squareFrom, squareTo, (HashMap<EPieceAbbreviation, Long>) gameState.clone());

        // Checks if the played move is valid
        if (isMoveValid(movePlayed)) {
            // Adds the played move to move history
            moveHistory.push(movePlayed);

            for (Map.Entry<EPieceAbbreviation, Long> entry : gameState.entrySet()) {
                // Deletes a piece on target square if existent
                if (BitHelper.isBitSet(entry.getValue(), squareTo)) {
                    entry.setValue(BitHelper.clearBit(entry.getValue(), squareTo));
                }
                // Moves moved pieve to target square
                if (BitHelper.isBitSet(entry.getValue(), squareFrom)) {
                    entry.setValue(BitHelper.clearBit(entry.getValue(), squareFrom));
                    entry.setValue(BitHelper.setBit(entry.getValue(), squareTo));
                }
            }

            // Transfers the right to move
            whiteToMove = !whiteToMove;

            // Refreshes graphics
            cGUI.render();
        }
    }

    /**
     * <p>
     * Generates a list of all possible moves for the player whose turn it is,
     * based on the current game state. The method checks if it's white's turn or black's turn
     * and generates moves accordingly for each piece type: Pawn, Knight, Bishop, Rook, Queen, and King.
     * </p>
     *
     * @return An {@code ArrayList} of {@link Move} objects representing all possible moves for the current player.
     */
    public ArrayList<Move> generateAllPossibleMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        // Generate moves for white pieces
        if (whiteToMove) {
            possibleMoves = generateAllPossibleWhiteMoves(gameState);
        }

        // Generate moves for black pieces
        if (!whiteToMove) {
            possibleMoves = generateAllPossibleBlackMoves(gameState);
        }

        return possibleMoves;
    }

    /**
     * <p>
     * Generates a list of all possible moves for the white pieces, based on the current game state.
     * </p>
     *
     * @return An {@code ArrayList} of {@link Move} objects representing all possible moves for the current player.
     */
    private ArrayList<Move> generateAllPossibleWhiteMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

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

        return possibleMoves;
    }

    /**
     * <p>
     * Generates a list of all possible moves for the black pieces, based on the current game state.
     * </p>
     *
     * @return An {@code ArrayList} of {@link Move} objects representing all possible moves for the current player.
     */
    private ArrayList<Move> generateAllPossibleBlackMoves(HashMap<EPieceAbbreviation, Long> gameState) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

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

        return possibleMoves;
    }

    /**
     * <p>
     *  Checks if the given move is valid by generating all possible moves for the current player
     *  and filtering out invalid moves. The method then compares the provided move with the possible moves.
     * </p>
     *
     * @param movePlayed The {@link Move} object representing the move to be validated.
     * @return {@code true} if the move is valid, {@code false} otherwise.
     */
    public boolean isMoveValid(Move movePlayed) {
        ArrayList<Move> possibleMoves = generateAllPossibleMoves(gameState);
        filterPossibleMovesForInvalidMoves(possibleMoves);

        // Iterate through all possible moves
        for (Move possibleMove : possibleMoves) {
            // Check if the played move is listed in the possible moves
            if (movePlayed.hashCode() == possibleMove.hashCode()) {
                return true;
            }
        }
        // If not, return false
        return false;
    }

    /**
     * <p>
     *  Filters out illegal moves from the provided list of possible moves. A move is considered illegal if it leaves the
     *  player's own king in check.
     * </p>
     *
     * @param possibleMoves An {@code ArrayList} of {@link Move} objects representing all possible moves before filtering.
     */
    public void filterPossibleMovesForInvalidMoves(ArrayList<Move> possibleMoves) {
        ArrayList<Move> illegalMoves = new ArrayList<>();
        // Delete moves which are illegal because the own king is in check afterward
        for (Move possibleMove : possibleMoves) {
            Move move = possibleMove.clone();
            HashMap<EPieceAbbreviation, Long> gameState = move.getGameState();

            // Calculate the game state from the current position + 1
            long bitboard = gameState.get(move.getPiece());
            bitboard = BitHelper.clearBit(bitboard, move.getSquareFrom());
            bitboard = BitHelper.setBit(bitboard, move.getSquareTo());
            gameState.replace(move.getPiece(), bitboard);

            // Check if now the own king is in check
            if (whiteToMove) {
                long possession = BoardHelper.getBlackAttackRays(gameState);
                if ((possession & gameState.get(EPieceAbbreviation.WK)) != 0) {
                    illegalMoves.addLast(possibleMove);
                }
            }
            if (!whiteToMove) {
                long possession = BoardHelper.getWhiteAttackRays(gameState);
                if ((possession & gameState.get(EPieceAbbreviation.BK)) != 0) {
                    illegalMoves.addLast(possibleMove);
                }
            }
        }
        // Remove the illegal moves from the possible moves
        for (Move illegalMove : illegalMoves) {
            possibleMoves.remove(illegalMove);
        }
    }

    /**
     * <p>
     * Undoes the last move.
     * </p>
     * <p>
     * Restores the game state to the state before the last move, updates the GUI, removes the move from history
     * and transfers the right to move.
     * </p>
     */
    public void undoMove() {
        // Checks if there are moves to take back
        if (!moveHistory.empty()) {
            // Gets the latest move, and the game state bevor the move was executed
            Move move = moveHistory.pop();
            HashMap<EPieceAbbreviation, Long> lastGameState = move.getGameState();

            // Replaces current game state with game state move – 1
            for (Map.Entry<EPieceAbbreviation, Long> entry : lastGameState.entrySet()) {
                gameState.replace(entry.getKey(), entry.getValue());
            }

            // Transfers the right to move
            whiteToMove = !whiteToMove;

            // Refreshes graphics
            cGUI.render();
        }
    }

    /**
     * <p>
     * Retrieves the current game state.
     * </p>
     *
     * @return The current game state as a {@code HashMap} of {@link EPieceAbbreviation} to {@code long} values.
     */
    public HashMap<EPieceAbbreviation, Long> getGameState() {
        return gameState;
    }
}
