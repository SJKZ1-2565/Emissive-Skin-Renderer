package com.sjkz1.sjkz1misc.screen;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.config.SJKZ1MiscConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.io.IOException;

public class SJKZ1MiscModMenuScreen implements ModMenuApi
{
	  @Override
	    public ConfigScreenFactory<?> getModConfigScreenFactory() {
			return this::createConfigScreen;
		}
	  private Screen createConfigScreen(Screen screen)
	    {
	        SJKZ1MiscConfig config = SJKZ1Misc.CONFIG.getConfig();
	        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(screen).setTitle(new TranslatableComponent("sjkz1misc.config.title"));
	        builder.setSavingRunnable(() ->
	        {
	            try
	            {
	            	SJKZ1Misc.CONFIG.saveConfig();
	            }
	            catch (IOException e)
	            {
	                e.printStackTrace();
	            }
	        });

	        ConfigEntryBuilder entry = ConfigEntryBuilder.create();
	        ConfigCategory generalCategory = builder.getOrCreateCategory(new TextComponent("General Settings"));
	        ConfigCategory singlePlayerCategory = builder.getOrCreateCategory(new TextComponent("Single player Settings"));
	        ConfigCategory specialMemberCategory = builder.getOrCreateCategory(new TextComponent("Special member Settings"));
	        ConfigCategory colorCategory = builder.getOrCreateCategory(new TextComponent("Color Settings"));


			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("sjkz1misc.config.hp_detector"), config.showHealthStatus).setTooltip(new TextComponent("Render entity HP, on their name tag.")).setSaveConsumer(value -> config.showHealthStatus = value).setDefaultValue(true).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("sjkz1misc.config.ignore_villager"), config.IgnoreHittingVillager).setTooltip(new TextComponent("Ignore attack village.")).setSaveConsumer(value -> config.IgnoreHittingVillager = value).setDefaultValue(true).build());

			colorCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("sjkz1misc.config.rainbow_fps"), config.rainbowFpsColor).setTooltip(new TextComponent("Set rainbow color text.")).setSaveConsumer(value -> config.rainbowFpsColor = value).setDefaultValue(false).build());
			colorCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("sjkz1misc.config.rainbow_health"), config.healthStatusRainbowColor).setTooltip(new TextComponent("Set rainbow color text.")).setSaveConsumer(value -> config.healthStatusRainbowColor = value).setDefaultValue(false).build());
			colorCategory.addEntry(entry.startColorField(new TranslatableComponent("sjkz1misc.config.hi_fps_color"), config.HeightFPSColor).setTooltip(new TextComponent("Text color.")).setSaveConsumer(value -> config.HeightFPSColor = value).setDefaultValue(0x4dff52).build());
			colorCategory.addEntry(entry.startColorField(new TranslatableComponent("sjkz1misc.config.med_fps_color"), config.MediumFPSColor).setTooltip(new TextComponent("Text color.")).setSaveConsumer(value -> config.MediumFPSColor = value).setDefaultValue(0xf8fc05).build());
			colorCategory.addEntry(entry.startColorField(new TranslatableComponent("sjkz1misc.config.low_fps_color"), config.lowFPSColor).setTooltip(new TextComponent("Text color.")).setSaveConsumer(value -> config.lowFPSColor = value).setDefaultValue(0xff0000).build());
			colorCategory.addEntry(entry.startColorField(new TranslatableComponent("sjkz1misc.config.health_color"), config.healthStatusColor).setTooltip(new TextComponent("Text color.")).setSaveConsumer(value -> config.healthStatusColor = value).setDefaultValue(0x4dff52).build());

			singlePlayerCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("sjkz1misc.config.swap"), config.SwapArmorAndElytra).setTooltip(new TextComponent("Swap armor and elytra.")).setSaveConsumer(value -> config.SwapArmorAndElytra = value).setDefaultValue(true).build());

			specialMemberCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("sjkz1misc.config.glowing_skin"), config.glowingSkin).setTooltip(new TextComponent("Render glowing skin!")).setSaveConsumer(value -> config.glowingSkin = value).setDefaultValue(true).build());
			return builder.build();
	    }
}
