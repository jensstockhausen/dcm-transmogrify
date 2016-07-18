package de.tomtec.transmogrify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by jens on 17/07/16.
 */
public class FileRenamer
{
    public static final Logger LOG = LoggerFactory.getLogger(FileReader.class);

    private InputReader reader;
    private String output;

    public FileRenamer(InputReader reader, String output)
    {
        this.reader = reader;
        this.output = output;
    }


    public boolean write()
    {
        Path outFolder = Paths.get(output);

        LOG.info("rename {} files to output folder {}",
                reader.getDcmFiles().size(),
                outFolder.toAbsolutePath());

        try
        {
            Files.createDirectory(outFolder);
        }
        catch (IOException e)
        {
            LOG.error("creating output folder {}", e.getMessage());
            return false;
        }

        LOG.info("found {} studies", reader.getStudies().size());

        for (DcmStudy study : reader.getStudies().values())
        {
            Path studyFolder = outFolder.resolve(study.instance());

            LOG.info("create study folder {}", studyFolder.toAbsolutePath());

            try
            {
                Files.createDirectory(studyFolder);
            }
            catch (IOException e)
            {
                LOG.error("creating folder {}", e.getMessage());
            }
        }


        return true;
    }
}
