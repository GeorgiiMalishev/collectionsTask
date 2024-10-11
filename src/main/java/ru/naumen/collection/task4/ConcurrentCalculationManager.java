package ru.naumen.collection.task4;

import java.util.concurrent.*;
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
    private final BlockingQueue<Future<T>> taskQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void addTask(Supplier<T> task) {
        taskQueue.add(executorService.submit(task::get));
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() throws InterruptedException {
        try {
            return taskQueue.take().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}