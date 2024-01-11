package GP_Package;

public class City {
    private String name;
    private int x_axis;
    private int y_axis;

    public City(String name, int x_axis, int y_axis) {
        this.name = name;
        this.x_axis = x_axis;
        this.y_axis = y_axis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX_axis() {
        return x_axis;
    }

    public void setX_axis(int x_axis) {
        this.x_axis = x_axis;
    }

    public int getY_axis() {
        return y_axis;
    }

    public void setY_axis(int y_axis) {
        this.y_axis = y_axis;
    }
}
