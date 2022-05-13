/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 6, 13/5/22
 */
import java.io.File;//Allows file stuff
import java.io.FileWriter;//Allows the writing of files so saved.
import java.io.IOException;//Allows errors for files.
import java.util.Scanner;//Importing Scanner so keyboard inputs can be recorded.
public class Main
{
    // instance variables - replace the example below with your own
    public boolean[][] grid = 
        {{true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,true,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}};
    public boolean[][] lastGrid = 
        {{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,true,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}};
    final String SWAP_COMMAND="swap";
    final String END_COMMAND="end";
    final String START_COMMAND="start";
    final String STOP_COMMAND="stop";
    final String ONE_GEN_COMMAND="go";
    final String ONE_K_GEN_COMMAND="";
    final String UNDO_COMMAND="undo";
    final String RENDER_COMMAND="render";
    int gen=0;
    /**
     * Constructor for objects of class Main
     */
    public Main()
    {
        boolean running=true;
        //int gen=0;
        while (running){
            //Reads input
            Scanner scanner = new Scanner(System.in);//Set up the scanner
            System.out.println("What do you want to do?");
            String scannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
            boolean validCommand=false;//If none of the commands work, then this should tell the user
            //Loops if active
            boolean active=false;
            boolean generation=false;
            //If true, then it wont render at the end of this loop. Used for the generation loop so 
            boolean dontPrint=false;
            //Do generations command
            if ((scannerOutput.equals(START_COMMAND))){
                dontPrint=true;
                active=true;
                validCommand=true;
                System.out.println("Starting?");}
            if ((scannerOutput.equals(ONE_GEN_COMMAND))){
                dontPrint=true;
                generation=true;
                active=true;
                validCommand=true;
                System.out.println("Starting one gen?");
            doGen();}
            if ((scannerOutput.equals(ONE_K_GEN_COMMAND))){
                dontPrint=true;
                active=true;
                validCommand=true;
                System.out.println("Starting one k gen?");
            for(int i=0; i<1000;i++){
            doGen();
            }}
            /*      Code to display last generation
            System.out.println("Last Generation");
            for(int i=0;i<lastGrid.length;i++){
            String line="";
            for(int j=0;j<lastGrid[i].length;j++) {
            if(lastGrid[i][j]){
            line+="A "; 
            }else{
            line+="D ";
            }
            }
            System.out.println(line);
            }*/
            boolean endCommands=false;
            while(!endCommands){
                endCommands=true;
            }
            //end command
            if ((scannerOutput.equals(END_COMMAND))){
                running=false;
                validCommand=true;
                System.out.println("end?");}
            //swap command
            if ((scannerOutput.equals(SWAP_COMMAND))){
                validCommand=true;
                System.out.println("What do you wish to swap?");
                try{
                    String coordinates[]=scanner.nextLine().split(" ");//
                    if(grid[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]){
                        grid[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=false;
                    }else{
                        grid[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=true;
                    }
                }
                catch(Exception e){
                    //e.printStackTrace();
                    System.out.println("Thats outside of this world!");
                }
            }
            
            //undo command
            if ((scannerOutput.equals(UNDO_COMMAND))){
                validCommand=true;
                if(!(grid==lastGrid)){
                grid=lastGrid;
                System.out.println("undone?");
                gen--;
            }
            else{
                System.out.println("Cannot undo");
            }
            if ((scannerOutput.equals(RENDER_COMMAND))){
                validCommand=true;
            }
            if(!validCommand){
                dontPrint=true;
                System.out.println("You can't this!");
            }
            } 
            //Printing cells
            if(!dontPrint){
                renderArray(grid);
            }
        }
    }
    public void renderArray(boolean array[][]){
        for(int i=0;i<array.length;i++){
                    String line="";
                    for(int j=0;j<array[i].length;j++) {
                        if(grid[i][j]){
                            line+="O "; 
                        }else{
                            line+="  ";
                        }
                    }
                    System.out.println(line);
                }
    }
    public void doGen(){
                lastGrid=grid;
                int[][] test={{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
                for(int i=0;i<grid.length;i++){
                    for(int j=0;j<grid[i].length;j++) {
                        int jChange=j+1;
                        int iChange=i+1;
                        int nextToCells=0;
                        if (jChange==grid[i].length){
                            
                            jChange=0; 
                        }
                        if(iChange==grid.length){
                            iChange=0;
                            
                        }
                        nextToCells+=testCell(iChange,jChange);
                        nextToCells+=testCell(i,jChange);
                        iChange=i-1;
                        if(iChange==-1){
                            iChange=grid.length-1; 
                        }
                        nextToCells+=testCell(iChange,jChange);
                        nextToCells+=testCell(iChange,j);
                        jChange=j-1;
                        if(jChange==-1){
                            jChange=grid[i].length-1; 
                        }
                        nextToCells+=testCell(iChange,jChange);
                        nextToCells+=testCell(i,jChange);
                        iChange=i+1;
                        if (iChange==grid.length){
                            iChange=0; 
                            
                        }
                        nextToCells+=testCell(iChange,jChange);
                        nextToCells+=testCell(iChange,j);
                        //
                        if(nextToCells==3){
                            grid[i][j]=true;
                        }else if(nextToCells==2&&grid[i][j]){
                            grid[i][j]=true;
                        }else{
                            grid[i][j]=false;
                        }
                        test[i][j]=nextToCells;
                    }
                }
                gen++;
                System.out.println("Generation "+gen);
                renderArray(grid);
                ///*
                System.out.println("Pot for "+gen);
                for(int i=0;i<test.length;i++){
                    String line="";
                    for(int j=0;j<test[i].length;j++) {                  //Shows how many alive tiles adjacent
                        if(grid[i][j]&&lastGrid[i][j]){
                        line+=test[i][j]+"A";
                        }else if(lastGrid[i][j]){line+=test[i][j]+"D";}
                        else if(grid[i][j]){line+=test[i][j]+"R";}
                        else{line+=test[i][j]+" ";}
                    }
                    System.out.println(line);
                }
                System.out.println("End of pot for "+gen);//*/
    }
    public int testCell(int y, int x){
        if (lastGrid[y][x]){
        return 1;}
        else{
        return 0;
        }
    }
}
