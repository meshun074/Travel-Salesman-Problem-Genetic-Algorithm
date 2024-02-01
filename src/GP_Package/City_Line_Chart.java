package GP_Package;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class City_Line_Chart {
    public static void DrawChart(ArrayList<String> path, ArrayList<City> cities, String title){
        JFrame frame = new JFrame(title);
        frame.setSize(864,864);
        frame.setContentPane(new myPanel(cities, path));
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
        Graphics2D gd = (Graphics2D)g;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setColor(Color.BLACK);
        City city1;
        City city2;
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

}