package de.tomtec.transmogrify;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jens on 16/07/16.
 */
public class FileReaderTest
{
    Path dcmFile = Paths.get("src/test/resources/dcm/us-mono.dcm");
    Path pngFile = Paths.get("src/test/resources/img/nm-mono-0.png");
    Path noFile = Paths.get("asdf/asdf/asdf/asdf.dcm");

    @Test
    public void canDetectDicom()
    {
        FileReader reader = new FileReader(dcmFile);

        assertThat(reader.isDicom(), is(true));
    }

    @Test
    public void nonDicomFilesNotDetected()
    {
        FileReader reader = new FileReader(pngFile);

        assertThat(reader.isDicom(), is(false));
    }

    @Test
    public void unkownFileIsNoDicom()
    {
        FileReader reader = new FileReader(noFile);

        assertThat(reader.isDicom(), is(false));
    }

    @Test
    public void canReadDicom()
    {
        FileReader reader = new FileReader(dcmFile);

        assertThat(reader.scan(), is(equalTo(true)));
        assertThat(reader.dcm(), is(notNullValue()));
    }

    @Test
    public void nonDicomFileCannotBeAnalysed()
    {
        FileReader reader = new FileReader(pngFile);

        assertThat(reader.scan(), is(equalTo(false)));
        assertThat(reader.dcm(), is(nullValue()));
    }


}
