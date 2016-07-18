package de.tomtec.transmogrify;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jens on 17/07/16.
 */
public class FileRenamerTest
{
    @Rule
    public TemporaryFolder tempFolder= new TemporaryFolder();

    String root = ("src/test/resources/");

    @Test
    public void outFolderIsCreated() throws Exception
    {
        InputReader input = new InputReader(root);

        String output = tempFolder.getRoot().getAbsolutePath() + "/out";

        FileRenamer ren = new FileRenamer(input, output);

        assertThat(ren.write(), is(equalTo(true)));
        assertThat(new File(output).exists(), is(equalTo(true)));

        assertThat(new File(output+"/").exists(), is(equalTo(true)));

    }


    @Test
    public void invalidOutFolderIsNotCreated() throws Exception
    {
        InputReader input = new InputReader(root);

        String output = "x:///asdf";

        FileRenamer ren = new FileRenamer(input, output);

        assertThat(ren.write(), is(equalTo(false)));
    }
}
