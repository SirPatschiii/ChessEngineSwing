import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * The {@code GUI} class represents the graphical user interface for the chess game.
 * It initializes and manages the display of the chessboard, pieces, and other GUI components.
 * </p>
 *
 * @author SirPatschiii
 * @version 14.07.2024
 */
public class GUI {
    private final ChessEngine cChessEngine;
    private final MouseKeyboard cMouseKeyboard;

    private final JFrame frame;
    private final ArrayList<JLabel> squares;
    private final ArrayList<JLabel> squareLetters;
    private final ArrayList<JLabel> squareNumbers;
    private final HashMap<EPieceAbbreviation, ImageIcon> pieces;
    private final HashMap<ESquareLetterAbbreviation, ImageIcon> squareLettersMap;
    private final HashMap<ESquareNumberAbbreviation, ImageIcon> squareNumbersMap;
    private final HashMap<Double, ESquareDescription> squareDescriptionWhitePerspective;
    private final HashMap<Double, ESquareDescription> squareDescriptionBlackPerspective;

    private boolean perspectiveWhite;

    private static final byte height = 100;
    private static final byte width = 100;
    private static short adjustmentX;
    private static short adjustmentY;

    /**
     * <p>
     * Constructs a new {@code GUI} object with the specified {@link ChessEngine} and {@link MouseKeyboard}.
     * </p>
     *
     * @param chessEngine the chess engine to be used
     * @param mouseKeyboard the mouse and keyboard handler to be used
     */
    public GUI(ChessEngine chessEngine, MouseKeyboard mouseKeyboard) {
        cChessEngine = chessEngine;
        cMouseKeyboard = mouseKeyboard;

        frame = new JFrame();
        squares = new ArrayList<>();
        squareLetters = new ArrayList<>();
        squareNumbers = new ArrayList<>();
        pieces = new HashMap<>();
        squareLettersMap = new HashMap<>();
        squareNumbersMap = new HashMap<>();
        squareDescriptionWhitePerspective = new HashMap<>();
        squareDescriptionBlackPerspective = new HashMap<>();

        perspectiveWhite = true;

        initializeGUI();
    }

    /**
     * <p>
     * Initializes the GUI components, including the frame, squares, piece images, and square descriptions.
     * Prepares the GUI for rendering the chess board and pieces based on initial configurations.
     * </p>
     */
    private void initializeGUI() {
        initializeFrame();
        initializeSquares();
        initializeSquareDescriptions();
        preloadPieces();
        preloadSquareNames();
        drawSquares();
        drawSquareDescription();
        render();
    }

