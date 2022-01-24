package com.sjkz1.minetils.gui.screen;

import java.io.IOException;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.config.MinetilsConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class MinetilscModMenuScreen implements ModMenuApi
{
	  @Override
	    public ConfigScreenFactory<?> getModConfigScreenFactory() {
			return this::createConfigScreen;
		}
	  private Screen createConfigScreen(Screen screen)
	    {
	        MinetilsConfig config = Minetils.CONFIG.getConfig();
	        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(screen).setTitle(new TranslatableText("minetils.config.title"));
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
	        ConfigCategory generalCategory = builder.getOrCreateCategory(Text.of("General Settings"));
	        ConfigCategory singlePlayerCategory = builder.getOrCreateCategory(Text.of("Single player Settings"));
	        ConfigCategory specialMemberCategory = builder.getOrCreateCategory(Text.of("Special member Settings"));
	        ConfigCategory colorCategory = builder.getOrCreateCategory(Text.of("Color Settings"));


			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.hp_detector"), config.showHealthStatus).setTooltip(Text.of("Render entity HP, on their name tag.")).setSaveConsumer(value -> config.showHealthStatus = value).setDefaultValue(false).build());
			generalCategory.addEntry(entry.startIntSlider(new TranslatableText("minetils.config.y_hp_detector"), config.yPositionHealthStatus,1,3).setTooltip(Text.of("Y position of health status.")).setSaveConsumer(value -> config.yPositionHealthStatus = value).setDefaultValue(1).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.tamed_horse_detector"), config.showTamedHorse).setTooltip(Text.of("Show tame horses.")).setSaveConsumer(value -> config.showTamedHorse = value).setDefaultValue(false).build());
			generalCategory.addEntry(entry.startIntSlider(new TranslatableText("minetils.config.y_tamed_horse_detector"), config.yPositionHorseDisplay,1,3).setTooltip(Text.of("Y position of display tamed horse")).setSaveConsumer(value -> config.yPositionHorseDisplay = value).setDefaultValue(2).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.ignore_villager"), config.IgnoreHittingVillager).setTooltip(Text.of("Ignore attack village.")).setSaveConsumer(value -> config.IgnoreHittingVillager = value).setDefaultValue(true).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.eating_anim"), config.enableEatingAnim).setTooltip(Text.of("Enable Eating Animation.")).setSaveConsumer(value -> config.enableEatingAnim = value).setDefaultValue(true).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.disableHurtCamera"), config.disableHurtCamera).setTooltip(Text.of("Disable shake camera when hurting.")).setSaveConsumer(value -> config.disableHurtCamera = value).setDefaultValue(false).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.manual_skin"), config.manualSkinEditor).setTooltip(Text.of("")).setSaveConsumer(value -> config.manualSkinEditor = value).setDefaultValue(true).build());


			colorCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.rainbow_health"), config.healthStatusRainbowColor).setTooltip(Text.of("Set rainbow color text.")).setSaveConsumer(value -> config.healthStatusRainbowColor = value).setDefaultValue(false).build());
			colorCategory.addEntry(entry.startColorField(new TranslatableText("minetils.config.health_color"), config.healthStatusColor).setTooltip(Text.of("Text color.")).setSaveConsumer(value -> config.healthStatusColor = value).setDefaultValue(0x4dff52).build());

			singlePlayerCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.swap"), config.SwapArmorAndElytra).setTooltip(Text.of("Swap armor and elytra.")).setSaveConsumer(value -> config.SwapArmorAndElytra = value).setDefaultValue(true).build());
			singlePlayerCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.particle"), config.disableBlockParticle).setTooltip(Text.of("Disable block particle.")).setSaveConsumer(value -> config.disableBlockParticle = value).setDefaultValue(false).build());

			specialMemberCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.glowing_skin"), config.glowingSkin).setTooltip(Text.of("Render glowing skin!")).setSaveConsumer(value -> config.glowingSkin = value).setDefaultValue(true).build());
			specialMemberCategory.addEntry(entry.startBooleanToggle(new TranslatableText("minetils.config.SpecialCape"), config.SpecialCape).setTooltip(Text.of("Render special cape!")).setSaveConsumer(value -> config.SpecialCape = value).setDefaultValue(false).build());
			return builder.build();
	    }
}
