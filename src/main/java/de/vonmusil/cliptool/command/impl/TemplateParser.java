package de.vonmusil.cliptool.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParser
{
	public String template;

	public TemplateParser(String template)
	{
		this.template = template;
	}

	public List<String> findVariables()
	{
		final List<String> variables = new ArrayList<>();
		final Matcher matcher = Pattern.compile("(\\$\\{(.+?)\\})").matcher(template);
		while (matcher.find())
		{
			final String varname = matcher.group(2);
			variables.add(varname);
		}
		
		return variables;
	}
}