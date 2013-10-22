import java.io.FileInputStream;

import org.python.util.PythonInterpreter;

public class SnakeCaller{
    public static void main(String[] args) throws Exception{
        PythonInterpreter interpreter = new PythonInterpreter();
        FileInputStream pythonSource = new FileInputStream("pyscript.py");
        interpreter.execfile(pythonSource);

        pythonSource.close();
    }
}
