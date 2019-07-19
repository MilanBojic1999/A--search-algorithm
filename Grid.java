package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int i;
    private int j;

    double f;
    double g;
    double h;
    
    private GraphicsContext gc;
    
    List<Grid> neighbor;
    boolean wall;
    Grid cameFrom;

    public Grid(int x, int y) {
        gc=Main.getGc();
        this.i = x;
        this.j = y;
        wall = false;
        neighbor=new ArrayList<>();
        wall = (Math.random() < 0.3);
        if((i==0 && j==0) || (i==(Main.collumn-1) && j==(Main.row-1)))
            wall=false;
        g=h=f=0;
        cameFrom=null;

    }

    public Grid getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(Grid cameFrom) {
        this.cameFrom = cameFrom;
    }

    void addNeighbor(Grid[][] allGrids){
        if(i>0){
            neighbor.add(allGrids[i-1][j]);
        }

        if(j>0){
            neighbor.add(allGrids[i][j-1]);
        }

        if(i<(Main.collumn-1)){
            neighbor.add(allGrids[i+1][j]);
        }

        if(j<(Main.row-1)){
            neighbor.add(allGrids[i][j+1]);
        }

        if(i>0 && j>0){
            neighbor.add(allGrids[i-1][j-1]);
        }

        if(i<Main.collumn-1 && j<Main.row-1){
            neighbor.add(allGrids[i+1][j+1]);
        }

        if(i>0 && j<Main.row-1){
            neighbor.add(allGrids[i-1][j+1]);
        }

        if(i<Main.collumn-1 && j>0){
            neighbor.add(allGrids[i+1][j-1]);
        }
    }

    public void show(Color color){
        gc.setFill(color);
        if (wall) {
            gc.setFill(Color.BLACK);
        }

        //gc.strokeRect(i * Main.r, j * Main.c, Main.r-1, Main.c-1);
        gc.fillRect(i * Main.r, j * Main.c, Main.r-1, Main.c-1);
        //gc.fillOval(i * Main.r, j * Main.c, Main.r-1, Main.c-1);
    }

    void showNeighbor(){
        for(Grid grid:neighbor){
            System.out.println("\t"+grid);
        }
    }

    @Override
    public String toString() {
        return "i:"+i+" j:"+j+", "+((wall) ? "wall":"not wall");
    }

    public double heristic(Grid end){
        return Math.sqrt(Math.pow(end.i-this.i,2)+Math.pow(end.j-this.j,2));
    }
}
