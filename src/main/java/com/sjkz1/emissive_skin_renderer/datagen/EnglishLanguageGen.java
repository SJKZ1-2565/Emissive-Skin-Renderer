package com.sjkz1.emissive_skin_renderer.datagen;

import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import java.nio.file.Path;

public class EnglishLanguageGen extends FabricLanguageProvider {
    protected EnglishLanguageGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.title", "Emissive Skin Renderer");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.glowingSkin", "Glowing Skin");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.glowingHorseArmor", "Glowing horse armor");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.palletsRate", "Deleting rate of glows skin creating");
        translationBuilder.add(  "text.autoconfig.emissive-skin-renderer.option.main.renderPlayerNameInThirdPerson", "Render name tag in third-person");
        translationBuilder.add( "create_skin_less_than", "Create Skin (L)");
        translationBuilder.add( "create_skin_greater_than", "Create Skin (G)");
        translationBuilder.add( "pallets_desc_l1" , "L is for `less than`");
        translationBuilder.add( "pallets_desc_l2", "G is for `greater than`");
        translationBuilder.add( "pallets_desc_l3", "In previous release some skin can't be glowing");
        translationBuilder.add(   "pallets_desc_l4", "`Greater than` when you have selected this option it will delete color that has delta more than `Base Color Delta");
        translationBuilder.add(     "pallets_desc_l5", "`Less than` when you have selected this option it will delete color that has delta less than `Base Color Delta`");
        translationBuilder.add(  "pallets_desc_l6", "(Most skin use less than option)");
        translationBuilder.add(  "not_final_gui", "NOT FINAL GUI WIP!!");
        translationBuilder.add("base_delta", "Base Color Delta");
    }
}
