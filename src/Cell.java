import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Random;

public class Cell{
    private boolean cell_empty;
    public final JButton jButton;
    private final int row;
    private final int column;
    Diploid diploid;

    public Cell(boolean cell_empty, JButton jButton, int row, int column){
        this.cell_empty = cell_empty;
        this.row = row;
        this.column = column;
        this.jButton = jButton;
    }

    public static Diploid diploidFromHaploids(Haploid haploidA, Haploid haploidB){
        char[] allelesA = haploidA.getRGB();
        char[] allelesB = haploidB.getRGB();
        int[] cols = new int[3];
        String[] newAlleles = new String[3];

        for (int i = 0; i<3; i++){
            newAlleles[i] = allelesA[i]+""+allelesB[i];
            boolean isA = new Random().nextBoolean();
            int v;
            if (isA){
                v = haploidA.getColorVals()[i];
            }
            else {
                v = haploidB.getColorVals()[i];
            }
            cols[i] = v;
        }
        return new Diploid(newAlleles[0], newAlleles[1], newAlleles[2], cols);

    }

    public static Haploid haploidFromDiploid(Diploid diploid){
        String[] cols = diploid.getRGB();
        char[] alleles = new char[3];
        for (int i = 0; i < 3; i++){
            boolean isZero = new Random().nextBoolean();
            String k = cols[i];
            if (isZero){
                alleles[i] = k.charAt(0);
            }
            else {
                alleles[i] = k.charAt(1);
            }
        }
        return new Haploid(alleles[0], alleles[1], alleles[2], diploid.getColorVals());
    }

    public Diploid makeDiploid(){
        char[] rgb = new char[]{'r', 'g', 'b'};
        int[] vals = new int[3];
        String[] alleles = new String[3];
        for (int i =0; i<3; i++){
            boolean upper_p0 = new Random().nextBoolean();
            boolean upper_p1 = new Random().nextBoolean();
            char p0 = rgb[i];
            char p1 = rgb[i];
            if (upper_p0){
                p0 = Character.toUpperCase(p0);
            }
            if (upper_p1){
                p1 = Character.toUpperCase(p1);
            }
            vals[i] = (int)(Math.random()*255);
            alleles[i] = p0 + "" + p1;
        }
        return new Diploid(alleles[0], alleles[1], alleles[2], vals);
    }

    public Color diploidToColour(){
        int[] colVals = diploid.getColorVals();
        String[] alleles = diploid.getRGB();
        for (int i = 0; i<3; i++){
            String k = alleles[i];
            char p0 = k.charAt(0);
            char p1 = k.charAt(1);
            if (Character.isLowerCase(p0) && Character.isLowerCase(p1)){
                colVals[i] = 0;
            }
            i++;
        }
        return new Color(colVals[0], colVals[1], colVals[2]);
    }

    public Haploid getHaploid() {
        return haploidFromDiploid(diploid);
    }

    public boolean isEmpty(){
        return cell_empty;
    }

    public void makeEmpty() {
        this.cell_empty = true;
        colorCell(Color.white, false);
    }

    public void makeFull(Haploid haploidA, Haploid haploidB) {
        this.cell_empty = false;
        if (haploidA == null && haploidB == null){
            diploid = makeDiploid();
        }
        else{
            diploid = diploidFromHaploids(haploidA, haploidB);
        }
        colorCell(diploidToColour(), false);
    }

    public int getRow() {return row;}

    public int getColumn() {
        return column;
    }

    public void colorCell(Color colour, boolean full){
        // colours the cell to a colour
        if (full){
            this.jButton.setBackground(colour);
        }
        this.jButton.setBorderPainted(true);
        this.jButton.setBorder(new LineBorder(colour));

    }

}
