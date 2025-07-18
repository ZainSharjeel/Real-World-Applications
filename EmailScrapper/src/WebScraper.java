import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScraper {

    private static final int MAX_PAGES = 10; // Maximum number of pages to scrape
    private static Set<String> visitedUrls = new HashSet<>(); // To keep track of visited URLs

    public static void main(String[] args) {
        // Specify the URL of the website to scrape
        String websiteUrl = "https://firstsolar.com";

        // Scrape the emails from the website
        String[] emails = scrapeEmails(websiteUrl);

        // Save the emails to a CSV file
        String csvFilename = "emails.csv";
        saveEmailsToCsv(emails, csvFilename);
    }

    public static String[] scrapeEmails(String url) {
        String[] emails = {};

        try {
            // Send a GET request to the website and parse the HTML content
            Document document = Jsoup.connect(url).get();
            visitedUrls.add(url);

            // Extract email addresses using regular expressions
            String textContent = document.text();
            String emailPattern = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(textContent);

            // Collect all matching email addresses
            while (matcher.find()) {
                String email = matcher.group();
                emails = appendToArray(emails, email);
            }

            // Crawl linked pages recursively (up to MAX_PAGES)
            if (visitedUrls.size() < MAX_PAGES) {
                Elements links = document.select("a[href]");
                for (Element link : links) {
                    String linkedUrl = link.absUrl("href");
                    if (!visitedUrls.contains(linkedUrl) && !linkedUrl.startsWith("javascript:")) {
                        try {
                            String[] linkedEmails = scrapeEmails(linkedUrl);
                            emails = mergeArrays(emails, linkedEmails);
                        } catch (Exception e) {
                            // Handle exceptions while scraping linked pages
                            System.err.println("Error scraping URL: " + linkedUrl);
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Handle exceptions while connecting to the website
            System.err.println("Error connecting to URL: " + url);
            e.printStackTrace();
        }

        return emails;
    }

    public static void saveEmailsToCsv(String[] emails, String filename) {
        try {
            FileWriter csvWriter = new FileWriter(filename);

            // Write the header row
            csvWriter.append("Email");
            csvWriter.append("\n");

            // Write each email address in a separate row
            for (String email : emails) {
                csvWriter.append(email);
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] appendToArray(String[] array, String element) {
        String[] newArray = new String[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = element;
        return newArray;
    }

    public static String[] mergeArrays(String[] array1, String[] array2) {
        String[] mergedArray = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, mergedArray, 0, array1.length);
        System.arraycopy(array2, 0, mergedArray, array1.length, array2.length);
        return mergedArray;
    }
}
