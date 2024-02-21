package GP_Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class City_Line_Chart {
    public static void DrawChart(ArrayList<String> path, ArrayList<City> cities, String title){
        // create window for drawing graph
        JFrame frame = new JFrame(title);
        frame.setSize(800,800);
        myPanel panel = new myPanel(cities, path);
        frame.setContentPane(panel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
class myPanel extends JPanel{
    private final ArrayList<City> cities;
    private final ArrayList<String> path;
    myPanel(ArrayList<City> cities, ArrayList<String> path){
        setBackground(Color.white);
        this.cities = new ArrayList<>(cities);
        this.path = new ArrayList<>(path);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    private void draw(Graphics g){
        Graphics2D gd = (Graphics2D)g;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setColor(Color.BLACK);
        City city1;
        City city2;
        double max = Max(cities);
        scaleXY(cities, max);
        //Drawing points and lines for graph
        for(int i=0; i<path.size(); i++){
            if(i==path.size()-1) {
                city1 = cities.get(Integer.parseInt(path.get(i)));
                city2 = cities.get(Integer.parseInt(path.get(0)));
            }
            else {
                city1 = cities.get(Integer.parseInt(path.get(i)));
                city2 = cities.get(Integer.parseInt(path.get(i + 1)));
            }
            //Drawing point
            gd.setStroke(new BasicStroke(12));
            gd.setColor(Color.GREEN);
            gd.draw(new Line2D.Double( city1.getX_axis(),city1.getY_axis(),city1.getX_axis(),city1.getY_axis()));
            //Drawing line
            gd.setStroke(new BasicStroke(4));
            gd.setColor(Color.BLUE);
            gd.draw(new Line2D.Double( city1.getX_axis(),city1.getY_axis(),city2.getX_axis(),city2.getY_axis()));
        }
    }
// gets the maximum x or y coordinate
    private double Max(ArrayList<City> cities) {
        double max =0.0;
        for (City c: cities)
        {
            max = Math.max(max, c.getX_axis());
            max = Math.max(max,c.getY_axis());
        }
        return max;
    }

    // Scale coordination to graph dimension
    private void scaleXY(ArrayList<City> cities, double max) {
        DecimalFormat df = new DecimalFormat("#.00");
        for (City c: cities){
            c.setX_axis(Double.parseDouble(df.format(c.getX_axis()/max*700))+50);
            c.setY_axis(Double.parseDouble(df.format(c.getY_axis()/max*700))+50);
        }
    }

}