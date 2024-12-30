package themasterkitty.fireworkindicator.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import themasterkitty.fireworkindicator.Main;

@Mixin(ClientPlayerInteractionManager.class)
public class MinecraftClientMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At(value = "HEAD"), method = "interactItem")
	public void interactItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = hand == Hand.MAIN_HAND ? player.getInventory().getMainHandStack() : player.getInventory().offHand.getFirst();
        if (stack.contains(DataComponentTypes.FIREWORKS) && player.isFallFlying() && player == client.player) {
            FireworksComponent fc = stack.get(DataComponentTypes.FIREWORKS);
            Main.duration = new int[] { 1170, 1550, 2220 }[(fc != null ? fc.flightDuration() : 1) - 1];
            Main.lastused = System.currentTimeMillis();
        }
    }
}