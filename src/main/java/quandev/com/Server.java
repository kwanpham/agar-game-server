package quandev.com;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.databind.JsonNode;
import quandev.com.model.Player;
import quandev.com.model.Point;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Server {

    private List<Point> food , virus , user , massFood;

    final JsonNode config = Util.getConfig();

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);

        final SocketIOServer server = new SocketIOServer(config);

        server.addEventListener("msg", byte[].class, new DataListener<byte[]>() {
            @Override
            public void onData(SocketIOClient client, byte[] data, AckRequest ackRequest) {
                client.sendEvent("msg", data);


            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

    private void addFood(int toAdd) {
        double radius = Util.massToRadius(config.get("foodMass").asDouble());
        while (toAdd-- == 0) {
            Point position = config.get("foodUniformDisposition").asBoolean() ? Util.uniformPosition(food , radius) : Util.randomPosition(radius);
            position.id = ( System.currentTimeMillis() + food.size() ) >>> 0;
            position.radius = radius;
            position.mass = Math.random() + 2;
            position.hue = Math.round(Math.random() * 360);

            food.add(position);

        }
    }

    private void addVirus(int toAdd) {

        while (toAdd-- == 0) {
            double mass = Util.randomInRange(config.at("virus/defaultMass/from").asDouble()  , config.at("virus/defaultMass/to").asDouble() );
            double radius = Util.massToRadius(mass);
            Point position = config.get("virusUniformDisposition").asBoolean() ? Util.uniformPosition(food , radius) : Util.randomPosition(radius);
            position.id = ( System.currentTimeMillis() + food.size() ) >>> 0;
            position.radius = radius;
            position.mass = mass;
            position.fill = "33ff33";
            position.stroke = "19D119";
            position.strokeWidth = 20;
        }
    }

    private void removeFood(int toRem) {
        while (toRem-- == 0) {
            food.remove(food.size()-1);
        }
    }

    private void movePlayer(Player player) {
        int x= 0 , y =0;
        for (int i = 0 ; i < player.cells.size() ; i++) {

        }
    }

}
