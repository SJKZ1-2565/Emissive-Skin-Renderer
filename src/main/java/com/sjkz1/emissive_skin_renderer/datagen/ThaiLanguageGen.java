package com.sjkz1.emissive_skin_renderer.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ThaiLanguageGen extends FabricLanguageProvider {
    protected ThaiLanguageGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "th_th");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.title", "Emissive Skin Renderer");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.glowingSkin", "สกินเรืองแสง");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.glowingHorseArmor", "เกราะม้าเรืองแสง");
        translationBuilder.add("create_skin", "สร้างสกิน");
        translationBuilder.add("delete_rate", "เดลต้าของสี");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.palletsRate", "เดลต้าของสี");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.renderPlayerNameInThirdPerson", "แสดงชื่อในมุมมองบุคคลที่สาม");
    }
}
