import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class YearlyReport {
    public ArrayList<YearData> yearDataList = new ArrayList<>();

    public YearlyReport(int year, String path) {
        List <String> contentFile = readFileContents(path); //month, amount, is_expense

        for (int i = 1; i < contentFile.size(); i++) {
            String line = contentFile.get(i);
            String[] lineContent = line.split(",");
            String month = lineContent[0];
            int amount = Integer.parseInt(lineContent[1]);
            boolean isExpense = Boolean.parseBoolean(lineContent[2]);
            YearData yearData = new YearData(month, amount, isExpense, year);
            yearDataList.add(yearData);
        }

    }

    public List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }

    public int getYear(){
        return yearDataList.get(0).year;
    }

    public String getMonthName(String month) {
        String[] monthName = {"Январь", "Февраль", "Март"};
        HashMap <String, String> monthNameList = new HashMap<>();

        for (int i = 0; i < monthName.length; i++) {
            monthNameList.put("0" + (i + 1), monthName[i]);
        }
        return monthNameList.get(month);

    }
    // Расчет прибыли по каждому месяцу
    public HashMap <String, Integer> getProfit(String month) {
        HashMap <String, Integer> profit = new HashMap<>();
        int income  = 0;
        int expense = 0;

        for (YearData yearData : yearDataList) {
            if (!yearData.month.equals(month)) {
                continue;
            }
            if (yearData.isExpense) {
                expense = yearData.amount;
            } else {
                income = yearData.amount;
            }
        }
        profit.put(month, income - expense);
        return profit;
    }
    // Получение среднего расхода за все месяцы
    public int getAverageExpense() {
        int countExpense = 0;
        int countMonth = yearDataList.size() / 2; // по 2 записи на каждый месяц

        for (YearData yearData : yearDataList) {
            if (!yearData.isExpense) {
                continue;
            }
            countExpense += yearData.amount;
        }
        return countExpense / countMonth;
    }
    // Получение среднего дохода за все месяцы
    public int getAverageIncome() {
        int countIncome = 0;
        int countMonth = yearDataList.size() / 2;
        for (YearData yearData : yearDataList) {
            if (yearData.isExpense) {
                continue;
            }
            countIncome += yearData.amount;
        }
        return countIncome / countMonth;

    }
}

