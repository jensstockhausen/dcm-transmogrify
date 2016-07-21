package de.tomtec.transmogrify;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jens on 17/07/16.
 */
public class DcmInstance
{
    private FileReader reader;
    private String instance;
    private List<String> refInstances = new ArrayList<>();

    public DcmInstance(FileReader reader)
    {
        this.reader = reader;
        instance = reader.dcm().getString(Tag.SOPInstanceUID);

        Sequence seq = reader.dcm().getSequence(Tag.SourceImageSequence);

        if (seq != null)
        {
            for (Attributes attrib : seq)
            {
                refInstances.add(attrib.getString(Tag.ReferencedSOPInstanceUID));
            }
        }

    }

    public String instance()
    {
        return instance;
    }

    public List<String> references()
    {
        return refInstances;
    }


}
