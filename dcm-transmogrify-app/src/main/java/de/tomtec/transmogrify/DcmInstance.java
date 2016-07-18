package de.tomtec.transmogrify;

import org.dcm4che3.data.Tag;

/**
 * Created by jens on 17/07/16.
 */
public class DcmInstance
{
    private FileReader reader;
    private String instance;

    public DcmInstance(FileReader reader)
    {
        this.reader = reader;
        instance = reader.dcm().getString(Tag.SOPInstanceUID);
    }


    public String instance()
    {
        return instance;
    }

}
