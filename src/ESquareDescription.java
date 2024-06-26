/**
 * Enumeration representing descriptions and abbreviations for squares on a chess board.
 * Each enumeration value corresponds to a specific square's description or abbreviation.
 *
 * <p>
 * The enumeration includes abbreviations for square letters (columns) and square numbers (rows)
 * on the chess board, providing a comprehensive set of identifiers for each square.
 * </p>
 *
 * <p>
 * Abbreviations:
 * <ul>
 *     <li>{@code DA} to {@code DH} - Square letters A to H</li>
 *     <li>{@code LA} to {@code LH} - Square letters A to H (alternative abbreviations)</li>
 *     <li>{@code D1} to {@code D8} - Square numbers 1 to 8</li>
 *     <li>{@code L1} to {@code L8} - Square numbers 1 to 8 (alternative abbreviations)</li>
 * </ul>
 * </p>
 *
 * <p>
 * Note: The enum provides a unified set of identifiers for squares on a chess board,
 * combining both letter (file) and number (rank) representations.
 * </p>
 *
 * @author SirPatschiii
 * @version 2024-06-26
 */
public enum ESquareDescription {
    DA, LA, DB, LB, DC, LC, DD, LD, DE, LE, DF, LF, DG, LG, DH, LH,
    D1, L1, D2, L2, D3, L3, D4, L4, D5, L5, D6, L6, D7, L7, D8, L8
}
