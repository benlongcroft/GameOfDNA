import java.util.Arrays;

public class Haploid {
    public char getRed() {
        return red;
    }


    public char getGreen() {
        return this.green;
    }


    public char getBlue() {
        return blue;
    }


    public int[] getColorVals() {
        return colorVals;
    }

    public char[] getRGB(){
        return new char[]{this.red, this.green, this.blue};
    }


    private final char red;

    private final char green;

    private final char blue;

    private final int[] colorVals;

    @Override
    public String toString() {
        return "Haploid{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", colorVals=" + Arrays.toString(colorVals) +
                '}';
    }

    public Haploid(char r, char g, char b, int[] values){
        this.red = r;
        this.green = g;
        this.blue = b;
        this.colorVals = values;
    }

}
