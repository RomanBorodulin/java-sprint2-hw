import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static final int NUMBER_OF_MONTHLY_REPORTS = 3; // количество месячных отчетов
    public static final int YEAR_IN_YEARLY_REPORT = 2021; // рассматриваемый год в годовом отчете

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        YearlyReport yearlyReport = null;
        MonthlyReport monthlyReport = null;

        while (true) {
            printMenu();
            String command = scanner.nextLine();

            if (command.equals("1")){
                // Добавлена проверка на успешное считывание конкретного месяца
                monthlyReport = new MonthlyReport();
                int sizeList = 0;
                for (int i = 0; i < NUMBER_OF_MONTHLY_REPORTS; i++) {
                    monthlyReport.loadFile("0" + (i + 1), "resources/m.20210" + (i + 1) + ".csv");
                    int currentSizeList = monthlyReport.monthDataList.size();
                    if (currentSizeList != sizeList) {
                        sizeList = currentSizeList;
                        System.out.println("Месячный отчет за " + monthlyReport.getMonthName("0" + (i + 1)) + " успешно считался.");
                    }
                }

            } else if (command.equals("2")) {
                yearlyReport = new YearlyReport(YEAR_IN_YEARLY_REPORT, "resources/y.2021.csv");
                if (!yearlyReport.yearDataList.isEmpty()) {
                    System.out.println("Годовые отчеты успешно считались.");
                }
            /* Добавлена конструкция try-catch. В блоке try происходит проверка на пустоту списков класса
            monthlyReport и yearlyReport, которые указывают на удачное/неудачное считывание файла.
            Блок catch выполняется, если пользователь не считал годовой или месячный отчет.
             */
            } else if (command.equals("3")) {
                try {
                    if (monthlyReport.monthDataList.isEmpty() || yearlyReport.yearDataList.isEmpty()) {
                        System.out.println("Произошла ошибка при считывании месячного или годового отчёта.");
                        continue;
                    }
                } catch (NullPointerException exception) {
                    System.out.println("Отчёты не были считаны. Считайте сначала месячные и годовые отчёты.");
                    continue;
                }
                Checker checker = new Checker(yearlyReport, monthlyReport);
                if (checker.check()) {
                    System.out.println("Сверка данных прошла успешно. Ошибок не обнаружено.");
                }

            } else if (command.equals("4")) {
                if (monthlyReport == null) {
                    System.out.println("Месячный отчёт не был считан. Считайте сначала отчёт.");
                    continue;
                } else if (monthlyReport.monthDataList.isEmpty()) {
                    System.out.println("Произошла ошибка при считывании месячного отчёта.");
                    continue;
                }
                for (int i = 0; i < NUMBER_OF_MONTHLY_REPORTS; i++) {
                    String monthName = monthlyReport.getMonthName("0" + (i + 1));
                    HashMap<String, Integer> report = monthlyReport.getProfitableProduct("0" + (i + 1));

                    System.out.println("Месяц - " + monthName);
                    report.forEach((name, sum) -> System.out.println("Самый прибыльный товар - \"" + name
                            +  "\" на сумму - " + sum));
                    report = monthlyReport.getMaxExpense("0" + (i + 1));
                    report.forEach((name, sum) -> System.out.println("Самая большая трата - \"" + name
                            +  "\" на сумму - " + sum));
                }

            } else if (command.equals("5")) {
                if (yearlyReport == null) {
                    System.out.println("Годовой отчёт не был считан. Считайте сначала отчёт.");
                    continue;
                } else if (yearlyReport.yearDataList.isEmpty()) {
                    System.out.println("Произошла ошибка при считывании годового отчёта.");
                    continue;
                }
                System.out.println("Рассматриваемый год - " + yearlyReport.getYear());
                for (int i = 0; i < NUMBER_OF_MONTHLY_REPORTS; i++) {
                    HashMap <String, Integer> profitOfMonth = yearlyReport.getProfit("0" + (i + 1));
                    int profit = profitOfMonth.get("0" + (i + 1));
                    String nameMonth = yearlyReport.getMonthName("0" + (i + 1));

                    System.out.println("Прибыль за " + nameMonth + ": " + profit);
                }

                System.out.println("Средний расход за все месяцы в году: " + yearlyReport.getAverageExpense());
                System.out.println("Средний доход за все месяцы в году: " + yearlyReport.getAverageIncome());

            } else if (command.equals(":q!")){
                System.out.println("Пока!");
                break;

            } else {
                System.out.println("Извините, такой команды пока нет.");

            }
        }
    }

    public static void printMenu() {
        System.out.println("\n Что вы хотите сделать? ");
        System.out.println(" 1  - Считать все месячные отчёты");
        System.out.println(" 2  - Считать годовой отчёт");
        System.out.println(" 3  - Сверить отчёты");
        System.out.println(" 4  - Вывести информацию о всех месячных отчётах");
        System.out.println(" 5  - Вывести информацию о годовом отчёте");
        System.out.println(":q! - Выход");
    }


}

