/**
 * Singleton enum representing configuration constants for a chess game application.
 * This enum provides paths to images used for pieces, square names, and square numbers,
 * as well as an icon path for the application frame.
 *
 * <p>
 * This enum ensures that configuration paths are centralized and easily accessible
 * throughout the application.
 * </p>
 *
 * <p>
 * The paths provided include:
 * <ul>
 *     <li>{@code pathWP} - Path to the image file for White Pawn piece.</li>
 *     <li>{@code pathWN} - Path to the image file for White Knight piece.</li>
 *     <li>{@code pathWB} - Path to the image file for White Bishop piece.</li>
 *     <li>{@code pathWR} - Path to the image file for White Rook piece.</li>
 *     <li>{@code pathWQ} - Path to the image file for White Queen piece.</li>
 *     <li>{@code pathWK} - Path to the image file for White King piece.</li>
 *     <li>{@code pathBP} - Path to the image file for Black Pawn piece.</li>
 *     <li>{@code pathBN} - Path to the image file for Black Knight piece.</li>
 *     <li>{@code pathBB} - Path to the image file for Black Bishop piece.</li>
 *     <li>{@code pathBR} - Path to the image file for Black Rook piece.</li>
 *     <li>{@code pathBQ} - Path to the image file for Black Queen piece.</li>
 *     <li>{@code pathBK} - Path to the image file for Black King piece.</li>
 *     <li>{@code pathDA} - Path to the dark brown square name image files.</li>
 *     <li>{@code pathLA} - Path to the light brown square name image files.</li>
 *     <li>{@code pathDB} - Path to the dark brown square name image files.</li>
 *     <li>{@code pathLB} - Path to the light brown square name image files.</li>
 *     <li>{@code pathDC} - Path to the dark brown square name image files.</li>
 *     <li>{@code pathLC} - Path to the light brown square name image files.</li>
 *     <li>{@code pathDD} - Path to the dark brown square name image files.</li>
 *     <li>{@code pathLD} - Path to the light brown square name image files.</li>
 *     <li>{@code pathDE} - Path to the dark brown square name image files.</li>
 *     <li>{@code pathLE} - Path to the light brown square name image files.</li>
 *     <li>{@code pathDF} - Path to the dark brown square name image files.</li>
 *     <li>{@code pathLF} - Path to the light brown square name image files.</li>
 *     <li>{@code pathDG} - Path to the dark brown square name image files.</li>
 *     <li>{@code pathLG} - Path to the light brown square name image files.</li>
 *     <li>{@code pathDH} - Path to the dark brown square name image files.</li>
 *     <li>{@code pathLH} - Path to the light brown square name image files.</li>
 *     <li>{@code pathD1} - Path to the dark brown square number image files.</li>
 *     <li>{@code pathL1} - Path to the light brown square number image files.</li>
 *     <li>{@code pathD2} - Path to the dark brown square number image files.</li>
 *     <li>{@code pathL2} - Path to the light brown square number image files.</li>
 *     <li>{@code pathD3} - Path to the dark brown square number image files.</li>
 *     <li>{@code pathL3} - Path to the light brown square number image files.</li>
 *     <li>{@code pathD4} - Path to the dark brown square number image files.</li>
 *     <li>{@code pathL4} - Path to the light brown square number image files.</li>
 *     <li>{@code pathD5} - Path to the dark brown square number image files.</li>
 *     <li>{@code pathL5} - Path to the light brown square number image files.</li>
 *     <li>{@code pathD6} - Path to the dark brown square number image files.</li>
 *     <li>{@code pathL6} - Path to the light brown square number image files.</li>
 *     <li>{@code pathD7} - Path to the dark brown square number image files.</li>
 *     <li>{@code pathL7} - Path to the light brown square number image files.</li>
 *     <li>{@code pathD8} - Path to the dark brown square number image files.</li>
 *     <li>{@code pathL8} - Path to the light brown square number image files.</li>
 *     <li>{@code pathII} - Path to the image file for the application frame icon.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This enum ensures that all paths are centralized and easily accessible
 * throughout the chess application, facilitating consistent image loading and management.
 * </p>
 *
 * @see Configuration#INSTANCE
 * @author SirPatschiii
 * @version 2024-06-26
 */
public enum Configuration {
    INSTANCE;

    // Constants for piece image paths
    public final String pathWP = "./gameFiles/images/pieces/WhitePawn200x200.png";
    public final String pathWN = "./gameFiles/images/pieces/WhiteKnight200x200.png";
    public final String pathWB = "./gameFiles/images/pieces/WhiteBishop200x200.png";
    public final String pathWR = "./gameFiles/images/pieces/WhiteRook200x200.png";
    public final String pathWQ = "./gameFiles/images/pieces/WhiteQueen200x200.png";
    public final String pathWK = "./gameFiles/images/pieces/WhiteKing200x200.png";
    public final String pathBP = "./gameFiles/images/pieces/BlackPawn200x200.png";
    public final String pathBN = "./gameFiles/images/pieces/BlackKnight200x200.png";
    public final String pathBB = "./gameFiles/images/pieces/BlackBishop200x200.png";
    public final String pathBR = "./gameFiles/images/pieces/BlackRook200x200.png";
    public final String pathBQ = "./gameFiles/images/pieces/BlackQueen200x200.png";
    public final String pathBK = "./gameFiles/images/pieces/BlackKing200x200.png";

