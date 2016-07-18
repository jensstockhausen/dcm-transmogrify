package de.tomtec.transmogrify.app;

import de.tomtec.transmogrify.FileRenamer;
import de.tomtec.transmogrify.InputReader;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application
{
    public static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args)
    {
        Application app = new Application(args);
    }

    private Options options = null;


    public Application(String[] args)
    {
        options = createOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try
        {
            cmd = parser.parse(options, args);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        transmogrify(cmd);
    }


    private Options createOptions()
    {
        Options opts = new Options();

        opts.addOption(Option.builder("h")
                .longOpt("help")
                .desc("display this help")
                .required(false)
                .build());

        opts.addOption(Option.builder("i")
                .longOpt("input")
                .hasArg(true)
                .argName("input folder")
                .desc("input folder to be analysed")
                .required(false)
                .build());

        opts.addOption(Option.builder("o")
                .longOpt("output")
                .hasArg(true)
                .argName("output folder")
                .desc("folder results are written to")
                .required(false)
                .build());

        opts.addOption(Option.builder("m")
                .longOpt("mode")
                .hasArg(true)
                .argName("opration mode")
                .desc("possible values: rename")
                .required(false)
                .build());

        return opts;
    }

    private void transmogrify(CommandLine cmd)
    {
        if (cmd == null)
        {
            return;
        }

        if (cmd.hasOption("h"))
        {

            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("dcm-transmogrifier-app", options);

            return;
        }

        String input = "./";
        String output = "./out";


        if (cmd.hasOption("i"))
        {
            input = cmd.getOptionValue("i");
        }

        if (cmd.hasOption("o"))
        {
            output = cmd.getOptionValue("o");
        }


        InputReader reader = new InputReader(input);

        reader.scan();
        reader.analyse();

        String mode = "";

        if (cmd.hasOption("m"))
        {
            mode = cmd.getOptionValue("m");
        }

        if (mode.equals("rename"))
        {
            FileRenamer rename = new FileRenamer(reader, output);

            rename.write();
        }


    }

}
