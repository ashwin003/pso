/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psojavafx;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a vehicle moving across the highway
 * @author ashwin
 */
public final class Vehicle {

    private int _number;

    public int getNumber() {
        return _number;
    }

    public void setNumber(int _number) {
        this._number = _number;
    }

    private Node<Float> _position;

    public Node<Float> getPosition() {
        return _position;
    }

    public void setPosition(Node<Float> _position) {
        this._position = _position;
    }

    private Node<Float> _velocity;

    public Node<Float> getVelocity() {
        return _velocity;
    }

    public void setVelocity(Node<Float> _velocity) {
        this._velocity = _velocity;

        setType(_velocity.getX());
    }

    private int _lane;

    public int getLane() {
        return _lane;
    }

    public void setLane(int _lane) {
        this._lane = _lane;
    }

    public void assignLane() {
        boolean foundLane = false;
        for (int i = 0; i < this.getNumber(); i++) {
            Vehicle vehicle = Utils.swarm.get(i);
            if (this.getType() == vehicle.getType()) {
                this.setLane(vehicle.getLane());
                foundLane = true;
                break;
            }
        }
        if (!foundLane) {
            this.setLane(GlobalConstants.assignedLanes++);
        }
    }

    private char _type;

    public char getType() {
        return _type;
    }

    public void setType(char _type) {
        this._type = _type;
    }

    private void setType(float velocity) {
        if ((velocity > 0.0) && (velocity <= 10.0)) {
            setType('A');
        } else if ((velocity > 10.0) && (velocity <= 30.0)) {
            setType('B');
        } else if ((velocity > 30.0) && (velocity <= 45.0)) {
            setType('C');
        } else if ((velocity > 45.0) && (velocity <= 50.0)) {
            setType('D');
        } else if ((velocity > 50.0) && (velocity <= 100.0)) {
            setType('E');
        }
    }

    private Date _entry;

    public Date getEntry() {
        return _entry;
    }

    public void setEntry(Date _entry) {
        this._entry = _entry;
    }

    private ArrayList<Node<Float>> _path = new ArrayList<>();

    public ArrayList<Node<Float>> getPath() {
        return _path;
    }

    public Node<Float> getPath(int index) {
        return getPath().get(index);
    }

    public void setPath(ArrayList<Node<Float>> _path) {
        this._path = _path;
    }

    public void addNode(Node<Float> node) {
        this._path.add(node);
    }

    public Vehicle(int _number, Node<Float> _velocity) {
        setNumber(_number);
        setVelocity(_velocity);
        setEntry(new Date());
    }
    
    /**
     * Compares vehicle's current position with respect to the screen
     * @return Whether or not the current vehicle is visible on screen
     */
    public boolean isVisible() {
        return this.getPosition().getX() > GlobalConstants.canvasWidth;
    }
    
    /**
     * Whether or not the current vehicle will collide with the given one
     * @param vehicle Vehicle to compare the current one with
     * @param time  Time interval for distance comparison
     * @return whether or not the vehicles will collide
     */
    public boolean willCollide(Vehicle vehicle) {
        if(!vehicle.isVisible())
            return false;
        double dA = this.getPosition().getX();
        double dB = vehicle.getPosition().getX();
        return dA < (dB + ( GlobalConstants.nSecondRule * vehicle.getVelocity().getX() ));
    }
    
    public void move() {
        Node<Float> position = getNextPosition();
        this.setPosition(position);
    }
    
    public Node<Float> getNextPosition() {
        Node<Float> position = this.getPosition();
        position.setX(position.getX() + this.getVelocity().getX());
        position.setY(position.getY() + this.getVelocity().getY());
        return position;
    }
    
    public void move(int laneNumber) {
        Node<Float> position = getNextPosition(laneNumber);
        this.setPosition(position);
    }
    
    public Node<Float> getNextPosition(int laneNumber) {
        double y = Utils.getLaneCoords(laneNumber);
        Node<Float> position = this.getPosition();
        position.setX(position.getX() + this.getVelocity().getX());
        position.setY((float)y);
        return position;
    }
    
    public void computePath() {
        int lane = this.getLane();
        float xPos = this.getPosition().getX();
        while(this.isVisible()) {
            Vehicle neighbour = Utils.getClosestVehicle(lane);
            if(neighbour != null) {
                Node<Float> po = neighbour.getPosition();
                while(xPos < neighbour.getPosition().getX()) {
                    if(willCollide(neighbour)) {
                        // Get neighbouring lane to transition to and add it to the path list
                        ArrayList<Integer> neighbours = Utils.getNeighbouringLanes(this.getLane());
                        int selected = Math.round(Utils.random(0, neighbours.size()));
                        selected = neighbours.get(selected);
                        addNode(this.getNextPosition(selected));
                        this.move(selected);
                        // Reset neighbours position for next iteration
                        neighbour.setPosition(po);
                        break;
                    }
                    else {
                        // Add to path list
                        addNode(this.getNextPosition());
                        this.move();
                    }
                    neighbour.move();
                    xPos += this.getVelocity().getX();
                }
            }
            else {
                // No neighbours. All straight paths
                addNode(this.getNextPosition());
                this.move();
            }
        }
    }
}