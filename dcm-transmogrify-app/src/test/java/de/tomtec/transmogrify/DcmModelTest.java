package de.tomtec.transmogrify;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jens on 17/07/16.
 */
public class DcmModelTest
{
    Path dcmFile = Paths.get("src/test/resources/dcm/us-mono.dcm");

    @Test
    public void studyIsConstructed() throws Exception
    {
        FileReader reader = new FileReader(dcmFile);
        reader.scan();

        DcmStudy study = new DcmStudy(reader);
        assertThat(study.instance(), is(equalTo("999.999.2.19941105.134500")));
    }

    @Test
    public void seriesIsCreated() throws Exception
    {
        FileReader reader = new FileReader(dcmFile);
        reader.scan();

        DcmStudy study = new DcmStudy(reader);
        DcmSeries series = study.updateSeries(reader);

        assertThat(series.instance(), is(equalTo("999.999.2.19941105.134500.2")));
    }


    @Test
    public void instanceIsCreated() throws Exception
    {
        FileReader reader = new FileReader(dcmFile);
        reader.scan();

        DcmStudy study = new DcmStudy(reader);
        DcmSeries series = study.updateSeries(reader);
        DcmInstance inst = series.updateInstances(reader);

        assertThat(inst.instance(), is(equalTo("999.999.2.19941105.134500.2.101")));
    }
}
