package com.vyrriox.areyousure;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * Are You Sure? - every interaction asks for confirmation and a captcha.
 *
 * @author vyrriox
 */
@Mod(AreYouSure.MODID)
public final class AreYouSure {
    public static final String MODID = "areyousure";

    public AreYouSure() {
        if (FMLEnvironment.dist.isClient()) {
            MinecraftForge.EVENT_BUS.addListener(ActionGuard::onInteraction);
            MinecraftForge.EVENT_BUS.addListener(ActionGuard::onClientTick);
        }
    }
}
