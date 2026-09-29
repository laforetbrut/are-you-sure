package com.vyrriox.areyousure;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
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
            InputEvent.InteractionKeyMappingTriggered.BUS.addListener(ActionGuard::onInteraction);
            TickEvent.ClientTickEvent.Post.BUS.addListener(ActionGuard::onClientTick);
        }
    }
}
