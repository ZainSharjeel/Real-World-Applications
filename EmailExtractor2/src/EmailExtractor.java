import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EmailExtractor {
    public static void main(String[] args) throws IOException {
        // Create a list to store the extracted emails
        List<String> emails = new ArrayList<>();

        // Create a pattern to match email addresses
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");

        // Open the Excel file
        FileInputStream file = new FileInputStream("emails.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        // Iterate through the rows and cells
        for (Row row : sheet) {
            for (Cell cell : row) {
                // Get the cell value as a string
                String cellValue = cell.getStringCellValue();

                // Check if the cell value matches the email pattern
                Matcher matcher = pattern.matcher(cellValue);
                while (matcher.find()) {
                    // Add the matched email to the list
                    emails.add(matcher.group());
                }
            }
        }

        // Close the workbook and file input stream
        workbook.close();
        file.close();

        // Print the extracted emails
        for (String email : emails) {
            System.out.println(email);
        }
    }
}