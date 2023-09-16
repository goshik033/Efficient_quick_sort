
/*
-- ПРИНЦИП РАБОТЫ --
Создается массив заполнений объектами класса Person
Дальше этот массив сортируется с помощью рекурсивной быстрой сортировки "in-place".
Смысл заключается в том, что после выбора опорного элемента, два указателя начинают идти навстречу друг другу,
параллельно заменяя неподходящие с двух сторон эелменты друг на друга, а подходящие пропуская.
Сравнение двух элементов проходит с помощью метода isLess, который показывет, нужно ли менять элементы или нет.


-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
Здесь могут возникнуть небольше трудности, потому что я сам до конца не уверен, что алгоритм корректно работает,
но все тесты на платформе он прошел да и ничего нового по улучшению этого алгоритма я придумать не могу.
А именно, больше всего мне не нравится кусок кода с 80 строчки по 90, но идей по улучшению нет.

Если говорить про корректность, то когда длина отрезка становится равна 2 и меньше и рекурсия заканчивается.
Алгоритм дополнительно проверяет необходимость замены этих двух элементов между собой.
Также он вроде бы корректо сортирует две части массива, а после рекурсивно сортирует
новые отрезки от left, которое было подано на вход, до left, которое получилось после сортировки,
и так же только с right


-- ВРЕМЕННАЯ СЛОЖНОСТЬ --

Поскольку на каждом шаге алгоритма размер уменьшается примерно вдвое,
алгоритм будет выполняться в среднем за O(n log n).
Однако в худшем случае алгоритм может потребовать O(n^2) операций. Но благодаря случайному выбору pivot,
вероятность худшего случая становится сеньше

Также нужно O(n), чтобы считать все массив

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
Максимальная глубина стека вызовов рекурсии будет O(n)
Дополнительные переменные: O(1)
Общая пространственная сложность алгоритма составит O(n)

ID верной попытки 89703550.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        Person[] array = new Person[n];
        for (int i = 0; i < n; i++) {
            String str = bf.readLine();
            array[i] = new Person(str.split(" ")[0], Integer.parseInt(str.split(" ")[1]),
                    Integer.parseInt(str.split(" ")[2]));
        }
        array = quickSort(array, 0, n - 1);
        for (Person person : array) {
            System.out.println(person.name + " ");
        }
    }

    public static Person[] quickSort(Person[] arr, int left, int right) {
        int oldLeft = left;
        int oldRight = right;
        if (right - left < 2) {
            if (!isLess(arr[left], arr[right])) {
                Person temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
            return arr;
        } else {
            Random random = new Random();
            Person pivot = arr[left + random.nextInt(right - left)];
            while (left < right) {
                while (left < right && isLess(arr[left], pivot)) {
                    left++;
                }
                while (left < right && isLess(pivot, arr[right])) {
                    right--;
                }
                if (left < right) {
                    Person temp = arr[left];
                    arr[left] = arr[right];
                    arr[right] = temp;
                    left++;
                    right--;
                }
            }
            arr = quickSort(arr, oldLeft, left);
            arr = quickSort(arr, right, oldRight);
        }
        return arr;
    }

    public static boolean isLess(Person a, Person b) {
        if (a.task != b.task) {
            return a.task > b.task;
        } else {
            if (a.fine != b.fine) {
                return a.fine < b.fine;
            } else {
                int result = a.name.compareToIgnoreCase(b.name);
                if (result < 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public static class Person {
        int task;
        int fine;
        String name;

        public Person(String name, int task, int fine) {
            this.fine = fine;
            this.task = task;
            this.name = name;
        }
    }
}


