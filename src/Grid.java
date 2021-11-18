import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Grid extends JFrame{
    int boardSize;
    ArrayList<int[]> live_cells;
    Cell[][] grid;
    Automata game;

    public Grid(int board_size){
        this.boardSize = board_size;
        setTitle("High Life");
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        this.grid = buildGrid();
        this.live_cells = new ArrayList<>();
        this.game = new Automata(this.boardSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

    }

    private void randomise(){
        clear();
        for (int i=0; i<this.boardSize; i++){
            for (int j=0; j<this.boardSize; j++){
                Random rd = new Random();
                if (rd.nextBoolean()){
                    this.grid[i][j].makeFull(null, null);
                }
            }
        }
    }

    private void startPosition(){
        for (int[] coord : this.live_cells){
            this.grid[coord[0]][coord[1]].makeFull(null, null);
        }
    }

    private void reset(){
        for (int i=0; i<this.boardSize; i++){
            for (int j=0; j<this.boardSize; j++){
                this.grid[i][j].makeEmpty();
            }
        }
        startPosition();
    }

    private void clear(){
        for (int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++){
                grid[i][j].makeEmpty();
            }
        }
    }

    private void makeStartPosition(int row, int column){
        this.grid[row][column].makeFull(null, null);
        this.live_cells.add(new int[]{row, column});
    }


    private Cell[][] buildGrid(){
        //builds the grid on set up

        JPanel Grid = new JPanel();
        Grid.setLayout(new GridLayout(this.boardSize, this.boardSize));
        Grid.setBackground(Color.white);
        this.add(Grid);

        grid = new Cell[this.boardSize][this.boardSize];

        for (int i=0; i<this.boardSize; i++){
            for (int j=0; j<this.boardSize; j++){
                JButton button = setUpElement(Grid, i, j);
                Cell tempCell = new Cell(true, button, i, j);
                grid[i][j] = tempCell;
            }
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JButton playButton = new JButton("Play");
        Timer timer = new Timer(100, e -> {
            playButton.setEnabled(false);
            game.makeNextGen(grid);
        });
        playButton.addActionListener(e -> timer.start());

        JButton nextGenButton = new JButton("Next Gen");
        nextGenButton.addActionListener(e -> {
            game.makeNextGen(grid);
        });

        JButton randomButton = new JButton("Randomise");
        randomButton.addActionListener(e -> {
            timer.stop();
            playButton.setEnabled(true);
            randomise();
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            timer.stop();
            playButton.setEnabled(true);
            reset();
        });
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            timer.stop();
            playButton.setEnabled(true);
            clear();
        });

        panel.add(resetButton);
        panel.add(playButton);
        panel.add(clearButton);
        panel.add(randomButton);
        panel.add(nextGenButton);
        this.add(panel);
        return grid;
    }

    private JButton setUpElement(JPanel Grid, int row, int column){

        JButton button = new JButton();
        button.addActionListener(e -> makeStartPosition(row, column));
        button.setPreferredSize(new Dimension(10,10));
        button.setBackground(Color.white);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setOpaque(true);
        Grid.add(button);
        return button;
    }
}
