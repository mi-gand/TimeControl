package timecontrol;

import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeControl {
    private LocalDateTime startWorkTime;        //время начала работы
    private int restCounter;                    //количество отдыхов
    private LocalDateTime startRestTime;        //время начала текущего отдыха
    private LocalDateTime endRestTime;          //время окончания текущего отдыха
    private long restDurationInMillis;             //общее время отдыха
    private long workDurationInMillis;             //общее время работы

    private boolean isRestStart = false;
    private static final long MINUTES_IN_HOUR = 60;
    private static final long MILLIS_IN_HOUR = 3_600_000;
    private static final long MILLIS_IN_MINUTE = 60_000;

    private final Task task;

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    public TimeControl(Task task) {
        this.task = task;
    }

    public LocalDateTime startWork(){
        startWorkTime = LocalDateTime.now();
        return startWorkTime;
    }


    public boolean startRest(){
        if(!isRestStart){
            startRestTime = LocalDateTime.now();
            if(endRestTime == null){
                workDurationInMillis = Duration.between(startWorkTime, startRestTime).toMillis();
            }else{
                workDurationInMillis += Duration.between(endRestTime, startRestTime).toMillis();
            }
            isRestStart = true;
            ++restCounter;
            return true;
        }else{
            return false;
        }
    }

    public boolean endRest(){
        if(isRestStart){
            endRestTime = LocalDateTime.now();
            restDurationInMillis += (Duration.between(startRestTime, endRestTime)).toMillis();
            isRestStart = false;
            return true;
        }else{
            return false;
        }
    }

    public void showLearningTime(){
        if(isRestStart){
            endRest();
            getWorkingTimeInformation();
            startRest();
            restCounter--;
        }else{
            startRest();
            restCounter--;
            getWorkingTimeInformation();
            endRest();
        }
    }

    public Task getTask() {
        return task;
    }

    public void getWorkingTimeInformation(){
        long allPeriod = Duration.between(startWorkTime, LocalDateTime.now() ).toMillis();
        //long workingMinutes = allPeriod - restDurationInMin;
        int workHours = (int)(workDurationInMillis / MILLIS_IN_HOUR);
        int workMinutes = (int)((workDurationInMillis % MILLIS_IN_HOUR)/ MILLIS_IN_MINUTE);

        int restHours = (int)(restDurationInMillis / MILLIS_IN_HOUR);
        int restMinutes = (int)((restDurationInMillis % MILLIS_IN_HOUR)/ MILLIS_IN_MINUTE);
        System.out.printf("Вы занимались %s: %d часов %d минут%n",task.getTaskName(),workHours, workMinutes );
        System.out.printf("Вы отдыхали %d раз суммарно: %d часов %d минут%n",
                restCounter,restHours, restMinutes );

        /*Проверка*/
        System.out.println("Погрешность мин: " + (allPeriod - workDurationInMillis - restDurationInMillis)
                /MILLIS_IN_MINUTE);

    }
}

/*
Учет времени отдыха
Старт начала занятия (точное время)


 */