import java.util.Arrays;

public class Diploid {
    public String getRed() {
        return red;
    }


    public String getGreen() {
        return green;
    }


    public String getBlue() {
        return blue;
    }

    public String[] getRGB(){
        return new String[]{this.red, this.blue, this.green};
    }

    public int[] getColorVals() {
        return colorVals;
    }


    private final String red;
    private final String green;
    private final String blue;
    private final int[] colorVals;

    @Override
    public String toString() {
        return "Diploid{" +
                "red='" + red + '\'' +
                ", green='" + green + '\'' +
                ", blue='" + blue + '\'' +
                ", colorVals=" + Arrays.toString(colorVals) +
                '}';
    }

    public Diploid(String r, String g, String b, int[] values){
        this.red = r;
        this.green = g;
        this.blue = b;
        this.colorVals = values;
    }
}
