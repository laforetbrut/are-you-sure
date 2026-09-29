package com.vyrriox.areyousure;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Are You Sure? - every interaction asks for confirmation and a captcha.
 *
 * @author vyrriox
 */
@Mod(value = AreYouSure.MODID, dist = Dist.CLIENT)
public final class AreYouSure {
    public static final String MODID = "areyousure";

    public AreYouSure() {
        NeoForge.EVENT_BUS.addListener(ActionGuard::onInteraction);
        NeoForge.EVENT_BUS.addListener(ActionGuard::onClientTick);
    }
}
