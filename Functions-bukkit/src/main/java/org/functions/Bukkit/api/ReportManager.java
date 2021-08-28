package org.functions.Bukkit.api;

public interface ReportManager {
    int getReportMin();
    int getReportMax();
    int getReportRandom();
    void report(String report,String reason);
    void report(String report);
    String[] getReport();
    void check();
}
