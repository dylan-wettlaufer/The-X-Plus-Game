/* This class represents configurations for the x+ game. each configuration contains a length to win and a game board */
public class Configurations {
    private int lengthToWin; /* the length of the x or + need to win the game */
    private char[][] board; /* the board that the game is played on */

    /* Constructor: initializes the length to win and creates the board  with each index containing an empty space */
    public Configurations(int board_size, int lengthToWin, int max_levels) {
        board = new char[board_size][board_size]; // creates the board size using the parameter board_size
        this.lengthToWin = lengthToWin;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /* Creates a hash dictionary */
    public HashDictionary createDictionary() {
        return new HashDictionary(9719);
    }

    /* Checks for repeated configurations. If a configuration is repeated, the Data's score is returned. Otherwise, -1 is returned */
    public int repeatedConfiguration(HashDictionary hashTable) {
        String boardStr = "";
        for (int i = 0; i < board.length; i++) { // loops through each index in the board
            for (int j = 0; j < board[i].length; j++) {
                boardStr = boardStr + board[i][j]; // adds each character in the board to a string
            }
        }
        return hashTable.get(boardStr); // uses the created string to check if it is the given hash table
    }

    /* Bulids a string using the game board and adds the string and the given score to the hash dictionary */
    public void addConfiguration(HashDictionary hashDictionary, int score) {
        String boardStr = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boardStr = boardStr + board[i][j];
            }
        }
        Data data = new Data(boardStr, score);
        hashDictionary.put(data);
    }

    /* updates the play on the board with the given rwo, col, and symbol */
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }
    /* Checks if the given square is empty, if it is tue is returned, if not, false is returned */
    public boolean squareIsEmpty (int row, int col) {
        if (board[row][col] == ' ') return true;
        else return false;
    }

    /* Checks if a player has won the game by looking for an X shape or a + shape with the needed length to win the game
    * It checks by treating each symbol as the centrepiece and checking if there are symbols around it that form an x or + shape*/
    public boolean wins (char symbol) {
        int rows = board.length;
        int cols = board[0].length;
        int numOfSymbol = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) { // two for loops to loop through the game board
                if (board[i][j] == symbol) { // checks if the index contains the given symbol
                    numOfSymbol++;
                    if (i != 0 && i != rows - 1 && j != 0 && j != cols - 1) { // no centrepiece can be on the edges of the game board so if the symbol is not on the edge the statement is true
                        if (board[i - 1][j - 1] == symbol && board[i - 1][j + 1] == symbol && board[i + 1][j - 1] == symbol && board[i + 1][j + 1] == symbol) { // checks if there are symbols at each corner so that an x shape is formed
                            // calls the getXLength method to calculate the score of x shape
                            numOfSymbol = numOfSymbol + getXLength(i-1,j-1,'-','-', symbol) + getXLength(i-1, j+1, '-', '+', symbol) +
                                    getXLength(i+1, j-1, '+', '-', symbol) + getXLength(i+1, j+1, '+', '+', symbol);

                            if (numOfSymbol >= lengthToWin) return true; // if the x shape contains enough of the given symbol true is returned
                            else numOfSymbol = 1; // if not, the num of symbol is set back to 1 to check for a plus shape
                        }
                        if (board[i][j-1] == symbol && board[i][j + 1] == symbol && board[i + 1][j] == symbol && board[i-1][j] == symbol) { // checks if there are symbols around the centerpiece that for a plus
                            // calls the getPlusLength method to calculate the score of the + shape
                            numOfSymbol = numOfSymbol + getPlusLength(i, j-1, ' ', '-', symbol) + getPlusLength(i, j+1, ' ', '+', symbol) +
                                    getPlusLength(i+1, j, '+', ' ',symbol) + getPlusLength(i-1, j, '-', ' ', symbol);

                            if (numOfSymbol >= lengthToWin) return true; // if the + shape contains enough of the given symbol true is returned
                        }
                    }
                    numOfSymbol = 0; // set back to zero for the next loop
                }
            }
        }
        return false; // if no shape of given length has been found, false is returned
    }

    /* Checks for a draw by looking for any blank spaces on the game board, if a blank space is found false is returned */
    public boolean isDraw() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true; // no blank spaces so the game is a draw and true is returned
    }

    /* checks for human wins, computer wins, draws, and undecided matches */
    public int evalBoard() {
        if (wins('O')) return 3;
        else if (wins('X')) return 0;
        else if (isDraw()) return 2;
        else return 1;
    }

    /* method to get the length of the x shape */
    private  int getXLength(int rows, int cols, char rowSymbol, char colSymbol, char symbol) {
        int numOfSymbol = 0;
        char pos = board[rows][cols];
        int rowSize = board.length;
        int colSize = board[0].length;
        boolean hitWall = false;

        while (pos == symbol && !hitWall) { // loops until a wall is hit or there are no more symbols in the diagonal
            numOfSymbol++;
            if (rowSymbol == '-' && colSymbol == '-') { // loops in the direction of up and left for the x shape
                rows--;
                cols--;
                if (rows < 0 || cols < 0) hitWall = true; // if it's less than zero, a wall has been hit and the loop will end
                else pos = board[rows][cols]; // uses the updated rows and cols to get the next position in the x shape
            }
            else if (rowSymbol == '-' && colSymbol == '+') { // does the same as the last if statement except now for a different corner of the x shape
                rows--;
                cols++;
                if (rows < 0 || cols > colSize-1) hitWall = true;
                else pos = board[rows][cols];
            }
            else if (rowSymbol == '+' && colSymbol == '-') {
                rows++;
                cols--;
                if (rows > rowSize-1 || cols < 0) hitWall = true;
                else pos = board[rows][cols];
            }
            else if (rowSymbol == '+' && colSymbol == '+') {
                rows++;
                cols++;
                if (rows > rowSize-1 || cols > colSize-1) hitWall = true;
                else pos = board[rows][cols];
            }
        }
        return numOfSymbol; // returns the number of symbols in the x shape
    }

    /* Get the length of the plus shape */
    private  int getPlusLength(int rows, int cols, char rowSymbol, char colSymbol, char symbol) {
        int numOfSymbol = 0;
        char pos = board[rows][cols];
        int rowSize = board.length;
        int colSize = board[0].length;
        boolean hitWall = false;

        while (pos == symbol && !hitWall) { // loops until a wall is hit ot until there are no more symbols in the + shape
            numOfSymbol++;
            if (rowSymbol == ' ' && colSymbol == '-') { // loops to the left of the centerpiece
                cols--;
                if (cols < 0) hitWall = true; // if it's less than zero, a wall has been hit and the loop will end
                else pos = board[rows][cols]; // uses the updated rows and cols to get the next position in the + shape
            }
            else if (rowSymbol == ' ' && colSymbol == '+') { // does the same as the last if statement except now for a different side of the + shape
                cols++;
                if (cols > colSize-1) hitWall = true;
                else pos = board[rows][cols];
            }
            else if (rowSymbol == '-' && colSymbol == ' ') {
                rows--;
                if (rows < 0) hitWall = true;
                else pos = board[rows][cols];
            }
            else if (rowSymbol == '+' && colSymbol == ' ') {
                rows++;
                if (rows > rowSize-1) hitWall = true;
                else pos = board[rows][cols];
            }
        }
        return numOfSymbol;
    }


}
