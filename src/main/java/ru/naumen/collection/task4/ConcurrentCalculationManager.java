package ru.naumen.collection.task4;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
public class ConcurrentCalculationManager<T> {

    /**
     * Добавить задачу на параллельное вычисление
     */
    //Выбрана за потокобезопасность и прямую передачу между потоками.
    //Сложность: O(1) для add и take.
    private final BlockingQueue<Supplier<T>> taskQueue = new LinkedBlockingQueue<>();
    //Выбрана за потокобезопасность и быстрый доступ к данным.
    //Сложность: O(1) для put и get.
    private final Map<Supplier<T>, T> taskMap = new ConcurrentHashMap<>();

    public void addTask(Supplier<T> task) {
        taskQueue.add(task);
        taskMap.put(task, task.get());
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() throws InterruptedException {
        while (!taskMap.containsKey(taskQueue.peek())) {
        }
        return taskMap.get(taskQueue.take());
    }
}