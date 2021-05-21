package quandev.com;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import quandev.com.model.Point;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Util {

    public static JsonNode getConfig()  {
        try {
            return new ObjectMapper().readTree(new File("D:\\Documents\\Java Workspace 4\\agar-game-server\\config.json"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // xác định khối lượng từ bán kính của hình tròn
    public static double massToRadius(double mass) {
        return 4 + Math.sqrt(mass) * 6;
    }

    //nhận khoảng cách Euclid giữa các cạnh của hai hình
    public static double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2)) - p1.radius - p2.radius;
    }

    public static double randomInRange(double from, double to) {
        return Math.floor(Math.random() * (to - from)) + from;
    }

    //tạo một vị trí ngẫu nhiên trong sân chơi
    public static Point randomPosition(double radius) {
        return new Point()
                .setX(randomInRange(radius , getConfig().get("gameWidth").asDouble() -radius))
                .setY(randomInRange(radius , getConfig().get("gameHeigh").asDouble() -radius));
    }

    public static Point uniformPosition(List<Point> points , double radius) {
        Point bestCandidate= null;
        double maxDistance = 0 ;
        int numberOfCandidates = 10;

        if (points.size() == 0) {
            return randomPosition(radius);
        }

        //Tạo vị trí cho ng tham gia game
        for (int ci = 0 ; ci < numberOfCandidates; ci++) {
            double minDistance = Double.POSITIVE_INFINITY;
            Point candidate = randomPosition(radius);
            candidate.radius = radius;

            for (Point point : points) {
                double distance = getDistance(candidate, point);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }

            if (minDistance > maxDistance) {
                bestCandidate = candidate;
                maxDistance = minDistance;
            } else {
                return randomPosition(radius);
            }
        }

        return bestCandidate;

    }


}
