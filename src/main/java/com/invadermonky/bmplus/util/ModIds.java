package com.invadermonky.bmplus.util;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.pam.harvestcraft.Reference;

import javax.annotation.Nullable;

public enum ModIds {
    harvestcraft(ConstIds.harvestcraft, ConstVersions.harvestcraft),
    mystical_agriculture(ConstIds.mystical_agriculture),
    unique_crops(ConstIds.unique_crops, ConstVersions.unique_crops),
    ;

    public final String modId;
    public final String version;
    public final boolean isLoaded;

    ModIds(String modId) {
        this(modId, null);
    }

    ModIds(String modId, @Nullable String version) {
        this.modId = modId;
        this.version = version;
        this.isLoaded = ModHelper.isModLoaded(modId, version);
    }
    
    ModIds(String modId, @Nullable String version, boolean isMinVersion, boolean isMaxVersion) {
        this.modId = modId;
        this.version = version;
        this.isLoaded = ModHelper.isModLoaded(modId, version, isMinVersion, isMaxVersion);
    }
    
    public static class ConstIds {
        public static final String harvestcraft = Reference.MODID;
        public static final String mystical_agriculture = MysticalAgriculture.MOD_ID;
        public static final String unique_crops = UniqueCrops.MOD_ID;
    }

    public static class ConstVersions {
        public static final String harvestcraft = Reference.VERSION;
        public static final String unique_crops = UniqueCrops.VERSION;
    }
}
