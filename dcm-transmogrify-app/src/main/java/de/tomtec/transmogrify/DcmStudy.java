package de.tomtec.transmogrify;

import org.dcm4che3.data.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jens on 17/07/16.
 */
public class DcmStudy
{
    public static final Logger LOG = LoggerFactory.getLogger(DcmStudy.class);

    private String instance;

    private Map<String,DcmSeries> series = new HashMap<>();

    public DcmStudy(FileReader reader)
    {
        instance = reader.dcm().getString(Tag.StudyInstanceUID);
    }

    public String instance()
    {
        return instance;
    }

    public DcmSeries updateSeries(FileReader reader)
    {
        String seriesInstance = reader.dcm().getString(Tag.SeriesInstanceUID);

        LOG.debug("update series {}", seriesInstance);

        DcmSeries ser;

        if (!series.containsKey(seriesInstance))
        {
            ser = new DcmSeries(reader);
            series.put(seriesInstance, ser);
        }
        else
        {
            ser = series.get(seriesInstance);
        }

        return ser;
    }

    Collection<DcmSeries> series()
    {
        return series.values();
    }


}
