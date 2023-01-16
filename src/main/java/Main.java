import task.Task;
import timecontrol.TimeControl;

import java.time.LocalDateTime;
import java.util.Scanner;

import static timecontrol.TimeControl.DATE_TIME_FORMATTER;

public class Main {
    private static final String DONT_START =  "Обучение еще не началось";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        TimeControl timeControl = new TimeControl(getTaskName());
        boolean isLearningStart = false;

        while (true){
            showMenu();
            if(!scanner.hasNextInt()){
                System.out.println("Введите числовую команду");
            }else {
                int input = scanner.nextInt();
                switch (input){
                    case 1:
                        if(!isLearningStart){
                            LocalDateTime start = timeControl.startWork();
                            isLearningStart = true;
                            System.out.println("Вы начали заниматься - " + timeControl.getTask().getTaskName() +
                                    ".Время начала - " + start.format(DATE_TIME_FORMATTER));
                        }else{
                            System.out.println("Текущее занятие уже начато. Закончите его");
                        }
                        break;
                    case 2:
                        if(isLearningStart){
                            if(timeControl.startRest()){
                                System.out.println("Начали отдых");
                            }else{
                                System.out.println("Вы уже отдыхаете");
                            }
                        }else {
                            System.out.println(DONT_START);
                        }
                        break;
                    case 3:
                        if(isLearningStart){
                            if(timeControl.endRest()){
                                System.out.println("Продолжили заниматься");
                            }else{
                                System.out.println("Вы уже занимаетесь");
                            }
                        }else {
                            System.out.println(DONT_START);
                        }
                        break;
                    case 4:
                        if(isLearningStart){
                            timeControl.showLearningTime();

                        }else {
                            System.out.println(DONT_START);
                        }
                        break;
                    case 5:
                        if(isLearningStart){
                            timeControl.getWorkingTimeInformation();
                            System.out.printf("До свидания");
                            Thread.sleep(10000);
                        }else {
                            System.out.println(DONT_START + ", но вы попытались. До свидания.");
                            Thread.sleep(6000);
                        }
                        return;
                    default:
                        System.out.println("Введена неправильная команда");
                }
            }
        }
    }

    private static void showMenu(){
        System.out.println(
                        "1. Начать занятия\n" +
                        "2. Пауза\n" +
                                "3. Продолжить занятие\n"+
                        "4. Показать время занятий\n" +
                        "5. Закончить заниматься(выход)\n"
        );
    }

    private static Task getTaskName(){
        System.out.println("Чем планируете заниматься?");
        return new Task((scanner.next()));
    }
}
