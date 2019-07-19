package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    private Canvas canvas;
    private static GraphicsContext gc;
    static int row = 20;
    static int collumn = 20;
    static int r = 400 / row;
    static int c = 400 / collumn;
    Grid[][] allGrids;


    List<Grid> openSet;
    List<Grid> closedSet;

    Grid start;
    Grid end;


    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(400, 400);
        Group group = new Group(canvas);

        openSet=new ArrayList<>();
        closedSet=new ArrayList<>();
        allGrids=new Grid[row][collumn];

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        //gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);


        for (int i = 0; i < collumn; i++) {
            for (int j = 0; j < row; j++) {
                allGrids[i][j]=new Grid(i,j);
            }
        }

        start=allGrids[0][0];
        end=allGrids[collumn-1][row-1];

        for (int i = 0; i < collumn; i++) {
            for (int j = 0; j < row; j++) {
                allGrids[i][j].addNeighbor(allGrids);
            }
        }


        openSet.add(start);

        at.start();

        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            at.stop();
        });
    }

    public static GraphicsContext getGc() {
        return gc;
    }

    public Set<Grid> reconstructPath(Grid current){
        Set<Grid> totalPath=new HashSet<>();
        /*while(current!=null){
            totalPath.add(current);
            current=current.cameFrom;
        }
        return totalPath;*/
        totalPath.add(current);
        if(current.getCameFrom()!=null)
            totalPath.addAll(reconstructPath(current.getCameFrom()));

        return totalPath;
    }


    AnimationTimer at=new AnimationTimer() {

        @Override
        public void handle(long now) {
            Grid current=null;
            if(openSet.size()>0){
                int low=0;
                for(int i=0;i<openSet.size();i++){
                    if(openSet.get(low).f > openSet.get(i).f){
                        low=i;
                    }
                }
                current=openSet.get(low);
                System.out.println(current);
                if(current==end){
                    System.out.println("DONE!");
                    at.stop();
                }

                openSet.remove(current);
                closedSet.add(current);

                for(Grid grid:current.neighbor){
                    if(closedSet.contains(grid) || grid.wall){
                        if(grid.wall){
                            System.out.println();
                        }
                        continue;
                    }
                    double tempG=current.g+1;
                    boolean newPath=false;
                    if(openSet.contains(grid)){
                        if(grid.g > tempG) {
                            grid.g = tempG;
                            newPath=true;
                        }
                    }else{
                        newPath=true;
                        grid.g=tempG;
                        openSet.add(grid);
                    }
                    if(newPath) {
                        grid.h = grid.heristic(end);
                        grid.f = grid.g + grid.h;
                        grid.setCameFrom(current);
                        current = grid;
                    }
                }

            }else{
                System.out.println("No solution");
                at.stop();
                return;
            }

            for (int i = 0; i < collumn; i++) {
                for (int j = 0; j < row; j++) {
                    allGrids[i][j].show(Color.WHITE);
                }
            }

            for(Grid grid:openSet){
                grid.show(Color.BLUE);
            }

            for(Grid grid:closedSet){
                grid.show(Color.RED);
            }

            for(Grid grid:reconstructPath(current)){
                grid.show(Color.GREEN);
            }


            System.out.println(openSet.size());
        }
    };

    public static void main(String[] args) {
        launch(args);

    }

}
