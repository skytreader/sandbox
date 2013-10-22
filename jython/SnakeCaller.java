import org.python.util.PythonInterpreter;

public class SnakeCaller{
    public static void main(String[] args) throws Exception{
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("pyscript.py");
    }
}
