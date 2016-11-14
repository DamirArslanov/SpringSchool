package school.utils;

import org.springframework.stereotype.Component;
import school.entity.Schedule;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by ArslanovDamir on 01.10.2016.
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
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        Date date = calendar.getTime();
        return date;
    }

    //Метод возвращает дату по дню недели
    public Date getFutureDateByWeekNumber(int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Устанавливаем текущее время
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Перебрасываем дату на понедельник
        calendar.add(Calendar.DAY_OF_WEEK, (number - 1)); // Прибавляем к понедельнику дату необходимого дня недели
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date date = calendar.getTime();
        return date;
    }


    public LocalDate getLocalDate() {
        Clock clock = Clock.system(ZoneId.of("Europe/Moscow"));
        LocalDate localDate = LocalDate.now();
        return localDate;
    }


    //Метод возвращает массив с датами этой недели
    //Можно использовать для массового создания лекции на эту неделю
    public List<Date> giveMeThisWeekDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Устанавливаем текущее время
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //Устанавливаем понедельник на календаре, будто сейчас понедельник

        List<Date> dates = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            Date date = calendar.getTime();
            dates.add(date);
            calendar.add(Calendar.DAY_OF_WEEK, 1); //Прибавляем сутки
        }
        return dates;
    }

    //Метод возвращает массив с датами новой (следующей) недели
    //Например, если метод создания лекции был вызван в воскресенье, то не имеет смысла делать лекции на эту неделю
    public List<Date> giveMeFutureWeekDays() {

        Date date = new Date(); //Получаем сегодняшнуюю дату
        Calendar instance = Calendar.getInstance(); //Получаем инстанс календаря
        instance.setTime(date); //Сетапим сегодняшнюю дату (сегодня 7 день недели, наверное)
        instance.set(Calendar.HOUR_OF_DAY, 0);
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

    public static void main(String[] args) {
        DateUtil dateUtil = new DateUtil();
        System.out.println(dateUtil.giveMeThisWeekDays());

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

    // Метод возвращает ОТРЕДАКТИРОВАННУЮ ДАТУ в соответствии с ЧАСАМИ ЗАНЯТИЙ
    // (IN 12.06.2016 00:00:00 - OUT 12.06.2016 08:30:00)
    public Date setLessonTime(Date date, Schedule schedule) {
        int i = schedule.getTime().indexOf(":");
        String hour = schedule.getTime().substring(0, i);
        String minute = schedule.getTime().substring(i+1, schedule.getTime().length());

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        date=calendar.getTime();
        return date;
    }


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

    // Возвращает LIST с Первым и Последним днем месяца из полученной даты
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
}
