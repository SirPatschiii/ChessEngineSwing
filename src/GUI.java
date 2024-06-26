import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code GUI} class manages the graphical user interface for a chess game.
 * It includes methods for initializing the frame, drawing squares, pieces, and other GUI elements.
 * The class handles user input through a {@link MouseKeyboard} instance and updates based on the game state from {@link ChessEngine}.
 *
 * <p>
 * The GUI supports both white and black perspectives and uses various images and configurations to represent the chess board and pieces.
 * </p>
 *
 * <p>
 * This class uses Swing components for GUI elements and Java's {@code ImageIcon} to manage images for squares and pieces.
 * </p>
 *
 * <p>
 * Note: This class assumes specific configurations and images are available in the project's resources.
 * </p>
 *
 * @author SirPatschiii
 * @version 2024-06-26
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
     * Constructs a {@code GUI} object initialized with a {@code ChessEngine} and {@code MouseKeyboard} instance.
     * Initializes GUI components and sets up the initial game state representation.
     *
     * @param chessEngine   the {@code ChessEngine} instance managing the game logic.
     * @param mouseKeyboard the {@code MouseKeyboard} instance handling user input.
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
     * Initializes the GUI components, including the frame, squares, piece images, and square descriptions.
     * Prepares the GUI for rendering the chess board and pieces based on initial configurations.
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
     * Initializes the main frame of the GUI with specific configurations and settings.
     * Sets up the frame's appearance, size, background, and event listeners.
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
     * Initializes the square labels on the chess board, arranging them in a grid layout.
     * Sets up their positions, sizes, and initial appearances.
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
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int index = col + row * 8;
                int x = col * width + adjustmentX;
                int y = row * height + adjustmentY;

                squares.get(index).setBounds(x, y, width, height);
                squareLetters.get(index).setBounds(x, y, width, height);
                squareNumbers.get(index).setBounds(x, y, width, height);
            }
        }
    }

    /**
     * Initializes descriptions for specific squares on the chess board for both white and black perspectives.
     * Uses {@link ESquareDescription} for mapping square numbers to descriptions.
     */
    private void initializeSquareDescriptions() {
        double[] squareNumber = {7, 15, 23, 31, 39, 47, 55, 56, 57, 58, 59, 60, 61, 62, 63.0, 63.1};

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
     * Preloads images for chess pieces based on {@link EPieceAbbreviation} values.
     * Scales images to fit the square size defined by {@code width} and {@code height}.
     */
    private void preloadPieces() {
        EPieceAbbreviation[] pieceAbbreviation = EPieceAbbreviation.values();

        // Preload piece images
        for (EPieceAbbreviation piece : pieceAbbreviation) {
            pieces.put(piece, new ImageIcon(new ImageIcon(Configuration.INSTANCE.getImagePath(piece.toString())).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        }
    }

    /**
     * Preloads images for square letters (a-h) and square numbers (1-8) based on {@link ESquareLetterAbbreviation} and {@link ESquareNumberAbbreviation} values.
     * Scales images to fit appropriate sizes for letters and numbers on the board.
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
     * Renders the GUI components, updating the chess board with current game state.
     * Calls methods to draw squares, piece positions, and descriptions.
     */
    public void render() {
        drawPieces();
        frame.repaint();
    }

    /**
     * Draws the squares of the chess board with alternating colors.
     * Colors are defined in an array and applied based on the position of the square.
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
     * Draws square descriptions on the chess board based on the current perspective (white or black).
     * Uses {@link ESquareDescription} values to determine which description to display on specific squares.
     */
    private void drawSquareDescription() {
        // Draw square descriptions based on perspective (white or black)
        if (perspectiveWhite) {
            for (Map.Entry<Double, ESquareDescription> entry : squareDescriptionWhitePerspective.entrySet()) {
                switch (entry.getKey().intValue()) {
                    case 7 ->
                            squareNumbers.get(7).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 15 ->
                            squareNumbers.get(15).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 23 ->
                            squareNumbers.get(23).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 31 ->
                            squareNumbers.get(31).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 39 ->
                            squareNumbers.get(39).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 47 ->
                            squareNumbers.get(47).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 55 ->
                            squareNumbers.get(55).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 56 ->
                            squareLetters.get(56).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 57 ->
                            squareLetters.get(57).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 58 ->
                            squareLetters.get(58).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 59 ->
                            squareLetters.get(59).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 60 ->
                            squareLetters.get(60).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 61 ->
                            squareLetters.get(61).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 62 ->
                            squareLetters.get(62).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 63 -> {
                        if (entry.getKey() == 63.0) {
                            squareLetters.get(63).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                        } else if (entry.getKey() == 63.1) {
                            squareNumbers.get(63).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                        }
                    }
                    default -> throw new RuntimeException("Illegal key in HashMap!");
                }
            }
        } else {
            for (Map.Entry<Double, ESquareDescription> entry : squareDescriptionBlackPerspective.entrySet()) {
                switch (entry.getKey().intValue()) {
                    case 7 ->
                            squareNumbers.get(7).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 15 ->
                            squareNumbers.get(15).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 23 ->
                            squareNumbers.get(23).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 31 ->
                            squareNumbers.get(31).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 39 ->
                            squareNumbers.get(39).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 47 ->
                            squareNumbers.get(47).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 55 ->
                            squareNumbers.get(55).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                    case 56 ->
                            squareLetters.get(56).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 57 ->
                            squareLetters.get(57).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 58 ->
                            squareLetters.get(58).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 59 ->
                            squareLetters.get(59).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 60 ->
                            squareLetters.get(60).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 61 ->
                            squareLetters.get(61).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 62 ->
                            squareLetters.get(62).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                    case 63 -> {
                        if (entry.getKey() == 63.0) {
                            squareLetters.get(63).setIcon(squareLettersMap.get(ESquareLetterAbbreviation.valueOf(entry.getValue().toString())));
                        } else if (entry.getKey() == 63.1) {
                            squareNumbers.get(63).setIcon(squareNumbersMap.get(ESquareNumberAbbreviation.valueOf(entry.getValue().toString())));
                        }
                    }
                    default -> throw new RuntimeException("Illegal key in HashMap!");
                }
            }
        }
    }

    /**
     * Draws chess pieces on the board based on the current game state.
     * Retrieves piece positions from the {@code ChessEngine} and displays corresponding images.
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
                    squares.get(63 - i).setIcon(pieces.get(entry.getKey()));
                }
            }
        }
    }

    /**
     * Sets the perspective of the GUI to either white or black.
     * Determines how square descriptions are displayed based on this perspective.
     *
     * @param perspectiveWhite {@code true} to set white perspective, {@code false} for black perspective.
     */
    public void setPerspectiveWhite(boolean perspectiveWhite) {
        this.perspectiveWhite = perspectiveWhite;
    }

    /**
     * Retrieves the horizontal adjustment value for positioning elements on the GUI.
     * Used to calculate X coordinates for squares and labels.
     *
     * @return the horizontal adjustment value.
     */
    public static short getAdjustmentX() {
        return adjustmentX;
    }

    /**
     * Retrieves the vertical adjustment value for positioning elements on the GUI.
     * Used to calculate Y coordinates for squares and labels.
     *
     * @return the vertical adjustment value.
     */
    public static short getAdjustmentY() {
        return adjustmentY;
    }
}
