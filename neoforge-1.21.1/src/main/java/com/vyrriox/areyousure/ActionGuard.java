package com.vyrriox.areyousure;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;

/**
 * Intercepts attack / use / pick-block inputs and requires a confirmation plus a captcha
 * before letting a single action through.
 *
 * @author vyrriox
 */
public final class ActionGuard {
    /** How long a solved captcha stays valid if the player does not act on it. */
    private static final long PASS_TIMEOUT_MS = 10_000L;

    private static KeyMapping passKey;
    private static long passExpiresAt;
    private static boolean passInUse;

    private ActionGuard() {
    }

    public static void onInteraction(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        KeyMapping key = event.getKeyMapping();
        if (passKey == key && (passInUse || System.currentTimeMillis() < passExpiresAt)) {
            passInUse = true;
            return;
        }
        event.setCanceled(true);
        event.setSwingHand(false);
        if (mc.screen == null) {
            String kind = event.isAttack() ? "attack" : event.isPickBlock() ? "pick" : "use";
            ItemStack held = event.isUseItem() ? mc.player.getItemInHand(event.getHand()) : ItemStack.EMPTY;
            mc.setScreen(new SureScreen(describe(mc, kind, held), key));
        }
    }

    /** Consumes the pass once the key used for the approved action is released. */
    public static void onClientTick(ClientTickEvent.Post event) {
        if (passKey == null) {
            return;
        }
        if (passInUse ? !passKey.isDown() : System.currentTimeMillis() >= passExpiresAt) {
            passKey = null;
            passInUse = false;
        }
    }

    static void grant(KeyMapping key) {
        passKey = key;
        passInUse = false;
        passExpiresAt = System.currentTimeMillis() + PASS_TIMEOUT_MS;
    }

    private static Component describe(Minecraft mc, String kind, ItemStack held) {
        HitResult hit = mc.hitResult;
        if (hit instanceof BlockHitResult blockHit && hit.getType() == HitResult.Type.BLOCK && mc.level != null) {
            Component name = mc.level.getBlockState(blockHit.getBlockPos()).getBlock().getName();
            return Component.translatable("areyousure.action." + kind + ".block", name);
        }
        if (hit instanceof EntityHitResult entityHit && hit.getType() == HitResult.Type.ENTITY) {
            return Component.translatable("areyousure.action." + kind + ".entity", entityHit.getEntity().getDisplayName());
        }
        if (!held.isEmpty()) {
            return Component.translatable("areyousure.action.use.item", held.getHoverName());
        }
        return Component.translatable("areyousure.action." + kind + ".air");
    }
}
