public class YearData {
    // Данные файла y.YYYY.csv
    public String month;
    public int amount;
    public boolean isExpense;
    public int year;

    public YearData(String month, int amount, boolean isExpense, int year) {
        this.month = month;
        this.amount = amount;
        this.isExpense = isExpense;
        this.year = year;
    }
}
