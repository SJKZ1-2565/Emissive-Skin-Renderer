package com.sjkz1.emissive_skin_renderer.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EnglishLanguageGen extends FabricLanguageProvider {
    protected EnglishLanguageGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.title", "Emissive Skin Renderer");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.glowingSkin", "Glowing Skin");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.glowingHorseArmor", "Glowing horse armor");
        translationBuilder.add("create_skin", "Create Skin");
        translationBuilder.add("delete_rate", "Color delta disappear rate");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.palletsRate", "Color delta disappear rate");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.renderPlayerNameInThirdPerson", "Render Name tag in F5");
    }
}
