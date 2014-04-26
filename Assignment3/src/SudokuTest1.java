public class SudokuTest1 {

    public static void randomTest(int rows, int cols) {
        // create a board of the given layout
        SudokuBoard board = new SudokuBoard(rows, cols);
        // put random values into the board
        for(int i = 0; i < board.size*board.size; i++) {
            int row = (int)(Math.random() * board.size);
            int col = (int)(Math.random() * board.size);
            int value = (int)(Math.random() * (board.size + 1));
            board.setValue(row, col, value);
        }

        // print out the board
        System.out.println("Random board: " + rows + "x" + cols);
        System.out.println(board.toString());
        // report state
        for(int i = 0; i < board.size; i++)
            System.out.println("Row " + i + " state: " + board.getRowState(i));
        for(int i = 0; i < board.size; i++)
            System.out.println("Column " + i + " state: " + board.getColumnState(i));
        for(int i = 0; i < board.size; i++)
            System.out.println("Region " + i + " state: " + board.getRegionState(i));
    }

    public static void main(String[] args) {
        randomTest(2,2);
        randomTest(2,3);
        randomTest(3,3);
    }
}