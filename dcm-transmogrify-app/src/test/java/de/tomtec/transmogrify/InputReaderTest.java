package de.tomtec.transmogrify;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class InputReaderTest
{

    String root = ("src/test/resources/");

    @Test
    public void canFindFiles()
    {
        InputReader input = new InputReader(root);

        assertThat(input.scan(), is(equalTo(3)));
    }

    @Test
    public void canAnalyseFiles() throws Exception
    {
        InputReader input = new InputReader(root);

        input.analyse();
    }
}
