package com.vyrriox.areyousure;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import org.lwjgl.glfw.GLFW;

/**
 * Two-step confirmation: "are you sure?" then a captcha to type back.
 *
 * @author vyrriox
 */
public final class SureScreen extends Screen {
    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int CODE_LENGTH = 6;
    private static final int NOISE_DOTS = 40;
    private static final int[] COLORS = {0xFFFF5555, 0xFF55FF55, 0xFF5599FF, 0xFFFFFF55, 0xFFFF55FF, 0xFF55FFFF, 0xFFFFAA00};

    private final Component action;
    private final KeyMapping key;
    private final RandomSource random = RandomSource.create();

    private boolean captchaStep;
    private String code = "";
    private int[] charOffsets = new int[0];
    private int[] charColors = new int[0];
    private int[] noise = new int[0];
    private EditBox input;
    private Component feedback = Component.empty();

    public SureScreen(Component action, KeyMapping key) {
        super(Component.translatable("areyousure.title"));
        this.action = action;
        this.key = key;
    }

    @Override
    protected void init() {
        int cx = width / 2;
        int cy = height / 2;
        if (!captchaStep) {
            addRenderableWidget(Button.builder(Component.translatable("areyousure.yes"), b -> startCaptcha())
                    .bounds(cx - 105, cy + 10, 100, 20).build());
            addRenderableWidget(Button.builder(Component.translatable("areyousure.no"), b -> onClose())
                    .bounds(cx + 5, cy + 10, 100, 20).build());
            return;
        }
        String previous = input != null ? input.getValue() : "";
        input = new EditBox(font, cx - 60, cy + 10, 120, 20, Component.translatable("areyousure.captcha.input"));
        input.setMaxLength(CODE_LENGTH);
        input.setValue(previous);
        addRenderableWidget(input);
        setInitialFocus(input);
        addRenderableWidget(Button.builder(Component.translatable("areyousure.captcha.verify"), b -> verify())
                .bounds(cx - 105, cy + 40, 70, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("areyousure.captcha.refresh"), b -> newCode())
                .bounds(cx - 30, cy + 40, 60, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("areyousure.no"), b -> onClose())
                .bounds(cx + 35, cy + 40, 70, 20).build());
    }

    private void startCaptcha() {
        captchaStep = true;
        newCode();
        rebuildWidgets();
    }

    private void newCode() {
        StringBuilder sb = new StringBuilder();
        charOffsets = new int[CODE_LENGTH];
        charColors = new int[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
            charOffsets[i] = random.nextInt(9) - 4;
            charColors[i] = COLORS[random.nextInt(COLORS.length)];
        }
        code = sb.toString();
        noise = new int[NOISE_DOTS * 3];
        for (int i = 0; i < noise.length; i += 3) {
            noise[i] = random.nextInt(138);
            noise[i + 1] = random.nextInt(28);
            noise[i + 2] = COLORS[random.nextInt(COLORS.length)] & 0x88FFFFFF;
        }
        if (input != null) {
            input.setValue("");
        }
    }

    private void verify() {
        if (input.getValue().trim().equalsIgnoreCase(code)) {
            ActionGuard.grant(key);
            onClose();
            return;
        }
        feedback = Component.translatable("areyousure.captcha.wrong");
        newCode();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (captchaStep && (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER)) {
            verify();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        int cx = width / 2;
        int cy = height / 2;
        graphics.drawCenteredString(font, title, cx, cy - 60, 0xFFFF5555);
        graphics.drawCenteredString(font, Component.translatable("areyousure.question", action), cx, cy - 45, 0xFFFFFFFF);
        if (!captchaStep) {
            graphics.drawCenteredString(font, Component.translatable("areyousure.warning"), cx, cy - 25, 0xFFAAAAAA);
            return;
        }
        graphics.drawCenteredString(font, Component.translatable("areyousure.captcha.prompt"), cx, cy - 32, 0xFFAAAAAA);
        int left = cx - 70;
        int top = cy - 22;
        graphics.fill(left, top, left + 140, top + 30, 0xFF202020);
        for (int i = 0; i < noise.length; i += 3) {
            graphics.fill(left + noise[i], top + noise[i + 1], left + noise[i] + 2, top + noise[i + 1] + 2, noise[i + 2]);
        }
        for (int i = 0; i < code.length(); i++) {
            graphics.drawString(font, String.valueOf(code.charAt(i)), left + 16 + i * 19, top + 11 + charOffsets[i], charColors[i], true);
        }
        graphics.fill(left + 4, top + 15, left + 136, top + 16, 0xAAFFFFFF);
        graphics.drawCenteredString(font, feedback, cx, cy + 65, 0xFFFF5555);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
