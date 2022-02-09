package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following:
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        (Tabs and other whitespace have been added here for clarity.)  Note the
        curly braces, square brackets, and double-quotes!  These indicate which
        values should be encoded as strings, and which values should be encoded
        as integers!  The data files which contain this CSV and JSON data are
        given in the "resources" package of this project.
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity and readability.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            // INSERT YOUR CODE HERE
            
            String[] storage;
            JSONArray rowHeaders = new JSONArray();
            JSONArray colHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            JSONArray intermediary;
            JSONObject output = new JSONObject();
            
            storage = iterator.next();
            
            for (int i = 0; i < storage.length; ++i) {
           
                colHeaders.add(storage[i]);
            
            } //END FOR
            
            
            
            while (iterator.hasNext()) {
                
                storage = iterator.next();
                rowHeaders.add(storage[0]);
                
                intermediary = new JSONArray();
                
                for (int i = 1; i < storage.length; ++i) {
                    
                    int integerParser = Integer.parseInt(storage[i]);
                    intermediary.add(integerParser);
                    
                } //END FOR
                
                data.add(intermediary);
            
            } //END WHILE
            
            output.put("colHeaders", colHeaders);
            output.put("rowHeaders", rowHeaders);
            output.put("data", data);
            
            results = JSONValue.toJSONString(output);
            
        } //END TRY AND INSERTED CODE
                    
        catch(Exception e) { e.printStackTrace(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            
            // INSERT YOUR CODE HERE
            
            JSONParser parser = new JSONParser();
            JSONObject input = (JSONObject)parser.parse(jsonString);
            JSONArray colHeaders = (JSONArray)input.get("colHeaders");
            JSONArray rowHeaders = (JSONArray)input.get("rowHeaders");
            JSONArray data = (JSONArray)input.get("data");
            JSONArray intermediary;
            String[] storage = new String[colHeaders.size()];
            
            for (int i = 0; i < colHeaders.size(); ++i) {
               
                storage[i] = (String) colHeaders.get(i);
            
            } //END FOR
            
            csvWriter.writeNext(storage);
            
            for(int i = 0; i < data.size(); ++i) {
                
                intermediary = (JSONArray) data.get(i);
                storage = new String[intermediary.size() + 1];
                storage[0] = (String) rowHeaders.get(i);
                
                for (int j = 0; j < intermediary.size(); ++j) {
                   
                    storage[j + 1] = Long.toString((long)intermediary.get(j));
                
                } //END NESTED FOR
                
                csvWriter.writeNext(storage);

            } //END FOR

            results = writer.toString();
    
        } //END TRY AND INSERTED CODE 
        
        catch(Exception e) { e.printStackTrace(); }
        
        return results.trim();
        
    }

}