    /**
     * Retrieves the path to the image file corresponding to the given piece abbreviation.
     *
     * @param pieceAbbreviation The abbreviation of the chess piece.
     * @return The path to the image file for the specified piece.
     * @throws RuntimeException if an illegal piece abbreviation is provided.
     */
    public String getImagePath(String pieceAbbreviation) {
        return switch (pieceAbbreviation) {
            case "WP" -> pathWP;
            case "WN" -> pathWN;
            case "WB" -> pathWB;
            case "WR" -> pathWR;
            case "WQ" -> pathWQ;
            case "WK" -> pathWK;
            case "BP" -> pathBP;
            case "BN" -> pathBN;
            case "BB" -> pathBB;
            case "BR" -> pathBR;
            case "BQ" -> pathBQ;
            case "BK" -> pathBK;
            default -> throw new RuntimeException("Illegal piece abbreviation!");
        };
    }

    // Constants for square name image paths
    public final String pathDA = "./gameFiles/images/squarenames/a-darkbrown.png";
    public final String pathLA = "./gameFiles/images/squarenames/a-lightbrown.png";
    public final String pathDB = "./gameFiles/images/squarenames/b-darkbrown.png";
    public final String pathLB = "./gameFiles/images/squarenames/b-lightbrown.png";
    public final String pathDC = "./gameFiles/images/squarenames/c-darkbrown.png";
    public final String pathLC = "./gameFiles/images/squarenames/c-lightbrown.png";
    public final String pathDD = "./gameFiles/images/squarenames/d-darkbrown.png";
    public final String pathLD = "./gameFiles/images/squarenames/d-lightbrown.png";
    public final String pathDE = "./gameFiles/images/squarenames/e-darkbrown.png";
    public final String pathLE = "./gameFiles/images/squarenames/e-lightbrown.png";
    public final String pathDF = "./gameFiles/images/squarenames/f-darkbrown.png";
    public final String pathLF = "./gameFiles/images/squarenames/f-lightbrown.png";
    public final String pathDG = "./gameFiles/images/squarenames/g-darkbrown.png";
    public final String pathLG = "./gameFiles/images/squarenames/g-lightbrown.png";
    public final String pathDH = "./gameFiles/images/squarenames/h-darkbrown.png";
    public final String pathLH = "./gameFiles/images/squarenames/h-lightbrown.png";

    /**
     * Retrieves the path to the image file corresponding to the given square name abbreviation.
     *
     * @param letterAbbreviation The abbreviation of the square name.
     * @return The path to the image file for the specified square name.
     * @throws RuntimeException if an illegal square name abbreviation is provided.
     */
    public String getLetterPath(String letterAbbreviation) {
        return switch (letterAbbreviation) {
            case "DA" -> pathDA;
            case "LA" -> pathLA;
            case "DB" -> pathDB;
            case "LB" -> pathLB;
            case "DC" -> pathDC;
            case "LC" -> pathLC;
            case "DD" -> pathDD;
            case "LD" -> pathLD;
            case "DE" -> pathDE;
            case "LE" -> pathLE;
            case "DF" -> pathDF;
            case "LF" -> pathLF;
            case "DG" -> pathDG;
            case "LG" -> pathLG;
            case "DH" -> pathDH;
            case "LH" -> pathLH;
            default -> throw new RuntimeException("Illegal letter abbreviation!");
        };
    }

    // Constants for square number image paths
    public final String pathD1 = "./gameFiles/images/squarenames/one-darkbrown.png";
    public final String pathL1 = "./gameFiles/images/squarenames/one-lightbrown.png";
    public final String pathD2 = "./gameFiles/images/squarenames/two-darkbrown.png";
    public final String pathL2 = "./gameFiles/images/squarenames/two-lightbrown.png";
    public final String pathD3 = "./gameFiles/images/squarenames/three-darkbrown.png";
    public final String pathL3 = "./gameFiles/images/squarenames/three-lightbrown.png";
    public final String pathD4 = "./gameFiles/images/squarenames/four-darkbrown.png";
    public final String pathL4 = "./gameFiles/images/squarenames/four-lightbrown.png";
    public final String pathD5 = "./gameFiles/images/squarenames/five-darkbrown.png";
    public final String pathL5 = "./gameFiles/images/squarenames/five-lightbrown.png";
    public final String pathD6 = "./gameFiles/images/squarenames/six-darkbrown.png";
    public final String pathL6 = "./gameFiles/images/squarenames/six-lightbrown.png";
    public final String pathD7 = "./gameFiles/images/squarenames/seven-darkbrown.png";
    public final String pathL7 = "./gameFiles/images/squarenames/seven-lightbrown.png";
    public final String pathD8 = "./gameFiles/images/squarenames/eight-darkbrown.png";
    public final String pathL8 = "./gameFiles/images/squarenames/eight-lightbrown.png";

    /**
     * Retrieves the path to the image file corresponding to the given square number abbreviation.
     *
     * @param numberAbbreviation The abbreviation of the square number.
     * @return The path to the image file for the specified square number.
     * @throws RuntimeException if an illegal square number abbreviation is provided.
     */
    public String getNumberPath(String numberAbbreviation) {
        return switch (numberAbbreviation) {
            case "D1" -> pathD1;
            case "L1" -> pathL1;
            case "D2" -> pathD2;
            case "L2" -> pathL2;
            case "D3" -> pathD3;
            case "L3" -> pathL3;
            case "D4" -> pathD4;
            case "L4" -> pathL4;
            case "D5" -> pathD5;
            case "L5" -> pathL5;
            case "D6" -> pathD6;
            case "L6" -> pathL6;
            case "D7" -> pathD7;
            case "L7" -> pathL7;
            case "D8" -> pathD8;
            case "L8" -> pathL8;
            default -> throw new RuntimeException("Illegal number abbreviation!");
        };
    }

    // Constant for application frame icon path
    public final String pathII = "./gameFiles/images/FrameIcon.png";
}
