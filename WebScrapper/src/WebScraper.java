import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class WebScraper {
    public static void main(String[] args) {
        String url = "https://app.apollo.io/#/people?finderViewId=5a205be19a57e40c095e1d5f&personTitles[]=owner&personTitles[]=founder&personTitles[]=ceo&personTitles[]=managing%20director&page=1&personLocations[]=United%20States&organizationLocations[]=United%20States&organizationNumEmployeesRanges[]=1%2C10&organizationIndustryTagIds[]=5567cd82736964540d0b0000&includedOrganizationKeywordFields[]=tags&includedOrganizationKeywordFields[]=name&contactEmailStatus[]=verified&prospectedByCurrentTeam[]=yes";

        try {
            // Connect to the webpage and retrieve the HTML content
            Document document = Jsoup.connect(url).get();

            // Find all the <div> tags with class "result-row"
            Elements results = document.select("div.result-row");

            // Open a file for writing the scraped data
            FileWriter writer = new FileWriter("scraped_data.csv");
            writer.write("Name,Title,Company,Location,Industry\n");

            // Loop through the results and extract the data
            for (Element result : results) {
                // Extract the name of the person
                String name = result.select("div.result-name").text().trim();

                // Extract the title of the person
                String title = result.select("div.result-title").text().trim();

                // Extract the company name
                String company = result.select("div.result-company").text().trim();

                // Extract the company location
                String location = result.select("div.result-location").text().trim();

                // Extract the company industry
                String industry = result.select("div.result-industry").text().trim();

                // Write the data to the file
                writer.write(name + "," + title + "," + company + "," + location + "," + industry + "\n");
            }

            // Close the file writer and print a message indicating the data has been successfully scraped
            writer.close();
            System.out.println("Data has been successfully scraped and saved to scraped_data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

