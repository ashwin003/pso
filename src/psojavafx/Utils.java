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

    public static double getTimeTaken(Date date) {
        return (new Date().getTime() - date.getTime()) / 1000.0;
    }

    public static Vehicle initializeVehicle() {
        Node<Float> velocity;
        velocity = new Node<>(random(1, 101), 0.0f);
        return new Vehicle(swarm.size(), velocity);
    }

    public static float random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    
    public static double getLaneCoords(int laneNumber) {
        return (GlobalConstants.canvasHeight * 0.5) - 
               (GlobalConstants.assignedLanes * GlobalConstants.scl) + 
               (laneNumber * GlobalConstants.scl * 2);
    }
    
    public static Vehicle getClosestVehicle(int laneNumber) {
        Vehicle vehicle = null;
        for(Vehicle v : swarm) {
            if(v.getLane() == laneNumber) {
              if(
                      (vehicle != null) && 
                      (v.getPosition().getX() > vehicle.getPosition().getX())
                ) {
                  vehicle = v;
              }  
            }
        }
        return vehicle;
    }
    
    public static ArrayList<Integer> getNeighbouringLanes(int laneNumber) {
        ArrayList<Integer> neighbours = new ArrayList<>();
        // If it's not the first lane, add the one before it to the list
        if(laneNumber != 0) {
          neighbours.add(laneNumber - 1);
        }
        // If it's not the last lane, add the one after it to the list
        if(laneNumber < (GlobalConstants.assignedLanes - 1)) {
          neighbours.add(laneNumber + 1);
        }
        return neighbours;
    }
}
