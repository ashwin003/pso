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
public class Vehicle {

    private int _number;

    public int getNumber() {
        return _number;
    }

    public void setNumber(int _number) {
        this._number = _number;
    }

    private Node<Float> _position;
    
    /**
     * Gets a vehicle's position in a 2-dimensional space
     * @return Vehicle's space in a two dimensional region 
     */
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
            if(this.getType() == vehicle.getType()) {
                this.setLane(vehicle.getLane());
                foundLane = true;
                break;
            }
        }
        if(!foundLane) {
            this.setLane(Utils.assignedLanes++);
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

    private ArrayList<Node<Float>> _path = new ArrayList<Node<Float>>();

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
}
