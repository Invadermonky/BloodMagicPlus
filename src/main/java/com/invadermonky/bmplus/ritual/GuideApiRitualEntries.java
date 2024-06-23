package com.invadermonky.bmplus.ritual;

import WayofTime.bloodmagic.ConfigHandler;
import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.compat.guideapi.GuideBloodMagic;
import WayofTime.bloodmagic.compat.guideapi.entry.EntryText;
import WayofTime.bloodmagic.util.helper.TextHelper;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.page.PageText;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GuideApiRitualEntries {
    public static void addEntries() {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();

        if(ConfigHandler.ritualAnimalGrowth) { addGuideRitualEntry("animalGrowth", entries); }
        if(ConfigHandlerRituals.ritualCondor) addGuideRitualEntry("condor", entries);
        if(ConfigHandlerRituals.ritualCrystalSplit) addGuideRitualEntry("crystalSplit", entries);
        if(ConfigHandlerRituals.ritualEllipsoid) addGuideRitualEntry("ellipsoid", entries);
        if(ConfigHandlerRituals.ritualEternalSoul) addGuideRitualEntry("eternalSoul", entries);
        if(ConfigHandlerRituals.ritualFeatheredEarth) addGuideRitualEntry("featheredEarth", entries);
        if(ConfigHandlerRituals.ritualGrounding) addGuideRitualEntry("grounding", entries);
        if(ConfigHandlerRituals.ritualVeilOfEvil) addGuideRitualEntry("veilOfEvil", entries);
        if(ConfigHandlerRituals.ritualWardOfSacrosanctity) addGuideRitualEntry("wardOfSacrosanctity", entries);

        entries.forEach((loc,entry) -> {
            for(IPage page : entry.pageList) {
                ((PageText) page).setUnicodeFlag(true);
            }
        });

        Book guideBook = GuideBloodMagic.guideBook;
        List<CategoryAbstract> categoryList = guideBook.getCategoryList();
        for(CategoryAbstract category : categoryList) {
            if(category.unlocCategoryName.equals("guide.BloodMagic.category.ritual")) {
                category.addEntries(entries);
                break;
            }
        }
    }

    private static void addGuideRitualEntry(String guideRitualName, Map<ResourceLocation, EntryAbstract> entries) {
        String keyBase = "guide." + Constants.Mod.MODID.toLowerCase() + ".entry.ritual.";
        List<IPage> pages = new ArrayList<>(PageHelper.pagesForLongText(TextHelper.localize(keyBase + guideRitualName + ".info"), 370));
        entries.put(new ResourceLocation(keyBase + guideRitualName), new EntryText(pages, TextHelper.localize(keyBase + guideRitualName), true));
    }
}
