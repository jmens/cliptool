package de.vonmusil.cliptool.command.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.vonmusil.cliptool.gui.mainframe.CliptoolClipboard;

public class TemplateParser
{
	private final String template;
	private Map<String, String> defaultValues;

	public TemplateParser(String template)
	{
		this.template = template;
	}

	public List<String> findVariables()
	{
		if (defaultValues == null)
		{
			parse();
		}

		return new ArrayList<>(defaultValues.keySet());
	}

	public Map<String, String> findDefaultValues()
	{
		if (defaultValues == null)
		{
			parse();
		}

		return defaultValues;
	}

	private void parse()
	{
		this.defaultValues = new HashMap<>();

		final Matcher matcher = Pattern.compile("(\\$\\{(.+?)\\})").matcher(template);
		while (matcher.find())
		{
			final String[] parts = matcher.group(2).split("\\|");

			final String varname = parts[0];

			String defaultValue;
			if (parts.length > 1)
			{
				if ("clipboard".equalsIgnoreCase(parts[0]))
				{
					defaultValue = CliptoolClipboard.getInstance().getClipboardContent();
				}
				else if ("clipboard".equalsIgnoreCase(parts[1]))
				{
					defaultValue = CliptoolClipboard.getInstance().getClipboardContent();
				}
				else
				{
					defaultValue = parts[1];
				}
			}
			else
			{
				if ("clipboard".equalsIgnoreCase(parts[0]))
				{
					defaultValue = CliptoolClipboard.getInstance().getClipboardContent();
				}
				else
				{
					defaultValue = "";
				}
			}

			defaultValues.put(varname, defaultValue);
		}
	}
}