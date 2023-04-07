package part4.exercice1;

import java.util.ArrayList;
import java.util.List;

class Manager implements Runnable {
    List<String> taskList;
    private int nextTaskNum;

    public Manager() {
        taskList = new ArrayList<>();
        nextTaskNum = 1;
    }

    public synchronized void addTask(String task) {
        taskList.add(task);
        System.out.println("Manager added task #" + nextTaskNum + ": " + task);
        nextTaskNum++;
        notifyAll();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (taskList.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                String task = taskList.remove(0);
                System.out.println("Manager assigned task: " + task);
            }
        }
    }
}

class Worker implements Runnable {
    private String name;
    private Manager manager;

    public Worker(String name, Manager manager) {
        this.name = name;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (manager) {
                while (manager.taskList.isEmpty()) {
                    try {
                        manager.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                String task = manager.taskList.remove(0);
                System.out.println(name + " is performing task: " + task);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Thread managerThread = new Thread(manager);
        managerThread.start();

        Worker worker1 = new Worker("Worker 1", manager);
        Worker worker2 = new Worker("Worker 2", manager);
        Thread workerThread1 = new Thread(worker1);
        Thread workerThread2 = new Thread(worker2);
        workerThread1.start();
        workerThread2.start();

        manager.addTask("Task 1");
        manager.addTask("Task 2");
        manager.addTask("Task 3");
    }
}