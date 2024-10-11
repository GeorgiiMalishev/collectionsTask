package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace
{
    private static final int WORDS_LIMIT = 10;
    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        //использование LinkedHashMap для удобного хранения пары ключ-значение, и для быстрой итерации.
        //вставка и получение O(1)
        Map<String, Integer> wordCountMap = new LinkedHashMap<>();
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1));

        //ArrayList для быстрой вставки и итерации по списку. Вставка и получение O(1).
        List<Map.Entry<String, Integer>> topWords = getTopWords(wordCountMap, Map.Entry.comparingByValue());
        List<Map.Entry<String, Integer>> bottomWords = getTopWords(wordCountMap, (a, b) -> b.getValue().compareTo(a.getValue()));

        printWords(topWords,"10 наиболее используемых слов:");
        printWords(bottomWords,"10 наименее используемых слов:");
    }

    private static List<Map.Entry<String, Integer>> getTopWords(Map<String, Integer> words, Comparator<Map.Entry<String, Integer>> comparator){
        //использование PriorityQueue для отбора 10 слов. вставка O(log(n)), удаление и получение O(1).
        Queue<Map.Entry<String, Integer>> topTenWords = new PriorityQueue<>(WORDS_LIMIT + 1, comparator);
        //O(n)
        for(Map.Entry<String, Integer> entry : words.entrySet()){
            if(topTenWords.size() == WORDS_LIMIT && comparator.compare(topTenWords.peek(), entry) > 0){
                continue;
            }
            //O(log(n))
            topTenWords.add(entry);
            if (topTenWords.size() > WORDS_LIMIT) {
                //O(1)
                topTenWords.poll();
            }
        }

        return reverseQueue(topTenWords);
    }

    private static List<Map.Entry<String, Integer>> reverseQueue(Queue<Map.Entry<String, Integer>> queue){
        List<Map.Entry<String, Integer>> reversed = new ArrayList<>(WORDS_LIMIT);
        //O(10)
        while(!queue.isEmpty()){
            //O(1)
            reversed.add(queue.poll());
        }
        //O(10)
        return reversed.reversed();
    }
    private static void printWords(List<Map.Entry<String, Integer>> words, String message) {
        System.out.println("\n" + message);
        for (Map.Entry<String, Integer> entry : words) {
            System.out.printf("%s - %d раз(а)%n", entry.getKey(), entry.getValue());
        }
    }
}
