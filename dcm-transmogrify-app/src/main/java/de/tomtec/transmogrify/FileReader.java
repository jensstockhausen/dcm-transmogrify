package de.tomtec.transmogrify;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.io.DicomInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;


public class FileReader
{
    public static final Logger LOG = LoggerFactory.getLogger(FileReader.class);

    private File file = null;

    private Attributes dcm = null;


    public Attributes dcm()
    {
        return dcm;
    }

    public FileReader(Path filePath)
    {
        LOG.info("reading file: {}", filePath.toAbsolutePath().toString());

        file = filePath.toFile();
    }

    /*
    DICOM files with a header have the ASCII signature "DICM" at byte offset 128.
     */
    boolean isDicom()
    {
        FileInputStream inStream;

        try
        {
            inStream = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            LOG.warn("file does not exist");
            return false;
        }

        byte[] tag = new byte[] { 'D', 'I', 'C', 'M'};
        byte[] buffer = new byte[4];

        try
        {
            inStream.skip(128);
            inStream.read(buffer);
            inStream.close();
        }
        catch (IOException e)
        {
            LOG.warn("error reading file {}", e.getMessage());
            return false;
        }

        for (int i = 0; i<4; i++)
        {
            if (buffer[i] != tag[i])
            {
                return false;
            }
        }

        return true;
    }


    public boolean scan()
    {
        DicomInputStream  dis = null;

        try
        {
            dis = new DicomInputStream(file);
        }
        catch (IOException e)
        {
            LOG.error("reading dicom file {}", e.getMessage());

            return false;
        }

        dis.setIncludeBulkData(DicomInputStream.IncludeBulkData.NO);

        try
        {
            dcm = dis.readDataset(-1, -1);
            dis.close();


        }
        catch (IOException e)
        {
            LOG.error("parsing dicom file {}", e.getMessage());

            return false;
        }

        return true;
    }

}
