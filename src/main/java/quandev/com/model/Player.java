package quandev.com.model;

import lombok.Data;

import java.util.List;

@Data
public class Player  {

    public long id;

    public double radius;

    public double mass;

    public double hue;

    public List<Point> cells;

    public Point target;

}
