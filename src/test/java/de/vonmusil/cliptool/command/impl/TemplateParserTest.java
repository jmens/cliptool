package de.vonmusil.cliptool.command.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TemplateParserTest
{

	@Test
	public void testFindNoVariables()
	{
		final TemplateParser parser = new TemplateParser("abc");

		final List<String> variables = parser.findVariables();

		assertThat(variables, hasSize(0));
	}

	@Test
	public void testFindOneVariable()
	{
		final TemplateParser parser = new TemplateParser("abc ${hallo}");

		final List<String> variables = parser.findVariables();

		assertThat(variables, hasSize(1));
	}

	@Test
	public void testFindCorrectVariable()
	{
		final TemplateParser parser = new TemplateParser("abc ${hallo}");

		final List<String> variables = parser.findVariables();

		assertThat(variables, hasItem("hallo"));
	}

	@Test
	public void testFindTwoVariables()
	{
		final TemplateParser parser = new TemplateParser("abc ${hallo} lieber ${user}");

		final List<String> variables = parser.findVariables();

		assertThat(variables, hasItem("hallo"));
		assertThat(variables, hasItem("user"));
	}

	@Test
	public void testFindThreeVariablesVariation2()
	{
		final TemplateParser parser = new TemplateParser("${this}${is}${sparta}${!");

		final List<String> variables = parser.findVariables();

		assertThat(variables, hasItem("this"));
		assertThat(variables, hasItem("is"));
		assertThat(variables, hasItem("sparta"));
	}

	@Test
	public void testFindEmptyVariable()
	{
		final TemplateParser parser = new TemplateParser("I have an ${} empty var");

		final List<String> variables = parser.findVariables();

		assertThat(variables, hasSize(0));
	}

	@Test
	public void testFindNoDefaultValue()
	{
		final TemplateParser parser = new TemplateParser("");

		final Map<String, String> values = parser.findDefaultValues();

		assertThat(values.size(), equalTo(0));
	}

	@Test
	public void testFindDefaultValue()
	{
		final TemplateParser parser = new TemplateParser("${x|1}");

		final Map<String, String> values = parser.findDefaultValues();

		assertThat(values.size(), equalTo(1));
		assertThat(values, hasEntry("x", "1"));
	}

	@Test
	public void testFindDefaulValuesMixed()
	{
		final TemplateParser parser = new TemplateParser("This ${x|1} is ${y} a ${name|me} test.");

		final Map<String, String> values = parser.findDefaultValues();

		assertThat(values.size(), equalTo(3));
		assertThat(values, hasEntry("x", "1"));
		assertThat(values, hasEntry("y", ""));
		assertThat(values, hasEntry("name", "me"));
	}

}
