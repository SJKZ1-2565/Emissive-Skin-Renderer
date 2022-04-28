package com.sjkz1.minetils.gui.screen;

import com.sjkz1.minetils.config.MinetilsConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class MinetilsConfigModMenuScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(MinetilsConfig.class, parent).get();
    }
}
