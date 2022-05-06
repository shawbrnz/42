
/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 2, 5/5/22
 */
public class Main
{
    // instance variables - replace the example below with your own
    public boolean[][] grid = 
        {{true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,true},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,true},
        {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,true},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,true}};
    public boolean[][] lastGrid = 
        {{true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,true},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,true},
        {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,true},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,true}};
    /**
     * Constructor for objects of class Main
     */
    public Main()
    {
        System.out.println("SOC");
        boolean running=true;
        int gen=0;
        while (running){
            System.out.println("SOL");
            lastGrid=grid;
            //Modifying cells
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[i].length;j++) {
                    int jChange;
                    int iChange;
                    if (j==grid[i].length-1){
                        jChange=-1;
                    }else{
                        jChange=1;
                    }
                    if (i==grid.length-1){
                        iChange=-1;
                    }else{
                        iChange=1;
                    }
                    if(lastGrid[i+iChange][j+jChange]){
                        grid[i][j]=false; 
                    }else{
                        grid[i][j]=true; 
                    }
                }
            }
            //Printing cells
            for(int i=0;i<grid.length;i++){
                String line="";
                for(int j=0;j<grid[i].length;j++) {
                    if(grid[i][j]){
                        line+="A "; 
                    }else{
                        line+="D ";
                    }
                }
                System.out.println(line);
            }
            /*      Code to display last generation
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
            if(gen>10){
                running=false;
            }
            gen++;
        }
        System.out.println("EOC");
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return y;
    }
}
