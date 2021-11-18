import java.util.ArrayList;
import java.util.Arrays;

public class Automata {
    int boardSize;
    double reproductionChance = 0.75;
    public Automata(int boardSize){
        this.boardSize = boardSize;
    }

    private ArrayList<Cell> countNeighbours(Cell[][] grid, int row, int column){
        int[][] dirs = new int[][] {{-1,-1}, {-1,0}, {-1,1},
                {0,-1}, {0,1}, {1,-1},
                {1,0}, {1,1}};
        ArrayList<Cell> neighbours = new ArrayList<>();
        for (int[] d : dirs){
            int rPos = row + d[0];
            int cPos = column + d[1];
            if ((rPos < this.boardSize && rPos > 0) && (cPos < this.boardSize && cPos > 0)){
                if (!grid[rPos][cPos].isEmpty()){
                    neighbours.add(grid[rPos][cPos]);
                }
            }
        }
        return neighbours;
    }

    public static double cosineSimilarity(int[] vectorA, int[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }


    private int[] reproduce(Cell[][]grid, ArrayList<Cell> neighbours, int row, int column){
        Cell cell = grid[row][column];

        double max = 0;
        Cell neighbour = null;
        for (Cell n : neighbours){
            double sim = cosineSimilarity(cell.diploid.getColorVals(), n.diploid.getColorVals());
            if (sim > max && !n.isEmpty()){
                max = sim;
                neighbour = n;
            }
        }
        if (reproductionChance < max && neighbour != null){
            return new int[]{neighbour.getRow(), neighbour.getColumn()};
        }
        return  new int[]{-1, -1};
    }

    private ArrayList<int[]> getEmptyNearbyCells(Cell[][] grid, int row, int column){
        int[][] dirs = new int[][] {{-1,-1}, {-1,0}, {-1,1},
                {0,-1}, {0,1}, {1,-1},
                {1,0}, {1,1}};
        ArrayList<int[]> nearbyEmpties = new ArrayList<>();
        for (int[] d : dirs){
            int rPos = row + d[0];
            int cPos = column + d[1];
            if ((rPos < this.boardSize && rPos > 0) && (cPos < this.boardSize && cPos > 0)){
                if (grid[rPos][cPos].isEmpty()){
                    nearbyEmpties.add(new int[]{rPos, cPos});
                }
            }
        }
        return nearbyEmpties;
    }

    private int countRecessive(char[] rgbA, char[] rgbB){
        int count = 0;
        for (int i = 0; i<3; i++){
            char a = rgbA[i];
            char b = rgbB[i];
            if (Character.isLowerCase(a)){
                count++;
            }

            if (Character.isLowerCase(b)){
                count++;
            }
        }
        return count;
    }

    public void makeNextGen(Cell[][] grid) {
        ArrayList<Cell> toEmpty = new ArrayList<>();
        ArrayList<Cell> toFill = new ArrayList<>();
        int r_count = 0;
        for (int row=0; row<this.boardSize; row++) {
            for (int column = 0; column < this.boardSize; column++) {
                if (!grid[row][column].isEmpty()) {
                    ArrayList<Cell> neighbours = countNeighbours(grid, row, column);
                    int count = neighbours.size();
                    if (count > 3) {
                        Cell cell = grid[row][column];
                        cell.makeEmpty();
                        toEmpty.add(cell);
                    } else if (count < 2) {
                        Cell cell = grid[row][column];
                        cell.makeEmpty();
                        toEmpty.add(cell);
                    } else{
                        int[] parentCoordinates = reproduce(grid, neighbours, row, column);
                        if (!Arrays.equals(parentCoordinates, new int[]{-1, -1})) {
                            ArrayList<int[]> nearbyEmptyCells = getEmptyNearbyCells(grid, parentCoordinates[0], parentCoordinates[1]);
                            for (int[] childCoordinates : nearbyEmptyCells) {
                                ArrayList<Cell> childNeighbours = countNeighbours(grid, childCoordinates[0], childCoordinates[1]);
                                int childNeighbourCount = childNeighbours.size();
                                if (childNeighbourCount == 3) {
                                    //System.out.println("("+parentCoordinates[0]+" ,"+parentCoordinates[1]+") and ("+row+" ,"+column+") Reproduces to ("+childCoordinates[0]+" ,"+childCoordinates[1]+")");
                                    Cell pCell = grid[childCoordinates[0]][childCoordinates[1]];
                                    Cell nCell = new Cell(false, pCell.jButton, childCoordinates[0], childCoordinates[1]);
                                    Haploid haploidA = grid[parentCoordinates[0]][parentCoordinates[1]].getHaploid();
                                    Haploid haploidB = grid[row][column].getHaploid();
                                    r_count += countRecessive(haploidA.getRGB(), haploidB.getRGB());
                                    nCell.makeFull(haploidA, haploidB);
                                    toFill.add(nCell);
                                }
                            }
                        }
                    }
                }
            }
        }
//        System.out.println(toEmpty.size()+" cells to die");
        for (Cell cell : toEmpty){
            grid[cell.getRow()][cell.getColumn()] = cell;
        }
//        System.out.println(toFill.size()+" cells to reproduce");
        for (Cell cell : toFill){
            grid[cell.getRow()][cell.getColumn()] = cell;
        }
        System.out.println(r_count);
    }
}
