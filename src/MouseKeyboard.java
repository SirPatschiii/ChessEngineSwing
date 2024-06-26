import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The {@code MouseKeyboard} class implements {@code KeyListener} and {@code MouseListener} to handle keyboard and mouse events for the chess engine GUI.
 * This class interacts with the {@code ChessEngine} to manage user inputs from both the keyboard and the mouse.
 *
 * @author SirPatschiii
 * @version 2024-06-26
 */
public class MouseKeyboard implements KeyListener, MouseListener {
    private final ChessEngine cChessEngine;

    private short adjustmentX;
    private short adjustmentY;
    private byte squareFrom;
    private byte squareTo;

    /**
     * Constructs a {@code MouseKeyboard} object that listens to keyboard and mouse events.
     *
     * @param chessEngine the {@code ChessEngine} instance to interact with for processing moves.
     */
    public MouseKeyboard(ChessEngine chessEngine) {
        cChessEngine = chessEngine;
    }

    /**
     * Invoked when a key has been typed.
     * This event occurs when a key press is followed by a key release.
     *
     * @param e the event to be processed.
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     *
     * @param e the event to be processed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Enables the ESC button to exit the application
            case 27 -> System.exit(0);
            // Undoes a move when pressing arrow left
            case 37 -> cChessEngine.undoMove();
        }
    }

    /**
     * Invoked when a key has been released.
     *
     * @param e the event to be processed.
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Invoked when the mouse has been clicked.
     *
     * @param e the event to be processed.
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Gets the adjustment of the board from the GUI
        adjustmentX = GUI.getAdjustmentX();
        adjustmentY = GUI.getAdjustmentY();

        // Gets the position of the mouse and subtracts the adjustment to get a normalized x and y value
        short mouseX = (short) (e.getX() - adjustmentX);
        short mouseY = (short) (e.getY() - adjustmentY);

        // Checks if the mouse was over the board
        if (mouseX >= 0 && mouseX <= 799 && mouseY >= 0 && mouseY <= 799) {
            // Calculates the square the mouse selected
            squareFrom = (byte) ((mouseX / 100) + (mouseY / 100 * 8));
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Gets the position of the mouse and subtracts the adjustment to get a normalized x and y value
        short mouseX = (short) (e.getX() - adjustmentX);
        short mouseY = (short) (e.getY() - adjustmentY);

        // Checks if the mouse was over the board
        if (mouseX >= 0 && mouseX <= 799 && mouseY >= 0 && mouseY <= 799) {
            // Calculates the square the mouse selected
            squareTo = (byte) ((mouseX / 100) + (mouseY / 100 * 8));
            // Passes the squares to the chess engine to check the move for its correctness
            cChessEngine.checkMove(squareFrom, squareTo);
        }
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed.
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed.
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
