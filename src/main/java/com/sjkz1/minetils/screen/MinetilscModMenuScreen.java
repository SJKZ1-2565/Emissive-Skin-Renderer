package com.sjkz1.minetils.screen;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.config.MinetilsConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.io.IOException;

public class MinetilscModMenuScreen implements ModMenuApi
{
	  @Override
	    public ConfigScreenFactory<?> getModConfigScreenFactory() {
			return this::createConfigScreen;
		}
	  private Screen createConfigScreen(Screen screen)
	    {
	        MinetilsConfig config = Minetils.CONFIG.getConfig();
	        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(screen).setTitle(new TranslatableComponent("minetils.config.title"));
	        builder.setSavingRunnable(() ->
	        {
	            try
	            {
	            	Minetils.CONFIG.saveConfig();
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


			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("minetils.config.hp_detector"), config.showHealthStatus).setTooltip(new TextComponent("Render entity HP, on their name tag.")).setSaveConsumer(value -> config.showHealthStatus = value).setDefaultValue(true).build());
			generalCategory.addEntry(entry.startIntSlider(new TranslatableComponent("minetils.config.y_hp_detector"), config.yPositionHealthStatus,1,3).setTooltip(new TextComponent("Y position of health status.")).setSaveConsumer(value -> config.yPositionHealthStatus = value).setDefaultValue(1).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("minetils.config.tamed_horse_detector"), config.showTamedHorse).setTooltip(new TextComponent("Show tame data.")).setSaveConsumer(value -> config.showTamedHorse = value).setDefaultValue(false).build());
			generalCategory.addEntry(entry.startIntSlider(new TranslatableComponent("minetils.config.y_tamed_horse_detector"), config.yPositionHorseDisplay,1,3).setTooltip(new TextComponent("Y position of display tamed horse")).setSaveConsumer(value -> config.yPositionHorseDisplay = value).setDefaultValue(2).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("minetils.config.ignore_villager"), config.IgnoreHittingVillager).setTooltip(new TextComponent("Ignore attack village.")).setSaveConsumer(value -> config.IgnoreHittingVillager = value).setDefaultValue(true).build());

			colorCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("minetils.config.rainbow_health"), config.healthStatusRainbowColor).setTooltip(new TextComponent("Set rainbow color text.")).setSaveConsumer(value -> config.healthStatusRainbowColor = value).setDefaultValue(false).build());
			colorCategory.addEntry(entry.startColorField(new TranslatableComponent("minetils.config.health_color"), config.healthStatusColor).setTooltip(new TextComponent("Text color.")).setSaveConsumer(value -> config.healthStatusColor = value).setDefaultValue(0x4dff52).build());

			singlePlayerCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("minetils.config.swap"), config.SwapArmorAndElytra).setTooltip(new TextComponent("Swap armor and elytra.")).setSaveConsumer(value -> config.SwapArmorAndElytra = value).setDefaultValue(true).build());

			specialMemberCategory.addEntry(entry.startBooleanToggle(new TranslatableComponent("minetils.config.glowing_skin"), config.glowingSkin).setTooltip(new TextComponent("Render glowing skin!")).setSaveConsumer(value -> config.glowingSkin = value).setDefaultValue(true).build());
			return builder.build();
	    }
}
