import java.io.FileInputStream;

import org.python.util.PythonInterpreter;

public class SnakeCaller{
    public static void main(String[] args) throws Exception{
        PythonInterpreter interpreter = new PythonInterpreter();
        //BufferedReader pythonSource = new BufferedReader(new InputStreamReader(new FileInputStream("pyscript.py")));
        interpreter.execfile(new FileInputStream("pyscript.py"));

        //pythonSource.close();
    }
}
