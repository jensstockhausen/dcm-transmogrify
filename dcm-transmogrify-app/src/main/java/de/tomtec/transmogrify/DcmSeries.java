package de.tomtec.transmogrify;

import org.dcm4che3.data.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jens on 17/07/16.
 */
public class DcmSeries
{
    public static final Logger LOG = LoggerFactory.getLogger(DcmSeries.class);

    private String instance;

    Map<String,DcmInstance> instances = new HashMap<>();


    public DcmSeries(FileReader reader)
    {
        instance = reader.dcm().getString(Tag.SeriesInstanceUID);
    }

    public String instance()
    {
        return instance;
    }


    public DcmInstance updateInstances(FileReader reader)
    {
        String instuid = reader.dcm().getString(Tag.SOPInstanceUID);

        LOG.info("update instance {}", instuid);

        DcmInstance ins;

        if (!instances.containsKey(instuid))
        {
            ins = new DcmInstance(reader);
            instances.put(instuid, ins);
        }
        else
        {
            ins = instances.get(instuid);
        }

        return ins;
    }
}
