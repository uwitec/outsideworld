package com.instructure.queue;

public interface Queue<T> {
    public T poll();
    public void push(T t);
}
