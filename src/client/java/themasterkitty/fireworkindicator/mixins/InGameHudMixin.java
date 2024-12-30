package themasterkitty.fireworkindicator.mixins;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import themasterkitty.fireworkindicator.Main;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("HEAD"))
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (System.currentTimeMillis() < Main.lastused + Main.duration) {
            double prog = 1 - (double) (System.currentTimeMillis() - Main.lastused) / Main.duration;

            int w = 12, h = 5;
            context.drawTexture(Identifier.of("fireworkindicator", "textures/firework.png"),
                    context.getScaledWindowWidth() / 2 - 6, context.getScaledWindowHeight() / 2 - 11,
                    0, 0,
                    w, h,
                    w, h);
            context.drawTexture(Identifier.of("fireworkindicator", "textures/fireworkgray.png"),
                    context.getScaledWindowWidth() / 2 - 6 + (int) Math.ceil(12 * prog), context.getScaledWindowHeight() / 2 - 11,
                    (int) Math.ceil(w * prog), 0,
                    (int) (w * (1 - prog)), h,
                    w, h);
        }
    }
}