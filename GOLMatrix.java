public class GOLMatrix {
    /*Question 1*/
    boolean[][] world;
    int generations;

    public GOLMatrix(int n) {
        if (n<3)
            world=new boolean[3][3];
        else {
            world=new boolean[n][n];
            for (int i=0 ; i<world.length ; i++)
                for (int j=0 ; j<world.length ; j++)
                    world[i][j]=false;
        }
        generations=0;
    }

    public void flipCell(int row, int col) {
        world[row][col]=!world[row][col];
    }

    public boolean[][] getWorld() {
        return world;
    }

    public int getGenerations() {
        return generations;
    }

    public void clearWorld() {
        for (int i=0 ; i<world.length ; i++) {
            for (int j=0 ; j<world.length ; j++) {
                world[i][j]=false;
            }
        }
        generations=0;
    }

    public void nextGeneration() {

        int length=world.length;
        boolean[][] worldHelper=new boolean[length][length];
        for (int row=0 ; row<length ; row++) {
            for (int col=0 ; col<length ; col++) {
                int liveNeighbors=liveNeighborsCounter( row, col );

                //implementing the game rules
                if (world[row][col]) { //there is live cells
                    if (liveNeighbors<2 || liveNeighbors>3)
                        worldHelper[row][col]=false;
                    else //living neighbors=2 or =3
                        worldHelper[row][col]=true;

                }else { //the cells are dead
                    if (liveNeighbors==3) //if the neighbors are exactly 3 then it will live
                        worldHelper[row][col]=true;
                    else //stay dead
                        worldHelper[row][col]=false;
                }

            }
        }
        world=worldHelper;
        generations++;
    }

    //helping method to count the neighbors
    private int liveNeighborsCounter(int row, int col) {
        int liveNeighbors=0,
                rows=world.length,
                cols=world[0].length;

        //top left
        if (row-1>=0 && col-1>=0 && world[row-1][col-1])
            liveNeighbors++;

        //top
        if (row-1>=0 && world[row-1][col])
            liveNeighbors++;

        //top right
        if (row-1>=0 && col+1<cols && world[row-1][col+1])
            liveNeighbors++;

        //left
        if (col-1>=0 && world[row][col-1])
            liveNeighbors++;

        //right
        if (col+1<cols && world[row][col+1])
            liveNeighbors++;

        //bottom left
        if (row+1<rows && col-1>=0 && world[row+1][col-1])
            liveNeighbors++;

        //bottom
        if (row+1<rows && world[row+1][col])
            liveNeighbors++;


        //bottom right
        if (row+1<rows && col+1<cols && world[row+1][col+1])
            liveNeighbors++;


        return liveNeighbors;
    }

}
