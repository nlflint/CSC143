import java.io.*;

/**
 * Created by nate on 5/24/14.
 */
public class SudokuSerializer {

    public OutputStream serializeBoard(SudokuBase base, OutputStream outputStream)
        throws FileNotFoundException, IOException{


        // Write width and height header
        DataOutputStream dataOut = new DataOutputStream(outputStream);
        dataOut.writeInt(base.rows);
        dataOut.writeInt(base.columns);

        // Let the board write its own rows
        base.writeToStream(outputStream);

        return  outputStream;
    }
}
