/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psojavafx;

/**
 *
 * @author ashwin
 * @param <T>
 */
public class Node<T> {
    private T _x;
    private T _y;

    public T getX() {
        return _x;
    }

    public void setX(T _x) {
        this._x = _x;
    }

    public T getY() {
        return _y;
    }

    public void setY(T _y) {
        this._y = _y;
    }

    public Node(T _x, T _y) {
        this._x = _x;
        this._y = _y;
    }
}
