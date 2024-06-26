/**
 * Entry point of the chess engine application.
 *
 * <p>
 * The {@code Application} class initializes the {@link ChessEngine} upon execution.
 * </p>
 *
 * <p>
 * This class contains the {@code main} method which:
 * <ul>
 *     <li>Creates an instance of the {@link ChessEngine} class to start the chess engine.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class serves as the entry point for the application and does not support instantiation.
 * </p>
 *
 * @author SirPatschiii
 * @version 2024-06-26
 */
public class Application {
    /**
     * Main method, entry point of the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new ChessEngine();
    }
}
