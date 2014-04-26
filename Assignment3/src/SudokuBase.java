public abstract class SudokuBase {
   
   public final int rows;
   public final int columns;
   public final int size;
   private final int[] grid;
   
   private static final int GIVEN_MASK = 0x00000100;
   private static final int GIVEN_UNMASK = ~ GIVEN_MASK;
   
   public enum State {COMPLETE, INCOMPLETE, ERROR};
   
   public SudokuBase(int layoutRows, int layoutColumns) {
      rows = layoutRows;
      columns = layoutColumns;
      size = columns * rows;
      grid = new int[size*size];
   }
   
   private int getIndex(int row, int col) {
      if(row < 0 || row >= size || col < 0 || col >= size) {
         String msg = "Error in location";
         throw new IllegalArgumentException(msg);
      }
      return row * size + col;
   }
   
   public int getValue(int row, int col) {
      return grid[getIndex(row, col)] & GIVEN_UNMASK;
   }
   public void setValue(int row, int col, int value) {
      if(value < 0 || value > size) {
         String msg = "Value out of range: " + value;
         throw new IllegalArgumentException(msg);
      }
      if(isGiven(row, col)) {
         String msg = "Cannot set given location: " + row + ", " + col;
         throw new IllegalStateException(msg);
      }
      grid[getIndex(row, col)] = value;
   }
   
   public boolean isGiven(int row, int col) {
      return (grid[getIndex(row, col)] & GIVEN_MASK) == GIVEN_MASK;
   }
   public void fixGivens() {
      for(int i = 0; i < grid.length; i++)
         if(grid[i] != 0) 
            grid[i] |= GIVEN_MASK;
   }
   
   public abstract State getRowState(int n);
   public abstract State getColumnState(int n);
   public abstract State getRegionState(int n);
   
   public String toString() {
      String board = "";
      for(int i = 0; i < size; i ++) {
         for(int j = 0; j < size; j ++)
            board += charFor(i, j) + " ";
         board += "\n";
      }
      return board;
   }

   private String charFor(int i, int j) {
      int v = getValue(i, j);
      if(v < 0) {
         return "?";
      } else if(v == 0) {
         return " ";
      } else if(v < 36) {
         return Character.toString(Character.forDigit(v, 36)).toUpperCase();
      } else {
         return "?";
      }
   }

   protected void readFromStream(java.io.InputStream is) {
   }
   protected void writeToStream(java.io.OutputStream os) {
   }
   protected int getRawValue(int row, int col) {
      return grid[getIndex(row, col)];
   }
   protected void setRawValue(int row, int col, int value) {
      grid[getIndex(row, col)] = value;
   }
}
