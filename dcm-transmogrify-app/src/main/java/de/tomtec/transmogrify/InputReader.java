package de.tomtec.transmogrify;

import org.apache.commons.io.FileUtils;
import org.dcm4che3.data.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InputReader
{
    public static final Logger LOG = LoggerFactory.getLogger(InputReader.class);

    Path rootPath = null;

    public List<FileReader> getDcmFiles()
    {
        return dcmFiles;
    }

    private List<FileReader> dcmFiles = new ArrayList<FileReader>();

    private Map<String, DcmStudy> studies = new HashMap<String, DcmStudy>();

    public InputReader(String input)
    {
        rootPath = Paths.get(input);
    }

    public Map<String, DcmStudy> getStudies()
    {
        return studies;
    }

    public int scan()
    {
        LOG.info("scanning folder {}", rootPath.toAbsolutePath().toString());

        List<File> files = (List<File>) FileUtils.listFiles(rootPath.toFile(), null, true);

        for(File file:files)
        {
            FileReader reader = new FileReader(file.toPath());

            if (reader.isDicom())
            {
                LOG.info("adding file {}", file.getAbsolutePath());
                dcmFiles.add(reader);
            }
        }

        return dcmFiles.size();
    }


    public void analyse()
    {
        for (FileReader reader: dcmFiles)
        {
            reader.scan();

            LOG.info("Study    {}", reader.dcm().getString(Tag.StudyInstanceUID));
            LOG.info("Series   {}", reader.dcm().getString(Tag.SeriesInstanceUID));
            LOG.info("Instance {}", reader.dcm().getString(Tag.SOPInstanceUID));

            DcmStudy study = updateStudy(reader);
            DcmSeries series = study.updateSeries(reader);
            DcmInstance instance = series.updateInstances(reader);
        }
    }

    private DcmStudy updateStudy(FileReader reader)
    {
        String studyInstance = reader.dcm().getString(Tag.StudyInstanceUID);

        LOG.info("update study {}", studyInstance);

        DcmStudy study = null;

        if (!studies.containsKey(studyInstance))
        {
            study = new DcmStudy(reader);
            studies.put(studyInstance, study);
        }
        else
        {
            study = studies.get(studyInstance);
        }

        return study;
    }

}
