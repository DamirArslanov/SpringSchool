package school.utils;

import org.springframework.stereotype.Component;
import school.entity.Schedule;

import java.time.*;
import java.util.*;

/**
 * Created by Cheshire on 01.10.2016.
 */
@Component
public class DateUtil {


    //метод возвращает число соответствующее дню недели(1 - Понедельник, 3 - Среда)
    public int getWeekNumber(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day1 = calendar.get(Calendar.DAY_OF_WEEK);
        int day = ((day1 == 1) ? 7 : --day1);
        return day;
    }

    //метод возвращает число = дню недели, но соответствующее переданной дате (date)
    public int giveMeWeekNumberOnDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day1 = calendar.get(Calendar.DAY_OF_WEEK);
        int day = ((day1 == 1) ? 7 : --day1);
        return day;
    }

    //Метод возвращает дату по дню недели
    public Date getDateByWeekNumber(int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Устанавливаем текущее время
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Перебрасываем дату на понедельник
        calendar.add(Calendar.DAY_OF_WEEK, (number - 1)); // Прибавляем к понедельнику дату необходимого дня недели
        Date date = calendar.getTime();
        return date;
    }


    public LocalDate getLocalDate() {
        Clock clock = Clock.system(ZoneId.of("Europe/Moscow"));
        LocalDate localDate = LocalDate.now();
        return localDate;
    }

//
//    public Date getDate() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        Date date6 = new Date();
//        return dateFormat.format(date6);
//    }


    //Метод возвращает массив с датами этой недели
    //Можно использовать для массового создания лекции на эту неделю
    public List<Date> giveMeThisWeekDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Устанавливаем текущее время
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //Устанавливаем понедельник на календаре, будто сейчас понедельник

        List<Date> dates = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            Date date = calendar.getTime();
            dates.add(date);
            calendar.add(Calendar.DAY_OF_WEEK, 1); //Прибавляем сутки
            System.out.println(date);
        }
        return dates;
    }

    //Метод возвращает массив с датами новой (следующей) недели
    //Например, если метод создания лекции был вызван в воскресенье, то не имеет смысла делать лекции на эту неделю
    public List<Date> giveMeFutureWeekDays() {

        Date date = new Date(); //Получаем сегодняшнуюю дату
        Calendar instance = Calendar.getInstance(); //Получаем инстанс календаря
        instance.setTime(date); //Сетапим сегодняшнюю дату (сегодня 7 день недели, наверное)
        if (getWeekNumber() == 1) {
            instance.add(Calendar.DAY_OF_MONTH, 7); //Получим один из дней следующей недели
        } else { // Иначе можем "перепрыгнуть" неделю
            instance.add(Calendar.DAY_OF_MONTH, 6); //Получим один из дней следующей недели
        }
        date = instance.getTime(); //Обновляем дату, возможно это уже "СРЕДА" новой недели
        instance.setTime(date); // Обновляем календарь
        instance.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //Устанавливаем понедельник на календаре, будто сейчас понедельник

        List<Date> dates = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            Date newDate = instance.getTime();
            dates.add(newDate);
            instance.add(Calendar.DAY_OF_WEEK, 1); //Прибавляем сутки
        }
        return dates;
    }


    //Метод возвращает массив с датами нынешней или следующей недели
    //В зависимости от того, когда был вызван, т.к. он использует метод getWeekNumber()
    public List<Date> giveMeWeekDays() {
        int weekDayNumber = getWeekNumber();
        if (weekDayNumber > 6) {
            return giveMeFutureWeekDays();
        } else {
            return giveMeThisWeekDays();
        }
    }

    public Date setLessonTime(Date date, Schedule schedule) {
        LocalDateTime currentTime = LocalDateTime.now();
        int i = schedule.getTime().indexOf(":");
        String hour = schedule.getTime().substring(0, i);
        String minute = schedule.getTime().substring(i+1, schedule.getTime().length());


//        LocalDate localDate = LocalDate.of(Year.now(),Month.valueOf(),,1,1,1);
//        Clock clock = Clock.system('');


        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        date=calendar.getTime();
        System.out.println("Current DateTime: " + date);
        return date;
    }


    public static void main(String[] args) {
        DateUtil dateUtil = new DateUtil();
        Date date = new Date(); //Получаем сегодняшнуюю дату
        Calendar instance = Calendar.getInstance(); //Получаем инстанс календаря
        instance.setTime(date); //Сетапим сегодняшнюю дату (сегодня 7 день недели, наверное)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        date = instance.getTime();
        System.out.println(date);
        instance.add(Calendar.DAY_OF_MONTH, 6); //Получим один из дней следующей недели
        date = instance.getTime();
        System.out.println(date);


    }
//
//    public static void main(String[] args) {
//        DateUtil dateUtil = new DateUtil();
//        List<Date> dates = dateUtil.giveMeWeekDays();
//        for (Date date : dates) {
//            System.out.println(date);
//            System.out.println("DAY NUMBER: " + dateUtil.giveMeWeekNumberOnDate(date));
//            System.out.println("--------------------------------------");
//        }
//    }

    //Вернуть последний день месяца
    public Date getLastDayMonth() {
        Calendar c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        now = c.getTime();
        return now;
    }
    //Вернуть первый день месяца
    public Date getFirstDayMonth() {
        Calendar c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        now = c.getTime();
        return now;
    }

    public List<Date> getFirstAndLastDaysOfSelectedMonth(Date date) {
        List<Date> dates = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        date = c.getTime();
        dates.add(date);

        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        date = c.getTime();
        dates.add(date);

        return dates;
    }

    public Date getSelectedMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month -1 , 1);
        Date date = calendar.getTime();
        return date;
    }
//
//    public static void main(String[] args) {
//        DateUtil dateUtil = new DateUtil();
//        Date selectedMonth = dateUtil.getSelectedMonthDays(2016, 5);
//        Date firstDay = dateUtil.getFirstAndLastDaysOfSelectedMonth(selectedMonth).get(0);
//        Date lastDay = dateUtil.getFirstAndLastDaysOfSelectedMonth(selectedMonth).get(1);
//        System.out.println(firstDay);
//        System.out.println(lastDay);
//    }

}
