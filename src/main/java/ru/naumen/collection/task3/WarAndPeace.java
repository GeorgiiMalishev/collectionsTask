package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        //использование HashMap для удобного хранения пары ключ-значение.
        //вставка и получение O(1)
        Map<String, Integer> wordCountMap = new HashMap<>();
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1));

        //пришлось использовать List, потому что у Map нет сортировки по значениям.
        //сортировка О(n*log(n)), получение по индексу О(1)
        List<Map.Entry<String, Integer>> sortedWords = sortListByValue(wordCountMap);

        printWords(sortedWords, 0, 10, "10 наиболее используемых слов:");
        printWords(sortedWords, sortedWords.size() - 10, sortedWords.size(), "10 наименее используемых слов:");
    }

    private static List<Map.Entry<String, Integer>> sortListByValue(Map<String, Integer> wordCount) {
        return wordCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .toList();
    }

    private static void printWords(List<Map.Entry<String, Integer>> sortedWords, int fromIndex, int toIndex, String message) {
        System.out.println("\n" + message);
        for (int i = fromIndex; i < toIndex; i++) {
            Map.Entry<String, Integer> entry = sortedWords.get(i);
            System.out.printf("%s - %d раз(а)%n", entry.getKey(), entry.getValue());
        }
    }
}
