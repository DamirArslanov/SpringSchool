package school.utils;




import java.util.Scanner;
public class JavaLab {

    public static void main(String[] args) {
        int days = 0, allTemperature = 0;
        boolean correct;
        String monthString = "";
        int[] temperature;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите название месяца");

        //будем требовать ввести название месяца до тех пор, пока не будет введен корректно
        do {
            correct = false;
            //можно вводить в любом регистре
            monthString = sc.nextLine().toUpperCase();
            switch (monthString) {
                case "ЯНВАРЬ":  days = 31;
                    break;
                case "ФЕВРАЛЬ":  days = 28;
                    break;
                case "МАРТ":  days = 31;
                    break;
                case "АПРЕЛЬ":  days = 30;
                    break;
                case "МАЙ":  days = 31;
                    break;
                case "ИЮНЬ":  days = 30;
                    break;
                case "ИЮЛЬ":  days = 31;
                    break;
                case "АВГУСТ":  days = 31;
                    break;
                case "СЕНТЯБРЬ":  days = 30;
                    break;
                case "ОКТЯБРЬ": days = 31;
                    break;
                case "НОЯБРЬ": days = 30;
                    break;
                case "ДЕКАБРЬ": days = 31;
                    break;
                default:
                    System.out.println("Название месяца некорректно!");
                    //TRUE даст возможность при ошибке возобновить запрос месяца
                    correct = true;
                    break;
            }
        }while (correct);
        System.out.println("Месяц: " + monthString.toUpperCase());
        System.out.println("Количество дней: " + days);
        System.out.println("Введите температуру за день");
        //инициализировали массив полученным месяцем
        temperature = new int[days];
        //заполним месяц
        for (int i = 0; i < days; i++) {
            System.out.println("День №: " + (i+1));
            temperature[i] = sc.nextInt();
            //"складываем" общую сумму температур для получения в дальнейшем средней
            allTemperature += temperature[i];
        }
       //пройдем по массиву и сравним каждый день с
        for (int i = 0; i < temperature.length; i++){
            if ((allTemperature / days) < temperature[i]) {
                System.out.println("Среднемесячная температура: " + (allTemperature / days) + "Сº");
                System.out.println("Температура выше суточной была в: " + (i+1) + " день = " + temperature[i] + "Сº");
            }
        }
    }
}
