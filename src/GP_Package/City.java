package GP_Package;

public class City {
    private final int index;
    private final String name;
    private final int x_axis;
    private final int y_axis;
// A city has a name, index which is in the order read from data1.txt and a x and y points
    public City(int index, String name, int x_axis, int y_axis) {
        this.name = name;
        this.x_axis = x_axis;
        this.y_axis = y_axis;
        this.index= index;
    }

    public int getIndex() {
        return index;
    }
    public String getName() {
        return name;
    }


    public int getX_axis() {
        return x_axis;
    }


    public int getY_axis() {
        return y_axis;
    }

}
