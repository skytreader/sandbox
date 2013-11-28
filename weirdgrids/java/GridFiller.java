import java.util.Random;

public class GridFiller{
    private static char[][] canvas;
    private static boolean[] flags;
    private static Random randomizer = new Random();

    private static final int[] BINARY = {0, 1};

    private static class Artist implements Runnable{
        private int row;
        private int duration;

        public Artist(int row, int duration){
            this.row = row;
            this.duration = duration;
        }

        private synchronized void fillGrid(){
            int randomIndex = randomizer.nextInt(canvas[row].length);
            int randomBit = randomizer.nextInt(2);

            canvas[row][randomIndex] = BINARY[randomBit];
        }
    
        @Override
        public void run(){
            for(int i = 0; i < duration; i++){
                fillGrid();

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException ie){
                    System.err.println("InterruptedException occurred.");
                }
            }
        }
    }

    private static class Animator implements Runnable{
        @Override
        public void run(){
            // TODO
        }
    }

    private static String gridObject(){
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < canvas.length; i++){
            builder.append(canvas[i]);
        }

        return builder.toString();
    }

    public static void main(String[] args) throws Exception{
        if(args.length != 3){
            System.out.println("Usage: java GridFiller <grid width> <grid height> <duration>");
            System.exit(1);
        }

        int gridWidth = Integer.parseInt(args[0]);
        int gridHeight = Integer.parseInt(args[1]);
        int duration = Integer.parseInt(args[2]);

        canvas = new char[gridHeight][gridWidth];

        for(int row = 0; row < gridHeight; row++){
            for(int col = 0; col < gridWidth; col++){
                canvas[row][col] = '0';
            }
        }

        flags = new boolean[gridHeight];

        for(int i = 0; i < gridHeight; i++){
            Thread t = new Artist(i, duration);
            t.start();
        }

        Thread t = new Animator();
        t.start();
    }
}
