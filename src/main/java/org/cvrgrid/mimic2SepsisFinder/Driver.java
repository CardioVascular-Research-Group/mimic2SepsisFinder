package org.cvrgrid.mimic2SepsisFinder;


import org.apache.commons.cli.*;
import org.cvrgrid.mimic2SepsisFinder.ApplicationConfigs;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Application to upload CSV files into OpenTSDB.
 * Created by rliu14 on 7/8/16.
 */
public class Driver {

    static Options options = new Options();

    /**
     * Registers command line interface options.
     */
    private static void registerOptions() {
        Option version = new Option("v", "version", false, "Displays version information");

        Option filename = new Option("f", "filename", true, "File to read");

        options.addOption(version);
        options.addOption(filename);
    }

    private static void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java -jar mimic2SepsisFinder.jar", options);
    }

    public static void main(String[] args) {

        registerOptions();

        CommandLineParser parser = new BasicParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            ApplicationContext context =  new AnnotationConfigApplicationContext(ApplicationConfigs.class);

            if (cmd.hasOption("version")) {
                String versionInfo = (String)context.getBean("version");
                System.out.println(versionInfo);

            } else if (cmd.hasOption("filename")) {
                String filename = cmd.getOptionValue("filename");
                 
                mimic2SepsisFinderFacade facade = (mimic2SepsisFinderFacade)context.getBean("mimic2SepsisFinderFacade");
                facade.validateTrew(filename);

            } else {
                printHelp();
            }

        } catch (ParseException | IOException | java.text.ParseException e) {
            System.err.println(e.getMessage());
        }

    }
}
