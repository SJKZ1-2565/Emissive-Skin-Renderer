package com.sjkz1.emissive_skin_renderer.gui.screen;

import com.sjkz1.emissive_skin_renderer.config.EmissiveSkinRendererConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class EmissiveSkinRendererConfigModMenuScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(EmissiveSkinRendererConfig.class, parent).get();
    }
}
