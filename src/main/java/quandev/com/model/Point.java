package quandev.com.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Point {

    public long id;

    public double x;

    public double y;

    public double radius;

    public double mass;

    public double hue;

    public String fill;

    public String stroke;

    public  double strokeWidth;

    public double speed;

}
