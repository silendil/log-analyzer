package com.loganalyzer.starter;
import com.loganalyzer.Processor;
import com.loganalyzer.file.FileParser;

import java.util.Scanner;


public class Starter {
    public static void main(String ... args){
        try(Scanner scanner = new Scanner(System.in)){
            Processor processor = new Processor(new FileParser(scanner.next()));
            if(processor.process()){
                if(processor.readData() != null)
                    processor.readData().forEach(System.out::println);
            }else{
                System.out.println("Error data processing");
            }
        }
    }
}