    /**
     * <p>
     * Initializes the main frame of the GUI with specific configurations and settings.
     * Sets up the frame's appearance, size, background, and event listeners.
     * </p>
     */
    private void initializeFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(Configuration.INSTANCE.pathII).getImage());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(new Color(40, 44, 52));
        frame.setLayout(null);
        frame.setVisible(true);
        frame.addKeyListener(cMouseKeyboard);
        frame.addMouseListener(cMouseKeyboard);

        adjustmentX = (short) (frame.getWidth() / 2 - 400);
        adjustmentY = (short) (frame.getHeight() / 2 - 400);
    }

    /**
     * <p>
     * Initializes the square labels on the chess board, arranging them in a grid layout.
     * Sets up their positions, sizes, and initial appearances.
     * </p>
     */
    private void initializeSquares() {
        // Create square labels for the chess board
        for (int i = 0; i < 64; i++) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            frame.add(label, -1);
            squares.addLast(label);
        }

        // Create labels for square letters (a-h)
        for (int i = 0; i < 64; i++) {
            JLabel label = new JLabel();
            label.setOpaque(false);
            label.setVerticalAlignment(JLabel.BOTTOM);
            label.setHorizontalAlignment(JLabel.LEFT);
            frame.add(label, 0);
            squareLetters.addLast(label);
        }

        // Create labels for square numbers (1-8)
        for (int i = 0; i < 64; i++) {
            JLabel label = new JLabel();
            label.setOpaque(false);
            label.setVerticalAlignment(JLabel.TOP);
            label.setHorizontalAlignment(JLabel.RIGHT);
            frame.add(label, 0);
            squareNumbers.addLast(label);
        }

        // Position squares and labels on the frame based on chess board layout
        int index = 0;
        for (int row = 7; row >= 0; row--) {
            for (int col = 7; col >= 0; col--) {
                int x = col * width + adjustmentX;
                int y = row * height + adjustmentY;

                squares.get(index).setBounds(x, y, width, height);
                squareLetters.get(index).setBounds(x, y, width, height);
                squareNumbers.get(index).setBounds(x, y, width, height);
                index++;
            }
        }
    }

    /**
     * <p>
     * Initializes the descriptions for the squares from both white and black perspectives.
     * </p>
     * <p>
     * The descriptions map each square number to its corresponding description, represented by an enumeration value
     * from {@link ESquareDescription}. This mapping helps in identifying each square uniquely from either perspective.
     * </p>
     */
    private void initializeSquareDescriptions() {
        double[] squareNumber = {56, 48, 40, 32, 24, 16, 8, 7, 6, 5, 4, 3, 2, 1, 0.0, 0.1};

        // Initialize descriptions for white perspective
        String[] squareDescription = {"L8", "D7", "L6", "D5", "L4", "D3", "L2", "LA", "DB", "LC", "DD", "LE", "DF", "LG", "DH", "D1"};
        for (int i = 0; i < squareNumber.length; i++) {
            double key = squareNumber[i];
            String value = squareDescription[i];
            squareDescriptionWhitePerspective.put(key, ESquareDescription.valueOf(value));
        }

        // Initialize descriptions for black perspective
        squareDescription = new String[]{"L1", "D2", "L3", "D4", "L5", "D6", "L7", "LH", "DG", "LF", "DE", "LD", "DC", "LB", "DA", "D8"};
        for (int i = 0; i < squareNumber.length; i++) {
            double key = squareNumber[i];
            String value = squareDescription[i];
            squareDescriptionBlackPerspective.put(key, ESquareDescription.valueOf(value));
        }
    }

    /**
     * <p>
     * Preloads the images for all chess pieces and stores them in a hash map for quick access.
     * </p>
     * <p>
     * This method iterates over all values of the {@link EPieceAbbreviation} enum, retrieves the corresponding image
     * path from the configuration, and scales the image to fit the predefined width and height. The scaled image is then
     * stored in the {@code pieces} hash map with the piece abbreviation as the key.
     * </p>
     */
    private void preloadPieces() {
        EPieceAbbreviation[] pieceAbbreviation = EPieceAbbreviation.values();

        // Preload piece images
        for (EPieceAbbreviation piece : pieceAbbreviation) {
            pieces.put(piece, new ImageIcon(new ImageIcon(Configuration.INSTANCE.getImagePath(piece.toString())).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        }
    }

    /**
     * <p>
     * Preloads the images for square letters and numbers and stores them in hash maps for quick access.
     * </p>
     * <p>
     * This method iterates over all values of the {@link ESquareLetterAbbreviation} and {@link ESquareNumberAbbreviation} enums,
     * retrieves the corresponding image paths from the configuration, and scales the images to fit predefined dimensions.
     * The scaled images are then stored in the {@code squareLettersMap} and {@code squareNumbersMap} hash maps with the
     * abbreviations as the keys.
     * </p>
     */
    private void preloadSquareNames() {
        ESquareLetterAbbreviation[] squareLetterAbbreviation = ESquareLetterAbbreviation.values();
        ESquareNumberAbbreviation[] squareNumberAbbreviation = ESquareNumberAbbreviation.values();

        // Preload square letter images
        for (ESquareLetterAbbreviation letter : squareLetterAbbreviation) {
            squareLettersMap.put(letter, new ImageIcon(new ImageIcon(Configuration.INSTANCE.getLetterPath(letter.toString())).getImage().getScaledInstance(width / 6, height / 6, Image.SCALE_SMOOTH)));
        }

        // Preload square number images
        for (ESquareNumberAbbreviation number : squareNumberAbbreviation) {
            squareNumbersMap.put(number, new ImageIcon(new ImageIcon(Configuration.INSTANCE.getNumberPath(number.toString())).getImage().getScaledInstance(width / 5, height / 5, Image.SCALE_SMOOTH)));
        }
    }

    /**
     * <p>
     * Renders the chess board by drawing the pieces and repainting the frame.
     * </p>
     * <p>
     * This method invokes {@code drawPieces()} to update the piece icons on the squares and then calls {@code repaint()}
     * on the frame to refresh the display.
     * </p>
     */
    public void render() {
        drawPieces();
        frame.repaint();
    }

    /**
     * <p>
     * Draws the chess board squares with alternating colors.
     * </p>
     * <p>
     * This method sets the background color of each square on the chess board, alternating between two colors
     * to create the standard checkerboard pattern. The colors used are a light beige and a darker brown.
     * </p>
     */
    private void drawSquares() {
        Color[] colors = {new Color(241, 217, 192), new Color(169, 122, 101)};

        // Draw squares with alternating colors
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Color color = colors[(row + col) % 2];
                JLabel square = squares.get(col + row * 8);
                square.setBackground(color);
            }
        }
    }

    /**
     * <p>
     * Draws the descriptions for the chess squares based on the current perspective (white or black).
     * </p>
     * <p>
     * This method iterates through the predefined square descriptions and sets the corresponding icons
     * for the square letters and numbers based on the current perspective. The descriptions are stored
     * in two separate maps: one for the white perspective and one for the black perspective. It assigns
     * icons based on the square's position on the board, ensuring the correct visual representation
     * for the players.
     * </p>
     *
     * @throws RuntimeException if an illegal key is encountered in the square description HashMap.
     */
    private void drawSquareDescription() {
        // Draw square descriptions based on perspective (white or black)
        if (perspectiveWhite) {
            for (Map.Entry<Double, ESquareDescription> entry : squareDescriptionWhitePerspective.entrySet()) {
                switch (entry.getKey().intValue()) {
                    case 0 -> {
                        if (entry.getKey() == 0.0) {
                            squareLetters.getFirst().setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                        } else if (entry.getKey() == 0.1) {
                            squareNumbers.getFirst().setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                        }
                    }
                    case 1 ->
                            squareLetters.get(1).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 2 ->
                            squareLetters.get(2).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 3 ->
                            squareLetters.get(3).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 4 ->
                            squareLetters.get(4).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 5 ->
                            squareLetters.get(5).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 6 ->
                            squareLetters.get(6).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 7 ->
                            squareLetters.get(7).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 8 ->
                            squareNumbers.get(8).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 16 ->
                            squareNumbers.get(16).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 24 ->
                            squareNumbers.get(24).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 32 ->
                            squareNumbers.get(32).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 40 ->
                            squareNumbers.get(40).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 48 ->
                            squareNumbers.get(48).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 56 ->
                            squareNumbers.get(56).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    default -> throw new RuntimeException("Illegal key in HashMap!");
                }
            }
        } else {
            for (Map.Entry<Double, ESquareDescription> entry : squareDescriptionBlackPerspective.entrySet()) {
                switch (entry.getKey().intValue()) {
                    case 0 -> {
                        if (entry.getKey() == 0.0) {
                            squareLetters.getFirst().setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                        } else if (entry.getKey() == 0.1) {
                            squareNumbers.getFirst().setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                        }
                    }
                    case 1 ->
                            squareLetters.get(1).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 2 ->
                            squareLetters.get(2).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 3 ->
                            squareLetters.get(3).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 4 ->
                            squareLetters.get(4).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 5 ->
                            squareLetters.get(5).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 6 ->
                            squareLetters.get(6).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 7 ->
                            squareLetters.get(7).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 8 ->
                            squareNumbers.get(8).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 16 ->
                            squareNumbers.get(16).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 24 ->
                            squareNumbers.get(24).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 32 ->
                            squareNumbers.get(32).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 40 ->
                            squareNumbers.get(40).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 48 ->
                            squareNumbers.get(48).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 56 ->
                            squareNumbers.get(56).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    default -> throw new RuntimeException("Illegal key in HashMap!");
                }
            }
        }
    }

    /**
     * <p>
     * Draws the chess pieces on the board based on the current game state.
     * </p>
     * <p>
     * This method retrieves the current game state from the chess engine, clears any existing piece icons from
     * the board squares, and then sets the appropriate icons for each piece according to their positions as
     * represented in the game state bitboards.
     * </p>
     */
    private void drawPieces() {
        HashMap<EPieceAbbreviation, Long> gameState = cChessEngine.getGameState();

        // Clear existing piece icons
        for (JLabel square : squares) {
            square.setIcon(null);
        }

        // Draw pieces on the board based on current game state
        for (Map.Entry<EPieceAbbreviation, Long> entry : gameState.entrySet()) {
            long bitboard = entry.getValue();
            for (int i = 0; i < 64; i++) {
                if (BitHelper.isBitSet(bitboard, i)) {
                    squares.get(i).setIcon(pieces.get(entry.getKey()));
                }
            }
        }
    }

    /**
     * <p>
     * Sets the perspective of the chess board display to either white or black.
     * </p>
     *
     * @param perspectiveWhite {@code true} to set the perspective to white, {@code false} for black.
     */
    public void setPerspectiveWhite(boolean perspectiveWhite) {
        this.perspectiveWhite = perspectiveWhite;
    }

    /**
     * <p>
     * Retrieves the X-axis adjustment for positioning elements in the GUI.
     * </p>
     *
     * @return The X-axis adjustment as a {@code short}.
     */
    public static short getAdjustmentX() {
        return adjustmentX;
    }

    /**
     * <p>
     * Retrieves the Y-axis adjustment for positioning elements in the GUI.
     * </p>
     *
     * @return The Y-axis adjustment as a {@code short}.
     */
    public static short getAdjustmentY() {
        return adjustmentY;
    }
}
