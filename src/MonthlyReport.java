import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MonthlyReport {
    public ArrayList<MonthData> monthDataList = new ArrayList<>();

    public void loadFile (String month, String path) {
        List <String> contentFile = readFileContents(path); //item_name, is_expense, quantity, sum_of_one

        for (int i = 1; i < contentFile.size(); i++) {
            String line = contentFile.get(i);
            String[] lineContent = line.split(",");
            String itemName = lineContent[0];
            boolean isExpense = Boolean.parseBoolean(lineContent[1]);
            int quantity = Integer.parseInt(lineContent[2]);
            int sumOfOne = Integer.parseInt(lineContent[3]);
            MonthData monthData = new MonthData(itemName, isExpense, quantity, sumOfOne, month);
            monthDataList.add(monthData);
        }
    }
    /* Реализация считывания файла.
    Возвращает либо список строк содержимого файла, либо пустой спиок */
    public List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }
    // Получение названия месяца по числу:  "01" -> Январь
    public String getMonthName(String month) {
        String[] monthName = {"Январь", "Февраль", "Март"};
        HashMap <String, String> monthNameList = new HashMap<>();

        for (int i = 0; i < monthName.length; i++) {
            monthNameList.put("0" + (i + 1), monthName[i]);
        }
        return monthNameList.get(month);
    }
    /* Подсчёт самого прибыльного товара, для которого
    is_expense == false, a quantity * sum_of_one - максимально */
    public HashMap<String, Integer> getProfitableProduct(String month) {
        HashMap<String, Integer> nameProductAndSum = new HashMap<>();
        String nameProduct = null;
        int maxSumProduct = 0;
        for (MonthData monthData : monthDataList) {
            if (!monthData.month.equals(month) || monthData.isExpense) {
                continue;
            }
            int totalSumOfProducts = monthData.getTotalSum();
            if (maxSumProduct < totalSumOfProducts) {
                maxSumProduct = totalSumOfProducts;
                nameProduct = monthData.itemName;
            }
        }
        nameProductAndSum.put(nameProduct, maxSumProduct);
        return nameProductAndSum;
    }
    //Получение самой большой траты
    public HashMap<String, Integer> getMaxExpense(String month) {
        HashMap<String, Integer> nameExpenseAndSum = new HashMap<>();
        String nameExpense = null;
        int maxExpense = 0;
        for (MonthData monthData : monthDataList) {
            if (!monthData.month.equals(month) || !monthData.isExpense) {
                continue;
            }
            int currentTotalExpense = monthData.getTotalSum();
            if (maxExpense < currentTotalExpense) {
                maxExpense = currentTotalExpense;
                nameExpense = monthData.itemName;
            }
        }
        nameExpenseAndSum.put(nameExpense, maxExpense);
        return nameExpenseAndSum;
    }
}
