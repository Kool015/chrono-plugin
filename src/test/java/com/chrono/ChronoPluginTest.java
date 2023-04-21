package com.chrono;

import com.chrono.gpu.ChronoGpuPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ChronoPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ChronoPlugin.class, ChronoGpuPlugin.class);
		RuneLite.main(args);
	}
}