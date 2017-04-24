/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psojavafx;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ashwi
 */
public class Utils {
    public static ArrayList<Vehicle> swarm = new ArrayList<Vehicle>();

    public static int assignedLanes = 1;

    public static double getTimeTaken(Date date) {
        return (new Date().getTime() - date.getTime()) / 1000.0;
    }

    public static Vehicle initializeVehicle() {
        Node<Float> velocity;
        velocity = new Node<Float>(random(1, 101), 0.0f);
        return new Vehicle(swarm.size(), velocity);
    }

    public static float random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
