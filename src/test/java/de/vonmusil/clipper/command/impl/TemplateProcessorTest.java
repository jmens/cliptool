package de.vonmusil.clipper.command.impl;

import static de.vonmusil.clipper.command.impl.TemplateProcessor.newProcessor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.vonmusil.clipper.gui.mainframe.paraminput.ParamEditModel.Parameter;

public class TemplateProcessorTest
{    
    @Test
    public void testInitializing() throws Exception
    {
        final TemplateProcessor processor = TemplateProcessor.newProcessor(); 
        assertThat(processor, notNullValue());
    }
    
    @Test
    public void testSettingTemplate() throws Exception
    {
        final TemplateProcessor processor = TemplateProcessor.newProcessor().setTemplate("");
        
        assertThat(processor.process(), equalTo(""));
    }
    
    @Test
    public void testReplacingWithoutVars() throws Exception
    {
        assertThat(newProcessor().setTemplate("Hello world").process(), equalTo("Hello world"));
        assertThat(newProcessor().setTemplate("Hallo ${world}").process(), equalTo("Hallo ${world}"));
    }
    
    @Test
    public void testReplacingWithOneBinding() throws Exception
    {
        final TemplateProcessor processor = newProcessor().setTemplate("Hallo ${world}");
        
        processor.bind("world", "Phil");
        
        assertThat(processor.process(), equalTo("Hallo Phil"));
    }
    
    @Test
    public void testReplacingWithMoreBindings() throws Exception
    {
        final TemplateProcessor processor = newProcessor().setTemplate("Hallo ${world}. ${greeting}?");
        
        processor.bind("world", "Phil");
        assertThat(processor.process(), equalTo("Hallo Phil. ${greeting}?"));

        processor.bind("greeting", "How are you");
        assertThat(processor.process(), equalTo("Hallo Phil. How are you?"));
        
        processor.bind("unused", "abng");
        assertThat(processor.process(), equalTo("Hallo Phil. How are you?"));
    }
    
    @Test
    public void testReplacingOfMultipleOccurences() throws Exception
    {
        final TemplateProcessor processor = newProcessor().setTemplate("${one} two ${one} two ${three} two ${one}");
    
        processor.bind("one", "1");
        
        assertThat(processor.process(), equalTo("1 two 1 two ${three} two 1"));
    }
    
    @Test
    public void testBulkBinding() throws Exception
    {
        final TemplateProcessor processor = newProcessor().setTemplate("${one} two ${one} ${two} ${three} two ${one}");
        
        final Set<Parameter> bindings = new HashSet<>(); 
        bindings.add(new Parameter("one", "1")); 
        bindings.add(new Parameter("two", "2")); 
        bindings.add(new Parameter("three", "3")); 
        
        processor.bind(bindings);
        
        assertThat(processor.process(), equalTo("1 two 1 2 3 two 1"));
    }

}
