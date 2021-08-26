package com.sjkz1.sjkz1misc.screen;

import java.io.IOException;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.config.SJKZ1MiscConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SJKZ1MiscModMenuScreen implements ModMenuApi
{
	  @Override
	    public ConfigScreenFactory<?> getModConfigScreenFactory() {
			return this::createConfigScreen;
		}
	  private Screen createConfigScreen(Screen screen)
	    {
	        SJKZ1MiscConfig config = SJKZ1Misc.CONFIG.getConfig();
	        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(screen).setTitle(new TranslatableText("sjkz1misc.config.title"));
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
	        ConfigCategory generalCategory = builder.getOrCreateCategory(Text.of("General Settings"));
	        ConfigCategory singlePlayerCategory = builder.getOrCreateCategory(Text.of("Single player Settings"));
	        ConfigCategory specialMemberCategory = builder.getOrCreateCategory(Text.of("Special member Settings"));
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.show_fps"), config.showFps).setTooltip(Text.of("Show current fps, on your top right corner.")).setSaveConsumer(value -> config.showFps = value).setDefaultValue(true).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.hp_detector"), config.showHealthStatus).setTooltip(Text.of("Render entity HP, on their name tag.")).setSaveConsumer(value -> config.showHealthStatus = value).setDefaultValue(true).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.render_glint_on_cape"), config.renderglintncape).setTooltip(Text.of("Render glint effect when armor is enchanted.")).setSaveConsumer(value -> config.renderglintncape = value).setDefaultValue(true).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.ignore_villager"), config.IgnoreHittingVillager).setTooltip(Text.of("Ignore attack village.")).setSaveConsumer(value -> config.IgnoreHittingVillager = value).setDefaultValue(true).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.rainbow"), config.rainbowColor).setTooltip(Text.of("Set rainbow color text.")).setSaveConsumer(value -> config.rainbowColor = value).setDefaultValue(true).build());
			generalCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.coloredEnderman"), config.coloredEnderman).setTooltip(Text.of("Render colored enderman to easier to finding.")).setSaveConsumer(value -> config.coloredEnderman = value).setDefaultValue(false).build());
			generalCategory.addEntry(entry.startColorField(new TranslatableText("sjkz1misc.config.color"), config.color).setTooltip(Text.of("Text color.")).setSaveConsumer(value -> config.color = value).setDefaultValue(config.color).build());

			singlePlayerCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.swap"), config.SwapArmorAndElytra).setTooltip(Text.of("Swap armor and elytra.")).setSaveConsumer(value -> config.SwapArmorAndElytra = value).setDefaultValue(true).build());
			singlePlayerCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.show_fire_debris"), config.ShowDebrisUnderFire).setTooltip(Text.of("Easiest way to find Ancient Debris.")).setSaveConsumer(value -> config.ShowDebrisUnderFire = value).setDefaultValue(true).build());
			singlePlayerCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.discord_message"), config.enableDiscordMessage).setTooltip(Text.of("Enable Discord Message.")).setSaveConsumer(value -> config.enableDiscordMessage = value).setDefaultValue(false).build());

			specialMemberCategory.addEntry(entry.startBooleanToggle(new TranslatableText("sjkz1misc.config.glowing_skin"), config.glowingSkin).setTooltip(Text.of("Render glowing skin!")).setSaveConsumer(value -> config.glowingSkin = value).setDefaultValue(true).build());
			return builder.build();
	    }
}
