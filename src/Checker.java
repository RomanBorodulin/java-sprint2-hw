import java.util.HashMap;

public class Checker {
    public YearlyReport yearlyReport;
    public MonthlyReport monthlyReport;

    public Checker(YearlyReport yearlyReport, MonthlyReport monthlyReport) {
        this.yearlyReport = yearlyReport;
        this.monthlyReport = monthlyReport;
    }
    // Реализация сверки данных из двух разных отчётов
    public boolean check(){
        boolean check = true;
        HashMap <String, HashMap <Boolean, Integer>> tableByMonthlyReport = new HashMap<>(); // month -> is_expense -> amount
        for (MonthData monthData : monthlyReport.monthDataList) {
            if (!tableByMonthlyReport.containsKey(monthData.month)){
                tableByMonthlyReport.put(monthData.month, new HashMap<>());
            }
            HashMap<Boolean, Integer> mapToCount = tableByMonthlyReport.get(monthData.month);
            int totalSumOfProducts = monthData.quantity * monthData.sumOfOne;
            // Подсчитываем сумму доходов и расходов по каждому из месяцев
            mapToCount.put(monthData.isExpense, mapToCount.getOrDefault(monthData.isExpense, 0) + totalSumOfProducts);
        }

        HashMap <String, HashMap <Boolean, Integer>> tableByYearlyReport = new HashMap<>();
        for (YearData yearData : yearlyReport.yearDataList) {
            if (!tableByYearlyReport.containsKey(yearData.month)) {
                tableByYearlyReport.put(yearData.month, new HashMap<>());
            }
            HashMap<Boolean, Integer> mapToCount = tableByYearlyReport.get(yearData.month);
            mapToCount.put(yearData.isExpense, mapToCount.getOrDefault(yearData.isExpense, 0) + yearData.amount);
        }

        for (String month : tableByMonthlyReport.keySet()) {
            HashMap<Boolean, Integer> mapToCountByMonth = tableByMonthlyReport.get(month);
            HashMap<Boolean, Integer> mapToCountByYear = tableByYearlyReport.get(month);

            for (Boolean isExpense : mapToCountByMonth.keySet()) {
                int countByReportMonth = mapToCountByMonth.get(isExpense);
                int countByReportYear = mapToCountByYear.get(isExpense);
                if (countByReportMonth != countByReportYear) {
                    System.out.println("За " + monthlyReport.getMonthName(month)
                            + " была обнаружена ошибка при сверке данных.");
                    check = false;
                }
            }
        }
        return check;
    }

}
